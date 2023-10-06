package com.team.togethart.dto.follow;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class FollowAddRequest {
    private Long follow_id;
    private Long follow_from;
    private Long follow_to;
}
