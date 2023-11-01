#FROM openjdk:17
FROM amazoncorretto:17
LABEL authors="Grzegorz Jewusiak"
COPY target/todo_list-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]