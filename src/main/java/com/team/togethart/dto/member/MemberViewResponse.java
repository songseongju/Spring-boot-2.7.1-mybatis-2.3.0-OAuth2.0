package com.team.togethart.dto.member;

import java.util.Map;

// 마이페이지 요청 시 응답.
public class MemberViewResponse {

    private String memberUsername;
    private String memberImage;
    private String followerCount;
    private String memberRegiDate;
    private String artworksCount;
    private Map<String,String> category;
    private String memberIntro;

    // 회원이 올린 작품, 좋아요한 작품 각각 아트워크 4~5 개 받아와서 뿌려야 됨.
    private String ResizedPath;


}
