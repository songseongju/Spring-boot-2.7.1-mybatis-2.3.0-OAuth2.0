package com.team.togethart.dto.member;

import lombok.Data;

@Data
public class MemberLoginRequest {
    private  String memberEmail;
    private  String memberPwd;
}
