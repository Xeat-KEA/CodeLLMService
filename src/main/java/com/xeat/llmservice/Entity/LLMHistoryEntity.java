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
public class LLMHistoryEntity {
    // LLMHistoryEntity는 MySQL(SourceDB)에 저장될 데이터의 형식을 정의한 클래스
    // 각 채팅방 별로 대화 내용을 저장하는 개념으로 생각하면 된다.
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer chatHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatId")
    private LLMEntity llmEntity;

    private String chatMessage;
    private String chatAnswer;

}
