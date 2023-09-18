package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import kr.codesquad.secondhand.api.product.domain.ProductStat;
import kr.codesquad.secondhand.api.product.repository.ProductStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductStatService {

    private final ProductStatRepository productStatRepository;

    @Transactional
    public void saveAll(List<ProductStat> productStats) {
        productStatRepository.saveAll(productStats);
    }
}
