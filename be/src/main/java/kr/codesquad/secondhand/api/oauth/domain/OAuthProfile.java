package kr.codesquad.secondhand.api.oauth.domain;

import java.util.Arrays;
import java.util.Map;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.oauth.exception.InvalidOauthProviderNameException;
import lombok.Builder;
import lombok.Getter;

/**
 * Oauth 리소스 서버의 유저 정보와 바인딩되는 객체
 */
@Getter
public class OAuthProfile {

    private final String email;
    private final String name;
    private final String imageUrl;

    @Builder
    public OAuthProfile(String email, String name, String imageUrl) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static OAuthProfile of(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(OAuthAttributes.values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findFirst()
                .orElseThrow(InvalidOauthProviderNameException::new)
                .of(attributes);
    }

    public Member toMember(OAuthAttributes oAuthAttributes) {
        return Member.builder()
                .oAuthAttributes(oAuthAttributes)
                .email(email)
                .nickname(name)
                .profileImgUrl(imageUrl)
                .build();
    }
}
