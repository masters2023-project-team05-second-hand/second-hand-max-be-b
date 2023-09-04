package kr.codesquad.secondhand.api.member.controller;

import java.util.List;
import kr.codesquad.secondhand.api.member.dto.AddressSliceResponse;
import kr.codesquad.secondhand.api.member.dto.LastVisitedAddressRequest;
import kr.codesquad.secondhand.api.member.dto.MemberAddressModifyRequest;
import kr.codesquad.secondhand.api.member.dto.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.dto.OAuthSignInRequest;
import kr.codesquad.secondhand.api.member.dto.OAuthSignInResponse;
import kr.codesquad.secondhand.api.member.service.AddressService;
import kr.codesquad.secondhand.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/api/members/addresses")
    public ResponseEntity<List<MemberAddressResponse>> modifyMemberAddresses(
            @RequestBody MemberAddressModifyRequest memberAddressModifyRequest) {
        // 임시 id
        Long memberId = 1L;
        List<MemberAddressResponse> memberAddressResponses = memberService.modifyMemberAddresses(memberId,
                memberAddressModifyRequest);
        return ResponseEntity.ok().body(memberAddressResponses);
    }

    @PatchMapping("/api/members/addresses")
    public ResponseEntity setLastVisitedAddress(@RequestBody LastVisitedAddressRequest lastVisitedAddressRequest) {
        // 임시 id
        Long memberId = 1L;
        memberService.setLastVisitedAddress(memberId, lastVisitedAddressRequest.getLastVisitedAddressId());
        return ResponseEntity.ok().build();
    }

}
