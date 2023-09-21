package kr.codesquad.secondhand.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kr.codesquad.secondhand.util.AcceptanceTest;
import org.springframework.http.MediaType;

public class CategoryStep extends AcceptanceTest {

    public static ExtractableResponse<Response> 카테고리_목록_조회_요청() {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/categories")
                .then().log().all().extract();
    }
}
