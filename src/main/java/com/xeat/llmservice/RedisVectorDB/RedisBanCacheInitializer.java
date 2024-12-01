package com.xeat.llmservice.RedisVectorDB;

import lombok.AllArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RedisBanCacheInitializer {

    private final VectorStore vectorStore;
    private final List<String> BAN_QUESTIONS = List.of(
            "코딩 테스트 만들어줘",
            "코딩 테스트 만들어 주세요.",
            "코딩 테스트 생성해 주세요.",
            "코딩 테스트 생성해줘",
            "문제 만들어 줘"
    );
    public void init(){

//        String firstQuestion = BAN_QUESTIONS.get(0);
//        List<Document> existingDocs = vectorStore.similaritySearch(SearchRequest.query(firstQuestion));
//
//        if (!existingDocs.isEmpty()) {
//            System.out.println("Ban cache already initialized. Skipping initialization.");
//            return;
//        }

        List<Document> documents = BAN_QUESTIONS.stream()
                .map(banQuestion -> new Document(banQuestion, Map.of("type", "banGeneratingQuestion")))
                .toList();
        vectorStore.add(documents);
    }
}
