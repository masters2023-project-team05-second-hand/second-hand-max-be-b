package kr.codesquad.secondhand.api.jwt.service;

import java.util.Collections;
import java.util.Date;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.api.jwt.domain.MemberRefreshToken;
import kr.codesquad.secondhand.api.jwt.repository.TokenRedisRepository;
import kr.codesquad.secondhand.api.jwt.repository.TokenRepository;
import kr.codesquad.secondhand.api.jwt.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String MEMBER_ID = "memberId";
    private static final long BUFFER_TIME_IN_MILLIS = 60 * 1000;

    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;
    private final TokenRedisRepository tokenRedisRepository;

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

    public void addAccessTokenToBlackList(String accessToken) {
        Date expiration = jwtProvider.getExpiration(accessToken);
        Date currentDate = new Date();

        long ttlMillis = expiration.getTime() - currentDate.getTime(); // TODO 서버 시간과 클라이언트 시간의 불일치 문제 해결 필요
        long ttlMinutes = (ttlMillis + BUFFER_TIME_IN_MILLIS) / 1000 / 60;

        tokenRedisRepository.addAccessTokenToBlackList(accessToken, ttlMinutes);
    }
}
