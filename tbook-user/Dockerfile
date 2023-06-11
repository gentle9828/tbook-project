FROM openjdk:21-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/tbooke-user-1.0.jar UserService.jar
ENTRYPOINT ["java", "-jar", "UserService.jar"]