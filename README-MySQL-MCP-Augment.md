# MySQL MCP for Augment

🚀 **一个专为 Augment 设计的 MySQL MCP 服务器，让 AI 助手能够直接与您的 MySQL 数据库交互！**

## ✨ 特性

- 🔌 **即插即用**: 简单配置即可在 Augment 中使用
- 🛡️ **安全可靠**: 支持用户认证和权限控制
- 📊 **功能完整**: 支持查询、插入、更新、删除等所有 SQL 操作
- 🧪 **经过测试**: 包含完整的测试套件，确保稳定性
- 📝 **详细文档**: 提供完整的使用说明和示例

## 🎯 测试结果

```
=== MySQL MCP 服务器测试 ===
✓ MCP 服务器已启动
✓ 初始化成功
✓ 获取工具列表成功
✓ 显示数据库成功
✓ 显示表成功
✓ 描述表结构成功
✓ 查询成功

=== 测试结果 ===
通过: 6/6
成功率: 100.0%
🎉 所有测试通过！MCP 服务器工作正常。
```

## 📁 文件结构

```
├── mysql-mcp-server.py          # MCP 服务器主程序
├── augment-mysql-mcp.json       # Augment 配置文件 ⭐
├── mysql-mcp-server.json        # 完整配置文件
├── test-mysql-mcp.py            # 测试脚本
└── MySQL-MCP-Augment-使用说明.md # 详细使用说明
```

## 🚀 快速开始

### 1. 安装依赖
```bash
pip3 install mysql-connector-python
```

### 2. 配置 Augment
将以下配置添加到您的 Augment 设置中：

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

### 4. 在 Augment 中使用
现在您可以在 Augment 中直接询问数据库相关问题：

- "请查询所有用户数据"
- "显示用户表的结构"
- "创建一个新的测试用户"
- "统计用户总数"

## 🛠️ 支持的操作

| 工具名称 | 功能描述 | 示例 |
|---------|----------|------|
| `mysql_query` | 执行任意 SQL 语句 | `SELECT * FROM user` |
| `mysql_show_databases` | 显示所有数据库 | 自动执行 |
| `mysql_show_tables` | 显示数据库中的表 | 指定数据库名 |
| `mysql_describe_table` | 查看表结构 | 指定表名 |

## 🔧 配置说明

### 默认配置
- **主机**: localhost
- **端口**: 3306  
- **用户**: root
- **密码**: 123456
- **数据库**: zhaoruoyi

### 自定义配置
修改 `augment-mysql-mcp.json` 中的环境变量即可连接到不同的数据库。

## 📊 使用示例

### 在 Augment 中的对话示例

**用户**: "请查询用户表中的所有数据"

**Augment**: 我来为您查询用户表的数据...
```sql
SELECT * FROM user
```
查询结果显示当前用户表为空，包含以下字段：
- id (bigint): 主键
- username (varchar): 用户名  
- age (int): 年龄

**用户**: "请创建一个测试用户"

**Augment**: 我来为您创建一个测试用户...
```sql
INSERT INTO user (username, age) VALUES ('test_user', 25)
```
测试用户创建成功！

## 🛡️ 安全建议

1. **生产环境**: 请更改默认密码
2. **权限控制**: 创建专用数据库用户
3. **网络安全**: 配置防火墙规则
4. **数据备份**: 定期备份重要数据

## 🔍 故障排除

### 常见问题

**Q: 连接失败怎么办？**
A: 检查 MySQL 服务是否运行，用户名密码是否正确

**Q: 权限错误？**  
A: 确保数据库用户有足够的操作权限

**Q: Augment 无法识别 MCP 服务器？**
A: 检查配置文件路径是否正确，Python 环境是否可用

### 测试命令
```bash
# 测试 MySQL 连接
python3 mysql-mcp-tool.py test-connection

# 测试 MCP 服务器
python3 test-mysql-mcp.py
```

## 📚 更多信息

- 📖 [详细使用说明](MySQL-MCP-Augment-使用说明.md)
- 🔧 [MySQL 工具说明](MySQL-MCP-使用说明.md)
- ⚙️ [配置文件示例](mysql-mcp-server.json)

## 🎉 开始使用

现在您已经拥有了一个功能完整的 MySQL MCP 服务器！

1. ✅ 配置文件已准备就绪
2. ✅ 服务器已通过全部测试  
3. ✅ 文档说明已完善
4. ✅ 可以在 Augment 中直接使用

享受与数据库的智能交互吧！🚀
