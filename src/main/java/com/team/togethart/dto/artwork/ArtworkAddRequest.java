package com.team.togethart.dto.artwork;

import java.util.Date;

// 컨트롤러에 보낼 객체
public class ArtworkAddRequest {

    // 업로드 하는 회원
    private String memberId;
    private String artworkTitle;
    private String artworkDescription;
    private Date aUploadDate;
    private String uploadPath;
    private String artworkScope;
    private String categoryName;

}
