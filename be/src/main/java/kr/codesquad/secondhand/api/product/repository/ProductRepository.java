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
                                              @Param("statusIds") List<Integer> statusIds, Pageable pageable);

    @Query("SELECT product FROM Product product WHERE (:categoryId = 0L OR product.categoryId = :categoryId) AND product.id IN :id")
    Slice<Product> findByCategoryIdAndIdIn(@Param("id") List<Long> id,
                                           @Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT DISTINCT product.categoryId FROM  Product product WHERE product.id IN :id")
    List<Long> findCategoryIdsByIdIn(@Param("id") List<Long> id);
}
