package com.xeat.llmservice.Entity;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

// LLMRedisEntity는 Redis에 저장될 데이터의 형식을 정의한 클래스
// 비관계형 데이터베이스 이므로 Entity 어노테이션을 사용하지 않음
@RedisHash("LLMCache")
public class LLMRedisCacheEntity implements Serializable {
    private String chatId;    // 메시지 ID (고유 식별자)
    private String userId;       // 사용자 ID
    private String message;      // 실제 메시지 내용
    private String answer;       // 시스템이 생성한 답변

    // 필요할 경우 벡터 데이터도 포함 가능
    private double[] vector;     // 벡터화된 메시지
}
