FROM openjdk:17
COPY build/libs/LLMService-0.0.1-SNAPSHOT.jar spring.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=prod", "spring.jar"]