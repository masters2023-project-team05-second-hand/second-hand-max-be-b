package kr.codesquad.secondhand.api.member.controller;

import kr.codesquad.secondhand.api.member.dto.OAuthSignInRequest;
import kr.codesquad.secondhand.api.member.dto.OAuthSignInResponse;
import kr.codesquad.secondhand.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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

}
