package com.xeat.llmservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Getter
@AllArgsConstructor
public class LLMResponseDTO {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CodeGenerateResponse{
        private String title;
        private String algorithm;
        private String content;
        private String difficulty;
        private String additionalNotes;
        private List<TestCaseSpec> testCases;

        @Builder
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class TestCaseSpec {
            @JsonProperty("input")
            private String input;
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
                        .difficulty(problemInfoNode.path("difficulty").asText())
                        .additionalNotes(problemInfoNode.path("additional_notes").asText())
                        .testCases(testCases)
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse JSON response"+ e.getMessage(), e);
            }
    }






    }




}
