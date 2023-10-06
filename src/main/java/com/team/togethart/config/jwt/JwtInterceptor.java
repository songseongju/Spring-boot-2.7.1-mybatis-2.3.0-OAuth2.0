package com.team.togethart.config.jwt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 토큰 받기
        System.out.println("preHandle 실행!!!!!!!!!");
        String accessToken = jwtUtils.getAccessToken(request);
        System.out.println("Interceptor accessToken : " + accessToken);
        // 로깅용 URI
        String requestURI = request.getRequestURI();

        // 비회원일 때
        if (accessToken == null) {
            logger.debug("비회원 유저입니다 URI: {}", requestURI);
            System.out.println("비회원"+requestURI);
            return true;
        } else { // 액세스, 리프래쉬 토큰 둘 다 있을 때
            logger.debug("access 존재합니다.");
            System.out.println("존재합니다"+requestURI);
            if (jwtUtils.validateToken(accessToken)) {
                //accesstoke이 유효할 때
                logger.debug("유효한 access 토큰 정보입니다. URI: {}", requestURI);
                System.out.println("유효"+requestURI);
                return true;
            } else {
                //둘 다 유효하지 않을 때
                logger.debug("유효하지 않은 JWT 토큰입니다. uri: {}", requestURI);
                System.out.println("유효하지않음"+requestURI);
                return false;
            }
        }
    }
}
