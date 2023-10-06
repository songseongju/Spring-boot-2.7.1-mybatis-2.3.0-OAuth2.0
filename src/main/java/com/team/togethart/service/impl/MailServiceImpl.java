package com.team.togethart.service.impl;

import com.team.togethart.dto.member.EmailModel;
import com.team.togethart.service.MailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {


    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Async("EmailAsync")
    @Override
    public Boolean sendSimpleMail(EmailModel emailModel) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);// 보내는 사람, 설정 하지 않으면 기본 값 .properties 값이 셋팅된다.
            message.setTo(emailModel.getEmail().split(";"));//수신자  1명 혹은 여러명일때 ";" 로 분리
            message.setSubject(emailModel.getSubject());// 제목
            message.setText(emailModel.getContent());// 본문
            mailSender.send(message);

            logger.info("단순 문자전자 메일전송 성공!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("단순 문자전자 메일전송 실패!");
            return false;
        }
        return true;
    }

    @Async("EmailAsync")
    @Override
    public Boolean sendHtmlMail(EmailModel emailModel) {

        String to = emailModel.getEmail();
        String subject = emailModel.getSubject();
        String content = emailModel.getContent();
        logger.info("HTML Template 메일전송 시작:{},{},{}", to, subject, content);
        //MimeMessage，MIME 프로토콜 사용
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        //MimeMessageHelper 더욱 많은 내용들을 설정 할수 있다.
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to.split(";"));
            helper.setSubject(subject);
            helper.setText(content, true);// ture 일시 html 지원
            mailSender.send(message);
            logger.info("HTML Template 메일전송 성공!");
        } catch (MessagingException e) {
            logger.error("HTML Template 메일전송 실패!", e);
            return false;
        }
        return true;
    }

    @Async("EmailAsync")
    @Override
    public Boolean sendAttachmentMail(EmailModel emailModel) {


        String to = emailModel.getEmail();
        String subject = emailModel.getSubject();
        String content = emailModel.getContent();
        String filePath = emailModel.getAttachFilePath();
        logger.info("첨부파일 메일전송 시작:{},{},{},{}", to, subject, content, filePath);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true); //true 파일 여러개라는 뜻
            helper.setFrom(from);
            helper.setTo(to.split(";"));
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);//添加附件，可多次调用该方法添加多个附件
            mailSender.send(message);
            logger.info("첨부파일 메일전송 성공!");
        } catch (MessagingException e) {
            logger.error("첨부파일 메일전송 실패!", e);
            return false;
        }
        return true;
    }

    @Async("EmailAsync")
    @Override
    public Boolean sendInlineResourceMail(EmailModel emailModel) {

        String to = emailModel.getEmail();
        String subject = emailModel.getSubject();
        String content = emailModel.getContent();
        String resourcePath = emailModel.getResourcePath();
        String resourceName = emailModel.getResourceName();
        logger.info("첨부파일 메일전송 시작:{},{},{},{},{}", to, subject, content, resourcePath, resourceName);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to.split(";"));
            helper.setSubject(subject);
            helper.setText(content, true);
            // 절대경로를 읽는다
            FileSystemResource res = new FileSystemResource(new File(resourcePath));
            helper.addInline(resourceName, res);//파일여러개 일때  여러번 사용가능
            mailSender.send(message);
            logger.info("첨부파일 메일전송 성공!");
        } catch (MessagingException e) {
            logger.error("첨부파일 메일전송 실패!", e);
            return false;
        }
        return true;
    }

    @Async("EmailAsync")
    @Override
    public Boolean sendHtmlImageMail(EmailModel emailModel) {

        String to = emailModel.getEmail();
        String subject = emailModel.getSubject();
        String content = emailModel.getContent();
        String resourcePath = emailModel.getResourcePath();
        logger.info("첨부파일 메일전송 시작:{},{},{},{}", to, subject, content, resourcePath);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to.split(";"));
            helper.setSubject(subject);
            helper.setText(content, true);

            helper.setText("<html><head></head><body><h1>hello world!</h1>" + "<img src=\"cid:aaa\"/></body></html>", true);
            FileSystemResource img = new FileSystemResource(new File(resourcePath));

            helper.addInline("aaa", img);

            mailSender.send(message);
            logger.info("첨부파일 메일전송 성공!");
            return true;

        } catch (MessagingException e) {
            logger.error("첨부파일 메일전송 실패!", e);
            return false;

        }
    }



}







