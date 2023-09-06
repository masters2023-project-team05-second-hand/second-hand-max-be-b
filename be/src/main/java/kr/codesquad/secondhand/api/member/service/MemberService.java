package kr.codesquad.secondhand.api.member.service;

import java.util.Optional;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.repository.MemberRepository;
import kr.codesquad.secondhand.api.oauth.domain.OAuthAttributes;
import kr.codesquad.secondhand.api.oauth.domain.OAuthProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long oAuthLogin(String providerName, OAuthProfile oAuthProfile) {
        Member member = oAuthProfile.toMember(OAuthAttributes.from(providerName));
        Optional<Member> findMember = memberRepository.findBySignInTypeIdAndEmail(
                member.getSignInTypeId(), member.getEmail()
        );
        return signInOrSignUp(member, findMember);
    }

    private Long signInOrSignUp(Member member, Optional<Member> findMember) {
        if (findMember.isEmpty()) {
            return memberRepository.save(member);
        }
        return findMember.get().getId();
    }

    public Member getMemberReferenceById(Long memberId) {
        return memberRepository.getReferenceById(memberId);
    }
}
