package com.xeat.llmservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;


public class LLMResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class test {
        String testString;

        public static test testBuilder(String testString) {
            return test.builder()
                    .testString(testString)
                    .build();
        }
    }


}
