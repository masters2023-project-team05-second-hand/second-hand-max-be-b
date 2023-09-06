package kr.codesquad.secondhand.api.address.controller;

import kr.codesquad.secondhand.api.address.service.AddressService;
import kr.codesquad.secondhand.api.member.dto.response.AddressSliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/api/addresses")
    public ResponseEntity<AddressSliceResponse> readAddresses(@RequestParam int page, @RequestParam int size) {
        AddressSliceResponse addressSliceResponse = addressService.findAddresses(page, size);
        return ResponseEntity.ok()
                .body(addressSliceResponse);
    }
}
