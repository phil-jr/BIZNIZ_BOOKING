FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=target/app-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM adoptopenjdk/maven-openjdk11 AS build
#COPY . /tmp
#WORKDIR /tmp
#RUN ["mvn", "package", "-P LOCAL"]
#WORKDIR /tmp/app-api
#RUN ls