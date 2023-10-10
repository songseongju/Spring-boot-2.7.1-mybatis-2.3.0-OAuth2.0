package com.team.togethart.dto.subscription;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SubscriptionImage {
    private long artworkId;

    private String resourcePathname;


}
