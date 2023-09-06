package kr.codesquad.secondhand.api.member.exception;

public class MemberAddressException extends MemberException {

    private static final String ERROR_MESSAGE = "존재하지 않는 Address Id 입니다.";

    public MemberAddressException() {
        super(ERROR_MESSAGE);
    }

    public MemberAddressException(String message) {
        super(message);
    }
}
