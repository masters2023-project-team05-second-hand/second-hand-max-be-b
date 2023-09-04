package kr.codesquad.secondhand.api.member.dto;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.api.member.domain.Address;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import lombok.Getter;

@Getter
public class OAuthSignInResponse {

    private final Jwt tokens;

    public OAuthSignInResponse(Jwt tokens) {
        this.tokens = tokens;
    }

    public static OAuthSignInResponse from(Jwt jwt) {
        return new OAuthSignInResponse(jwt);
    }
}
