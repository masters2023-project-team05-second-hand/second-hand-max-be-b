package kr.codesquad.secondhand.oauth.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import kr.codesquad.secondhand.oauth.domain.OAuthProfile;
import kr.codesquad.secondhand.oauth.domain.OAuthRegistration;
import kr.codesquad.secondhand.oauth.dto.OAuthTokenResponse;
import kr.codesquad.secondhand.oauth.repository.OAuthRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final OAuthRegistrationRepository oauthRegistrationRepository;

    public OAuthProfile requestOauthProfile(String providerName, String authorizationCode) {
        // repository에서 providerName과 일치하는 registration 가져오기
        OAuthRegistration registration = oauthRegistrationRepository.findByProviderName(providerName);

        // Oauth 인증 서버에 token 요청
        OAuthTokenResponse tokenResponse = requestToken(authorizationCode, registration);

        // Oauth 리소스 서버에 유저 정보 요청
        Map<String, Object> attributes = requestOauthAttributes(registration, tokenResponse);

        // TODO github의 경우, Private email을 받아오기 위한 메소드 추가 필요
        if (providerName.equals("github")) {
            attributes.put("email", getGithubPrivateEmail(tokenResponse).get(0).get("email"));
        }
        return OAuthProfile.of(providerName, attributes);
    }

    /**
     * Oauth 인증 서버에 token을 요청하는 메서드
     */
    private OAuthTokenResponse requestToken(String authorizationCode, OAuthRegistration registration) {
        return WebClient.create()
                .post()
                .uri(registration.getTokenRequestUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequestBodyData(authorizationCode, registration))
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> tokenRequestBodyData(String authorizationCode, OAuthRegistration provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", authorizationCode);
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", provider.getClientId());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("redirect_uri", provider.getRedirectUri());
        return formData;
    }

    /**
     * OAuth 리소스 서버에 유저 정보를 요청하는 메서드
     */
    private Map<String, Object> requestOauthAttributes(OAuthRegistration registration,
                                                       OAuthTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri(registration.getUserInfoRequestUri())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

    public List<Map<String, Object>> getGithubPrivateEmail(OAuthTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri("https://api.github.com/user/emails")
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                })
                .block();
    }
}
