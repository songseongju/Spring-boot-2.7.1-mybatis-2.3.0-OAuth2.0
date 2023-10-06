package com.team.togethart.dto.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SubscriptionAddRequest {

    private String subscribe_from;
    private String subscribe_to;
    private Date start_Date;
    private Date expire_Date;

}
