package kr.codesquad.secondhand.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import kr.codesquad.secondhand.api.product.dto.ProductStatusesInfoResponse;
import kr.codesquad.secondhand.step.StatusStep;
import kr.codesquad.secondhand.util.AcceptanceTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class StatusAcceptanceTest extends AcceptanceTest {

    @Test
    void 상태_목록_조회_요청_시_응답이_성공한다() {
        // when
        var response = StatusStep.상태_목록_조회_요청();

        // then
        응답_상태코드_검증(response, HttpStatus.OK);
        상태_목록_조회_정보_검증(response);
    }

    private void 상태_목록_조회_정보_검증(ExtractableResponse<Response> response) {
        List<ProductStatusesInfoResponse> actualResponse = response.jsonPath().getList(".", ProductStatusesInfoResponse.class);
        List<ProductStatusesInfoResponse> expectedResponse = ProductStatusesInfoResponse.from(ProductStatus.findAll());

        Assertions.assertThat(actualResponse).containsExactlyInAnyOrderElementsOf(expectedResponse);
    }
}
