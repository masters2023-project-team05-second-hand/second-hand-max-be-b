package kr.codesquad.secondhand.api.member.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.api.jwt.service.JwtService;
import kr.codesquad.secondhand.api.member.domain.Address;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import kr.codesquad.secondhand.api.member.dto.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.dto.OAuthSignInResponse;
import kr.codesquad.secondhand.api.member.repository.AddressRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberFacadeService {

    private final JwtService jwtService;
    private final MemberService memberService;
    private final MemberAddressService memberAddressService;

    private final AddressRepositoryImpl addressRepository;


    @Transactional
    public OAuthSignInResponse login(Member member) {
        Boolean existMember = memberService.isExistMember(member.getSignInTypeId(), member.getEmail());
        if (!existMember) {
            memberService.save(member);
        }
        Jwt jwt = jwtService.issueJwt(member.getId());
        return OAuthSignInResponse.from(jwt);
    }

    @Transactional
    public List<MemberAddressResponse> updateMemberAddress(Long memberId, List<Long> addressIds) {
        memberAddressService.clearMemberAddressByMemberId(memberId);
        List<Address> addresses = getAddressesByIds(addressIds);
        Member member = memberService.getMemberReferenceById(memberId);
        List<MemberAddress> memberAddresses = MemberAddress.of(member, addresses);
        return memberAddressService.modifyMemberAddresses(memberAddresses);
    }

    @Transactional
    public void updateLastVisitedAddress(Long memberId, Long lastVisitedAddressId) {
        memberAddressService.updateLastVisitedAddress(memberId, lastVisitedAddressId);
    }

    private List<Address> getAddressesByIds(List<Long> addressIds) {
        return addressIds.stream()
                .map(addressRepository::getReferenceById)
                .collect(Collectors.toUnmodifiableList());
    }
}
