# Spring Cloud Demo Project

这是一个基于Spring Cloud的微服务示例项目，包含以下组件：

1. Eureka Server (服务注册中心) - 端口：8761
2. Config Server (配置中心) - 端口：8888
3. Gateway Service (网关服务) - 端口：8080
4. Service Provider (服务提供者) - 端口：8081

## 项目结构

```
springcloud-demo/
├── eureka-server/        # 服务注册中心
├── config-server/        # 配置中心
├── gateway-service/      # 网关服务
└── service-provider/     # 服务提供者
```

## 运行说明

1. 首先启动Eureka Server：
```bash
cd eureka-server
mvn spring-boot:run
```

2. 启动Config Server：
```bash
cd config-server
mvn spring-boot:run
```

3. 启动Service Provider：
```bash
cd service-provider
mvn spring-boot:run
```

4. 启动Gateway Service：
```bash
cd gateway-service
mvn spring-boot:run
```

## 测试服务

1. 访问Eureka控制台：http://localhost:8761
2. 通过网关访问服务：http://localhost:8080/api/hello

## 注意事项

- 确保所有服务都已正确启动
- 检查各个服务的配置文件中的端口号是否正确
- 确保网络连接正常，各个服务之间可以互相访问 