package kr.codesquad.secondhand.api.product.repository;

import java.util.List;
import kr.codesquad.secondhand.api.product.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT product FROM Product product WHERE product.seller.id = :sellerId AND product.statusId IN :statusIds")
    Slice<Product> findBySellerIdAndStatusIds(@Param("sellerId") Long sellerId,
                                              @Param("statusIds") List<Integer> statusIds, Pageable pageRequest);
}
