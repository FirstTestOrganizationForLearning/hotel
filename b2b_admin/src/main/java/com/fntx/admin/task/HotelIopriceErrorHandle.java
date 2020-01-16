package com.fntx.admin.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelIopriceErrorHandle
 * @Author: 王俊文
 * @Date: 19-8-8 下午1:05
 * @Description: 处理异常直连入离报价信息--停用
 */
@Component
public class HotelIopriceErrorHandle
{
    private static final Logger logger = LoggerFactory.getLogger(HotelIopriceErrorHandle.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Description: 处理异常直连入离报价信息
     * @Author: 王俊文
     * @Date: 19-8-8 下午1:07
     * @Param: []
     * @returns: void
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-8 下午1:07          1.0          处理异常直连入离报价信息
     */
//    @Scheduled(cron = "0 0 0 1/4 * ?")
    public void hotelIncrData()
    {
        //停用
//        redisTemplate.convertAndSend("IStaticInformationChange",
//                                "MessageQueueHotelIopriceErrorHandle");
//        redisTemplate.convertAndSend("IStaticInformationChange",
//                                "MessageQueueHoteliopricErrorSecondaryHandle");
//        logger.info("处理异常直连入离报价信息");
    }
}
