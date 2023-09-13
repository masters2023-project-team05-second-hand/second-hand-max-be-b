package kr.codesquad.secondhand.api.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import kr.codesquad.secondhand.api.member.dto.response.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.exception.InvalidMemberAddressIdException;
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
        memberAddressRepository.deleteByMemberId(member.getId());
        return updateMemberAddresses(MemberAddress.of(member, addresses));
    }

    private List<MemberAddressResponse> updateMemberAddresses(List<MemberAddress> memberAddresses) {
        List<MemberAddress> memberAddressesResult = memberAddressRepository.saveAll(memberAddresses);
        return MemberAddressResponse.from(memberAddressesResult);
    }

    @Transactional
    public List<MemberAddressResponse> readMemberAddresses(Long memberId) {
        List<MemberAddress> memberAddresses = findByMemberId(memberId);
        return MemberAddressResponse.from(memberAddresses);
    }

    private List<MemberAddress> findByMemberId(Long memberId) {
        Optional<List<MemberAddress>> memberAddresses = memberAddressRepository.findAllByMemberId(memberId);
        if (memberAddresses.isPresent()) {
            return memberAddresses.orElseThrow(InvalidMemberAddressIdException::new);
        }
        return new ArrayList<>();
    }
}
