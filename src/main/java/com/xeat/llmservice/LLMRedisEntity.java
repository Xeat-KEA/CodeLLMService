package com.xeat.llmservice;


// LLMRedisEntity는 Redis에 저장될 데이터의 형식을 정의한 클래스
// 비관계형 데이터베이스 이므로 Entity 어노테이션을 사용하지 않음
public class LLMRedisEntity {
    private String messageId;    // 메시지 ID (고유 식별자)
    private String sessionId;    // 대화 세션 ID
    private String userId;       // 사용자 ID
    private String message;      // 실제 메시지 내용
    private long timestamp;      // 메시지 시간 (UNIX 타임스탬프)
    private String intent;       // 메시지의 의도 (예: "주문", "예약", "문의" 등)
    private String answer;       // 시스템이 생성한 답변

    // 필요할 경우 벡터 데이터도 포함 가능
    private double[] vector;     // 벡터화된 메시지
}
