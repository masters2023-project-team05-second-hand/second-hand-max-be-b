package kr.codesquad.secondhand.api.oauth.util;

import java.util.Map;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.oauth.config.OAuthProperties;
import kr.codesquad.secondhand.api.oauth.domain.OAuthRegistration;

/**
 * Spring Security의 OAuth2ClientPropertiesRegistrationAdapter 역할 : OauthProperties를 OauthRegistration으로 변환
 */
public class OAuthPropertiesRegistrationAdapter {

    public static Map<String, OAuthRegistration> getOauthRegistrations(OAuthProperties properties) {
        return properties.getClient().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // provider name
                        entry -> new OAuthRegistration(
                                entry.getValue(), // app-info 값(client-id, client-secret, redirect-url)
                                properties.getProvider().get(entry.getKey()) // providers map의 value 값(token-url, user-info-url)
                        )
                ));
    }
}
