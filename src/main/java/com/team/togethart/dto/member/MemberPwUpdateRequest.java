package com.team.togethart.dto.member;

import lombok.Data;

@Data
public class MemberPwUpdateRequest {

    private String memberEmail;
    private String pwd;
    private String newPwd;
    private String newPwdCheck;


}
