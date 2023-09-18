package kr.codesquad.secondhand.api.member.service;

import java.util.List;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.address.service.AddressService;
import kr.codesquad.secondhand.api.image.service.ImageService;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.api.jwt.service.JwtService;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.dto.MemberProfileImgUpdateDto;
import kr.codesquad.secondhand.api.member.dto.request.MemberNicknameUpdateRequest;
import kr.codesquad.secondhand.api.member.dto.response.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.dto.response.OAuthSignInResponse;
import kr.codesquad.secondhand.api.member.exception.InvalidRefreshTokenException;
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
    private final ImageService imageService;

    @Transactional
    public OAuthSignInResponse signIn(String providerName, String authorizationCode) {
        OAuthProfile oAuthProfile = oAuthService.requestOauthProfile(providerName, authorizationCode);
        Long memberId = memberService.oAuthLogin(providerName, oAuthProfile);
        Jwt tokens = jwtService.issueJwt(memberId);
        return OAuthSignInResponse.from(tokens);
    }

    @Transactional
    public void signOut(Long memberId, String accessToken, String refreshToken) {
        if (!jwtService.isMemberRefreshToken(memberId, refreshToken)) {
            throw new InvalidRefreshTokenException();
        }
        jwtService.deleteRefreshToken(memberId);
        jwtService.addAccessTokenToBlackList(accessToken);
    }

    @Transactional
    public List<MemberAddressResponse> updateMemberAddresses(Long memberId, List<Long> addressIds) {
        List<Address> addresses = addressService.findAddressesByIds(addressIds);
        Member member = memberService.getMemberReferenceById(memberId);
        return memberAddressService.deleteAndUpdateMemberAddresses(member, addresses);
    }

    @Transactional
    public MemberProfileImgUpdateDto.Response updateMemberProfileImg(Long memberId,
                                                                     MemberProfileImgUpdateDto.Request request) {
        String newImageUrl = imageService.uploadSingleImageToS3(request.getNewProfileImg())
                .toString();
        memberService.updateMemberProfileImg(memberId, newImageUrl);
        return new MemberProfileImgUpdateDto.Response(newImageUrl);
    }

    @Transactional
    public void updateMemberNickname(Long memberId, MemberNicknameUpdateRequest request) {
        memberService.updateMemberNickname(memberId, request.getNewNickname());
    }
}
