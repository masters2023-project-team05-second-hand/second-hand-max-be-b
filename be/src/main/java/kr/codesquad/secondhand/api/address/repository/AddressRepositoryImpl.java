package kr.codesquad.secondhand.api.address.repository;

import kr.codesquad.secondhand.api.address.domain.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepositoryImpl extends JpaRepository<Address, Long> {

    Slice<Address> findAllBy(Pageable pageable);

    Address getReferenceById(Long id);
}