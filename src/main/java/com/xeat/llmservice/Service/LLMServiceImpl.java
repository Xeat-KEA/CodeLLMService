package com.xeat.llmservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeat.llmservice.DTO.LLMRequestDTO;
import com.xeat.llmservice.DTO.LLMResponseDTO;
import com.xeat.llmservice.Global.ResponseEntity;
import com.xeat.llmservice.OpenAI.FunctionSpec;
import com.xeat.llmservice.OpenAI.OpenAIMessage;
import com.xeat.llmservice.OpenAI.OpenAIRequest;
import com.xeat.llmservice.Repository.LLMHistoryRepository;
import com.xeat.llmservice.Repository.LLMRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.xeat.llmservice.OpenAI.FunctionSpec.codeContentPropertiesDetail.createCodeContentPropertiesDetail;
import static com.xeat.llmservice.OpenAI.FunctionSpec.codeGeneratorFunctionDetail.createCodeGeneratorFunctionDetail;
import static com.xeat.llmservice.OpenAI.FunctionSpec.codePropertiesDetail.createCodePropertiesDetail;
import static com.xeat.llmservice.OpenAI.FunctionSpec.contentSpec.createContentSpec;
import static com.xeat.llmservice.OpenAI.FunctionSpec.itemsSpec.createItemsSpec;
import static com.xeat.llmservice.OpenAI.FunctionSpec.parameterSpec.createParameterSpec;
import static com.xeat.llmservice.OpenAI.FunctionSpec.testCasePropertiesSpec.createTestCasePropertiesSpec;
import static com.xeat.llmservice.OpenAI.FunctionSpec.testCaseSpec.createTestCaseSpec;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LLMServiceImpl implements LLMService{
    private final LLMRepository llmRepository;
    private final LLMHistoryRepository llmHistoryRepository;

    @Override
    public Mono<ResponseEntity<LLMResponseDTO>> codeGenerator(LLMRequestDTO.codeGeneratingInfo request) {


        return null;
    }

    @Override
    public ResponseEntity<LLMResponseDTO> chat(LLMRequestDTO.chatMessage request) {
        if(request.getCodeHistoryId()==null){
//            getuserLanguage

        }


        return null;
    }



//    // cache에 저장된 질문을 가져오는 메소드
//    private static final String RECENT_QUESTION = "recentQuestion :";
//    private final RedisTemplate<String, Object> redisTemplate;
//    public void


}

//    private final WebClient webClient;
//    private final ObjectMapper objectMapper;
//    private final String apiKey;

//    // Constructor
//    public LLMServiceImpl(LLMRepository llmRepository,
//                          LLMHistoryRepository llmHistoryRepository,
//                          WebClient.Builder webClientBuilder,
//                          @Value("${chat-GPT.api-key}") String apiKey,
//                          @Value("${chat-GPT.base-url}") String baseUrl) {
//        this.llmRepository = llmRepository;
//        this.llmHistoryRepository = llmHistoryRepository;
//        this.apiKey = apiKey;
//        this.objectMapper = new ObjectMapper();
//        this.webClient = webClientBuilder
//                .baseUrl(baseUrl)
//                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
//                .build();
//    }
//
//    private final static String ALGORITHM = "알고리즘 : ";
//    private final static String DIFFICULTY = "난이도 : ";
//    private final static String ETC = "추가 사항 : ";
//
//    @Override
//    public Mono<ResponseEntity<LLMResponseDTO>> codeGenerator(LLMRequestDTO.codeGeneratingInfo request) {
//        String etc = request.getEtc() != null ? request.getEtc() : "null";
//
//        OpenAIMessage systemMessage = OpenAIMessage.createMessage("system", "난이도(1~5), 알고리즘, 기타 사항을 받아 한글로 만든 코딩테스트를 만들어준다. 모든 질문의 답은 html 태그에 담아 말하고, 적재적소에 필요한 태그를 사용한다. 단, <h2>가 가장 큰 글씨이다.  입력 예제, 출력 예제 하나를 제외한 테스트케이스는 말해주지는 않는다.");
////        OpenAIMessage assistantMessage = OpenAIMessage.createMessage("assistant", "");
//        OpenAIMessage userMessage = OpenAIMessage.createMessage("user", DIFFICULTY + request.getDifficulty()+ " " + ALGORITHM + request.getAlgorithm() + " " + ETC + etc);
//        OpenAIMessage.toolMessage toolMessage = OpenAIMessage.toolMessage.createExtendedMessage("tool", "\"success : true\"", "call_jRc9982SHvKmEZ0NxhDJaibm");
//        OpenAIMessage assistantInfoMessage = OpenAIMessage.assistantMessage.createExtendedMessage("assistant",
//                "call_OGZEbEe2XEJXuy69BUVYkhFA",
//                "create_coding_test",
//                "<h2>DP를 활용한 최적 부분 문제 해결: 피보나치 수열</h2><br><p>동적 프로그래밍(DP)은 복잡한 문제를 더 간단한 하위 문제로 나누고, 그 하위 문제들을 풀어서 원래의 문제를 해결하는 알고리즘 기법이다. 이번 코딩 테스트에서는 피보나치 수열을 DP를 활용하여 해결하는 문제를 다룬다.</p><br><p>피보나치 수열은 다음과 같은 점화식을 갖는다:</p><ul><li><code>F(0) = 0</code></li><li><code>F(1) = 1</code></li><li><code>F(n) = F(n-1) + F(n-2)</code> for <code>n >= 2</code></li></ul><br><p>정수 <code>n</code>이 주어졌을 때, <code>F(n)</code>을 DP를 사용하여 계산하라.</p><br><h2>입력</h2><p>정수 <code>n</code> (0 ≤ n ≤ 30)</p><br><h2>출력</h2><p><code>F(n)</code>의 값을 정수로 출력한다.</p>");
//
//        FunctionSpec codeGeneratorFunctionSpec = FunctionSpec.createFunctionSpec("function",
//                FunctionSpec.functionDetail.createFunctionDetail("create_coding_test","난이도(1~5), 알고리즘, 기타 추가사항(can null)을 받아 코딩테스트를 만든다.",
//                        createCodeGeneratorFunctionDetail("object", List.of("difficulty", "algorithm", "additional_notes","title","content"), false,
//                                createCodePropertiesDetail(createParameterSpec("string","코딩테스트 제목"),
//                                        createContentSpec("object", List.of("html", "test_cases"),
//                                                createCodeContentPropertiesDetail(createParameterSpec("string","코딩테스트 및 글 내용을 담는 HTML 코드이다.다음 네가지 조건을 꼭 지킨다. 1. \\n을 필요한 곳에 꼭 넣으며, <br>을 통해 단을 나눈다. 2. 강조될 글자는 최대 <h2>이다. 3. 일반텍스트는 <p>에 담는다. 4. 필요에 의해 목록은 <ul><li>, 코드는 <pre><code>에 담는다."),
//                                                        createTestCaseSpec("array",
//                                                                createItemsSpec("object",List.of("input","expected_output"),false,
//                                                                        createTestCasePropertiesSpec(createParameterSpec("string","테스트케이스의 입력값이다."),
//                                                                                createParameterSpec("string","테스트케이스의 출력값이다."))),
//                                                                "테스트 케이스 배열, 최대 20개를 담는다."))),false),
//                                createParameterSpec("string","사용할 알고리즘"),
//                                createParameterSpec("string","난이도 level(1~5)"),
//                                createParameterSpec("string","추가 사항(can null)")
//                                ),true));
//
//        OpenAIRequest openAIRequest = OpenAIRequest.builder()
//                .model("gpt-4o")
//                .messages(List.of(systemMessage, userMessage, assistantInfoMessage, toolMessage))
//                .temperature(1)
//                .max_tokens(4095)
//                .top_p(1)
//                .frequency_penalty(0)
//                .presence_penalty(0)
//                .tools(List.of(codeGeneratorFunctionSpec))
//                .parallel_tool_calls(true)
//                .response_format(OpenAIRequest.responseFormatSpec.builder().type("text").build())
//                .build();
//
//        log.info("Request to OpenAI: {}", openAIRequest.toString());
//        String requestBody="";
//        try {
//            requestBody = objectMapper.writeValueAsString(openAIRequest);
//            log.info("Request to OpenAI: {}", objectMapper.writeValueAsString(openAIRequest));
//        } catch (Exception e) {
//            log.error("Failed to convert to JSON", e);
//        }
//
//        return webClient.post()
//                .uri("/chat/completions")
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(LLMResponseDTO.class)
//                .map(ResponseEntity::success)
//                .doOnError(e -> log.error("Failed to generate code", e));
//    }
