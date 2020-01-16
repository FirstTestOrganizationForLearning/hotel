package com.fntx.admin.task;

import com.fntx.common.domain.dto.HotelIncrDataDto;
import com.fntx.common.domain.dto.InternationalHotelIncrDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: ClearHotelIncrData
 * @Author: 王俊文
 * @Date: 19-8-7 上午11:33
 * @Description: 删除已过期静态信息
 */
@Component
public class ClearHotelIncrData
{
    private static final Logger logger = LoggerFactory.getLogger(ClearHotelIncrData.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    /***
     * @Description: 删除已过期静态信息
     * @Author: 王俊文
     * @Date: 19-8-7 下午1:17
     * @Param: []
     * @returns: void
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-7 下午1:17          1.0      删除已过期静态信息
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void hotelIncrData()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
        Date date = new Date();
        Calendar B = Calendar.getInstance();
        B.setTime(date);
        B.add(Calendar.DAY_OF_MONTH, -1);
        Query query =
                new Query(Criteria.where("ChangeTime").lte(sdf.format(B.getTime())));
        mongoTemplate.remove(query, HotelIncrDataDto.class);
        mongoTemplate.remove(query, InternationalHotelIncrDataDto.class);
        logger.info("删除已过期静态信息");
    }
}
