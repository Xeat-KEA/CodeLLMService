package com.xeat.llmservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LLMRequestDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class chatMessage {
        private String chatMessage;
        private Integer userId;
        private Integer codeHistoryId;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class codeGeneratingInfo {
        private String algorithm;
        private Integer difficulty;
        private String etc;
    }


}
