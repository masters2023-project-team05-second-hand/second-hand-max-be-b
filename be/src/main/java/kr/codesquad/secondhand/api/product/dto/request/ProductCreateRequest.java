package kr.codesquad.secondhand.api.product.dto.request;

import java.net.URL;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "등록할 상품의 카테고리 정보가 비어 있습니다.")
    private final Long categoryId;

    @NotNull(message = "등록할 상품의 동네 정보가 비어 있습니다.")
    private final Long addressId;

    @NotBlank(message = "제목이 비어있습니다.")
    private final String title;

    @NotBlank(message = "내용이 비어있습니다.")
    private final String content;

    private final Long price;

    // TODO Multipart는 Bean Validation이 안먹혀서 Spring이 제공하는 다른 방법이 있는지 확인 필요, 아님 직접 검증하는 코드 구현
//    @Valid
//    @NotEmpty(message = "상품 이미지는 최소 1개를 첨부해야 합니다.")
//    @Size(min = 1, max = 10, message = "상품 이미지는 최소 1개를 첨부해야 하며, 최대 10개까지 첨부할 수 있습니다.")
    private final List<MultipartFile> images;

    public Product toEntity(Member seller, Integer statusId, Address address, Category category, URL thumbnailImgUrl) {
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
