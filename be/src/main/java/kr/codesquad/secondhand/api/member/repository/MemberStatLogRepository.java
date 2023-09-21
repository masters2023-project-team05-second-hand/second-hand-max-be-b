package kr.codesquad.secondhand.api.member.repository;

import kr.codesquad.secondhand.api.member.domain.MemberStatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberStatLogRepository extends JpaRepository<MemberStatLog, Long> {
}
