package com.xeat.llmservice.Service;

import com.xeat.llmservice.DTO.LLMRequestDTO;
import com.xeat.llmservice.DTO.LLMResponseDTO;
import com.xeat.llmservice.Global.ResponseEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


public interface LLMService {

    ResponseEntity<LLMResponseDTO.CodeGenerateResponse> codeGenerator(LLMRequestDTO.codeGeneratingInfo request);
    ResponseEntity<LLMResponseDTO> chat(LLMRequestDTO.chatMessage request);


}
