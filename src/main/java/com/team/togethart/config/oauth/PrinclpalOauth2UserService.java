package com.team.togethart.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrinclpalOauth2UserService extends DefaultOAuth2UserService {


    //구글로 부터 받은 userRequest 데이터에 대한 후처리되는 메소드

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        System.out.println("getClientRegistration : " + userRequest.getClientRegistration()); //registrationId로 어떤 OAuth로 로그인 했는지 가능
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());

        // 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code를 리턴(OAuth-Client라이브러리) -> AccessToken요청
        // userRequest정보 -> loadUser 메소드 호출-> (구글)회원프로필 받아준다.
        System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());


        OAuth2User oauth2User = super.loadUser(userRequest);

        //회원가입을 강제로 진행해볼 예정
        return super.loadUser(userRequest);

    }


}
