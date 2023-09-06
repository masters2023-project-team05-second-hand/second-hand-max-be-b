package kr.codesquad.secondhand.api.address.service;

import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.address.repository.AddressRepositoryImpl;
import kr.codesquad.secondhand.api.member.dto.response.AddressSliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepositoryImpl addressRepository;

    public AddressSliceResponse findAddresses(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Slice<Address> addressSlice = addressRepository.findAllBy(pageRequest);
        return AddressSliceResponse.of(addressSlice.getContent(), addressSlice.hasNext());
    }
}
