package kr.codesquad.secondhand.global.exception;

import java.util.List;
import lombok.Getter;

@Getter
public class ErrorResponseEntity {

    private final String message;

    public ErrorResponseEntity(String message) {
        this.message = "[ERROR] " + message;
    }

    public ErrorResponseEntity(List<String> messages) {
        this.message = "[ERROR] " + String.join("; ", messages);
    }
}
