package kr.codesquad.secondhand.api.product.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class ProductStats {

    private static final Integer VIEW_COUNT_INDEX = 0;
    private static final Integer WISH_COUNT_INDEX = 1;

    private final Integer viewCount;
    private final Integer wishCount;

    private ProductStats(Integer viewCount, Integer wishCount) {
        this.viewCount = viewCount;
        this.wishCount = wishCount;
    }

    public static ProductStats from(List<Object> stats){
        Integer viewCount = Integer.parseInt(stats.get(VIEW_COUNT_INDEX).toString());
        Integer wishCount = Integer.parseInt(stats.get(WISH_COUNT_INDEX).toString());
        return new ProductStats(viewCount, wishCount);
    }
}

