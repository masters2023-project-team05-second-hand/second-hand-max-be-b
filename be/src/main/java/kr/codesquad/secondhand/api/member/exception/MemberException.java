package kr.codesquad.secondhand.api.member.exception;

import kr.codesquad.secondhand.global.exception.CustomRuntimeException;

public class MemberException extends CustomRuntimeException {

    public MemberException(String message) {
        super(message);
    }
}
