package kr.codesquad.secondhand.api.member.controller;

import static kr.codesquad.secondhand.global.util.HttpAuthorizationUtils.extractMemberId;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import kr.codesquad.secondhand.api.member.dto.request.LastVisitedUpdateRequest;
import kr.codesquad.secondhand.api.member.dto.request.MemberAddressUpdateRequest;
import kr.codesquad.secondhand.api.member.dto.request.MemberRequest.MemberProfileImgUpdateRequest;
import kr.codesquad.secondhand.api.member.dto.request.OAuthSignInRequest;
import kr.codesquad.secondhand.api.member.dto.response.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.dto.response.MemberProfileResponse;
import kr.codesquad.secondhand.api.member.dto.response.MemberResponse.MemberProfileImgUpdateResponse;
import kr.codesquad.secondhand.api.member.dto.response.OAuthSignInResponse;
import kr.codesquad.secondhand.api.member.service.MemberAddressService;
import kr.codesquad.secondhand.api.member.service.MemberFacadeService;
import kr.codesquad.secondhand.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final MemberService memberService;

    /**
     * 로그인 요청
     */
    @PostMapping("/api/members/sign-in/{provider}")
    public ResponseEntity<OAuthSignInResponse> login(@PathVariable String provider,
                                                     @Validated @RequestBody OAuthSignInRequest request) {

        OAuthSignInResponse oAuthSignInResponse = memberFacadeService.login(provider, request.getAccessCode());
        return ResponseEntity.ok()
                .body(oAuthSignInResponse);
    }

    @PutMapping("/api/members/addresses")
    public ResponseEntity<List<MemberAddressResponse>> updateMemberAddresses(HttpServletRequest httpServletRequest,
                                                                             @Validated @RequestBody MemberAddressUpdateRequest memberAddressUpdateRequest) {
        Long memberId = extractMemberId(httpServletRequest);
        List<MemberAddressResponse> memberAddressResponses = memberFacadeService.updateMemberAddresses(
                memberId,
                memberAddressUpdateRequest.getAddressIds()
        );
        return ResponseEntity.ok()
                .body(memberAddressResponses);
    }

    @PatchMapping("/api/members/addresses")
    public ResponseEntity<String> updateLastVisitedAddress(HttpServletRequest httpServletRequest,
                                                           @RequestBody LastVisitedUpdateRequest lastVisitedUpdateRequest) {
        Long memberId = extractMemberId(httpServletRequest);
        memberAddressService.updateLastVisitedAddress(memberId, lastVisitedUpdateRequest.getLastVisitedAddressId());
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/api/members")
    public ResponseEntity<MemberProfileResponse> readMemberProfile(HttpServletRequest httpServletRequest) {
        Long memberId = extractMemberId(httpServletRequest);
        MemberProfileResponse memberProfileResponse = memberService.readMemberProfile(memberId);
        return ResponseEntity.ok().body(memberProfileResponse);
    }

    @GetMapping("api/members/addresses")
    public ResponseEntity<List<MemberAddressResponse>> readMemberAddress(HttpServletRequest httpServletRequest) {
        Long memberId = extractMemberId(httpServletRequest);
        List<MemberAddressResponse> memberAddressResponse = memberAddressService.readMemberAddresses(memberId);
        return ResponseEntity.ok().body(memberAddressResponse);
    }

    @PatchMapping("api/members/profile-image")
    public ResponseEntity<MemberProfileImgUpdateResponse> updateMemberProfileImg(HttpServletRequest httpServletRequest,
                                                                                 @ModelAttribute MemberProfileImgUpdateRequest request) {
        Long memberId = extractMemberId(httpServletRequest);
        MemberProfileImgUpdateResponse memberProfileImgUrlResponse = memberFacadeService.updateMemberProfileImg(
                memberId, request);
        return ResponseEntity.ok().body(memberProfileImgUrlResponse);
    }
}
