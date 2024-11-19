package com.xeat.llmservice.OpenAI;

import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class OpenAIFunctionConfig {
    @Bean(name = "coding-test-generator")
    @Description("난이도(1~5), 알고리즘, 추가 사항을 기반으로 코딩 테스트를 생성하는 기능입니다.")
    public Function<Object,FunctionSpec.CodingTestGeneratorVo> codingTestGeneratorFunction(){
        FunctionSpec.CodingTestGeneratorVo generatorVo = FunctionSpec.CodingTestGeneratorVo.setCTGeneratorFunctionList().get(0);
        return input -> generatorVo;
    }
}
