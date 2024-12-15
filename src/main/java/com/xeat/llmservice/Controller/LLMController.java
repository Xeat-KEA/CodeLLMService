package com.xeat.llmservice.Controller;

import com.xeat.llmservice.DTO.LLMRequestDTO;
import com.xeat.llmservice.DTO.LLMResponseDTO;
import com.xeat.llmservice.Global.ResponseCustomEntity;
import com.xeat.llmservice.Service.LLMService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    public ResponseCustomEntity<LLMResponseDTO.CodeGenerateClientResponse> codeGenerator(@RequestHeader("UserId") String userId,
                                                                                         @RequestBody LLMRequestDTO.codeGeneratingInfo request) {
        return llmService.codeGenerator(userId, request);
    }
    @Operation(summary = "코딩테스트 질의 응답 API", description = "코딩테스트 질문에 대한 정답을 포함한 답변을 제공하는 API")
    @ApiResponse(responseCode = "200", description = "정상적으로 질문에 대한 답변을 제공함",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LLMResponseDTO.CodeQuestionClientResponse.class)))
    @PostMapping("/chat")
    public ResponseCustomEntity<LLMResponseDTO.CodeQuestionClientResponse> chat(@RequestHeader("UserId") String userId,
                                                                                             @RequestBody LLMRequestDTO.chatMessage request) {
        return llmService.chat(userId, request);
    }

    @Operation(summary = "코딩테스트 채팅 페이지네이션 조회 API", description = "코딩테스트 채팅을 조회하는 API")
    @ApiResponse(responseCode = "200", description = "코딩테스트 채팅 조회",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LLMResponseDTO.ChatResponseList.class)))
    @GetMapping("/history/{codeHistoryId}")
    public ResponseCustomEntity<LLMResponseDTO.ChatResponseList> chatPagedHistory(@PathVariable Long codeHistoryId, @RequestParam("page") Integer page,
                                                                                  @RequestHeader("UserId") String userId) {
        return llmService.chatPagedHistory(userId,codeHistoryId, page);
    }
}
