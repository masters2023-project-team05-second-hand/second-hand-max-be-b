package kr.codesquad.secondhand.acceptance;

import static kr.codesquad.secondhand.step.CategoryStep.카테고리_목록_조회_요청;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.category.dto.CategoryReadResponse;
import kr.codesquad.secondhand.util.AcceptanceTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class CategoryAcceptanceTest extends AcceptanceTest {

    @Test
    void 카테고리_목록_조회_요청_시_응답이_성공한다() {
        // when
        var response = 카테고리_목록_조회_요청();

        // then
        응답_상태코드_검증(response, HttpStatus.OK);
        카테고리_목록_조회_정보_검증(response);
    }

    private void 카테고리_목록_조회_정보_검증(ExtractableResponse<Response> response) {
        List<CategoryReadResponse> actualResponse = response.jsonPath().getList(".", CategoryReadResponse.class);
        List<CategoryReadResponse> expectedResponse = CategoryReadResponse.from(Category.findAll());

        Assertions.assertThat(actualResponse).containsExactlyInAnyOrderElementsOf(expectedResponse);
    }
}
