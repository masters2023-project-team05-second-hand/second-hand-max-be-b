package kr.codesquad.secondhand.api.jwt.repository;

import kr.codesquad.secondhand.api.jwt.domain.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<MemberRefreshToken, Long> {

}
