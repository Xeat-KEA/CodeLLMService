package com.xeat.llmservice.Controller;

import com.xeat.llmservice.DTO.LLMRequestDTO;
import com.xeat.llmservice.DTO.LLMResponseDTO;
import com.xeat.llmservice.Global.ResponseEntity;
import com.xeat.llmservice.Service.LLMService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/llm")
@Tag(name = "Chat-GPT(LLM) 서비스", description = "LLM 관련 API")
public class LLMController {
    private final LLMService llmService;
    @Operation(summary = "코딩테스트 문제 생성 API", description = "난이도(1~5), 알고리즘, 추가 사항을 기반으로 코딩 테스트를 생성하는 API")
    @PostMapping("/code-generating")
    public ResponseEntity<LLMResponseDTO.CodeGenerateClientResponse> codeGenerator(@RequestBody LLMRequestDTO.codeGeneratingInfo request) {
        return llmService.codeGenerator(request);
    }

    @PostMapping("/chat")
    public ResponseEntity<LLMResponseDTO> chat(@RequestBody LLMRequestDTO.chatMessage request) {
        return llmService.chat(request);
    }
}
