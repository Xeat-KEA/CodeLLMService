package com.xeat.llmservice.Service;

import com.xeat.llmservice.Client.CodeBankClient;
import com.xeat.llmservice.Client.ClientResponseDTO;
import com.xeat.llmservice.DTO.LLMRequestDTO;
import com.xeat.llmservice.DTO.LLMResponseDTO;
import com.xeat.llmservice.Entity.LLMEntity;
import com.xeat.llmservice.Entity.LLMHistoryEntity;
import com.xeat.llmservice.Global.ResponseCustomEntity;
import com.xeat.llmservice.RedisVectorDB.RedisWarningTextInitializer;
import com.xeat.llmservice.Repository.LLMHistoryRepository;
import com.xeat.llmservice.Repository.LLMRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LLMServiceImpl implements LLMService {
    private final LLMRepository llmRepository;
    private final LLMHistoryRepository llmHistoryRepository;
    private final OpenAiChatModel openAiChatModel;
    private final RedisVectorStore redisVectorStore;
    private final CodeBankClient codeBankClient;

    @Override
    public ResponseCustomEntity<LLMResponseDTO.CodeGenerateClientResponse> codeGenerator(String userId, LLMRequestDTO.codeGeneratingInfo request) {
        String etc = request.getEtc() != null ? request.getEtc() : "null";
        UserMessage userMessage1 = new UserMessage("난이도 : " + request.getDifficulty() + " " + "알고리즘 : " + request.getAlgorithm() + " " + "추가 사항 : " + etc);
        String jsonSchema = """
                {
                  "type": "object",
                  "properties": {
                    "title": {
                      "type": "string",
                      "description": "코딩 테스트 문제의 제목을 나타냅니다. 문제의 핵심 주제나 목적을 간결히 나타내는 한글 문구(must be)여야 합니다."
                    },
                    "content": {
                      "type": "string",
                      "description": "문제 설명을 HTML 형식으로 작성합니다. 문제 설명에는 다음 요소들이 포함되어야 합니다: \\n\\n- **문제 설명:** 문제의 배경과 목표를 설명 \\n- **문제 제한 사항:** 문제 풀이 조건을 기술(필요시 null 가능) \\n- **입력 예시:** 문제 풀이를 위한 예제 입력값,  \\n- **출력 예시:** 예제 입력에 대한 출력값 \\n- **입출력 예 설명:** 예제 입력과 출력의 관계를 설명 \\n\\nHTML 작성 시 다음 규칙을 지켜야 합니다: \\n1. 문단 구분 시 `\\n`과 `<br>`을 적절히 활용합니다. \\n2. 강조가 필요한 제목은 `<h3>`를 사용합니다. \\n3. 일반 텍스트는 `<p>` 태그에 포함합니다. \\n4. 목록은 `<ul><li>`, 코드 예시는 `<pre><code>`로 감싸 작성합니다.\\n\\n입출력 예시의 형식은 다음을 준수해야 합니다: \\n1. 입력값, 출력값은 각 항목을 줄바꿈(`\\n`)을 이용해 나타냅니다. 예를 들어, 그래프의 노드와 간선을 입력받는 경우 다음과 같은 형식을 사용합니다: \\n```\\n6\\n0 1 5\\n0 2 10\\n1 3 8\\n2 3 2\\n3 4 5\\n4 5 2\\n``` \\n2. 출력값은 필요한 경우 문장 대신 값만 포함합니다. \\n 3. 입출력(테스트케이스)은 항상 세가지를 담아야합니다."
                    },
                    "content_test_cases": {
                      "type": "array",
                      "description": "문제 설명에 들어간 입력 예시와 출력 예시에 대한 테스트케이스 세개 전부를 담습니다.  \\n\\n테스트 케이스 입력값과 출력값은 각 줄마다 하나의 정보를 포함하며, 줄바꿈(`\\n`)을 이용하여 여러 줄로 나타냅니다. 예를 들어, 그래프 문제의 입력값은 다음 형식을 따릅니다: \\n```\\n6\\n0 1 5\\n0 2 10\\n1 3 8\\n2 3 2\\n3 4 5\\n4 5 2\\n``` \\n출력값은 다음과 같이 나타낼 수 있습니다: \\n```\\n15\\n0 1 3 4 5\\n```",
                      "items": {
                        "type": "object",
                        "properties": {
                          "input": {
                            "type": "string",
                            "description": "테스트 케이스의 입력 값입니다. 각 줄에 하나의 정보를 포함하며, 여러 줄로 이루어진 데이터를 줄바꿈(`\\n`) 형식으로 작성합니다."
                          },
                          "output": {
                            "type": "string",
                            "description": "해당 입력값에 대한 예상 출력값입니다. 어떠한 기호를 절대 사용하지 않으며, 출력 값이 여러 줄로 이루어져있다면, 출력값을 줄바꿈(`\\n`) 형식으로 작성합니다."
                          }
                        },
                        "required": [
                          "input",
                          "output"
                        ],
                        "additionalProperties": false
                      }
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
                            "description": "문제의 난이도를 LEVEL1~LEVEL5 사이의 레벨로 나타냅니다. (LEVEL1: 가장 쉬움, LEVEL5: 매우 어려움)"
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
                      "description": "테스트 케이스 배열로 문제 풀이 검증을 위한 입력과 출력 정보를 담습니다. 본문에 포함된 테스트케이스를 제외한 입력값과 출력값 쌍의 개수는 문제의 모든 경우의 수를 검증하는 최소의 수 입니다.  \\n\\n테스트 케이스 입력값과 출력값은 각 줄마다 하나의 정보를 포함하며, 줄바꿈(`\\n`)을 이용하여 여러 줄로 나타냅니다. 예를 들어, 그래프 문제의 입력값은 다음 형식을 따릅니다: \\n```\\n6\\n0 1 5\\n0 2 10\\n1 3 8\\n2 3 2\\n3 4 5\\n4 5 2\\n``` \\n출력값은 다음과 같이 나타낼 수 있습니다: \\n```\\n15\\n0 1 3 4 5\\n```",
                      "items": {
                        "type": "object",
                        "properties": {
                          "input": {
                            "type": "string",
                            "description": "테스트 케이스의 입력값입니다. 각 줄에 하나의 정보를 포함하며, 여러 줄로 이루어진 데이터를 줄바꿈(`\\n`) 형식으로 작성합니다."
                          },
                          "output": {
                            "type": "string",
                            "description": "해당 입력값에 대한 예상 출력값입니다. 어떠한 기호를 절대 사용하지 않으며, 출력 값이 여러 줄로 이루어져있다면, 출력값을 줄바꿈(`\\n`) 형식으로 작성합니다."
                          }
                        },
                        "required": [
                          "input",
                          "output"
                        ],
                        "additionalProperties": false
                      }
                    }
                  },
                  "required": [
                    "title",
                    "content",
                    "problem_info",
                    "content_test_cases",
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
        LLMResponseDTO.CodeGenerateResponse sendToCodeFeignClient = LLMResponseDTO.CodeGenerateResponse.of(chatResponse.getResult().getOutput().getContent());
        ResponseEntity<ClientResponseDTO> response = codeBankClient.createCodeId(sendToCodeFeignClient, userId);

        ClientResponseDTO data = response.getBody();
        assert data != null;
        return ResponseCustomEntity.success(LLMResponseDTO.CodeGenerateClientResponse.builder()
                .codeId(data.getCodeId())
                .build());
    }

    @Override
    public ResponseCustomEntity<LLMResponseDTO.CodeQuestionClientResponse> chatIncludeAnswer(String userId, LLMRequestDTO.chatMessage request) {
        if(banQuestionChecker(request.getChatMessage() , RedisWarningTextInitializer.REMOVE_PROMPT.getType())) {
            return ResponseCustomEntity.error(400, "금지된 질문입니다.", null);

        }

        List<Message> systemMessages;
        if(!llmRepository.existsByUserId(userId)){
            systemMessages = List.of(
                    new SystemMessage("문제 정보 : " + request.getCodingTestContent()),
                    new SystemMessage("사용자 기본 언어 : " + request.getCodeLanguage())
            );
        }else{
            systemMessages = processQuestion(userId, request.getChatMessage());
        }


        ChatResponse chatResponse = ChatClient.builder(openAiChatModel)
                .defaultSystem("코딩테스트에 대한 질문을 응답해주는 친절한 챗봇입니다. 답변은 코딩테스트에 대한 답을 포함할 수 있습니다. 항상 답변은 HTML 태그에 담아 보내며, 답변 작성 시 다음 규칙을 지켜야 합니다: \\n1. 문단 구분 시 `\\n`과 `<br>`을 적절히 활용합니다. \\n2. 강조가 필요한 제목은 `<h3>`를 사용합니다. \\n3. 일반 텍스트는 `<p>` 태그에 포함합니다. \\n4. 목록은 `<ul><li>`, 코드 예시는 `<pre><code>`로 감싸 작성합니다.")
                .build()
                .prompt(new Prompt(systemMessages))
                .user(request.getChatMessage())
                .call()
                .chatResponse();


        String answer = chatResponse.getResult().getOutput().getContent();

        if(banQuestionChecker(request.getChatMessage() , RedisWarningTextInitializer.BAN_QUESTIONS.getType())) {
            answer += "<warning>질의응답으로 생성된 코딩테스트 문제는 실행해볼 수 없습니다.</warning><br><br>";
        }

        LLMHistoryEntity history = llmHistoryRepository.save(LLMRequestDTO.LLMHistoryDTO.toEntity(LLMRequestDTO.LLMHistoryDTO.builder()
                .chatHistoryId(request.getCodeHistoryId())
                .question(request.getChatMessage())
                .answer(answer)
                .llmEntity(llmRepository.findByCodeHistoryId(request.getCodeHistoryId()))
                .build()));


        return ResponseCustomEntity.success(LLMResponseDTO.CodeQuestionClientResponse.of(history.getAnswer()));
    }

    @Override
    public ResponseCustomEntity<LLMResponseDTO.CodeQuestionClientResponse> chatJustGuidance(String userId, LLMRequestDTO.chatMessage request) {
        if(banQuestionChecker(request.getChatMessage() , RedisWarningTextInitializer.REMOVE_PROMPT.getType())) {
            return ResponseCustomEntity.error(400, "금지된 질문입니다.", null);

        }

        List<Message> systemMessages;
        if(!llmRepository.existsByUserId(userId)){
            systemMessages = List.of(
                    new SystemMessage("문제 정보 : " + request.getCodingTestContent()),
                    new SystemMessage("사용자 기본 언어 : " + request.getCodeLanguage())
            );
        }else{
            systemMessages = processQuestion(userId, request.getChatMessage());
        }


        ChatResponse chatResponse = ChatClient.builder(openAiChatModel)
                .defaultSystem("코딩테스트에 대한 질문을 응답해주는 친절한 챗봇입니다. 답변은 코딩테스트에 대한 답을 절대 포함할 수 없으며, 오로지 힌트만 제공해야 합니다. 항상 답변은 HTML 태그에 담아 보내며, 답변 작성 시 다음 규칙을 지켜야 합니다: \\n1. 문단 구분 시 `\\n`과 `<br>`을 적절히 활용합니다. \\n2. 강조가 필요한 제목은 `<h3>`를 사용합니다. \\n3. 일반 텍스트는 `<p>` 태그에 포함합니다. \\n4. 목록은 `<ul><li>`, 코드 예시는 `<pre><code>`로 감싸 작성합니다.")
                .build()
                .prompt(new Prompt(systemMessages))
                .user(request.getChatMessage())
                .call()
                .chatResponse();


        String answer = chatResponse.getResult().getOutput().getContent();

        if(banQuestionChecker(request.getChatMessage() , RedisWarningTextInitializer.BAN_QUESTIONS.getType())) {
            answer += "<warning>질의응답으로 생성된 코딩테스트 문제는 실행해볼 수 없습니다.</warning><br><br>";
        }

        LLMHistoryEntity history = llmHistoryRepository.save(LLMRequestDTO.LLMHistoryDTO.toEntity(LLMRequestDTO.LLMHistoryDTO.builder()
                .chatHistoryId(request.getCodeHistoryId())
                .question(request.getChatMessage())
                .answer(answer)
                .llmEntity(llmRepository.findByCodeHistoryId(request.getCodeHistoryId()))
                .build()));


        return ResponseCustomEntity.success(LLMResponseDTO.CodeQuestionClientResponse.of(history.getAnswer()));
    }

    @Override
    public ResponseCustomEntity<LLMResponseDTO.ChatResponseList> chatPagedHistory(String userId, Long codeHistoryId, Integer page) {
        if(!llmRepository.existsByCodeHistoryId(codeHistoryId)){
            return ResponseCustomEntity.error(400, "해당 코딩테스트 ID에 대한 채팅 기록이 없습니다.", null);
        }

        if(!llmRepository.existsByUserId(userId)){
            return ResponseCustomEntity.error(400, "해당 유저 ID에 대한 채팅 기록이 없습니다.", null);
        }

        Pageable pageable = PageRequest.ofSize(6).withSort(Sort.Direction.DESC, "chatHistoryId").withPage(page);
        Page<LLMHistoryEntity> llmHistoryEntities = llmHistoryRepository.findAllByLlmEntity_CodeHistoryId(codeHistoryId, pageable);
        List<LLMResponseDTO.ChatResponse> chatList = llmHistoryEntities.getContent().stream()
                .map(LLMResponseDTO.ChatResponse::of)
                .toList();
        return ResponseCustomEntity.success(LLMResponseDTO.ChatResponseList.toChatResponseList(llmHistoryEntities, chatList));
    }

    private boolean banQuestionChecker(String chatMessage, String questionType) {
        return !redisVectorStore.similaritySearch(
                SearchRequest.query(chatMessage)
                        .withFilterExpression(
                                new Filter.Expression(
                                    Filter.ExpressionType.EQ,
                                    new Filter.Key("type"),
                                    new Filter.Value(questionType)))
                        .withSimilarityThreshold(0.9f)
        ).isEmpty();
    }

    public List<Message> processQuestion(String userId, String question) {
        List<Document> similarQuestions = redisVectorStore.similaritySearch(
                SearchRequest.query(question)
                        .withFilterExpression(
                                new Filter.Expression(
                                        Filter.ExpressionType.EQ,
                                        new Filter.Key("userId"),
                                        new Filter.Value(userId)
                                )
                        )
                        .withTopK(2)
        );


        // 유사한 질문 2개 추출
        String mostSimilar = !similarQuestions.isEmpty() ? similarQuestions.get(0).getContent() : "현재 질문과 가장 유사한 질문이 아직 없음";
        String secondMostSimilar = similarQuestions.size() > 1 ? similarQuestions.get(1).getContent() : "현재 질문과 두번째로 유사한 질문이 아직 없음";

        // ChatGPT 전달 데이터 구성
        SystemMessage mostSimilarMessage = new SystemMessage("가장 유사한 질문: " + mostSimilar);
        SystemMessage secondMostSimilarMessage = new SystemMessage("두 번째로 유사한 질문: " + secondMostSimilar);

        return List.of(mostSimilarMessage, secondMostSimilarMessage);
    }
}