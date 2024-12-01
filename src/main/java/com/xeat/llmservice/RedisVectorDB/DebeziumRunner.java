//package com.xeat.llmservice.RedisVectorDB;
//
//import io.debezium.embedded.EmbeddedEngine;
//import lombok.AllArgsConstructor;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.ExecutorService;
//
//@Component
//@AllArgsConstructor
//public class DebeziumRunner {
//    private final EmbeddedEngine debeziumEngine;
//    private final ExecutorService executorService;
//
//    @EventListener(ContextRefreshedEvent.class)
//    public void startDebezium() {
//        executorService.execute(debeziumEngine);
//    }
//
//}
