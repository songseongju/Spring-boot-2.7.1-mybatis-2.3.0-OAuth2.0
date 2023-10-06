package com.team.togethart.controller.member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.togethart.dto.member.KakaoProfile;
import com.team.togethart.dto.member.MemberAddRequest;
import com.team.togethart.dto.member.OAuthToken;
import com.team.togethart.service.MemberService;
import com.team.togethart.service.OAuthService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Builder
@RequestMapping("/auth")
public class OAuthController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberAddRequest memberAddRequest;


    @GetMapping ("/kakao/callback")
    public @ResponseBody String kakaocallback(String code) throws JsonProcessingException { //DATA를 리턴해주는 컨트롤러 함수

        //post방식으로 key = value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        //http body 오브젝트 생성
        HttpHeaders headers= new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","adbbf5ce440e188cfcb07e0628098935");
        params.add("redirect_uri","http://localhost:8080/auth/kakao/callback");
        params.add("code",code);

        //http Header와 HttpBody를 하나의 오브젝트에 담기

        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
                new HttpEntity<>(params,headers);

        // Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity <String> response = rt.exchange(
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
            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        System.out.println("카카오 엑세스 토큰:" + oauthToken.getAccess_token());

//  -------------------------------------------------------------------------------------------------//

        RestTemplate rt2= new RestTemplate();

        //http body 오브젝트 생성
        HttpHeaders headers2= new HttpHeaders();
        headers2.add("Authorization","Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //http Header와 HttpBody를 하나의 오브젝트에 담기.

        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답 받음.

        ResponseEntity <String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        System.out.println(response2.getBody());


        // DTO ObjectMapper 파싱.

        ObjectMapper objectMapper2 = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        // User 오브젝트 : username, password , email

        System.out.println("카카오 아이디 (번호): " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());


        System.out.println("togethart 유저네임 : " + "[kakao]" + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("togethart  이메일 : " + kakaoProfile.getKakao_account().getEmail());

        // UUID TemporaryPassword = UUID.randomUUID();

        String coskey = "cos1234";

        System.out.println("togethart  패스워드 : " + coskey);

        MemberAddRequest kakaoUser = MemberAddRequest.builder()
                .memberUsername("[kakao]"+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                .memberPwd(coskey)
                .memberRegiType("S")
                .memberEmail(kakaoProfile.getKakao_account().getEmail())
                .build();


        // 가입자 혹은 비가입자 체크 해서 처리

        MemberAddRequest originUser = memberService.findMember(kakaoUser.getMemberUsername());

       if(originUser == null) {
           System.out.println("기존회원이 아니므로 자동으로 회원가입이 됩니다.");
            memberService.register2(kakaoUser);
        }

        //기존회원인 경우 로그인 처리 메소드 처리



        System.out.println("자동 로그인 이 됩니다.");


        // memberService.register2(kakaoUser);

        return "회원가입 및 로그인처리완료";

    }

}
