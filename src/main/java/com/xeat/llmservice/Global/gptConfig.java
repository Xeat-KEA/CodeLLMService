package com.xeat.llmservice.Global;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class gptConfig {
    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("코딩테스트에 대한 질문을 응답해주는 친절한 챗봇입니다. 질문을 입력해주세요.").build();
    }
}
