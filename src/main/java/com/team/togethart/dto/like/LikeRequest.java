package com.team.togethart.dto.like;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Getter
@Setter
public class LikeRequest {

    private Long artworkId;
    private Long memberId;


}
