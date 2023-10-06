package com.team.togethart.controller.member;

import com.team.togethart.dto.member.EmailModel;
import com.team.togethart.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mail")
public class MailController {


    private final MailService mailService;


    @PostMapping("/test")
    public ResponseEntity sendSimpleMail(String toEmail) {
        mailService.sendSimpleMail(EmailModel
                .builder()
                .email(toEmail)
                .subject("test")
                .content("일단 아무거나 테스트")
                .build());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/simple")
    public ResponseEntity sendSimpleMail(@RequestBody EmailModel emailModel) {

        mailService.sendSimpleMail(emailModel);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/html")
    public ResponseEntity sendHtmlMail(@RequestBody EmailModel emailModel) {

        mailService.sendHtmlMail(emailModel);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/attach")
    public ResponseEntity sendAttachmentMail(@RequestBody EmailModel emailModel) {

        mailService.sendAttachmentMail(emailModel);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/resource")
    public ResponseEntity sendInlineResourceMail(@RequestBody EmailModel emailModel) {

        mailService.sendInlineResourceMail(emailModel);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/image")
    public ResponseEntity sendHtmlImageMail(@RequestBody EmailModel emailModel) {

        mailService.sendHtmlImageMail(emailModel);
        return ResponseEntity.ok().build();
    }


}
