package com.xeat.llmservice.OpenAI;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class OpenAIHeaderConfiguration {
    @Value("${openai.api.key}")
    String OpenAIAPIKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + OpenAIAPIKey);
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
