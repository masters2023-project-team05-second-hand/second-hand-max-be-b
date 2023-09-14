package kr.codesquad.secondhand.api.address.controller;

import kr.codesquad.secondhand.api.address.dto.AddressSliceResponse;
import kr.codesquad.secondhand.api.address.service.AddressService;
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
    public ResponseEntity<AddressSliceResponse> readAddresses(@RequestParam Integer page, @RequestParam Integer size) {
        AddressSliceResponse addressSliceResponse = addressService.findAddresses(page, size);
        return ResponseEntity.ok()
                .body(addressSliceResponse);
    }
}
