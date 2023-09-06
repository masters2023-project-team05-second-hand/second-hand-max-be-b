package kr.codesquad.secondhand.api.product.dto;

import java.net.URL;
import java.util.List;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.product.domain.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class ProductCreateRequest {

    private final Long categoryId;
    private final Long addressId;
    private final String title;
    private final String content;
    private final Long price;
    private final List<MultipartFile> images;

    public Product toEntity(Member seller, Integer statusId, Address address, Category category,
                            URL thumbnailImgUrl) {
        return Product.builder()
                .seller(seller)
                .thumbnailImgUrl(thumbnailImgUrl)
                .category(category)
                .statusId(statusId)
                .address(address)
                .title(title)
                .content(content)
                .price(price)
                .build();
    }
}
