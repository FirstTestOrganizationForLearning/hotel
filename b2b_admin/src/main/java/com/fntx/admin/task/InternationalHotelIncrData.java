package com.fntx.admin.task;

import com.fntx.admin.service.IHotelIncrDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelIncrData
 * @Author: 王俊文
 * @Date: 19-7-15 上午9:29
 * @Description: 监测静态信息变化--国际
 */
@Component
public class InternationalHotelIncrData
{
    private static final Logger logger = LoggerFactory.getLogger(InternationalHotelIncrData.class);

    @Autowired
    private IHotelIncrDataService iHotelIncrDataService;

    /**
     * @Description: 监测静态信息变化--国际
     * @Author: 王俊文
     * @Date: 19-7-15 上午10:00
     * @Param: []
     * @returns: void
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-15 上午10:00          1.0          每十分钟执行一次. 查询当前时间前十分钟内数据变动情况
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void internationalHotelIncrData()
    {
        logger.info("监测静态信息变化--国际");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
        Date date = new Date();
        Calendar B = Calendar.getInstance();
        B.setTime(date);
        B.add(Calendar.MINUTE, -10);
        boolean sign = iHotelIncrDataService.getHotelIncrDataInternational(sdf.format(B.getTime()));
        if ( sign )
        {
            logger.info("完成-监测静态信息变化--国际");
        }
    }
}
