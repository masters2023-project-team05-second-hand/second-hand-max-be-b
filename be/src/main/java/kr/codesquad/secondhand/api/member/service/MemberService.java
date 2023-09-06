package kr.codesquad.secondhand.api.member.service;

import java.util.Optional;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.repository.MemberRepository;
import kr.codesquad.secondhand.api.oauth.domain.OAuthAttributes;
import kr.codesquad.secondhand.api.oauth.domain.OAuthProfile;
import kr.codesquad.secondhand.api.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final OAuthService oAuthService;

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member getMemberReferenceById(Long memberId) {
        return memberRepository.getReferenceById(memberId);
    }

    public Member oAuthLogin(String providerName, String authorizationCode) {
        OAuthProfile oAuthProfile = oAuthService.requestOauthProfile(providerName, authorizationCode);
        OAuthAttributes oAuthAttributes = OAuthAttributes.from(providerName);
        return oAuthProfile.toMember(oAuthAttributes);
    }

    public Boolean isExistMember(Long signInType, String email) {
        Optional<Member> findMember = memberRepository.findBySignInTypeIdAndEmail(signInType, email);
        return findMember.isPresent();
    }
}
