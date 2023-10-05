package kr.codesquad.secondhand.api.product.dto.response;

import kr.codesquad.secondhand.api.product.domain.ProductStats;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductStatsResponse {

    private final Integer viewCount;
    private final Integer wishCount;
    private final Integer chatCount;

    @Builder
    private ProductStatsResponse(Integer viewCount, Integer wishCount, Integer chatCount) {
        this.viewCount = viewCount;
        this.wishCount = wishCount;
        this.chatCount = chatCount;
    }

    public static ProductStatsResponse from(ProductStats stats) {
        return ProductStatsResponse.builder()
                .viewCount(stats.getViewCount())
                .wishCount(stats.getWishCount())
                .chatCount(stats.getChatCount())
                .build();
    }
}
