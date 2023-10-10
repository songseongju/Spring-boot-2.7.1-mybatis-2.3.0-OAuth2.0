package com.team.togethart.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.togethart.dto.member.GoogleOAuthResponseDTO;
import com.team.togethart.dto.member.GoogleProfile;
import com.team.togethart.dto.member.MemberAddRequest;
import com.team.togethart.repository.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

    @Autowired
    private final MemberMapper memberMapper;

    public ResponseEntity<?> getGoogleAccessToken(String accessCode) {


        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessCode);
        params.put("client_id", "567588959844-20n8u80tg9nf9i3ldkisv5nvo3o91cja.apps.googleusercontent.com");
        params.put("client_secret", "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");
        params.put("redirect_uri", "http://localhost:8080/auth/google/callback");
        params.put("grant_type", "authorization_code");

        // ResponseEntity<?> responseEntity=  restTemplate.postForEntity(GOOGLE_TOKEN_URL,params,String.class);

        ResponseEntity<GoogleOAuthResponseDTO> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, GoogleOAuthResponseDTO.class);


        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            return responseEntity;
        }

        return null;

    }

}
