package com.team.togethart.dto.artwork;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class ArtworkViewResponse {

    private Long artworkId;
    private Long memberId;
    private String memberUsername;
    private String artworkTitle;
    private String artworkDescription;
    private Date aUploadDate;
    private String artworkScope;
    private String viewCount;
    private String likeCount;
    private String resourcePathname;
    private String filename;

    // 해당 작품에 담긴 comment list 가져와야됨.

}
