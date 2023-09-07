package kr.codesquad.secondhand.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponseEntity {

    private final String message;

    public ErrorResponseEntity(String message) {
        this.message = "[ERROR] " + message;
    }
}
