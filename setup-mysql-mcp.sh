#!/bin/bash

# MySQL MCP 工具安装脚本
echo "=== MySQL MCP 工具安装脚本 ==="

# 检查 Python 是否安装
if ! command -v python3 &> /dev/null; then
    echo "错误: Python 3 未安装，请先安装 Python 3"
    exit 1
fi

echo "✓ Python 3 已安装"

# 检查 pip 是否安装
if ! command -v pip3 &> /dev/null; then
    echo "错误: pip3 未安装，请先安装 pip3"
    exit 1
fi

echo "✓ pip3 已安装"

# 安装 mysql-connector-python
echo "正在安装 mysql-connector-python..."
pip3 install mysql-connector-python

if [ $? -eq 0 ]; then
    echo "✓ mysql-connector-python 安装成功"
else
    echo "✗ mysql-connector-python 安装失败"
    exit 1
fi

# 给脚本添加执行权限
chmod +x mysql-mcp-tool.py

echo "✓ 已添加执行权限"

# 测试连接
echo "正在测试 MySQL 连接..."
python3 mysql-mcp-tool.py test-connection

if [ $? -eq 0 ]; then
    echo "✓ MySQL MCP 工具安装完成！"
    echo ""
    echo "使用方法："
    echo "  python3 mysql-mcp-tool.py --help"
    echo "  python3 mysql-mcp-tool.py test-connection"
    echo "  python3 mysql-mcp-tool.py databases"
    echo ""
    echo "详细使用说明请查看 MySQL-MCP-使用说明.md"
else
    echo "⚠ 安装完成，但 MySQL 连接测试失败"
    echo "请检查 MySQL 服务是否运行，用户名密码是否正确"
fi
