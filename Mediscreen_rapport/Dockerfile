FROM openjdk:11-jre-slim
EXPOSE 8080
ARG JAR_FILE=target/rapport-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} rapport.jar
ENTRYPOINT ["java","-jar","rapport.jar"]


