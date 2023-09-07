package kr.codesquad.secondhand.api.member.controller;

import java.util.List;
import kr.codesquad.secondhand.api.member.dto.request.LastVisitedUpdateRequest;
import kr.codesquad.secondhand.api.member.dto.request.MemberAddressUpdateRequest;
import kr.codesquad.secondhand.api.member.dto.request.OAuthSignInRequest;
import kr.codesquad.secondhand.api.member.dto.response.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.dto.response.MemberProfileResponse;
import kr.codesquad.secondhand.api.member.dto.response.OAuthSignInResponse;
import kr.codesquad.secondhand.api.member.service.MemberAddressService;
import kr.codesquad.secondhand.api.member.service.MemberFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacadeService memberFacadeService;
    private final MemberAddressService memberAddressService;

    /**
     * 로그인 요청
     */
    @PostMapping("/api/members/sign-in/{provider}")
    public ResponseEntity<OAuthSignInResponse> login(@PathVariable String provider,
                                                     @RequestBody OAuthSignInRequest request) {

        OAuthSignInResponse oAuthSignInResponse = memberFacadeService.login(provider, request.getAccessCode());
        return ResponseEntity.ok()
                .body(oAuthSignInResponse);
    }

    @PutMapping("/api/members/addresses")
    public ResponseEntity<List<MemberAddressResponse>> updateMemberAddresses(
            @RequestBody MemberAddressUpdateRequest memberAddressUpdateRequest) {
        // 임시 id
        Long memberId = 1L;
        List<MemberAddressResponse> memberAddressResponses = memberFacadeService.updateMemberAddresses(
                memberId,
                memberAddressUpdateRequest.getAddressIds()
        );
        return ResponseEntity.ok()
                .body(memberAddressResponses);
    }

    @PatchMapping("/api/members/addresses")
    public ResponseEntity<String> updateLastVisitedAddress(
            @RequestBody LastVisitedUpdateRequest lastVisitedUpdateRequest) {
        // 임시 id
        Long memberId = 1L;
        memberAddressService.updateLastVisitedAddress(memberId, lastVisitedUpdateRequest.getLastVisitedAddressId());
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("api/members")
    public ResponseEntity<MemberProfileResponse> readMemberProfile() {
        Long memberId = 1L;
        MemberProfileResponse memberProfileResponse = memberFacadeService.readMemberProfile(memberId);
        return ResponseEntity.ok().body(memberProfileResponse);
    }

    @GetMapping("api/members/addresses")
    public ResponseEntity<List<MemberAddressResponse>> readMemberAddress() {
        Long memberId = 1L;
        List<MemberAddressResponse> memberAddressResponse = memberFacadeService.readMemberAddresses(memberId);
        return ResponseEntity.ok().body(memberAddressResponse);
    }
}
