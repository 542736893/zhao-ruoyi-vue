# MySQL MCP for Augment 使用说明

这是一个专为 Augment 设计的 MySQL MCP (Model Context Protocol) 服务器，允许 Augment 通过 MCP 协议与本地 MySQL 数据库进行交互。

## 📁 文件说明

| 文件名 | 描述 |
|--------|------|
| `mysql-mcp-server.py` | MCP 协议兼容的 MySQL 服务器实现 |
| `mysql-mcp-server.json` | 完整的 MCP 服务器配置文件（包含详细配置） |
| `augment-mysql-mcp.json` | Augment 专用的简洁配置文件 |
| `test-mysql-mcp.py` | MCP 服务器测试脚本 |

## 🚀 快速配置

### 1. 确保依赖已安装
```bash
pip3 install mysql-connector-python
```

### 2. 配置 Augment
将 `augment-mysql-mcp.json` 的内容添加到您的 Augment 配置中：

```json
{
  "mcpServers": {
    "mysql": {
      "command": "python3",
      "args": [
        "/Users/zhaozb/Documents/Code/zhao-ruoyi-vue/mysql-mcp-server.py"
      ],
      "env": {
        "MYSQL_HOST": "localhost",
        "MYSQL_PORT": "3306",
        "MYSQL_USER": "root",
        "MYSQL_PASSWORD": "123456",
        "MYSQL_DATABASE": "zhaoruoyi"
      }
    }
  }
}
```

### 3. 测试连接
```bash
python3 test-mysql-mcp.py
```

## 🛠️ 可用工具

MCP 服务器提供以下工具：

### 1. `mysql_query`
执行任意 MySQL 查询语句

**参数:**
- `query` (string): 要执行的 SQL 语句

**示例:**
```sql
SELECT * FROM user LIMIT 10
INSERT INTO user (username, age) VALUES ('test', 25)
UPDATE user SET age = 26 WHERE username = 'test'
DELETE FROM user WHERE username = 'test'
```

### 2. `mysql_show_databases`
显示所有数据库

**参数:** 无

### 3. `mysql_show_tables`
显示指定数据库的所有表

**参数:**
- `database` (string, 可选): 数据库名称

### 4. `mysql_describe_table`
显示表结构

**参数:**
- `table` (string): 表名称
- `database` (string, 可选): 数据库名称

## 🎯 在 Augment 中使用

配置完成后，您可以在 Augment 中使用以下方式与 MySQL 数据库交互：

### 查询数据
```
请查询 zhaoruoyi 数据库中的所有用户
```

### 查看表结构
```
请显示 user 表的结构
```

### 执行复杂查询
```
请查询用户表中年龄大于 18 的所有用户，按创建时间排序
```

### 数据库管理
```
请显示所有数据库
请显示 zhaoruoyi 数据库中的所有表
```

## ⚙️ 配置选项

### 环境变量

| 变量名 | 默认值 | 描述 |
|--------|--------|------|
| `MYSQL_HOST` | localhost | MySQL 服务器地址 |
| `MYSQL_PORT` | 3306 | MySQL 端口 |
| `MYSQL_USER` | root | MySQL 用户名 |
| `MYSQL_PASSWORD` | 123456 | MySQL 密码 |
| `MYSQL_DATABASE` | zhaoruoyi | 默认数据库 |

### 自定义配置

如果需要连接到不同的数据库，可以修改配置文件中的环境变量：

```json
{
  "mcpServers": {
    "mysql-prod": {
      "command": "python3",
      "args": ["/path/to/mysql-mcp-server.py"],
      "env": {
        "MYSQL_HOST": "192.168.1.100",
        "MYSQL_PORT": "3306",
        "MYSQL_USER": "myuser",
        "MYSQL_PASSWORD": "mypassword",
        "MYSQL_DATABASE": "production_db"
      }
    }
  }
}
```

## 🔍 测试和调试

### 运行测试
```bash
python3 test-mysql-mcp.py
```

### 手动测试 MCP 服务器
```bash
# 启动服务器
python3 mysql-mcp-server.py

# 发送测试消息（在另一个终端）
echo '{"jsonrpc": "2.0", "id": 1, "method": "tools/list", "params": {}}' | python3 mysql-mcp-server.py
```

### 查看日志
MCP 服务器会将错误信息输出到 stderr，可以通过重定向查看：
```bash
python3 mysql-mcp-server.py 2> mcp-errors.log
```

## 🛡️ 安全注意事项

1. **密码安全**: 不要在生产环境中使用默认密码
2. **网络安全**: 确保 MySQL 服务器的网络访问控制
3. **权限控制**: 为 MCP 服务器创建专门的数据库用户，限制权限
4. **数据备份**: 定期备份重要数据

### 创建专用数据库用户
```sql
-- 创建专用用户
CREATE USER 'mcp_user'@'localhost' IDENTIFIED BY 'secure_password';

-- 授予必要权限
GRANT SELECT, INSERT, UPDATE, DELETE ON zhaoruoyi.* TO 'mcp_user'@'localhost';

-- 刷新权限
FLUSH PRIVILEGES;
```

然后更新配置文件中的用户名和密码。

## 🔧 故障排除

### 连接失败
1. 检查 MySQL 服务是否运行
2. 验证用户名和密码
3. 确认防火墙设置
4. 检查 MySQL 配置文件中的 bind-address

### 权限错误
1. 确保用户有足够的数据库权限
2. 检查表级别的访问权限

### MCP 协议错误
1. 确保 JSON 消息格式正确
2. 检查方法名和参数
3. 查看错误日志

## 📈 性能优化

1. **连接池**: 在高并发场景下考虑使用连接池
2. **查询优化**: 为常用查询添加索引
3. **缓存**: 对频繁查询的结果进行缓存
4. **超时设置**: 合理设置连接和查询超时时间

## 🔄 版本更新

当前版本: 1.0.0

### 更新日志
- v1.0.0: 初始版本，支持基本的 MySQL 操作和 MCP 协议
