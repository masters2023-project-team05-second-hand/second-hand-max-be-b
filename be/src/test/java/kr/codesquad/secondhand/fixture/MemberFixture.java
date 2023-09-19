package kr.codesquad.secondhand.fixture;

import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.oauth.domain.OAuthAttributes;
import kr.codesquad.secondhand.api.oauth.domain.OAuthProfile;

public enum MemberFixture {

    멤버_지니(
            OAuthAttributes.GITHUB,
            "jinny@test.com",
            "지니",
            "https://s3.ap-northeast-2.amazonaws.com/gamgyul-bucket/public/76696703-fae1-4bcf-98ef-84b64df847f4"
    ),
    멤버_감귤(OAuthAttributes.KAKAO,
            "gamgyul@test.com",
            "감귤",
            "https://s3.ap-northeast-2.amazonaws.com/gamgyul-bucket/public/76696703-fae1-4bcf-98ef-84b64df847f4"
    );

    private final OAuthAttributes oAuthAttributes;
    private final String email;
    private final String nickname;
    private final String profileImgUrl;

    MemberFixture(OAuthAttributes oAuthAttributes, String email, String nickname, String profileImgUrl) {
        this.oAuthAttributes = oAuthAttributes;
        this.email = email;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public OAuthProfile toOauthProfile() {
        return new OAuthProfile(email, nickname, profileImgUrl);
    }

    public Member toMember() {
        return Member.builder()
                .oAuthAttributes(oAuthAttributes)
                .email(email)
                .nickname(nickname)
                .profileImgUrl(profileImgUrl)
                .build();
    }

    public OAuthAttributes getoAuthAttributes() {
        return oAuthAttributes;
    }

}
