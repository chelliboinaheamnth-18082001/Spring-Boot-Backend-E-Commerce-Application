#!/bin/bash

# Build all services using mvnw
cd SpringCloud_EurekaServer && ./mvnw clean package -DskipTests && cd ..
cd E-Com_ApiGateWay && ./mvnw clean package -DskipTests && cd ..
cd SpringCloudConfigServer/SpringCloud_ConfigServer && ./mvnw clean package -DskipTests && cd ../..
cd Notification-Service && ./mvnw clean package -DskipTests && cd ..
cd ProductService/Product_Service && ./mvnw clean package -DskipTests && cd ../..
cd UserService/User_Service && ./mvnw clean package -DskipTests && cd ../..
cd CartAndOrderService/Cart_Order_Service && ./mvnw clean package -DskipTests && cd ../..
