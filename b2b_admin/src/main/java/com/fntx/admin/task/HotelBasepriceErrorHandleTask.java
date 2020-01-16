package com.fntx.admin.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelBasepriceErrorHandleTask
 * @Author: 王俊文
 * @Date: 19-8-9 下午3:44
 * @Description: 处理异常城市入离起价信息--停用
 */
@Component
public class HotelBasepriceErrorHandleTask
{
    private static final Logger logger = LoggerFactory.getLogger(HotelBasepriceErrorHandleTask.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Description: 处理异常城市入离起价信息
     * @Author: 王俊文
     * @Date: 19-8-9 下午3:47
     * @Param: []
     * @returns: void
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-9 下午3:47          1.0          处理异常城市入离起价信息
     */
//    @Scheduled(cron = "0 0 0 5,20 * ?")
    public void hotelIncrData()
    {
        //停用
//        redisTemplate.convertAndSend("IStaticInformationChange",
//                                "MessageQueueHotelBasepriceErrorHandle");
//        logger.info("处理异常城市入离起价信息");
    }
}
