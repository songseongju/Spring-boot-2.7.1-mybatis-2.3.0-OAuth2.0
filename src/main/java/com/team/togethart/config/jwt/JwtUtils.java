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
    //일반회원 토큰생성
    public String createAccessToken(String Togethart, int memberId, String memberEmail, String Username) {

        System.out.println("createAccessToken");

        //토큰 만료 시간 설정(access token)
        Date now = new Date();
        Date expiration = new Date(now.getTime() + ACCESS_TOKEN_VALIDATION_SECOND);

        //accessToken 생성 - 나중에 바로 RETURN값에 넣어주기
        String accessToken = Jwts.builder()
                .setSubject(Togethart)
                .claim("memberId", memberId)
                .claim("memberEmail",memberEmail)
                .claim("Username",Username)
                .setIssuedAt(now) //토큰발행일자
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();

        System.out.println("JwtUtils accessToken : " + accessToken);

        return accessToken;
    }


    public String createAccessToken2(String Togethart2, int memberId, String memberEmail, String Username) {

        System.out.println("createAccessToken2");

        //토큰 만료 시간 설정(access token)
        Date now = new Date();
        Date expiration = new Date(now.getTime() + ACCESS_TOKEN_VALIDATION_SECOND);

        //accessToken 생성 - 나중에 바로 RETURN값에 넣어주기
        String accessToken = Jwts.builder()
                .setSubject(Togethart2)
                .claim("memberId", memberId)
                .claim("memberEmail",memberEmail)
                .claim("Username",Username)
                .setIssuedAt(now) //토큰발행일자
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();

        System.out.println("JwtUtils accessToken2 : " + accessToken);

        return accessToken;
    }




    //토큰 유효성 검증 수행

  public boolean validateToken(String token) {

        System.out.println("validateToken");

        //토큰 파싱 후 발생하는 예외를 캐치하여 문제가 있으면 false, 정상이면 true 반환
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getRole(String token) {
        System.out.println("memberId");

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("memberId").toString();
    }

    public String getId(String token) {
        System.out.println("memberEmail");

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("memberEmail").toString();
    }

    public String getName(String token) {
        System.out.println("Username");

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("Username").toString();
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

}

