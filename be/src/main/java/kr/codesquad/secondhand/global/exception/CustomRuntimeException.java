package kr.codesquad.secondhand.global.exception;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {

    public CustomRuntimeException(String message) {
        super(message);
    }
}
