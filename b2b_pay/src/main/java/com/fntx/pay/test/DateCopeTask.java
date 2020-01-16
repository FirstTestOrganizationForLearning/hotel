package com.fntx.pay.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName:
 * @Author: 渠猛
 * @Date: 2019/7/8 11:07
 * @Description:
 */
@Component
public class DateCopeTask {

    private static final Logger logger = LoggerFactory.getLogger(DateCopeTask.class);

    @Scheduled(cron = "0 0/9 * * * ?")
    public void refreshToken() {

    }
}
