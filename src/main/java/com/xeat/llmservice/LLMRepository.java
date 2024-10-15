package com.xeat.llmservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LLMRepository extends JpaRepository <LLMEntity, Long> {

}
