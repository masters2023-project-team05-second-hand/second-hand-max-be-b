package kr.codesquad.secondhand.api.product.service;

import java.net.URL;
import java.util.List;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.address.service.AddressService;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.service.MemberService;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductImage;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import kr.codesquad.secondhand.api.product.dto.ProductCreateRequest;
import kr.codesquad.secondhand.api.product.dto.ProductCreateResponse;
import kr.codesquad.secondhand.api.product.dto.ProductReadResponse;
import kr.codesquad.secondhand.api.product.dto.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductFacadeService {

    private static final Integer THUMBNAIL_IMAGE_INDEX = 0;
    private static final Integer DEFAULT_PRODUCT_STATUS_ID = ProductStatus.FOR_SALE.getId();

    private final ProductService productService;
    private final ImageService imageService;
    private final AddressService addressService;
    private final MemberService memberService;
    private final StatService statService;

    @Transactional
    public ProductCreateResponse saveProduct(Long memberId, ProductCreateRequest productCreateRequest) {
        List<URL> imageUrls = imageService.uploadMultiImagesToS3(productCreateRequest.getImages());
        URL thumbnailImgUrl = imageService.resizeAndUploadToS3(imageUrls.get(THUMBNAIL_IMAGE_INDEX));
        Product product = toProduct(memberId, productCreateRequest, thumbnailImgUrl);

        // 주의: 상품 저장 시 순서(상품 Insert -> 상품 이미지 Insert), 순서 바뀌면 ProductImage 의 product_id 에서 NPE 발생함
        Long productId = productService.saveProduct(product);
        imageService.saveProductImages(productCreateRequest.getImages(), product);
        statService.saveNewProductStats(productId);
        return new ProductCreateResponse(productId);
    }

    private Product toProduct(Long memberId, ProductCreateRequest productCreateRequest, URL thumbnailImgUrl) {
        Member seller = memberService.getMemberReferenceById(memberId);
        Address address = addressService.getReferenceById(productCreateRequest.getAddressId());
        Category category = Category.from(productCreateRequest.getCategoryId());

        return productCreateRequest.toEntity(seller, DEFAULT_PRODUCT_STATUS_ID, address, category, thumbnailImgUrl);
    }

    @Transactional
    public ProductReadResponse readProduct(Long memberId, Long productId) {
        Product product = productService.findById(productId);
        boolean isSeller = product.isSellerIdEqualsTo(memberId);
        List<ProductImage> productImages = imageService.findAllByProductId(productId);
        List<ProductStatus> productStatuses = ProductStatus.findAll();
        List<Integer> stats = statService.findProductStats(memberId, productId);
        Category category = Category.from(product.getCategoryId());
        Address address = product.getAddress();
        return ProductReadResponse.of(isSeller, product, productImages, productStatuses, stats, category, address);
    }

    @Transactional
    public void updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
        Address address = addressService.getReferenceById(productUpdateRequest.getAddressId());
        Category categories = Category.from(productUpdateRequest.getCategoryId());
        Product product = productService.findById(productId);

        // 주의: 상품 수정 시 이미지 순서가 변경되기 때문에 먼저 이미지 전체를 업데이트하고 상품 업데이트를 해야 함
        updateProductImages(product, productUpdateRequest);
        URL thumbnailImgUrl = imageService.resizeAndUploadToS3(imageService.getThumbnailImgUrl(productId));
        product.updateProduct(productUpdateRequest.getTitle(), productUpdateRequest.getContent(), // TODO: title, content 순서 바뀌면 버그 나는데, 방지할 수 있는 방법이 있을지 고민중
                productUpdateRequest.getPrice(), address, categories, thumbnailImgUrl);
    }

    private void updateProductImages(Product product, ProductUpdateRequest productUpdateRequest) {
        List<MultipartFile> newImages = productUpdateRequest.getNewImages();
        List<Long> deleteImgIds = productUpdateRequest.getDeletedImgIds();
        imageService.updateImageUrls(product, newImages, deleteImgIds);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        // 주의: Entity 영속성으로 인해 순서 바뀌면 이미지 삭제 안됨(순서: 이미지 삭제 -> 상품 삭제)
        imageService.deleteProductImages(productId);
        productService.deleteProduct(productId);
    }
}
