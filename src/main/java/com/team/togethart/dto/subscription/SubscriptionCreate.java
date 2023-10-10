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
public class SubscriptionCreate {
    private String subscribeFrom;
    private String subscribeTo;
    private Date startDate;
    private Date expireDate;
}
