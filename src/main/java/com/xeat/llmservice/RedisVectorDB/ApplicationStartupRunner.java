package com.xeat.llmservice.RedisVectorDB;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApplicationStartupRunner implements CommandLineRunner {
    private final RedisWarningTextInitializer redisWarningTextInitializer;

    @Override
    public void run(String... args) {
        redisWarningTextInitializer.init();
    }
}
