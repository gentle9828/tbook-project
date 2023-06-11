FROM adoptopenjdk/openjdk11
CMD ["./gradlew", "clean", "build"]
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} OrderService.jar
ENTRYPOINT ["java", "-jar", "OrderService.jar"]