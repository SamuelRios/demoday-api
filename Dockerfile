# syntax = docker/dockerfile:1.2

FROM ubuntu:latest AS build

RUN apt-get update && apt-get install -y openjdk-17-jdk

COPY pom.xml .

COPY . .

RUN apt-get install -y maven

RUN RUN --mount=type=secret,id=_env,dst=/etc/secrets/.env  mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/demoday-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]