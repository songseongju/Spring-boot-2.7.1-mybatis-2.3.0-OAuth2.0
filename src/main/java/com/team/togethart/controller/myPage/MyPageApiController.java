package com.team.togethart.controller.myPage;

import com.team.togethart.dto.myPage.MyPageMemberInfoResponse;
import com.team.togethart.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class MyPageApiController {

    /* myPage View 는 ViewController 에서 마이페이지 템플릿 반환.
       당 클래스는 myPage View 에 필요한 요소들을 반환하기 위한 클래스임.
       myPage View 는 /member/{memberId} 의 엔드포인트를 기준으로 반환 할 요소들을 구분한다.
       프론트에서는 각각의 필요 요소들을 fetch 로 요청하여 받은 응답을 페이지 각 요소에 배치한다.
    */
    @Autowired
    MyPageService myPageService;

    /* /api/member/{memberId}
       반환이 필요한 항목
       1. 해당 멤버 정보 ==> member table
       SELECT member_id,
              member_username,
              member_regi_date,
              member_birth,
              member_image,
              member_intro
         FROM member
        WHERE member_id = #{memberId}

        MyPageMemberResponse DTO 를 반환해서 프론트에 전달.
    */
    @GetMapping("api/member/{memberId}/memberInfo")
    public ResponseEntity<MyPageMemberInfoResponse> myPageMemberInfoGet(
            @PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok().body(myPageService.getMyPageMemberInfo(memberId));
    }
    /*
       2. 해당 멤버가 업로드한 artwork. ==> artwork table, upload_file table (INNER JOIN)
       SELECT u.artwork_id,
	          u.resource_pathname
         FROM upload_file u
        INNER JOIN artwork a USING(artwork_id)
        WHERE member_id = #{memberId}

        List<MyPageArtworks> List<DTO> 를 반환해서 프론트에 전달.
    */
//    @GetMapping("api/member/{memberId}/artworksHome")
//    public ResponseEntity<MyPageArtworksResponse> myPageArtworksGet(
//            @PathVariable("memberId") Long memberId) {
//
//    }
    /*
       3. 해당 멤버가 좋아요한 artwork. ==> like table, artwork table (INNER JOIN)
       SELECT u.artwork_id,
              u.resource_pathname
         FROM upload_file u
        INNER JOIN likes l USING(artwork_id)
        WHERE member_id = #{memberId}

        List<MyPageLikes> List<DTO> 를 반환해서 프론트에 전달.
    */
}
