package kr.codesquad.secondhand.api.oauth.exception;

import kr.codesquad.secondhand.global.exception.CustomException;

public class OAuthException extends CustomException {

    public OAuthException(String message) {
        super(message);
    }
}
