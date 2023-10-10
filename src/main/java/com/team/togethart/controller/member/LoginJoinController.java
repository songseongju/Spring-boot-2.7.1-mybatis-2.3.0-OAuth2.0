package com.team.togethart.controller.member;

import com.team.togethart.config.jwt.JwtUtils;
import com.team.togethart.dto.member.MailDto;
import com.team.togethart.dto.member.MemberAddRequest;
import com.team.togethart.dto.member.MemberLoginRequest;
import com.team.togethart.repository.member.MemberMapper;
import com.team.togethart.service.MemberService;
import com.team.togethart.service.SendEmailService;
import org.aspectj.weaver.bcel.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController 
public class LoginJoinController {


    private final JavaMailSender javaMailSender;
    public LoginJoinController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // 연동 확인
    @GetMapping("api")

    public ResponseEntity<?> memberAddRequest() {
        List<MemberAddRequest> memberAddRequest = memberService.getUserList();
        return ResponseEntity.ok(memberAddRequest);
    }


    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MemberService memberService;

    @Autowired OAuthController oAuthController;

    @Autowired
    SendEmailService sendEmailService;

    //로그인
//    @GetMapping("/login")
//    public String login() {
//        return "index";
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequest memberLoginRequest, HttpServletResponse response) throws IOException {
        MemberAddRequest memberAddRequest = memberService.login(memberLoginRequest.getMemberEmail(), memberLoginRequest.getMemberPwd());
        if (memberAddRequest != null) {
            String accessToken = jwtUtils.createAccessToken("Togethart",memberAddRequest.getMemberId()
                    , memberAddRequest.getMemberEmail(), memberAddRequest.getMemberUsername());
            response.setHeader("Authorization", "Bearer " + accessToken);
            Map<String, String> result = new HashMap<>();
            result.put("jwtToken", accessToken);

            return ResponseEntity.ok(result);
        } else {

            return new ResponseEntity<>("요청하신 값이 다릅니다 다시 확인 해 주세요..", HttpStatus.UNAUTHORIZED);
        }
    }

    //회원가입
//    @GetMapping("/signup")
//    public String Join() {
//        return "";
//    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<?> Register(@RequestBody MemberAddRequest memberAddRequest) {

        if (memberAddRequest != null) {

            memberService.register(memberAddRequest);

            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>("요청하신 값이 다릅니다 다시 확인 해 주세요..", HttpStatus.UNAUTHORIZED);
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("jwtToken"); // 세션에서 토큰 정보 제거
        return "/"; // 로그아웃 후 메인 홈페이지로 이동
    }

    /* 토큰 쿠키를 삭제하는 컨트롤러 (로그아웃) */

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        // 쿠키 무효화
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return ResponseEntity.ok().build();
    }

    // 이메일 찾기
/*    @GetMapping("/login/findEmail")   // 템플리에서 불러올값
    public String idfind() {

        return "member/findEmail";   // 템플리 주속값
    }*/

    @PostMapping("/login/findEmail")
    public ResponseEntity<?> findUserId(@RequestBody Map<String, String> params) {
        String memberUsername = params.get("memberUsername");
        List<String> userIds = memberService.findUserIdsByNameAndEmail(memberUsername);
        return ResponseEntity.ok(userIds);
    }

    //비밀번호 찾기
  /*  @GetMapping("login/findPwd")
    public String Pwfind() {
        return "";
    }*/

    @PostMapping("login/findPwd")
    public ResponseEntity<?> findUserPw(@RequestBody Map<String, String> params) {
        String Pwd = params.get("memberEmail");
        List<String> useremails = memberService.findUserIdsByNameAndPwd(Pwd);
        return ResponseEntity.ok(useremails);
    }


    // 이메일 과 유저네임 일치한지 체크하는 메소드
    @GetMapping("/check/findPw")

    public @ResponseBody Map<String, Boolean> pw_find(String memberEamil , String memberUsername){
        Map<String,Boolean> json = new HashMap<>();
        boolean pwFindCheck = memberService.userEmailCheck(memberEamil,memberUsername);
        System.out.println(pwFindCheck);
        json.put("check", pwFindCheck);
        return json;
    }

    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
    @PostMapping("/check/findPw/sendEmail")
    public @ResponseBody void sendEmail(@RequestBody MemberAddRequest memberAddRequest){
        MailDto dto = sendEmailService.createMailAndChangePassword(memberAddRequest.getMemberEmail(), memberAddRequest.getMemberUsername());
        sendEmailService.mailSend(dto);
    }

}
