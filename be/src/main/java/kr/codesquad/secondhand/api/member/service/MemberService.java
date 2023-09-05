package kr.codesquad.secondhand.api.member.service;

import java.util.Optional;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.domain.SignInType;
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

    private final SignInTypeRepositoryImpl signInTypeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member getMemberReferenceById(Long memberId) {
        return memberRepository.getReferenceById(memberId);
    }


    public Member oAuthLogin(String providerName, String authorizationCode) {
        OAuthProfile oAuthProfile = oAuthService.requestOauthProfile(providerName, authorizationCode);
        SignInType signInType = signInTypeRepository.findByProvider(
                providerName);
        return oAuthProfile.toMember(signInType);
    }

    public Boolean isExistMember(Long signInType, String email) {
        Optional<Member> findMember = memberRepository.findBySignInTypeIdAndEmail(signInType, email);
        return findMember.isPresent();
    }
}
