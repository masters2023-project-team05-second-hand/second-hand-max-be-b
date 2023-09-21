package kr.codesquad.secondhand.api.member.service;

import java.util.List;
import java.util.Optional;
import kr.codesquad.secondhand.api.member.domain.MemberStatLog;
import kr.codesquad.secondhand.api.member.repository.MemberStatLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberStatLogService {

    private final MemberStatLogRepository memberStatLogRepository;

    @Transactional
    public void saveAll(List<MemberStatLog> memberStatLogs) {
        memberStatLogRepository.saveAll(memberStatLogs);
    }

    public Optional<MemberStatLog> findById(Long id) {
        return memberStatLogRepository.findById(id);
    }
}
