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
import kr.codesquad.secondhand.api.product.domain.ProductImage;
import kr.codesquad.secondhand.api.product.domain.Status;
import kr.codesquad.secondhand.api.product.dto.ProductCreateRequest;
import kr.codesquad.secondhand.api.product.dto.ProductCreateResponse;
import kr.codesquad.secondhand.api.product.dto.ProductModifyRequest;
import kr.codesquad.secondhand.api.product.dto.ProductReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductFacadeService {

    private final ProductService productService;
    private final ImageService imageService;
    private final AddressRepositoryImpl addressRepository;
    private final CategoryRepositoryImpl categoryRepository;
    private final MemberService memberService;

    @Transactional
    public ProductReadResponse readProduct(long memberId, Long productId) {
        Product product = productService.findById(productId);
        List<ProductImage> productImages = imageService.findAllByProductId(productId);
        boolean isSeller = product.isSellerIdEqualsTo(memberId);
        List<Status> statuses = Status.findAll();

        return ProductReadResponse.of(isSeller, product, productImages, statuses);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        // 주의: Entity 영속성으로 인해 순서 바뀌면 이미지 삭제 안됨
        imageService.deleteProductImages(productId);
        productService.deleteProduct(productId);
    }

    @Transactional
    public ProductCreateResponse saveProduct(Long memberId, ProductCreateRequest productCreateRequest)
            throws IOException {
        List<URL> imageUrls = imageService.uploadMultiImagesToS3(productCreateRequest.getImages());
        URL thumbnailImgUrl = imageUrls.get(0); // 임시 썸네일 이미지
        Member seller = memberService.getMemberReferenceById(memberId);
        Address address = addressRepository.getReferenceById(productCreateRequest.getAddressId());
        Category category = categoryRepository.getReferenceById(productCreateRequest.getCategoryId());
        Integer statusId = Status.FOR_SALE.getId();
        Product product = productCreateRequest.toEntity(seller, statusId, address, category, thumbnailImgUrl);

        productService.saveProduct(product);
        imageService.saveAll(imageUrls, product);
        return new ProductCreateResponse(product.getId());
    }

    @Transactional
    public void updateProduct(Long productId, ProductModifyRequest productModifyRequest) throws IOException {
        Product product = productService.findById(productId);
        List<MultipartFile> newImages = productModifyRequest.getNewImages();
        List<Integer> deleteImgIds = productModifyRequest.getDeletedImgIds();
        Address address = addressRepository.getReferenceById(productModifyRequest.getAddressId());
        Category category = categoryRepository.getReferenceById(productModifyRequest.getCategoryId());
        imageService.updateImageUrls(product, newImages, deleteImgIds);
        URL thumbnailImgUrl = imageService.getThumbnailImgUrl(productId);

        productService.updateProduct(product, productModifyRequest.getTitle(), productModifyRequest.getContent(),
                productModifyRequest.getPrice(), address, category, thumbnailImgUrl);
    }
}
