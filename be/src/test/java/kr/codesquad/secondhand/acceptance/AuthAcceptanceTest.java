package kr.codesquad.secondhand.acceptance;

import static kr.codesquad.secondhand.step.AuthStep.로그아웃_요청;
import static kr.codesquad.secondhand.step.AuthStep.액세스_토큰_재발급_요청;

import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.fixture.MemberFixture;
import kr.codesquad.secondhand.util.AcceptanceTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그아웃_요청_시_응답이_성공한다() {
        // given
        Jwt jwt = 로그인을_한다(MemberFixture.멤버_지니);

        // when
        var response = 로그아웃_요청(jwt.getAccessToken(), jwt.getRefreshToken());

        // then
        응답_상태코드_검증(response, HttpStatus.OK);
    }

    @Test
    void 로그아웃_요청_시_액세스_토큰이_만료되면_응답이_실패한다() {
        // given
        Jwt jwt = 로그인을_한다(MemberFixture.멤버_지니);

        // when
        var response = 로그아웃_요청(
                "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6NCwiZXhwIjoxNjk0NDQwOTI4fQ.NCeZj4oZPTlXKN9yIwnMVtrcEJ4mHD-GiJcoiyhPsYM",
                jwt.getRefreshToken());

        // then
        응답_상태코드_검증(response, HttpStatus.UNAUTHORIZED);
    }

    @Test
    void 액세스_토큰_재발급_요청_시_응답이_성공한다() {
        // given
        Jwt jwt = 로그인을_한다(MemberFixture.멤버_지니);

        // when
        var response = 액세스_토큰_재발급_요청(jwt.getAccessToken(), jwt.getRefreshToken());

        // then
        응답_상태코드_검증(response, HttpStatus.OK);
    }
}
