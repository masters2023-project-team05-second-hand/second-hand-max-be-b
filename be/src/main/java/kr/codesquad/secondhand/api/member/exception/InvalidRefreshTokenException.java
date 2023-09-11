package kr.codesquad.secondhand.api.member.exception;

public class InvalidRefreshTokenException extends MemberException {

    private static final String ERROR_MESSAGE = "회원의 Refresh Token 정보와 일치하지 않습니다.";

    public InvalidRefreshTokenException() {
        super(ERROR_MESSAGE);
    }
}
