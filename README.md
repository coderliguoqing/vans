# vans V1.0.0
vans 基于SpringBoot构建的后端开发脚手架项目，完美整合springmvc+mybatis+spring security+jwt token。项目代码简介，注释清楚，容易上手，针对用户、角色、权限、字典等模块已经开发完成。项目适用于前段后端开发的系统，可以作为脚手架，直接拿来使用！

# 功能简介
1、用户管理
2、角色管理
3、菜单管理
4、字典管理
5、定时任务管理（可动态添加、修改、运行、停止任务，日志监控）
6、代码自动生成（mapper,entity,service）

# 代码自动生成使用说明
由于功能比较简单，就不做成页面功能了。vans-admin-web 模块和依赖的 vans-system-provider 服务启动后，直接在浏览器里运行一下地址即可：
http://localhost:8088/sys/generator/code?tables=sys_operation_log&package=cn.com.guoqing.vans.system&author=Guoqing&email=514471352@qq.com
其中，tables 为表名，可用逗号隔开一次生成多个，package为包名的前部分，author、email为类注解上的信息

# 所用框架
# 前端
1、vue.js
2、element ui
3、vue-router
4、admin lte
# 后端
1、SpringBoot 1.5.4.RELEASE
2、MyBatis
3、Spring
4、SpringSecurity
5、Fastjson
6、Durid
7、Hibernate-validator
8、jwt token
9、Maven
10、Redis

# 效果图

