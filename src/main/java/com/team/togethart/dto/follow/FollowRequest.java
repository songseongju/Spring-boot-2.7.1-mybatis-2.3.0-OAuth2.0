package com.team.togethart.dto.follow;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class FollowRequest {

    private Long followTo;
    private Long followFrom;
}
