FROM openjdk:21-jdk
LABEL authors="silif"
WORKDIR /app
COPY /out/artifacts/TaskManagement_jar/*.jar /app/
ENTRYPOINT ["java", "-jar", "TaskManagement.jar"]