package kr.codesquad.secondhand.api.member.repository;

import java.util.List;
import kr.codesquad.secondhand.api.member.domain.MemberStatLogRedis;
import org.springframework.data.repository.CrudRepository;

public interface MemberStatLogRedisRepository extends CrudRepository<MemberStatLogRedis, Long> {

    List<MemberStatLogRedis> findAll();
}
