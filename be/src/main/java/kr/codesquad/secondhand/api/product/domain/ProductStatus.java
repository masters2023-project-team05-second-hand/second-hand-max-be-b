package kr.codesquad.secondhand.api.product.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    FOR_SALE(1, "판매중"),
    SOLD(2, "판매완료"),
    RESERVED(3, "예약중");

    private static final List<ProductStatus> PRODUCT_STATUSES;

    private final Integer id;
    private final String type;

    static {
        PRODUCT_STATUSES = Arrays.stream(values())
                .collect(Collectors.toUnmodifiableList());
    }

    public static ProductStatus from(Integer statusId) {
        return Arrays.stream(values())
                .filter(status -> status.id.equals(statusId))
                .findAny()
                .orElseThrow();
    }

    public static List<ProductStatus> findAll() {
        return PRODUCT_STATUSES;
    }
}
