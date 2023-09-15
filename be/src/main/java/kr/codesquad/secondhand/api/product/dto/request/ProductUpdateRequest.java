package kr.codesquad.secondhand.api.product.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ProductUpdateRequest {

    @NotNull(message = "등록할 상품의 카테고리 정보가 비어 있습니다.")
    private final Long categoryId;

    @NotNull(message = "등록할 상품의 동네 정보가 비어 있습니다.")
    private final Long addressId;

    @NotEmpty(message = "제목이 비어있습니다.")
    private final String title;

    @NotEmpty(message = "내용이 비어있습니다.")
    private final String content;

    private final Long price;
    // TODO Multipart는 Bean Validation이 안먹혀서 Spring이 제공하는 다른 방법이 있는지 확인 필요, 아님 직접 검증하는 코드 구현
    private final List<MultipartFile> newImages;
    private final List<Long> deletedImageIds;

    public ProductUpdateRequest(Long categoryId, Long addressId, String title, String content, Long price,
                                List<MultipartFile> newImages, List<Long> deletedImageIds) {
        this.categoryId = categoryId;
        this.addressId = addressId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.newImages = newImages;
        this.deletedImageIds = deletedImageIds;
    }
}
