package com.xeat.llmservice.OpenAI;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FunctionSpec {
    private String type;
    private String description;
    private Parameters parameers;

    @Data
    public static class Parameters{
        private String type;
        private Properties properties;
        private List<String> required;

        @Data
        public static class Properties{
            private Format format;

            @Data
            public static class Format {
                private String type;
                private String description;
            }
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

