package com.fntx.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.fntx.admin.service.IHotelIopriceService;
import com.fntx.admin.service.IHotelStaticInformationService;
import com.fntx.common.dao.BHotelListMapper;
import com.fntx.common.dao.BHotelRoomPriceInfosMapper;
import com.fntx.common.domain.dto.HotelRoomMongoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private String value;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Resource
    private IHotelStaticInformationService iHotelStaticInformationService;
    @Autowired
    private IHotelIopriceService iHotelIopriceService;
    @Autowired
    private BHotelListMapper bHotelListMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BHotelRoomPriceInfosMapper bHotelRoomPriceInfosMapper;

    @RequestMapping("hello")
    public String hello(String name) {
        return "hello," +name+ "获取配置为" + value;
    }

    @RequestMapping("getConfig")
    public String getConfig() {
        return "获取配置为" + value;
    }

    @RequestMapping("getHotelDetal")
    public JSONObject insertMongoHotelList(int current,int size,int count) {


        JSONObject obj = new JSONObject();
        iHotelStaticInformationService.addHotelDetails(current, size, count);


        return obj;
    }

    @RequestMapping("getRoomDetail")
    public JSONObject insertMongoHotelDetail(String name) {
        //System.out.println("开始进行酒店静态信息缓存到mongoDb!!"+ DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //JSONObject obj =  iHotelStaticInformationService.queryRoomDetailNotInMongo("6750578","1");
        JSONObject obj =  null;
        //System.out.println("结束进行酒店静态信息缓存到mongoDb!!"+ DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return obj;
    }

    /**
     * 直连报价
     * @return
     */
    @GetMapping("/hotelIoprice")
    public Object hotelIoprice() {
        redisTemplate.convertAndSend("IStaticInformationChange",
                                "MessageQueueDirectQuotation");
        return true;
    }

    /**
     * 入离起价
     * @return
     */
    @GetMapping("/hotelBaseprice")
    public Object hotelBaseprice() {
        redisTemplate.convertAndSend("IStaticInformationChange",
                                "MessageQueueStartingPrice");
        return true;
    }

    /**
     * 直连异常处理-用于手动触发处理
     * @return
     */
    @GetMapping("/hotelIopriceErrorHandle")
    public Object hotelIopriceErrorHandle() {
        redisTemplate.convertAndSend("IStaticInformationChange",
                "MessageQueueHotelIopriceErrorHandle");
        return true;
    }

    /**
     * 直连异常处理-用于手动停止处理
     * @return
     */
    @GetMapping("/hotelIopriceErrorHandleClose")
    public Object hotelIopriceErrorHandleClose() {
        redisTemplate.opsForValue().set("hotelIopriceErrorHandleErrorSign", "1");
        return true;
    }

    /**
     * 二次直连异常处理-用于手动触发处理
     * @return
     */
    @GetMapping("/hoteliopricErrorSecondaryHandle")
    public Object hoteliopricErrorSecondaryHandle() {
        redisTemplate.convertAndSend("IStaticInformationChange",
                "MessageQueueHoteliopricErrorSecondaryHandle");
        return true;
    }

    /**
     * 二次直连异常处理-用于手动停止处理
     * @return
     */
    @GetMapping("/hoteliopricErrorSecondaryHandleClose")
    public Object hoteliopricErrorSecondaryHandleClose() {
        redisTemplate.opsForValue().set("hoteliopricErrorSecondaryHandleSign", "1");
        return true;
    }

    /**
     *起价异常信息处理-用于手动触发处理
     * @return
     */
    @GetMapping("/hotelBasepriceErrorHandle")
    public Object hotelBasepriceErrorHandle() {
        redisTemplate.convertAndSend("IStaticInformationChange",
                                "MessageQueueHotelBasepriceErrorHandle");
        return true;
    }

    /**
     * 起价异常信息处理-用于手动停止处理
     * @return
     */
    @GetMapping("/hotelBasepriceErrorHandleClose")
    public Object hotelBasepriceErrorHandleClose() {
        redisTemplate.opsForValue().set("hotelBasepriceErrorDataListSign", "1");
        return true;
    }

    /**
     * 处理起价过滤字段取值内容
     * @return
     */
    @GetMapping("/hotelBasepriceScreening")
    public Object hotelBasepriceScreening() {
        redisTemplate.convertAndSend("IStaticInformationChange",
                                "MessageQueueHotelBasepriceScreening");
        return true;
    }

    /**
     * 处理直连报价过滤字段取值内容
     * @return
     */
    @GetMapping("/hotelIopriceScreening")
    public Object hotelIopriceScreening() {
        redisTemplate.convertAndSend("IStaticInformationChange",
                                "MessageQueueHotelIopriceScreening");
        return true;
    }

    @GetMapping("/selectMongodb")
    public Object selectMongodb() {
        Query query = new Query(Criteria.where("hotelId").is("968492"));
        List<HotelRoomMongoModel> hotelRoomMongoModel = mongoTemplate.find(query, HotelRoomMongoModel.class);
        return hotelRoomMongoModel;
    }

}
