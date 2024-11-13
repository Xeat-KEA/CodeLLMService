package com.xeat.llmservice.OpenAI;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OpenAIRequest {
    private String model;
    private List<OpenAIMessage> messages;
    private Integer temperature;
    private Integer max_tokens;
    private Integer top_p;
    private Integer frequency_penalty;
    private Integer presence_penalty;
    private List<FunctionSpec> tools;
    private Boolean parallel_tool_calls;
    private responseFormatSpec response_format;


    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class responseFormatSpec{
        private String type;
    }
}
