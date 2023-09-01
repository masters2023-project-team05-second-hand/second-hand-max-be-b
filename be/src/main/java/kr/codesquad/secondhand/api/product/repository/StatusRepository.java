package kr.codesquad.secondhand.api.product.repository;

import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<ProductStatus, Long> {
    ProductStatus getReferenceByType(String type);
}
