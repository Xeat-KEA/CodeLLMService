package com.xeat.llmservice.DTO;

import com.xeat.llmservice.Entity.LLMEntity;
import com.xeat.llmservice.Entity.LLMHistoryEntity;
import com.xeat.llmservice.Global.Enum.Difficulty;
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
    @Schema(name = "chatMessage", title = "chatMessage [질의 응답 요청 정보]", description = "질의 응답 요청 정보")
    public static class chatMessage {
        @Schema(name = "isIncludingAnswer", description = "답변에 정답 포함 여부", example = "true")
        private boolean isIncludingAnswer;
        @Schema(name = "chatMessage", description = "채팅 메시지", example = "파이썬에서 출력을 담당하는 함수가 뭐지?")
        private String chatMessage;
        @Schema(name = "codeHistoryId", description = "코드 히스토리 ID", example = "1")
        private Long codeHistoryId;
        @Schema(name = "codeLanguage", description = "코드 언어", example = "PYTHON")
        private String codeLanguage;
        @Schema(name = "codingTestContent", description = "코딩테스트 문제 내용", example = "\"<h3>문제 설명:</h3>\\n<p>당신은 '1'과 '0'으로 이루어진 2D 이진 행렬에서 가장 큰 정사각형으로 '1'로 이루어진 배열을 찾아야 합니다. 이때, 가장 큰 정사각형 배열의 면적을 반환합니다.</p>\\n\\n<h3>문제 제한 사항:</h3>\\n<ul>\\n<li>행렬의 크기는 MxN이며, M과 N은 최대 300입니다.</li>\\n</ul>\\n\\n<h3>입력 예시:</h3>\\n<pre><code>4 5\\n1 0 1 0 0\\n1 0 1 1 1\\n1 1 1 1 1\\n1 0 0 1 0\\n</code></pre>\\n\\n<h3>출력 예시:</h3>\\n<pre><code>4\\n</code></pre>\\n\\n<h3>입출력 예 설명:</h3>\\n<p>입력 행렬에서 가장 큰 정사각형의 크기는 2x2이며, 면적은 4입니다.</p>\\n\\n<h3>문제 정보:</h3>\\n<ul>\\n<li>알고리즘: 동적 프로그래밍(DP)</li>\\n<li>난이도: LEVEL3</li>\\n<li>추가 사항: null</li>\\n</ul>\\n\\n<h3>입출력 예 :</h3>\\n<table><tr><th>입력</th><th>출력</th></tr><tr><td>4 5\\n1 0 1 0 0\\n1 0 1 1 1\\n1 1 1 1 1\\n1 0 0 1 0\\n</td><td>4</td></tr><tr><td>3 3\\n0 0 0\\n0 0 0\\n0 0 0\\n</td><td>0</td></tr><tr><td>5 5\\n1 1 0 0 1\\n1 1 0 1 1\\n0 1 1 1 1\\n0 1 1 1 0\\n1 1 1 0 0\\n</td><td>4</td></tr></table>\"")
        private String codingTestContent;
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
        private Long codeHistoryId;

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
        private Long chatHistoryId;
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
