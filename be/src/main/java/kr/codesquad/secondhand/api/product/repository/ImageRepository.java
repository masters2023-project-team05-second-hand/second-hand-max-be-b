package kr.codesquad.secondhand.api.product.repository;

import java.net.URL;
import java.util.List;
import kr.codesquad.secondhand.api.product.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByProductId(Long ProductId);

    @Query(value = "select i.url from product_image i where i.product_id = ?1 order by id asc limit 1", nativeQuery = true)
    URL findMinByProductId(Long productId);

}
