FROM openjdk:17
COPY build/libs/LLMService-0.0.1-SNAPSHOT.jar spring.jar
ENV TZ Asia/Seoul
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "spring.jar"]