package com.team.togethart.service;

import com.team.togethart.dto.member.MailDto;
import com.team.togethart.dto.member.MemberAddRequest;
import com.team.togethart.repository.member.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendEmailService {

    @Autowired
    MemberMapper memberMapper;

    private JavaMailSender mailSender;

    private static final String FROM_ADDRESS = "togethart222@gmail.com";



    public MailDto createMailAndChangePassword(String memberEmail, String memberUsername){
        String str = getTempPassword(); // 임시비번 발급
        MailDto dto = new MailDto();
        dto.setAddress(memberEmail);
        dto.setTitle(memberUsername+"님의 Togethart 임시비밀번호 안내 이메일 입니다."); // 메일 제목
        dto.setMessage("안녕하세요. Togethart 임시비밀번호 안내 관련 이메일 입니다." + "[" + memberUsername + "]" +"님의 임시 비밀번호는 "
                + str + " 입니다."); // 메일 내용
        MemberAddRequest memberAddRequest = new MemberAddRequest();
        memberAddRequest.setMemberEmail(memberEmail);
        memberAddRequest.setMemberPwd(str);
         updatePassword(memberAddRequest);

        return dto;

    }

    public void updatePassword(MemberAddRequest memberAddRequest){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        memberAddRequest.setMemberPwd(passwordEncoder.encode(memberAddRequest.getMemberPwd()));

        memberMapper.updateUserPassword(memberAddRequest);
    }


    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    public void mailSend(MailDto mailDto){
        System.out.println("이메일 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(SendEmailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }


}
