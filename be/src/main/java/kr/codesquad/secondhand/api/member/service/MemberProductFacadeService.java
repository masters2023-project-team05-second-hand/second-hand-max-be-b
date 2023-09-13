package kr.codesquad.secondhand.api.member.service;

import java.util.List;
import java.util.Map;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStats;
import kr.codesquad.secondhand.api.product.dto.ProductSlicesResponse;
import kr.codesquad.secondhand.api.product.service.ProductService;
import kr.codesquad.secondhand.api.product.service.StatService;
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
    private final StatService statService;

    @Transactional
    public ProductSlicesResponse readMemberSales(Long memberId, List<Integer> statusIds, Integer page, Integer size) {
        Sort sort = Sort.by(Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Slice<Product> productSlices = productService.findBySellerIdAndStatusIds(memberId, statusIds, pageRequest);

        List<Product> products = productSlices.getContent();
        Boolean hasNext = productSlices.hasNext();
        Map<Long, ProductStats> productStats = statService.findProductsStats(products);

        return ProductSlicesResponse.of(products, productStats, hasNext);
    }
}
