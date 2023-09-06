package kr.codesquad.secondhand.api.oauth.domain;

import java.util.Arrays;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Oauth 서버별 OauthProfile 정보를 갖고 있는 Enum 클래스
 */
@Getter
@RequiredArgsConstructor
public enum OAuthAttributes {

    GITHUB(1L, "github") {
        @Override
        public OAuthProfile of(Map<String, Object> attributes) {
            return OAuthProfile.builder()
                    .email((String) attributes.get("email"))
                    .name((String) attributes.get("login"))
                    .imageUrl((String) attributes.get("avatar_url"))
                    .build();
        }
    },
    KAKAO(2L, "kakao") {
        @Override
        public OAuthProfile of(Map<String, Object> attributes) {
            Map<String, Object> info = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) info.get("profile");
            return OAuthProfile.builder()
                    .email((String) info.get("email"))
                    .name((String) profile.get("nickname"))
                    .imageUrl((String) profile.get("profile_image_url"))
                    .build();
        }
    };

    public final Long id;
    public final String providerName;

    public static boolean isGithub(String providerName) {
        return GITHUB.providerName.equals(providerName);
    }

    public static OAuthAttributes from(String providerName) {
        return Arrays.stream(values())
                .filter(oAuthAttribute -> oAuthAttribute.providerName.equals(providerName))
                .findAny()
                .orElseThrow();
    }

    public abstract OAuthProfile of(Map<String, Object> attributes);
}
