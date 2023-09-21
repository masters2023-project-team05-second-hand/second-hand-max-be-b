package kr.codesquad.secondhand.api.product.repository;

import kr.codesquad.secondhand.api.product.domain.ProductStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStatRepository extends JpaRepository<ProductStat, Long> {
}
