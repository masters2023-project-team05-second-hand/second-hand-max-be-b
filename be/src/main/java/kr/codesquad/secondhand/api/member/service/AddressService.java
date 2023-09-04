package kr.codesquad.secondhand.api.member.service;

import kr.codesquad.secondhand.api.member.domain.Address;
import kr.codesquad.secondhand.api.member.dto.FindAddressesResponse;
import kr.codesquad.secondhand.api.member.repository.AddressRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepositoryImpl addressRepository;

    public FindAddressesResponse getAddresses(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Slice<Address> addressSlice = addressRepository.findAllBy(pageRequest);
        return FindAddressesResponse.of(addressSlice.getContent(), addressSlice.hasNext());
    }
}
