package kr.codesquad.secondhand.api.member.repository;

import kr.codesquad.secondhand.api.member.domain.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepositoryImpl extends JpaRepository<Address, Long> {

    Slice<Address> findAllBy(Pageable pageable);

    Address getReferenceById(Long id);
}
