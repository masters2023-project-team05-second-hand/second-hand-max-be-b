package kr.codesquad.secondhand.api.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import java.util.Collections;
import java.util.Date;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.api.jwt.domain.MemberRefreshToken;
import kr.codesquad.secondhand.api.jwt.exception.ExpiredRefreshTokenException;
import kr.codesquad.secondhand.api.jwt.repository.TokenRedisRepository;
import kr.codesquad.secondhand.api.jwt.repository.TokenRepository;
import kr.codesquad.secondhand.api.jwt.util.JwtProvider;
import kr.codesquad.secondhand.api.member.exception.InvalidRefreshTokenException;
import kr.codesquad.secondhand.api.member.exception.NotSignedInException;
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
        deleteRefreshToken(memberId);
        tokenRepository.save(new MemberRefreshToken(memberId, jwt.getRefreshToken()));
        return jwt;
    }

    public String reissueAccessToken(Long memberId, String refreshToken) {
        MemberRefreshToken memberRefreshToken = tokenRepository.findById(memberId)
                .orElseThrow(NotSignedInException::new);

        if (!memberRefreshToken.matches(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        try { // TODO RT 만료를 try-catch 말고 깔끔하게 처리할 수 있는 방법이 있을지?
            jwtProvider.getClaims(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException();
        }

        return jwtProvider.reissueAccessToken(Collections.singletonMap(MEMBER_ID, memberId));
    }

    public void deleteRefreshToken(Long memberId) {
        if (tokenRepository.existsById(memberId)) {
            tokenRepository.deleteById(memberId);
        }
    }

    public void addAccessTokenToBlackList(String accessToken) {
        Date expiration = jwtProvider.getExpiration(accessToken);
        Date currentDate = new Date();

        long ttlMillis = expiration.getTime() - currentDate.getTime(); // TODO 서버 시간과 클라이언트 시간의 불일치 문제 해결 필요
        long ttlMinutes = (ttlMillis + BUFFER_TIME_IN_MILLIS) / 1000 / 60;

        tokenRedisRepository.addAccessTokenToBlackList(accessToken, ttlMinutes);
    }

    public boolean isMemberRefreshToken(Long memberId, String refreshToken) {
        MemberRefreshToken memberRefreshToken = tokenRepository.findById(memberId)
                .orElseThrow(NotSignedInException::new);
        return memberRefreshToken.matches(refreshToken);
    }
}
