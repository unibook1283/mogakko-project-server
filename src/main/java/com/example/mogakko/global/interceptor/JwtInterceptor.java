package com.example.mogakko.global.interceptor;

import com.example.mogakko.domain.user.exception.UnauthorizedException;
import com.example.mogakko.domain.user.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("accessToken"); // client에서 요청할 때 header에 넣어둔 "jwt-auth-token"이라는 키 값을 확인
        if (token != null && jwtService.validateToken(token)) {
            return true;
        } else {
            throw new UnauthorizedException("유효한 인증토큰이 존재하지 않습니다.");
        }
    }
}
