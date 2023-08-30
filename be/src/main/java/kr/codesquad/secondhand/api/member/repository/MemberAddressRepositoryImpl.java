package kr.codesquad.secondhand.api.member.repository;

import java.util.List;
import java.util.Optional;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAddressRepositoryImpl extends JpaRepository<MemberAddress, Long> {

    Optional<List<MemberAddress>> findAllByMemberId(Long memberId);
}