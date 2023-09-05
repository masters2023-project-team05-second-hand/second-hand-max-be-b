package kr.codesquad.secondhand.api.member.service;

import java.util.List;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import kr.codesquad.secondhand.api.member.dto.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.repository.MemberAddressRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberAddressService {

    private final MemberAddressRepositoryImpl memberAddressRepository;

    @Transactional
    public List<MemberAddressResponse> modifyMemberAddresses(List<MemberAddress> memberAddresses) {
        List<MemberAddress> memberAddressesResult = memberAddressRepository.saveAll(memberAddresses);
        return MemberAddressResponse.from(memberAddressesResult);
    }

    @Transactional
    public void updateLastVisitedAddress(Long memberId, Long lastVisitedAddressId) {
        MemberAddress memberAddress = memberAddressRepository.findByMemberIdAndIsLastVisited(memberId, true)
                .orElseThrow();
        memberAddress.updateLastVisited(false);
        MemberAddress lastVisitedAddress = memberAddressRepository.findByAddressId(lastVisitedAddressId).orElseThrow();
        lastVisitedAddress.updateLastVisited(true);
    }

    public void clearMemberAddressByMemberId(Long memberId) {
        if (memberAddressRepository.existsByMemberId(memberId)) {
            memberAddressRepository.deleteByMemberId(memberId);
        }
    }
}
