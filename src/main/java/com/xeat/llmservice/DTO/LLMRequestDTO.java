package com.xeat.llmservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
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
    public static class chatMessage {
        private String chatMessage;
        private Integer userId;
        private Integer codeHistoryId;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Schema(name = "codeGeneratingInfo", title = "codeGeneratingInfo [코딩테스트 생성 정보]", description = "코딩테스트 생성 정보")
    public static class codeGeneratingInfo {
        @Schema(name = "algorithm", description = "코딩테스트 알고리즘 유형", example = "DP")
        private String algorithm;
        @Schema(name = "difficulty", description = "코딩테스트 난이도", example = "3")
        private Integer difficulty;
        @Schema(name = "etc", description = "코딩테스트 제작 추가 사항", example = "문제의 조건을 만족하는 최소의 시간 복잡도를 갖는 알고리즘을 작성해줘.(can be null)")
        private String etc;
    }


}
