package kr.codesquad.secondhand.api.jwt.exception;

import io.jsonwebtoken.JwtException;

public class TokenNotFoundException extends JwtException {

    public static final String ERROR_MESSAGE = "헤더에 Jwt 토큰이 없습니다.";

    public TokenNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
