package kr.codesquad.secondhand.api.product.repository;

import kr.codesquad.secondhand.api.product.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
