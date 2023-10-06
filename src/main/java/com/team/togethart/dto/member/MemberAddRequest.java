package com.team.togethart.dto.member;

import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Builder

public class MemberAddRequest {
    @Id
    private  int  memberId;
    private  String memberEmail;
    private  String memberPwd;
    private  String memberUsername;
    private  String memberRegiType;
    private String memberRegiDate;
    private  int memberAuth;



}
