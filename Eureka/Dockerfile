FROM openjdk:21-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/Eureka-1.0.jar EurekaServer.jar
ENTRYPOINT ["java", "-jar", "EurekaServer.jar"]