package com.team.togethart.config;

import com.team.togethart.config.jwt.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class webConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor jwtInterceptor() {
        JwtInterceptor jwtInterceptor = new JwtInterceptor();
        return jwtInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/home")
                .excludePathPatterns("/map/**")
                .excludePathPatterns("/loginjoin/**")
                .excludePathPatterns("/profile/**")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/findUserId")
                .excludePathPatterns("/checkIdDuplicate/{id}")
                .excludePathPatterns("/checkNidDuplicate/{nid}")
                .excludePathPatterns("/top5artists")
                .excludePathPatterns("/top5concert")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/assets/**")
                .excludePathPatterns("/img/**")
        ;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}
