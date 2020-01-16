package com.fntx.admin.task;

import com.fntx.admin.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName:
 * @Author: 胡庆康
 * @Date: 2019/7/8 11:07
 * @Description:
 */
@Component
public class RefreshTask {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    TokenUtil tokenUtil;
    private static final Logger logger = LoggerFactory.getLogger(RefreshTask.class);

    @Scheduled(cron = "0 0/8 * * * ?")
    public void refreshToken() {
        if (!stringRedisTemplate.hasKey("token") && !stringRedisTemplate.hasKey("testToken")) {
            tokenUtil.getToken();
            return;
        }
        boolean flag = tokenUtil.refreshToken();
        if (flag) {
            logger.info("=====*********======刷新了token************");
        }else{
            logger.info("=====*********======刷新了token失败************");
            tokenUtil.getToken();
        }
    }
}
