FROM adoptopenjdk/openjdk11
CMD ["./gradlew", "bootJar"]
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} ProductService.jar
ENTRYPOINT ["java", "-jar", "ProductService.jar"]