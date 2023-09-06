package kr.codesquad.secondhand.api.member.service;

import java.util.List;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import kr.codesquad.secondhand.api.member.dto.response.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.repository.MemberAddressRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberAddressService {

    private final MemberAddressRepositoryImpl memberAddressRepository;

    @Transactional
    public List<MemberAddressResponse> deleteAndUpdateMemberAddresses(Member member, List<Address> addresses) {
        deleteMemberAddressIfExists(member.getId());
        return updateMemberAddresses(MemberAddress.of(member, addresses));
    }

    private void deleteMemberAddressIfExists(Long memberId) {
        if (memberAddressRepository.existsByMemberId(memberId)) {
            memberAddressRepository.deleteByMemberId(memberId);
        }
    }

    private List<MemberAddressResponse> updateMemberAddresses(List<MemberAddress> memberAddresses) {
        List<MemberAddress> memberAddressesResult = memberAddressRepository.saveAll(memberAddresses);
        return MemberAddressResponse.from(memberAddressesResult);
    }

    @Transactional
    public void updateLastVisitedAddress(Long memberId, Long lastVisitedAddressId) {
        resetLastVisitedAddress(memberId);
        setLastVisitedAddress(lastVisitedAddressId);
    }

    private void resetLastVisitedAddress(Long memberId) {
        MemberAddress memberAddress = memberAddressRepository.findByMemberIdAndIsLastVisited(memberId, true)
                .orElseThrow();
        memberAddress.updateLastVisited(false);
    }

    private void setLastVisitedAddress(Long lastVisitedAddressId) {
        MemberAddress lastVisitedAddress = memberAddressRepository.findByAddressId(lastVisitedAddressId).orElseThrow();
        lastVisitedAddress.updateLastVisited(true);
    }
}
