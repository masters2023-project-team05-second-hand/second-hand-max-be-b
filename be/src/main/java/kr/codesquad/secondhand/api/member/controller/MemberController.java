package kr.codesquad.secondhand.api.member.controller;

import kr.codesquad.secondhand.api.member.dto.AddressSliceResponse;
import kr.codesquad.secondhand.api.member.dto.OAuthSignInRequest;
import kr.codesquad.secondhand.api.member.dto.OAuthSignInResponse;
import kr.codesquad.secondhand.api.member.service.AddressService;
import kr.codesquad.secondhand.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AddressService addressService;

    /**
     * 로그인 요청
     */
    @PostMapping("/api/members/sign-in/{provider}")
    public ResponseEntity<OAuthSignInResponse> login(@PathVariable String provider,
                                                     @RequestBody OAuthSignInRequest request) {
        OAuthSignInResponse OAuthSignInResponse = memberService.signInOrSignUp(provider, request.getAccessCode());
        return ResponseEntity.ok()
                .body(OAuthSignInResponse);
    }

    @GetMapping("/api/addresses")
    private ResponseEntity<AddressSliceResponse> getAddresses(@RequestParam int page, @RequestParam int size) {
        AddressSliceResponse addressSliceResponse = addressService.getAddresses(page, size);
        return ResponseEntity.ok().body(addressSliceResponse);
    }

}
