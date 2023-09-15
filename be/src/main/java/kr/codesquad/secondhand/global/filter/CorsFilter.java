package kr.codesquad.secondhand.global.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.codesquad.secondhand.global.util.HttpAuthorizationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsUtils;

public class CorsFilter implements Filter {

    private static final String ALLOWED_HEADERS = String.join(", ",
            "Authorization, x-requested-with, origin, content-type, accept",
            String.join(", ", HttpAuthorizationUtils.IP_CHECK_HEADERS));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);

        if (CorsUtils.isPreFlightRequest((HttpServletRequest) servletRequest)) {
            return;
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
