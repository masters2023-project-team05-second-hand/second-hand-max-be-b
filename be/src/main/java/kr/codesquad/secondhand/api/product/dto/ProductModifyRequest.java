package kr.codesquad.secondhand.api.product.dto;

import java.util.List;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ProductModifyRequest {
    private final Long categoryId;
    private final Long addressId;
    private final String title;
    private final String content;
    private final Long price;
    private final List<MultipartFile> newImages;
    private final List<Integer> deletedImgIds;

    public ProductModifyRequest(Long categoryId, Long addressId, String title, String content, Long price,
                                List<MultipartFile> newImages, List<Integer> deletedImgIds) {
        this.categoryId = categoryId;
        this.addressId = addressId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.newImages = newImages;
        this.deletedImgIds = deletedImgIds;
    }
}
