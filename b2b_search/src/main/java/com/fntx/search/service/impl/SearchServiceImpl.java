package com.fntx.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fntx.common.constant.CtripApiErrorCode;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.dao.*;
import com.fntx.common.domain.BHotelDailyPrices;
import com.fntx.common.domain.BaseApiReq;
import com.fntx.common.domain.dto.*;
import com.fntx.common.utils.MD5Util;
import com.fntx.search.feign.AdminFeign;
import com.fntx.search.service.ISearchService;
import com.fntx.search.utils.CommonRedisHelper;
import com.fntx.search.utils.ReturnUtils;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SearchServiceImpl implements ISearchService {

    private static Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
    @Autowired
    private ReturnUtils returnUtils;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BHotelBasepriceMapper bHotelBasepriceMapper;
    @Autowired
    private BHotelCityMapper bHotelCityMapper;
    @Autowired
    private BHotelRoomPriceInfosMapper bHotelRoomPriceInfosMapper;
    @Autowired
    private BHotelDailyPricesMapper bHotelDailyPricesMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AdminFeign adminFeign;
    @Autowired
    private BSaleRoomMapper bSaleRoomMapper;
    @Autowired
    private BStaticChangeInfoMapper bStaticChangeInfoMapper;
    @Autowired
    private CommonRedisHelper commonRedisHelper;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
    private static SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdfmd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM");
    private static SimpleDateFormat sdfd = new SimpleDateFormat("dd");

    /**
     * @Description: 监测静态信息变化
     * @Author: 王俊文
     * @Date: 19-7-12 下午3:11
     * @Param: [
     * parasJSON
     * ,IsIntel    酒店类型 0：国内   1：国际
     * ]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-12 下午3:11          1.0          监测静态信息变化
     */
    @Override
    public JSONObject getHotelIncrData(JSONObject parasJSON, String IsIntel) {
        //返回参数
        JSONObject resultModel = new JSONObject(16);
        JSONObject responseStatus = returnUtils.responseStatus();
        JSONObject pagingInfo = new JSONObject(16);
        JSONArray changeInfos = new JSONArray(16);

        //校验请求参数
        if (parasJSON.get("SearchCandidate") == null || "".equals(parasJSON.get("SearchCandidate")))
        {
            return returnUtils.timeRange(responseStatus);
        }
        JSONObject SearchCandidate = parasJSON.getJSONObject("SearchCandidate");
        JSONObject Settings = parasJSON.getJSONObject("Settings");
        JSONObject PagingSettings = parasJSON.getJSONObject("PagingSettings");
        //校验子级请求参数
        if (SearchCandidate.getString("StartTime") == null || "".equals(SearchCandidate.getString("StartTime")) ||
                SearchCandidate.getInteger("Duration") == null || "".equals(SearchCandidate.getInteger("Duration"))) {
            return returnUtils.timeIllegality(responseStatus);
        }

        //校验请求类型
        String FullAmount = Settings.get("FullAmount") != null ? Settings.getString("FullAmount"):"F";
        if ( FullAmount.compareTo("T") == 0 )
        {
            //转发携程
            Settings.remove("FullAmount");
            parasJSON.put("Settings", Settings);
            String icode = "";
            if (Enviroment.IS_RELEASE_ENV) {
                icode = Enviroment.HOTEL_BASEPRICE.getValue();
            } else {
                icode = Enviroment.TEST_HOTEL_BASEPRICE.getValue();
            }
            //弗恩测试  正式情况下注释掉
                icode = "hotel.incr.data";
            if ( "0".equals(IsIntel) )
            {
                resultModel = getResponse(parasJSON, icode, "200");
            }else if ( "1".equals(IsIntel) )
            {
                resultModel = getResponseOverseas(parasJSON, icode, "200");
            }
            return resultModel;
        }

        //查询请求时间内有变动的酒店id
        //开始时间
        String StartTime = SearchCandidate.getString("StartTime");
        //校验开始时间是否符合规定
        Date starTimeDate = null;
        try {
            Date date = new Date();
            starTimeDate = sdft.parse(StartTime);
            Calendar A = Calendar.getInstance();
            Calendar B = Calendar.getInstance();
            A.setTime(date);
            A.add(Calendar.DAY_OF_MONTH, -1);
            B.setTime(date);
            B.add(Calendar.MINUTE, 10);
            if (A.getTime().compareTo(starTimeDate) > 0 || B.getTime().compareTo(starTimeDate) < 0)
            {
                return returnUtils.timeIllegality(responseStatus);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //持续秒数
        Integer Duration = SearchCandidate.getInteger("Duration");
        //校验持续秒数是否符合规定
        if (Duration.compareTo(600) > 0 || Duration.compareTo(0) == 0) {
            Duration = 600;
        }
        //获取分页信息  本接口规则,该参数为上次请求的实际页数.本次分页请求是只需加1即可.
        //解密页码
        Integer LastRecordID =1;
        if ( PagingSettings.getString("LastRecordID") != null && !"".equals(PagingSettings.getString("LastRecordID")) )
        {
            String LastRecordIDStrs = PagingSettings.getString("LastRecordID");
            if ( !LastRecordIDStrs.equals("0") && LastRecordIDStrs.length()>6)
            {
               String LastRecordIDStr = PagingSettings.getString("LastRecordID").substring(3, PagingSettings.getString("LastRecordID").length()-3);
               Double LastRecordIDDouble = Double.parseDouble(LastRecordIDStr)/33;
               String signP = "([^0][0-9]+|0)";
               Pattern p = Pattern.compile(signP); // 正则表达式
               Matcher m = p.matcher(LastRecordIDDouble.toString()); // 操作的字符串
               boolean b = m.matches(); //返回是否匹配的结果
               if ( b )
               {
                   LastRecordID = Integer.parseInt(LastRecordIDDouble.toString());
               }
            }
        }

        //获取步长
        Integer PageSize = (PagingSettings.getInteger("PageSize") != null && !"".equals(PagingSettings.getInteger("PageSize"))) ?
                PagingSettings.getInteger("PageSize") : 10;
        if (PageSize.compareTo(200) > 0) {
            PageSize = 200;
        }
        //获取IsShowChangeDetails内容  暂不需要处理
        String IsShowChangeDetails = Settings.getString("IsShowChangeDetails");

        //生成结束时间
        Calendar AEnd = Calendar.getInstance();
        AEnd.setTime(starTimeDate);
        AEnd.add(Calendar.SECOND, Duration);

        //查询信息 mongodb 和 入库逻辑只能二选一
        //走mongodb start
        changeInfos = getHotelIncrDataMongodb(sdf.format(starTimeDate), sdf.format(AEnd.getTime()), PageSize,
                LastRecordID, IsIntel);
        //走mongodb end

        //入库 start
//        changeInfos = getHotelIncrDataWarehousing(sdfo.format(starTimeDate), sdfo.format(AEnd.getTime()), PageSize,
//                LastRecordID, IsIntel);
        //入库 end

        LastRecordID = LastRecordID+1;
        LastRecordID = LastRecordID*33;
        int tempFront=(int)(Math.random()*500)+100;
        int tempAfter=(int)(Math.random()*500)+100;

        pagingInfo.put("LastRecordID", String.valueOf(tempFront) + LastRecordID + String.valueOf(tempAfter));
        resultModel.put("PagingInfo", pagingInfo);
        resultModel.put("ChangeInfos", changeInfos);
        resultModel.put("ResponseStatus", responseStatus);
        return resultModel;
    }

    /**
     * 静态增量mongodb逻辑
     * @return
     */
    private JSONArray getHotelIncrDataMongodb(String starTimeDate, String endTimeDate, Integer PageSize,
                                              Integer LastRecordID, String IsIntel)
    {
        JSONArray jsonArray = new JSONArray(16);
        //查询mongodb数据
        Sort sort = new Sort(Sort.Direction.ASC, "ChangeTime");//多条件DEVID、time
        Query query =
                new Query(new Criteria().andOperator(
                        Criteria.where("ChangeTime").gte(starTimeDate),
                        Criteria.where("ChangeTime").lte(endTimeDate)));
        if ( "0".equals(IsIntel) )
        {
            List<HotelIncrDataDto> hotelIncrDataDtoList =
            mongoTemplate.find(query.with(sort).skip(LastRecordID * PageSize).limit(PageSize), HotelIncrDataDto.class);
            jsonArray =  JSONArray.parseArray(JSONObject.toJSONString(hotelIncrDataDtoList));
        }else if ( "1".equals(IsIntel) )
        {
            List<InternationalHotelIncrDataDto> internationalHotelIncrDataDtoList  =
            mongoTemplate.find(query.with(sort).skip(LastRecordID * PageSize).limit(PageSize), InternationalHotelIncrDataDto.class);
            jsonArray =  JSONArray.parseArray(JSONObject.toJSONString(internationalHotelIncrDataDtoList));
        }
        return jsonArray;
    }

    /**
     * 静态增量查库逻辑--待数据入库后修改.  添加酒店类型字段
     * @return
     */
    private JSONArray getHotelIncrDataWarehousing(String starTimeDate, String endTimeDate, Integer PageSize,
                                                  Integer LastRecordID, String IsIntel)
    {
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("starTimeDate", starTimeDate);
        paramMap.put("endTimeDate", endTimeDate);
        paramMap.put("IsIntel", IsIntel);
        PageHelper.startPage(LastRecordID, PageSize);
        PageHelper.orderBy("bsci.CHANGETIME");
        List<HotelIncrDataWarehousingDto> hotelIncrDataWarehousingDtoList =
            bStaticChangeInfoMapper.selectIncrDataListLimit(paramMap);

        List<HotelIncrDataDto> hotelIncrDataDtoList = bStaticChangeInfoMapper.selectIncrDataList(hotelIncrDataWarehousingDtoList);
        return JSONArray.parseArray(JSONObject.toJSONString(hotelIncrDataDtoList));
    }

    /**
     * @Description: 监测房型上下线--停用
     * @Author: 王俊文
     * @Date: 19-7-12 下午3:10
     * @Param: [
     * parasJSON
     * ,IsIntel    酒店类型 0：国内   1：国际
     * ]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-12 下午3:10          1.0          监测房型上下线
     */
    @Override
    public JSONObject getHotelIncrRoomtype(JSONObject parasJSON, String IsIntel) {
        //返回参数
        JSONObject resultModel = new JSONObject(16);
        JSONObject responseStatus = returnUtils.responseStatus();

        JSONArray RoomStatusChangeItems = new JSONArray(16);

        //校验请求参数
        if (parasJSON.get("SearchCandidate") == null || "".equals(parasJSON.get("SearchCandidate"))) {
            return returnUtils.parameterEmpty(responseStatus);
        }
        JSONObject SearchCandidate = parasJSON.getJSONObject("SearchCandidate");
        //校验子级请求参数
        if (SearchCandidate.getString("DateTimeRange") == null || "".equals(SearchCandidate.getString("DateTimeRange"))) {
            return returnUtils.invalidDate(responseStatus);
        }
        JSONObject DateTimeRange = SearchCandidate.getJSONObject("DateTimeRange");

        //校验开始,结束时间是否符合标准
        Date starTimeDate = null;
        Date endTimeDate = null;
        try {
            Date date = new Date();
            starTimeDate = sdft.parse(DateTimeRange.getString("Start"));
            endTimeDate = sdft.parse(DateTimeRange.getString("End"));
            Calendar A = Calendar.getInstance();
            Calendar B = Calendar.getInstance();
            A.setTime(date);
            A.add(Calendar.DAY_OF_MONTH, -1);
            B.setTime(starTimeDate);
            B.add(Calendar.HOUR, 1);
            //目前时间范围超过当前时间1天以上则无数据,待确定规则
            if (B.getTime().compareTo(endTimeDate) < 0) {
                //开始时间和结束时间间隔不能超过1小时
                return returnUtils.serviceError(responseStatus);
            }
            if (starTimeDate.compareTo(endTimeDate) > 0) {
                //截至时间不能比起始时间早
                return returnUtils.startTimeGreaterThanEndTime(responseStatus);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //查询请求时间段内房型信息
        //查询信息 mongodb 和 入库逻辑只能二选一
        //走mongodb start
        RoomStatusChangeItems = getHotelIncrRoomtypeMongodb(sdf.format(starTimeDate), sdf.format(endTimeDate));
        //走mongodb end

        //入库 start
//        RoomStatusChangeItems = getHotelIncrRoomtypeWarehousing(starTimeDate, endTimeDate, IsIntel);
        //入库 end


        resultModel.put("ResponseStatus", responseStatus);
        resultModel.put("RoomStatusChangeItems", RoomStatusChangeItems);
        return resultModel;
    }

    /**
     * 房型上下架mongodb逻辑
     * @return
     */
    private JSONArray getHotelIncrRoomtypeMongodb(String starTimeDate, String endTimeDate)
    {
        //查询mongodb数据
        //排序
        Sort sort = new Sort(Sort.Direction.ASC, "DateChangeLastTime");//多条件DEVID、time
        //筛选条件
        Query query =
                new Query(new Criteria().andOperator(
                        Criteria.where("DateChangeLastTime").gte(starTimeDate),
                        Criteria.where("DateChangeLastTime").lte(endTimeDate)));
        List<HotelIncrRoomtypeMgDto> hotelIncrRoomtypeMgDtoList =
        mongoTemplate.find(query.with(sort), HotelIncrRoomtypeMgDto.class);
        return JSONArray.parseArray(JSONObject.toJSONString(hotelIncrRoomtypeMgDtoList));
    }

    /**
     * 房型上下架查库逻辑
     * @return
     */
    private JSONArray getHotelIncrRoomtypeWarehousing(Date starTimeDate, Date endTimeDate, String IsIntel)
    {
        Map<String, Object> paramMap = new HashMap<>(16);
        //开始时间
        paramMap.put("starTimeDate", starTimeDate);
        //结束时间
        paramMap.put("endTimeDate", endTimeDate);
        //查询对应酒店信息
        paramMap.put("IsIntel", IsIntel);
        List<HotelIncrRoomtypeDto> hotelIncrRoomtypeDtoList = bSaleRoomMapper.selectBSaleRoomUpDownLine(paramMap);
        return JSONArray.parseArray(JSON.toJSONString(hotelIncrRoomtypeDtoList));
    }

    /**
     * @Description: 查询某城市下各酒店的起价（国内+海外）
     * @Author: 王俊文
     * @Date: 19-7-11 下午4:42
     * @Param: [parasJSON]
     * @returns: java.util.Map<java.lang.String, java.lang.Object>
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-11 下午4:42          1.0          查询某城市下各酒店的起价（国内+海外）
     */
    @Override
    public JSONObject getHotelBaseprice(JSONObject parasJSON, String IsInte)
    {
        long startTime=System.currentTimeMillis();
//        JSONObject jsonObject = new JSONObject(16);
        JSONArray errors  = new JSONArray(16);
        JSONObject errorsObject = new JSONObject(16);
        JSONArray extension = new JSONArray(16);
        JSONObject extensionObject = new JSONObject(16);
        //校验分销商类型是否正确
        JSONObject resultModel = new JSONObject(16);
        JSONObject responseStatus = returnUtils.responseStatus();

        JSONArray NumEntity = new JSONArray(16);
        JSONArray HotelDataLists = new JSONArray(16);
        //校验请求参数
        if (parasJSON.get("SearchTypeEntity") == null || "".equals(parasJSON.get("SearchTypeEntity")) ||
                parasJSON.get("PublicSearchParameter") == null || "".equals(parasJSON.get("PublicSearchParameter"))) {
            return returnUtils.parameterEmpty(responseStatus);
        }

        JSONObject SearchTypeEntity = parasJSON.getJSONObject("SearchTypeEntity");
        JSONObject PublicSearchParameter = parasJSON.getJSONObject("PublicSearchParameter");
        JSONObject FacilityEntity = parasJSON.getJSONObject("FacilityEntity");
        JSONObject OrderBy = parasJSON.getJSONObject("OrderBy");
        //校验二级参数
        if (SearchTypeEntity.getString("SearchType") == null || "".equals(SearchTypeEntity.getString("SearchType")) ||
                SearchTypeEntity.getString("HotelCount") == null || "".equals(SearchTypeEntity.getString("HotelCount")) ||
                PublicSearchParameter.getString("CheckInDate") == null || "".equals(PublicSearchParameter.getString("CheckInDate")) ||
                PublicSearchParameter.getString("CheckOutDate") == null || "".equals(PublicSearchParameter.getString("CheckOutDate")) ||
                (
                        (PublicSearchParameter.getString("City") == null || "".equals(PublicSearchParameter.getString("City")))
                                &&
                                (PublicSearchParameter.getString("HotelList") == null || "".equals(PublicSearchParameter.getString("HotelList")))
                )
        ) {
            return returnUtils.parameterEmpty(responseStatus);
        }
        //国内城市/酒店(包含港澳台)，则传值“WirelessSearch”;国外城市/酒店，则传值“OnLineIntlGTASearch”;
        String SearchType = SearchTypeEntity.getString("SearchType");
        if ( "WirelessSearch".equals(SearchType) )
        {
            if ( !"0".equals(IsInte) )
            {
                responseStatus.put("Ack", "Warning");
                errorsObject.put("Message", "业务类型有误。");
                errorsObject.put("ErrorCode", "100001");
                errorsObject.put("ErrorFields", new ArrayList<>());
                errorsObject.put("ErrorClassification", "ValidationError");
                errors.add(errorsObject);
                extensionObject.put("Id", "100001");
                extensionObject.put("Value", "业务类型有误。");
                extension.add(extensionObject);

                responseStatus.put("Errors", errors);
                responseStatus.put("Extension", extension);
                resultModel.put("ResponseStatus", responseStatus);
                return resultModel;
            }
        }else if ( "OnLineIntlGTASearch".equals(SearchType) )
        {
             if ( !"1".equals(IsInte) )
            {
                responseStatus.put("Ack", "Warning");
                errorsObject.put("Message", "业务类型有误。");
                errorsObject.put("ErrorCode", "100001");
                errorsObject.put("ErrorFields", new ArrayList<>());
                errorsObject.put("ErrorClassification", "ValidationError");
                errors.add(errorsObject);
                extensionObject.put("Id", "100001");
                extensionObject.put("Value", "业务类型有误。");
                extension.add(extensionObject);

                responseStatus.put("Errors", errors);
                responseStatus.put("Extension", extension);
                resultModel.put("ResponseStatus", responseStatus);
                return resultModel;
            }
        }

        //校验当前请求是否为重复请求
        //md5转换当前请求体
        String md5ParasMap = MD5Util.encrypt(parasJSON.toJSONString());
        Object returnMap = redisTemplate.opsForValue().get("HotelBaseprice_" + md5ParasMap);
        if ( returnMap != null )
        {
            resultModel = JSONObject.parseObject(returnMap.toString());
        }else
        {
            //请求服务
           //redis缓存逻辑 start
            resultModel =  getHotelBasepriceRedis(parasJSON, IsInte);
            //redis缓存逻辑 end

            //oracle入库逻辑 start
    //       resultModel =  getHotelBasepriceOracle(parasJSON, String IsInte);
            //oracle 入库逻辑 end
        }
        long endTime=System.currentTimeMillis();
        logger.info("主服务运行时间： "+(endTime-startTime) + "毫秒");
        return resultModel;
    }


    /**
     * 查询某城市下各酒店的起价（国内+海外）-- redis缓存逻辑
     * @param parasJSON
     * @return
     */
    private JSONObject getHotelBasepriceRedis(JSONObject parasJSON, String IsInte)
    {
        JSONObject jsonObject = new JSONObject(16);
        //查询reids
        String key = "HotelBaseprice_" + MD5Util.encrypt(parasJSON.toJSONString());
        //转发携程
        String icode = "";
        if (Enviroment.IS_RELEASE_ENV) {
            icode = Enviroment.HOTEL_BASEPRICE.getValue();
        } else {
            icode = Enviroment.TEST_HOTEL_BASEPRICE.getValue();
        }
        //弗恩测试  正式情况下注释掉
            icode = "hotel.baseprice";
        if ( "0".equals(IsInte) )
        {
            jsonObject = getResponse(parasJSON, icode, "200");
        }else if ( "1".equals(IsInte) )
        {
            jsonObject = getResponseOverseas(parasJSON, icode, "200");
        }

        if ( CtripApiErrorCode.RateLimit.getCode().equals(jsonObject.get("ErrCode")) )
        {
            return jsonObject;
        }
        //请求缓存
        redisTemplate.opsForValue().set(key, jsonObject.toJSONString(), 60*60*24,
                TimeUnit.SECONDS);

        return jsonObject;
    }

    /**
     * 查询某城市下各酒店的起价（国内+海外）-- 入库逻辑
     * @param parasJSON
     * @return
     */
    private JSONObject getHotelBasepriceOracle(JSONObject parasJSON, String IsInte)
    {
        //返回参数
        JSONObject resultModel = new JSONObject(16);
        JSONObject responseStatus = returnUtils.responseStatus();

        JSONArray NumEntity = new JSONArray(16);
        JSONArray HotelDataLists = new JSONArray(16);
        //校验请求参数
        if (parasJSON.get("SearchTypeEntity") == null || "".equals(parasJSON.get("SearchTypeEntity")) ||
                parasJSON.get("PublicSearchParameter") == null || "".equals(parasJSON.get("PublicSearchParameter"))) {
            return returnUtils.parameterEmpty(responseStatus);
        }

        JSONObject SearchTypeEntity = parasJSON.getJSONObject("SearchTypeEntity");
        JSONObject PublicSearchParameter = parasJSON.getJSONObject("PublicSearchParameter");
        JSONObject FacilityEntity = parasJSON.getJSONObject("FacilityEntity");
        JSONObject OrderBy = parasJSON.getJSONObject("OrderBy");
        //校验二级参数
        if (SearchTypeEntity.getString("SearchType") == null || "".equals(SearchTypeEntity.getString("SearchType")) ||
                SearchTypeEntity.getString("HotelCount") == null || "".equals(SearchTypeEntity.getString("HotelCount")) ||
                PublicSearchParameter.getString("CheckInDate") == null || "".equals(PublicSearchParameter.getString("CheckInDate")) ||
                PublicSearchParameter.getString("CheckOutDate") == null || "".equals(PublicSearchParameter.getString("CheckOutDate")) ||
                (
                        (PublicSearchParameter.getString("City") == null || "".equals(PublicSearchParameter.getString("City")))
                                &&
                                (PublicSearchParameter.getString("HotelList") == null || "".equals(PublicSearchParameter.getString("HotelList")))
                )
        ) {
            return returnUtils.parameterEmpty(responseStatus);
        }
        //获取参数
        resultModel.put("ResponseStatus", responseStatus);
        resultModel.put("NumEntity", NumEntity);
        resultModel.put("AllHotelID", "");
        resultModel.put("HotelDataLists", HotelDataLists);
        //拼接筛选条件并获取起价信息
        Map<String, Object> paramMap = new HashMap<>(16);
        //酒店类型
        String SearchType = SearchTypeEntity.getString("SearchType");
        paramMap.put("SEARCH_TYPE", SearchType);
        //步长 定义单页最大返回几家酒店，上限为100
        Integer HotelCount = SearchTypeEntity.getInteger("HotelCount");
        if (HotelCount == null || HotelCount <= 0 || HotelCount > 100) {
            HotelCount = 100;
        }
        //页码 指定返回酒店列表的页码，留空则返回第一页
        Integer PageIndex = SearchTypeEntity.getInteger("PageIndex");
        boolean pageSign = false;
        if (PageIndex == null || PageIndex <= 1)
        {
            PageIndex = 1;
            pageSign = true;
        }
        //分页及 排序
        PageHelper.startPage(PageIndex, HotelCount);
        PageHelper.orderBy("MIN_PRICE " + OrderBy.getString("OrderType"));
        //入离时间忽略
        //城市id 或 酒店id列表
        String HotelList = PublicSearchParameter.getString("HotelList");
        Integer City = PublicSearchParameter.getInteger("City");
        boolean hotelListBoolean = (HotelList == null || "".equals(HotelList));
        boolean cityBoolean = (City == null || "".equals(City));
        if ( hotelListBoolean && cityBoolean )
        {
            return resultModel;
        }
        paramMap.put("CITY_ID", City);
        paramMap.put("HOTEL_ID", HotelList);
        //人数限制
        String FILTER_ROOM_BY_PERSON = PublicSearchParameter.getString("FilterRoomByPerson");
        paramMap.put("FILTER_ROOM_BY_PERSON", FILTER_ROOM_BY_PERSON != null ? FILTER_ROOM_BY_PERSON.split(",")[0] : null);
        //支付类型
        Boolean OnlyPPPrice = PublicSearchParameter.getBoolean("OnlyPPPrice") != null ? PublicSearchParameter.getBoolean(
                "OnlyPPPrice") : false;
        Boolean OnlyFGPrice = PublicSearchParameter.getBoolean("OnlyFGPrice") != null ? PublicSearchParameter.getBoolean(
                "OnlyFGPrice") : false;
        if (OnlyPPPrice && OnlyFGPrice) {
            return resultModel;
        }
        if (OnlyPPPrice || OnlyFGPrice) {
            paramMap.put("PAYMENT_TYPE", OnlyPPPrice ? "OnlyPPPrice" : "OnlyFGPrice");
        }
        //立即确认
        paramMap.put("IS_JUSTIFY_CONFIRM", PublicSearchParameter.getString("IsJustifyConfirm"));

        if (FacilityEntity != null) {
            //儿童限制
            JSONArray ChildrenAgeList = FacilityEntity.getJSONArray("ChildrenAgeList");
            if (ChildrenAgeList != null) {
                //获取儿童人数限制
                paramMap.put("CHILDREN_NUMBER_LIST", ChildrenAgeList.size());
                //获取年龄限制
                Integer maxAge = ChildrenAgeList.getInteger(0), minAge = ChildrenAgeList.getInteger(0);
                for ( int i = 0; i<ChildrenAgeList.size(); i++)
                {
                    if ( ChildrenAgeList.getInteger(i) > maxAge )
                    {
                        maxAge = ChildrenAgeList.getInteger(i);
                    }
                    if ( ChildrenAgeList.getInteger(i) < minAge )
                    {
                        minAge = ChildrenAgeList.getInteger(i);
                    }
                }
                paramMap.put("CHILD_MAXAGE", maxAge);
                paramMap.put("CHILD_MINAGE", minAge);
            }
            //起价区间
            JSONObject priceRange = FacilityEntity.getJSONObject("priceRange");
            if (priceRange != null) {
                if (priceRange.getString("lowPrice") != null && !"".equals(priceRange.getString("lowPrice"))) {
                    //最低价
                    paramMap.put("lowPrice", priceRange.getString("lowPrice"));
                }
                if (priceRange.getString("highPrice") != null && !"".equals(priceRange.getString("highPrice"))) {
                    //最高价
                    paramMap.put("highPrice", priceRange.getString("highPrice"));
                }
            }
        }
        List<com.fntx.common.domain.dto.HotelDataLists> bHotelBasepriceList = bHotelBasepriceMapper.getBHotelBasepriceList(paramMap);
        if ( bHotelBasepriceList == null || bHotelBasepriceList.size() <= 0 )
        {
            //转发携程
            String icode = "";
            if (Enviroment.IS_RELEASE_ENV) {
                icode = Enviroment.HOTEL_IOPRICE.getValue();
            } else {
                icode = Enviroment.TEST_HOTEL_IOPRICE.getValue();
            }
            //弗恩测试  正式情况下注释掉
            icode = "hotel.baseprice";
            JSONObject jsonObject = new JSONObject(16);
            if ( "0".equals(IsInte) )
            {
                jsonObject = getResponse(parasJSON, icode, "200");
            }else if ( "1".equals(IsInte) )
            {
                jsonObject = getResponseOverseas(parasJSON, icode, "200");
            }
            if ( CtripApiErrorCode.RateLimit.getCode().equals(jsonObject.get("ErrCode")) )
            {
                return jsonObject;
            }
            //通知消息队列补录相关直连报价信息
            if ( jsonObject.get("ResponseStatus") != null && !"".equals(jsonObject.get("ResponseStatus")) )
            {
                JSONObject ResponseStatus = jsonObject.getJSONObject("ResponseStatus");
                if ( ResponseStatus.getString("Ack") != null && "Success".equals(ResponseStatus.getString("Ack")) )
                {
                    //解析数据体
                    List<HotelDataLists> hotelIncrDataDtoList= jsonObject.getObject("HotelDataLists",
                            ArrayList.class);
                    if ( hotelIncrDataDtoList != null && hotelIncrDataDtoList.size() > 0 )
                    {
                        Map<String, Object> paramMapMesssage = new HashMap<>(16);
//                        String sign = (City==null || "".equals(City))?HotelList:City.toString();
                        if ( City != null && !"".equals(City) )
                        {
                            paramMapMesssage.put("redisKey",
                                    City+"_hotelbaseprice_Warehousing_"+PublicSearchParameter.getString("CheckInDate")+PageIndex);
                            adminFeign.messageQueue(paramMapMesssage);
                        }
                    }
                }
            }
            return jsonObject;
        }
        //获取所有符合条件的酒店id
        String AllHotelID = "";
        if ( pageSign )
        {
            AllHotelID = bHotelBasepriceMapper.getBHotelBasepriceHotelStr(paramMap);
        }
        //获取城市信息
        paramMap.put("CityNum", bHotelBasepriceList.size());
        //查询城市信息
        List<com.fntx.common.domain.dto.NumEntity> numEntityList = bHotelBasepriceMapper.getBHotelCityList(paramMap);

        NumEntity.addAll(numEntityList);
        HotelDataLists.addAll(bHotelBasepriceList);
        resultModel.put("ResponseStatus", responseStatus);
        resultModel.put("NumEntity", JSONArray.parseArray(JSON.toJSONString(NumEntity)));
        resultModel.put("AllHotelID", AllHotelID);
        resultModel.put("HotelDataLists", JSONArray.parseArray(JSON.toJSONString(HotelDataLists)));
        return resultModel;
    }

    /**
     * @Description: 查询某酒店的直连/入离报价（国内+海外）
     * @Author: 王俊文
     * @Date: 19-7-11 下午4:43
     * @Param: [parasJSON]
     * @returns: java.util.Map<java.lang.String, java.lang.Object>
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-11 下午4:43          1.0
     */
    @Override
    public JSONObject getHotelIoprice(JSONObject checkModel, JSONObject parasJSON, String IsInte)
    {
        JSONObject jsonObject = new JSONObject(16);
        //redis缓存逻辑 start
        jsonObject = getHotelIopriceRedis(parasJSON, IsInte);
        //redis缓存逻辑 end

        //oracle入库逻辑 start
//       jsonObject = getHotelIopriceOracle(parasJSON, String IsInte);
        //oracle 入库逻辑 end
        return jsonObject;
    }

    /**
     * 查询某酒店的直连/入离报价（国内+海外） -- redis缓存逻辑
     * @param parasJSON
     * @return
     */
    private JSONObject getHotelIopriceRedis(JSONObject parasJSON, String IsInte)
    {
        JSONObject jsonObject = new JSONObject(16);
        //查询reids
        String key = "HotelIoprice_" + MD5Util.encrypt(parasJSON.toJSONString());
        //转发携程
        String icode = "";
        if (Enviroment.IS_RELEASE_ENV) {
            icode = Enviroment.HOTEL_IOPRICE.getValue();
        } else {
            icode = Enviroment.TEST_HOTEL_IOPRICE.getValue();
        }
        //弗恩测试  正式情况下注释掉
            icode = "hotel.ioprice";
        if ( "0".equals(IsInte) )
        {
            jsonObject = getResponse(parasJSON, icode, "6000");
        }else if ( "1".equals(IsInte) )
        {
            jsonObject = getResponseOverseas(parasJSON, icode, "6000");
        }
        if ( CtripApiErrorCode.RateLimit.getCode().equals(jsonObject.get("ErrCode")) )
        {
            return jsonObject;
        }
        //请求缓存
        redisTemplate.opsForValue().set(key, jsonObject.toJSONString(), 60*60*24,
                TimeUnit.SECONDS);
        return jsonObject;
    }

    /**
     * 查询某酒店的直连/入离报价（国内+海外） -- oracle入库逻辑
     * @param checkModel
     * @param parasJSON
     * @return
     */
    private JSONObject getHotelIopriceOracle(JSONObject checkModel, JSONObject parasJSON, String IsInte)
    {
        long startTime=System.currentTimeMillis();
        //返回参数
        JSONObject resultModel = new JSONObject(16);
        JSONObject responseStatus = checkModel.getJSONObject("responseStatus");
        JSONObject SearchCandidate =  checkModel.getJSONObject("SearchCandidate");
        String HotelID = checkModel.getString("HotelID");
        Date Start = checkModel.getDate("Start"), End = checkModel.getDate("End");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Start);
        calendar.add(Calendar.DAY_OF_MONTH, 28);
        if (calendar.getTime().compareTo(End) == -1 || Start.compareTo(new Date()) <= 0) {
            resultModel.put("ResponseStatus", responseStatus);
            return resultModel;
        }
        JSONObject Occupancy = SearchCandidate.getJSONObject("Occupancy");
        //获取报价信息
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("HotelID", HotelID);
        if (Occupancy != null && !"".equals(Occupancy)) {
            if (Occupancy.getInteger("Adult") != null && !"".equals(Occupancy.getInteger("Adult"))) {
                paramMap.put("Adult", Occupancy.getInteger("Adult"));
            }
        }
        //判断redis缓存中是否存在对应数据
        List<BHotelDailyPrices> bHotelDailyPricesList = new ArrayList<>();
        List<RoomPriceItems> roomPriceItemsList = new ArrayList<>();
        String redisKey = HotelID + "_QuoteCache";
        Object object = redisTemplate.opsForValue().get(redisKey);
        if ( object == null || "".equals(object) )
        {
            //数据库读取数据或直接请求携程获取数据
            //可用房型列表
            List<Integer> roomIdList = bHotelRoomPriceInfosMapper.getRoomIdList(paramMap);
            //判断当前数据库中是否存在对应房型
            if (roomIdList == null || roomIdList.size() <= 0) {
                logger.info("请求携程");
                //库中不存在,直接请求携程
                JSONObject jsonObject = hotelIopriceCtrip(parasJSON, HotelID, IsInte);
                return jsonObject;
            }
            else {
                logger.info("查询数据库");
                //库中存在
                //获取物理房型信息 获取售卖房型信息
                List<RoomPriceItemsDto> roomPriceItemsDtoList = bHotelRoomPriceInfosMapper.getRoomPriceItemsList(paramMap);
                roomPriceItemsList = JSONArray.parseArray(JSONObject.toJSONString(roomPriceItemsDtoList
                        , SerializerFeature.WriteMapNullValue
                        , SerializerFeature.WriteNullListAsEmpty
                        , SerializerFeature.WriteNullStringAsEmpty
                        , SerializerFeature.WriteNullBooleanAsFalse
                ), RoomPriceItems.class);
                //获取日报价信息
                //获取所有日报价列表
                bHotelDailyPricesList = bHotelDailyPricesMapper.selectBatchIds(roomIdList);

                Map<String, Object> paramMaps = new HashMap<>(16);
                //触发缓存
                paramMaps.put("redisKey", HotelID + "_hotelIopriceCache");
                adminFeign.messageQueue(paramMaps);
            }
        }
        else
        {
            logger.info("查询缓存");
            //缓存中读取数据
            JSONObject jsonObject = JSONObject.parseObject(object.toString());
            roomPriceItemsList = JSONArray.parseArray(jsonObject.getString("houseType"), RoomPriceItems.class);
            bHotelDailyPricesList = JSONArray.parseArray(jsonObject.getString("quote"), BHotelDailyPrices.class);
        }

        //日报价列表,通过房型id放入map,更好读取
        Map<Integer, BHotelDailyPrices> bHotelDailyPricesMap = new HashMap<>(16);
        for (BHotelDailyPrices bHotelDailyPrices : bHotelDailyPricesList) {
            bHotelDailyPricesMap.put(bHotelDailyPrices.getRoomId(), bHotelDailyPrices);
        }
        for (RoomPriceItems roomPriceItems : roomPriceItemsList) {
            List<RoomPriceItems.RoomPriceInfosBean> RoomPriceInfos = roomPriceItems.getRoomPriceInfos();
            for (RoomPriceItems.RoomPriceInfosBean roomPriceInfosBean : RoomPriceInfos) {
                //处理取消策略信息 时间
                for (RoomPriceItems.RoomPriceInfosBean.CancelPolicyInfosBean cancelPolicyInfosBean : roomPriceInfosBean.getCancelPolicyInfos())
                {
                    cancelPolicyInfosBean.setStart(sdf.format(Start));
                    cancelPolicyInfosBean.setEnd(sdf.format(End));
                }
                //处理保留时间
                RoomPriceItems.RoomPriceInfosBean.ReserveTimeLimitInfoBean reserveTimeLimitInfoBean =
                        roomPriceInfosBean.getReserveTimeLimitInfo();
                Calendar sa = Calendar.getInstance();
                sa.setTime(Start);
                sa.add(Calendar.DAY_OF_MONTH, 1);
                reserveTimeLimitInfoBean.setLatestReserveTime(sdf.format(sa.getTime()));
                roomPriceInfosBean.setReserveTimeLimitInfo(reserveTimeLimitInfoBean);
                List<RoomPriceItems.RoomPriceInfosBean.PriceInfosBean> priceInfosBeanDtoList = roomPriceInfosBean.getPriceInfos();
                for (RoomPriceItems.RoomPriceInfosBean.PriceInfosBean priceInfosBean : priceInfosBeanDtoList) {
                    //判断请求时间是否在数据库中对应月份中
                    BHotelDailyPrices bHotelDailyPrices = bHotelDailyPricesMap.get(roomPriceInfosBean.getRoomID());
                    if (bHotelDailyPrices == null || "".equals(bHotelDailyPrices)) {
                        continue;
                    }
                    List<RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean> DailyPrices = getDailyPricesTimeWithin(Start, End, bHotelDailyPrices);
                    if ( DailyPrices == null || DailyPrices.size() <= 0 )
                    {
                        //当库中数据缺失过多时主动触发携程补充数据
//                        logger.info("数据缺失转发携程");
//                        JSONObject jsonObject = hotelIopriceCtrip(parasJSON, HotelID);
//                        long endTime=System.currentTimeMillis();
//                        logger.info("主程序运行时间： "+(endTime-startTime) + "毫秒");
//                        return jsonObject;
                        continue;
                    }
                    priceInfosBean.setDailyPrices(DailyPrices);
                }
            }
        }
        JSONArray RoomPriceItemsJson = JSONArray.parseArray(JSONObject.toJSONString(roomPriceItemsList
                , SerializerFeature.WriteMapNullValue
                , SerializerFeature.WriteNullListAsEmpty
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.WriteNullBooleanAsFalse)
        );

        if ( RoomPriceItemsJson == null || RoomPriceItemsJson.size() <= 0 )
        {
            String md5ParasMap = MD5Util.encrypt(parasJSON.toJSONString());
            redisTemplate.opsForValue().set("hotelIoprice_" + md5ParasMap, RoomPriceItemsJson.toJSONString(), 60*5,
                    TimeUnit.SECONDS);
        }

        resultModel.put("ResponseStatus", responseStatus);
        resultModel.put("RoomPriceItems", RoomPriceItemsJson);
        long endTime=System.currentTimeMillis();
        logger.info("主程序运行时间： "+(endTime-startTime) + "毫秒");
        return resultModel;
    }

    /**
     * @Description: 获取时间区间段内的字段报价
     * @Author: 王俊文
     * @Date: 19-7-23 上午11:49
     * @Param: [Start, End, bHotelDailyPrices]
     * @returns: java.util.List<java.lang.String>
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-23 上午11:49          1.0        获取时间区间段内的字段报价
     */
    private List<RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean> getDailyPricesTimeWithin(Date Start, Date End, BHotelDailyPrices bHotelDailyPrices) {
        List<RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean> DailyPrices = new ArrayList<>();
        while (Start.compareTo(End) == -1) {
            Calendar A = Calendar.getInstance();
            A.setTime(Start);

            if (sdfm.format(Start).compareTo(bHotelDailyPrices.getCurrentMonth()) == 0) {
                boolean sign = false;
                //当前月
                switch (sdfd.format(Start)) {
                    case "01": {
                        if (bHotelDailyPrices.getCurrentMonth1() != null && !"".equals(bHotelDailyPrices.getCurrentMonth1())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth1(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "02": {
                        if (bHotelDailyPrices.getCurrentMonth2() != null && !"".equals(bHotelDailyPrices.getCurrentMonth2())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth2(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "03": {
                        if (bHotelDailyPrices.getCurrentMonth3() != null && !"".equals(bHotelDailyPrices.getCurrentMonth3())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth3(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "04": {
                        if (bHotelDailyPrices.getCurrentMonth4() != null && !"".equals(bHotelDailyPrices.getCurrentMonth4())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth4(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "05": {
                        if (bHotelDailyPrices.getCurrentMonth5() != null && !"".equals(bHotelDailyPrices.getCurrentMonth5())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth5(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "06": {
                        if (bHotelDailyPrices.getCurrentMonth6() != null && !"".equals(bHotelDailyPrices.getCurrentMonth6())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth6(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "07": {
                        if (bHotelDailyPrices.getCurrentMonth7() != null && !"".equals(bHotelDailyPrices.getCurrentMonth7())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth7(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "08": {
                        if (bHotelDailyPrices.getCurrentMonth8() != null && !"".equals(bHotelDailyPrices.getCurrentMonth8())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth8(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "09": {
                        if (bHotelDailyPrices.getCurrentMonth9() != null && !"".equals(bHotelDailyPrices.getCurrentMonth9())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth9(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "10": {
                        if (bHotelDailyPrices.getCurrentMonth10() != null && !"".equals(bHotelDailyPrices.getCurrentMonth10())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth10(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "11": {
                        if (bHotelDailyPrices.getCurrentMonth11() != null && !"".equals(bHotelDailyPrices.getCurrentMonth11())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth11(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "12": {
                        if (bHotelDailyPrices.getCurrentMonth12() != null && !"".equals(bHotelDailyPrices.getCurrentMonth12())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth12(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "13": {
                        if (bHotelDailyPrices.getCurrentMonth13() != null && !"".equals(bHotelDailyPrices.getCurrentMonth13())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth13(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "14": {
                        if (bHotelDailyPrices.getCurrentMonth14() != null && !"".equals(bHotelDailyPrices.getCurrentMonth14())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth14(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                    }
                    case "15": {
                        if (bHotelDailyPrices.getCurrentMonth15() != null && !"".equals(bHotelDailyPrices.getCurrentMonth15())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth15(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "16": {
                        if (bHotelDailyPrices.getCurrentMonth16() != null && !"".equals(bHotelDailyPrices.getCurrentMonth16())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth16(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "17": {
                        if (bHotelDailyPrices.getCurrentMonth17() != null && !"".equals(bHotelDailyPrices.getCurrentMonth17())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth17(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "18": {
                        if (bHotelDailyPrices.getCurrentMonth18() != null && !"".equals(bHotelDailyPrices.getCurrentMonth18())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth18(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "19": {
                        if (bHotelDailyPrices.getCurrentMonth19() != null && !"".equals(bHotelDailyPrices.getCurrentMonth19())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth19(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "20": {
                        if (bHotelDailyPrices.getCurrentMonth20() != null && !"".equals(bHotelDailyPrices.getCurrentMonth20())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth20(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "21": {
                        if (bHotelDailyPrices.getCurrentMonth21() != null && !"".equals(bHotelDailyPrices.getCurrentMonth21())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth21(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "22": {
                        if (bHotelDailyPrices.getCurrentMonth22() != null && !"".equals(bHotelDailyPrices.getCurrentMonth22())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth22(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "23": {
                        if (bHotelDailyPrices.getCurrentMonth23() != null && !"".equals(bHotelDailyPrices.getCurrentMonth23())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth23(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "24": {
                        if (bHotelDailyPrices.getCurrentMonth24() != null && !"".equals(bHotelDailyPrices.getCurrentMonth24())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth24(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "25": {
                        if (bHotelDailyPrices.getCurrentMonth25() != null && !"".equals(bHotelDailyPrices.getCurrentMonth25())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth25(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "26": {
                        if (bHotelDailyPrices.getCurrentMonth26() != null && !"".equals(bHotelDailyPrices.getCurrentMonth26())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth26(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "27": {
                        if (bHotelDailyPrices.getCurrentMonth27() != null && !"".equals(bHotelDailyPrices.getCurrentMonth27())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth27(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "28": {
                        if (bHotelDailyPrices.getCurrentMonth28() != null && !"".equals(bHotelDailyPrices.getCurrentMonth28())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth28(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "29": {
                        if (bHotelDailyPrices.getCurrentMonth29() != null && !"".equals(bHotelDailyPrices.getCurrentMonth29())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth29(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "30": {
                        if (bHotelDailyPrices.getCurrentMonth30() != null && !"".equals(bHotelDailyPrices.getCurrentMonth30())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth30(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "31": {
                        if (bHotelDailyPrices.getCurrentMonth31() != null && !"".equals(bHotelDailyPrices.getCurrentMonth31())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth31(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    default: {
                        //处理生效日期
                        if (bHotelDailyPrices.getCurrentMonth1() != null && !"".equals(bHotelDailyPrices.getCurrentMonth1())) {
                            JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth1());
                            daily.put("EffectiveDate", sdf.format(Start));
                            DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        } else if (bHotelDailyPrices.getCurrentMonth5() != null && !"".equals(bHotelDailyPrices.getCurrentMonth5())) {
                            JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth5());
                            daily.put("EffectiveDate", sdf.format(Start));
                            DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }else if (bHotelDailyPrices.getCurrentMonth10() != null && !"".equals(bHotelDailyPrices.getCurrentMonth10())) {
                            JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth10());
                            daily.put("EffectiveDate", sdf.format(Start));
                            DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }else if (bHotelDailyPrices.getCurrentMonth20() != null && !"".equals(bHotelDailyPrices.getCurrentMonth20())) {
                            JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth20());
                            daily.put("EffectiveDate", sdf.format(Start));
                            DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }else if (bHotelDailyPrices.getCurrentMonth25() != null && !"".equals(bHotelDailyPrices.getCurrentMonth25())) {
                            JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth25());
                            daily.put("EffectiveDate", sdf.format(Start));
                            DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                }
                if (!sign) {
                    //处理生效日期
                    if (bHotelDailyPrices.getCurrentMonth1() != null && !"".equals(bHotelDailyPrices.getCurrentMonth1())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth1());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getCurrentMonth5() != null && !"".equals(bHotelDailyPrices.getCurrentMonth5())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth5());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    }else if (bHotelDailyPrices.getCurrentMonth15() != null && !"".equals(bHotelDailyPrices.getCurrentMonth15())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth15());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getCurrentMonth10() != null && !"".equals(bHotelDailyPrices.getCurrentMonth10())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth10());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getCurrentMonth20() != null && !"".equals(bHotelDailyPrices.getCurrentMonth20())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth20());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getCurrentMonth25() != null && !"".equals(bHotelDailyPrices.getCurrentMonth25())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth25());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    }
                }
                if ( !sign )
                {
                    return null;
                }
            } else if ((sdfm.format(Start).compareTo(bHotelDailyPrices.getNextMonth()) == 0)) {
                boolean sign = false;
                //下一个月
                switch (sdfd.format(Start)) {
                    case "01": {
                        if (bHotelDailyPrices.getNextMonth1() != null && !"".equals(bHotelDailyPrices.getNextMonth1())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth1(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "02": {
                        if (bHotelDailyPrices.getNextMonth2() != null && !"".equals(bHotelDailyPrices.getNextMonth2())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth2(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "03": {
                        if (bHotelDailyPrices.getNextMonth3() != null && !"".equals(bHotelDailyPrices.getNextMonth3())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth3(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "04": {
                        if (bHotelDailyPrices.getNextMonth4() != null && !"".equals(bHotelDailyPrices.getNextMonth4())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth4(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "05": {
                        if (bHotelDailyPrices.getNextMonth5() != null && !"".equals(bHotelDailyPrices.getNextMonth5())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth5(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "06": {
                        if (bHotelDailyPrices.getNextMonth6() != null && !"".equals(bHotelDailyPrices.getNextMonth6())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth6(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "07": {
                        if (bHotelDailyPrices.getNextMonth7() != null && !"".equals(bHotelDailyPrices.getNextMonth7())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth7(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "08": {
                        if (bHotelDailyPrices.getNextMonth8() != null && !"".equals(bHotelDailyPrices.getNextMonth8())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth8(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "09": {
                        if (bHotelDailyPrices.getNextMonth9() != null && !"".equals(bHotelDailyPrices.getNextMonth9())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth9(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "10": {
                        if (bHotelDailyPrices.getNextMonth10() != null && !"".equals(bHotelDailyPrices.getNextMonth10())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth10(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "11": {
                        if (bHotelDailyPrices.getNextMonth11() != null && !"".equals(bHotelDailyPrices.getNextMonth11())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth11(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "12": {
                        if (bHotelDailyPrices.getNextMonth12() != null && !"".equals(bHotelDailyPrices.getNextMonth12())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth12(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "13": {
                        if (bHotelDailyPrices.getNextMonth13() != null && !"".equals(bHotelDailyPrices.getNextMonth13())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth13(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "14": {
                        if (bHotelDailyPrices.getNextMonth14() != null && !"".equals(bHotelDailyPrices.getNextMonth14())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth14(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                    }
                    case "15": {
                        if (bHotelDailyPrices.getNextMonth15() != null && !"".equals(bHotelDailyPrices.getNextMonth15())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth15(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "16": {
                        if (bHotelDailyPrices.getNextMonth16() != null && !"".equals(bHotelDailyPrices.getNextMonth16())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth16(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "17": {
                        if (bHotelDailyPrices.getNextMonth17() != null && !"".equals(bHotelDailyPrices.getNextMonth17())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth17(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "18": {
                        if (bHotelDailyPrices.getNextMonth18() != null && !"".equals(bHotelDailyPrices.getNextMonth18())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth18(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "19": {
                        if (bHotelDailyPrices.getNextMonth19() != null && !"".equals(bHotelDailyPrices.getNextMonth19())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth19(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "20": {
                        if (bHotelDailyPrices.getNextMonth20() != null && !"".equals(bHotelDailyPrices.getNextMonth20())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth20(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "21": {
                        if (bHotelDailyPrices.getNextMonth21() != null && !"".equals(bHotelDailyPrices.getNextMonth21())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth21(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "22": {
                        if (bHotelDailyPrices.getNextMonth22() != null && !"".equals(bHotelDailyPrices.getNextMonth22())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth22(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "23": {
                        if (bHotelDailyPrices.getNextMonth23() != null && !"".equals(bHotelDailyPrices.getNextMonth23())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth23(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "24": {
                        if (bHotelDailyPrices.getNextMonth24() != null && !"".equals(bHotelDailyPrices.getNextMonth24())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth24(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "25": {
                        if (bHotelDailyPrices.getNextMonth25() != null && !"".equals(bHotelDailyPrices.getNextMonth25())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth25(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "26": {
                        if (bHotelDailyPrices.getNextMonth26() != null && !"".equals(bHotelDailyPrices.getNextMonth26())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth26(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "27": {
                        if (bHotelDailyPrices.getNextMonth27() != null && !"".equals(bHotelDailyPrices.getNextMonth27())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth27(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "28": {
                        if (bHotelDailyPrices.getNextMonth28() != null && !"".equals(bHotelDailyPrices.getNextMonth28())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth28(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "29": {
                        if (bHotelDailyPrices.getNextMonth29() != null && !"".equals(bHotelDailyPrices.getNextMonth29())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth29(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "30": {
                        if (bHotelDailyPrices.getNextMonth30() != null && !"".equals(bHotelDailyPrices.getNextMonth30())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth30(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    case "31": {
                        if (bHotelDailyPrices.getNextMonth31() != null && !"".equals(bHotelDailyPrices.getNextMonth31())) {
                            DailyPrices.add(JSONObject.parseObject(bHotelDailyPrices.getNextMonth31(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                    default: {
                        //处理生效日期
                        if (bHotelDailyPrices.getNextMonth1() != null && !"".equals(bHotelDailyPrices.getNextMonth1())) {
                            JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth1());
                            daily.put("EffectiveDate", sdf.format(Start));
                            DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        } else if (bHotelDailyPrices.getNextMonth10() != null && !"".equals(bHotelDailyPrices.getNextMonth10())) {
                            JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth10());
                            daily.put("EffectiveDate", sdf.format(Start));
                            DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }else if (bHotelDailyPrices.getNextMonth15() != null && !"".equals(bHotelDailyPrices.getNextMonth15())) {
                            JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth15());
                            daily.put("EffectiveDate", sdf.format(Start));
                            DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }else if (bHotelDailyPrices.getNextMonth20() != null && !"".equals(bHotelDailyPrices.getNextMonth20())) {
                            JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth20());
                            daily.put("EffectiveDate", sdf.format(Start));
                            DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                    RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                            sign = true;
                        }
                        break;
                    }
                }
                if (!sign) {
                    //处理生效日期
                    if (bHotelDailyPrices.getNextMonth1() != null && !"".equals(bHotelDailyPrices.getNextMonth1())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth1());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getNextMonth5() != null && !"".equals(bHotelDailyPrices.getNextMonth5())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth5());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getNextMonth15() != null && !"".equals(bHotelDailyPrices.getNextMonth15())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth15());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getNextMonth10() != null && !"".equals(bHotelDailyPrices.getNextMonth10())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth10());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getNextMonth20() != null && !"".equals(bHotelDailyPrices.getNextMonth20())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth20());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getNextMonth25() != null && !"".equals(bHotelDailyPrices.getNextMonth25())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth25());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    }
                }
                if ( !sign )
                {
                    return null;
                }
            } else {
                boolean sign = false;
                //处理生效日期
                if (bHotelDailyPrices.getNextMonth1() != null && !"".equals(bHotelDailyPrices.getNextMonth1()))
                {
                    JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth1());
                    daily.put("EffectiveDate", sdf.format(Start));
                    DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                            RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                    sign = true;
                } else if (bHotelDailyPrices.getNextMonth15() != null && !"".equals(bHotelDailyPrices.getNextMonth15())) {
                    JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth15());
                    daily.put("EffectiveDate", sdf.format(Start));
                    DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                            RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                    sign = true;
                } else if (bHotelDailyPrices.getNextMonth10() != null && !"".equals(bHotelDailyPrices.getNextMonth10())) {
                    JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth10());
                    daily.put("EffectiveDate", sdf.format(Start));
                    DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                            RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                    sign = true;
                } else if (bHotelDailyPrices.getNextMonth20() != null && !"".equals(bHotelDailyPrices.getNextMonth20())) {
                    JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth20());
                    daily.put("EffectiveDate", sdf.format(Start));
                    DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                            RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                    sign = true;
                } else if (bHotelDailyPrices.getNextMonth25() != null && !"".equals(bHotelDailyPrices.getNextMonth25())) {
                    JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getNextMonth25());
                    daily.put("EffectiveDate", sdf.format(Start));
                    DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                            RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                    sign = true;
                }
                if (!sign)
                {
                    //处理生效日期
                    if (bHotelDailyPrices.getCurrentMonth1() != null && !"".equals(bHotelDailyPrices.getCurrentMonth1())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth1());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getCurrentMonth5() != null && !"".equals(bHotelDailyPrices.getCurrentMonth5())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth5());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getCurrentMonth10() != null && !"".equals(bHotelDailyPrices.getCurrentMonth10())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth10());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getCurrentMonth15() != null && !"".equals(bHotelDailyPrices.getCurrentMonth15())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth15());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getCurrentMonth20() != null && !"".equals(bHotelDailyPrices.getCurrentMonth20())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth20());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    } else if (bHotelDailyPrices.getCurrentMonth25() != null && !"".equals(bHotelDailyPrices.getCurrentMonth25())) {
                        JSONObject daily = JSONObject.parseObject(bHotelDailyPrices.getCurrentMonth25());
                        daily.put("EffectiveDate", sdf.format(Start));
                        DailyPrices.add(JSONObject.parseObject(daily.toJSONString(),
                                RoomPriceItems.RoomPriceInfosBean.PriceInfosBean.DailyPricesBean.class));
                        sign = true;
                    }
                }
                if ( !sign )
                {
                    return null;
                }
            }

            //累加开始时间 直到其超过结束时间
            A.add(Calendar.DAY_OF_MONTH, 1);
            Start = A.getTime();
        }
        return DailyPrices;
    }

    /**
     * @Description: 直连携程获取入离报价并根据情况决定通知入库
     * @Author: 王俊文
     * @Date: 19-7-24 上午2:36
     * @Param: [jsonObject, HotelID, Start, End, sign]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-24 上午2:36          1.0          直连携程获取入离报价并根据情况决定通知入库
     */
    private JSONObject hotelIopriceCtrip(JSONObject jsonObject, String HotelID, String IsInte) {
        String icode = "";
        if (Enviroment.IS_RELEASE_ENV) {
            icode = Enviroment.HOTEL_IOPRICE.getValue();
        } else {
            icode = Enviroment.TEST_HOTEL_IOPRICE.getValue();
        }
        //弗恩测试  正式情况下注释掉
        icode = "hotel.ioprice";
        JSONObject returnJSONObject = new JSONObject(16);
        if ( "0".equals(IsInte) )
            {
                jsonObject = getResponse(jsonObject, icode, "6000");
            }else if ( "1".equals(IsInte) )
            {
                jsonObject = getResponseOverseas(jsonObject, icode, "6000");
            }
        if ( CtripApiErrorCode.RateLimit.getCode().equals(returnJSONObject.get("ErrCode")) )
        {
            return returnJSONObject;
        }
        //通知消息队列补录相关直连报价信息
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("redisKey", HotelID + "_hoteliopriceupdate");
        adminFeign.messageQueue(paramMap);
        return returnJSONObject;
    }

    /**
     * @Description: 直连报价--验价
     * @Author: 王俊文
     * @Date: 19-7-24 上午2:39
     * @Param: [parasJSON]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-24 上午2:39          1.0     直连报价--验价
     */
    @Override
    public JSONObject hotelIpriceTestPrice(JSONObject checkModel, JSONObject parasJSON, String IsInte)
    {
        //返回参数
        JSONObject resultModel = new JSONObject(16);
        String RoomTypeID = checkModel.getString("RoomTypeID");
        String RoomID = checkModel.getString("RoomID");
        String HotelID = checkModel.getString("HotelID");
        Date Start = checkModel.getDate("Start"), End = checkModel.getDate("End");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Start);
        calendar.add(Calendar.DAY_OF_MONTH, 28);
        if (calendar.getTime().compareTo(End) == -1 || Start.compareTo(new Date()) == -1) {
            resultModel.put("ResponseStatus", checkModel.get("responseStatus"));
            return resultModel;
        }
        String icode = "";
        if (Enviroment.IS_RELEASE_ENV) {
            icode = Enviroment.HOTEL_IOPRICE.getValue();
        } else {
            icode = Enviroment.TEST_HOTEL_IOPRICE.getValue();
        }
        //弗恩测试  正式情况下注释掉
        icode = "hotel.ioprice";
        JSONObject returnJSONObject = new JSONObject(16);
        if ( "0".equals(IsInte) )
        {
            returnJSONObject = getResponse(parasJSON, icode, "6000");
        }else if ( "1".equals(IsInte) )
        {
            returnJSONObject = getResponseOverseas(parasJSON, icode, "6000");
        }
        if ( CtripApiErrorCode.RateLimit.getCode().equals(returnJSONObject.get("ErrCode")) )
        {
            return returnJSONObject;
        }
        if (returnJSONObject.get("ResponseStatus") != null && !"".equals(returnJSONObject.get("ResponseStatus"))) {
            JSONObject ResponseStatuss = returnJSONObject.getJSONObject("ResponseStatus");
            if (ResponseStatuss.getString("Ack") != null && "Success".equals(ResponseStatuss.getString("Ack"))) {
                //解析数据体
                List<RoomPriceItems> roomPriceItemsList = JSONArray.parseArray(returnJSONObject.getJSONArray(
                        "RoomPriceItems").toJSONString(), RoomPriceItems.class);

                List<RoomPriceItems> roomPriceItemsListReturn = new ArrayList<>();
                if ( RoomTypeID != null && !"".equals(RoomTypeID) )
                {
                    for ( RoomPriceItems roomPriceItems : roomPriceItemsList )
                    {
                        if ( RoomTypeID.compareTo(String.valueOf(roomPriceItems.getRoomTypeID())) == 0 )
                        {
                            if ( RoomID != null && !"".equals(RoomID) )
                            {
                                List<RoomPriceItems.RoomPriceInfosBean> roomPriceInfosBeanList = new ArrayList<>();
                                for ( RoomPriceItems.RoomPriceInfosBean roomPriceInfosBean : roomPriceItems.getRoomPriceInfos() )
                                {
                                    if ( RoomID.compareTo(String.valueOf(roomPriceInfosBean.getRoomID())) == 0 )
                                    {
                                        roomPriceInfosBeanList.add(roomPriceInfosBean);
                                    }
                                }
                                roomPriceItems.setRoomPriceInfos(roomPriceInfosBeanList);
                            }
                            roomPriceItemsListReturn.add(roomPriceItems);
                        }
                    }
                    returnJSONObject.put("RoomPriceItems",JSONArray.parseArray(JSONObject.toJSONString(roomPriceItemsListReturn
                                    , SerializerFeature.WriteMapNullValue
                                    , SerializerFeature.WriteNullListAsEmpty
                                    , SerializerFeature.WriteNullStringAsEmpty
                                    , SerializerFeature.WriteNullBooleanAsFalse)
                            ));
                }
                    Map<String, Object> paramMap = new HashMap<>(16);
                    paramMap.put("redisKey", HotelID + "_hoteliopriceupdate");
                    adminFeign.messageQueue(paramMap);
            }
        }
        return returnJSONObject;
    }

    /**
     * @Description: 统一远程请求--国内
     * @Author: 王俊文
     * @Date: 2019/8/30 上午9:17
     * @Param: [param, icode, inRedisValue]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/8/30 上午9:17          1.0          统一远程请求
     */
    private JSONObject getResponse(JSONObject param, String icode, String inRedisValue)
    {
        Long sleepS = (Long) Math.round(1+Math.random()*(50-1+1));
        try {
            Thread.sleep(sleepS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject resultModel = new JSONObject(16);
        boolean sign = false;
        Long value = 0L;

        String key = icode;
        boolean lock = commonRedisHelper.lock(key);

        if (lock)
        {
            // 执行逻辑操作
            sign = true;
        } else {
            // 设置失败次数计数器, 当到达3次时, 返回失败
            int failCount = 1;
            while(failCount <= 3)
            {
                // 等待100ms重试
                try {
                    Thread.sleep(50l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (commonRedisHelper.lock(key))
                {
                   // 执行逻辑操作
                    sign = true;
                    break;
                }else{
                    failCount ++;
                }
            }
            logger.info("现在创建的人太多了, 请稍等再试");
        }

        if (sign)
        {
            redisTemplate.opsForValue().setIfAbsent(key, inRedisValue, 60,
                TimeUnit.SECONDS);
            value = redisTemplate.opsForValue().increment(key, -1);
            if ( value.compareTo(0L) <= 0 )
            {
                commonRedisHelper.delete(key);
                resultModel.put("ErrCode", CtripApiErrorCode.RateLimit.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.RateLimit.getName());
            }else
            {
                commonRedisHelper.delete(key);
                RestTemplate restTemplate = new RestTemplate();
                String token  = stringRedisTemplate.opsForValue().get("token");
                JSONObject body;
                BaseApiReq baseApiReq = Enviroment.getBaseApiReq("0");
//                String url = Enviroment.API_COMMON_URL.getValue();
//                url = url.replace("{app_path}", baseApiReq.getUrl())
//                        .replace("{aid}", baseApiReq.getAID())
//                        .replace("{sid}", baseApiReq.getSID())
//                        .replace("{key}",  baseApiReq.getKEY())
//                        .replace("{format}", baseApiReq.getFormat())
//                        .replace("{GUID}", baseApiReq.getUUID())
//                        .replace("{Access_Token}", token)
//                        .replace("{ICODE}", icode);

                //测试时使用  调用弗恩
                String url = "http://{app_path}/OpenService/FenHotelService" +
                        ".ashx?AID={aid}&SID={sid}&ICODE={ICODE}&UUID={GUID}&Token={Access_Token}&mode=1&format={format}";
                url = url.replace("{app_path}", "hotel.tianxiafen.com:82")
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
                return body;
            }
        }else
        {
            resultModel.put("ErrCode", CtripApiErrorCode.Timestampistimeout.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.Timestampistimeout.getName());
        }
        return resultModel;
    }

    /**
     * @Description: 统一远程请求--国际
     * @Author: 王俊文
     * @Date: 2019/9/12 上午10:24
     * @Param: [param, icode, inRedisValue]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/9/12 上午10:24          1.0          统一远程请求--国际
     */
    private JSONObject getResponseOverseas(JSONObject param, String icode, String inRedisValue)
    {
        Long sleepS = (Long) Math.round(1+Math.random()*(50-1+1));
        try {
            Thread.sleep(sleepS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject resultModel = new JSONObject(16);
        boolean sign = false;
        Long value = 0L;

        String key = icode + "_overseas";
        boolean lock = commonRedisHelper.lock(key);

        if (lock)
        {
            // 执行逻辑操作
            sign = true;
        } else {
            // 设置失败次数计数器, 当到达3次时, 返回失败
            int failCount = 1;
            while(failCount <= 3)
            {
                // 等待100ms重试
                try {
                    Thread.sleep(50l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (commonRedisHelper.lock(key))
                {
                   // 执行逻辑操作
                    sign = true;
                    break;
                }else{
                    failCount ++;
                }
            }
            logger.info("现在创建的人太多了, 请稍等再试");
        }

        if (sign)
        {
            redisTemplate.opsForValue().setIfAbsent(key, inRedisValue, 60,
                TimeUnit.SECONDS);
            value = redisTemplate.opsForValue().increment(key, -1);
            if ( value.compareTo(0L) <= 0 )
            {
                commonRedisHelper.delete(key);
                resultModel.put("ErrCode", CtripApiErrorCode.RateLimit.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.RateLimit.getName());
            }else
            {
                commonRedisHelper.delete(key);
                RestTemplate restTemplate = new RestTemplate();
                String token  = stringRedisTemplate.opsForValue().get("overseasToken");
                JSONObject body;
                BaseApiReq baseApiReq = Enviroment.getBaseApiReq("1");
//                String url = Enviroment.API_COMMON_URL.getValue();
//                url = url.replace("{app_path}", baseApiReq.getUrl())
//                        .replace("{aid}", baseApiReq.getAID())
//                        .replace("{sid}", baseApiReq.getSID())
//                        .replace("{key}",  baseApiReq.getKEY())
//                        .replace("{format}", baseApiReq.getFormat())
//                        .replace("{GUID}", baseApiReq.getUUID())
//                        .replace("{Access_Token}", token)
//                        .replace("{ICODE}", icode);

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
                return body;
            }
        }else
        {
            resultModel.put("ErrCode", CtripApiErrorCode.Timestampistimeout.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.Timestampistimeout.getName());
        }
        return resultModel;
    }

        /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/2 15:01
     * @Description: 酒店清单接口
     */
    @Override
    public JSONObject getHotelList(JSONObject param, String IsInte) {
        //弗恩测试  正式情况下注释掉
        String icode = "hotel.list";
        JSONObject returnJSONObject = new JSONObject(16);
        if ( "0".equals(IsInte) )
        {
            returnJSONObject = getResponse(param, icode, "60");
        }else if ( "1".equals(IsInte) )
        {
            returnJSONObject = getResponseOverseas(param, icode, "60");
        }
        return returnJSONObject;
    }
    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/2 15:04
     * @Description: 酒店静态信息详情接口
     */
    @Override
    public JSONObject getHotelDetail(JSONObject param, String IsInte) {
        //弗恩测试  正式情况下注释掉
        String icode = "hotel.detail";
        JSONObject returnJSONObject = new JSONObject(16);
        if ( "0".equals(IsInte) )
        {
            returnJSONObject = getResponse(param, icode, "600");
        }else if ( "1".equals(IsInte) )
        {
            returnJSONObject = getResponseOverseas(param, icode, "600");
        }
        return returnJSONObject;
    }

    @Override
    public JSONObject getRoomtypeList(JSONObject param, String IsInte) {
        //弗恩测试  正式情况下注释掉
        String icode = "hotel.roomtype.list";
        JSONObject returnJSONObject = new JSONObject(16);
        if ( "0".equals(IsInte) )
        {
            returnJSONObject = getResponse(param, icode, "600");
        }else if ( "1".equals(IsInte) )
        {
            returnJSONObject = getResponseOverseas(param, icode, "600");
        }
        return returnJSONObject;
    }
}