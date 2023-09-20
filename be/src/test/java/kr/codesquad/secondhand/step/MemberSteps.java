package kr.codesquad.secondhand.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import kr.codesquad.secondhand.api.member.dto.request.MemberAddressUpdateRequest;
import kr.codesquad.secondhand.util.AcceptanceTest;
import org.springframework.http.MediaType;

public class MemberSteps extends AcceptanceTest {

    public static ExtractableResponse<Response> 회원_정보_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/members")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원_동네_정보_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/members/addresses")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원_동네_수정_요청(String accessToken, List<Long> addressIds) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new MemberAddressUpdateRequest(addressIds))
                .when().put("/api/members/addresses")
                .then().log().all().extract();
    }
}
