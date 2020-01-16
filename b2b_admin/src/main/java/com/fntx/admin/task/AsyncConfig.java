package com.fntx.admin.task;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: AsyncConfig
 * @Author: 王俊文
 * @Date: 2019/9/12 下午5:46
 * @Description: 开启定时任务多线程.  Scheduled 配置定时任务 默认只有一个线程.
 */
@Component
public class AsyncConfig {
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(100);//我这里设置的线程数是100,可以根据需求调整
        return taskScheduler;
    }
}
