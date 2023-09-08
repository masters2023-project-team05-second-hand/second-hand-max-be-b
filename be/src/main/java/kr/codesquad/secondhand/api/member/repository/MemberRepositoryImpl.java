package kr.codesquad.secondhand.api.member.repository;

import java.util.Optional;
import kr.codesquad.secondhand.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final JpaMemberRepository repository;

    @Override
    public Long save(Member member) {
        return repository.save(member).getId();
    }

    @Override
    public Optional<Member> findBySignInTypeIdAndEmail(Long signInTypeId, String email) {
        return repository.findBySignInTypeIdAndEmail(signInTypeId, email);
    }

    public Member getReferenceById(Long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return repository.findById(id);
    }

}
