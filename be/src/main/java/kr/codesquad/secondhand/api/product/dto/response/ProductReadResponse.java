package kr.codesquad.secondhand.api.product.dto.response;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.category.dto.CategorySummaryResponse;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductImage;
import kr.codesquad.secondhand.api.product.domain.ProductStats;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductReadResponse {

    private final ProductResponse product;
    private final List<ProductImageResponse> images;
    private final ProductStatsResponse stats;

    @Builder
    private ProductReadResponse(ProductResponse product, List<ProductImageResponse> images,
                                ProductStatsResponse stats) {
        this.product = product;
        this.images = images;
        this.stats = stats;
    }

    public static ProductReadResponse of(Product product, List<ProductImage> productImages, ProductStats stats,
                                         Category category, Address address) {
        return ProductReadResponse.builder()
                .product(ProductResponse.from(product, CategorySummaryResponse.from(category),
                        AddressResponse.from(address), SellerResponse.from(product.getSeller())))
                .images(ProductImageResponse.from(productImages))
                .stats(ProductStatsResponse.from(stats))
                .build();
    }

    @Getter
    private static class ProductResponse {

        private final SellerResponse seller;
        private final CategorySummaryResponse category;
        private final AddressResponse address;
        private final String title;
        private final String contents;
        private final Long price;
        private final Date createdTime; // 시간 타입 뭘로 할지 정해야 함
        private final Integer status;

        @Builder
        private ProductResponse(SellerResponse seller, CategorySummaryResponse category, AddressResponse address,
                                String title, String contents, Long price, Date createdTime, Integer status) {
            this.seller = seller;
            this.category = category;
            this.address = address;
            this.title = title;
            this.contents = contents;
            this.price = price;
            this.createdTime = createdTime;
            this.status = status;
        }

        private static ProductResponse from(Product product, CategorySummaryResponse category, AddressResponse address,
                                            SellerResponse sellerResponse) {
            return ProductResponse.builder()
                    .seller(sellerResponse)
                    .category(category)
                    .address(address)
                    .title(product.getTitle())
                    .contents(product.getContent())
                    .price(product.getPrice())
                    .createdTime(product.getCreatedTime())
                    .status(product.getStatusId())
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

        private static List<ProductImageResponse> from(List<ProductImage> productImages) {
            return productImages.stream()
                    .map(ProductImageResponse::from)
                    .collect(Collectors.toUnmodifiableList());
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

    @Getter
    private static class SellerResponse {

        private final Long id;
        private final String nickname;

        public SellerResponse(Long id, String nickname) {
            this.id = id;
            this.nickname = nickname;
        }

        private static SellerResponse from(Member member) {
            return new SellerResponse(member.getId(), member.getNickname());
        }
    }
}
