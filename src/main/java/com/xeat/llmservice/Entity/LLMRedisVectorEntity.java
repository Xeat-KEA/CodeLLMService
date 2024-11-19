package com.xeat.llmservice.Entity;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

// LLMRedisEntity는 Redis에 저장될 데이터의 형식을 정의한 클래스
// 비관계형 데이터베이스 이므로 Entity 어노테이션을 사용하지 않음
@RedisHash("QuestionVector")
public class LLMRedisVectorEntity implements Serializable {
    @Id
    private String chatId;          // 메시지 ID (고유 식별자)
    private double[] vector;     // 질문의 벡터 데이터
    private String questionText;    // 질문 텍스트
    private String answerText;      // 답변 텍스트
}
