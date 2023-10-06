package kr.codesquad.secondhand.api.product.service;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.address.service.AddressService;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.chat.service.ChatRoomService;
import kr.codesquad.secondhand.api.image.service.ImageService;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.service.MemberService;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStats;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import kr.codesquad.secondhand.api.product.dto.ProductStatusesInfoResponse;
import kr.codesquad.secondhand.api.product.dto.request.ProductCreateRequest;
import kr.codesquad.secondhand.api.product.dto.request.ProductStatusUpdateRequest;
import kr.codesquad.secondhand.api.product.dto.request.ProductUpdateRequest;
import kr.codesquad.secondhand.api.product.dto.response.ProductCreateResponse;
import kr.codesquad.secondhand.api.product.dto.response.ProductReadResponse;
import kr.codesquad.secondhand.api.product.dto.response.ProductSlicesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
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
    private final ChatRoomService chatRoomService;

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
        statService.increaseViews(memberId, productId);
        return toProductReadResponse(productId);
    }

    @Transactional
    public ProductReadResponse readProduct(String clientIP, Long productId) {
        statService.increaseViews(clientIP, productId);
        return toProductReadResponse(productId);
    }

    private ProductReadResponse toProductReadResponse(Long productId) {
        return ProductReadResponse.of(
                productService.findById(productId),
                imageService.findAllByProductId(productId),
                readProductStats(productId),
                Category.from(productService.findById(productId).getCategoryId()),
                productService.findById(productId).getAddress()
        );
    }

    // TODO setter로 임시처리한 로직 개선 필요
    private ProductStats readProductStats(Long productId) {
        ProductStats productStats = statService.findProductStats(productId);
        int chatCount = chatRoomService.countChatRoomsBy(productId);
        productStats.setChatCount(chatCount);
        return productStats;
    }

    public List<ProductStatusesInfoResponse> readProductStatuses() {
        List<ProductStatus> productStatuses = ProductStatus.findAll();
        return ProductStatusesInfoResponse.from(productStatuses);
    }

    @Transactional
    public ProductSlicesResponse readProducts(Long cursor, Long addressId, Long categoryId, Integer size) {
        Slice<Product> productSlices = productService.findByAddressIdAndCategoryId(
                cursor, addressId, categoryId, size
        );

        List<Product> products = productSlices.getContent();
        Boolean hasNext = productSlices.hasNext();
        Map<Long, ProductStats> productStats = products.stream()
                .collect(Collectors.toUnmodifiableMap(
                        product -> product.getId(),
                        product -> readProductStats(product.getId()))
                );

        return ProductSlicesResponse.of(products, productStats, hasNext);
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
        List<Long> deleteImgIds = productUpdateRequest.getDeletedImageIds();
        imageService.updateImageUrls(product, newImages, deleteImgIds);
    }

    @Transactional
    public void updateProductStatus(Long productId, ProductStatusUpdateRequest request) {
        ProductStatus productStatus = ProductStatus.from(request.getStatusId());
        productService.updateProductStatus(productId, productStatus);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        // 주의: Entity 영속성으로 인해 순서 바뀌면 이미지 삭제 안됨(순서: 이미지 삭제 -> 상품 삭제)
        imageService.deleteProductImages(productId);
        productService.deleteProduct(productId);
    }
}
