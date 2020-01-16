package com.fntx.admin.messagequeue;

import com.fntx.admin.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: Receiver
 * @Author: 王俊文
 * @Date: 19-7-15 上午11:16
 * @Description: TODO
 */
@Component
public class Receiver  implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private IHotelIopriceService iHotelIopriceService;
    @Autowired
    private IHotelIncrRoomtypeService iHotelIncrRoomtypeService;
    @Autowired
    private IErrorDataHandleService iErrorDataHandleService;
    @Autowired
    private IHotelIncrDataService iHotelIncrDataService;
    @Autowired
    private IHotelBasepriceService iHotelBasepriceService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes)
    {
//        try {
//            Thread.sleep(60000*3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        logger.info("消息队列进来了");
        byte[] body = message.getBody();
        String redisKey = new String(body);
        String objectStr = String.valueOf(redisTemplate.opsForValue().get(redisKey));
        boolean sign = false;
        if ( objectStr != null && !"".equals(objectStr) )
        {
            if ( redisKey.indexOf("_hotelincrdataWare_") != -1 )
            {
                //静态信息
                sign = iHotelIncrDataService.hotelIncrDataWarehousing(objectStr, redisKey.split("_")[0]);
            }
            else if ( redisKey.indexOf("hotelincrroomtype_") != -1 )
            {
                //房型上下架
                sign = iHotelIncrRoomtypeService.hotelIncrRoomtypeWarehousing(objectStr);
            }
            else if ( redisKey.indexOf("_hotelbaseprice_Warehousing") != -1 )
            {
                //指定城市入离起起价
                sign = iHotelBasepriceService.hotelBaseprice(redisKey.split("_")[0]);
            }
            else if ( redisKey.indexOf("hotelbaseprice_WirelessSearch_") != -1 )
            {
                //入离起起价-国内酒店
                sign = iHotelBasepriceService.hotelBasepriceStorageWirelessSearch(objectStr);
            }
            else if ( redisKey.indexOf("hotelbaseprice_OnLineIntlGTASearch_") != -1 )
            {
                //入离起价-国际酒店
                sign = iHotelBasepriceService.hotelBasepriceStorageOnLineIntlGTASearch(objectStr);
            }
            else if ( redisKey.indexOf("_hotelioprice_") != -1 )
            {
                //酒店直连报价--入库
                sign = iHotelIopriceService.hotelIopriceStorage(objectStr, redisKey.split("_")[0]);
            }
            else if ( redisKey.indexOf("_hoteliopriceupdate") != -1 )
            {
                //更新指定酒店入离报价
                sign = iHotelIopriceService.getHotelIoprice(redisKey.split("_")[0]);
            }
            else if ( redisKey.indexOf("_hotelIopriceCache") != -1 )
            {
                //更新指定酒店缓存
                sign = iHotelIopriceService.hotelIopriceCache(redisKey.split("_")[0]);
            }
            else if ( redisKey.indexOf("0_hotelincrdata_IncrDataHotel_") != -1 )
            {
                //酒店静态信息增量--国内
                sign = iHotelIncrDataService.IncrDataHotelWarehousing();
            }
            else if ( redisKey.indexOf("0_hotelincrdata_IncrDataRoomType_") != -1 )
            {
                //物理房型静态信息增量--国内
                sign = iHotelIncrDataService.IncrDataRoomTypeWarehousing();
            }
            else if ( redisKey.indexOf("0_hotelincrdata_IncrDataRoom_") != -1 )
            {
                //售卖房型静态信息增量--国内
                sign = iHotelIncrDataService.IncrDataRoomWarehousing();
            }
            else if ( redisKey.indexOf("1_hotelincrdata_IncrDataHotel_") != -1 )
            {
                //酒店静态信息增量--国际
                sign = iHotelIncrDataService.IncrDataHotelWarehousingInternational();
            }
            else if ( redisKey.indexOf("1_hotelincrdata_IncrDataRoomType_") != -1 )
            {
                //物理房型静态信息增量--国际
                sign = iHotelIncrDataService.IncrDataRoomTypeWarehousingInternational();
            }
            else if ( redisKey.indexOf("1_hotelincrdata_IncrDataRoom_") != -1 )
            {
                //售卖房型静态信息增量--国际
                sign = iHotelIncrDataService.IncrDataRoomWarehousingInternational();
            }
            else if ( redisKey.indexOf("MessageQueueDirectQuotation") != -1 )
            {
                //直连报价 正式上线后预计会停止运行
                sign = iHotelIopriceService.hotelIoprice();
            }
            else if ( redisKey.indexOf("MessageQueueStartingPrice") != -1 )
            {
                //入离起价 正式上线后预计会停止运行
                sign = iHotelBasepriceService.getHotelBaseprice();
            }
            else if ( redisKey.indexOf("MessageQueueHotelIopriceErrorHandle") != -1 )
            {
                //直连报价异常数据处理
                sign = iErrorDataHandleService.hotelIopriceErrorHandle();
            }
            else if ( redisKey.indexOf("MessageQueueHoteliopricErrorSecondaryHandle") != -1 )
            {
                //二次直连报价异常数据处理
                sign = iErrorDataHandleService.hoteliopricErrorSecondaryHandle();
            }
            else if ( redisKey.indexOf("MessageQueueHotelBasepriceErrorHandle") != -1 )
            {
                //城市起价异常数据处理
                sign = iErrorDataHandleService.hotelBasepriceErrorDataList();
            }
            else if ( redisKey.indexOf("MessageQueueHotelBasepriceScreening") != -1 )
            {
                //城市起价 填充输入列表的筛选字段的值
                sign = iErrorDataHandleService.hotelBasepriceScreeningReceiver();
            }
            else if ( redisKey.indexOf("MessageQueueHotelIopriceScreening") != -1 )
            {
                //直连入离报价 填充输入列表的筛选字段的值
                sign = iErrorDataHandleService.hotelIopriceScreeningReceiver();
            }
            if ( sign )
            {
                redisTemplate.delete(redisKey);
            }
        }
    }
}
