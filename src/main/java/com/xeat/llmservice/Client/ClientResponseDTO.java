package com.xeat.llmservice.Client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientResponseDTO {
    private Long codeHistoryId;
    private Long codeId;
}
