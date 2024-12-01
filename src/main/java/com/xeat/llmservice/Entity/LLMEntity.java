package com.xeat.llmservice.Entity;

import com.xeat.llmservice.Global.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LLMEntity extends BaseTimeEntity {
    // LLMEntity는 MySQL(SourceDB)에 저장될 데이터의 형식을 정의한 클래스
    // 채팅방 번호를 매기는 개념으로 생각하면 된다.
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer chatId;             // 채팅 ID
    // 채팅 아이디는 사용자 코딩 테스트 내역과 동일하다고 생각하면 된다.
    private Integer codeHistoryId;      // 코딩 테스트 내역 ID
    private String userId;             // 사용자 ID
//    private Integer codeId;             // 코딩 테스트 문제 ID, 사용자 코딩 테스트 내영으로 불러올 수 있음.
}
