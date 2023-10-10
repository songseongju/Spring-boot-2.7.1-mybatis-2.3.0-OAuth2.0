package com.team.togethart.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.togethart.config.jwt.JwtUtils;
import com.team.togethart.dto.member.GoogleProfile;
import com.team.togethart.dto.member.KakaoProfile;
import com.team.togethart.dto.member.MemberAddRequest;
import com.team.togethart.dto.member.OAuthToken;
import com.team.togethart.repository.member.MemberMapper;
import com.team.togethart.service.MemberService;
import com.team.togethart.service.OAuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class OAuthController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MemberService memberService;
    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private MemberMapper memberMapper;

    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaocallback(String code) throws JsonProcessingException, URISyntaxException {

        //post방식으로 key = value 데이터를 요청 (카카오쪽으로)

        RestTemplate rt = new RestTemplate();

        //http body 오브젝트 생성

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "");
        params.add("redirect_uri", "");
        params.add("code", code);

        //http Header와 HttpBody를 하나의 오브젝트에 담기

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답 받음.

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // DTO ObjectMapper 파싱

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("카카오 엑세스 토큰:" + oauthToken.getAccess_token());


        //  -------------------------------------------------------------------------------------------------//

        RestTemplate rt2 = new RestTemplate();

        //http body 오브젝트 생성

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //http Header와 HttpBody를 하나의 오브젝트에 담기.

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답 받음.

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
        System.out.println(response2.getBody());
        // DTO ObjectMapper 파싱.

        ObjectMapper objectMapper2 = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // User 오브젝트 : username, password , email
        System.out.println("카카오 아이디 (번호): " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("togethart 유저네임 : " + "[kakao]" + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        System.out.println("togethart  이메일 : " + kakaoProfile.getKakao_account().getEmail());
        UUID TemporaryPassword = UUID.randomUUID();
        System.out.println("togethart  패스워드 : " + TemporaryPassword);
        MemberAddRequest kakaoUser = MemberAddRequest.builder()
                .memberUsername("[kakao]" + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .memberPwd(TemporaryPassword.toString())
                .memberRegiType("K")
                .memberEmail(kakaoProfile.getKakao_account().getEmail())
                .build();
        // 가입자 확인 . 없을경우 자동 회원가입 DB에 저장 후 jwt 토큰 발급 안시켜주고 메인페이지 이동

        MemberAddRequest originUser = memberService.findMember(kakaoUser.getMemberUsername());
        if (originUser == null) {
            System.out.println("기존회원이 아니므로 자동으로 회원가입이 됩니다.");
            memberService.kakaoregister(kakaoUser);
            System.out.println("회원가입 완료 메인페이지에서 다시 로그인 해주세요.");
            HttpHeaders headers3 = new HttpHeaders();
            headers3.setLocation(URI.create("/"));
            return new ResponseEntity<> (headers3, HttpStatus.MOVED_PERMANENTLY);

        }
        // 위에 if null 값 으로 회원아닌 사람들 걸러 낸 후 기존 있던 회원 이므로 jwt 토큰 발급 후 자동 로그인

            String accessToken = jwtUtils.createAccessToken2("Togethart", originUser.getMemberId()
                    , kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
            httpServletResponse.setHeader("Authorization", "Bearer " + accessToken);
            Map<String, String> result = new HashMap<>();
            result.put("jwtToken", accessToken);
            System.out.println("kakao JWT token :" + accessToken);
            System.out.println("이전에 가입한 회원입니다. 자동 로그인이 됩니다.");

            HttpHeaders headers4 = new HttpHeaders();
            headers4.add("Authorization", "Bearer " + accessToken);
            headers4.setLocation(URI.create("/"));
            return new ResponseEntity<> (headers4, HttpStatus.MOVED_PERMANENTLY);
        }

// ------------------------------------------- Google 소셜 로그인 --------------------------------------------------------------------------------------

   @GetMapping("/google/callback")

    public ResponseEntity<?> successGoogleLogin(@RequestParam("code") String accessCode) {

        return oAuthService.getGoogleAccessToken(accessCode);

        }
}








