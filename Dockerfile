FROM openjdk:11-jre
COPY build/libs/LLMService-0.0.1-SNAPSHOT.jar spring.jar
EXPOSE 8080
ENTRYPOINT exec java -jar -Duser.timezone=Asia/Seoul spring.jar