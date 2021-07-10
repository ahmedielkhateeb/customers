FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ARG DB_FILE=sample.db
COPY ${DB_FILE} sample.db
ENTRYPOINT ["java","-jar","/app.jar"]