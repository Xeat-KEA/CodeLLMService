package com.xeat.llmservice.Client;

import com.xeat.llmservice.DTO.ClientResponseDTO;
import com.xeat.llmservice.DTO.LLMResponseDTO;
import com.xeat.llmservice.Global.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "code-bank-service")
public interface CodeBankClient {
    @PostMapping("/code/llm/gpt/create")
    ResponseEntity<ClientResponseDTO.CodeBankResponseDTO> createCodeId(@RequestBody LLMResponseDTO.CodeGenerateResponse request);
}