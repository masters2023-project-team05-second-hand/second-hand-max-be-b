package kr.codesquad.secondhand.api.member.service;

import java.util.Optional;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.api.jwt.service.JwtService;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.domain.SignInType;
import kr.codesquad.secondhand.api.member.dto.OAuthSignInResponse;
import kr.codesquad.secondhand.api.member.repository.MemberAddressRepositoryImpl;
import kr.codesquad.secondhand.api.member.repository.MemberRepository;
import kr.codesquad.secondhand.api.member.repository.SignInTypeRepositoryImpl;
import kr.codesquad.secondhand.api.oauth.domain.OAuthProfile;
import kr.codesquad.secondhand.api.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final OAuthService oAuthService;
    private final JwtService jwtService;

    private final SignInTypeRepositoryImpl signInTypeRepository;
    private final MemberRepository memberRepository;
    private final MemberAddressRepositoryImpl memberAddressRepository;

    @Transactional
    public OAuthSignInResponse signInOrSignUp(String providerName, String authorizationCode) {
        OAuthProfile oAuthProfile = oAuthService.requestOauthProfile(providerName, authorizationCode);
        SignInType signInType = signInTypeRepository.findByProvider(
                providerName); // TODO: 메모리 저장소에 캐싱해두고 사용할 수 있게 개선하는 방법 고민중
        Member member = oAuthProfile.toMember(signInType);

        // DB에 회원 저장 유무 판단
        Optional<Member> findMember = memberRepository.findBySignInTypeIdAndEmail(
                member.getSignInType().getId(),
                member.getEmail()
        );

        if (findMember.isEmpty()) {
            memberRepository.save(member);
            return signIn(member);
        }
        return signIn(findMember.get());
    }

    private OAuthSignInResponse signIn(Member member) {
        Jwt jwt = jwtService.issueJwt(member.getId());
        return OAuthSignInResponse.from(jwt);
    }

    public Member getMemberReferenceById(Long memberId) {
        return memberRepository.getReferenceById(memberId);
    }
}
