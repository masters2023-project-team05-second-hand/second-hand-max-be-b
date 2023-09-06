package kr.codesquad.secondhand.api.member.service;

import java.util.List;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.address.service.AddressService;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.api.jwt.service.JwtService;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.dto.response.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.dto.response.OAuthSignInResponse;
import kr.codesquad.secondhand.api.oauth.domain.OAuthProfile;
import kr.codesquad.secondhand.api.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberFacadeService {

    private final MemberService memberService;
    private final OAuthService oAuthService;
    private final JwtService jwtService;
    private final MemberAddressService memberAddressService;
    private final AddressService addressService;

    @Transactional
    public OAuthSignInResponse login(String providerName, String authorizationCode) {
        OAuthProfile oAuthProfile = oAuthService.requestOauthProfile(providerName, authorizationCode);
        Long memberId = memberService.oAuthLogin(providerName, oAuthProfile);
        Jwt tokens = jwtService.issueJwt(memberId);
        return OAuthSignInResponse.from(tokens);
    }

    @Transactional
    public List<MemberAddressResponse> updateMemberAddresses(Long memberId, List<Long> addressIds) {
        List<Address> addresses = addressService.findAddressesByIds(addressIds);
        Member member = memberService.getMemberReferenceById(memberId);
        return memberAddressService.deleteAndUpdateMemberAddresses(member, addresses);
    }
}
