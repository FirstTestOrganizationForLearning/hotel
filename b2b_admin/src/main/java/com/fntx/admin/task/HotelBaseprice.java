package com.fntx.admin.task;

import com.fntx.admin.service.IHotelBasepriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelBaseprice
 * @Author: 王俊文
 * @Date: 19-7-17 下午4:32
 * @Description: 酒店起价--停用
 */
public class HotelBaseprice
{
    private static final Logger logger = LoggerFactory.getLogger(HotelBaseprice.class);

    @Autowired
    private IHotelBasepriceService iHotelBasepriceService;

    /**
     * @Description: 酒店起价
     * @Author: 王俊文
     * @Date: 19-7-15 上午10:00
     * @Param: []
     * @returns: void
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-15 上午10:00          1.0          每月1号和16号分别执行一次
     */
//    @Scheduled(cron = "0 0 0 1,16 * ?")
    public void hotelIncrData()
    {
        //停用
//        boolean sign = iHotelBasepriceService.getHotelBaseprice();
//        if ( sign )
//        {
//            logger.info("酒店起价数据同步完成");
//        }
    }
}
