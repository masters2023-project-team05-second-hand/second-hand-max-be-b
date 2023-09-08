package kr.codesquad.secondhand.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
