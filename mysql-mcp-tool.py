#!/usr/bin/env python3
"""
MySQL MCP Tool - 用于连接和操作本地 MySQL 数据库
使用方法: python mysql-mcp-tool.py [command] [args]
"""

import mysql.connector
import json
import sys
import argparse
from typing import Dict, List, Any, Optional

class MySQLMCPTool:
    def __init__(self, host='localhost', user='root', password='123456', port=3306):
        self.host = host
        self.user = user
        self.password = password
        self.port = port
        self.connection = None
        self.cursor = None
    
    def connect(self, database: Optional[str] = None) -> Dict[str, Any]:
        """连接到 MySQL 数据库"""
        try:
            config = {
                'host': self.host,
                'user': self.user,
                'password': self.password,
                'port': self.port,
                'charset': 'utf8mb4',
                'collation': 'utf8mb4_unicode_ci'
            }
            
            if database:
                config['database'] = database
            
            self.connection = mysql.connector.connect(**config)
            self.cursor = self.connection.cursor(dictionary=True)
            
            return {
                "status": "success",
                "message": f"成功连接到 MySQL 服务器 {self.host}:{self.port}" + (f" 数据库: {database}" if database else ""),
                "server_info": self.connection.get_server_info()
            }
        except mysql.connector.Error as e:
            return {
                "status": "error",
                "message": f"连接失败: {str(e)}"
            }
    
    def disconnect(self) -> Dict[str, Any]:
        """断开数据库连接"""
        try:
            if self.cursor:
                self.cursor.close()
            if self.connection:
                self.connection.close()
            return {
                "status": "success",
                "message": "已断开数据库连接"
            }
        except Exception as e:
            return {
                "status": "error",
                "message": f"断开连接时出错: {str(e)}"
            }
    
    def execute_query(self, query: str) -> Dict[str, Any]:
        """执行 SQL 查询"""
        try:
            if not self.connection or not self.connection.is_connected():
                return {
                    "status": "error",
                    "message": "数据库未连接，请先连接数据库"
                }
            
            self.cursor.execute(query)
            
            # 判断是否是查询语句
            if query.strip().upper().startswith(('SELECT', 'SHOW', 'DESCRIBE', 'DESC', 'EXPLAIN')):
                results = self.cursor.fetchall()
                return {
                    "status": "success",
                    "message": f"查询成功，返回 {len(results)} 行数据",
                    "data": results,
                    "row_count": len(results)
                }
            else:
                # 对于 INSERT, UPDATE, DELETE 等语句
                self.connection.commit()
                return {
                    "status": "success",
                    "message": f"执行成功，影响 {self.cursor.rowcount} 行",
                    "affected_rows": self.cursor.rowcount
                }
                
        except mysql.connector.Error as e:
            return {
                "status": "error",
                "message": f"SQL 执行失败: {str(e)}"
            }
    
    def show_databases(self) -> Dict[str, Any]:
        """显示所有数据库"""
        return self.execute_query("SHOW DATABASES")
    
    def show_tables(self, database: Optional[str] = None) -> Dict[str, Any]:
        """显示指定数据库的所有表"""
        if database:
            query = f"SHOW TABLES FROM `{database}`"
        else:
            query = "SHOW TABLES"
        return self.execute_query(query)
    
    def describe_table(self, table_name: str, database: Optional[str] = None) -> Dict[str, Any]:
        """描述表结构"""
        if database:
            query = f"DESCRIBE `{database}`.`{table_name}`"
        else:
            query = f"DESCRIBE `{table_name}`"
        return self.execute_query(query)
    
    def create_database(self, database_name: str) -> Dict[str, Any]:
        """创建数据库"""
        query = f"CREATE DATABASE IF NOT EXISTS `{database_name}` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci"
        return self.execute_query(query)
    
    def use_database(self, database_name: str) -> Dict[str, Any]:
        """切换到指定数据库"""
        query = f"USE `{database_name}`"
        return self.execute_query(query)

def main():
    parser = argparse.ArgumentParser(description='MySQL MCP Tool')
    parser.add_argument('command', choices=[
        'connect', 'disconnect', 'query', 'databases', 'tables', 'describe', 
        'create-db', 'use-db', 'test-connection'
    ], help='要执行的命令')
    
    parser.add_argument('--database', '-d', help='数据库名称')
    parser.add_argument('--table', '-t', help='表名称')
    parser.add_argument('--sql', '-s', help='要执行的 SQL 语句')
    parser.add_argument('--host', default='localhost', help='MySQL 主机地址')
    parser.add_argument('--port', type=int, default=3306, help='MySQL 端口')
    parser.add_argument('--user', '-u', default='root', help='MySQL 用户名')
    parser.add_argument('--password', '-p', default='123456', help='MySQL 密码')
    
    args = parser.parse_args()
    
    # 创建 MySQL 工具实例
    mysql_tool = MySQLMCPTool(
        host=args.host,
        user=args.user,
        password=args.password,
        port=args.port
    )
    
    result = {}
    
    try:
        if args.command == 'test-connection':
            result = mysql_tool.connect()
            if result['status'] == 'success':
                mysql_tool.disconnect()
        
        elif args.command == 'connect':
            result = mysql_tool.connect(args.database)
        
        elif args.command == 'disconnect':
            result = mysql_tool.disconnect()
        
        elif args.command == 'query':
            if not args.sql:
                result = {"status": "error", "message": "请提供 SQL 语句 (--sql)"}
            else:
                mysql_tool.connect(args.database)
                result = mysql_tool.execute_query(args.sql)
                mysql_tool.disconnect()
        
        elif args.command == 'databases':
            mysql_tool.connect()
            result = mysql_tool.show_databases()
            mysql_tool.disconnect()
        
        elif args.command == 'tables':
            mysql_tool.connect(args.database)
            result = mysql_tool.show_tables(args.database)
            mysql_tool.disconnect()
        
        elif args.command == 'describe':
            if not args.table:
                result = {"status": "error", "message": "请提供表名称 (--table)"}
            else:
                mysql_tool.connect(args.database)
                result = mysql_tool.describe_table(args.table, args.database)
                mysql_tool.disconnect()
        
        elif args.command == 'create-db':
            if not args.database:
                result = {"status": "error", "message": "请提供数据库名称 (--database)"}
            else:
                mysql_tool.connect()
                result = mysql_tool.create_database(args.database)
                mysql_tool.disconnect()
        
        elif args.command == 'use-db':
            if not args.database:
                result = {"status": "error", "message": "请提供数据库名称 (--database)"}
            else:
                mysql_tool.connect()
                result = mysql_tool.use_database(args.database)
                mysql_tool.disconnect()
    
    except Exception as e:
        result = {
            "status": "error",
            "message": f"执行命令时出错: {str(e)}"
        }
    
    # 输出结果
    print(json.dumps(result, ensure_ascii=False, indent=2))

if __name__ == '__main__':
    main()
