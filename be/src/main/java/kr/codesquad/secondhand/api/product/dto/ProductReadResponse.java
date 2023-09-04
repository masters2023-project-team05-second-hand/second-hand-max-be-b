package kr.codesquad.secondhand.api.product.dto;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.product.domain.Image;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductReadResponse {

    private final Boolean isSeller;
    private final ProductResponse product;
    private final List<ProductImageResponse> images;
    private final List<ProductStatusResponse> statuses;
//    private Stats stats; redis로 구현 필요


    private ProductReadResponse(boolean isSeller, ProductResponse product, List<ProductImageResponse> images,
                               List<ProductStatusResponse> statuses) {
        this.isSeller = isSeller;
        this.product = product;
        this.images = images;
        this.statuses = statuses;
    }

    public static ProductReadResponse of(boolean isSeller, Product product, List<Image> images, List<ProductStatus> status) {
        ProductResponse productResponse = ProductResponse.from(product);
        List<ProductImageResponse> productImageResponse = images.stream()
                .map(ProductImageResponse::from)
                .collect(Collectors.toUnmodifiableList());
        List<ProductStatusResponse> productStatusResponse = status.stream()
                .map(ProductStatusResponse::from)
                .collect(Collectors.toUnmodifiableList());
        return new ProductReadResponse(isSeller, productResponse, productImageResponse, productStatusResponse);
    }

    @Getter
    private static class ProductResponse {

        private final String title;
        private final String contents;
        private final Long price;
        private final Date createdTime; // 시간 타입 뭘로 할지 정해야 함
        private final Long productStatus;

        @Builder
        private ProductResponse(String title, String contents, Long price, Date createdTime, Long productStatus) {
            this.title = title;
            this.contents = contents;
            this.price = price;
            this.createdTime = createdTime;
            this.productStatus = productStatus;
        }

        private static ProductResponse from(Product product) {
            return ProductResponse.builder()
                    .title(product.getTitle())
                    .contents(product.getContent())
                    .price(product.getPrice())
                    .createdTime(product.getCreatedTime())
                    .productStatus(product.getStatus().getId())
                    .build();
        }
    }

    @Getter
    private static class ProductImageResponse {

        private final Long id;
        private final URL url;

        private ProductImageResponse(Long id, URL url) {
            this.id = id;
            this.url = url;
        }

        public static ProductImageResponse from(Image image) {
            return new ProductImageResponse(image.getId(), image.getUrl());
        }
    }

    @Getter
    private static class ProductStatusResponse {

        private final Long id;
        private final String type;

        public ProductStatusResponse(Long id, String type) {
            this.id = id;
            this.type = type;
        }

        public static ProductStatusResponse from(ProductStatus productStatus) {
            return new ProductStatusResponse(productStatus.getId(), productStatus.getType());
        }
    }
}
