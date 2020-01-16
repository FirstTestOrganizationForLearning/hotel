package com.fntx.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fntx.admin.service.IHotelIncrRoomtypeService;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.dao.BSaleRoomMapper;
import com.fntx.common.domain.BaseApiReq;
import com.fntx.common.domain.dto.HotelIncrRoomtypeDto;
import com.fntx.common.domain.dto.HotelIncrRoomtypeMgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelIncrRoomtypeServiceImpl
 * @Author: 王俊文
 * @Date: 19-8-9 下午3:12
 * @Description: 房型上下架--停用
 */
@Service
public class HotelIncrRoomtypeServiceImpl implements IHotelIncrRoomtypeService
{
    private static Logger logger = LoggerFactory.getLogger(HotelIncrRoomtypeServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BSaleRoomMapper bSaleRoomMapper;

    /**
     * @Description: 监测房型上下架
     * @Author: 王俊文
     * @Date: 19-7-15 下午4:54
     * @Param: [StartTime, EndTime]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-15 下午4:54          1.0          监测房型上下架
     */
    @Override
    public boolean getHotelIncrRoomtype(String StartTime, String EndTime)
    {
        JSONObject jsonObject = new JSONObject(16);
        JSONObject SearchCandidate = new JSONObject(16);
        JSONObject DateTimeRange = new JSONObject(16);
        /*
        定义静态信息发生变化的起始时间
         */
        DateTimeRange.put("Start", StartTime);
        /*
        定义静态信息发生变化的结束时间
         */
        DateTimeRange.put("End", EndTime);
        SearchCandidate.put("DateTimeRange", DateTimeRange);
        jsonObject.put("SearchCandidate", SearchCandidate);

        String icode = "";
        if ( Enviroment.IS_RELEASE_ENV )
        {
            icode = Enviroment.HOTEL_INCR_ROOMTYPE.getValue();
        }else
        {
            icode = Enviroment.TEST_HOTEL_INCR_ROOMTYPE.getValue();
        }
        //弗恩测试  正式情况下注释掉
//        icode = "hotel.incr.roomtype";
        JSONObject returnJSONObject = null;
        try {
            returnJSONObject = getResponse(jsonObject, icode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ( returnJSONObject.get("ResponseStatus") != null && !"".equals(returnJSONObject.get("ResponseStatus")) )
        {
            JSONObject ResponseStatus = returnJSONObject.getJSONObject("ResponseStatus");
            if ( ResponseStatus.getString("Ack") != null && "Success".equals(ResponseStatus.getString("Ack")) )
            {
                //解析数据体
                redisTemplate.opsForValue().set("hotelincrroomtype_"+StartTime, returnJSONObject.getJSONArray(
                        "RoomStatusChangeItems").toJSONString(), 60*60*24, TimeUnit.SECONDS);
                redisTemplate.convertAndSend("IStaticInformationChange", "hotelincrroomtype_"+StartTime);
                logger.info("房型上下架完成");
            }
        }
        return true;
    }

    /**
     * @Description: 房型上下架-调用接口-入库
     * @Author: 王俊文
     * @Date: 19-7-16 上午9:36
     * @Param: [test]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-16 上午9:36          1.0          房型上下架-调用接口-入库
     */
    @Override
    public boolean hotelIncrRoomtypeWarehousing(String hotelIncrRoomTypeStr)
    {
        //入库逻辑 暂时停止 start
//        hotelIncrRoomtypeWarehousingOracle(hotelIncrRoomTypeStr);
        //入库逻辑 暂时停止 end
        //mongodb逻辑 start
        hotelIncrRoomtypeWarehousingMongodb(hotelIncrRoomTypeStr);
        //mongodb逻辑 end
        return true;
    }

    /**
     * 房型上下架入mongodb
     * @param hotelIncrRoomTypeStr
     * @return
     */
    private boolean hotelIncrRoomtypeWarehousingMongodb(String hotelIncrRoomTypeStr)
    {
        List<HotelIncrRoomtypeMgDto> hotelIncrRoomtypeDtoList = JSONArray.parseArray(hotelIncrRoomTypeStr, HotelIncrRoomtypeMgDto.class);
        mongoTemplate.insertAll(hotelIncrRoomtypeDtoList);
        return true;
    }

    /**
     * 房型上下架入oracle数据库
     * @param hotelIncrRoomTypeStr
     * @return
     */
    private boolean hotelIncrRoomtypeWarehousingOracle(String hotelIncrRoomTypeStr)
    {
        List<HotelIncrRoomtypeDto> hotelIncrRoomtypeDtoList = JSONArray.parseArray(hotelIncrRoomTypeStr, HotelIncrRoomtypeDto.class);
        Integer index = 0;
        while (true)
        {
            List<HotelIncrRoomtypeDto> hotelIncrRoomtypeDtos = hotelIncrRoomtypeDtoList.subList(index, index+500);
            if ( hotelIncrRoomtypeDtos.size() > 0 )
            {
                bSaleRoomMapper.updateBSaleRoomList(hotelIncrRoomtypeDtos);
            }

            index+=500;
            if ( hotelIncrRoomtypeDtoList.size() < index+500 )
            {
                bSaleRoomMapper.updateBSaleRoomList(hotelIncrRoomtypeDtoList.subList(index,
                        hotelIncrRoomtypeDtoList.size()));
                break;
            }
        }
        return true;
    }

    /**
     * @Description: 统一远程请求
     * @Author: 王俊文
     * @Date: 19-7-16 上午9:36
     * @Param: [param, master, test]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-16 上午9:36          1.0         统一远程请求
     */
    private JSONObject getResponse(JSONObject param, String icode) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String token = "";
        JSONObject body;
        if (Enviroment.IS_RELEASE_ENV) {
            token = stringRedisTemplate.opsForValue().get("token");
        } else {
            token = stringRedisTemplate.opsForValue().get("testToken");
        }
        BaseApiReq baseApiReq = Enviroment.getBaseApiReq("0");
        String url = Enviroment.API_COMMON_URL.getValue();
        url = url.replace("{app_path}", baseApiReq.getUrl())
                .replace("{aid}", baseApiReq.getAID())
                .replace("{sid}", baseApiReq.getSID())
                .replace("{key}",  baseApiReq.getKEY())
                .replace("{format}", baseApiReq.getFormat())
                .replace("{GUID}", baseApiReq.getUUID())
                .replace("{Access_Token}", token)
                .replace("{ICODE}", icode);
//            //测试时使用  调用弗恩
//            url = url.replace("{app_path}", "hotel.tianxiafen.com:82")
//                    .replace("{aid}", "1")
//                    .replace("{sid}", "101")
//                    .replace("{key}", Enviroment.KEY.getValue())
//                    .replace("{format}", Enviroment.FORMAT.getValue())
//                    .replace("{GUID}", "A63FA038-32B2-46C7-A363-42E651378752")
//                    .replace("{Access_Token}", redisTemplate.opsForValue().get("testToken").toString())
//                    .replace("{ICODE}", icode);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
                body = restTemplate.exchange(url, HttpMethod.POST, formEntity, JSONObject.class).getBody();
        return body;
    }
}
