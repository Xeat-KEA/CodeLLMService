package com.xeat.llmservice.OpenAI;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class FunctionSpec {
    @Data
    @Builder
    public static class CodingTestGeneratorVo {
        private String name;
        private String description;
        private boolean strict;
        private Parameters parameters;

        @Data
        @Builder
        public static class Parameters {
            private String type;
            private Properties properties;
            private List<String> required;

            @Data
            @Builder
            public static class Properties {
                private Title title;
                private Content content;
                private Algorithm algorithm;
                private Difficulty difficulty;
                private AdditionalNotes additionalNotes;

                @Data
                @Builder
                public static class Title {
                    private String type;
                    private String description;
                }

                @Data
                @Builder
                public static class Content {
                    private String type;
                    private String description;
                    private ContentProperties properties;

                    @Data
                    @Builder
                    public static class ContentProperties {
                        private Html html;
                        private List<TestCase> testCases;

                        @Data
                        @Builder
                        public static class Html {
                            private String type;
                            private String description;
                        }

                        @Data
                        @Builder
                        public static class TestCase {
                            private String type;
                            private String description;
                            private String input;
                            private String expectedOutput;
                        }
                    }

                }

                @Data
                @Builder
                public static class Algorithm {
                    private String type;
                    private String description;
                }

                @Data
                @Builder
                public static class Difficulty {
                    private String type;
                    private String description;
                }

                @Data
                @Builder
                public static class AdditionalNotes {
                    private String type;
                    private String description;
                    private boolean nullable;
                }
            }
        }
        public static List<CodingTestGeneratorVo> setCTGeneratorFunctionList() {
            List<CodingTestGeneratorVo> ctGeneratorFunctionList = new ArrayList<>();
            ctGeneratorFunctionList.add(CodingTestGeneratorVo.builder()
                    .name("coding-test-generator")
                    .description("난이도(1~5), 알고리즘, 기타 사항을 받아 한글로 만든 코딩테스트를 만들어준다. 모든 질문의 답은 html 태그에 담아 말하고, 적재적소에 필요한 태그를 사용한다. 단, <h2>가 가장 큰 글씨이다.  입력 예제, 출력 예제 하나를 제외한 테스트케이스는 말해주지는 않는다.")
                    .strict(true)
                    .parameters(CodingTestGeneratorVo.Parameters.builder()
                            .type("object")
                            .properties(CodingTestGeneratorVo.Parameters.Properties.builder()
                                    .title(CodingTestGeneratorVo.Parameters.Properties.Title.builder()
                                            .type("string")
                                            .description("코딩테스트 제목")
                                            .build())
                                    .content(CodingTestGeneratorVo.Parameters.Properties.Content.builder()
                                            .type("object")
                                            .description("코딩테스트 내용")
                                            .properties(CodingTestGeneratorVo.Parameters.Properties.Content.ContentProperties.builder()
                                                    .html(CodingTestGeneratorVo.Parameters.Properties.Content.ContentProperties.Html.builder()
                                                            .type("string")
                                                            .description("코딩테스트 내용")
                                                            .build())
                                                    .testCases(List.of(CodingTestGeneratorVo.Parameters.Properties.Content.ContentProperties.TestCase.builder()
                                                            .type("array")
                                                            .description("테스트케이스")
                                                            .input("string")
                                                            .expectedOutput("string")
                                                            .build()))
                                                    .build())
                                            .build())
                                    .algorithm(CodingTestGeneratorVo.Parameters.Properties.Algorithm.builder()
                                            .type("string")
                                            .description("알고리즘")
                                            .build())
                                    .difficulty(CodingTestGeneratorVo.Parameters.Properties.Difficulty.builder()
                                            .type("number")
                                            .description("난이도")
                                            .build())
                                    .additionalNotes(CodingTestGeneratorVo.Parameters.Properties.AdditionalNotes.builder()
                                            .type("string")
                                            .description("추가 사항")
                                            .nullable(true)
                                            .build())
                                    .build())
                            .build())
                    .build());
            return ctGeneratorFunctionList;
        }
    }




//    private String type;
//    private functionDetail function;
//
//    public static FunctionSpec createFunctionSpec(String type, functionDetail function){
//        return FunctionSpec.builder()
//                .type(type)
//                .function(function)
//                .build();
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    @Getter
//    public static class functionDetail{
//        private String name;
//        private String description;
//        private parameterDetail parameters;
//        private Boolean strict;
//
//        public static functionDetail createFunctionDetail(String name, String description, parameterDetail parameters, Boolean strict){
//            return functionDetail.builder()
//                    .name(name)
//                    .description(description)
//                    .parameters(parameters)
//                    .strict(strict)
//                    .build();
//        }
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @SuperBuilder
//    @Getter
//    public static class parameterDetail{
//        private String type;
//        private List<String> required;
//        private Boolean additionalProperties;
//
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @SuperBuilder
//    @Getter
//    public static class codeGeneratorFunctionDetail extends parameterDetail{
//        private codePropertiesDetail properties;
//        private parameterSpec algorithm;
//        private parameterSpec difficulty;
//        private parameterSpec additional_notes;
//
//        public static codeGeneratorFunctionDetail createCodeGeneratorFunctionDetail(String type, List<String> required, Boolean additionalProperties, codePropertiesDetail properties, parameterSpec algorithm, parameterSpec difficulty, parameterSpec additional_notes){
//            return codeGeneratorFunctionDetail.builder()
//                    .type(type)
//                    .required(required)
//                    .properties(properties)
//                    .algorithm(algorithm)
//                    .difficulty(difficulty)
//                    .additional_notes(additional_notes)
//                    .additionalProperties(additionalProperties)
//                    .build();
//        }
//    }
//
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    @Getter
//    public static class codePropertiesDetail{
//        private parameterSpec title;
//        private contentSpec content;
//        private Boolean additionalProperties;
//
//        public static codePropertiesDetail createCodePropertiesDetail(parameterSpec title, contentSpec content, Boolean additionalProperties){
//            return codePropertiesDetail.builder()
//                    .title(title)
//                    .content(content)
//                    .additionalProperties(additionalProperties)
//                    .build();
//        }
//
//
//    }
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    @Getter
//    public static class contentSpec{
//        private String type;
//        private List<String> required;
//        private codeContentPropertiesDetail properties;
//
//        public static contentSpec createContentSpec(String type,List<String> required ,codeContentPropertiesDetail properties){
//            return contentSpec.builder()
//                    .type(type)
//                    .required(required)
//                    .properties(properties)
//                    .build();
//        }
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    @Getter
//    public static class codeContentPropertiesDetail{
//        private parameterSpec html;
//        private testCaseSpec test_cases;
//        private Boolean additionalProperties;
//
//        public static codeContentPropertiesDetail createCodeContentPropertiesDetail(parameterSpec html, testCaseSpec test_cases){
//            return codeContentPropertiesDetail.builder()
//                    .html(html)
//                    .test_cases(test_cases)
//                    .additionalProperties(false)
//                    .build();
//        }
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    @Getter
//    public static class parameterSpec{
//        private String type;
//        private String description;
//
//        public static parameterSpec createParameterSpec(String type, String description){
//            return parameterSpec.builder()
//                    .type(type)
//                    .description(description)
//                    .build();
//        }
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    @Getter
//    public static class testCaseSpec{
//        private String type;
//        private itemsSpec items;
//        private String description;
//
//        public static testCaseSpec createTestCaseSpec(String type, itemsSpec items, String description){
//            return testCaseSpec.builder()
//                    .type(type)
//                    .items(items)
//                    .description(description)
//                    .build();
//        }
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    @Getter
//    public static class itemsSpec{
//        private String type;
//        private List<String> required;
//        private Boolean additionalProperties;
//        private testCasePropertiesSpec properties;
//
//        public static itemsSpec createItemsSpec(String type, List<String> required, Boolean additionalProperties, testCasePropertiesSpec properties){
//            return itemsSpec.builder()
//                    .type(type)
//                    .required(required)
//                    .properties(properties)
//                    .additionalProperties(additionalProperties)
//                    .build();
//        }
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    @Getter
//    public static class testCasePropertiesSpec{
//        private parameterSpec input;
//        private parameterSpec expected_output;
//
//        public static testCasePropertiesSpec createTestCasePropertiesSpec(parameterSpec input, parameterSpec output){
//            return testCasePropertiesSpec.builder()
//                    .input(input)
//                    .expected_output(output)
//                    .build();
//        }
//    }

}

