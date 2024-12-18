package com.xeat.llmservice.Client;

import com.xeat.llmservice.DTO.LLMResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "code-bank-service")
public interface CodeBankClient {
    @PostMapping("/code/llm/gpt/create")
    ResponseEntity<ClientResponseDTO> createCodeId(@RequestBody LLMResponseDTO.CodeGenerateResponse request, @RequestHeader("UserId") String userId);
}
