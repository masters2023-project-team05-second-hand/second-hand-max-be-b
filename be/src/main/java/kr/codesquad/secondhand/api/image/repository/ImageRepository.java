package kr.codesquad.secondhand.api.image.repository;

import java.net.URL;
import java.util.List;
import kr.codesquad.secondhand.api.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllByProductId(Long ProductId);

    void deleteAllByProductId(Long productId);

    @Query(value = "select i.url from product_image i where i.product_id = ?1 order by id asc limit 1", nativeQuery = true)
    URL findMinByProductId(Long productId);

}
