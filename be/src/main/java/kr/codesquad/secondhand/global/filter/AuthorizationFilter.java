package kr.codesquad.secondhand.global.filter;

import static kr.codesquad.secondhand.global.util.HttpAuthorizationUtils.containsBearerToken;
import static kr.codesquad.secondhand.global.util.HttpAuthorizationUtils.extractAccessToken;
import static kr.codesquad.secondhand.global.util.HttpAuthorizationUtils.setAttributeFromToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.codesquad.secondhand.api.jwt.exception.BlackListedAccessTokenException;
import kr.codesquad.secondhand.api.jwt.exception.JwtExceptionType;
import kr.codesquad.secondhand.api.jwt.exception.TokenNotFoundException;
import kr.codesquad.secondhand.api.jwt.repository.TokenRedisRepository;
import kr.codesquad.secondhand.api.jwt.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter implements Filter {

    private static final String[] GET_WHITE_LIST = new String[]{
            // GET 요청 허용 URL 추가
            "/",
            "/api/addresses",
            "/api/categories",
            "/api/products*",
            "/api/statuses"
    };

    private static final String[] POST_WHITE_LIST = new String[]{
            // POST 요청 허용 URL 추가
            "/api/members/sign-in/*"
    };

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final TokenRedisRepository tokenRedisRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws
            ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (isGetRequestInWhiteList(httpServletRequest) || isPostRequestInWhiteList(httpServletRequest)) {
            // TODO 화이트리스트 + 로그인 경우 회원 정보를 담을 수 없어 임시 처리, 리팩토링 필요
            if (containsBearerToken(httpServletRequest)) {
                try {
                    setAttributesFromAccessToken(httpServletRequest);
                } catch (TokenNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!containsBearerToken(httpServletRequest)) {
            sendErrorResponse(httpServletResponse, new TokenNotFoundException());
            return;
        }

        try {
            if (tokenRedisRepository.isAccessTokenInBlackList(extractAccessToken(httpServletRequest))) {
                sendErrorResponse(httpServletResponse, new BlackListedAccessTokenException());
                return;
            }

            setAttributesFromAccessToken(httpServletRequest);
            chain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            sendErrorResponse(httpServletResponse, e);
        }
    }

    private boolean isGetRequestInWhiteList(HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURI();
        return HttpMethod.GET.matches(httpServletRequest.getMethod())
                && PatternMatchUtils.simpleMatch(GET_WHITE_LIST, uri);
    }

    private boolean isPostRequestInWhiteList(HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURI();
        return HttpMethod.POST.matches(httpServletRequest.getMethod())
                && PatternMatchUtils.simpleMatch(POST_WHITE_LIST, uri);
    }

    private void setAttributesFromAccessToken(HttpServletRequest httpServletRequest) throws TokenNotFoundException {
        String token = extractAccessToken(httpServletRequest);
        Claims claims = jwtProvider.getClaims(token);
        setAttributeFromToken(httpServletRequest, claims);
    }

    private void sendErrorResponse(HttpServletResponse response, Exception e) throws IOException {
        log.error(e.getMessage());
        e.printStackTrace();

        JwtExceptionType jwtExceptionType = JwtExceptionType.from(e);

        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(jwtExceptionType.getHttpStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString("[ERROR] " + jwtExceptionType.getMessage()));
    }
}
