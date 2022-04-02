FROM openjdk:11-jre-slim
EXPOSE 8082
ARG JAR_FILE=target/notes-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} notes.jar
ENTRYPOINT ["java","-jar","notes.jar"]