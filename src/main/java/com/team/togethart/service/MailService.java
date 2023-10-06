package com.team.togethart.service;

import com.team.togethart.dto.member.EmailModel;

public interface MailService {


    /**
     * 단순 문자 내용만 전송하는 메일
     *
     * @param emailModel 메일객체
     * @return 발송성공 여부
     */
    Boolean sendSimpleMail(EmailModel emailModel);

    /**
     * HTML 포맷 메일
     *
     * @param emailModel 메일객체
     * @return 발송성공 여부
     */
    Boolean sendHtmlMail(EmailModel emailModel);

    /**
     * 첨부파일이 있는 메일
     *
     * @param emailModel 메일객체
     * @return 발송성공 여부
     */
    Boolean sendAttachmentMail(EmailModel emailModel);

    /**
     * resource 가 첨부된 메일 (자주 쓰지는 않을것 같음)
     *
     * @param emailModel 메일객체
     * @return 발송성공 여부
     */
    Boolean sendInlineResourceMail(EmailModel emailModel);

    /**
     * HTML 포맷 + 첨부파일이 포함된 메일 전송
     *
     * @param emailModel 메일객체
     * @return 발송성공 여부
     */
    Boolean sendHtmlImageMail(EmailModel emailModel);
}
