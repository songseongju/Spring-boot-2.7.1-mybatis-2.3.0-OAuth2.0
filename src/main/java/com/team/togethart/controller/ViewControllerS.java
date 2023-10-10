package com.team.togethart.controller;

import com.team.togethart.dto.artwork.ArtworkViewResponse;
import com.team.togethart.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewControllerS {

    @Autowired
    ArtworkService artworkService;

//    @GetMapping("/")
//    public String index() {
//        return "gallery/index";
//    }
    // 로그인
    @GetMapping("/login")
    public String loginView() {
        return "member/login";
    }
    // 로그아웃
    @GetMapping("/signup")
    public String joinView() {
        return "member/signup";
    }
    //이메일 찾기
    @GetMapping("/login/findEmail")
    public String findEmail() { return "member/findEmail"; }
    //비밀번호 찾기
    @GetMapping("/login/findPwd")
    public String findPwd() {
        return "member/findPwd";
    }



//--------------------------------------------------------------------------------------------------------//

    @GetMapping("/newArtwork")
    public String newArtworkView() {
        return "artwork/newArtwork";
    }

//     ("/artwork/{artworkId}") 로 요청 들어오면?
    @GetMapping("/artwork/{artworkId}")
    public String artworkView(@PathVariable("artworkId") Long artworkId, Model model) {

            // artworkId 를 통해 DB table 'artwork' 에서 해당 레코드 가져오기.?
        ArtworkViewResponse artworkViewResponse = artworkService.findArtwork(artworkId);
        model.addAttribute("artwork", artworkViewResponse);

        return "artwork/artworkView";


    }

    // 마이 페이지
    @GetMapping("/member/{memberId}")
    public String memberMyPage(@PathVariable("memberId") Long memberId) {
        return "myPage/myPage";
    }

    @GetMapping("/member/{memberId}/subscribes")
    public String memberSubscribes(@PathVariable("memberId") Long memberId) {
        return "myPage/memberSubscribes";
    }

    @GetMapping("/member/{memberId}/artworks")
    public String memberArtworks(@PathVariable("memberId") Long memberId) {
        return "myPage/memberArtworks";
    }

    @GetMapping("/member/{memberId}/likes")
    public String memberLikes(@PathVariable("memberId") Long memberId) {
        return "myPage/memberLikes";
    }

    @GetMapping("member/{memberId}/subs-info")
    public String memberSubsInfo(@PathVariable("memberId") Long memberId) {
        return "myPage/subscribeInfo";
    }

    /*
    * 팔로우한 작가의 작품 모아보기,
    * 페이지만 리턴,
    * 프론트에서 로그인 여부에 따라 보여줄 내용 결정.
    * 비회원 시, 로그인 이후 사용 안내문,
    * 회원일 시, 팔로우한 작가가 없다면, 팔로우를 하고 사용하도록 안내문.
    *          팔로우한 작가가 있다면, 팔로우한 작가의 작품을 불러오는 백엔드 api fetch 해서 가져옴.
    * */
    @GetMapping("/follow")
    public String followArtworks() {
        return "gallery/follow";
    }

    @GetMapping("/popular")
    public String popular() {
        return "gallery/popular";
    }

    @GetMapping("/category")
    public String category() {
        return "gallery/category";
    }

    @GetMapping("/category/{categoryId}")
    public String oneCategory(@PathVariable("categoryId") Long categoryId) {
        return "gallery/category";
    }

    
}
