package kr.codesquad.secondhand.api.jwt.service;

import java.util.Collections;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.api.jwt.domain.MemberRefreshToken;
import kr.codesquad.secondhand.api.jwt.repository.TokenRepository;
import kr.codesquad.secondhand.api.jwt.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String MEMBER_ID = "memberId";

    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;

    @Transactional
    public Jwt issueJwt(Long memberId) {
        Jwt jwt = jwtProvider.createTokens(Collections.singletonMap(MEMBER_ID, memberId));
        if (tokenRepository.existsById(memberId)) {
            tokenRepository.deleteById(memberId);
        }
        tokenRepository.save(new MemberRefreshToken(memberId, jwt.getRefreshToken()));
        return jwt;
    }

    public String reissueAccessToken() {
        // TODO
        return null;
    }

}
