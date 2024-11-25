package com.xeat.llmservice.VectorDB;

import lombok.AllArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RedisBanCacheInitializer {

    private final RedisVectorStore vectorStore;

    public void init(){
        List<String> banQuestions = List.of(
                "코딩 테스트 만들어줘",
                "코딩 테스트 만들어 주세요.",
                "코딩 테스트 생성해 주세요.",
                "코딩 테스트 생성해줘",
                "문제 만들어 줘"
        );


        List<Document> documents = banQuestions.stream()
                .map(banQuestion -> new Document(banQuestion, Map.of("type", "banGeneratingQuestion")))
                .toList();

        vectorStore.add(documents);
    }
}
