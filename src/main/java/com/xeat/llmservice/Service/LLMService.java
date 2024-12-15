package com.xeat.llmservice.Service;

import com.xeat.llmservice.DTO.LLMRequestDTO;
import com.xeat.llmservice.DTO.LLMResponseDTO;
import com.xeat.llmservice.Global.ResponseCustomEntity;


public interface LLMService {
    ResponseCustomEntity<LLMResponseDTO.CodeGenerateClientResponse> codeGenerator(String userId, LLMRequestDTO.codeGeneratingInfo request);
    ResponseCustomEntity<LLMResponseDTO.CodeQuestionClientResponse> chat(String userId, LLMRequestDTO.chatMessage request);
    ResponseCustomEntity<LLMResponseDTO.ChatResponseList> chatPagedHistory(String userId, Long codeHistoryId, Integer page);
}
