package kr.codesquad.secondhand.api.member.exception;

public class NotSignedInException extends MemberException {

    private static final String ERROR_MESSAGE = "로그인하지 않은 사용자입니다.";

    public NotSignedInException() {
        super(ERROR_MESSAGE);
    }
}
