package com.team.togethart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


@EnableAsync
@Configuration
public class ExecutorConfig extends AsyncConfigurerSupport {

    /**
     * 스레드풀 사이즈 설정
     **/
    private int corePoolSize = 4;
    /**
     * 스레드풀 초대 사이즈 설정
     **/
    private int maxPoolSize = 16;
    /**
     * 스레드풀 큐 사이즈
     **/
    private int queueCapacity = 10;
    /**
     * 스레드 이름 접두사
     */
    private String threadNamePrefix = "EmailAsyncExecutor-";

    @Bean("EmailAsync")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);


        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.setWaitForTasksToCompleteOnShutdown(true);

        executor.setAwaitTerminationSeconds(60);

        executor.initialize();

        return executor;
    }

}
