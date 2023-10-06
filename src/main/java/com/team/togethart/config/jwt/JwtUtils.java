package com.team.togethart.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
@Service
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //accessToken 만료시간 설정
    public final static long ACCESS_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 12; //12시간

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public String createAccessToken(int memberId, String memberEmail, String Username) {

        System.out.println("createAccessToken");

        //토큰 만료 시간 설정(access token)
        Date now = new Date();
        Date expiration = new Date(now.getTime() + ACCESS_TOKEN_VALIDATION_SECOND);

        //accessToken 생성 - 나중에 바로 RETURN값에 넣어주기
        String accessToken = Jwts.builder()
                .setSubject(memberEmail)
                .claim("memberId", memberId)
                .claim("Username",Username)
                .setIssuedAt(now) //토큰발행일자
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();

        System.out.println("JwtUtils accessToken : " + accessToken);

        return accessToken;
    }

    //토큰 유효성 검증 수행
    public boolean validateToken(String token) {

        System.out.println("validateToken");

        //토큰 파싱 후 발생하는 예외를 캐치하여 문제가 있으면 false, 정상이면 true 반환
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        }
        catch(io.jsonwebtoken.security.SignatureException e) {
            System.out.printf("잘못된 토큰 서명입니다.");
            //logger.info("잘못된 토큰 서명입니다.");
        }catch(ExpiredJwtException e) {
            System.out.printf("만료된 토큰입니다.");
            //logger.info("만료된 토큰입니다.");
        }catch(IllegalArgumentException | MalformedJwtException e) {
            System.out.println("잘못된 토큰입니다.");
            //logger.info("잘못된 토큰입니다.");
        }return false;
    }

    public String getRole(String token) {
        System.out.println("getRoles");

        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("roles").toString();
    }

    public String getId(String token) {
        System.out.println("getId");

        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getName(String token) {
        System.out.println("getName");

        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("name").toString();
    }

    //request Header에서 access토큰 정보를 꺼내오기
    public String getAccessToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        System.out.println("JwtUtils getAccessToken : " + bearerToken);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    // role에 따라서 페이지 이동을 다르게 하는 메서드 - 비회원 , 회원
    public String authByRole(HttpServletRequest httpServletRequest ,String commonURI){
        String token = getAccessToken(httpServletRequest);
        if (token == null){
            return null;
        }else if(getRole(token).equals("1")){
            return commonURI;
        }
        return null;
    }
}

