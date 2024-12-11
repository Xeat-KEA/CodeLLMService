package com.xeat.llmservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ClientResponseDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CodeBankResponseDTO {
        private Long codeHistoryId;
        private Long codeId;

        public static CodeBankResponseDTO toEntity(CodeBankResponseDTO codeBankResponseDTO) {
            return CodeBankResponseDTO.builder()
                    .codeHistoryId(codeBankResponseDTO.getCodeHistoryId())
                    .codeId(codeBankResponseDTO.getCodeId())
                    .build();
        }
    }
}
