#!/usr/bin/env python3
"""
MySQL MCP Server - 符合 MCP 协议的 MySQL 数据库服务器
用于与 Augment 等 MCP 客户端交互
"""

import asyncio
import json
import sys
import os
from typing import Any, Dict, List, Optional
import mysql.connector
from mysql.connector import Error

# MCP 协议相关的消息类型
class MCPServer:
    def __init__(self):
        self.mysql_config = {
            'host': os.getenv('MYSQL_HOST', 'localhost'),
            'port': int(os.getenv('MYSQL_PORT', '3306')),
            'user': os.getenv('MYSQL_USER', 'root'),
            'password': os.getenv('MYSQL_PASSWORD', '123456'),
            'database': os.getenv('MYSQL_DATABASE', 'zhaoruoyi'),
            'charset': 'utf8mb4',
            'collation': 'utf8mb4_unicode_ci'
        }
        self.connection = None
        self.cursor = None

    async def connect_mysql(self) -> Dict[str, Any]:
        """连接到 MySQL 数据库"""
        try:
            self.connection = mysql.connector.connect(**self.mysql_config)
            self.cursor = self.connection.cursor(dictionary=True)
            return {
                "success": True,
                "message": f"成功连接到 MySQL 服务器 {self.mysql_config['host']}:{self.mysql_config['port']}",
                "server_info": self.connection.server_info
            }
        except Error as e:
            return {
                "success": False,
                "error": f"连接失败: {str(e)}"
            }

    async def execute_query(self, query: str) -> Dict[str, Any]:
        """执行 SQL 查询"""
        try:
            if not self.connection or not self.connection.is_connected():
                connect_result = await self.connect_mysql()
                if not connect_result["success"]:
                    return connect_result

            self.cursor.execute(query)
            
            # 判断是否是查询语句
            if query.strip().upper().startswith(('SELECT', 'SHOW', 'DESCRIBE', 'DESC', 'EXPLAIN')):
                results = self.cursor.fetchall()
                return {
                    "success": True,
                    "type": "query",
                    "data": results,
                    "row_count": len(results)
                }
            else:
                # 对于 INSERT, UPDATE, DELETE 等语句
                self.connection.commit()
                return {
                    "success": True,
                    "type": "command",
                    "affected_rows": self.cursor.rowcount,
                    "message": f"执行成功，影响 {self.cursor.rowcount} 行"
                }
                
        except Error as e:
            return {
                "success": False,
                "error": f"SQL 执行失败: {str(e)}"
            }

    async def list_tools(self) -> Dict[str, Any]:
        """返回可用的工具列表"""
        return {
            "tools": [
                {
                    "name": "mysql_query",
                    "description": "执行 MySQL 查询语句",
                    "inputSchema": {
                        "type": "object",
                        "properties": {
                            "query": {
                                "type": "string",
                                "description": "要执行的 SQL 查询语句"
                            }
                        },
                        "required": ["query"]
                    }
                },
                {
                    "name": "mysql_show_databases",
                    "description": "显示所有数据库",
                    "inputSchema": {
                        "type": "object",
                        "properties": {}
                    }
                },
                {
                    "name": "mysql_show_tables",
                    "description": "显示当前数据库的所有表",
                    "inputSchema": {
                        "type": "object",
                        "properties": {
                            "database": {
                                "type": "string",
                                "description": "数据库名称（可选）"
                            }
                        }
                    }
                },
                {
                    "name": "mysql_describe_table",
                    "description": "描述表结构",
                    "inputSchema": {
                        "type": "object",
                        "properties": {
                            "table": {
                                "type": "string",
                                "description": "表名称"
                            },
                            "database": {
                                "type": "string",
                                "description": "数据库名称（可选）"
                            }
                        },
                        "required": ["table"]
                    }
                }
            ]
        }

    async def call_tool(self, name: str, arguments: Dict[str, Any]) -> Dict[str, Any]:
        """调用指定的工具"""
        try:
            if name == "mysql_query":
                query = arguments.get("query", "")
                if not query:
                    return {"success": False, "error": "查询语句不能为空"}
                return await self.execute_query(query)
            
            elif name == "mysql_show_databases":
                return await self.execute_query("SHOW DATABASES")
            
            elif name == "mysql_show_tables":
                database = arguments.get("database")
                if database:
                    query = f"SHOW TABLES FROM `{database}`"
                else:
                    query = "SHOW TABLES"
                return await self.execute_query(query)
            
            elif name == "mysql_describe_table":
                table = arguments.get("table", "")
                database = arguments.get("database")
                if not table:
                    return {"success": False, "error": "表名称不能为空"}
                
                if database:
                    query = f"DESCRIBE `{database}`.`{table}`"
                else:
                    query = f"DESCRIBE `{table}`"
                return await self.execute_query(query)
            
            else:
                return {"success": False, "error": f"未知的工具: {name}"}
                
        except Exception as e:
            return {"success": False, "error": f"工具调用失败: {str(e)}"}

    async def handle_message(self, message: Dict[str, Any]) -> Dict[str, Any]:
        """处理 MCP 消息"""
        method = message.get("method", "")
        params = message.get("params", {})
        
        if method == "tools/list":
            return await self.list_tools()
        
        elif method == "tools/call":
            tool_name = params.get("name", "")
            arguments = params.get("arguments", {})
            return await self.call_tool(tool_name, arguments)
        
        elif method == "initialize":
            return {
                "protocolVersion": "2024-11-05",
                "capabilities": {
                    "tools": {}
                },
                "serverInfo": {
                    "name": "mysql-mcp-server",
                    "version": "1.0.0"
                }
            }
        
        else:
            return {"error": f"未知的方法: {method}"}

    async def run(self):
        """运行 MCP 服务器"""
        try:
            # 连接到 MySQL
            connect_result = await self.connect_mysql()
            if not connect_result["success"]:
                print(json.dumps({"error": connect_result["error"]}), file=sys.stderr)
                return

            # 处理标准输入的消息
            while True:
                try:
                    line = input()
                    if not line.strip():
                        continue
                    
                    message = json.loads(line)
                    response = await self.handle_message(message)
                    
                    # 添加消息 ID 到响应中
                    if "id" in message:
                        response["id"] = message["id"]
                    
                    print(json.dumps(response))
                    sys.stdout.flush()
                    
                except EOFError:
                    break
                except json.JSONDecodeError as e:
                    error_response = {"error": f"JSON 解析错误: {str(e)}"}
                    print(json.dumps(error_response))
                    sys.stdout.flush()
                except Exception as e:
                    error_response = {"error": f"处理消息时出错: {str(e)}"}
                    print(json.dumps(error_response))
                    sys.stdout.flush()
                    
        except KeyboardInterrupt:
            pass
        finally:
            if self.cursor:
                self.cursor.close()
            if self.connection:
                self.connection.close()

async def main():
    server = MCPServer()
    await server.run()

if __name__ == "__main__":
    asyncio.run(main())
