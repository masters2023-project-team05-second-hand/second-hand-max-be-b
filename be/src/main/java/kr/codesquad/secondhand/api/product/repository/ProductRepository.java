package kr.codesquad.secondhand.api.product.repository;

import kr.codesquad.secondhand.api.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
