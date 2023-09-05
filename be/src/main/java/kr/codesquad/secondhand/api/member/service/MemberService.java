package kr.codesquad.secondhand.api.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.codesquad.secondhand.api.member.domain.Address;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import kr.codesquad.secondhand.api.member.domain.SignInType;
import kr.codesquad.secondhand.api.member.dto.MemberAddressResponse;
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
    private final MemberAddressRepositoryImpl memberAddressRepository;

    @Transactional
    public void save(Member member){
        memberRepository.save(member);
    }

    @Transactional
    public List<MemberAddressResponse> modifyMemberAddresses(List<MemberAddress> memberAddresses) {
        List<MemberAddress> memberAddressesResult = memberAddressRepository.saveAll(memberAddresses);
        return MemberAddressResponse.from(memberAddressesResult);
    }

    @Transactional
    public void setLastVisitedAddress(Long memberId, Long lastVisitedAddressId) {
        MemberAddress memberAddress = memberAddressRepository.findByMemberIdAndIsLastVisited(memberId, true)
                .orElseThrow();
        memberAddress.updateLastVisited(false);
        MemberAddress lastVisitedAddress = memberAddressRepository.findByAddressId(lastVisitedAddressId).orElseThrow();
        lastVisitedAddress.updateLastVisited(true);
    }

    public Member getMemberReferenceById(Long memberId) {
        return memberRepository.getReferenceById(memberId);
    }


    public Member oAuthLogin(String providerName, String authorizationCode){
        OAuthProfile oAuthProfile = oAuthService.requestOauthProfile(providerName, authorizationCode);
        SignInType signInType = signInTypeRepository.findByProvider(
                providerName);
        return oAuthProfile.toMember(signInType);
    }


    public Boolean isExistMember(Long signInType, String email){
        Optional<Member> findMember = memberRepository.findBySignInTypeIdAndEmail(signInType, email);
        return findMember.isEmpty();
    }

    public void clearMemberAddressByMemberId(Long memberId){
        if (memberAddressRepository.existsByMemberId(memberId)) {
            memberAddressRepository.deleteByMemberId(memberId);
        }
    }
    public List<MemberAddress> getMemberAddresses(Member member, List<Address> addresses){
        List<MemberAddress> memberAddresses = new ArrayList<>();

        for (int i = 0; i < addresses.size(); i++) {
            Address address = addresses.get(i);
            boolean isLastVisited = i == addresses.size() - 1;
            memberAddresses.add(new MemberAddress(member, address, isLastVisited));
        }

        return memberAddresses;
    }
}
