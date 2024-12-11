package com.xeat.llmservice.Repository;

import com.xeat.llmservice.Entity.LLMEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LLMRepository extends JpaRepository <LLMEntity, Long> {
    boolean existsByCodeHistoryId(Integer codeHistoryId);
    LLMEntity findByCodeHistoryId(Integer codeHistoryId);

    boolean existsByUserId(String userId);
}
