package com.fntx.admin.task;

import com.fntx.admin.service.IHotelIncrRoomtypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelIncrRoomtype
 * @Author: 王俊文
 * @Date: 19-7-15 下午4:29
 * @Description: 监测房型上下架--停用
 */
@Component
public class HotelIncrRoomtype
{
    private static final Logger logger = LoggerFactory.getLogger(HotelIncrRoomtype.class);

    @Autowired
    private IHotelIncrRoomtypeService iHotelIncrRoomtypeService;

    /**
     * @Description: 监测房型上下架
     * @Author: 王俊文
     * @Date: 19-7-15 下午4:34
     * @Param: []
     * @returns: void
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-15 下午4:34          1.0          每一小时执行一次. 查询当前时间前一小时内数据变动情况
     */
//    @Scheduled(cron = "0 0 0/1 * * ?")
    public void hotelIncrRoomtype()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
        Date date = new Date();
        Calendar B = Calendar.getInstance();
        B.setTime(date);
        B.add(Calendar.HOUR, -1);
        boolean sign = iHotelIncrRoomtypeService.getHotelIncrRoomtype(sdf.format(B.getTime()), sdf.format(date));
        if ( sign )
        {
            logger.info("监测房型上下架变化");
        }
    }
}
