package kr.codesquad.secondhand.api.jwt.exception;

import kr.codesquad.secondhand.global.exception.CustomRuntimeException;

public class ExpiredRefreshTokenException extends CustomRuntimeException {

    public static final String ERROR_MESSAGE = "Refresh token이 만료되었습니다.";

    public ExpiredRefreshTokenException() {
        super(ERROR_MESSAGE);
    }

    public ExpiredRefreshTokenException(String message) {
        super(message);
    }
}
