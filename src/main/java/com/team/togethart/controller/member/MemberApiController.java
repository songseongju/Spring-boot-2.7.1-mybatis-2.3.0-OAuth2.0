package com.team.togethart.controller.member;

import com.team.togethart.config.jwt.JwtUtils;
import com.team.togethart.dto.member.MemberAddRequest;
import com.team.togethart.dto.member.MemberUpdateRequest;
import com.team.togethart.repository.member.MemberMapper;
import com.team.togethart.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class MemberApiController {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtUtils jwtUtils;


    @GetMapping("/profile/common/account")
    public String commontAccount(){
        return "html/profile/account/profile_common_account";
    }

    @GetMapping("/profile/data")
    @ResponseBody
    public ResponseEntity<?> getArtistData(HttpServletRequest request) {
        String token = jwtUtils.getAccessToken(request);
        String commonUserId = jwtUtils.getId(token);
        System.out.println("id : " + commonUserId);

        if (commonUserId != null) {
            // ID를 이용해 관리자 정보를 가져옵니다.
           MemberAddRequest memberAddRequest = memberMapper.getCommonInfoById(commonUserId);
            if (memberAddRequest != null) {
                return ResponseEntity.ok(memberAddRequest);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }
    }


    // 회원 정보 수정 폼 요청 처리
    @GetMapping("/profile/modify")
    public String commonAccountModify() {
        return "회원정보 수정 페이지 리턴";
    }

    // 회원 정보 수정 처리
    @PostMapping("/profile/modify")
    public ResponseEntity<?> updateCommonMember(@RequestBody MemberUpdateRequest memberUpdateRequest
            , @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) throws IOException {
        if(imgFile == null){
            System.out.println(memberUpdateRequest);
            memberService.modifyCommonWithoutImage(memberUpdateRequest);
        }else{
            System.out.println(memberUpdateRequest);
            memberService.modifyCommon(memberUpdateRequest, imgFile);
        }
        return ResponseEntity.ok().build();
    }

}




