package kr.codesquad.secondhand.api.member.dto;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.member.domain.Address;
import lombok.Getter;

@Getter
public class AddressSliceResponse {

    public List<AddressesResponse> addresses;
    public Boolean hasNext;

    public AddressSliceResponse(List<AddressesResponse> addresses, Boolean hasNext) {
        this.addresses = addresses;
        this.hasNext = hasNext;
    }

    public static AddressSliceResponse of(List<Address> addresses, Boolean hasNext) {
        return new AddressSliceResponse(addresses.stream()
                .map(AddressesResponse::from)
                .collect(Collectors.toUnmodifiableList()), hasNext);
    }

    @Getter
    public static class AddressesResponse {

        private Long id;
        private String name;

        public AddressesResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public static AddressesResponse from(Address address) {
            return new AddressesResponse(address.getId(), address.getName());
        }
    }
}
