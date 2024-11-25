package com.xeat.llmservice.VectorDB;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApplicationStartupRunner implements CommandLineRunner {
    private final RedisBanCacheInitializer redisBanCacheInitializer;

    @Override
    public void run(String... args) {
        redisBanCacheInitializer.init();
    }
}
