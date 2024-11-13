package com.xeat.llmservice.Global;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseEntity<T> {
    private Integer statusCode;
    private String message;
    private T data;

    public static <T> ResponseEntity<T> success(T data) {
        return new ResponseEntity<>(HttpStatus.OK.value(), "OK", data);
    }

    public static <T> ResponseEntity<T> error(Integer statusCode, String message, T data) {
        return new ResponseEntity<>(statusCode, message, data);
    }
}
