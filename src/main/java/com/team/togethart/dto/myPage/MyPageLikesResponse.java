package com.team.togethart.dto.myPage;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MyPageLikesResponse {

    private Long artworkId;
    private String resourcesPathname;

}