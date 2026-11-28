package com.financialpilot.security;

import java.io.IOException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.financialpilot.util.JwtService;
import com.financialpilot.util.ResponseUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {
    "/users/*", "/budget/*", "/expenses/*",
    "/accounts/*", "/reports/*", "/summary/*"
})
public class BearerTokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI()
                .substring(httpRequest.getContextPath().length());

        boolean publicAuthRequest =
                "POST".equalsIgnoreCase(httpRequest.getMethod())
                && ("/users/login".equals(path)
                    || "/users/register".equals(path));

        if (publicAuthRequest) {
            chain.doFilter(request, response);
            return;
        }

        String authorization = httpRequest.getHeader("Authorization");
        if (authorization == null
                || !authorization.regionMatches(true, 0, "Bearer ", 0, 7)) {
            ResponseUtil.sendUnauthorized(httpResponse, "Bearer token required.");
            return;
        }

        String token = authorization.substring(7).trim();
        try {
            int userId = JwtService.verifyAccessToken(token);
            httpRequest.setAttribute("authenticatedUserId", userId);
            chain.doFilter(request, response);
        } catch (JWTVerificationException | IllegalArgumentException e) {
            ResponseUtil.sendUnauthorized(httpResponse, "Invalid or expired token.");
        }
    }
}