package com.team.togethart.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MemberPwfindRequest {
    private String memberEmail;
    private String memberPwd;
    private String memberUsername;


}
