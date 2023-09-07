package kr.codesquad.secondhand.api.product.dto;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.category.dto.CategoryReadResponse;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductImage;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductReadResponse {

    private final Boolean isSeller;
    private final ProductResponse product;
    private final List<ProductImageResponse> images;
    private final List<ProductStatusResponse> statuses;
    private final ProductStats stats;
    private final CategoryReadResponse category;
    private final AddressResponse address;

    public ProductReadResponse(Boolean isSeller, ProductResponse product, List<ProductImageResponse> images,
                               List<ProductStatusResponse> statuses, ProductStats stats,
                               CategoryReadResponse category, AddressResponse address) {
        this.isSeller = isSeller;
        this.product = product;
        this.images = images;
        this.statuses = statuses;
        this.stats = stats;
        this.category = category;
        this.address = address;
    }

    public static ProductReadResponse of(boolean isSeller, Product product, List<ProductImage> productImages,
                                         List<ProductStatus> productStatuses, List<Integer> statsList,
                                         Category category, Address address) {
        ProductResponse productResponse = ProductResponse.from(product);
        List<ProductImageResponse> productImageResponse = productImages.stream()
                .map(ProductImageResponse::from)
                .collect(Collectors.toUnmodifiableList());
        List<ProductStatusResponse> productStatusResponse = productStatuses.stream()
                .map(ProductStatusResponse::from)
                .collect(Collectors.toUnmodifiableList());
        ProductStats stats = ProductStats.from(statsList);
        CategoryReadResponse categoryReadResponse = CategoryReadResponse.from(category);
        AddressResponse addressResponse = AddressResponse.from(address);
        return new ProductReadResponse(isSeller, productResponse, productImageResponse, productStatusResponse, stats,
                categoryReadResponse, addressResponse);
    }

    @Getter
    private static class ProductResponse {

        private final String title;
        private final String contents;
        private final Long price;
        private final Date createdTime; // 시간 타입 뭘로 할지 정해야 함
        private final Integer productStatus;

        @Builder
        private ProductResponse(String title, String contents, Long price, Date createdTime, Integer productStatus) {
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
                    .productStatus(product.getStatusId())
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

        private static ProductImageResponse from(ProductImage productImage) {
            return new ProductImageResponse(productImage.getId(), productImage.getUrl());
        }
    }

    @Getter
    private static class ProductStatusResponse {

        private final Integer id;
        private final String type;

        private ProductStatusResponse(Integer id, String type) {
            this.id = id;
            this.type = type;
        }

        private static ProductStatusResponse from(ProductStatus productStatus) {
            return new ProductStatusResponse(productStatus.getId(), productStatus.getType());
        }
    }

    @Getter
    private static class ProductStats {

        private final Integer viewCount;
        private final Integer wishCount;

        private ProductStats(Integer viewCount, Integer wishCount) {
            this.viewCount = viewCount;
            this.wishCount = wishCount;
        }

        private static ProductStats from(List<Integer> stats) {
            return new ProductStats(stats.get(0), stats.get(1));
        }
    }

    @Getter
    private static class AddressResponse {

        private final Long id;
        private final String name;

        private AddressResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        private static AddressResponse from(Address address) {
            return new AddressResponse(address.getId(), address.getName());
        }
    }
}
