package com.team.togethart.dto.subscription;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SubscriptionAuthRequest {
    private Long member_auth;

    private Long member_id;

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }


}
