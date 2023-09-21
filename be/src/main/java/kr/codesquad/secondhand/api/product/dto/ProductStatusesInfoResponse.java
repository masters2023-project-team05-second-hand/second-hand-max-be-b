package kr.codesquad.secondhand.api.product.dto;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductStatusesInfoResponse {

    private Integer id;
    private String type;

    public ProductStatusesInfoResponse(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    private static ProductStatusesInfoResponse from(ProductStatus status) {
        return new ProductStatusesInfoResponse(status.getId(), status.getType());
    }

    public static List<ProductStatusesInfoResponse> from(List<ProductStatus> statuses) {
        return statuses.stream()
                .map(ProductStatusesInfoResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
