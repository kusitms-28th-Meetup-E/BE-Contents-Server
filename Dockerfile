FROM openjdk:17
COPY ./build/libs/contents-0.0.1-SNAPSHOT.jar contents.jar
ENTRYPOINT ["java", "-jar", "contents.jar"]