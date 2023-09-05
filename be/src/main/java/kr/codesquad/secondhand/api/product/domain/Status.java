package kr.codesquad.secondhand.api.product.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    FOR_SALE(1, "판매중"),
    SOLD(2, "판매완료"),
    RESERVED(3, "예약중");

    private static final List<Status> STATUSES;

    private final Integer id;
    private final String type;

    static {
        STATUSES = Arrays.stream(values())
                .collect(Collectors.toUnmodifiableList());
    }

    public static Status from(Integer statusId) {
        return Arrays.stream(values())
                .filter(status -> status.id.equals(statusId))
                .findAny()
                .orElseThrow();
    }

    public static List<Status> findAll() {
        return STATUSES;
    }
}
