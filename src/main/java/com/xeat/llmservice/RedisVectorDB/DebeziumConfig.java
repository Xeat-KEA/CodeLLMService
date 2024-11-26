package com.xeat.llmservice.RedisVectorDB;

import io.debezium.embedded.EmbeddedEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class DebeziumConfig {
    @Bean
    public EmbeddedEngine debeziumEngine(@Value("${spring.datasource.username}") String username, @Value("${spring.datasource.password}") String password) {
        io.debezium.config.Configuration config = io.debezium.config.Configuration.create()
                .with("name", "mysql-connector")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("database.hostname", "localhost")
                .with("database.port", "3306")
                .with("database.user", username)
                .with("database.password", password)
                .with("database.server.name", "mysql_server")
                .with("database.include.list", "xeat_test")
                .with("table.include.list", "llm_history")
                .with("database.history.kafka.bootstrap.servers", "localhost:9092")
                .with("database.history.kafka.topic", "schema-changes.questions_answers")
                .build();

        return new EmbeddedEngine.BuilderImpl()
                .using(config)
                .notifying(record -> System.out.println("CDC Event: " + record))
                .build();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newSingleThreadExecutor();
    }
}
