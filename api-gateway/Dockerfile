FROM adoptopenjdk/openjdk11
CMD ["./gradlew", "bootJar"]
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} ApiGateway.jar
ENTRYPOINT ["java", "-jar", "ApiGateway.jar"]