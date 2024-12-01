package com.xeat.llmservice.DTO;

import com.xeat.llmservice.Entity.LLMEntity;
import com.xeat.llmservice.Entity.LLMHistoryEntity;
import com.xeat.llmservice.Global.Enum.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "LLMRequestDTO", description = "LLM 요청 DTO")
public class LLMRequestDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(name = "chatMessage", title = "chatMessage [질의 응답 요청 정보]", description = "질의 응답 요청 정보")
    public static class chatMessage {
        @Schema(name = "chatMessage", description = "채팅 메시지", example = "파이썬에서 출력을 담당하는 함수가 뭐지?")
        private String chatMessage;
        @Schema(name = "codeHistoryId", description = "코드 히스토리 ID", example = "1")
        private Integer codeHistoryId;
        @Schema(name = "codeLanguage", description = "코드 언어", example = "PYTHON")
        private String codeLanguage;
        @Schema(name = "isNotReqCodeGen", description = "코드 생성을 원하는 질문인지 확인", example = "false")
        private Boolean isNotReqCodeGen;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(name = "codeGeneratingInfo", title = "codeGeneratingInfo [코딩테스트 생성 정보]", description = "코딩테스트 생성 정보")
    public static class codeGeneratingInfo {
        @Schema(name = "algorithm", description = "코딩테스트 알고리즘 유형", example = "DP")
        private String algorithm;
        @Schema(name = "difficulty", description = "코딩테스트 난이도", example = "LEVEL3")
        private Difficulty difficulty;
        @Schema(name = "etc", description = "코딩테스트 제작 추가 사항", example = "문제의 조건을 만족하는 최소의 시간 복잡도를 갖는 알고리즘을 작성해줘.(can be null)")
        private String etc;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class LLMDTO {
        private String userId;
        private Integer codeHistoryId;

        public static LLMEntity toEntity(LLMDTO llmDTO) {
            return LLMEntity.builder()
                    .userId(llmDTO.getUserId())
                    .codeHistoryId(llmDTO.getCodeHistoryId())
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class LLMHistoryDTO {
        private Integer chatHistoryId;
        private LLMEntity llmEntity;
        private String question;
        private String answer;

        public static LLMHistoryEntity toEntity(LLMHistoryDTO llmHistoryDTO) {
            return LLMHistoryEntity.builder()
                    .chatHistoryId(llmHistoryDTO.getChatHistoryId())
                    .llmEntity(llmHistoryDTO.getLlmEntity())
                    .question(llmHistoryDTO.getQuestion())
                    .answer(llmHistoryDTO.getAnswer())
                    .build();
        }

    }






}
