FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=app-api/target/app-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM maven:3.6.0-jdk-11-slim AS build
#COPY . /tmp
#WORKDIR /tmp
#RUN ["mvn", "package", "-P LOCAL"]
#WORKDIR /tmp/app-api
#RUN ls


#
# Build stage
#
#FROM maven:3.6.0-jdk-11-slim AS builder
#RUN mvn -f /tmp/pom.xml package -P LOCAL

#
# Build stage
#
#FROM node:11 AS build
#COPY . /tmp
#WORKDIR /tmp/app-client
#RUN ["npm", "install"]

#
# Package stage
#
#FROM openjdk:11-jre-slim
#COPY --from=build2 /tmp/demo-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]