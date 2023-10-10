package com.team.togethart.dto.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GoogleOAuthResponseDTO {

    private String access_token;
    private String expires_in;
    private String scope;

    private String token_tpye;

    private String id_token;




}
