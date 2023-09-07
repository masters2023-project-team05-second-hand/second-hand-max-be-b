package kr.codesquad.secondhand.api.oauth.exception;

public class InvalidOauthProviderNameException extends OAuthException {

    private static final String ERROR_MESSAGE = "존재하지 않는 Oauth Provider 입니다.";

    public InvalidOauthProviderNameException() {
        super(ERROR_MESSAGE);
    }
}
