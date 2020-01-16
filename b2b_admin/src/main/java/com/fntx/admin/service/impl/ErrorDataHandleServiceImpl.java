package com.fntx.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fntx.admin.service.IBHotelBasepriceService;
import com.fntx.admin.service.IErrorDataHandleService;
import com.fntx.admin.service.IHotelBasepriceService;
import com.fntx.admin.service.IHotelIopriceService;
import com.fntx.admin.utils.HotelBasepriceUtil;
import com.fntx.admin.utils.HotelIopriceUtil;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.domain.BHotelBaseprice;
import com.fntx.common.domain.BHotelRoomPriceInfos;
import com.fntx.common.domain.BaseApiReq;
import com.fntx.common.domain.dto.HotelDataLists;
import com.fntx.common.domain.dto.RoomPriceItems;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: ErrorDataHandleServiceImpl
 * @Author: 王俊文
 * @Date: 19-8-9 上午11:15
 * @Description: 异常数据处理--停用
 */
@Service
public class ErrorDataHandleServiceImpl implements IErrorDataHandleService
{
    private static Logger logger = LoggerFactory.getLogger(ErrorDataHandleServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IBHotelBasepriceService ibHotelBasepriceService ;
    @Autowired
    private IHotelBasepriceService iHotelBasepriceService;
    @Autowired
    private IHotelIopriceService iHotelIopriceService;
    @Autowired
    private BHotelRoomPriceInfosServiceImpl bHotelRoomPriceInfosService;

     /**
     * @Description: 消息队列--从房型数据中获取筛选字段的值--直连报价
     * @Author: 王俊文
     * @Date: 19-8-13 上午9:53
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-13 上午9:53          1.0          消息队列--从房型数据中获取筛选字段的值--直连报价
     */
    @Override
    public boolean hotelIopriceScreeningReceiver()
    {
        List<BHotelRoomPriceInfos> bHotelRoomPriceInfosList = new ArrayList<>();
        Integer index = 0;
        while (true)
        {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("ADULT", 1);

            boolean sign = false;
            for ( int i = 0; i<50; i++)
            {
                PageHelper.startPage(++index, 500);
                bHotelRoomPriceInfosList = bHotelRoomPriceInfosService.list(queryWrapper);
                HotelIopriceUtil hotelIopriceUtil1 = new HotelIopriceUtil(iHotelIopriceService,
                        bHotelRoomPriceInfosList);
                hotelIopriceUtil1.start();
                if ( bHotelRoomPriceInfosList.size() < 500 )
                {
                    sign = true;
                    break;
                }
            }

            if ( sign )
            {
                break;
            }
        }
        return true;
    }

    /**
     * @Description: 消息队列--从房型数据中获取筛选字段的值--入离起价
     * @Author: 王俊文
     * @Date: 19-8-12 下午4:46
     * @Param: [bHotelBasepriceList]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-12 下午4:46          1.0      消息队列--从房型数据中获取筛选字段的值--入离起价
     */
    @Override
    public boolean hotelBasepriceScreeningReceiver()
    {
        List<BHotelBaseprice> bHotelBasepriceList = new ArrayList<>();
        Integer index = 0;
        while (true)
        {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.isNull("PAYMENT_TYPE");
            queryWrapper.or();
            queryWrapper.isNull("FILTER_ROOM_BY_PERSON");
            queryWrapper.or();
            queryWrapper.isNull("CHILD_MAXAGE");
            queryWrapper.or();
            queryWrapper.isNull("CHILD_MINAGE");
            queryWrapper.or();
            queryWrapper.isNull("CHILDREN_NUMBER_LIST");

             boolean sign = false;
            for ( int i = 0; i<50; i++)
            {
                PageHelper.startPage(++index, 500);
                bHotelBasepriceList = ibHotelBasepriceService.list(queryWrapper);
                HotelBasepriceUtil hotelBasepriceUtil1 = new HotelBasepriceUtil(iHotelBasepriceService,
                        bHotelBasepriceList);
                hotelBasepriceUtil1.start();
                if ( bHotelBasepriceList.size() < 500 )
                {
                    break;
                }
            }

            if ( sign )
            {
                break;
            }
        }
        return true;
    }

    /**
     * @Description: 直连入离报价异常错误数据处理
     * @Author: 王俊文
     * @Date: 19-8-8 上午10:24
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-8 上午10:24          1.0         直连入离报价异常错误数据处理
     */
    @Override
    public boolean hotelIopriceErrorHandle()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String icode = "";
        if ( Enviroment.IS_RELEASE_ENV )
        {
            icode = Enviroment.HOTEL_IOPRICE.getValue();
        }else
        {
            icode = Enviroment.TEST_HOTEL_IOPRICE.getValue();
        }
        //弗恩测试  正式情况下注释掉
//        icode = "hotel.ioprice";

        //封装请求参数并循环获取报价信息
        while ( redisTemplate.opsForList().size("hoteliopricErrorList") > 0 )
        {
            Object hotelIopriceErrorHandleErrorSign = redisTemplate.opsForValue().get(
                    "hotelIopriceErrorHandleErrorSign");
            if ( hotelIopriceErrorHandleErrorSign != null && "1".equals(hotelIopriceErrorHandleErrorSign) )
            {
                redisTemplate.delete("hotelIopriceErrorHandleErrorSign");
                break;
            }
            Object ss = redisTemplate.opsForList().leftPop("hoteliopricErrorList");
            System.out.println("直连报价redis异常数据内容" + ss.toString());
            //redis获取异常数据列表
            JSONObject jsonObject =
                    JSONObject.parseObject(ss.toString());
            String Start  = "";
            String End  = "";
            String HotelID = "";
            logger.info("本次异常内容:" + jsonObject.toJSONString());
            JSONObject SearchCandidate = jsonObject.getJSONObject("SearchCandidate");
            JSONObject DateRange = SearchCandidate.getJSONObject("DateRange");
            if ( Start.equals(DateRange.getString("Start")) && End.equals(DateRange.getString("End")) && HotelID.equals(SearchCandidate.getString("HotelID")) )
            {
                HotelID  = SearchCandidate.getString("HotelID");
                //获取开始时间
                Start = DateRange.getString("Start");
                //获取结束时间
                End = DateRange.getString("End");
                continue;
            }
            HotelID  = SearchCandidate.getString("HotelID");
            //获取开始时间
            Start = DateRange.getString("Start");
            //获取结束时间
            End = DateRange.getString("End");
            while (true)
            {
                if ( Start.compareTo(End) >= 0 )
                {
                    break;
                }
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(sdf.parse(Start));
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    String followingDay = sdf.format(calendar.getTime());
                    DateRange.put("Start", Start);
                    DateRange.put("End", followingDay);
                    SearchCandidate.put("DateRange", DateRange);
                    jsonObject.put("SearchCandidate", SearchCandidate);
                    logger.info("本次请求内容:" + jsonObject.toJSONString());
                    //判断异常数据其起始时间是否已超过当前时间
                    if ( sdf.parse(Start).compareTo(new Date()) == -1)
                    {
                        Start = followingDay;
                        continue;
                    }
                    JSONObject returnJSONObject = new JSONObject(16);
                    Integer sign = 0;
                    while (true)
                    {
                        if ( sign.compareTo(10) >= 0 )
                        {
                            break;
                        }
                        sign++;
                        try
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            returnJSONObject = getResponse(jsonObject, icode);
                            logger.info("本次返回内容:" + returnJSONObject.toJSONString());
                            if ( returnJSONObject.get("ResponseStatus") == null && "".equals(returnJSONObject.get(
                                        "ResponseStatus")) )
                            {
                                logger.info("本次为出现异常的第"+ sign +"次");
                                if ( ("100006").equals(returnJSONObject.get("ErrCode")) )
                                {
                                    continue;
                                }
                            }
                            break;
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                            logger.info("本次出现异常:" + e.getMessage());
                            continue;
                        }
                    }

                    if ( returnJSONObject.get("ResponseStatus") != null && !"".equals(returnJSONObject.get("ResponseStatus")) )
                    {
                        JSONObject ResponseStatus = returnJSONObject.getJSONObject("ResponseStatus");
                        if ( ResponseStatus.getString("Ack") != null && "Success".equals(ResponseStatus.getString("Ack")) )
                        {
                            //解析数据体
                            List<RoomPriceItems> roomPriceItemsList= returnJSONObject.getObject("RoomPriceItems",
                                    ArrayList.class);
                            if ( roomPriceItemsList == null || roomPriceItemsList.size() <= 0 )
                            {
//                                    errorList.add(jsonObject);
                                redisTemplate.expire("hoteliopricErrorSecondaryList",60*60*24*30,TimeUnit.SECONDS);
                                redisTemplate.opsForList().leftPush("hoteliopricErrorSecondaryList",jsonObject.toJSONString());
                                logger.info("本次异常,已记录到二次异常,等待处理");
                                Start = followingDay;
                                continue;
                            }
                            redisTemplate.opsForValue().set(HotelID + "_hotelioprice_"+Start+"_"+followingDay,
                                    returnJSONObject.getJSONArray("RoomPriceItems").toJSONString(), 60*60*24, TimeUnit.SECONDS);
                            redisTemplate.convertAndSend("IStaticInformationChange",
                                    HotelID + "_hotelioprice_"+Start+"_"+followingDay);
                            logger.info(HotelID+"酒店异常数据获取成功");
                            Start = followingDay;
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

     /**
     * @Description: 直连入离报价二次异常错误数据处理
     * @Author: 王俊文
     * @Date: 19-8-9 上午10:45
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-9 上午10:45          1.0          直连入离报价二次异常错误数据处理
     */
    @Override
    public boolean hoteliopricErrorSecondaryHandle()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String icode = "";
        if ( Enviroment.IS_RELEASE_ENV )
        {
            icode = Enviroment.HOTEL_IOPRICE.getValue();
        }else
        {
            icode = Enviroment.TEST_HOTEL_IOPRICE.getValue();
        }
        //弗恩测试  正式情况下注释掉
//        icode = "hotel.ioprice";

        boolean signPopList = false;
        while( redisTemplate.opsForList().size("hoteliopricErrorSecondaryList") > 0 )
        {
            Object hoteliopricErrorSecondaryHandleSign = redisTemplate.opsForValue().get(
                    "hoteliopricErrorSecondaryHandleSign");
            if ( hoteliopricErrorSecondaryHandleSign != null && "1".equals(hoteliopricErrorSecondaryHandleSign) )
            {
                redisTemplate.delete("hoteliopricErrorSecondaryHandleSign");
                break;
            }
            List<JSONObject> jsonObjectArrayList = new ArrayList<>();
            for ( int i = 0; i < 1000; i++)
            {
                Object object = redisTemplate.opsForList().leftPop("hoteliopricErrorSecondaryList");
                if ( object != null )
                {
                    String js = String.valueOf(object);
                    jsonObjectArrayList.add(JSONObject.parseObject(js));
                }else
                {
                    signPopList = true;
                    break;
                }
            }

            try
            {
                for ( JSONObject jsonObject : jsonObjectArrayList )
                {
                    JSONObject SearchCandidate = jsonObject.getJSONObject("SearchCandidate");
                    String HotelID  = SearchCandidate.getString("HotelID");
                    JSONObject DateRange = SearchCandidate.getJSONObject("DateRange");
                    //获取开始时间
                    String Start = DateRange.getString("Start");
                    //获取结束时间
                    String End = DateRange.getString("End");

                    //判断异常数据其起始时间是否已小于当前时间
                    try {
                        if ( sdf.parse(Start).compareTo(new Date()) == -1)
                        {
                            continue;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    JSONObject returnJSONObject = new JSONObject(16);
                    Integer sign = 0;
                    while (true)
                    {
                        if ( sign.compareTo(10) >= 0 )
                        {
                            break;
                        }
                        sign++;
                        try
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            returnJSONObject = getResponse(jsonObject, icode);
                            if ( returnJSONObject.get("ResponseStatus") == null && "".equals(returnJSONObject.get(
                                        "ResponseStatus")) )
                            {
                                if ( ("100006").equals(returnJSONObject.get("ErrCode")) )
                                {
                                    continue;
                                }
                            }
                            break;
                        }catch (Exception e)
                        {
                            continue;
                        }
                    }
                    if ( returnJSONObject.get("ResponseStatus") != null && !"".equals(returnJSONObject.get("ResponseStatus")) )
                    {
                        JSONObject ResponseStatus = returnJSONObject.getJSONObject("ResponseStatus");
                        if ( ResponseStatus.getString("Ack") != null && "Success".equals(ResponseStatus.getString("Ack")) )
                        {
                            //解析数据体
                            List<RoomPriceItems> roomPriceItemsList= returnJSONObject.getObject("RoomPriceItems",
                                    ArrayList.class);
                            if ( roomPriceItemsList == null || roomPriceItemsList.size() <= 0 )
                            {
//                                redisTemplate.expire("hoteliopricErrorSecondaryList",60*60*24*30,TimeUnit.SECONDS);
//                                redisTemplate.opsForList().leftPush("hoteliopricErrorSecondaryList",jsonObject.toJSONString());
                                continue;
                            }
                            redisTemplate.opsForValue().set(HotelID + "_hotelioprice_"+Start+"_"+End,
                                    returnJSONObject.getJSONArray("RoomPriceItems").toJSONString(), 60*60*24, TimeUnit.SECONDS);
                            redisTemplate.convertAndSend("IStaticInformationChange",
                                    HotelID + "_hotelioprice_"+Start+"_"+End);
                            logger.info(HotelID+"酒店二次异常数据获取成功");
                        }
                    }
                }
            }catch (Exception e)
            {
                logger.info("出现异常,执行数据备份");
                redisTemplate.opsForList().leftPushAll("hoteliopricErrorSecondaryList", jsonObjectArrayList);
            }

            if ( signPopList )
            {
                break;
            }
        }
        return true;
    }

    /**
     * @Description: 城市入离起价异常数据处理
     * @Author: 王俊文
     * @Date: 19-8-9 下午1:40
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-9 下午1:40          1.0          城市入离起价异常数据处理
     */
    @Override
    public boolean hotelBasepriceErrorDataList()
    {
        while ( redisTemplate.opsForList().size("hotelBasepriceErrorDataList") > 0 )
        {
            Object hotelBasepriceErrorDataListSign = redisTemplate.opsForValue().get(
                    "hotelBasepriceErrorDataListSign");
            if ( hotelBasepriceErrorDataListSign != null && "1".equals(hotelBasepriceErrorDataListSign) )
            {
                redisTemplate.delete("hotelBasepriceErrorDataListSign");
                break;
            }
            //处理请求时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String CheckInDate = sdf.format(new Date());
            Calendar systemDate = Calendar.getInstance();
            try {
                systemDate.setTime(sdf.parse(CheckInDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            systemDate.add(Calendar.DAY_OF_MONTH, 1);
            CheckInDate = sdf.format(systemDate.getTime());
            systemDate.add(Calendar.DAY_OF_MONTH, 1);
            String CheckOutDate = sdf.format(systemDate.getTime());

            JSONObject jsonObject = JSON.parseObject(redisTemplate.opsForList().leftPop("hotelBasepriceErrorDataList").toString());
            System.out.println("城市起价redis异常数据内容" + jsonObject.toJSONString());
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //解析城市,国家信息
            JSONObject SearchTypeEntity = jsonObject.getJSONObject("SearchTypeEntity");
            String SearchType = SearchTypeEntity.getString("SearchType");
            Integer PageIndex = SearchTypeEntity.getInteger("PageIndex");
            //处理请求时间
            JSONObject PublicSearchParameter = jsonObject.getJSONObject("PublicSearchParameter");
            String City = PublicSearchParameter.getString("City");
            //开始时间
            PublicSearchParameter.put("CheckInDate", CheckInDate);
            //结束时间
            PublicSearchParameter.put("CheckOutDate", CheckOutDate);
            while (true)
            {
                SearchTypeEntity.put("PageIndex", PageIndex);

                String icode = "";
                if ( Enviroment.IS_RELEASE_ENV )
                {
                    icode = Enviroment.HOTEL_BASEPRICE.getValue();
                }else
                {
                    icode = Enviroment.TEST_HOTEL_BASEPRICE.getValue();
                }
                //弗恩测试  正式情况下注释掉
//                icode = "hotel.baseprice";
                JSONObject returnJSONObject = new JSONObject(16);
                Integer sign = 0;
                while (true)
                {
                    if ( sign.compareTo(10) >= 0 )
                    {
                        break;
                    }
                    sign++;
                    try
                    {
                        returnJSONObject = getResponse(jsonObject, icode);
                        if ( returnJSONObject.get("ResponseStatus") == null && "".equals(returnJSONObject.get(
                                "ResponseStatus")) )
                        {
                            if ( ("100006").equals(returnJSONObject.get("ErrCode")) )
                            {
                                continue;
                            }
                        }
                        break;
                    }catch (Exception e)
                    {
                        continue;
                    }
                }
                if ( returnJSONObject.get("ResponseStatus") != null && !"".equals(returnJSONObject.get("ResponseStatus")) )
                {
                    JSONObject ResponseStatus = returnJSONObject.getJSONObject("ResponseStatus");
                    if ( ResponseStatus.getString("Ack") != null && "Success".equals(ResponseStatus.getString("Ack")) )
                    {
                        //解析数据体
                        List<HotelDataLists> hotelIncrDataDtoList= returnJSONObject.getObject("HotelDataLists",
                                ArrayList.class);
                        if ( hotelIncrDataDtoList == null || hotelIncrDataDtoList.size() <= 0 )
                        {
                            break;
                        }
                        redisTemplate.opsForValue().set("hotelbaseprice_"+SearchType+"_"+PublicSearchParameter.getString("CheckInDate")+PageIndex+"_"+City,
                                returnJSONObject.toJSONString(), 60*60*24, TimeUnit.SECONDS);
                        redisTemplate.convertAndSend("IStaticInformationChange",
                                "hotelbaseprice_"+SearchType+"_"+PublicSearchParameter.getString("CheckInDate")+PageIndex+"_"+City);
                        //页码自增
                        PageIndex++;
                        if ( hotelIncrDataDtoList.size() < 100 )
                        {
                            break;
                        }
                    }
                }
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
