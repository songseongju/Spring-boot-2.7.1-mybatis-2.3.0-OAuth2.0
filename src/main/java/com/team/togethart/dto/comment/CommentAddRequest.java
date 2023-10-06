package com.team.togethart.dto.comment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Component
@Getter
@Setter
public class CommentAddRequest {

    private String artworkId;
    private String memberId;
    private String memberUsername;
    private String commentContent;


}
