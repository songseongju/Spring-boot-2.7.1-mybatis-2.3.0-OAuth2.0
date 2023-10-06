package com.team.togethart.controller.member;

import com.team.togethart.config.jwt.JwtUtils;
import com.team.togethart.dto.member.MailDTO;
import com.team.togethart.dto.member.MemberAddRequest;
import com.team.togethart.dto.member.MemberLoginRequest;
import com.team.togethart.repository.member.MemberMapper;
import com.team.togethart.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController 
public class LoginJoinController {
    private final JavaMailSender javaMailSender;
    public LoginJoinController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // 연동 확인
    @GetMapping("api")

    public ResponseEntity<?> memberAddRequest() {
        List<MemberAddRequest> memberAddRequests = memberService.getUserList();
        return ResponseEntity.ok(memberAddRequests);
    }


    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MemberService memberService;

    //로그인
    @GetMapping("/login")

    public String login() {
        return "index";
    }

    @PostMapping("/login/login-token")

    public ResponseEntity<?> login(@RequestBody MemberLoginRequest memberLoginRequest, HttpServletResponse response) throws IOException {
        MemberAddRequest memberAddRequest = memberService.login(memberLoginRequest.getMemberEmail(), memberLoginRequest.getMemberPwd());
        if (memberAddRequest != null) {
            String accessToken = jwtUtils.createAccessToken(memberAddRequest.getMemberId()
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
    @GetMapping("/signup")
    public String Join() {
        return "";
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<?> Register(@RequestBody MemberAddRequest memberAddRequest) {
        memberService.register(memberAddRequest);
        return ResponseEntity.ok().build();
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("jwtToken"); // 세션에서 토큰 정보 제거
        return ""; // 로그아웃 후 메인 홈페이지로 이동
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
    @GetMapping("/login/findUserId")
    public String idfind() {

        return "";
    }

    @PostMapping("/login/findUserId")
    public ResponseEntity<?> findUserId(@RequestBody Map<String, String> params) {
        String username = params.get("memberUsername");
        List<String> userIds = memberService.findUserIdsByNameAndEmail(username);
        return ResponseEntity.ok(userIds);
    }

    //비밀번호 찾기

    @GetMapping("login/findUserPwd")
    public String Pwfind() {
        return "";
    }

    @PostMapping("login/findUserPwd")
    public ResponseEntity<?> findUserPw(@RequestBody Map<String, String> params) {
        String Pwd = params.get("memberEmail");
        List<String> useremails = memberService.findUserIdsByNameAndPwd(Pwd);
        return ResponseEntity.ok(useremails);
    }


    // 비밀번호 임시비밀번호 구현중
   @PostMapping("/loginjoin/Pwfind")
    public String findPassword(@RequestBody MemberAddRequest memberAddRequest
            , Model model) {
        // 입력받은 정보를 이용해 회원 정보를 조회합니다.
        MemberAddRequest selectinfo = memberMapper.findById(memberAddRequest.getMemberEmail());
        if (selectinfo == null
        ) {
            // DTO로 조회한 결과가 없는 경우
            model.addAttribute("errorMessage", "입력한 정보와 일치하는 회원이 존재하지 않습니다.");
            return "loginjoin/find_password_result";
       }
        else
         {
            // MemberAddRequest 로 통하여 조회한 결과가 있는 경우
            // 비밀번호 업데이트 및 임시 비밀번호 발급
            String tempPassword = memberService.generateTempPassword();
            memberMapper.updatePassword(memberAddRequest.getMemberUsername(), tempPassword);
            model.addAttribute("tempPasswordSent", true);
            return "redirect:/loginjoin/common/login";
        }
    }


    //이메일 중복 확인
    @GetMapping("/checkIdDuplicate/{id}")
    public ResponseEntity<Map<String, Boolean>> checkIdDuplicate(@PathVariable String id) {
        boolean duplicate = memberMapper.isIdDuplicated(id) ;
        Map<String, Boolean> response = Collections.singletonMap("duplicate", duplicate);
        return ResponseEntity.ok(response);
    }

    //회원탈퇴

    @DeleteMapping("/user")
    public ResponseEntity<Map<String, Object>> deleteUser(MemberAddRequest memberAddRequest) {
        String userId = memberAddRequest.getMemberUsername();
        memberService.deleteUser(userId);
        Map<String, Object> responseMap = new HashMap<>();
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
    @GetMapping("/delete")
    public String memberDelete(Model model) {
        return "";
    }

}
