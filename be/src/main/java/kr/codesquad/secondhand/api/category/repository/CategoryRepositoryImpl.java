package kr.codesquad.secondhand.api.category.repository;

import kr.codesquad.secondhand.api.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositoryImpl extends JpaRepository<Category, Long> {
}
