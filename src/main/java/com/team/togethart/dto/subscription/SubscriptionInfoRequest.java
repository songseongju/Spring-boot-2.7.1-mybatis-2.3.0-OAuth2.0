package com.team.togethart.dto.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SubscriptionInfoRequest {

    private Long subscription_id;
    private Long subscribe_from;
    private Long subscribe_to;
    private LocalDateTime start_date;
    private LocalDateTime expire_date;
  

}
