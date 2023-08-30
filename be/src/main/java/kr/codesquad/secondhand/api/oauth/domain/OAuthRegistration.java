package kr.codesquad.secondhand.api.oauth.domain;

import kr.codesquad.secondhand.api.oauth.config.OAuthProperties;
import kr.codesquad.secondhand.api.oauth.config.OAuthProperties.Client;
import lombok.Builder;
import lombok.Getter;

/**
 * OauthProperties의 app-info와 provider의 provider별로 필드를 합친 클래스
 */
@Getter
public class OAuthRegistration {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String tokenRequestUri;
    private final String userInfoRequestUri;

    public OAuthRegistration(Client client, OAuthProperties.Provider provider) {
        this(client.getClientId(), client.getClientSecret(), client.getRedirectUri(),
                provider.getTokenRequestUri(), provider.getUserInfoRequestUri()
        );
    }

    @Builder
    public OAuthRegistration(String clientId, String clientSecret, String redirectUri,
                             String tokenUri, String userInfoUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.tokenRequestUri = tokenUri;
        this.userInfoRequestUri = userInfoUri;
    }

}
