package kr.codesquad.secondhand.api.member.exception;

import kr.codesquad.secondhand.global.exception.CustomException;

public class MemberException extends CustomException {

    public MemberException(String message) {
        super(message);
    }
}
