package kr.codesquad.secondhand.api.oauth.domain;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Oauth 서버별 OauthProfile 정보를 갖고 있는 Enum 클래스
 */
@RequiredArgsConstructor
public enum OAuthAttributes {

    GITHUB("github") {
        @Override
        public OAuthProfile of(Map<String, Object> attributes) {
            return OAuthProfile.builder()
                    .email((String) attributes.get("email"))
                    .name((String) attributes.get("login"))
                    .imageUrl((String) attributes.get("avatar_url"))
                    .build();
        }
    },
    KAKAO("kakao") {
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

    @Getter
    public final String providerName;

    public abstract OAuthProfile of(Map<String, Object> attributes);
}
