package com.xeat.llmservice.Repository;

import com.xeat.llmservice.Entity.LLMHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LLMHistoryRepository extends JpaRepository<LLMHistoryEntity, Long> {
}
