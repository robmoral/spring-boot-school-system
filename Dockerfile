FROM openjdk:8-jdk-alpine
VOLUME /my-app
COPY target/my-app-1.0.jar my-app-1.0.jar
ENTRYPOINT ["java","-jar","/my-app-1.0.jar"]