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
    Slice<Product> findByCategoryIdAndIdIn(@Param("id") List<Long> wishlistId,
                                           @Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT DISTINCT product.categoryId FROM Product product WHERE product.id IN :id")
    List<Long> findCategoryIdsByIdIn(@Param("id") List<Long> wishlistId);

    /**
     * @param cursor 0은 첫 조회 요청을 의미
     * @param categoryId 0은 전체 카테고리 요청을 의미
     */
    @Query("SELECT product FROM Product product "
            + "WHERE product.address.id = :addressId "
            + "AND (:cursor = 0L OR product.id < :cursor) "
            + "AND (:categoryId = 0L OR product.categoryId = :categoryId) "
            + "ORDER BY product.id DESC"
    )
    Slice<Product> findByAddressIdAndCategoryId(@Param("cursor") Long cursor, @Param("addressId") Long addressId,
                                                @Param("categoryId") Long categoryId, Pageable pageable);

}
