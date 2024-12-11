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

    ResponseEntity<LLMResponseDTO.CodeGenerateClientResponse> codeGenerator(String userId, LLMRequestDTO.codeGeneratingInfo request);
    ResponseEntity<LLMResponseDTO.CodeQuestionClientResponse> chatIncludeAnswer(String userId, LLMRequestDTO.chatMessage request);

    ResponseEntity<LLMResponseDTO.CodeQuestionClientResponse> chatJustGuidance(String userId, LLMRequestDTO.chatMessage request);


    ResponseEntity<LLMResponseDTO.ChatResponseList> chatHistory(String userId, Integer codeHistoryId);

    ResponseEntity<LLMResponseDTO.ChatResponseList> chatPagedHistory(String userId, Integer codeHistoryId, Integer page);
}
