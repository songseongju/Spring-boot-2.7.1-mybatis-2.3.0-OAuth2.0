package com.team.togethart.dto.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SubscriptionDTO {

    private Long subscribeFrom;
    private Long subscribeTo;


}
