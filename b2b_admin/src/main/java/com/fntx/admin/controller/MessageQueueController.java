package com.fntx.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: MessageQueueController
 * @Author: 王俊文
 * @Date: 19-7-23 下午6:41
 * @Description: 消息队列
 */
@RestController
public class MessageQueueController
{
    private static final Logger logger = LoggerFactory.getLogger(MessageQueueController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/messageQueue")
    public void messageQueue(@RequestParam Map<String, String> paramMap)
    {
        logger.info(
            "\n***************************************" + "\n" +
            "start messageQueue" + "\n" +
            "消息队列" + "\n" +
            "redisKey = " + paramMap.get("redisKey") + "--  redisKey\n" +
            "redisValue = " + paramMap.get("redisValue")+ "--  redisValue\n" +
            "\n********************************************"
        );
            if ( paramMap.get("redisValue") != null && !"".equals(paramMap.get("redisValue")) )
            {
                redisTemplate.opsForValue().set(paramMap.get("redisKey"),paramMap.get("redisValue"));
            }
            if ( paramMap.get("redisKey") != null && !"".equals(paramMap.get("redisKey")) )
            {
                redisTemplate.convertAndSend("IStaticInformationChange",paramMap.get("redisKey"));
            }

        logger.info(
            "\n***************************************" + "\n" +
            "end messageQueue" + "\n" +
            "消息队列" + "\n" +
            "\n********************************************"
        );
    }
}
