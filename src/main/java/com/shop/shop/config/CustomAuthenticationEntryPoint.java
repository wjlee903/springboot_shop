package com.shop.shop.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ajax의 경우 http request header에 XMLHttpRequest라는 값이 세팅퇴어 요청이 옴
 * 인증되지 않은 사용자가 ajax로 리소스를 요청할 경우 "Unauthorized" 에러를 발생
 * 나머지경우는 로그인 페이지로 리다이렉트
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

}
