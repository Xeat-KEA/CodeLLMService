//package com.xeat.llmservice.RedisVectorDB;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class KafkaToRedisService {
//    private final ObjectMapper objectMapper;
//
//    @KafkaListener(topics = "xeat_test", groupId = "redis-sync-group")
//    public void consume(String message) throws Exception{
//        JsonNode payload = objectMapper.readTree(message).get("payload");
//        String question = payload.get("question").asText();
//        String answer = payload.get("answer").asText();
//        String userId = payload.get("userId").asText();
//
//    }
//}
