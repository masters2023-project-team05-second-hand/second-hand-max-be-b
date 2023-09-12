package kr.codesquad.secondhand.api.jwt.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtExceptionType {

    DEFAULT_ERROR(HttpStatus.UNAUTHORIZED, JwtException.class, "JWT 토큰 관련 에러입니다."),
    EXPIRED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, ExpiredJwtException.class, "토큰 기한이 만료되었습니다."),
    MALFORMED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, MalformedJwtException.class, "잘못된 형식의 토큰입니다."),
    SIGNATURE_EXCEPTION(HttpStatus.UNAUTHORIZED, SignatureException.class, "JWT 서명 검증에 실패했습니다."),
    TOKEN_NOT_FOUND_EXCEPTION(HttpStatus.UNAUTHORIZED, TokenNotFoundException.class, "헤더에 Jwt 토큰이 없습니다."),
    BLACKLISTED_ACCESS_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, BlackListedAccessTokenException.class, "로그아웃한 사용자입니다.");

    private final HttpStatus httpStatus;
    private final Class<? extends Exception> originException;
    private final String message;

    public static JwtExceptionType from(Exception e) {
        return Arrays.stream(values())
                .filter(jwtExceptionType -> jwtExceptionType.originException.equals(e.getClass()))
                .findAny()
                .orElse(JwtExceptionType.DEFAULT_ERROR);
    }
}
