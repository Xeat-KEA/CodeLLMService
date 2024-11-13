package com.xeat.llmservice.OpenAI;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OpenAIMessage {
        private String role;
        private List<Content> content;

        public static OpenAIMessage createMessage(String role, String text) {
            return OpenAIMessage.builder()
                    .role(role)
                    .content(List.of(Content.builder()
                            .type("text")
                            .text(text)
                            .build()))
                    .build();
        }

        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        public static class Content {
            private String type;
            private String text;
        }

        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        public static class toolMessage extends OpenAIMessage {
            private String toolCallId;

            public static toolMessage createExtendedMessage(String role, String text, String toolCallId) {
                return toolMessage.builder()
                        .role(role)
                        .content(List.of(Content.builder()
                                .type("text")
                                .text(text)
                                .build()))
                        .toolCallId(toolCallId)
                        .build();
            }
        }

        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        public static class assistantMessage extends OpenAIMessage {
            private toolInfo tool_calls;
            public static assistantMessage createExtendedMessage(String role, String assistantCallId, String functionName, String argument) {
                return assistantMessage.builder()
                        .role(role)
                        .content(List.of(Content.builder()
                                .type("text")
                                .text("")
                                .build()))
                        .tool_calls(toolInfo.builder()
                                .id(assistantCallId)
                                .type("function")
                                .function(List.of(functionInfo.builder()
                                        .name(functionName)
                                        .arguments(argument)
                                        .build()))
                                .build())
                        .build();
            }
        }
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        public static class toolInfo {
            private String id;
            private String type;
            private List<functionInfo> function;
        }

        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        public static class functionInfo {
            private String name;
            private String arguments;
        }


}


