package com.fntx.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fntx.admin.service.*;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.dao.*;
import com.fntx.common.domain.*;
import com.fntx.common.domain.dto.HotelRoomDetailResModel;
import com.fntx.common.domain.dto.HotelRoomMongoModel;
import com.fntx.common.domain.dto.RoomPriceItems;
import com.fntx.common.domain.dto.RoomPriceItemsDto;
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
import com.fntx.admin.service.IHotelStaticInformationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: IStaticInformationChangeServiceImpl
 * @Author: 王俊文
 * @Date: 19-7-15 上午10:08
 * @Description: 酒店直连报价--停用
 */
@Service
public class HotelIopriceServiceImpl implements IHotelIopriceService
{
    private static Logger logger = LoggerFactory.getLogger(HotelIopriceServiceImpl.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
    private static SimpleDateFormat sdfmd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM");
    private static SimpleDateFormat sdfd = new SimpleDateFormat("dd");

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BHotelCityMapper bHotelCityMapper;
    @Autowired
    private IBHotelRoomPriceInfosService ibHotelRoomPriceInfosService;
    @Autowired
    private IBHotelPriceInfosService ibHotelPriceInfosService;
    @Autowired
    private IBHotelDailyPricesService ibHotelDailyPricesService;
    @Autowired
    private BHotelRoomPriceInfosMapper bHotelRoomPriceInfosMapper;
    @Autowired
    private BHotelListMapper bHotelListMapper;
    @Autowired
    private BHotelDailyPricesMapper bHotelDailyPricesMapper;
    @Autowired
    private IHotelStaticInformationService iHotelStaticInformationService;
    @Autowired
    private BSaleRoomMapper bSaleRoomMapper;

    /**
     * @Description: 从房型数据中获取筛选字段的值--停用
     * @Author: 王俊文
     * @Date: 19-8-13 上午9:51
     * @Param: [bHotelRoomPriceInfosList]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-13 上午9:51          1.0          从房型数据中获取筛选字段的值--停用
     */
    @Override
    public boolean hotelIopriceScreening(List<BHotelRoomPriceInfos> bHotelRoomPriceInfosList)
    {
//        for ( BHotelRoomPriceInfos bHotelRoomPriceInfos : bHotelRoomPriceInfosList )
//        {
//            //查mongodb start 暂时启用
//            List<HotelRoomDetailResModel.RoomStaticInfosBean> roomStaticInfosBeanList =
//                    hotelIopriceStorageMongodbRoomTypeList(bHotelRoomPriceInfos.getHotelId().toString());
//            //查mongodb end 暂时启用
//            //入库物理房型信息
//            //获取房型人数限制  待实现
//            //查mongodb start 暂时启用
//            bHotelRoomPriceInfos = hotelIopriceStorageMongodb(roomStaticInfosBeanList,
//                    bHotelRoomPriceInfos.getRoomId(), bHotelRoomPriceInfos);
//            //查mongodb end 暂时启用
//            //查oracle逻辑 start 暂时停用
////                bHotelRoomPriceInfos = hotelIopriceStorageOracle(roomPriceInfosBean.getRoomID());
//            //查oracle逻辑 end 暂时停用
//
//            //添加或修改
//            try
//            {
//                boolean bHotelRoomPriceInfosSaveOrUpdateSign =
//                    ibHotelRoomPriceInfosService.saveOrUpdate(bHotelRoomPriceInfos);
//                if ( bHotelRoomPriceInfosSaveOrUpdateSign )
//                {
//
//                }
//            }catch (Exception e)
//            {
//                try
//                {
//                    ibHotelRoomPriceInfosService.updateById(bHotelRoomPriceInfos);
//                }catch ( Exception es )
//                {
//                    continue;
//                }
//            }
//        }
        return false;
    }

    /**
     * @Description: 酒店直连报价
     * @Author: 王俊文
     * @Date: 19-7-22 上午10:22
     * @Param: [HotelID--酒店id]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-22 上午10:22          1.0        酒店直连报价
     */
    @Override
    public boolean getHotelIoprice(String HotelID)
    {
        //拼装参数
        //获取当前月及下一个月的所有数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        //获取当前月的最后一天
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date CurrentMonth = ca.getTime();
        //获取下一月的最后一天
        ca.add(Calendar.MONTH, 1);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.add(Calendar.DAY_OF_MONTH, 1);
        Date NextMonth = ca.getTime();

        List<Map<String, String>> dateList = new ArrayList<>();
        Calendar sa = Calendar.getInstance();
        sa.setTime(date);
        while (true)
        {
            Map<String, String> dateMap = new HashMap<>(16);
            dateMap.put("Start", sdf.format(sa.getTime()));
            sa.add(Calendar.DAY_OF_MONTH, 28);
            if ( sa.getTime().compareTo(NextMonth) <= 0 )
            {
                dateMap.put("End", sdf.format(sa.getTime()));
                dateList.add(dateMap);
            }else
            {
                dateMap.put("End", sdf.format(NextMonth));
                dateList.add(dateMap);
                break;
            }
        }

        JSONObject jsonObject = new JSONObject(16);
        JSONObject Settings = new JSONObject(16);
        Settings.put("DisplayCurrency", "CNY");
        Settings.put("IsShowNonBookable", "F");
        Settings.put("PrimaryLangID", "zh-cn");
        jsonObject.put("Settings", Settings);
        JSONObject SearchCandidate = new JSONObject(16);
        SearchCandidate.put("HotelID", HotelID);

        //封装请求参数并循环获取报价信息
        for ( Map<String, String> map : dateList )
        {
            JSONObject DateRange = new JSONObject(16);
            DateRange.put("Start",  map.get("Start"));
            DateRange.put("End",  map.get("End"));
            SearchCandidate.put("DateRange", DateRange);
            jsonObject.put("SearchCandidate", SearchCandidate);
            String icode = "";
            if ( Enviroment.IS_RELEASE_ENV )
            {
                icode = Enviroment.HOTEL_IOPRICE.getValue();
            }else
            {
                icode = Enviroment.TEST_HOTEL_IOPRICE.getValue();
            }
            //弗恩测试  正式情况下注释掉
//            icode = "hotel.ioprice";
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
                        logger.info("连接异常:" + returnJSONObject.toJSONString());
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
                        logger.info(HotelID+"酒店无返回数据");
                        redisTemplate.expire("hoteliopricErrorList",60*60*24*30,TimeUnit.SECONDS);
                        redisTemplate.opsForList().leftPush("hoteliopricErrorList",
                            JSONObject.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect));
                        continue;
                    }
                    redisTemplate.opsForValue().set(HotelID + "_hotelioprice_"+map.get("Start")+"_"+map.get("End"),
                            returnJSONObject.getJSONArray("RoomPriceItems").toJSONString(), 60*60*24, TimeUnit.SECONDS);
                    redisTemplate.convertAndSend("IStaticInformationChange",
                            HotelID + "_hotelioprice_"+map.get("Start")+"_"+map.get("End"));
                    logger.info(HotelID+"酒店直连完成");
                }
            }
        }
        return true;
    }

    /**
     * @Description: 酒店直连报价--入库
     * @Author: 王俊文
     * @Date: 19-7-22 上午10:33
     * @Param: [RoomPriceItems]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-22 上午10:33          1.0
     */
    @Override
    public boolean hotelIopriceStorage(String RoomPriceItems, String HotelID)
    {
        //获取开始时间
        long startTime=System.currentTimeMillis();
        List<RoomPriceItems> roomPriceItemsList = new ArrayList<>();
        //解析数据体
        try {
            roomPriceItemsList = JSONArray.parseArray(RoomPriceItems, RoomPriceItems.class);
        }catch (JSONException je)
        {
            return false;
        }
        if ( roomPriceItemsList == null || roomPriceItemsList.size() <= 0 )
        {
            return false;
        }
        //查mongodb start 暂时启用
//        List<HotelRoomDetailResModel.RoomStaticInfosBean> roomStaticInfosBeanList =  hotelIopriceStorageMongodbRoomTypeList(HotelID);
        //查mongodb end 暂时启用
        for ( RoomPriceItems roomPriceItems : roomPriceItemsList )
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sdfd = new SimpleDateFormat("dd");
            Date date = new Date();
            //获取当前月
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            String CurrentMonth = sdfm.format(ca.getTime());
            //获取下一月
            ca.add(Calendar.MONTH, 1);
            String NextMonth = sdfm.format(ca.getTime());
            List<com.fntx.common.domain.dto.RoomPriceItems.RoomPriceInfosBean> roomPriceInfosBeanList =
                    roomPriceItems.getRoomPriceInfos();
            for (com.fntx.common.domain.dto.RoomPriceItems.RoomPriceInfosBean roomPriceInfosBean :
                    roomPriceInfosBeanList )
            {
                //入库物理房型信息
                BHotelRoomPriceInfos bHotelRoomPriceInfos = new BHotelRoomPriceInfos();
                bHotelRoomPriceInfos.setAdult(1);
                //获取房型人数限制  待实现
                //查mongodb start 暂时启用
//                bHotelRoomPriceInfos = hotelIopriceStorageMongodb(roomStaticInfosBeanList,
//                        roomPriceInfosBean.getRoomID(), bHotelRoomPriceInfos);
                //查mongodb end 暂时启用
                //查oracle逻辑 start 暂时停用
//                bHotelRoomPriceInfos = hotelIopriceStorageOracle(roomPriceInfosBean.getRoomID());
                //查oracle逻辑 end 暂时停用
                //售卖房型id
                bHotelRoomPriceInfos.setRoomId(roomPriceInfosBean.getRoomID());
                //酒店id
                bHotelRoomPriceInfos.setHotelId(Integer.parseInt(HotelID));
                //物理房型id
                bHotelRoomPriceInfos.setRoomTypeId(roomPriceItems.getRoomTypeID());
                //房型名称
                bHotelRoomPriceInfos.setRoomName(roomPriceInfosBean.getRoomName());
                //取消政策 存放CancelPolicyInfos--jsonarray串
                bHotelRoomPriceInfos.setCancelPolicyInfos(JSONObject.toJSONString(roomPriceInfosBean.getCancelPolicyInfos()));
                //最晚预定时间 存放ReserveTimeLimitInfo--jsonobject串
                bHotelRoomPriceInfos.setReserveTimeLimitInfo(JSONObject.toJSONString(roomPriceInfosBean.getReserveTimeLimitInfo()));
                //房间保留时间 存放HoldDeadline--jsonobject串
                bHotelRoomPriceInfos.setHoldDeadline(JSONObject.toJSONString(roomPriceInfosBean.getReserveTimeLimitInfo()));
                //上次同步时间
                bHotelRoomPriceInfos.setLastsynctime(new Date());
                //人数限制 待查询房型信息后填入
                //酒店状态
                bHotelRoomPriceInfos.setHotelStatus(1);
                //售卖房型状态
                bHotelRoomPriceInfos.setRoomStatus(1);

                //添加或修改
                try
                {
                    boolean bHotelRoomPriceInfosSaveOrUpdateSign =
                        ibHotelRoomPriceInfosService.saveOrUpdate(bHotelRoomPriceInfos);
                    if ( bHotelRoomPriceInfosSaveOrUpdateSign )
                    {

                    }
                }catch (Exception e)
                {
                    try
                    {
                        ibHotelRoomPriceInfosService.updateById(bHotelRoomPriceInfos);
                    }catch ( Exception es )
                    {
                        continue;
                    }
                }

                //入库售卖房型
                List<com.fntx.common.domain.dto.RoomPriceItems.RoomPriceInfosBean.PriceInfosBean> priceInfosBeanList
                        = roomPriceInfosBean.getPriceInfos();
                for (com.fntx.common.domain.dto.RoomPriceItems.RoomPriceInfosBean.PriceInfosBean priceInfosBean : priceInfosBeanList)
                {
                    BHotelPriceInfos bHotelPriceInfos = new BHotelPriceInfos();
                    //售卖房型id
                    bHotelPriceInfos.setRoomId(roomPriceInfosBean.getRoomID());
                    //物理房型id
                    bHotelPriceInfos.setRoomTypeId(roomPriceItems.getRoomTypeID());
                    //离店税前后信息 存放Prices--jsonarray串
                    bHotelPriceInfos.setPrices(JSONObject.toJSONString(priceInfosBean.getPrices()));
                    //税项 存放Taxes--jsonarray串
                    bHotelPriceInfos.setTaxes(JSONObject.toJSONString(priceInfosBean.getTaxes()));
                    //费项  存放Fees--jsonarray串
                    bHotelPriceInfos.setFees(JSONObject.toJSONString(priceInfosBean.getFees()));
                    //支付类型  PP-预付，FG-到付
                    bHotelPriceInfos.setPayType(priceInfosBean.getPayType());
                    //该售卖房型的所属分类  详情:501-标准预付房型;501-标准预付房型;502-促销预付房型;16-标准到付房型;14-促销到付房型;
                    bHotelPriceInfos.setRatePlanCategory(priceInfosBean.getRatePlanCategory());
                    //售卖房型是否可预订  1--true-可预订，0--false-不可预订
                    bHotelPriceInfos.setIsCanReserve(priceInfosBean.isIsCanReserve()?1:0);
                    //售卖房型是否需担保 1--true-需担保，0--false-不需担保
                    bHotelPriceInfos.setIsGuarantee(priceInfosBean.isIsGuarantee()?1:0);
                    //售卖房型是否可立即确认(仅代表订单确认速度，分销商仍需通过相关接口同步弗恩订单状态)。1--True-该房型为立即确认房型，0--false-该房型非立即确认房型"
                    bHotelPriceInfos.setIsInstantConfirm(priceInfosBean.isIsInstantConfirm()?1:0);
                    //可定房量 10间以内显示真实房量，大于10间显示 10+
                    bHotelPriceInfos.setRemainingRooms(priceInfosBean.getRemainingRooms());
                    //可定检查流水号 部分国家酒店的售卖房型会返回该参数
                    bHotelPriceInfos.setBookingCode(priceInfosBean.getBookingCode());
                    //价格计划ID 部分国家酒店的售卖房型会返回该参数
                    bHotelPriceInfos.setRatePlanId(priceInfosBean.getRatePlanID());
                    //固定返回F
                    bHotelPriceInfos.setIsPromotion(priceInfosBean.getIsPromotion());
                    //上次同步时间
                    bHotelPriceInfos.setLastsynctime(new Date());
                    try
                    {
                        boolean ibHotelPriceInfosSaveOrUpdateSign = ibHotelPriceInfosService.saveOrUpdate(bHotelPriceInfos);
                        if ( ibHotelPriceInfosSaveOrUpdateSign )
                        {

                        }
                    }catch (Exception e)
                    {
                        try
                        {
                            ibHotelPriceInfosService.updateById(bHotelPriceInfos);
                        }catch (Exception es)
                        {
                            continue;
                        }

                    }

                    //入库每日报价
                    BHotelDailyPrices bHotelDailyPrices = new BHotelDailyPrices();
                    //房型id
                    bHotelDailyPrices.setRoomId(roomPriceInfosBean.getRoomID());
                    //物理房型id
                    bHotelDailyPrices.setRoomTypeId(roomPriceItems.getRoomTypeID());
                    //当前月
                    bHotelDailyPrices.setCurrentMonth(CurrentMonth);
                    //下一月
                    bHotelDailyPrices.setNextMonth(NextMonth);
                    //最后同步时间
                    bHotelDailyPrices.setLastsynctime(new Date());
                    List<com.fntx.common.domain.dto.RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean> dailyPricesBeanList = priceInfosBean.getDailyPrices();
                    for ( com.fntx.common.domain.dto.RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean dailyPricesBean : dailyPricesBeanList )
                    {
                        try {
                            Date effectiveDate = sdfm.parse(dailyPricesBean.getEffectiveDate());
                            Date effectiveDateDay = sdf.parse(dailyPricesBean.getEffectiveDate());
                            if ( effectiveDate.compareTo(sdfm.parse(CurrentMonth)) == 0 )
                            {
                                //当前月
                                switch (sdfd.format(effectiveDateDay))
                                {
                                    case "01":
                                    {
                                        bHotelDailyPrices.setCurrentMonth1(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "02":
                                    {
                                        bHotelDailyPrices.setCurrentMonth2(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "03":
                                    {
                                        bHotelDailyPrices.setCurrentMonth3(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "04":
                                    {
                                        bHotelDailyPrices.setCurrentMonth4(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "05":
                                    {
                                        bHotelDailyPrices.setCurrentMonth5(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "06":
                                    {
                                        bHotelDailyPrices.setCurrentMonth6(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "07":
                                    {
                                        bHotelDailyPrices.setCurrentMonth7(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "08":
                                    {
                                        bHotelDailyPrices.setCurrentMonth8(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "09":
                                    {
                                        bHotelDailyPrices.setCurrentMonth9(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "10":
                                    {
                                        bHotelDailyPrices.setCurrentMonth10(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }case "11":
                                    {
                                        bHotelDailyPrices.setCurrentMonth11(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }case "12":
                                    {
                                        bHotelDailyPrices.setCurrentMonth12(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "13":
                                    {
                                        bHotelDailyPrices.setCurrentMonth13(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "14":
                                    {
                                        bHotelDailyPrices.setCurrentMonth14(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "15":
                                    {
                                        bHotelDailyPrices.setCurrentMonth15(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "16":
                                    {
                                        bHotelDailyPrices.setCurrentMonth16(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "17":
                                    {
                                        bHotelDailyPrices.setCurrentMonth17(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "18":
                                    {
                                        bHotelDailyPrices.setCurrentMonth18(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "19":
                                    {
                                        bHotelDailyPrices.setCurrentMonth19(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "20":
                                    {
                                        bHotelDailyPrices.setCurrentMonth20(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "21":
                                    {
                                        bHotelDailyPrices.setCurrentMonth21(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "22":
                                    {
                                        bHotelDailyPrices.setCurrentMonth22(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "23":
                                    {
                                        bHotelDailyPrices.setCurrentMonth23(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "24":
                                    {
                                        bHotelDailyPrices.setCurrentMonth24(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "25":
                                    {
                                        bHotelDailyPrices.setCurrentMonth25(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "26":
                                    {
                                        bHotelDailyPrices.setCurrentMonth26(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "27":
                                    {
                                        bHotelDailyPrices.setCurrentMonth27(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "28":
                                    {
                                        bHotelDailyPrices.setCurrentMonth28(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "29":
                                    {
                                        bHotelDailyPrices.setCurrentMonth29(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "30":
                                    {
                                        bHotelDailyPrices.setCurrentMonth30(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "31":
                                    {
                                        bHotelDailyPrices.setCurrentMonth31(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                }
                            }else
                            {
                                //下一个月
                                switch (sdfd.format(effectiveDateDay))
                                {
                                    case "01":
                                    {
                                        bHotelDailyPrices.setNextMonth1(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "02":
                                    {
                                        bHotelDailyPrices.setNextMonth2(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "03":
                                    {
                                        bHotelDailyPrices.setNextMonth3(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "04":
                                    {
                                        bHotelDailyPrices.setNextMonth4(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "05":
                                    {
                                        bHotelDailyPrices.setNextMonth5(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "06":
                                    {
                                        bHotelDailyPrices.setNextMonth6(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "07":
                                    {
                                        bHotelDailyPrices.setNextMonth7(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "08":
                                    {
                                        bHotelDailyPrices.setNextMonth8(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "09":
                                    {
                                        bHotelDailyPrices.setNextMonth9(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "10":
                                    {
                                        bHotelDailyPrices.setNextMonth10(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }case "11":
                                    {
                                        bHotelDailyPrices.setNextMonth11(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }case "12":
                                    {
                                        bHotelDailyPrices.setNextMonth12(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "13":
                                    {
                                        bHotelDailyPrices.setNextMonth13(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "14":
                                    {
                                        bHotelDailyPrices.setNextMonth14(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "15":
                                    {
                                        bHotelDailyPrices.setNextMonth15(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "16":
                                    {
                                        bHotelDailyPrices.setNextMonth16(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "17":
                                    {
                                        bHotelDailyPrices.setNextMonth17(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "18":
                                    {
                                        bHotelDailyPrices.setNextMonth18(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "19":
                                    {
                                        bHotelDailyPrices.setNextMonth19(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "20":
                                    {
                                        bHotelDailyPrices.setNextMonth20(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "21":
                                    {
                                        bHotelDailyPrices.setNextMonth21(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "22":
                                    {
                                        bHotelDailyPrices.setNextMonth22(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "23":
                                    {
                                        bHotelDailyPrices.setNextMonth23(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "24":
                                    {
                                        bHotelDailyPrices.setNextMonth24(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "25":
                                    {
                                        bHotelDailyPrices.setNextMonth25(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "26":
                                    {
                                        bHotelDailyPrices.setNextMonth26(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "27":
                                    {
                                        bHotelDailyPrices.setNextMonth27(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "28":
                                    {
                                        bHotelDailyPrices.setNextMonth28(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "29":
                                    {
                                        bHotelDailyPrices.setNextMonth29(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "30":
                                    {
                                        bHotelDailyPrices.setNextMonth30(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                    case "31":
                                    {
                                        bHotelDailyPrices.setNextMonth31(JSONObject.toJSONString(dailyPricesBean));
                                        break;
                                    }
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        boolean ibHotelDailyPricesSaveOrUpdateSign =
                            ibHotelDailyPricesService.saveOrUpdate(bHotelDailyPrices);
                    }catch (Exception e)
                    {
                        try
                        {
                            ibHotelDailyPricesService.updateById(bHotelDailyPrices);
                        }catch (Exception es)
                        {
                            continue;
                        }
                    }
                    logger.info(HotelID+":酒店-"+ roomPriceInfosBean.getRoomID() +":房型 入库成功");
                }
            }
        }
//        hotelIopriceCache(HotelID);
        //获取结束时间
        long endTime=System.currentTimeMillis();
        logger.info("程序运行时间： "+(endTime-startTime) + "毫秒");
        return true;
    }

     /**
     * @Description: 查mongodb获取指定酒店下所有房型信息
     * @Author: 王俊文
     * @Date: 2019/9/18 上午10:26
     * @Param: [HotelID    酒店id    String, isIntel    酒店类型(0:国内  1:国际 ) String]
     * @returns: java.util.List<com.fntx.common.domain.dto.HotelRoomDetailResModel.RoomStaticInfosBean>
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/9/18 上午10:26          1.0          查mongodb获取指定酒店下所有房型信息
     */
    @Override
    public List<HotelRoomDetailResModel.RoomStaticInfosBean> hotelIopriceStorageMongodbRoomTypeList(String HotelID,
                                                                                                    String isIntel)
    {
        BHotelBaseprice bHotelBaseprice = new BHotelBaseprice();
        HotelRoomDetailResModel hotelRoomDetailResModel = new HotelRoomDetailResModel();
        Query query = new Query(Criteria.where("hotelId").is(HotelID));
        List<HotelRoomMongoModel> hotelRoomMongoModel = mongoTemplate.find(query, HotelRoomMongoModel.class);
        try
        {
             if ( hotelRoomMongoModel == null || hotelRoomMongoModel.size() <= 0 )
            {
                JSONObject jsonObject =
                        iHotelStaticInformationService.queryRoomDetailNotInMongo(HotelID,"1", isIntel);
                hotelRoomDetailResModel = JSONObject.parseObject(jsonObject.toJSONString(), HotelRoomDetailResModel.class);
            }else
            {
                hotelRoomDetailResModel = JSONObject.parseObject(hotelRoomMongoModel.get(0).getHotelRoomDetail(),
                        HotelRoomDetailResModel.class);
                if ( !"Success".equals(hotelRoomDetailResModel.getResponseStatus().getAck()) ||
                        (hotelRoomDetailResModel.getRoomStaticInfos() == null || hotelRoomDetailResModel.getRoomStaticInfos().size() <=0 ) )
                {
                    JSONObject jsonObject =
                        iHotelStaticInformationService.queryRoomDetailNotInMongo(HotelID,"0", isIntel);
                    hotelRoomDetailResModel = JSONObject.parseObject(jsonObject.toJSONString(), HotelRoomDetailResModel.class);
                }
            }
        }catch (Exception e)
        {
            return null;
        }

        //获取对应物理,售卖房型信息
        List<HotelRoomDetailResModel.RoomStaticInfosBean> roomStaticInfosBeanList =
                hotelRoomDetailResModel.getRoomStaticInfos();
        return roomStaticInfosBeanList;
    }

    /**
     * 获取房型人数限制--查mongodb逻辑
     * @param roomStaticInfosBeanList
     * @param roomId
     * @return
     */
    private BHotelRoomPriceInfos hotelIopriceStorageMongodb(
            List<HotelRoomDetailResModel.RoomStaticInfosBean> roomStaticInfosBeanList, Integer roomId, BHotelRoomPriceInfos bHotelRoomPriceInfos)
    {
        if ( roomStaticInfosBeanList == null )
        {
            return bHotelRoomPriceInfos;
        }
        for ( HotelRoomDetailResModel.RoomStaticInfosBean roomStaticInfosBean : roomStaticInfosBeanList )
        {
            for ( HotelRoomDetailResModel.RoomStaticInfosBean.RoomInfosBean roomInfosBean :
                    roomStaticInfosBean.getRoomInfos() )
            {
                if ( roomInfosBean.getRoomID() == roomId )
                {
                    bHotelRoomPriceInfos.setAdult(roomInfosBean.getMaxOccupancy());
                }
            }
        }
        return bHotelRoomPriceInfos;
    }

    /**
     * 获取房型人数限制--查oracle逻辑
     * @param roomId
     * @return
     */
    private BHotelRoomPriceInfos hotelIopriceStorageOracle(Integer roomId)
    {
        BHotelRoomPriceInfos bHotelRoomPriceInfos = new BHotelRoomPriceInfos();
        BSaleRoom bSaleRoom = bSaleRoomMapper.selectById(roomId);
        if ( bSaleRoom == null )
        {
            return bHotelRoomPriceInfos;
        }
        bHotelRoomPriceInfos.setAdult(bSaleRoom.getMaxOccupancy());
        return bHotelRoomPriceInfos;
    }

    /**
     * @Description: 热门城市酒店直连报价缓存
     * @Author: 王俊文
     * @Date: 19-7-26 下午4:39
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-26 下午4:39          1.0          热门城市酒店直连报价缓存
     */
    @Override
    public boolean hotelHotCitiesIoprice()
    {
        /**
         * 获取热门城市列表
         */
        List<Long> cityIdStringList = hotelCityCache();
        /**
         * 获取城市所有房型报价信息并放入缓存
         */
        for ( Long bHotelCity : cityIdStringList )
        {
            /**
             * 获取城市酒店列表
             */
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("CITY_ID", bHotelCity);
            List<BHotelList> bHotelListList  = bHotelListMapper.selectList(queryWrapper);
            for ( BHotelList bHotelList : bHotelListList )
            {
                Map<String, Object> paramMap = new HashMap<>(16);
                paramMap.put("HotelID", bHotelList.getHotelId().toString());
                JSONObject jsonObject = hotelQuoteCache(paramMap);
                if ( jsonObject != null && !"".equals(jsonObject) )
                {
                    redisTemplate.opsForValue().set(bHotelList.getHotelId()+"_QuoteCache", jsonObject.toJSONString(),
                            60*60*24*32, TimeUnit.SECONDS);
                }
            }
        }
        redisTemplate.opsForValue().set("HotCities",
                JSONArray.parseArray(JSON.toJSONString(cityIdStringList)).toJSONString(), 60*60*24*32, TimeUnit.SECONDS);
        return true;
    }

    /**
     * @Description: 缓存指定酒店报价信息
     * @Author: 王俊文
     * @Date: 19-7-29 下午3:51
     * @Param: [HotelID]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-29 下午3:51          1.0          缓存指定酒店报价信息
     */
    @Override
    public boolean hotelIopriceCache(String HotelID)
    {
        Long timeout = 60*60*24L;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("HotelID", HotelID);
        JSONObject jsonObject = hotelQuoteCache(paramMap);
        Object object = redisTemplate.opsForValue().get("HotCities");
        List<Long> bHotelCityIdList = new ArrayList<>();
        if ( object == null || JSONArray.parseArray(object.toString(),Long.class).size() <= 0 )
        {
            //从数据库获取热门城市
            bHotelCityIdList = hotelCityCache();
            //存入redis
            if ( bHotelCityIdList != null && bHotelCityIdList.size() > 0 )
            {
                redisTemplate.opsForValue().set("HotCities",
                    JSONArray.parseArray(JSON.toJSONString(bHotelCityIdList)).toJSONString(), 60*60*24*32, TimeUnit.SECONDS);
            }
        }else
        {
            //判断当前酒店是否为热门城市中的酒店
            bHotelCityIdList= JSONArray.parseArray(object.toString(),
                    Long.class);
        }

        if ( bHotelCityIdList != null && bHotelCityIdList.size() > 0 )
        {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("HOTEL_ID", HotelID);
            queryWrapper.in("CITY_ID", bHotelCityIdList);
            List<BHotelList> bHotelListList  = bHotelListMapper.selectList(queryWrapper);
            if ( bHotelListList != null && bHotelListList. size() >=1 )
            {
                timeout = 60*60*24*32L;
            }else
            {
                timeout = 60*60*24L;
            }
        }

        redisTemplate.opsForValue().set(HotelID+"_QuoteCache", jsonObject.toJSONString(),
                            timeout, TimeUnit.SECONDS);
        return true;
    }

    /**
     * @Description: 热门城市缓存
     * @Author: 王俊文
     * @Date: 19-7-30 下午2:03
     * @Param: []
     * @returns: java.util.List<java.lang.Long>
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-30 下午2:03          1.0          热门城市缓存
     */
    @Override
    public List<Long> hotelCityCache()
    {
        /**
         * 获取热门城市列表
         */
        QueryWrapper queryWrapperCity = new QueryWrapper();
        queryWrapperCity.eq("IS_HOT", "1");
        List<BHotelCity> bHotelCityList = bHotelCityMapper.selectList(queryWrapperCity);
        logger.info("酒店列表:" + bHotelCityList.toString());
        /**
         * 获取城市所有房型报价信息并放入缓存
         */
        List<Long> hotelIdStringList = new ArrayList<>();
        for ( BHotelCity bHotelCityS : bHotelCityList )
        {
            hotelIdStringList.add(bHotelCityS.getCityId());
        }
        redisTemplate.opsForValue().set("HotCities",
                JSONArray.parseArray(JSON.toJSONString(hotelIdStringList)).toJSONString(), 60*60*24*32, TimeUnit.SECONDS);
        return hotelIdStringList;
    }

    /**
     * 获取对应酒店信息
     * @param paramMap
     * @return
     */
    private JSONObject hotelQuoteCache(Map<String, Object> paramMap)
    {
        JSONObject jsonObject = new JSONObject();
        //可用房型列表
        List<Integer> roomIdList = bHotelRoomPriceInfosMapper.getRoomIdList(paramMap);
        if ( roomIdList == null || roomIdList.size() <= 0 )
        {
            return null;
        }
        List<RoomPriceItemsDto> roomPriceItemsDtoList = bHotelRoomPriceInfosMapper.getRoomPriceItemsList(paramMap);
        //获取所有日报价列表
        List<BHotelDailyPrices> bHotelDailyPricesList = bHotelDailyPricesMapper.selectBatchIds(roomIdList);

        //房型信息
        jsonObject.put("houseType", JSONArray.parseArray(JSON.toJSONString(roomPriceItemsDtoList)).toJSONString());
        //报价信息
        jsonObject.put("quote", JSONArray.parseArray(JSON.toJSONString(bHotelDailyPricesList)).toJSONString());
        return jsonObject;
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
            //测试时使用  调用弗恩
           Object obj = redisTemplate.opsForValue().get("testToken");
           if ( obj == null )
           {
               return null;
           }
           String Access_Token = String.valueOf(obj);
//            url = url.replace("{app_path}", "hotel.tianxiafen.com:82")
//                    .replace("{aid}", "1")
//                    .replace("{sid}", "101")
//                    .replace("{key}", Enviroment.KEY.getValue())
//                    .replace("{format}", Enviroment.FORMAT.getValue())
//                    .replace("{GUID}", "A63FA038-32B2-46C7-A363-42E651378752")
//                    .replace("{Access_Token}", Access_Token)
//                    .replace("{ICODE}", icode);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
                body = restTemplate.exchange(url, HttpMethod.POST, formEntity, JSONObject.class).getBody();
        return body;
    }

    /**
     * @Description: 直连入离报价入库
     * @Author: 王俊文
     * @Date: 19-8-8 下午1:26
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-8 下午1:26          1.0      直连入离报价入库
     */
    @Override
    public boolean hotelIoprice() {
        List<Long> cityIdStringList = hotelCityCache();
        for ( Long bHotelCity : cityIdStringList )
        {
            /**
             * 获取城市酒店列表
             */
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("CITY_ID", bHotelCity);
            List<BHotelList> bHotelListList  = bHotelListMapper.selectList(queryWrapper);
            Integer sign = 0;
            for ( BHotelList bHotelList : bHotelListList )
            {
                sign++;
                //判断当前酒店是否已更新
//                Map<String, Object> paramMap = new HashMap<>(16);
//                paramMap.put("HotelID", bHotelList.getHotelId());
//                paramMap.put("CurrentMonth", sdfm.format(new Date()));
//                Integer hotelRoomNumber = bHotelRoomPriceInfosMapper.getHotelRoomNumber(paramMap);
//                if ( hotelRoomNumber > 0 )
//                {
//                    logger.info(bHotelCity + "城市下的 " + bHotelList.getHotelId() + ":酒店已更新,跳过");
//                    continue;
//                }
                //获取开始时间
                long startTime=System.currentTimeMillis();
                logger.info("城市:" + bHotelList.getCityId() + "; 酒店:" + bHotelList.getHotelId());
                boolean ss = getHotelIoprice(bHotelList.getHotelId().toString());
                //获取结束时间
                long endTime=System.currentTimeMillis();
                logger.info("controller层程序运行时间： "+(endTime-startTime) + "毫秒");
            }
        }
        return true;
    }
}
