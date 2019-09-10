# customer-service

客服IM，基于t-io

前端项目：

[https://github.com/weifuchuan/customer-service-webapp](https://github.com/weifuchuan/customer-service-webapp)

~~临时预览：~~(么得云服务器啦)

~~客户端~~：[http://123.207.28.107:7776/cs/customer](http://123.207.28.107:7776/cs/customer)

~~客服端~~：[http://123.207.28.107:7776/cs/waiter](http://123.207.28.107:7776/cs/waiter)

## 构建&运行

运行命令：`mvn clean package`

构建后，在target/customer-service-*文件夹中，修改`config/config.properties`配置文件，1）windows：运行start.bat；2）linux：`./server.sh start`或`./server.sh restart`或`./server.sh stop`。

## 项目结构

- src/main
    - java/com/fuchuan/customerservice Java源代码
        - common ImPacket定义、GRPC生成的代码、自动生成的Model类
            - model 用于db的Model，由_ModelGenerator自动生成，定义文件位于resources/model
            - payload 用于ImPacket的Payload，由_PayloadModelGenerator自动生成，定义文件位于resources/payload-model
        - db 数据库
        - kit 工具包
        - mock 开发、测试用的GRPC服务器、HTTP服务器
        - server 核心IM服务器
            - config ImServer的config类定义
            - handler ImServer用的处理器
                - actual 实际使用于各Command的handler
                - MainHandler.java
                - ... ...
            - listener ImServer用的listener
            - websocket 修改自t-io websocket的websocket实现，为了支持fin；待t-io websocket支持后删除
            - ImServer.java 
            - ServerModule.java
        - App.java
        - CustomerServiceModule.java
    - proto ProtoBuf定义文件
    - proto-ts TypeScript使用
    - resources 配置文件和一些有用的东西

## some point

- 添加处理新的Command的handler：在`com.fuchuan.customerservice.server.handler.actual`中添加类，继承BaseActualHandler类，并注解@HandlerForCommand(Command.XXX)，会自动注册到server
- DI容器：[feather](https://github.com/zsoltherpai/feather)
- 使用GRPC获取业务信息
- 默认用Redis数据库存储
- ... ...

## 关于GRPC

1. For Java：`mvn protobuf:compile && mvn protobuf:compile-custom`
2. For TypeScript：在src/main/proto-ts中，`npm install`或`yarn`安装node依赖，运行`./gen-ts.sh`。（项目中通过windows10的linux子系统运行之。）