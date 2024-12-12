package com.xeat.llmservice.Service;

import com.xeat.llmservice.DTO.LLMRequestDTO;
import com.xeat.llmservice.DTO.LLMResponseDTO;
import com.xeat.llmservice.Global.ResponseCustomEntity;


public interface LLMService {

    ResponseCustomEntity<LLMResponseDTO.CodeGenerateClientResponse> codeGenerator(String userId, LLMRequestDTO.codeGeneratingInfo request);
    ResponseCustomEntity<LLMResponseDTO.CodeQuestionClientResponse> chatIncludeAnswer(String userId, LLMRequestDTO.chatMessage request);

    ResponseCustomEntity<LLMResponseDTO.CodeQuestionClientResponse> chatJustGuidance(String userId, LLMRequestDTO.chatMessage request);


    ResponseCustomEntity<LLMResponseDTO.ChatResponseList> chatHistory(String userId, Long codeHistoryId);

    ResponseCustomEntity<LLMResponseDTO.ChatResponseList> chatPagedHistory(String userId, Long codeHistoryId, Integer page);
}
