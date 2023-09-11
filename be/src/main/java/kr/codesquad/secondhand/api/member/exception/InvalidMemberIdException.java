package kr.codesquad.secondhand.api.member.exception;

public class InvalidMemberIdException extends MemberException {

    private static final String ERROR_MESSAGE = "존재하지 않거나 잘못된 회원 ID입니다.";

    public InvalidMemberIdException(String message) {
        super(message);
    }

    public InvalidMemberIdException() {
        super(ERROR_MESSAGE);
    }
}
