package kr.codesquad.secondhand.api.oauth.config;

import java.util.Map;
import kr.codesquad.secondhand.api.oauth.domain.OAuthRegistration;
import kr.codesquad.secondhand.api.oauth.repository.OAuthRegistrationRepository;
import kr.codesquad.secondhand.api.oauth.util.OAuthPropertiesRegistrationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OAuthProperties.class)
@RequiredArgsConstructor
public class OAuthConfig {

    private final OAuthProperties properties;

    @Bean
    public OAuthRegistrationRepository oauthRegistrationRepository() {
        Map<String, OAuthRegistration> registrations =
                OAuthPropertiesRegistrationAdapter.getOauthRegistrations(properties);
        return new OAuthRegistrationRepository(registrations);
    }
}
