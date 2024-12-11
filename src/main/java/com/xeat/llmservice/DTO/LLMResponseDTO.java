package com.xeat.llmservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeat.llmservice.Entity.LLMHistoryEntity;
import com.xeat.llmservice.Global.Enum.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        @Schema(name = "content", description = "문제 내용", example = "\"<h3>문제 설명:</h3>\\n<p>당신은 '1'과 '0'으로 이루어진 2D 이진 행렬에서 가장 큰 정사각형으로 '1'로 이루어진 배열을 찾아야 합니다. 이때, 가장 큰 정사각형 배열의 면적을 반환합니다.</p>\\n\\n<h3>문제 제한 사항:</h3>\\n<ul>\\n<li>행렬의 크기는 MxN이며, M과 N은 최대 300입니다.</li>\\n</ul>\\n\\n<h3>입력 예시:</h3>\\n<pre><code>4 5\\n1 0 1 0 0\\n1 0 1 1 1\\n1 1 1 1 1\\n1 0 0 1 0\\n</code></pre>\\n\\n<h3>출력 예시:</h3>\\n<pre><code>4\\n</code></pre>\\n\\n<h3>입출력 예 설명:</h3>\\n<p>입력 행렬에서 가장 큰 정사각형의 크기는 2x2이며, 면적은 4입니다.</p>\\n\\n<h3>문제 정보:</h3>\\n<ul>\\n<li>알고리즘: 동적 프로그래밍(DP)</li>\\n<li>난이도: LEVEL3</li>\\n<li>추가 사항: null</li>\\n</ul>\\n\\n<h3>입출력 예 :</h3>\\n<table><tr><th>입력</th><th>출력</th></tr><tr><td>4 5\\n1 0 1 0 0\\n1 0 1 1 1\\n1 1 1 1 1\\n1 0 0 1 0\\n</td><td>4</td></tr><tr><td>3 3\\n0 0 0\\n0 0 0\\n0 0 0\\n</td><td>0</td></tr><tr><td>5 5\\n1 1 0 0 1\\n1 1 0 1 1\\n0 1 1 1 1\\n0 1 1 1 0\\n1 1 1 0 0\\n</td><td>4</td></tr></table>\"")
        private String content;
        @Schema(name = "difficulty", description = "난이도", example = "LEVEL3")
        private Difficulty difficulty;
        @Schema(name = "additionalNotes", description = "추가 사항", example = "문제의 조건을 만족하는 최소의 시간 복잡도를 갖는 알고리즘을 작성해줘.(can be null)")
        private String additionalNotes;
        @Schema(name = "testCases", description = "테스트 케이스 목록(front 필요 없을 확률이 높음.)")
        private List<TestCaseSpec> testCases;

        // Client에서 사용할 응답 객체 생성자
        @Builder
        public CodeGenerateResponse(String title, String algorithm, String content, Difficulty difficulty) {
            this.title = title;
            this.algorithm = algorithm;
            this.content = content;
            this.difficulty = difficulty;
        }

        @Builder
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        @Schema(name = "TestCaseSpec", description = "테스트 케이스 명세", title = "TestCaseSpec [테스트 케이스 명세]")
        public static class TestCaseSpec {
            @JsonProperty("input")
            @Schema(name = "input", description = "입력값", example = "5 5\n0 0 0 0 0\n0 -1 0 -1 0\n0 0 0 -1 0\n0 -1 -1 0 -1\n0 0 0 0 0")
            private String input;
            @Schema(name = "output", description = "예상 출력값", example = "7\n")
            @JsonProperty("output")
            private String output;
        }


        public static LLMResponseDTO.CodeGenerateResponse of(String jsonResponse) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode rootNode = objectMapper.readTree(jsonResponse);

                JsonNode problemInfoNode = rootNode.path("problem_info").get(0);


                List<TestCaseSpec> contnetTestCases = objectMapper.readValue(
                        rootNode.path("content_test_cases").toString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, TestCaseSpec.class)
                );

                String content = rootNode.path("content").asText() +"\n\n<h3>입출력 예 :</h3>\n"+ makeContent(contnetTestCases);


                List<TestCaseSpec> testCases = objectMapper.readValue(
                        rootNode.path("test_cases").toString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, TestCaseSpec.class)
                );

                List<TestCaseSpec> mergedTestCases = Stream.concat(contnetTestCases.stream(), testCases.stream())
                        .toList();

                return LLMResponseDTO.CodeGenerateResponse.builder()
                        .title(rootNode.path("title").asText())
                        .content(content)
                        .algorithm(problemInfoNode.path("algorithm").asText())
                        .difficulty(Difficulty.valueOf(problemInfoNode.path("difficulty").asText()))
                        .additionalNotes(problemInfoNode.path("additional_notes").asText())
                        .testCases(mergedTestCases)
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse JSON response"+ e.getMessage(), e);
            }
        }

        public static String makeContent(List<TestCaseSpec> contentTest){
            return "<table><tr><th>입력</th><th>출력</th></tr>" + contentTest.stream()
                    .map(test -> "<tr><td>"+test.getInput()+"</td><td>"+test.getOutput()+"</td></tr>")
                    .collect(Collectors.joining()) + "</table>";
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "CodeGenerateClientResponse", description = "코딩테스트 생성 클라이언트 응답", title = "CodeGenerateClientResponse [코딩테스트 생성 클라이언트 응답]")
    public static class CodeGenerateClientResponse extends CodeGenerateResponse{
        @Schema(name = "codeId", description = "코딩 테스트 ID", example = "1")
        private Integer codeId;

        public CodeGenerateClientResponse (String title, String algorithm, String content, Difficulty difficulty, Integer codeId) {
            super(title, algorithm, content, difficulty);
            this.codeId = codeId;
        }

        public static CodeGenerateClientResponse of(CodeGenerateResponse codeGenerateResponse, Integer codeId){
            return new CodeGenerateClientResponse(codeGenerateResponse.getTitle(),
                    codeGenerateResponse.getAlgorithm(),
                    codeGenerateResponse.getContent(),
                    codeGenerateResponse.getDifficulty(),
                    codeId);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "CodeQuestionClientResponse", description = "코딩테스트 gpt 질문 클라이언트 응답", title = "CodeQuestionClientResponse [코딩테스트 gpt 질문 클라이언트 응답]")
    public static class CodeQuestionClientResponse {
        @Schema(name = "answer", description = "질문에 대한 답변", example = "<h3>Python에서 배열을 만드는 함수들</h3>\\n\\n<p>Python에서 배열을 만들기 위해서는 여러 방법이 있습니다. 가장 일반적인 방법 몇 가지를 소개하겠습니다.</p>\\n\\n<h3>기본 리스트(List)</h3>\\n<p>Python에서는 기본적으로 리스트를 사용하여 배열과 유사한 기능을 구현할 수 있습니다.</p>\\n<pre><code># 빈 리스트 생성\\nmy_list = []\\n\\n# 초기값이 있는 리스트 생성\\nmy_list = [1, 2, 3, 4, 5]\\n</code></pre>\\n\\n<h3>Array 모듈 사용</h3>\\n<p>Python의 내장 모듈인 `array`를 사용하여 배열을 만들 수 있습니다. 이 방법은 값의 데이터 타입을 고정해야 할 때 유용합니다.</p>\\n<pre><code>import array\\n\\n# 정수를 담을 배열 생성\\nmy_array = array.array('i', [1, 2, 3, 4, 5])\\n</code></pre>\\n<p>여기서 `'i'`는 배열이 정수를 담고 있다는 데이터 타입코드입니다.</p>\\n\\n<h3>NumPy 라이브러리 사용</h3>\\n<p>NumPy는 수학 연산을 포함한 다양한 기능을 제공하는 강력한 라이브러리로, 배열을 다루는데 최적화되어 있습니다. NumPy 배열은 다차원 배열을 쉽게 처리할 수 있습니다.</p>\\n<pre><code>import numpy as np\\n\\n# NumPy 배열 생성\\nnp_array = np.array([1, 2, 3, 4, 5])\\n</code></pre>\\n\\n<p>각 방법은 목적과 용도에 따라 선택할 수 있습니다. 단순한 배열이 필요하다면 일반 리스트를, 데이터 타입이 중요한 경우는 array 모듈을, 그리고 더 복잡한 수학적 연산이 필요한 경우 NumPy를 사용하는 것이 적절합니다.</p>")
        private String answer;

        public static CodeQuestionClientResponse of(String answer){
            return new CodeQuestionClientResponse(answer);
        }

    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ChatResponse {
        @Schema(name = "codeId", description = "코딩 테스트 ID", example = "1")
        @Lob
        private String question;
        @Lob
        private String answer;
        private Integer chatHistoryId;

        public static ChatResponse of(LLMHistoryEntity llmHistoryEntity){
            return ChatResponse.builder()
                    .question(llmHistoryEntity.getQuestion())
                    .answer(llmHistoryEntity.getAnswer())
                    .chatHistoryId(llmHistoryEntity.getChatHistoryId())
                    .build();
        }
    }



    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(name = "CodeGenerateClientResponse", description = "코딩테스트 생성 클라이언트 응답", title = "CodeGenerateClientResponse [코딩테스트 생성 클라이언트 응답]")
    public static class ChatResponseList {
        List<ChatResponse> chatResponseList;
        Integer totalPage;
        Long totalElements;
        Integer listSize;
        Boolean firstPage;
        Boolean lastPage;

        public static ChatResponseList toChatResponseList(Page<LLMHistoryEntity> chatPage, List<ChatResponse> chatResponseList){
            return ChatResponseList.builder()
                    .chatResponseList(chatResponseList)
                    .totalPage(chatPage.getTotalPages())
                    .totalElements(chatPage.getTotalElements())
                    .listSize(chatResponseList.size())
                    .firstPage(chatPage.isFirst())
                    .lastPage(chatPage.isLast())
                    .build();
        }
    }



}
