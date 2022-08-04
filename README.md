<div align="center">
<br/>

  <h1 align="center">
    Pear Admin Boot
  </h1>
  <h4 align="center">
    开 箱 即 用 的 Spring 快 速 开 发 平 台
  </h4> 

  [预 览](http://boot.pearadmin.com)   |   [官 网](http://www.pearadmin.com/)   |   [社区](http://forum.pearadmin.com/)

</div>

<p align="center">
    <a href="#">
        <img src="https://img.shields.io/badge/Pear Admin Layui-3.1.0+-green.svg" alt="Pear Admin Layui Version">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/Spring Boot-2.3.0+-green.svg" alt="Jquery Version">
    </a>
      <a href="#">
        <img src="https://img.shields.io/badge/Layui-2.5.6+-green.svg" alt="Layui Version">
    </a>
</p>

<div align="center">
  <img  width="92%" style="border-radius:10px;margin-top:20px;margin-bottom:20px;box-shadow: 2px 0 6px gray;" src="https://images.gitee.com/uploads/images/2020/1019/104805_042b888c_4835367.png" />
</div>

### 项目介绍

Spring Boot + Security + MyBatis Plus 快速开发平台

### 内置功能

1. 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2. 权限管理：配置系统菜单，操作权限，按钮权限, 数据权限标识等。
3. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
4. 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
5. 参数管理：对系统动态配置常用参数。
6. 通知公告：系统通知公告信息发布维护。
7. 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
8. 登录日志：系统登录日志记录查询包含登录异常。
9. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
10. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
11. 系统接口：根据业务代码自动生成相关的api接口文档。
12. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
13. 表单构建：拖动表单元素生成相应的HTML代码。
14. 数据监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。
15. 租户管理：加入多租户架构, 使用逻辑隔离租户数据。
16. 接口限流：新增 @RateLimit 注解, 轻量级限流方案。
17. 导出导出：集成 easy-excel, 提供声明式报表导出。

### 项目结构

```
Pear Admin Boot
│
├─sql SQL 脚本
│
├─src 公共模块
│  │
│  └─main 
│      │
│      ├─java 源码文件
│      │   │
│      │   ├─common 公共代码
│      │   │   │
│      │   │   ├─aop 切面逻辑
│      │   │   │
│      │   │   ├─cache 缓存服务
│      │   │   │
│      │   │   ├─configure 集成配置
│      │   │   │
│      │   │   ├─constant 静态常量
│      │   │   │
│      │   │   ├─context 核心服务
│      │   │   │
│      │   │   ├─quartz 定时任务
│      │   │   │
│      │   │   ├─secure 安全实现
│      │   │   │
│      │   │   ├─tools 工具包
│      │   │   │
│      │   │   └─web 核心封装
│      │   │   
│      │   ├─modules 业务代码
│      │   │   │
│      │   │   ├─job 定时任务
│      │   │   │      │
│      │   │   │      ├─domain 实体
│      │   │   │      │
│      │   │   │      ├─params 参数
│      │   │   │      │
│      │   │   │      ├─repository ORM 操作
│      │   │   │      │
│      │   │   │      ├─rest 接口
│      │   │   │      │
│      │   │   │      └─service 服务
│      │   │   │        
│      │   │   │
│      │   │   └─sys 基础功能
│      │   │   
│      │   └─EntranceApplication 启动类
│      │   
│      └─resource 资源文件
│  
└─pom.xml  Maven 配置

```

### 预览项目

| |  |
|-------------------|---------------------|
| ![](readme/1.jpg) | ![](readme/2.jpg)  |
| ![](readme/3.jpg) | ![](readme/4.jpg)  |
| ![](readme/5.jpg) | ![](readme/6.jpg)  |
| ![](readme/7.jpg) | ![](readme/9.jpg)  |
| ![](readme/10.jpg)| ![](readme/11.jpg) |
| ![](readme/12.jpg)| ![](readme/13.jpg) |
| ![](readme/14.jpg)| ![](readme/15.jpg) |
| ![](readme/16.jpg)| ![](readme/17.jpg) |
| ![](readme/18.jpg)| ![](readme/19.jpg) |

### 开源共建

<p style="padding:10px;"  width="90%">

1. 欢迎提交 [pull request](https://gitee.com/pear-admin/Pear-Admin-Boot/pulls)，注意对应提交对应 `master` 分支

2. 欢迎提交 [issue](https://gitee.com/pear-admin/Pear-Admin-Boot/issues)，请写清楚遇到问题的原因、开发环境、复显步骤。

</p>

感谢每一位贡献代码的朋友。

如果对您有帮助，您可以点右上角 💘Star💘 支持