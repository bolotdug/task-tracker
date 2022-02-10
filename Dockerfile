FROM adoptopenjdk/openjdk11:alpine-jre

COPY target/task-tracker-0.0.1-SNAPSHOT.jar /task-tracker-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "task-tracker-0.0.1-SNAPSHOT.jar"]
