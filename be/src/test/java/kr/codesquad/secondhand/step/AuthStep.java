package kr.codesquad.secondhand.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kr.codesquad.secondhand.api.member.dto.ReissueAccessTokenDto;
import kr.codesquad.secondhand.api.member.dto.request.SignOutRequest;
import kr.codesquad.secondhand.util.AcceptanceTest;
import org.springframework.http.MediaType;

public class AuthStep extends AcceptanceTest {

    public static ExtractableResponse<Response> 로그아웃_요청(String accessToken, String refreshToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SignOutRequest(refreshToken))
                .when().post("/api/sign-out")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 액세스_토큰_재발급_요청(String accessToken, String refreshToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ReissueAccessTokenDto.Request(refreshToken))
                .when().post("/api/reissue-access-token")
                .then().log().all().extract();
    }
}
