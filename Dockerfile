FROM openjdk:17-jre-slim
ARG JAR_FILE=target/channel-leak-reproduction-spring-3-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
