package com.fntx.admin.task;

import com.fntx.admin.service.IHotelIopriceService;
import com.fntx.common.domain.dto.HotelIncrRoomtypeMgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: ClearHotelIncrRoomtype
 * @Author: 王俊文
 * @Date: 19-8-7 上午11:50
 * @Description: 删除已过期房型上下架信息-停用
 */
//@Component
public class ClearHotelIncrRoomtype
{
    private static final Logger logger = LoggerFactory.getLogger(ClearHotelIncrRoomtype.class);

    @Autowired
    private IHotelIopriceService iHotelIopriceService;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * @Description: 删除已过期房型上下架信息
     * @Author: 王俊文
     * @Date: 19-7-15 上午10:00
     * @Param: []
     * @returns: void
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-15 上午10:00          1.0          删除已过期房型上下架信息-仅针对mongodb逻辑下的数据删除
     */
//    @Scheduled(cron = "0 0 0 * * ?")
    public void hotelIncrData()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
        Date date = new Date();
        Calendar B = Calendar.getInstance();
        B.setTime(date);
        B.add(Calendar.DAY_OF_MONTH, -1);
        Query query =
                new Query(Criteria.where("DateChangeLastTime").lte(sdf.format(B.getTime())));
        mongoTemplate.remove(query, HotelIncrRoomtypeMgDto.class);
        logger.info("删除已过期房型上下架信息");
    }
}
