# syntax = docker/dockerfile:1.2

FROM ubuntu:latest AS build

RUN apt-get update && apt-get install -y openjdk-17-jdk

COPY pom.xml .

COPY . .

RUN mkdir -p /tmp/secrets

RUN --mount=type=secret,id=firebasetoken_json,dst=/etc/secrets/firebasetoken.json cat /etc/secrets/firebasetoken.json > /tmp/secrets/firebasetoken.json

RUN cat /tmp/secrets/firebasetoken.json

RUN cat /src/main/resources/application.properties

RUN cp /tmp/secrets/firebasetoken.json /src/main/resources/tokens/

RUN cat /src/main/resources/tokens/firebasetoken.json

RUN apt-get install -y maven

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/demoday-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]