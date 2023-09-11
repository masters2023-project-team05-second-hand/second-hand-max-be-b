package kr.codesquad.secondhand.api.jwt.exception;

import io.jsonwebtoken.JwtException;

public class BlackListedAccessTokenException extends JwtException {

    public static final String ERROR_MESSAGE = "로그아웃한 사용자입니다.";

    public BlackListedAccessTokenException() {
        super(ERROR_MESSAGE);
    }
}
