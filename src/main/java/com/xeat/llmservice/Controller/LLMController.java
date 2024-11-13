package com.xeat.llmservice.Controller;

import com.xeat.llmservice.DTO.LLMRequestDTO;
import com.xeat.llmservice.DTO.LLMResponseDTO;
import com.xeat.llmservice.Global.ResponseEntity;
import com.xeat.llmservice.Service.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/llm")
public class LLMController {
    private final LLMService llmService;

    @PostMapping("/code-generating")
    public Mono<ResponseEntity<LLMResponseDTO>> codeGenerator(@RequestBody LLMRequestDTO.codeGeneratingInfo request) {
        return llmService.codeGenerator(request);
    }

    @PostMapping("/chat")
    public ResponseEntity<LLMResponseDTO> chat(@RequestBody LLMRequestDTO.chatMessage request) {
        return llmService.chat(request);
    }
}
