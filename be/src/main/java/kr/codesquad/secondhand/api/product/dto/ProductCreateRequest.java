package kr.codesquad.secondhand.api.product.dto;

import java.util.List;
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
    private final Integer price;
    private final List<MultipartFile> images;

}
