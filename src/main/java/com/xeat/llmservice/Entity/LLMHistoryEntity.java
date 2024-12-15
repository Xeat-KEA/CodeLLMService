package com.xeat.llmservice.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "llm_history")
public class LLMHistoryEntity {
    // LLMHistoryEntity는 MySQL(SourceDB)에 저장될 데이터의 형식을 정의한 클래스
    // 각 채팅방 별로 대화 내용을 저장하는 개념으로 생각하면 된다.
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long chatHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatId")
    private LLMEntity llmEntity;

    @Lob
    private String question;
    @Lob
    private String answer;

}
