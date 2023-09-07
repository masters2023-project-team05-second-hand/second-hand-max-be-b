package kr.codesquad.secondhand.api.member.exception;

public class InvalidMemberAddressIdException extends MemberAddressException {

    private static final String ERROR_MESSAGE = "회원 정보에 없는 Address Id 이거나 존재하지 않는 Address Id 입니다. ";

    public InvalidMemberAddressIdException() {
        super(ERROR_MESSAGE);
    }
}
