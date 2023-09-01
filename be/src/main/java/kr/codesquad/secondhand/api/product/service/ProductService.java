package kr.codesquad.secondhand.api.product.service;

import com.amazonaws.services.s3.AmazonS3;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.category.repository.CategoryRepositoryImpl;
import kr.codesquad.secondhand.api.member.domain.Address;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.repository.AddressRepositoryImpl;
import kr.codesquad.secondhand.api.member.service.MemberService;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import kr.codesquad.secondhand.api.product.dto.ProductCreateRequest;
import kr.codesquad.secondhand.api.product.dto.ProductCreateResponse;
import kr.codesquad.secondhand.api.product.dto.ProductModifyRequest;
import kr.codesquad.secondhand.api.product.repository.ProductRepository;
import kr.codesquad.secondhand.api.product.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final AmazonS3 amazonS3;
    private final MemberService memberService;
    private final StatusRepository statusRepository;
    private final ProductRepository productRepository;
    private final AddressRepositoryImpl addressRepository;
    private final CategoryRepositoryImpl categoryRepository;
    private final ImageService imageService;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public ProductCreateResponse save(ProductCreateRequest productCreateRequest, Long memberId) throws IOException {
        //  TODO 썸네일 리사이징 기능 구현
        // 임시 썸네일 이미지
        List<URL> imageUrls = imageService.uploadMultiImagesToS3(productCreateRequest.getImages());
        URL thumbnailImgUrl = imageUrls.get(0);
        Member seller = memberService.getMemberReferenceById(memberId);
//        id가 아닌 type으로 조회할 경우 쿼리가 발생한다. (쿼리 메소드가 아닌 인터페이스에 추가한 메소드라서?)
//        signInType 처럼 인메모리에 캐싱할 수 있게 리팩토링 필요
        ProductStatus status = statusRepository.getReferenceByType("판매중");
        Address address = addressRepository.getReferenceById(productCreateRequest.getAddressId());
        Category category = categoryRepository.getReferenceById(productCreateRequest.getCategoryId());
        Product product = productCreateRequest.toEntity(seller, status, address, category, thumbnailImgUrl);
        productRepository.save(product);
        imageService.saveAll(imageUrls, product);
        return new ProductCreateResponse(product.getId());
    }

    @Transactional
    public void modifyProduct(Long productId, ProductModifyRequest productModifyRequest) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow();
        URL thumbnailImgUrl = imageService.updateImageUrls(product, productModifyRequest.getNewImages(),
                productModifyRequest.getDeletedImgIds());
        Address address = addressRepository.getReferenceById(productModifyRequest.getAddressId());
        Category category = categoryRepository.getReferenceById(productModifyRequest.getCategoryId());
        product.updateProduct(productModifyRequest, address, category, thumbnailImgUrl);
    }
}
