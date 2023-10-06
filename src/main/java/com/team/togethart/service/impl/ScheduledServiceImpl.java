package com.team.togethart.service.impl;

import com.team.togethart.dto.member.EmailModel;
import com.team.togethart.service.MailService;
import com.team.togethart.service.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


public class ScheduledServiceImpl implements ScheduledService {

    @Autowired
    MailService mailService;

    @Scheduled(cron = "0/1 * * * * ? ") // 시간당 1회 실행
    public void sendSimpleMail() {

        mailService.sendSimpleMail(EmailModel.builder()
                .email("sj88139@naver.com")
                .subject("test")
                .content("시간당 1회 스케줄 메일 입니다.(테스트)!")
                .build());
    }
}
