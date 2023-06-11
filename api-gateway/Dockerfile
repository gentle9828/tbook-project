FROM openjdk:21-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/apigateway-1.0.jar ApiGateway.jar
ENTRYPOINT ["java", "-jar", "ApiGateway.jar"]