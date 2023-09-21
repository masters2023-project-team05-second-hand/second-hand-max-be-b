package kr.codesquad.secondhand.acceptance;

import static kr.codesquad.secondhand.step.MemberSteps.회원_동네_수정_요청;
import static kr.codesquad.secondhand.step.MemberSteps.회원_동네_정보_조회_요청;
import static kr.codesquad.secondhand.step.MemberSteps.회원_정보_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import kr.codesquad.secondhand.api.member.dto.response.MemberAddressResponse;
import kr.codesquad.secondhand.api.member.dto.response.MemberProfileResponse;
import kr.codesquad.secondhand.fixture.AddressFixture;
import kr.codesquad.secondhand.fixture.MemberFixture;
import kr.codesquad.secondhand.util.AcceptanceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_정보_조회_요청_시_응답이_성공한다() {
        // given
        Member member = MemberFixture.멤버_지니.toMember();
        Jwt jwt = 로그인을_한다(MemberFixture.멤버_지니);

        // when
        var response = 회원_정보_조회_요청(jwt.getAccessToken());

        // then
        응답_상태코드_검증(response, HttpStatus.OK);
        회원_정보_조회_검증(response, member.getNickname(), member.getProfileImgUrl());
    }

    @Test
    void 회원_동네_정보_조회_시_응답이_성공한다() {
        // given
        Member member = MemberFixture.멤버_지니.toMember();
        Jwt jwt = 로그인을_한다(MemberFixture.멤버_지니);

        Address saJik = AddressFixture.SAJIK.toAddress();
        Address buAm = AddressFixture.BUAM.toAddress();

        List<MemberAddress> memberAddresses = MemberAddress.of(member, List.of(saJik, buAm));

        회원_동네_수정_요청(jwt.getAccessToken(), List.of(saJik.getId(), buAm.getId()));

        // when
        var response = 회원_동네_정보_조회_요청(jwt.getAccessToken());

        // then
        응답_상태코드_검증(response, HttpStatus.OK);
        회원_동네_조회_검증(response, memberAddresses);
    }

    @Test
    void 회원_동네_수정_요청_시_응답이_성공한다() {
        // given
        Jwt jwt = 로그인을_한다(MemberFixture.멤버_지니);
        Long addressId = AddressFixture.BUAM.getId();

        // when
        var response = 회원_동네_수정_요청(jwt.getAccessToken(), Collections.singletonList(addressId));

        // then
        응답_상태코드_검증(response, HttpStatus.OK);
    }

    @Test
    void 존재하지_않는_회원_동네_ID로_수정_요청_시_응답이_실패한다() {
        // given
        Jwt jwt = 로그인을_한다(MemberFixture.멤버_지니);
        Long addressId = 11L;

        // when
        var response = 회원_동네_수정_요청(jwt.getAccessToken(), Collections.singletonList(addressId));

        // then
        응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
    }

    private void 회원_정보_조회_검증(ExtractableResponse<Response> response, String expectedNickname,
                             String expectedProfileImageUrl) {

        MemberProfileResponse actualResponse = response.jsonPath().getObject(".", MemberProfileResponse.class);

        Assertions.assertAll(
                () -> assertThat(actualResponse.getId()).isEqualTo(1L),
                () -> assertThat(actualResponse.getNickname()).isEqualTo(expectedNickname),
                () -> assertThat(actualResponse.getProfileImgUrl()).isEqualTo(expectedProfileImageUrl)
        );
    }

    private void 회원_동네_조회_검증(ExtractableResponse<Response> response, List<MemberAddress> memberAddresses) {

        List<MemberAddressResponse> actualResponse = response.jsonPath().getList(".", MemberAddressResponse.class);
        List<MemberAddressResponse> expectedResponse = MemberAddressResponse.from(memberAddresses);

        Assertions.assertAll(
                () -> assertThat(actualResponse.size()).isEqualTo(expectedResponse.size()),
                () -> assertThat(actualResponse).containsAll(expectedResponse)
        );
    }
}
