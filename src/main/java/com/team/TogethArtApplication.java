package com.team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// @SpringBootConfiguration,
// @ComponentScan 해당 패키지에서 @Component 어노테이션을 가진 Bean들 스캔, 등록.
// @EnableAutoConfiguration Configuration, Bean 등록하는 자바 설정 파일. spring.factories 내부 자동 설정들이 조건에 따라 적용, Bean 생성
// 위 3개가 합쳐진 것. ==> @SpringBootApplication
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class TogethArtApplication {
    public static void main(String[] args) {
        SpringApplication.run(TogethArtApplication.class, args);
    }
}
