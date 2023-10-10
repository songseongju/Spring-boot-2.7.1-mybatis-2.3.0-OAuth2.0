package com.team.togethart.dto.comment;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CommentAddRequest {

    private Long artworkId;
    private Long memberId;
    private String memberUsername;
    private String commentContent;


}
