package kr.codesquad.secondhand.api.address.service;

import java.util.List;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.address.dto.AddressSliceResponse;
import kr.codesquad.secondhand.api.address.repository.AddressRepositoryImpl;
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

    public List<Address> findAddressesByIds(List<Long> addressIds) {
        return addressRepository.findAllById(addressIds);
    }

    public Address getReferenceById(Long addressId) {
        return addressRepository.getReferenceById(addressId);
    }
}
