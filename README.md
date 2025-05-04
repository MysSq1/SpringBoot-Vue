# 商城系统 (Shopping System)

基于 Spring Boot 和 Vue.js 的全栈应用程序，提供商品购物功能。

## 项目结构

项目由两个主要部分组成：

- `news` - 前端 Vue.js 应用
- `news-server` - 后端 Spring Boot 应用

## 技术栈

### 前端 (Frontend)

- **框架**: Vue.js 2.6
- **UI 库**: Element UI
- **状态管理**: Vuex
- **路由**: Vue Router
- **HTTP 客户端**: Axios
- **富文本编辑器**: wangEditor
- **数据可视化**: ECharts

### 后端 (Backend)

- **框架**: Spring Boot 2.7.16
- **ORM**: MyBatis Plus 3.5.2
- **数据库**: MySQL 8.0
- **连接池**: Druid 1.2.4
- **认证**: JWT (java-jwt 3.10.3)
- **工具库**: Hutool 5.7.20
- **开发工具**: Lombok

## 功能特性

- 用户认证与授权
- 商品管理（添加、删除、更新、查询）
- 商品分类管理
- 购物车功能
- 订单管理
- 数据统计与可视化
- 文件上传与管理
- 角色权限管理

## 安装与运行

### 前端

```bash
# 进入前端目录
cd news

# 安装依赖
npm install

# 启动开发服务器
npm run serve

# 构建生产环境
npm run build
```

### 后端

```bash
# 进入后端目录
cd news-server

# 使用 Maven 构建项目
mvn clean package

# 运行 JAR 文件
java -jar target/news-server-0.0.1-SNAPSHOT.jar
```

## 配置

### 数据库配置

在 `news-server/src/main/resources/application.properties` 或 `application.yml` 中配置数据库连接信息。

### 前端 API 配置

在前端项目中，API 基础 URL 可以在 `news/src/untils` 目录下的配置文件中修改。

