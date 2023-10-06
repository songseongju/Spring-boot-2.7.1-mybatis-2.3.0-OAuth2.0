package com.team.togethart.dto.member;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmailModel implements Serializable {

    /**
     * 초기화
     */
    private static final long serialVersionUID = 1404185636399251685L;

    /**
     * 수신자
     */
    private String email;
    /**
     * 메일 제목
     */
    private String subject;
    /**
     * 메일 내용
     */
    private String content;
    /**
     * 첨부파일 위치
     */
    private String attachFilePath;
    /**
     * 메일 resource 위치
     */
    private String resourcePath;
    /**
     * 메일 resource 이름
     */
    private String resourceName;

}
