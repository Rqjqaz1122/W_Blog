# W_Blog

##介绍
一个简单的单体Blog博客系统，使用springboot + vue
本项目刚刚写完，并没有对一些问题进行处理，后期慢慢维护

前台地址：[https://www.wrqj.xyz](https://www.wrqj.xyz)


## 软件架构
### 前端
核心框架：Vue2.x、Vue Router、Vuex

Vue 项目基于 @vue/cli4.x 构建

JS 依赖及参考的 css：axios、moment、nprogress、v-viewer、prismjs、APlayer、MetingJS、lodash、mavonEditor、echarts、tocbot、iCSS

### 后台 UI
后台基于 vue-admin-template 二次修改后的 my-vue-admin-template 模板进行开发

UI 框架为 Element UI

### 后端
| 技术              | 说明            |
|-----------------|---------------|
| Spring Boot     | Web应用开发框架     |
| Spring Security | 认证和授权框架       |
| MyBatis         | ORM 框架        |
| PageHelper      | 分页插件          |
| Redis           | NoSQL缓存       |
| commonmark-java | Markdown转HTML |
| ip2region       | 离线IP地址库       |
| quartz          | 定时任务          |
| yauaa           | UserAgent解析   |
| JWT             | JWT登录支持  |
| Hutool          | Java工具类库  |
| Lombok          | Java语言增强库  |


## 安装教程
1.创建MySQL数据库qj_blog，并执行qj_blog.sql脚本文件初始化表数据。
2.修改后端配置信息
3.安装Redis并启动
4.分别在 blog-cms 和 blog-view 目录下执行 npm install 安装依赖
5.分别在 blog-cms 和 blog-view 目录下执行 npm run serve 启动前端

## 注意事项
1.由于时间问题，且是学习阶段所以去掉了评论功能，后面工作以后再进行开发。
2.注意修改application-dev.yml中的MySQL配置和Redis配置
