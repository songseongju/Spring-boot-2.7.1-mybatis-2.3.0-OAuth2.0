package com.team.togethart.controller.member;

import com.team.togethart.config.jwt.JwtUtils;
import com.team.togethart.dto.member.MemberAddRequest;
import com.team.togethart.dto.member.MemberPwUpdateRequest;
import com.team.togethart.dto.member.MemberUpdateRequest;
import com.team.togethart.repository.member.MemberMapper;
import com.team.togethart.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class MemberApiController {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MemberAddRequest memberAddRequest;


    @GetMapping("/profile/common/account")
    public String commontAccount(){
        return "html/profile/account/profile_common_account";
    }

    @GetMapping("/profile/data")
    @ResponseBody
    public ResponseEntity<?> getArtistData(HttpServletRequest request) throws  IllegalAccessError {

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
        return " 회원 정보 수정 페이지 리턴 ";
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

    // 회원 정보 수정 처리 비밀번호 변경

    @GetMapping("/profile/pwdupdate")

    public String pwdupdate(){

        return "비밀번호 변경 페이지 리턴";
    }

    @PostMapping("/profile/pwdupdate")

    public ResponseEntity<?> passwd(@RequestBody MemberPwUpdateRequest memberPwUpdateRequest ,HttpServletRequest request, HttpServletResponse response){


        MemberAddRequest dbMemberDTO = memberService.getMemberByEmail(memberPwUpdateRequest.getMemberEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean passMatch = passwordEncoder.matches(memberPwUpdateRequest.getPwd(),dbMemberDTO.getMemberPwd());

        System.out.println("결과값 : " + passMatch);

        if(passMatch == false){

            return new ResponseEntity<>("현재 비밀번호가 틀렸습니다.",HttpStatus.FORBIDDEN);
        }

        //2. 새 비밀번호 새 비밀번호 확인 맞는지 체크

        if(memberPwUpdateRequest.getNewPwd().equals(memberPwUpdateRequest.getNewPwd()) == false ){

            return new ResponseEntity<>("new 비밀번호와 새 비밀번호 확인이 서로 일치 하지 않습니다.",HttpStatus.UNAUTHORIZED);
        }

        //3.  DB 비밀번호 변경

        memberService.modifyPwd(memberPwUpdateRequest);


        //4. 로그아웃(세션,쿠키 삭제)

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // 쿠키 무효화
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        return new ResponseEntity<>("비밀번호 변경완료.",HttpStatus.OK);
    }


    // 회원 탈퇴

    @GetMapping("/remove")
    public String remove(){

        return "회원 탈퇴 페이지 리턴";
    }


    @PostMapping("/remove")

    public ResponseEntity<?> remove(@RequestBody MemberAddRequest memberAddRequest,HttpServletRequest request, HttpServletResponse response){

        // 1. 비밀번호 체크

        MemberAddRequest dbMemberDTO = memberService.getMemberByEmail(memberAddRequest.getMemberEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean passMatch = passwordEncoder.matches(memberAddRequest.getMemberPwd(),dbMemberDTO.getMemberPwd());

        System.out.println("결과값 : " + passMatch);

        if(passMatch == false){

            return new ResponseEntity<>("현재 비밀번호가 틀렸습니다.",HttpStatus.FORBIDDEN);
        }

        // 2. 삭제완료

       memberService.deleteMemberByEmail(memberAddRequest.getMemberEmail());


        // 3. 로그아웃 처리 (세션,쿠키 삭제)

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // 쿠키 무효화
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return new ResponseEntity<>("회원탈퇴 완료",HttpStatus.OK);
    }

}




