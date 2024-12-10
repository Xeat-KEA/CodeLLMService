FROM openjdk:17
COPY build/libs/LLMService-0.0.1-SNAPSHOT.jar spring.jar
ENV TZ Asia/Seoul
ENTRYPOINT ["java", "-Dnetworkaddress.cache.ttl=0", "-Dnetworkaddress.cache.negative.ttl=0", "-Duser.timezone=Asia/Seoul", "-jar", "spring.jar"]