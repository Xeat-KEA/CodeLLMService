package com.xeat.llmservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeat.llmservice.Global.Enum.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Getter
@AllArgsConstructor
@Schema(name = "LLMResponseDTO", description = "LLM 응답 양식", title = "LLMResponseDTO [LLM 응답 양식]")
public class LLMResponseDTO {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "CodeGenerateResponse", description = "코딩테스트 생성 응답", title = "CodeGenerateResponse [코딩테스트 생성 응답]")
    public static class CodeGenerateResponse{
        @Schema(name = "title", description = "문제 제목", example = "혼자 남지 않기 위한 최적의 경로 찾기")
        private String title;
        @Schema(name = "algorithm", description = "알고리즘", example = "DP")
        private String algorithm;
        @Schema(name = "content", description = "문제 내용", example = "<h3>혼자 남지 않기 위한 최적의 경로 찾기</h3>\n\n<p>이 문제는 '혼자 남지 않기 위한 최적의 경로 찾기'를 목표로 합니다. 주어진 2차원 격자에서 특정 조건을 만족하며 목적지에 도달하기 위한 행동을 결정해야 합니다. 주어진 조건을 만족하는 함수를 작성하고, 격자를 탐색하는 중 최대한의 안전성을 확보해야 합니다.</p>\n\n<h4>문제 설명:</h4>\n<p>이 문제는 특정 시작점에서 목적지까지 '혼자 남지 않기' 위한 최적의 경로를 찾아야 합니다. 2차원 격자에서는 특정 위치로 이동할 때 1의 스탭이 소요되며, 각 위치에서 움직일 수 있는 최대 경로 수를 제한하여 주어질 것입니다. 주어진 조건하에서 최단 경로를 결정해야 하며, 경로에 존재하는 위험 요소를 피하면서 격자를 탐색해야 합니다.</p>\n\n<h4>문제 제한 사항:</h4>\n<ul>\n<li>격자 크기: M x N (1 ≤ M, N ≤ 100)</li>\n<li>시작점: 출발 위치는 (0,0)으로 고정되어 있습니다.</li>\n<li>목적지: 목적지는 항상 오른쪽 아래 모서리 (M-1, N-1)입니다.</li>\n<li>위험 요소는 격자 내 임의의 위치에 존재할 수 있으며, 이를 피하는 것이 중요합니다.</li>\n</ul>\n\n<h4>입력 예시:</h4>\n<pre><code>\n5 5\n0 0 0 0 0\n0 -1 0 -1 0\n0 0 0 -1 0\n0 -1 -1 0 -1\n0 0 0 0 0\n</code></pre>\n\n<h4>출력 예시:</h4>\n<pre><code>\n7\n</code></pre>\n\n<h4>입출력 예 설명:</h4>\n<p>주어진 격자에서 (0,0)에서 (4,4)까지 가장 적은 스탭으로 이동하기 위한 경로는 7스탭 입니다. 중간에 -1은 이동할 수 없는 위치이며, 다른 안전한 경로를 찾아야 합니다.</p>")
        private String content;
        @Schema(name = "difficulty", description = "난이도", example = "LEVEL3")
        private Difficulty difficulty;
        @Schema(name = "additionalNotes", description = "추가 사항", example = "문제의 조건을 만족하는 최소의 시간 복잡도를 갖는 알고리즘을 작성해줘.(can be null)")
        private String additionalNotes;
        @Schema(name = "testCases", description = "테스트 케이스 목록(front 필요 없을 확률이 높음.)")
        private List<TestCaseSpec> testCases;

        @Builder
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        @Schema(name = "TestCaseSpec", description = "테스트 케이스 명세", title = "TestCaseSpec [테스트 케이스 명세]")
        public static class TestCaseSpec {
            @JsonProperty("input")
            @Schema(name = "input", description = "입력값", example = "5 5\n0 0 0 0 0\n0 -1 0 -1 0\n0 0 0 -1 0\n0 -1 -1 0 -1\n0 0 0 0 0")
            private String input;
            @Schema(name = "expectedOutput", description = "예상 출력값", example = "7\n")
            @JsonProperty("expected_output")
            private String expectedOutput;
        }


        public static LLMResponseDTO.CodeGenerateResponse of(String jsonResponse) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode rootNode = objectMapper.readTree(jsonResponse);

                JsonNode problemInfoNode = rootNode.path("problem_info").get(0);

                List<TestCaseSpec> testCases = objectMapper.readValue(
                        rootNode.path("test_cases").toString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, TestCaseSpec.class)
                );
                return LLMResponseDTO.CodeGenerateResponse.builder()
                        .title(rootNode.path("title").asText())
                        .content(rootNode.path("content").asText())
                        .algorithm(problemInfoNode.path("algorithm").asText())
                        .difficulty(Difficulty.valueOf(problemInfoNode.path("difficulty").asText()))
                        .additionalNotes(problemInfoNode.path("additional_notes").asText())
                        .testCases(testCases)
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse JSON response"+ e.getMessage(), e);
            }
    }






    }




}
