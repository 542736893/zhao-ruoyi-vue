#!/usr/bin/env python3
"""
测试 MySQL MCP 工具
"""

import asyncio
import json
import subprocess
import sys
import os

class MCPTester:
    def __init__(self, tool_path):
        self.tool_path = tool_path
        self.process = None

    async def start_tool(self):
        """启动 MCP 工具"""
        try:
            env = os.environ.copy()
            env.update({
                'MYSQL_HOST': 'localhost',
                'MYSQL_PORT': '3306',
                'MYSQL_USER': 'root',
                'MYSQL_PASSWORD': '123456',
                'MYSQL_DATABASE': 'zhaoruoyi'
            })
            
            self.process = subprocess.Popen(
                [sys.executable, self.tool_path],
                stdin=subprocess.PIPE,
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE,
                text=True,
                bufsize=0,
                env=env
            )
            print("✓ MySQL MCP 工具已启动")
            return True
        except Exception as e:
            print(f"✗ 启动工具失败: {e}")
            return False

    async def send_message(self, message):
        """发送消息到工具"""
        try:
            message_json = json.dumps(message) + '\n'
            self.process.stdin.write(message_json)
            self.process.stdin.flush()
            
            # 读取响应
            response_line = self.process.stdout.readline()
            if response_line:
                return json.loads(response_line.strip())
            return None
        except Exception as e:
            print(f"发送消息失败: {e}")
            return None

    async def test_connection(self):
        """测试数据库连接"""
        print("\n1. 测试数据库连接...")
        message = {
            "jsonrpc": "2.0",
            "id": 1,
            "method": "tools/call",
            "params": {
                "name": "mysql_show_databases",
                "arguments": {}
            }
        }
        
        response = await self.send_message(message)
        if response and response.get("success"):
            print("✓ 数据库连接成功")
            print(f"  找到 {response.get('row_count', 0)} 个数据库")
            return True
        else:
            print("✗ 数据库连接失败")
            print(f"  错误: {response.get('error', '未知错误')}")
            return False

    async def test_query_user_table(self):
        """测试查询用户表"""
        print("\n2. 测试查询用户表...")
        message = {
            "jsonrpc": "2.0",
            "id": 2,
            "method": "tools/call",
            "params": {
                "name": "mysql_query",
                "arguments": {
                    "query": "SELECT COUNT(*) as total FROM user"
                }
            }
        }
        
        response = await self.send_message(message)
        if response and response.get("success"):
            print("✓ 查询用户表成功")
            if response.get("data"):
                total = response["data"][0].get("total", 0)
                print(f"  用户总数: {total}")
            return True
        else:
            print("✗ 查询用户表失败")
            print(f"  错误: {response.get('error', '未知错误')}")
            return False

    async def stop_tool(self):
        """停止工具"""
        if self.process:
            self.process.terminate()
            try:
                self.process.wait(timeout=5)
            except subprocess.TimeoutExpired:
                self.process.kill()
            print("✓ MySQL MCP 工具已停止")

    async def run_tests(self):
        """运行所有测试"""
        print("=== MySQL MCP 工具测试 ===")
        
        if not await self.start_tool():
            return False
        
        try:
            await asyncio.sleep(1)  # 等待工具启动
            
            tests = [
                self.test_connection,
                self.test_query_user_table
            ]
            
            passed = 0
            total = len(tests)
            
            for test in tests:
                try:
                    if await test():
                        passed += 1
                    await asyncio.sleep(0.5)
                except Exception as e:
                    print(f"✗ 测试异常: {e}")
            
            print(f"\n=== 测试结果 ===")
            print(f"通过: {passed}/{total}")
            print(f"成功率: {passed/total*100:.1f}%")
            
            return passed == total
            
        finally:
            await self.stop_tool()

async def main():
    tool_path = "/Users/zhaozb/Documents/Document/mcp/mysql/mysql-mcp-tool.py"
    tester = MCPTester(tool_path)
    success = await tester.run_tests()
    
    if success:
        print("\n🎉 所有测试通过！MySQL MCP 工具工作正常。")
    else:
        print("\n❌ 部分测试失败，请检查配置和连接。")
    
    return success

if __name__ == "__main__":
    asyncio.run(main())
