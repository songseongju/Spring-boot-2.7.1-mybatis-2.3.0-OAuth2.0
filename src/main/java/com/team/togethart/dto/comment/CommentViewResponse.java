package com.team.togethart.dto.comment;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class CommentViewResponse {

    private String memberUsername;
    private Date cUploadDate;
    private String commentContent;
    private String memberImage;

}
