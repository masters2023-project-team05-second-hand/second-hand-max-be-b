package kr.codesquad.secondhand.api.member.service;

import java.util.List;
import java.util.Map;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.category.dto.CategorySummaryResponse;
import kr.codesquad.secondhand.api.member.dto.response.ProductWishStatusResponse;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStat;
import kr.codesquad.secondhand.api.product.dto.response.ProductSlicesResponse;
import kr.codesquad.secondhand.api.product.service.ProductService;
import kr.codesquad.secondhand.api.product.service.ProductStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberProductFacadeService {

    private final ProductService productService;
    private final ProductStatService productStatService;
    private final MemberStatLogRedisService memberStatLogRedisService;


    @Transactional
    public ProductSlicesResponse readMemberSales(Long memberId, List<Integer> statusIds, Integer page, Integer size) {
        Sort sort = Sort.by(Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Slice<Product> productSlices = productService.findBySellerIdAndStatusIds(memberId, statusIds, pageRequest);

        List<Product> products = productSlices.getContent();
        Boolean hasNext = productSlices.hasNext();
        Map<Long, ProductStat> productStats = productStatService.findProductsStats(products);

        return ProductSlicesResponse.of(products, productStats, hasNext);
    }

    @Transactional
    public ProductSlicesResponse readMemberWishlist(Long memberId, Long categoryId, Integer page, Integer size) {
        Sort sort = Sort.by(Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        List<Long> wishlist = memberStatLogRedisService.findWishlistByMemberId(memberId);
        Slice<Product> productSlices = productService.findWishedProductByCategoryIdAndIdIn(wishlist, categoryId,
                pageRequest);

        List<Product> products = productSlices.getContent();
        Boolean hasNext = productSlices.hasNext();
        Map<Long, ProductStat> productStats = productStatService.findProductsStats(products);

        return ProductSlicesResponse.of(products, productStats, hasNext);
    }

    @Transactional
    public List<CategorySummaryResponse> readMemberWishCategories(Long memberId) {
        List<Long> wishlist = memberStatLogRedisService.findWishlistByMemberId(memberId);
        List<Long> categoryIds = productService.findCategoryIdsByIdIn(wishlist);
        List<Category> categories = Category.from(categoryIds);

        return CategorySummaryResponse.from(categories);
    }

    @Transactional
    public ProductWishStatusResponse checkProductWishedStatus(Long memberId, Long productId) {
        Boolean isWished = memberStatLogRedisService.containsWishedProductId(memberId, productId);
        return new ProductWishStatusResponse(isWished);
    }
}
