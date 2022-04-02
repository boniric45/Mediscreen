FROM openjdk:11-jre-slim
EXPOSE 9000
ARG JAR_FILE=target/front-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} front.jar
ENTRYPOINT ["java","-jar","front.jar"]

