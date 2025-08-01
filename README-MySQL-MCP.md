# MySQL MCP 工具

这是一个用于访问本地 MySQL 数据库的 MCP (Model Context Protocol) 工具集。

## 🚀 快速开始

### 1. 安装依赖
```bash
pip3 install mysql-connector-python
```

### 2. 测试连接
```bash
python3 mysql-mcp-tool.py test-connection
```

### 3. 运行示例
```bash
./mysql-mcp-examples.sh
```

## 📁 文件说明

| 文件名 | 描述 |
|--------|------|
| `mysql-mcp-tool.py` | 主要的 MySQL MCP 工具 |
| `mysql-mcp-config.json` | 配置文件和常用命令示例 |
| `mysql-mcp-examples.sh` | 使用示例脚本 |
| `setup-mysql-mcp.sh` | 自动安装脚本 |
| `MySQL-MCP-使用说明.md` | 详细使用说明文档 |

## 🔧 默认配置

- **主机**: localhost
- **端口**: 3306
- **用户名**: root
- **密码**: 123456

## 📋 常用命令

### 基本操作
```bash
# 测试连接
python3 mysql-mcp-tool.py test-connection

# 显示所有数据库
python3 mysql-mcp-tool.py databases

# 显示表
python3 mysql-mcp-tool.py tables --database zhaoruoyi

# 查看表结构
python3 mysql-mcp-tool.py describe --database zhaoruoyi --table user

# 执行查询
python3 mysql-mcp-tool.py query --database zhaoruoyi --sql "SELECT * FROM user"
```

### 数据库管理
```bash
# 创建数据库
python3 mysql-mcp-tool.py create-db --database mydb

# 执行复杂 SQL
python3 mysql-mcp-tool.py query --database zhaoruoyi --sql "
CREATE TABLE test (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
)"
```

## 🎯 为 ruoyi-module-user 项目使用

### 1. 创建用户表
```bash
python3 mysql-mcp-tool.py query --database zhaoruoyi --sql "
CREATE TABLE IF NOT EXISTS user (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
```

### 2. 插入测试数据
```bash
python3 mysql-mcp-tool.py query --database zhaoruoyi --sql "
INSERT INTO user (username, nick_name, email, phone_number, sex, password, status, create_by, create_time) VALUES 
('admin', '管理员', 'admin@ruoyi.com', '15888888888', '1', 'password123', '0', 'system', NOW()),
('user1', '测试用户1', 'user1@test.com', '13800138001', '0', 'password123', '0', 'system', NOW())"
```

### 3. 查询数据
```bash
python3 mysql-mcp-tool.py query --database zhaoruoyi --sql "SELECT * FROM user"
```

## 🔍 输出格式

所有命令都返回 JSON 格式的结果：

```json
{
  "status": "success",
  "message": "查询成功，返回 2 行数据",
  "data": [...],
  "row_count": 2
}
```

## ⚠️ 注意事项

1. 确保 MySQL 服务正在运行
2. 验证用户名和密码是否正确
3. 在生产环境中请使用更安全的密码
4. 定期备份重要数据

## 🆘 故障排除

### 连接失败
- 检查 MySQL 服务状态
- 验证连接参数
- 检查防火墙设置

### 权限错误
- 确保用户有足够权限
- 检查数据库访问权限

## 📚 更多信息

查看 `MySQL-MCP-使用说明.md` 获取详细的使用说明和高级功能。
