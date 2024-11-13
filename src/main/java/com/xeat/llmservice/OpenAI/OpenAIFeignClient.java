package com.xeat.llmservice.OpenAI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "OpenAIClient", url="https://api.openai.com/v1", configuration = {OpenAIHeaderConfiguration.class})
public interface OpenAIFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/chat/completions")
    OpenAIMessage sendMessage(@RequestBody OpenAIMessage message);
}
