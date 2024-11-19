package com.xeat.llmservice.Global;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Schema(name = "ResponseEntity", description = "전체 API 응답", title = "ResponseEntity [전체 API 응답]")
public class ResponseEntity<T> {
    @Schema(name = "statusCode", description = "응답 코드", example = "200")
    private Integer statusCode;
    @Schema(name = "message", description = "응답 메시지", example = "OK")
    private String message;
    @Schema(name = "data", description = "응답 데이터")
    private T data;

    public static <T> ResponseEntity<T> success(T data) {
        return new ResponseEntity<>(HttpStatus.OK.value(), "OK", data);
    }

    public static <T> ResponseEntity<T> error(Integer statusCode, String message, T data) {
        return new ResponseEntity<>(statusCode, message, data);
    }
}
