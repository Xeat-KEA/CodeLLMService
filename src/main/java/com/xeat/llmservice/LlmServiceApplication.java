package com.xeat.llmservice;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
public class LlmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LlmServiceApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.KOREA);
		return localeResolver;
	}

	// 질문과 응답 간의 유사성(벡터화)을 계산하기 위한 EmbeddingModel을 생성합니다.
	@Bean
	public EmbeddingModel embeddingModel(@Value("${spring.ai.openai.api-key}") String apiKey)  {
		return new OpenAiEmbeddingModel(new OpenAiApi(System.getenv(apiKey)));
	}
}
