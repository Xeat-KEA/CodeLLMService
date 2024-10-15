package com.xeat.llmservice;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class LLMEntity {
    @Id
    private Long id;

    private String sessionId;  // 대화 세션 ID
    private String userId;     // 사용자 ID
    private String message;    // 실제 메시지 내용
    private LocalDateTime timestamp;  // 메시지가 기록된 시간

    // 관계형 데이터베이스의 장점을 활용하여 다른 테이블과 관계를 설정할 수도 있음
    // 예: @ManyToOne 관계로 User 테이블과 연관

}
