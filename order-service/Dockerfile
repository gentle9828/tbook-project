FROM openjdk:21-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/order-1.0.jar OrderService.jar
ENTRYPOINT ["java", "-jar", "OrderService.jar"]