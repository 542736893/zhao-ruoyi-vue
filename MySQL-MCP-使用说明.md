# MySQL MCP 配置使用说明

这是一个专为 Agent 设计的 MySQL MCP (Model Context Protocol) 配置，允许 AI 助手直接访问本地 zhaoruoyi 数据库。

## 📁 文件说明

| 文件名 | 描述 |
|--------|------|
| `mysql-mcp-config.json` | 完整的 MCP 配置文件（包含详细信息和使用示例） |
| `augment-mysql-mcp.json` | 简化的 Augment 专用配置文件 |
| `test-mysql-mcp.py` | 测试脚本，验证 MCP 工具是否正常工作 |
| `/Users/zhaozb/Documents/Document/mcp/mysql/mysql-mcp-tool.py` | MySQL MCP 工具主程序 |

## 🚀 快速配置

### 1. 确保依赖已安装

```bash
pip3 install mysql-connector-python
```

### 2. 在 Augment 中配置

将 `augment-mysql-mcp.json` 的内容添加到您的 Augment 配置中：

```json
{
  "mcpServers": {
    "mysql": {
      "command": "python3",
      "args": [
        "/Users/zhaozb/Documents/Document/mcp/mysql/mysql-mcp-tool.py"
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

### 3. 验证配置

运行测试脚本确认一切正常：

```bash
cd /Users/zhaozb/Documents/Code/zhao-ruoyi-vue
python3 test-mysql-mcp.py
```

## ✅ 测试结果

```
=== MySQL MCP 工具测试 ===
✓ MySQL MCP 工具已启动
✓ 数据库连接成功 - 找到 7 个数据库
✓ 查询用户表成功 - 用户总数: 1

=== 测试结果 ===
通过: 2/2
成功率: 100.0%
🎉 所有测试通过！MySQL MCP 工具工作正常。
```

## 🛠️ 可用工具

配置完成后，Agent 可以使用以下工具与 zhaoruoyi 数据库交互：

### 1. `mysql_query`
执行任意 MySQL 查询语句

**示例:**
- `SELECT * FROM user LIMIT 10`
- `INSERT INTO user (username, age) VALUES ('test', 25)`
- `UPDATE user SET age = 26 WHERE username = 'test'`
- `DELETE FROM user WHERE username = 'test'`

### 2. `mysql_show_databases`
显示所有数据库

### 3. `mysql_show_tables`
显示数据库中的所有表

### 4. `mysql_describe_table`
显示表结构信息

### 5. `mysql_table_info`
获取表的详细统计信息

## 🎯 在 Agent 中使用

配置完成后，您可以直接与 Agent 对话：

### 数据查询
- "请查询 zhaoruoyi 数据库中的所有用户"
- "显示用户表的结构"
- "统计用户总数"

### 数据操作
- "创建一个新的测试用户"
- "更新用户信息"
- "删除指定用户"

### 数据库管理
- "显示所有数据库"
- "显示 zhaoruoyi 数据库中的所有表"
- "查看 user 表的详细信息"

## 📊 数据库信息

**连接配置:**
- 主机: localhost
- 端口: 3306
- 数据库: zhaoruoyi
- 用户: root
- 密码: 123456

**当前表结构:**
- **user 表**: 用户信息表
  - `id` (bigint) - 用户ID，主键，自增
  - `username` (varchar(50)) - 用户名，非空，唯一
  - `age` (int) - 年龄，可空

## 🛡️ 安全注意事项

1. **密码安全**: 当前使用的是开发环境密码，生产环境请使用更安全的密码
2. **权限控制**: 建议为 MCP 工具创建专门的数据库用户，限制权限
3. **数据备份**: 执行修改操作前请确保数据已备份
4. **网络安全**: 确保 MySQL 服务器的网络访问控制

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

## 📈 使用示例

### 查询示例
```sql
-- 查询所有用户
SELECT * FROM user;

-- 统计用户数量
SELECT COUNT(*) as total FROM user;

-- 按年龄查询
SELECT * FROM user WHERE age > 18;
```

### 操作示例
```sql
-- 添加用户
INSERT INTO user (username, age) VALUES ('newuser', 25);

-- 更新用户
UPDATE user SET age = 26 WHERE username = 'newuser';

-- 删除用户
DELETE FROM user WHERE username = 'newuser';
```

## 🎉 开始使用

现在您已经拥有了一个功能完整的 MySQL MCP 配置！

1. ✅ 配置文件已准备就绪
2. ✅ MCP 工具已通过测试
3. ✅ 数据库连接正常
4. ✅ 可以在 Agent 中直接使用

享受与数据库的智能交互吧！🚀
