#!/bin/bash

# MySQL MCP 工具使用示例脚本
echo "=== MySQL MCP 工具使用示例 ==="

# 1. 测试连接
echo "1. 测试 MySQL 连接..."
python3 mysql-mcp-tool.py test-connection

echo ""

# 2. 显示所有数据库
echo "2. 显示所有数据库..."
python3 mysql-mcp-tool.py databases

echo ""

# 3. 显示 zhaoruoyi 数据库中的表
echo "3. 显示 zhaoruoyi 数据库中的表..."
python3 mysql-mcp-tool.py tables --database zhaoruoyi

echo ""

# 4. 查看 user 表结构
echo "4. 查看 user 表结构..."
python3 mysql-mcp-tool.py describe --database zhaoruoyi --table user

echo ""

# 5. 查询 user 表数据
echo "5. 查询 user 表数据..."
python3 mysql-mcp-tool.py query --database zhaoruoyi --sql "SELECT * FROM user"

echo ""

# 6. 为 ruoyi-module-user 项目创建完整的用户表
echo "6. 创建完整的用户表结构..."
python3 mysql-mcp-tool.py query --database zhaoruoyi --sql "
DROP TABLE IF EXISTS user_ruoyi;
CREATE TABLE user_ruoyi (
  user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(30) NOT NULL UNIQUE,
  nick_name VARCHAR(30) NOT NULL,
  email VARCHAR(50) DEFAULT '',
  phone_number VARCHAR(11) DEFAULT '',
  sex CHAR(1) DEFAULT '0',
  avatar VARCHAR(100) DEFAULT '',
  password VARCHAR(100) DEFAULT '',
  status CHAR(1) DEFAULT '0',
  del_flag CHAR(1) DEFAULT '0',
  login_ip VARCHAR(128) DEFAULT '',
  login_date DATETIME DEFAULT NULL,
  create_by VARCHAR(64) DEFAULT '',
  create_time DATETIME DEFAULT NULL,
  update_by VARCHAR(64) DEFAULT '',
  update_time DATETIME DEFAULT NULL,
  remark VARCHAR(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';"

echo ""

# 7. 插入测试数据
echo "7. 插入测试数据..."
python3 mysql-mcp-tool.py query --database zhaoruoyi --sql "
INSERT INTO user_ruoyi (username, nick_name, email, phone_number, sex, password, status, create_by, create_time) VALUES 
('admin', '管理员', 'admin@ruoyi.com', '15888888888', '1', 'password123', '0', 'system', NOW()),
('user1', '测试用户1', 'user1@test.com', '13800138001', '0', 'password123', '0', 'system', NOW()),
('user2', '测试用户2', 'user2@test.com', '13800138002', '1', 'password123', '0', 'system', NOW());"

echo ""

# 8. 查询新创建的表数据
echo "8. 查询新创建的表数据..."
python3 mysql-mcp-tool.py query --database zhaoruoyi --sql "SELECT user_id, username, nick_name, email, phone_number, sex, status, create_time FROM user_ruoyi"

echo ""

# 9. 查看新表结构
echo "9. 查看新表结构..."
python3 mysql-mcp-tool.py describe --database zhaoruoyi --table user_ruoyi

echo ""
echo "=== 示例完成 ==="
echo ""
echo "常用命令："
echo "  python3 mysql-mcp-tool.py test-connection"
echo "  python3 mysql-mcp-tool.py databases"
echo "  python3 mysql-mcp-tool.py tables --database zhaoruoyi"
echo "  python3 mysql-mcp-tool.py query --database zhaoruoyi --sql 'SELECT * FROM user_ruoyi'"
echo ""
