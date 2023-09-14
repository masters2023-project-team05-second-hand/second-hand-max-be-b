package kr.codesquad.secondhand.api.member.controller;

import static kr.codesquad.secondhand.global.util.HttpAuthorizationUtils.extractAccessToken;
import static kr.codesquad.secondhand.global.util.HttpAuthorizationUtils.extractMemberId;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import kr.codesquad.secondhand.api.category.dto.CategorySummaryResponse;
import kr.codesquad.secondhand.api.jwt.exception.TokenNotFoundException;
import kr.codesquad.secondhand.api.jwt.service.JwtService;
import kr.codesquad.secondhand.api.member.dto.MemberProfileImgUpdateDto;
import kr.codesquad.secondhand.api.member.dto.ReissueAccessTokenDto;
import kr.codesquad.secondhand.api.member.dto.request.MemberAddressUpdateRequest;
import kr.codesquad.secondhand.api.member.dto.request.MemberNicknameUpdateRequest;
import kr.codesquad.secondhand.api.member.dto.request.OAuthSignInRequest;
import kr.codesquad.secondhand.api.member.dto.request.SignOutRequest;
import kr.codesquad.secondhand.api.member.dto.request.WishProductRequest;
import kr.codesquad.secondhand.api.member.dto.response.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.dto.response.MemberProfileResponse;
import kr.codesquad.secondhand.api.member.dto.response.OAuthSignInResponse;
import kr.codesquad.secondhand.api.member.dto.response.ProductWishStatusResponse;
import kr.codesquad.secondhand.api.member.service.MemberAddressService;
import kr.codesquad.secondhand.api.member.service.MemberFacadeService;
import kr.codesquad.secondhand.api.member.service.MemberProductFacadeService;
import kr.codesquad.secondhand.api.member.service.MemberService;
import kr.codesquad.secondhand.api.product.dto.response.ProductSlicesResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacadeService memberFacadeService;
    private final MemberProductFacadeService memberProductFacadeService;
    private final MemberAddressService memberAddressService;
    private final MemberService memberService;
    private final JwtService jwtService;

    /**
     * 로그인 요청
     */
    @PostMapping("/api/members/sign-in/{provider}")
    public ResponseEntity<OAuthSignInResponse> signIn(@PathVariable String provider,
                                                      @Validated @RequestBody OAuthSignInRequest request) {

        OAuthSignInResponse oAuthSignInResponse = memberFacadeService.signIn(provider, request.getAccessCode());
        return ResponseEntity.ok()
                .body(oAuthSignInResponse);
    }

    @PostMapping("/api/sign-out")
    public ResponseEntity<String> signOut(HttpServletRequest httpServletRequest,
                                          @Validated @RequestBody SignOutRequest signOutRequest) throws TokenNotFoundException {
        Long memberId = extractMemberId(httpServletRequest);
        String accessToken = extractAccessToken(httpServletRequest);
        memberFacadeService.signOut(memberId, accessToken, signOutRequest.getRefreshToken());
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/api/reissue-access-token")
    public ResponseEntity<ReissueAccessTokenDto.Response> reissueAccessToken(HttpServletRequest httpServletRequest,
                                                                             @Validated @RequestBody ReissueAccessTokenDto.Request request) {
        Long memberId = extractMemberId(httpServletRequest);
        String refreshToken = jwtService.reissueAccessToken(memberId, request.getRefreshToken());
        return ResponseEntity.ok()
                .body(new ReissueAccessTokenDto.Response(refreshToken));
    }

    @PostMapping("/api/members/wishlist")
    public ResponseEntity<String> toggleWishProduct(HttpServletRequest httpServletRequest,
                                                    @Validated @RequestBody WishProductRequest request) {
        Long memberId = extractMemberId(httpServletRequest);
        memberProductFacadeService.addOrResetWishes(memberId, request);
        return ResponseEntity.ok().build();
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

    @GetMapping("/api/members/sales")
    public ResponseEntity<ProductSlicesResponse> readMemberSales(HttpServletRequest httpServletRequest,
                                                                 @RequestParam List<Integer> statusId,
                                                                 @RequestParam Integer page,
                                                                 @RequestParam Integer size) {
        Long memberId = extractMemberId(httpServletRequest);
        ProductSlicesResponse productSlicesResponse = memberProductFacadeService.readMemberSales(
                memberId, statusId, page, size);
        return ResponseEntity.ok().body(productSlicesResponse);
    }

    @GetMapping("/api/members/wishlist")
    public ResponseEntity<ProductSlicesResponse> readMemberWishlist(HttpServletRequest httpServletRequest,
                                                                    @RequestParam(defaultValue = "0") Long categoryId,
                                                                    @RequestParam Integer page,
                                                                    @RequestParam Integer size) {
        Long memberId = extractMemberId(httpServletRequest);
        ProductSlicesResponse productSlicesResponse = memberProductFacadeService.readMemberWishlist(
                memberId, categoryId, page, size);
        return ResponseEntity.ok().body(productSlicesResponse);
    }

    @GetMapping("/api/members/wishlist/categories")
    public ResponseEntity<List<CategorySummaryResponse>> readMemberWishCategories(
            HttpServletRequest httpServletRequest) {
        Long memberId = extractMemberId(httpServletRequest);
        List<CategorySummaryResponse> response = memberProductFacadeService.readMemberWishCategories(memberId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/api/members/wishlist/{productId}")
    public ResponseEntity<ProductWishStatusResponse> checkProductWishedStatus(HttpServletRequest httpServletRequest,
                                                                              @PathVariable Long productId) {
        Long memberId = extractMemberId(httpServletRequest);
        ProductWishStatusResponse response = memberProductFacadeService.checkProductWishedStatus(memberId, productId);
        return ResponseEntity.ok().body(response);
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

    @PatchMapping("api/members/profile-image")
    public ResponseEntity<MemberProfileImgUpdateDto.Response> updateMemberProfileImg(
            HttpServletRequest httpServletRequest,
            @ModelAttribute MemberProfileImgUpdateDto.Request request) {
        Long memberId = extractMemberId(httpServletRequest);
        MemberProfileImgUpdateDto.Response response = memberFacadeService.updateMemberProfileImg(
                memberId, request);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("api/members/nickname")
    public ResponseEntity<String> updateMemberNickname(HttpServletRequest httpServletRequest,
                                                       @Validated @RequestBody MemberNicknameUpdateRequest request) {
        Long memberId = extractMemberId(httpServletRequest);
        memberFacadeService.updateMemberNickname(memberId, request);
        return ResponseEntity.ok().build();
    }
}
