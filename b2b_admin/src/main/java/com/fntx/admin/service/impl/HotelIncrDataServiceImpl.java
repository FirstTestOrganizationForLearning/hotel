package com.fntx.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fntx.admin.service.IHotelIncrDataService;
import com.fntx.admin.service.IHotelIopriceService;
import com.fntx.admin.service.IHotelStaticInformationService;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.dao.BStaticChangeInfoMapper;
import com.fntx.common.domain.BaseApiReq;
import com.fntx.common.domain.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelIncrDataServiceImpl
 * @Author: 王俊文
 * @Date: 19-8-9 下午3:08
 * @Description: 增量静态信息
 */
@Service
public class HotelIncrDataServiceImpl implements IHotelIncrDataService
{
    private static Logger logger = LoggerFactory.getLogger(HotelIncrDataServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BStaticChangeInfoMapper bStaticChangeInfoMapper;
    @Autowired
    private IHotelIopriceService iHotelIopriceService;
    @Autowired
    private IHotelStaticInformationService iHotelStaticInformationService;

    /**
     * @Description: 监测静态信息变化--国内
     * @Author: 王俊文
     * @Date: 19-7-15 下午4:53
     * @Param: [StartTime]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-15 下午4:53          1.0          监测静态信息变化
     */
    @Override
    public boolean getHotelIncrData(String StartTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");

        //清除过期数据
        //酒店
        redisTemplate.delete("0_hotelincrdata_IncrDataHotel");
        //物理房型
        redisTemplate.delete("0_hotelincrdata_IncrDataRoomType");
        //售卖房型
        redisTemplate.delete("0_hotelincrdata_IncrDataRoom");

        String LastRecordID = "0";
        int a = 0;
        Object o = redisTemplate.opsForValue().get("hotelincrdata_IncrDataHotelSign_0");
        Object o1 = redisTemplate.opsForValue().get("hotelincrdata_IncrDataRoomTypeSign_0");
        Object o2 = redisTemplate.opsForValue().get("hotelincrdata_IncrDataRoomSign_0");
        //0 执行通知  1 不执行通知
        String hotelincrdata_IncrDataHotelSign_0 = ( o == null ) ? "0": "1";
        String hotelincrdata_IncrDataRoomTypeSign_0 = ( o1 == null ) ? "0": "1";
        String hotelincrdata_IncrDataRoomSign_0 = ( o2 == null ) ? "0": "1";
        while (true)
        {
            //判断当前任务是否超时  如已超过10分钟的处理时间,则执行退出
            try {
                Date ss = sdf.parse(StartTime);
                Calendar Bs = Calendar.getInstance();
                Bs.setTime(ss);
                Bs.add(Calendar.MINUTE, 10);
                Date bsss = Bs.getTime();
                if ( new Date().compareTo(bsss) <= 0)
                {
                    logger.info("本次任务超时,退出!");
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Set<Map<String, Object>> hotelIncrDataDtoSet = new HashSet<>(16);
//            long startTime=System.currentTimeMillis();
            JSONObject jsonObject = new JSONObject(16);
            JSONObject SearchCandidate = new JSONObject(16);
            JSONObject Settings = new JSONObject(16);
            JSONObject PagingSettings = new JSONObject(16);
            /*
            定义静态信息发生变化的起始时间
             */
            SearchCandidate.put("StartTime", StartTime);
            /*
            默认取10分钟的数据
            定义从起始时间开始，查询多长时间内静态信息发生了变化。取值范围0到600，600表示10分钟。取值为0时，等价于600
             */
            SearchCandidate.put("Duration", 0);
            /*
            默认写死为F
            备注：目前仅放出少数几个字段的明细，请务必置为F。
             */
            Settings.put("IsShowChangeDetails", "T");
            /*
            每页最大记录数，默认为10，最大1000
             */
            PagingSettings.put("PageSize", 500);
            /*
            Start和Duration时间内首次调用，传0。之后，每次传上次调用时返回报文当中的LastRecordID
             */
            PagingSettings.put("LastRecordID", LastRecordID);
            jsonObject.put("SearchCandidate", SearchCandidate);
            jsonObject.put("Settings", Settings);
            jsonObject.put("PagingSettings", PagingSettings);

            String icode = "";
            if ( Enviroment.IS_RELEASE_ENV )
            {
                icode = Enviroment.HOTEL_INCR_DATA.getValue();
            }else
            {
                icode = Enviroment.TEST_HOTEL_INCR_DATA.getValue();
            }
            //弗恩测试  正式情况下注释掉
            icode = "hotel.incr.data";
            JSONObject returnJSONObject = null;
            try {
                returnJSONObject = getResponse(jsonObject, icode);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try
            {
                if ( returnJSONObject.get("ResponseStatus") != null && !"".equals(returnJSONObject.get("ResponseStatus")) )
                {
                    JSONObject ResponseStatus = returnJSONObject.getJSONObject("ResponseStatus");
                    if ( ResponseStatus.get("Ack") != null && "Success".equals(ResponseStatus.get("Ack")) )
                    {
                        //解析分页参数
                        JSONObject PagingInfo = returnJSONObject.getJSONObject("PagingInfo");
                        LastRecordID = String.valueOf(PagingInfo.get("LastRecordID"));
                        //解析数据体
                        Set<Map<String, Object>> hotelIncrDataDtoSets = new HashSet<>(16);
                        List<Map<String, Object>> hotelIncrDataDtoList= returnJSONObject.getObject("ChangeInfos",
                                ArrayList.class);
                        hotelIncrDataDtoSets.addAll(new HashSet<>(hotelIncrDataDtoList));
                        //售卖房型
                        for ( Map<String, Object> objectMap : hotelIncrDataDtoSets )
                        {
                            if ( objectMap.get("HotelID") == null || "".equals(objectMap.get("HotelID")) || "0".equals(objectMap.get(
                                    "HotelID")))
                            {
                                continue;
                            }
                            List<Map<String, Object>> mapList = (List<Map<String, Object>>) objectMap.get("ChangeDetails");
                            boolean sign = false;
                            List<Map<String, Object>> mapLists = new ArrayList<>(16);
                            for ( Map<String, Object> map : mapList )
                            {
                                if ( map.get("NewValue") == null || "".equals(map.get("NewValue")) || (!map.get("Name").equals(
                                        "IsOpen")  && !map.get("Name").equals("Bookable")) )
                                {
                                    sign = true;
                                }else if ( map.get("Name").equals("IsOpen") )
                                {
                                    mapLists.add(map);
                                }else if ( map.get("Name").equals("Bookable") )
                                {
                                    mapLists.add(map);
                                }
                            }
                            if ( sign )
                            {
                                continue;
                            }

                            objectMap.put("ChangeDetails", mapLists);
                            if ( objectMap.get("Category").equals("Hotel") )
                            {
                                //酒店
                                hotelIncrDataDtoSet.add(objectMap);
                                //酒店入redis
                                redisTemplate.opsForList().leftPush("0_hotelincrdata_IncrDataHotel",
                                        JSON.toJSONString(objectMap));
                            }
                            if ( "RoomType".equals(objectMap.get("Category")) )
                            {
                                //物理房型
                                hotelIncrDataDtoSet.add(objectMap);
                                //物理房型入redis
                                redisTemplate.opsForList().leftPush("0_hotelincrdata_IncrDataRoomType",
                                        JSON.toJSONString(objectMap));
                            }
                            if ( "Room".equals(objectMap.get("Category")) )
                            {
                                //售卖房型
                                hotelIncrDataDtoSet.add(objectMap);
                                //售卖房型入redis
                                redisTemplate.opsForList().leftPush("0_hotelincrdata_IncrDataRoom",
                                        JSON.toJSONString(objectMap));
                            }
                        }


                        redisTemplate.opsForValue().set("0_hotelincrdataWare_"+LastRecordID+StartTime,
                            JSONArray.parseArray(JSON.toJSONString(hotelIncrDataDtoSet)).toJSONString(), 60*60*24,
                                TimeUnit.SECONDS);
                        redisTemplate.convertAndSend("IStaticInformationChange", "0_hotelincrdataWare_"+LastRecordID+StartTime);
                        //获取到数据后,通知一次.
                        if ( "0".equals(hotelincrdata_IncrDataHotelSign_0) )
                        {
                            redisTemplate.convertAndSend("IStaticInformationChange", "0_hotelincrdata_IncrDataHotel_"+LastRecordID+StartTime);
                            redisTemplate.opsForValue().set("hotelincrdata_IncrDataHotelSign_0", "0", 60*20, TimeUnit.SECONDS);
                        }
                        if ( "0".equals(hotelincrdata_IncrDataRoomTypeSign_0) )
                        {
                            redisTemplate.convertAndSend("IStaticInformationChange", "0_hotelincrdata_IncrDataRoomType_"+LastRecordID+StartTime);
                            redisTemplate.opsForValue().set("hotelincrdata_IncrDataRoomTypeSign_0", "0", 60*20, TimeUnit.SECONDS);
                        }
                        if ( "0".equals(hotelincrdata_IncrDataRoomSign_0) )
                        {
                            redisTemplate.convertAndSend("IStaticInformationChange", "0_hotelincrdata_IncrDataRoom_"+LastRecordID+StartTime);
                            redisTemplate.opsForValue().set("hotelincrdata_IncrDataRoomSign_0", "0", 60*20, TimeUnit.SECONDS);
                        }

//                        hotelIncrDataDtoSet.addAll(new HashSet<>(hotelIncrDataDtoList));
    //                    redisTemplate.opsForValue().set("hotelincrdata_"+LastRecordID+StartTime,
    //                            returnJSONObject.getJSONArray("ChangeInfos").toJSONString(), 60*60*24, TimeUnit.SECONDS);
    //                    redisTemplate.convertAndSend("IStaticInformationChange", "hotelincrdata_"+LastRecordID+StartTime);
                        a++;
//                        long endTime=System.currentTimeMillis();
//                        logger.info("我执行了:" + a + "; " + LastRecordID + " 运行时间: " + (endTime-startTime) + "毫秒");
                        if ( hotelIncrDataDtoList.size() < 500 )
                        {
                            System.out.println("国内增量查询完了:" + a);
                            break;
                        }
                    }
                }
            }catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
        //酒店
//        JSONObject jsonObject = new JSONObject(16);
//        Integer RoomTypeNumber = 0, RoomNumber = 0, IsOpenNumber = 0, bookableNumber = 0, HotelNumber = 0;

//        Set<Map<String, Object>> hotelIncrDataHotelSet = new HashSet<>();
        //物理房型
//        Set<Map<String, Object>> hotelIncrDataRoomTypeSet = new HashSet<>();

//        for ( Map<String, Object> objectMap : hotelIncrDataDtoSet )
//        {
//            if ( objectMap.get("HotelID") == null || "".equals(objectMap.get("HotelID")) || "0".equals(objectMap.get(
//                    "HotelID")))
//            {
//                continue;
//            }
//            List<Map<String, Object>> mapList = (List<Map<String, Object>>) objectMap.get("ChangeDetails");
//            boolean sign = false;
//            List<Map<String, Object>> mapLists = new ArrayList<>(16);
//            for ( Map<String, Object> map : mapList )
//            {
//                if ( map.get("NewValue") == null || "".equals(map.get("NewValue")) || (!map.get("Name").equals(
//                        "IsOpen")  && !map.get("Name").equals("Bookable")) )
//                {
//                    sign = true;
//                }else if ( map.get("Name").equals("IsOpen") )
//                {
//                    IsOpenNumber++;
//                    mapLists.add(map);
//                }else if ( map.get("Name").equals("Bookable") )
//                {
//                    bookableNumber++;
//                    mapLists.add(map);
//                }
//            }
//            if ( sign )
//            {
//                continue;
//            }
//
//            objectMap.put("ChangeDetails", mapLists);
//            if ( objectMap.get("Category").equals("Hotel") )
//            {
//                HotelNumber++;
////                //酒店
////                hotelIncrDataHotelSet.add(objectMap);
//            }
//            if ( "RoomType".equals(objectMap.get("Category")) )
//            {
//                RoomTypeNumber++;
//                //物理房型
////                hotelIncrDataRoomTypeSet.add(objectMap);
//            }
//            if ( "Room".equals(objectMap.get("Category")) )
//            {
//                RoomNumber++;
//                //售卖房型
//                hotelIncrDataRoomSet.add(objectMap);
//            }
//        }
//        jsonObject.put("HotelNumber", HotelNumber);
//        jsonObject.put("RoomTypeNumber", RoomTypeNumber);
//        jsonObject.put("RoomNumber", RoomNumber);
//        jsonObject.put("IsOpenNumber", IsOpenNumber);
//        jsonObject.put("bookableNumber", bookableNumber);

//        JSONObject jsonObject1 = new JSONObject(16);
//        jsonObject1.put(StartTime, jsonObject);
//
//
//        redisTemplate.opsForList().leftPush("HotelIncrDataNumber", jsonObject1.toJSONString());

        //入库数据
//        List<Map<String, Object>> warehousingList = new ArrayList<>();
//        warehousingList.addAll(hotelIncrDataHotelSet);
//        warehousingList.addAll(hotelIncrDataRoomTypeSet);
//        warehousingList.addAll(hotelIncrDataRoomSet);
//        //酒店入redis
//        redisTemplate.opsForValue().set("0_hotelincrdata_IncrDataHotel_"+LastRecordID+StartTime,
//                            JSONArray.parseArray(JSON.toJSONString(hotelIncrDataHotelSet)).toJSONString(), 60*60*24,
//                TimeUnit.SECONDS);
//        redisTemplate.convertAndSend("IStaticInformationChange", "0_hotelincrdata_IncrDataHotel_"+LastRecordID+StartTime);
//        //物理房型入redis
//        redisTemplate.opsForValue().set("0_hotelincrdata_IncrDataRoomType_"+LastRecordID+StartTime,
//                            JSONArray.parseArray(JSON.toJSONString(hotelIncrDataRoomTypeSet)).toJSONString(),
//                60*60*24, TimeUnit.SECONDS);
//        redisTemplate.convertAndSend("IStaticInformationChange", "0_hotelincrdata_IncrDataRoomType_"+LastRecordID+StartTime);
//        //售卖房型入redis
//        redisTemplate.opsForValue().set("0_hotelincrdata_IncrDataRoom_"+LastRecordID+StartTime,
//                            JSONArray.parseArray(JSON.toJSONString(hotelIncrDataRoomSet)).toJSONString(), 60*60*24, TimeUnit.SECONDS);
//        redisTemplate.convertAndSend("IStaticInformationChange", "0_hotelincrdata_IncrDataRoom_"+LastRecordID+StartTime);
        return true;
    }

    /**
     * @Description: 监测静态信息变化--国际
     * @Author: 王俊文
     * @Date: 2019/9/11 下午3:04
     * @Param: [StartTime]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/9/11 下午3:04          1.0          监测静态信息变化--国际
     */
    @Override
    public boolean getHotelIncrDataInternational(String StartTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
        //清除过期数据
        //酒店
        redisTemplate.delete("1_hotelincrdata_IncrDataHotel");
        //物理房型
        redisTemplate.delete("1_hotelincrdata_IncrDataRoomType");
        //售卖房型
        redisTemplate.delete("1_hotelincrdata_IncrDataRoom");

        String LastRecordID = "0";
        int a = 0;
        Object o = redisTemplate.opsForValue().get("hotelincrdata_IncrDataHotelSign_1");
        Object o1 = redisTemplate.opsForValue().get("hotelincrdata_IncrDataRoomTypeSign_1");
        Object o2 = redisTemplate.opsForValue().get("hotelincrdata_IncrDataRoomSign_1");
        //0 执行通知  1 不执行通知
        String hotelincrdata_IncrDataHotelSign_1 = ( o == null ) ? "0": "1";
        String hotelincrdata_IncrDataRoomTypeSign_1 = ( o1 == null ) ? "0": "1";
        String hotelincrdata_IncrDataRoomSign_1 = ( o2 == null ) ? "0": "1";
        while (true)
        {
            //判断当前任务是否超时  如已超过10分钟的处理时间,则执行退出
            try {
                Date ss = sdf.parse(StartTime);
                Calendar Bs = Calendar.getInstance();
                Bs.setTime(ss);
                Bs.add(Calendar.MINUTE, 10);
                Date bsss = Bs.getTime();
                if ( new Date().compareTo(bsss) <= 0)
                {
                    logger.info("本次任务超时,退出!");
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Set<Map<String, Object>> hotelIncrDataDtoSet = new HashSet<>(16);
//            long startTime=System.currentTimeMillis();
            JSONObject jsonObject = new JSONObject(16);
            JSONObject SearchCandidate = new JSONObject(16);
            JSONObject Settings = new JSONObject(16);
            JSONObject PagingSettings = new JSONObject(16);
            /*
            定义静态信息发生变化的起始时间
             */
            SearchCandidate.put("StartTime", StartTime);
            /*
            默认取10分钟的数据
            定义从起始时间开始，查询多长时间内静态信息发生了变化。取值范围0到600，600表示10分钟。取值为0时，等价于600
             */
            SearchCandidate.put("Duration", 0);
            /*
            默认写死为F
            备注：目前仅放出少数几个字段的明细，请务必置为F。
             */
            Settings.put("IsShowChangeDetails", "T");
            /*
            每页最大记录数，默认为10，最大1000
             */
            PagingSettings.put("PageSize", 500);
            /*
            Start和Duration时间内首次调用，传0。之后，每次传上次调用时返回报文当中的LastRecordID
             */
            PagingSettings.put("LastRecordID", LastRecordID);
            jsonObject.put("SearchCandidate", SearchCandidate);
            jsonObject.put("Settings", Settings);
            jsonObject.put("PagingSettings", PagingSettings);

            String icode = "";
            if ( Enviroment.IS_RELEASE_ENV )
            {
                icode = Enviroment.HOTEL_INCR_DATA.getValue();
            }else
            {
                icode = Enviroment.TEST_HOTEL_INCR_DATA.getValue();
            }
            //弗恩测试  正式情况下注释掉
            icode = "hotel.incr.data";
            JSONObject returnJSONObject = null;
            try {
                returnJSONObject = getResponse(jsonObject, icode);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try
            {
                if ( returnJSONObject.get("ResponseStatus") != null && !"".equals(returnJSONObject.get("ResponseStatus")) )
                {
                    JSONObject ResponseStatus = returnJSONObject.getJSONObject("ResponseStatus");
                    if ( ResponseStatus.get("Ack") != null && "Success".equals(ResponseStatus.get("Ack")) )
                    {
                        //解析分页参数
                        JSONObject PagingInfo = returnJSONObject.getJSONObject("PagingInfo");
                        LastRecordID = String.valueOf(PagingInfo.get("LastRecordID"));
                        //解析数据体
                        Set<Map<String, Object>> hotelIncrDataDtoSets = new HashSet<>(16);
                        List<Map<String, Object>> hotelIncrDataDtoList= returnJSONObject.getObject("ChangeInfos",
                                ArrayList.class);
                        hotelIncrDataDtoSets.addAll(new HashSet<>(hotelIncrDataDtoList));
                        //售卖房型
                        for ( Map<String, Object> objectMap : hotelIncrDataDtoSets )
                        {
                            if ( objectMap.get("HotelID") == null || "".equals(objectMap.get("HotelID")) || "0".equals(objectMap.get(
                                    "HotelID")))
                            {
                                continue;
                            }
                            List<Map<String, Object>> mapList = (List<Map<String, Object>>) objectMap.get("ChangeDetails");
                            boolean sign = false;
                            List<Map<String, Object>> mapLists = new ArrayList<>(16);
                            for ( Map<String, Object> map : mapList )
                            {
                                if ( map.get("NewValue") == null || "".equals(map.get("NewValue")) || (!map.get("Name").equals(
                                        "IsOpen")  && !map.get("Name").equals("Bookable")) )
                                {
                                    sign = true;
                                }else if ( map.get("Name").equals("IsOpen") )
                                {
                                    mapLists.add(map);
                                }else if ( map.get("Name").equals("Bookable") )
                                {
                                    mapLists.add(map);
                                }
                            }
                            if ( sign )
                            {
                                continue;
                            }

                            objectMap.put("ChangeDetails", mapLists);
                            if ( objectMap.get("Category").equals("Hotel") )
                            {
                                //酒店
                                hotelIncrDataDtoSet.add(objectMap);
                                //酒店入redis
                                redisTemplate.opsForList().leftPush("1_hotelincrdata_IncrDataHotel",
                                        JSON.toJSONString(objectMap));
                            }
                            if ( "RoomType".equals(objectMap.get("Category")) )
                            {
                                //物理房型
                                hotelIncrDataDtoSet.add(objectMap);
                                //物理房型入redis
                                redisTemplate.opsForList().leftPush("1_hotelincrdata_IncrDataRoomType",
                                        JSON.toJSONString(objectMap));
                            }
                            if ( "Room".equals(objectMap.get("Category")) )
                            {
                                //售卖房型
                                hotelIncrDataDtoSet.add(objectMap);
                                //售卖房型入redis
                                redisTemplate.opsForList().leftPush("1_hotelincrdata_IncrDataRoom",
                                        JSON.toJSONString(objectMap));
                            }
                        }


                        redisTemplate.opsForValue().set("1_hotelincrdataWare_"+LastRecordID+StartTime,
                                            JSONArray.parseArray(JSON.toJSONString(hotelIncrDataDtoSet)).toJSONString(), 60*60*24,
                                TimeUnit.SECONDS);
                        redisTemplate.convertAndSend("IStaticInformationChange", "1_hotelincrdataWare_"+LastRecordID+StartTime);
                        //获取到数据后,通知一次.
                        if ( "0".equals(hotelincrdata_IncrDataHotelSign_1) )
                        {
                            redisTemplate.convertAndSend("IStaticInformationChange", "1_hotelincrdata_IncrDataHotel_"+LastRecordID+StartTime);
                            redisTemplate.opsForValue().set("hotelincrdata_IncrDataHotelSign_1", "0", 60*20, TimeUnit.SECONDS);
                        }
                        if ( "0".equals(hotelincrdata_IncrDataRoomTypeSign_1) )
                        {
                            redisTemplate.convertAndSend("IStaticInformationChange", "1_hotelincrdata_IncrDataRoomType_"+LastRecordID+StartTime);
                            redisTemplate.opsForValue().set("hotelincrdata_IncrDataRoomTypeSign_1", "0", 60*20, TimeUnit.SECONDS);
                        }
                        if ( "0".equals(hotelincrdata_IncrDataRoomSign_1) )
                        {
                            redisTemplate.convertAndSend("IStaticInformationChange", "1_hotelincrdata_IncrDataRoom_"+LastRecordID+StartTime);
                            redisTemplate.opsForValue().set("hotelincrdata_IncrDataRoomSign_1", "0", 60*20, TimeUnit.SECONDS);
                        }

//                        hotelIncrDataDtoSet.addAll(new HashSet<>(hotelIncrDataDtoList));
    //                    redisTemplate.opsForValue().set("hotelincrdata_"+LastRecordID+StartTime,
    //                            returnJSONObject.getJSONArray("ChangeInfos").toJSONString(), 60*60*24, TimeUnit.SECONDS);
    //                    redisTemplate.convertAndSend("IStaticInformationChange", "hotelincrdata_"+LastRecordID+StartTime);
                        a++;
//                        long endTime=System.currentTimeMillis();
//                        logger.info("我执行了:" + a + "; " + LastRecordID + " 运行时间: " + (endTime-startTime) + "毫秒");
                        if ( hotelIncrDataDtoList.size() < 500 )
                        {
                            System.out.println("国际增量查询完了:" + a);
                            break;
                        }
                    }
                }
            }catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
       /* //酒店
//        JSONObject jsonObject = new JSONObject(16);
//        Integer RoomTypeNumber = 0, RoomNumber = 0, IsOpenNumber = 0, bookableNumber = 0, HotelNumber = 0;

//        Set<Map<String, Object>> hotelIncrDataHotelSet = new HashSet<>();
        //物理房型
//        Set<Map<String, Object>> hotelIncrDataRoomTypeSet = new HashSet<>();

//        for ( Map<String, Object> objectMap : hotelIncrDataDtoSet )
//        {
//            if ( objectMap.get("HotelID") == null || "".equals(objectMap.get("HotelID")) || "0".equals(objectMap.get(
//                    "HotelID")))
//            {
//                continue;
//            }
//            List<Map<String, Object>> mapList = (List<Map<String, Object>>) objectMap.get("ChangeDetails");
//            boolean sign = false;
//            List<Map<String, Object>> mapLists = new ArrayList<>(16);
//            for ( Map<String, Object> map : mapList )
//            {
//                if ( map.get("NewValue") == null || "".equals(map.get("NewValue")) || (!map.get("Name").equals(
//                        "IsOpen")  && !map.get("Name").equals("Bookable")) )
//                {
//                    sign = true;
//                }else if ( map.get("Name").equals("IsOpen") )
//                {
//                    IsOpenNumber++;
//                    mapLists.add(map);
//                }else if ( map.get("Name").equals("Bookable") )
//                {
//                    bookableNumber++;
//                    mapLists.add(map);
//                }
//            }
//            if ( sign )
//            {
//                continue;
//            }
//
//            objectMap.put("ChangeDetails", mapLists);
//            if ( objectMap.get("Category").equals("Hotel") )
//            {
//                HotelNumber++;
////                //酒店
////                hotelIncrDataHotelSet.add(objectMap);
//            }
//            if ( "RoomType".equals(objectMap.get("Category")) )
//            {
//                RoomTypeNumber++;
//                //物理房型
////                hotelIncrDataRoomTypeSet.add(objectMap);
//            }
//            if ( "Room".equals(objectMap.get("Category")) )
//            {
//                RoomNumber++;
//                //售卖房型
//                hotelIncrDataRoomSet.add(objectMap);
//            }
//        }
//        jsonObject.put("HotelNumber", HotelNumber);
//        jsonObject.put("RoomTypeNumber", RoomTypeNumber);
//        jsonObject.put("RoomNumber", RoomNumber);
//        jsonObject.put("IsOpenNumber", IsOpenNumber);
//        jsonObject.put("bookableNumber", bookableNumber);

//        JSONObject jsonObject1 = new JSONObject(16);
//        jsonObject1.put(StartTime, jsonObject);
//
//
//        redisTemplate.opsForList().leftPush("HotelIncrDataNumber", jsonObject1.toJSONString());

        //入库数据
//        List<Map<String, Object>> warehousingList = new ArrayList<>();
//        warehousingList.addAll(hotelIncrDataHotelSet);
//        warehousingList.addAll(hotelIncrDataRoomTypeSet);
//        warehousingList.addAll(hotelIncrDataRoomSet);*/
//        //酒店入redis
//        redisTemplate.opsForValue().set("1_hotelincrdata_IncrDataHotel_"+LastRecordID+StartTime,
//                            JSONArray.parseArray(JSON.toJSONString(hotelIncrDataHotelSet)).toJSONString(), 60*60*24,
//                TimeUnit.SECONDS);
//        redisTemplate.convertAndSend("IStaticInformationChange",
//        "1_hotelincrdata_IncrDataHotel_"+LastRecordID+StartTime);
//        //物理房型入redis
//        redisTemplate.opsForValue().set("1_hotelincrdata_IncrDataRoomType_"+LastRecordID+StartTime,
//                            JSONArray.parseArray(JSON.toJSONString(hotelIncrDataRoomTypeSet)).toJSONString(),
//                60*60*24, TimeUnit.SECONDS);
//        redisTemplate.convertAndSend("IStaticInformationChange", "1_hotelincrdata_IncrDataRoomType_"+LastRecordID+StartTime);
//        //售卖房型入redis
//        redisTemplate.opsForValue().set("1_hotelincrdata_IncrDataRoom_"+LastRecordID+StartTime,
//                            JSONArray.parseArray(JSON.toJSONString(hotelIncrDataRoomSet)).toJSONString(), 60*60*24, TimeUnit.SECONDS);
//        redisTemplate.convertAndSend("IStaticInformationChange", "1_hotelincrdata_IncrDataRoom_"+LastRecordID+StartTime);
        return true;
    }

    /**
     * @Description: 静态信息-调用接口-入库
     * @Author: 王俊文
     * @Date: 2019/9/17 下午4:30
     * @Param: [hotelIncrDataStr    内容    String, isInte    类型(0:国内  1:国际)    String]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/9/17 下午4:30          1.0          静态信息-调用接口-入库
     */
    @Override
    public boolean hotelIncrDataWarehousing(String hotelIncrDataStr, String isInte)
    {
        //入库逻辑 start 暂时停用
//        hotelIncrDataWarehousingOracle(hotelIncrDataStr, isInte);
        //入库逻辑 end 暂时停用
        //入mongodb start 暂时启用
        hotelIncrDataWarehousingMongodb(hotelIncrDataStr, isInte);
        //入mongodb end 暂时启用
        return true;
    }

    /**
     * 静态增量入mongodb
     * @param hotelIncrDataStr
     * @return
     */
    private boolean hotelIncrDataWarehousingMongodb(String hotelIncrDataStr, String isInte)
    {
        try
        {
            if ( "0".equals(isInte) )
            {
                List<HotelIncrDataDto> hotelIncrDataDtoList = JSONArray.parseArray(hotelIncrDataStr, HotelIncrDataDto.class);
                mongoTemplate.insertAll(hotelIncrDataDtoList);
            }else if ( "1".equals(isInte) )
            {
                List<InternationalHotelIncrDataDto> internationalHotelIncrDataDtoList = JSONArray.parseArray(hotelIncrDataStr,
                        InternationalHotelIncrDataDto.class);
                mongoTemplate.insertAll(internationalHotelIncrDataDtoList);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 静态增量入oracle
     * @param hotelIncrDataStr
     * @return
     */
    private boolean hotelIncrDataWarehousingOracle(String hotelIncrDataStr)
    {
        List<HotelIncrDataWarehousingDto> hotelIncrDataWarehousingDtoList = JSONArray.parseArray(hotelIncrDataStr, HotelIncrDataWarehousingDto.class);
        //入库
        Integer index = 0;
        while (true)
        {
            List<HotelIncrDataWarehousingDto> hotelIncrRoomtypeDtos = hotelIncrDataWarehousingDtoList.subList(index, index+500);
            if ( hotelIncrRoomtypeDtos.size() > 0 )
            {
                bStaticChangeInfoMapper.insertBstaticChangeList(hotelIncrRoomtypeDtos);
            }

            index+=500;
            if ( hotelIncrDataWarehousingDtoList.size() < index+500 )
            {
                bStaticChangeInfoMapper.insertBstaticChangeList(hotelIncrDataWarehousingDtoList.subList(index,
                        hotelIncrDataWarehousingDtoList.size()));
                break;
            }
        }
        return true;
    }

    /**
     * @Description: 静态信息-对应酒店变动同步--国内
     * @Author: 王俊文
     * @Date: 19-8-2 下午6:06
     * @Param: [hotelIncrDataStr]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-2 下午6:06          1.0          对应酒店变动同步
     */
    @Override
    public boolean IncrDataHotelWarehousing()
    {
//        logger.info("静态信息-对应酒店变动同步--国内");
        Map<String, String> signMap = new LinkedHashMap<>(16);

        //酒店
//        List<HotelIncrDataDto> hotelIncrDataDtoList = JSONArray.parseArray(hotelIncrDataStr, HotelIncrDataDto.class);
//        for ( HotelIncrDataDto hotelIncrDataDto : hotelIncrDataDtoList)
//        {
            //入库逻辑 start 暂时停用
//            System.out.println(j.toString());
            //同步数据库
            //入库逻辑 end暂时停用

//        }
        while( redisTemplate.opsForList().size("0_hotelincrdata_IncrDataHotel") > 0 )
        {
            HotelIncrDataDto hotelIncrDataDto =
                    JSONObject.parseObject(redisTemplate.opsForList().rightPop("0_hotelincrdata_IncrDataHotel").toString(),
                            HotelIncrDataDto.class);
            String hotelId = hotelIncrDataDto.getHotelID();
            Query query = new Query(Criteria.where("hotelId").is(hotelIncrDataDto.getHotelID()));
            List<HotelMongoModel> hotelMongoModelList = mongoTemplate.find(query, HotelMongoModel.class);

            boolean sign = true;
            if ( hotelMongoModelList != null && hotelMongoModelList.size() > 0 )
            {
                for ( HotelMongoModel hotelMongoModel : hotelMongoModelList )
                {
                    HotelStaticResPonse hotelStaticResPonse =
                            JSONObject.parseObject(hotelMongoModel.getHotelDetail(), HotelStaticResPonse.class);
                    if ( hotelStaticResPonse.getResponseStatus().getAck() == null ||
                            !"Success".equals(hotelStaticResPonse.getResponseStatus().getAck()) ||
                            (hotelStaticResPonse.getHotelStaticInfo() == null || hotelStaticResPonse.getHotelStaticInfo().getHotelID()==0) )
                    {
                        //判断本次是否已就该酒店执行过处理
                        if ( signMap.containsKey(hotelId) )
                        {
                            continue;
                        }else
                        {
                            signMap.put(hotelId, "");
                        }
                        sign = false;
                        iHotelStaticInformationService.queryHotelDetailNotInMongo(hotelIncrDataDto.getHotelID(),
                                "0", "0");
                    }
                }
            }else
            {
                //判断本次是否已就该酒店执行过处理
                if ( signMap.containsKey(hotelId) )
                {
                    continue;
                }else
                {
                    signMap.put(hotelId, "");
                }
                sign = false;
                iHotelStaticInformationService.queryHotelDetailNotInMongo(hotelIncrDataDto.getHotelID(),"1", "0");
            }

//            System.out.println("静态信息-对应酒店变动同步--国内" + (sign? "数据存在": "调用更新") );
        }
        redisTemplate.delete("hotelincrdata_IncrDataHotelSign_0");
        return true;
    }

    /**
     * @Description: 静态信息-对应物理房型变动同步--国内
     * @Author: 王俊文
     * @Date: 19-8-2 下午6:06
     * @Param: [hotelIncrDataStr]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-2 下午6:06          1.0          对应物理房型变动同步
     */
    @Override
    public boolean IncrDataRoomTypeWarehousing()
    {
//        logger.info("静态信息-对应物理房型变动同步--国内");
        Map<String, String> signMap = new LinkedHashMap<>(16);

        //物理房型
//        List<HotelIncrDataDto> hotelIncrDataDtoList = JSONArray.parseArray(hotelIncrDataStr, HotelIncrDataDto.class);
//        for ( HotelIncrDataDto hotelIncrDataDto : hotelIncrDataDtoList)
//        {
            //入库逻辑 start 暂时停用
//            System.out.println(j.toString());
            //同步数据库
            //入库逻辑 end暂时停用

//        }
        while( redisTemplate.opsForList().size("0_hotelincrdata_IncrDataRoomType") > 0 )
        {
            boolean sign = false;
            HotelIncrDataDto hotelIncrDataDto =
                    JSONObject.parseObject(redisTemplate.opsForList().rightPop("0_hotelincrdata_IncrDataRoomType").toString(),
                            HotelIncrDataDto.class);
            String hotelId = hotelIncrDataDto.getHotelID();
            HotelRoomDetailResModel hotelRoomDetailResModel = new HotelRoomDetailResModel();
            Query query = new Query(Criteria.where("hotelId").is(hotelId));
            List<HotelRoomMongoModel> hotelRoomMongoModel = mongoTemplate.find(query, HotelRoomMongoModel.class);
            try
            {
                 if ( hotelRoomMongoModel == null || hotelRoomMongoModel.size() <= 0 )
                {
                    //mongodb中酒店不存在
                    //判断本次是否已就该酒店执行过处理
                    if ( signMap.containsKey(hotelId) )
                    {
                        continue;
                    }else
                    {
                        signMap.put(hotelId, "");
                    }
                    //通知更新房型
                    iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"1", "0");
                }else
                {
                    //mongodb中酒店存在
                    hotelRoomDetailResModel = JSONObject.parseObject(hotelRoomMongoModel.get(0).getHotelRoomDetail(),
                            HotelRoomDetailResModel.class);
                    if ( hotelRoomDetailResModel.getResponseStatus().getAck() == null || !"Success".equals(hotelRoomDetailResModel.getResponseStatus().getAck()) ||
                            (hotelRoomDetailResModel.getRoomStaticInfos() == null || hotelRoomDetailResModel.getRoomStaticInfos().size() <=0 ) )
                    {
                        //房型数据不存在
                        //判断本次是否已就该酒店执行过处理
                        if ( signMap.containsKey(hotelId) )
                        {
                            continue;
                        }else
                        {
                            signMap.put(hotelId, "");
                        }
                        //通知更新房型
                        iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"0", "0");
                    }else
                    {
                        //房型数据存在，判断对应房型是否存在
                        for ( HotelRoomDetailResModel.RoomStaticInfosBean roomStaticInfosBean :
                                hotelRoomDetailResModel.getRoomStaticInfos() )
                        {
                            if ( hotelIncrDataDto.getRoomTypeID().equals(roomStaticInfosBean.getRoomTypeInfo().getRoomTypeID()) )
                            {
                                sign = true;
                                break;
                            }
                        }
                        if ( !sign )
                        {
                            //判断本次是否已就该酒店执行过处理
                            if ( signMap.containsKey(hotelId) )
                            {
                                continue;
                            }else
                            {
                                signMap.put(hotelId, "");
                            }
                            iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"0", "0");
                        }
                    }
                }
            }catch (Exception e)
            {
                logger.info("本次数据跳过!" + e.getMessage());
            }
//            System.out.println("静态信息-对应物理房型变动同步--国内" + (sign? "数据存在": "调用更新"));
        }
        redisTemplate.delete("hotelincrdata_IncrDataRoomTypeSign_0");

        return true;
    }

    /**
     * @Description: 静态信息-对应售卖房型变动同步--国内
     * @Author: 王俊文
     * @Date: 19-8-2 下午6:06
     * @Param: [hotelIncrDataStr]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-2 下午6:06          1.0          对应售卖房型变动同步
     */
    @Override
    public boolean IncrDataRoomWarehousing()
    {
//        logger.info("静态信息-对应售卖房型变动同步--国内");
        Map<String, String> signMap = new LinkedHashMap<>(16);

        //售卖房型

//        List<HotelIncrDataDto> hotelIncrDataDtoList = JSONArray.parseArray(hotelIncrDataStr, HotelIncrDataDto.class);
//        for ( HotelIncrDataDto hotelIncrDataDto : hotelIncrDataDtoList)
//        {
            //入库逻辑 start 暂时停用
//            System.out.println(j.toString());
            //同步数据库
            //入库逻辑 end暂时停用

//        }
        while( redisTemplate.opsForList().size("0_hotelincrdata_IncrDataRoom") > 0 )
        {
            boolean sign = false;
            HotelIncrDataDto hotelIncrDataDto =
                    JSONObject.parseObject(redisTemplate.opsForList().rightPop("0_hotelincrdata_IncrDataRoom").toString(),
                            HotelIncrDataDto.class);
            String hotelId = hotelIncrDataDto.getHotelID();
            HotelRoomDetailResModel hotelRoomDetailResModel = new HotelRoomDetailResModel();
            Query query = new Query(Criteria.where("hotelId").is(hotelId));
            List<HotelRoomMongoModel> hotelRoomMongoModel = mongoTemplate.find(query, HotelRoomMongoModel.class);
            try
            {
                 if ( hotelRoomMongoModel == null || hotelRoomMongoModel.size() <= 0 )
                {
                    //mongodb中酒店不存在
                    //判断本次是否已就该酒店执行过处理
                    if ( signMap.containsKey(hotelId) )
                    {
                        continue;
                    }else
                    {
                        signMap.put(hotelId, "");
                    }
                    //通知更新房型
                    iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"1", "0");
                }else
                {
                    //mongodb中酒店存在
                    hotelRoomDetailResModel = JSONObject.parseObject(hotelRoomMongoModel.get(0).getHotelRoomDetail(),
                            HotelRoomDetailResModel.class);
                    if ( hotelRoomDetailResModel.getResponseStatus().getAck() == null ||
                            !"Success".equals(hotelRoomDetailResModel.getResponseStatus().getAck()) ||
                            (hotelRoomDetailResModel.getRoomStaticInfos() == null || hotelRoomDetailResModel.getRoomStaticInfos().size() <=0 ) )
                    {
                        //房型数据不存在
                        //判断本次是否已就该酒店执行过处理
                        if ( signMap.containsKey(hotelId) )
                        {
                            continue;
                        }else
                        {
                            signMap.put(hotelId, "");
                        }
                        //通知更新房型
                        iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"0", "0");
                    }else
                    {
                        //房型数据存在，判断对应房型是否存在
                        for ( HotelRoomDetailResModel.RoomStaticInfosBean roomStaticInfosBean :
                                hotelRoomDetailResModel.getRoomStaticInfos() )
                        {
                            if ( roomStaticInfosBean.getRoomInfos() != null && roomStaticInfosBean.getRoomInfos().size() > 0 )
                            {
                                for (HotelRoomDetailResModel.RoomStaticInfosBean.RoomInfosBean roomInfosBean :
                                        roomStaticInfosBean.getRoomInfos() )
                                {
                                    if ( hotelIncrDataDto.getRoomID().equals(roomInfosBean.getRoomID()) )
                                    {
                                        sign = true;
                                        break;
                                    }
                                    if ( sign )
                                    {
                                        break;
                                    }
                                }
                            }
                        }
                        if ( !sign )
                        {
                            //判断本次是否已就该酒店执行过处理
                            if ( signMap.containsKey(hotelId) )
                            {
                                continue;
                            }else
                            {
                                signMap.put(hotelId, "");
                            }
                            iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"0", "0");
                        }
                    }
                }
            }catch (Exception e)
            {
                logger.info("本次数据跳过!" + e.getMessage());
            }
//            System.out.println("静态信息-对应售卖房型变动同步--国内" + (sign? "数据存在": "调用更新"));
        }
        redisTemplate.delete("hotelincrdata_IncrDataRoomSign_0");

        return true;
    }

    /**
     * @Description: 静态信息-对应酒店变动同步--国际
     * @Author: 王俊文
     * @Date: 2019/9/17 下午4:52
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/9/17 下午4:52          1.0          对应酒店变动同步
     */
    @Override
    public boolean IncrDataHotelWarehousingInternational()
    {
//        logger.info("静态信息-对应酒店变动同步--国际");
        Map<String, String> signMap = new LinkedHashMap<>(16);
        //酒店
        while( redisTemplate.opsForList().size("1_hotelincrdata_IncrDataHotel") > 0 )
        {
            HotelIncrDataDto hotelIncrDataDto =
                    JSONObject.parseObject(redisTemplate.opsForList().rightPop("1_hotelincrdata_IncrDataHotel").toString(),
                            HotelIncrDataDto.class);
            String hotelId = hotelIncrDataDto.getHotelID();
            Query query = new Query(Criteria.where("hotelId").is(hotelIncrDataDto.getHotelID()));
            List<HotelMongoModel> hotelMongoModelList = mongoTemplate.find(query, HotelMongoModel.class);

            boolean sign = true;
            if ( hotelMongoModelList != null && hotelMongoModelList.size() > 0 )
            {
                for ( HotelMongoModel hotelMongoModel : hotelMongoModelList )
                {
                    HotelStaticResPonse hotelStaticResPonse =
                            JSONObject.parseObject(hotelMongoModel.getHotelDetail(), HotelStaticResPonse.class);
                    if ( hotelStaticResPonse.getResponseStatus().getAck() == null ||
                            !"Success".equals(hotelStaticResPonse.getResponseStatus().getAck()) ||
                            (hotelStaticResPonse.getHotelStaticInfo() == null || hotelStaticResPonse.getHotelStaticInfo().getHotelID()==0) )
                    {
                        //判断本次是否已就该酒店执行过处理
                        if ( signMap.containsKey(hotelId) )
                        {
                            continue;
                        }else
                        {
                            signMap.put(hotelId, "");
                        }
                        sign = false;
                        iHotelStaticInformationService.queryHotelDetailNotInMongo(hotelIncrDataDto.getHotelID(),
                                "0", "1");
                    }
                }
            }else
            {
                //判断本次是否已就该酒店执行过处理
                if ( signMap.containsKey(hotelId) )
                {
                    continue;
                }else
                {
                    signMap.put(hotelId, "");
                }
                sign = false;
                iHotelStaticInformationService.queryHotelDetailNotInMongo(hotelIncrDataDto.getHotelID(),"1", "1");
            }

//            System.out.println("静态信息-对应酒店变动同步--国际" + (sign? "数据存在": "调用更新") );
        }
        redisTemplate.delete("hotelincrdata_IncrDataHotelSign_1");
        return true;
    }

    /**
     * @Description: 静态信息-对应物理房型变动同步--国际
     * @Author: 王俊文
     * @Date: 2019/9/17 下午4:51
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/9/17 下午4:51          1.0          对应物理房型变动同步
     */
    @Override
    public boolean IncrDataRoomTypeWarehousingInternational()
    {
//        logger.info("静态信息-对应物理房型变动同步--国际");
        Map<String, String> signMap = new LinkedHashMap<>(16);
        //物理房型
        while( redisTemplate.opsForList().size("1_hotelincrdata_IncrDataRoomType") > 0 )
        {
            boolean sign = false;
            HotelIncrDataDto hotelIncrDataDto =
                    JSONObject.parseObject(redisTemplate.opsForList().rightPop("1_hotelincrdata_IncrDataRoomType").toString(),
                            HotelIncrDataDto.class);
            String hotelId = hotelIncrDataDto.getHotelID();
            HotelRoomDetailResModel hotelRoomDetailResModel = new HotelRoomDetailResModel();
            Query query = new Query(Criteria.where("hotelId").is(hotelId));
            List<HotelRoomMongoModel> hotelRoomMongoModel = mongoTemplate.find(query, HotelRoomMongoModel.class);
            try
            {
                 if ( hotelRoomMongoModel == null || hotelRoomMongoModel.size() <= 0 )
                {
                    //mongodb中酒店不存在
                    //判断本次是否已就该酒店执行过处理
                    if ( signMap.containsKey(hotelId) )
                    {
                        continue;
                    }else
                    {
                        signMap.put(hotelId, "");
                    }
                    //通知更新房型
                    iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"1", "1");
                }else
                {
                    //mongodb中酒店存在
                    hotelRoomDetailResModel = JSONObject.parseObject(hotelRoomMongoModel.get(0).getHotelRoomDetail(),
                            HotelRoomDetailResModel.class);
                    if ( hotelRoomDetailResModel.getResponseStatus().getAck() == null ||
                            !"Success".equals(hotelRoomDetailResModel.getResponseStatus().getAck()) ||
                            (hotelRoomDetailResModel.getRoomStaticInfos() == null || hotelRoomDetailResModel.getRoomStaticInfos().size() <=0 ) )
                    {
                        //房型数据不存在
                        //判断本次是否已就该酒店执行过处理
                        if ( signMap.containsKey(hotelId) )
                        {
                            continue;
                        }else
                        {
                            signMap.put(hotelId, "");
                        }
                        //通知更新房型
                        iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"0", "1");
                    }else
                    {
                        //房型数据存在，判断对应房型是否存在
                        for ( HotelRoomDetailResModel.RoomStaticInfosBean roomStaticInfosBean :
                                hotelRoomDetailResModel.getRoomStaticInfos() )
                        {
                            if ( hotelIncrDataDto.getRoomTypeID().equals(roomStaticInfosBean.getRoomTypeInfo().getRoomTypeID()) )
                            {
                                sign = true;
                                break;
                            }
                        }
                        if ( !sign )
                        {
                            //判断本次是否已就该酒店执行过处理
                            if ( signMap.containsKey(hotelId) )
                            {
                                continue;
                            }else
                            {
                                signMap.put(hotelId, "");
                            }
                            iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"0", "1");
                        }
                    }
                }
            }catch (Exception e)
            {
                logger.info("本次数据跳过!" + e.getMessage());
            }
//            System.out.println("静态信息-对应物理房型变动同步--国际" + (sign? "数据存在": "调用更新"));
        }
        redisTemplate.delete("hotelincrdata_IncrDataRoomTypeSign_1");
        return true;
    }

    /**
     * @Description: 静态信息-对应售卖房型变动同步--国际
     * @Author: 王俊文
     * @Date: 2019/9/17 下午4:51
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/9/17 下午4:51          1.0          对应售卖房型变动同步
     */
    @Override
    public boolean IncrDataRoomWarehousingInternational()
    {
//        logger.info("静态信息-对应售卖房型变动同步--国际");
        Map<String, String> signMap = new LinkedHashMap<>(16);
        //售卖房型
        while( redisTemplate.opsForList().size("1_hotelincrdata_IncrDataRoom") > 0 )
        {
            boolean sign = false;
            HotelIncrDataDto hotelIncrDataDto =
                    JSONObject.parseObject(redisTemplate.opsForList().rightPop("1_hotelincrdata_IncrDataRoom").toString(),
                            HotelIncrDataDto.class);
            String hotelId = hotelIncrDataDto.getHotelID();
            HotelRoomDetailResModel hotelRoomDetailResModel = new HotelRoomDetailResModel();
            Query query = new Query(Criteria.where("hotelId").is(hotelId));
            List<HotelRoomMongoModel> hotelRoomMongoModel = mongoTemplate.find(query, HotelRoomMongoModel.class);
            try
            {
                 if ( hotelRoomMongoModel == null || hotelRoomMongoModel.size() <= 0 )
                {
                    //mongodb中酒店不存在
                    //判断本次是否已就该酒店执行过处理
                    if ( signMap.containsKey(hotelId) )
                    {
                        continue;
                    }else
                    {
                        signMap.put(hotelId, "");
                    }
                    //通知更新房型
                    iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"1", "1");
                }else
                {
                    //mongodb中酒店存在
                    hotelRoomDetailResModel = JSONObject.parseObject(hotelRoomMongoModel.get(0).getHotelRoomDetail(),
                            HotelRoomDetailResModel.class);
                    if ( hotelRoomDetailResModel.getResponseStatus().getAck() == null ||
                            !"Success".equals(hotelRoomDetailResModel.getResponseStatus().getAck()) ||
                            (hotelRoomDetailResModel.getRoomStaticInfos() == null || hotelRoomDetailResModel.getRoomStaticInfos().size() <=0 ) )
                    {
                        //房型数据不存在
                        //判断本次是否已就该酒店执行过处理
                        if ( signMap.containsKey(hotelId) )
                        {
                            continue;
                        }else
                        {
                            signMap.put(hotelId, "");
                        }
                        //通知更新房型
                        iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"0", "1");
                    }else
                    {
                        //房型数据存在，判断对应房型是否存在
                        for ( HotelRoomDetailResModel.RoomStaticInfosBean roomStaticInfosBean :
                                hotelRoomDetailResModel.getRoomStaticInfos() )
                        {
                            if ( roomStaticInfosBean.getRoomInfos() != null && roomStaticInfosBean.getRoomInfos().size() > 0 )
                            {
                                for (HotelRoomDetailResModel.RoomStaticInfosBean.RoomInfosBean roomInfosBean :
                                        roomStaticInfosBean.getRoomInfos() )
                                {
                                    if ( hotelIncrDataDto.getRoomID().equals(roomInfosBean.getRoomID()) )
                                    {
                                        sign = true;
                                        break;
                                    }
                                    if ( sign )
                                    {
                                        break;
                                    }
                                }
                            }
                        }
                        if ( !sign )
                        {
                            //判断本次是否已就该酒店执行过处理
                            if ( signMap.containsKey(hotelId) )
                            {
                                continue;
                            }else
                            {
                                signMap.put(hotelId, "");
                            }
                            iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelId,"0", "1");
                        }
                    }
                }
            }catch (Exception e)
            {
                logger.info("本次数据跳过!" + e.getMessage());
            }
//            System.out.println("静态信息-对应售卖房型变动同步--国际" + (sign? "数据存在": "调用更新"));
        }
        redisTemplate.delete("hotelincrdata_IncrDataRoomSign_1");
        return true;
    }

    /**
     * @Description: 统一远程请求--国内
     * @Author: 王俊文
     * @Date: 19-7-16 上午9:36
     * @Param: [param, master, test]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-16 上午9:36          1.0         统一远程请求--国内
     */
    private JSONObject getResponse(JSONObject param, String icode)
    {
        JSONObject body = new JSONObject(16);
        while (true)
        {
            try
            {
                RestTemplate restTemplate = new RestTemplate();
                String token = stringRedisTemplate.opsForValue().get("token");
                BaseApiReq baseApiReq = Enviroment.getBaseApiReq("0");
        //        String url = Enviroment.API_COMMON_URL.getValue();
        //        url = url.replace("{app_path}", baseApiReq.getUrl())
        //                .replace("{aid}", baseApiReq.getAID())
        //                .replace("{sid}", baseApiReq.getSID())
        //                .replace("{key}",  baseApiReq.getKEY())
        //                .replace("{format}", baseApiReq.getFormat())
        //                .replace("{GUID}", baseApiReq.getUUID())
        //                .replace("{Access_Token}", token)
        //                .replace("{ICODE}", icode);
        //            //测试时使用  调用弗恩
                String url = "http://{app_path}/OpenService/FenHotelService" +
                                ".ashx?AID={aid}&SID={sid}&ICODE={ICODE}&UUID={GUID}&Token={Access_Token}&mode=1&format={format}";
                url = url.replace("{app_path}", "hotel.tianxiafen.com:82")
                        .replace("{aid}", "1")
                        .replace("{sid}", "101")
                        .replace("{key}", Enviroment.KEY.getValue())
                        .replace("{format}", Enviroment.FORMAT.getValue())
                        .replace("{GUID}", "A63FA038-32B2-46C7-A363-42E651378752")
                        .replace("{Access_Token}", redisTemplate.opsForValue().get("token").toString())
                        .replace("{ICODE}", icode);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
                body = restTemplate.exchange(url, HttpMethod.POST, formEntity, JSONObject.class).getBody();
                 break;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                continue;
            }
        }
        return body;
    }

    /**
     * @Description: 统一远程请求--国际
     * @Author: 王俊文
     * @Date: 2019/9/12 上午10:47
     * @Param: [param, icode]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/9/12 上午10:47          1.0          统一远程请求--国际
     */
    private JSONObject getResponseOverseas(JSONObject param, String icode)
    {
        JSONObject body = new JSONObject(16);
        while (true)
        {
            try
            {
                RestTemplate restTemplate = new RestTemplate();
                String token = stringRedisTemplate.opsForValue().get("overseasToken");

                BaseApiReq baseApiReq = Enviroment.getBaseApiReq("1");
        //        String url = Enviroment.API_COMMON_URL.getValue();
        //        url = url.replace("{app_path}", baseApiReq.getUrl())
        //                .replace("{aid}", baseApiReq.getAID())
        //                .replace("{sid}", baseApiReq.getSID())
        //                .replace("{key}",  baseApiReq.getKEY())
        //                .replace("{format}", baseApiReq.getFormat())
        //                .replace("{GUID}", baseApiReq.getUUID())
        //                .replace("{Access_Token}", token)
        //                .replace("{ICODE}", icode);

                //测试时使用  调用弗恩
                String url = "http://{app_path}/OpenService/FenHotelService" +
                    ".ashx?AID={aid}&SID={sid}&ICODE={ICODE}&UUID={GUID}&Token={Access_Token}&mode=1&format={format}";
                url = url.replace("{app_path}", "hotel.tianxiafen.com:83")
                        .replace("{aid}", "1")
                        .replace("{sid}", "101")
                        .replace("{key}", Enviroment.KEY.getValue())
                        .replace("{format}", Enviroment.FORMAT.getValue())
                        .replace("{GUID}", "A63FA038-32B2-46C7-A363-42E651378752")
                        .replace("{Access_Token}", token)
                        .replace("{ICODE}", icode);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
                body = restTemplate.exchange(url, HttpMethod.POST, formEntity, JSONObject.class).getBody();
                break;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                continue;
            }
        }
        return body;
    }
}
