package com.xeat.llmservice.Service;

import com.xeat.llmservice.DTO.LLMRequestDTO;
import com.xeat.llmservice.DTO.LLMResponseDTO;
import com.xeat.llmservice.Global.ResponseEntity;
import com.xeat.llmservice.Repository.LLMHistoryRepository;
import com.xeat.llmservice.Repository.LLMRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;



@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LLMServiceImpl implements LLMService{
    private final LLMRepository llmRepository;
    private final LLMHistoryRepository llmHistoryRepository;
    private final OpenAiChatModel openAiChatModel;

    @Override
    public ResponseEntity<LLMResponseDTO.CodeGenerateResponse> codeGenerator(LLMRequestDTO.codeGeneratingInfo request) {
        String etc = request.getEtc() != null ? request.getEtc() : "null";
        UserMessage userMessage1 = new UserMessage("난이도 : " + request.getDifficulty() + " " + "알고리즘 : " + request.getAlgorithm() + " " + "추가 사항 : " + etc);
        String jsonSchema = """
                {
                  "type": "object",
                  "properties": {
                    "title": {
                      "type": "string",
                      "description": "코딩 테스트 문제의 제목을 나타냅니다. 문제의 핵심 주제나 목적을 간결히 나타내는 한글 문구여야 합니다."
                    },
                    "content": {
                      "type": "string",
                      "description": "문제 설명을 HTML 형식으로 작성합니다. 문제 설명에는 다음 요소들이 포함되어야 합니다: \\n\\n- **문제 설명:** 문제의 배경과 목표를 설명 \\n- **문제 제한 사항:** 문제 풀이 조건을 기술(필요시 null 가능) \\n- **입력 예시:** 문제 풀이를 위한 예제 입력값,  \\n- **출력 예시:** 예제 입력에 대한 출력값 \\n- **입출력 예 설명:** 예제 입력과 출력의 관계를 설명 \\n\\nHTML 작성 시 다음 규칙을 지켜야 합니다: \\n1. 문단 구분 시 `\\n`과 `<br>`을 적절히 활용합니다. \\n2. 강조가 필요한 제목은 `<h3>`를 사용합니다. \\n3. 일반 텍스트는 `<p>` 태그에 포함합니다. \\n4. 목록은 `<ul><li>`, 코드 예시는 `<pre><code>`로 감싸 작성합니다.\\n\\n입출력 예시의 형식은 다음을 준수해야 합니다: \\n1. 입력값은 각 항목을 줄바꿈(`\\n`)을 이용해 나타냅니다. 예를 들어, 그래프의 노드와 간선을 입력받는 경우 다음과 같은 형식을 사용합니다: \\n```\\n6\\n0 1 5\\n0 2 10\\n1 3 8\\n2 3 2\\n3 4 5\\n4 5 2\\n``` \\n2. 출력값도 동일하게 줄바꿈(`\\n`)으로 나타내며, 필요한 경우 문장 대신 값만 포함합니다."
                    },
                    
                    "problem_info" : {
                      "type": "array",
                      "description" : "문제 제작에 필요한 정보를 배열로 작성합니다. 각 항목은 문제 알고리즘, 난이도, 추가 사항을 포함합니다.",
                      "items": {
                        "type" : "object",
                        "properties" : {
                          "algorithm": {
                            "type": "string",
                            "description": "문제 해결에 사용된 알고리즘의 이름입니다. 예: '브루트포스', 'DFS', 'BFS' 등"
                          },
                          "difficulty": {
                            "type": "string",
                            "description": "문제의 난이도를 1~5 사이의 숫자로 나타냅니다. (1: 가장 쉬움, 5: 매우 어려움)"
                          },
                          "additional_notes": {
                            "type": "string",
                            "description": "문제 제작자가 추가로 제공한 요구사항이나 정보입니다. (빈 값일 수 있음)"
                          }
                        },
                        "required": [
                          "algorithm",
                          "difficulty",
                          "additional_notes"
                        ],
                        "additionalProperties": false
                      }
                    },
                    
                    "test_cases": {
                      "type": "array",
                      "description": "테스트 케이스 배열로 문제 풀이 검증을 위한 입력과 출력 정보를 담습니다. 최대 20개의 케이스를 포함할 수 있습니다. \\n\\n테스트 케이스 입력값과 출력값은 각 줄마다 하나의 정보를 포함하며, 줄바꿈(`\\n`)을 이용하여 여러 줄로 나타냅니다. 예를 들어, 그래프 문제의 입력값은 다음 형식을 따릅니다: \\n```\\n6\\n0 1 5\\n0 2 10\\n1 3 8\\n2 3 2\\n3 4 5\\n4 5 2\\n``` \\n출력값은 다음과 같이 나타낼 수 있습니다: \\n```\\n15\\n0 1 3 4 5\\n```",
                      "items": {
                        "type": "object",
                        "properties": {
                          "input": {
                            "type": "string",
                            "description": "테스트 케이스의 입력값입니다. 각 줄에 하나의 정보를 포함하며, 여러 줄로 이루어진 데이터를 줄바꿈(`\\n`) 형식으로 작성합니다."
                          },
                          "expected_output": {
                            "type": "string",
                            "description": "해당 입력값에 대한 예상 출력값입니다. 출력 값이 여러 줄로 이루어져있다면, 출력값을 줄바꿈(`\\n`) 형식으로 작성합니다."
                          }
                        },
                        "required": [
                          "input",
                          "expected_output"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "required": [
                    "title",
                    "content",
                    "problem_info",
                    "test_cases"
                    ],
                  "additionalProperties": false
                }
                """;

        Prompt prompt = new Prompt(userMessage1,
                OpenAiChatOptions.builder()
                        .withModel("gpt-4o")
                        .withResponseFormat(new OpenAiApi.ChatCompletionRequest.ResponseFormat(OpenAiApi.ChatCompletionRequest.ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                        .build());

        ChatResponse chatResponse = this.openAiChatModel.call(prompt);
        //TODO : feign client로 code쪽 문제 정보 보내서 저장하기
        //TODO : feign client로 test case 정보 저장 요청?
        //TODO : 확인하여, front에 보내는 정보 test case 빼야하는지 확인
        return ResponseEntity.success(LLMResponseDTO.CodeGenerateResponse.of(chatResponse.getResult().getOutput().getContent()));
    }

    @Override
    public ResponseEntity<LLMResponseDTO> chat(LLMRequestDTO.chatMessage request) {
        //TODO: 코딩테스트 문제를 만들어달라는 요청을 받을 경우, 생성하지 않도록 수정
        //TODO: chat message를 받아서 OpenAI에 전달하고, 그 결과를 반환하는 메소드
        //1. 사용자의 메세지 받기
        //2. 사용자 신원 조회
        //a. 신규 사용자 : 사용자 언어 및 코테 문제 언어 받아 와서 OpenAI에 전달
        //b. 기존 사용자 : 사용자 기존 기록 가져와서 OpenAI에 전달
        //c. 기존 사용자 중

        if(request.getCodeHistoryId()==null){
//            getuserLanguage

        }


        return null;
    }

    //import static com.xeat.llmservice.OpenAI.FunctionSpec.codeContentPropertiesDetail.createCodeContentPropertiesDetail;
//import static com.xeat.llmservice.OpenAI.FunctionSpec.codeGeneratorFunctionDetail.createCodeGeneratorFunctionDetail;
//import static com.xeat.llmservice.OpenAI.FunctionSpec.codePropertiesDetail.createCodePropertiesDetail;
//import static com.xeat.llmservice.OpenAI.FunctionSpec.contentSpec.createContentSpec;
//import static com.xeat.llmservice.OpenAI.FunctionSpec.itemsSpec.createItemsSpec;
//import static com.xeat.llmservice.OpenAI.FunctionSpec.parameterSpec.createParameterSpec;
//import static com.xeat.llmservice.OpenAI.FunctionSpec.testCasePropertiesSpec.createTestCasePropertiesSpec;
//import static com.xeat.llmservice.OpenAI.FunctionSpec.testCaseSpec.createTestCaseSpec;

    //        List<OpenAIMessage> openAIMessageList = new ArrayList<>();
//        OpenAIMessage systemMessage = OpenAIMessage.createMessage("system", "난이도(1~5), 알고리즘, 기타 사항을 받아 한글로 만든 코딩테스트를 만들어준다. 모든 질문의 답은 html 태그에 담아 말하고, 적재적소에 필요한 태그를 사용한다. 단, <h2>가 가장 큰 글씨이다.  입력 예제, 출력 예제 하나를 제외한 테스트케이스는 말해주지는 않는다.");
//        OpenAIMessage userMessage = OpenAIMessage.createMessage("user", "난이도 : " + request.getDifficulty() + " " + "알고리즘 : " + request.getAlgorithm() + " " + "추가 사항 : " + etc);
//
//        openAIMessageList.add(systemMessage);
//        openAIMessageList.add(userMessage);

//        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
//                .withFunction("coding-test-generator")
//                .build();
//        ChatResponse chatResponse = this.openAiChatModel.call(new Prompt(userMessage1,
//                OpenAiChatOptions.builder().withFunction("coding-test-generator").build()));
//        Prompt prompt = new Prompt(String.valueOf(openAIMessageList), openAiChatOptions);

//        System.out.println("ai response : "+chatResponse.toString());
//        System.out.println(chatResponse.getResult().getOutput().getContent());

//        return ResponseEntity.success(LLMResponseDTO.from(chatResponse.toString()));


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
