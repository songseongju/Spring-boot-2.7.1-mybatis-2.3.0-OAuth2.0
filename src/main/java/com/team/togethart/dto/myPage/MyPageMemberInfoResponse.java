package com.team.togethart.dto.myPage;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class MyPageMemberInfoResponse {

    private Long memberId;
    private String memberUsername;
    private Date memberRegiDate;
    private Date memberBirth;
    private String memberImage;
    private String memberIntro;

}
