package com.team.togethart.service;

import com.team.togethart.dto.member.MemberAddRequest;
import com.team.togethart.dto.member.MemberPwUpdateRequest;
import com.team.togethart.dto.member.MemberUpdateRequest;
import com.team.togethart.repository.member.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberAddRequest memberAddRequest;

    // 회원가입 시 저장시간을 넣어줄 DateTime형
    SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

   
    // 로그인
    public MemberAddRequest login(String email, String pwd) {


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MemberAddRequest GetInfo = memberMapper.findById(email);

        String 불러온정보 = pwd;
        String 가져왔음 = GetInfo.getMemberPwd();

        boolean passMatch = passwordEncoder.matches(pwd,GetInfo.getMemberPwd());

        System.out.println("가져왔음"+" "+ 가져왔음);
        System.out.println("불러온정보"+" "+ 불러온정보);
        System.out.println("결과값"+" "+ passMatch);

        if (GetInfo != null
                && passMatch //가져온정보.getMemberPwd().equals(pwd)
        )
        {
            return GetInfo;
        } else {
            return null;
        }
    }
// 질문 -------------------------------------------------------------------------------------

    // 회원탈퇴 - 비밀번호체크

    public MemberAddRequest checkpwd(String pwd){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        MemberAddRequest GetInfo = memberMapper.findByPwd(pwd);

        String 불러온정보 = pwd;
        String 가져왔음 = GetInfo.getMemberPwd();

        boolean passMatch = passwordEncoder.matches(pwd,GetInfo.getMemberPwd());

        if (passMatch)
        {
            return GetInfo;
        } else {
            return null;
        }
    }

    // 회원탈퇴
    public void deleteMemberByEmail(String memberEmail){
        memberMapper.deleteMemberByEmail(memberEmail);
    }


 //-----------------------------------------------------------------------------------------------//

    //회원정보 수정 <비밀번호 변경>
    public MemberAddRequest getMemberByEmail(String memberEmail){

        return memberMapper.getMemberByEmail(memberEmail);

    }

    public void modifyPwd(MemberPwUpdateRequest memberPwUpdateRequest){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        memberPwUpdateRequest.setNewPwd(passwordEncoder.encode(memberPwUpdateRequest.getNewPwd()));

        memberMapper.modifyPwd(memberPwUpdateRequest);
    }


    //------------------------------------------------------------------------------------------//

    // 회원가입
    public boolean register(MemberAddRequest memberAddRequest) {
        // MemberAddRequest 객체 생성 및 정보 설정
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberAddRequest.setMemberPwd(passwordEncoder.encode(memberAddRequest.getMemberPwd()));
        memberAddRequest.setMemberRegiType("N"); // N 일반   S 소셜
        memberAddRequest.setMemberRegiDate(localTime);
        memberAddRequest.setMemberAuth(0); // 구독권한 기본값 0
        // MemberAddRequest
        int result = memberMapper.insert(memberAddRequest);
        return result == 1;
    }

    // kakao 회원가입

    public boolean kakaoregister(MemberAddRequest memberAddRequest) {
        // MemberAddRequest 객체 생성 및 정보 설정
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberAddRequest.setMemberPwd(passwordEncoder.encode(memberAddRequest.getMemberPwd()));
        memberAddRequest.setMemberRegiType("K"); // N 일반   K 카카오
        memberAddRequest.setMemberRegiDate(localTime);
        memberAddRequest.setMemberAuth(0); // 구독권한 기본값 0
        // MemberAddRequest
        int result = memberMapper.insert(memberAddRequest);
        return result == 1;
    }

    // 회원 찾기
    public MemberAddRequest findMember(String memberusername){

        MemberAddRequest memberAddRequest = memberMapper.findByName(memberusername);

        if (memberAddRequest != null
        )
        {
            return memberAddRequest;
        } else {
            return null;
        }
    }


    // 이메일 찾기
    public List<String> findUserIdsByNameAndEmail(String username) {
        List<String> userIds = new ArrayList<>();


        userIds.addAll(memberMapper.findUserIdsByNameAndEmail(username));


        return userIds;
    }

    // 비밀번호 찾기
    public List<String> findUserIdsByNameAndPwd(String pwd) {
        List<String> userPwds = new ArrayList<>();


        userPwds.addAll(memberMapper.findUserIdsByNameAndPwd(pwd));

        return userPwds;
    }

    //회원정보 수정
    public void modifyCommonWithoutImage(MemberUpdateRequest memberUpdateRequest){
        memberMapper.modifyCommonWithoutImage(memberUpdateRequest);
    }

    public void modifyCommon(MemberUpdateRequest memberUpdateRequest, MultipartFile imgFile) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String ext = imgFile.getContentType();
        memberMapper.commonModify(memberUpdateRequest);
    }


    //연동확인
    public List<MemberAddRequest> getUserList() {
        return memberMapper.getUserList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    // 임시 비밀번호 파트 (메일과 유저네임 찾기)
    public boolean userEmailCheck(String memberEamil, String memberUsername) {

         MemberAddRequest memberAddRequest = memberMapper.getMemberByEmail(memberEamil);
        if(memberAddRequest!=null && memberAddRequest.getMemberUsername().equals(memberUsername)) {
            return true;
        }
        else {
            return false;
        }
    }



















    //임시 비밀번호 구현중
/*
    public MemberPwfindRequest findMemberByEmail(String email , String username) {
        MemberAddRequest memberAddRequest = memberMapper.findByEmail(email,username);
        if (memberAddRequest != null) {
            return new MemberPwfindRequest(memberAddRequest.getMemberEmail(),memberAddRequest.getMemberUsername(),memberAddRequest.getMemberPwd());
        }
        return null;
    }
*/


/*
    public void updatePassword(String email, String password) {
        MemberAddRequest memberAddRequest = memberMapper.findById(email);
        if (memberAddRequest != null) {
            memberMapper.updatePassword(email, password);
            return;
        }


    }


*/

  /*  public String generateTempPassword() {
        int length = 12; // 임시 비밀번호 길이
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 사용 가능한 문자열
        StringBuilder tempPassword = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            tempPassword.append(characters.charAt(random.nextInt(characters.length())));
        }
        return tempPassword.toString();
    } */
/*
    public void sendTempPasswordByEmail(String email, String tempPassword) {
        String from = "songtjdwn@gmail.com"; // 발신자 이메일 주소
        String pwd = "dghzxxgpraglgcav"; // 발신자 이메일 비밀번호
        String to = email; // 수신자 이메일 주소

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP 서버 호스트
        properties.put("mail.smtp.port", "587"); // Gmail SMTP 서버 포트
        properties.put("mail.smtp.starttls.enable", "true"); // SSL/TLS 사용 여부
        properties.put("mail.smtp.auth", "true"); // 인증 사용 여부
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // SSL/TLS 사용 시 신뢰할 수 있는 호스트 설정

        javax.mail.Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pwd);
            }
        });

        try {
            javax.mail.internet.MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("임시 비밀번호 발급 안내");

            // HTML 형식으로 이메일 작성
            String htmlContent = "<html><body>"
                    + "<h3>임시 비밀번호 발급 안내</h3>"
                    + "<p>아래의 임시 비밀번호를 사용하여 로그인해주세요.</p>"
                    + "<p>임시 비밀번호: " + tempPassword + "</p>"
                    + "<p>임시 비밀번호를 이용해 로그인 한 후 꼭 비밀번호 변경 부탁드립니다.</p>"
                    + "</body></html>";
            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
*/

}