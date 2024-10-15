package com.xeat.llmservice;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class LLMServiceImpl implements LLMService{
    private final LLMRepository llmRepository;

}
