package kr.codesquad.secondhand.api.address.dto;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.address.domain.Address;
import lombok.Getter;

@Getter
public class AddressSliceResponse {

    private final List<AddressesResponse> addresses;
    private final Boolean hasNext;

    private AddressSliceResponse(List<AddressesResponse> addresses, Boolean hasNext) {
        this.addresses = addresses;
        this.hasNext = hasNext;
    }

    public static AddressSliceResponse of(List<Address> addresses, Boolean hasNext) {
        List<AddressesResponse> addressesResponses = addresses.stream()
                .map(AddressesResponse::from)
                .collect(Collectors.toUnmodifiableList());
        return new AddressSliceResponse(addressesResponses, hasNext);
    }

    @Getter
    private static class AddressesResponse {

        private final Long id;
        private final String name;

        private AddressesResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        private static AddressesResponse from(Address address) {
            return new AddressesResponse(address.getId(), address.getName());
        }
    }
}
