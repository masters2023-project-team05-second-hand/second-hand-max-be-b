package kr.codesquad.secondhand.api.oauth.exception;

import kr.codesquad.secondhand.global.exception.CustomRuntimeException;

public class OAuthException extends CustomRuntimeException {

    public OAuthException(String message) {
        super(message);
    }
}
