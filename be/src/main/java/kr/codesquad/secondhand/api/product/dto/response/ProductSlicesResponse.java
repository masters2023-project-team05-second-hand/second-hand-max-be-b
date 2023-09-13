package kr.codesquad.secondhand.api.product.dto.response;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStats;
import lombok.Builder;
import lombok.Getter;

/**
 * 판매 목록 조회, 관심 상품 조회, 전체 상품 조회 시 공통으로 사용
 */
@Getter
public class ProductSlicesResponse {

    private final List<ProductSlice> products;
    private final Boolean hasNext;

    public ProductSlicesResponse(List<ProductSlice> products, Boolean hasNext) {
        this.products = products;
        this.hasNext = hasNext;
    }

    public static ProductSlicesResponse of(List<Product> products, Map<Long, ProductStats> stats, Boolean hasNext) {
        List<ProductSlice> productSlices = products.stream()
                .map(product -> {
                    Long productId = product.getId();
                    ProductStats productStats = stats.get(productId);
                    return ProductSlice.of(product, productStats);
                })
                .collect(Collectors.toUnmodifiableList());
        return new ProductSlicesResponse(productSlices, hasNext);
    }

    @Getter
    private static class ProductSlice {

        private final Long productId;
        private final Long sellerId;
        private final URL thumbnailUrl;
        private final String title;
        private final String addressName;
        private final Timestamp createdTime;
        private final Long price;
        private final Integer statusId;
        private final ProductStatsResponse stats;

        @Builder
        private ProductSlice(Long productId, Long sellerId, URL thumbnailUrl, String title, String addressName,
                            Timestamp createdTime, Long price, Integer statusId, ProductStatsResponse stats) {
            this.productId = productId;
            this.sellerId = sellerId;
            this.thumbnailUrl = thumbnailUrl;
            this.title = title;
            this.addressName = addressName;
            this.createdTime = createdTime;
            this.price = price;
            this.statusId = statusId;
            this.stats = stats;
        }

        public static ProductSlice of(Product product, ProductStats stats) {
            return ProductSlice.builder()
                    .productId(product.getId())
                    .sellerId(product.getSeller().getId())
                    .thumbnailUrl(product.getThumbnailImgUrl())
                    .title(product.getTitle())
                    .addressName(product.getAddress().getName())
                    .createdTime((Timestamp) product.getCreatedTime())
                    .price(product.getPrice())
                    .statusId(product.getStatusId())
                    .stats(ProductStatsResponse.from(stats))
                    .build();
        }
    }

}
