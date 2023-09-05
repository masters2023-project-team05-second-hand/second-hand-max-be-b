package kr.codesquad.secondhand.api.product.service;

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
import kr.codesquad.secondhand.api.product.domain.Status;
import kr.codesquad.secondhand.api.product.dto.ProductCreateRequest;
import kr.codesquad.secondhand.api.product.dto.ProductCreateResponse;
import kr.codesquad.secondhand.api.product.dto.ProductModifyRequest;
import kr.codesquad.secondhand.api.product.dto.ProductStatusUpdateRequest;
import kr.codesquad.secondhand.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final MemberService memberService;
    private final ProductRepository productRepository;
    private final AddressRepositoryImpl addressRepository;
    private final CategoryRepositoryImpl categoryRepository;

    private final ImageService imageService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public ProductCreateResponse saveProduct(ProductCreateRequest productCreateRequest, Long memberId) throws IOException {
        //  TODO 썸네일 리사이징 기능 구현
        List<URL> imageUrls = imageService.uploadMultiImagesToS3(productCreateRequest.getImages());
        URL thumbnailImgUrl = imageUrls.get(0); // 임시 썸네일 이미지
        Member seller = memberService.getMemberReferenceById(memberId);
        Address address = addressRepository.getReferenceById(productCreateRequest.getAddressId());
        Category category = categoryRepository.getReferenceById(productCreateRequest.getCategoryId());
        Integer statusId = Status.FOR_SALE.getId();
        Product product = productCreateRequest.toEntity(seller, statusId, address, category, thumbnailImgUrl);
        productRepository.save(product);
        imageService.saveAll(imageUrls, product);
        return new ProductCreateResponse(product.getId());
    }

    @Transactional
    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    @Transactional
    public void updateProduct(Long productId, ProductModifyRequest productModifyRequest) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow();
        URL thumbnailImgUrl = imageService.updateImageUrls(product, productModifyRequest.getNewImages(),
                productModifyRequest.getDeletedImgIds());
        Address address = addressRepository.getReferenceById(productModifyRequest.getAddressId());
        Category category = categoryRepository.getReferenceById(productModifyRequest.getCategoryId());
        product.updateProduct(productModifyRequest, address, category, thumbnailImgUrl);
    }

    @Transactional
    public void updateProductStatus(Long productId, ProductStatusUpdateRequest request) {
        Status status = Status.from(request.getStatusId());
        Product product = productRepository.findById(productId).orElseThrow();
        product.updateStatus(status);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
