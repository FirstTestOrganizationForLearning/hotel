package com.fntx.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fntx.admin.service.IBHotelBasepriceService;
import com.fntx.admin.service.IBHotelDetailService;
import com.fntx.admin.service.IHotelBasepriceService;
import com.fntx.admin.service.IHotelStaticInformationService;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.dao.BHotelCityMapper;
import com.fntx.common.dao.BSaleRoomMapper;
import com.fntx.common.domain.BHotelBaseprice;
import com.fntx.common.domain.BHotelCity;
import com.fntx.common.domain.BHotelDetail;
import com.fntx.common.domain.BaseApiReq;
import com.fntx.common.domain.dto.*;
import com.github.pagehelper.PageHelper;
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
 * @ClassName: HotelBasepriceServiceImpl
 * @Author: 王俊文
 * @Date: 19-8-9 下午3:17
 * @Description: 城市入离起价--停用
 */
@Service
public class HotelBasepriceServiceImpl implements IHotelBasepriceService
{
    private static Logger logger = LoggerFactory.getLogger(HotelBasepriceServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BHotelCityMapper bHotelCityMapper;
    @Autowired
    private IBHotelDetailService ibHotelDetailService;
    @Autowired
    private IBHotelBasepriceService ibHotelBasepriceService;
    @Autowired
    private IHotelStaticInformationService iHotelStaticInformationService;
    @Autowired
    private BSaleRoomMapper bSaleRoomMapper;

    /**
     * @Description: 从房型数据中获取筛选字段的值
     * @Author: 王俊文
     * @Date: 19-8-12 下午4:46
     * @Param: [bHotelBasepriceList]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-12 下午4:46          1.0      从房型数据中获取筛选字段的值
     */
    @Override
    public boolean hotelBasepriceScreening(List<BHotelBaseprice> bHotelBasepriceList)
    {
        for (BHotelBaseprice bHotelBaseprice : bHotelBasepriceList)
        {
            HotelStatusEntityBean hotelStatusEntityBean = new HotelStatusEntityBean();
            hotelStatusEntityBean.setHotel(bHotelBaseprice.getHotelId());
            hotelStatusEntityBean.setMinPriceRoom(bHotelBaseprice.getRoomId());
            //封装起价信息
            //查询对应房型数据
            //查oracle逻辑 start 暂时停用
    //        bHotelBaseprice = saveHotelDetailOracle(hotelStatusEntityBean, bHotelBaseprice);
            //查oracle逻辑 end 暂时停用
            //查mongodb start 暂时启用
            bHotelBaseprice = saveHotelDetailMongodb(hotelStatusEntityBean, bHotelBaseprice);
            //查mongodb end 暂时启用
            //插入或修改对应信息
            boolean sign = false;
            try
            {
                 sign = ibHotelBasepriceService.saveOrUpdate(bHotelBaseprice);
            }catch (Exception sce)
            {
                ibHotelBasepriceService.updateById(bHotelBaseprice);
            }
        }
        return false;
    }

    /**
     * @Description: 获取指定城市的酒店入离起价信息
     * @Author: 王俊文
     * @Date: 19-8-9 下午4:30
     * @Param: [City]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-9 下午4:30          1.0          获取指定城市的酒店入离起价信息
     */
    @Override
    public boolean hotelBaseprice(String City)
    {
        //获取请求相应时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String CheckInDate = sdf.format(new Date());
        Calendar systemDate = Calendar.getInstance();
        try {
            systemDate.setTime(sdf.parse(CheckInDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        systemDate.add(Calendar.DAY_OF_MONTH, 16);
        String CheckOutDate = sdf.format(systemDate.getTime());

        //封装请求体
        JSONObject jsonObject = new JSONObject(16);
        JSONObject SearchTypeEntity = new JSONObject(16);
        JSONObject PublicSearchParameter = new JSONObject(16);
        JSONObject OrderBy = new JSONObject(16);
        //国内,国际 国内城市/酒店(包含港澳台)，则传值“WirelessSearch”； 国外城市/酒店，则传值“OnLineIntlGTASearch”;
        //步长
        SearchTypeEntity.put("HotelCount", 100);
        //入店时间
        PublicSearchParameter.put("CheckInDate", CheckInDate);
        //离店时间
        PublicSearchParameter.put("CheckOutDate", CheckOutDate);
        //排序字段
        OrderBy.put("OrderName", "MinPrice");
        //排序方式
        OrderBy.put("OrderType", "ASC");
        jsonObject.put("OrderBy", OrderBy);
        //获取指定城市信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("CITY_ID", City);
        List<BHotelCity> wirelessSearchHotelCityList = bHotelCityMapper.selectList(queryWrapper);
        try {
            restHotelBaseprice(jsonObject, wirelessSearchHotelCityList, "WirelessSearch", SearchTypeEntity, PublicSearchParameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @Description: 城市入离起价
     * @Author: 王俊文
     * @Date: 19-7-18 上午9:08
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-18 上午9:08          1.0        城市入离起价
     */
    @Override
    public boolean getHotelBaseprice()
    {
        //获取请求相应时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String CheckInDate = sdf.format(new Date());
        Calendar systemDate = Calendar.getInstance();
        try {
            systemDate.setTime(sdf.parse(CheckInDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        systemDate.add(Calendar.DAY_OF_MONTH, 16);
        String CheckOutDate = sdf.format(systemDate.getTime());

        //封装请求体
        JSONObject jsonObject = new JSONObject(16);
        JSONObject SearchTypeEntity = new JSONObject(16);
        JSONObject PublicSearchParameter = new JSONObject(16);
        JSONObject OrderBy = new JSONObject(16);
        //国内,国际 国内城市/酒店(包含港澳台)，则传值“WirelessSearch”； 国外城市/酒店，则传值“OnLineIntlGTASearch”;
        //步长
        SearchTypeEntity.put("HotelCount", 100);
        //入店时间
        PublicSearchParameter.put("CheckInDate", CheckInDate);
        //离店时间
        PublicSearchParameter.put("CheckOutDate", CheckOutDate);
        //排序字段
        OrderBy.put("OrderName", "MinPrice");
        //排序方式
        OrderBy.put("OrderType", "ASC");
        jsonObject.put("OrderBy", OrderBy);
        //获取所有城市信息
        QueryWrapper queryWrapper;
        //获取所有国内城市
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("COUNTRY_ID", 1);
        queryWrapper.eq("COUNTRY_NAME", "中国");
        List<BHotelCity> wirelessSearchHotelCityList = bHotelCityMapper.selectList(queryWrapper);
        try {
            restHotelBaseprice(jsonObject, wirelessSearchHotelCityList, "WirelessSearch", SearchTypeEntity, PublicSearchParameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        queryWrapper = new QueryWrapper();
        queryWrapper.ne("COUNTRY_ID", 1);
        queryWrapper.ne("COUNTRY_NAME", "中国");
        int pageIndex = 1;
        while (true)
        {
            PageHelper.startPage(pageIndex, 2000);
            List<BHotelCity> onLineIntlGTASearchHotelCityList = bHotelCityMapper.selectList(queryWrapper);
            if ( onLineIntlGTASearchHotelCityList.size() < 2000 )
            {
                break;
            }
            try {
                restHotelBaseprice(jsonObject, onLineIntlGTASearchHotelCityList, "OnLineIntlGTASearch", SearchTypeEntity, PublicSearchParameter);
                pageIndex++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * @Description: 入离起价入库--国内
     * @Author: 王俊文
     * @Date: 19-7-18 上午10:30
     * @Param: [
     *                      hotelDataLists--数据列表(包含酒店最低信息,房型最低信息)
     *                      , cityId--城市id
     *                  ]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-18 上午10:30          1.0          入离起价入库
     */
    @Override
    public boolean hotelBasepriceStorageWirelessSearch(String HotelBasepriceDto)
    {
        //解析数据体
        JSONObject returnJSONObject =  JSONObject.parseObject(HotelBasepriceDto);
        List<HotelDataLists> hotelIncrDataDtoList= JSON.parseArray(returnJSONObject.getJSONArray("HotelDataLists").toJSONString(), HotelDataLists.class);
        List<NumEntity> numEntityList = JSON.parseArray(returnJSONObject.getJSONArray("NumEntity").toJSONString(), NumEntity.class);
        NumEntity numEntity = null;
        if ( numEntityList != null && numEntityList.size() == 1 )
        {
            numEntity = numEntityList.get(0);
        }
        //入库
        for ( HotelDataLists hotelBasepriceDto : hotelIncrDataDtoList)
        {
            //查询相关信息并进行相应落库
            saveHotelDetail(hotelBasepriceDto.getHotelStatusEntity(), numEntity, "WirelessSearch");
        }

        return true;
    }

    /**
     * @Description: 入离起价入库--国际
     * @Author: 王俊文
     * @Date: 19-7-18 上午10:30
     * @Param: [
     *                      hotelDataLists--数据列表(包含酒店最低信息,房型最低信息)
     *                      , cityId--城市id
     *                  ]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-18 上午10:30          1.0          入离起价入库
     */
    @Override
    public boolean hotelBasepriceStorageOnLineIntlGTASearch(String HotelBasepriceDto)
    {
        //解析数据体
        if ( HotelBasepriceDto == null || "".equals(HotelBasepriceDto) )
        {
            return true;
        }
        JSONObject returnJSONObject =  JSONObject.parseObject(HotelBasepriceDto);
        List<HotelDataLists> hotelIncrDataDtoList= JSON.parseArray(returnJSONObject.getJSONArray("HotelDataLists").toJSONString(), HotelDataLists.class);
        List<NumEntity> numEntityList = JSON.parseArray(returnJSONObject.getJSONArray("NumEntity").toJSONString(), NumEntity.class);
        NumEntity numEntity = null;
        if ( numEntityList != null && numEntityList.size() == 1 )
        {
            numEntity = numEntityList.get(0);
        }
        //获取额外条件信息
        for ( HotelDataLists hotelBasepriceDto : hotelIncrDataDtoList)
        {
            //查询相关信息并进行相应落库
            saveHotelDetail(hotelBasepriceDto.getHotelStatusEntity(), numEntity, "OnLineIntlGTASearch");
        }

        return true;
    }

    /**
     * @Description: 酒店入离起价--入库
     * @Author: 王俊文
     * @Date: 19-7-18 下午2:11
     * @Param: [hotelStatusEntityBean, cityId]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-18 下午2:11          1.0      入离起价--封装数据并入库
     */
    private boolean saveHotelDetail(HotelStatusEntityBean hotelStatusEntityBean, NumEntity numEntity,
                                    String SearchType)
    {
        logger.info(numEntity.getCity() + ":" + numEntity.getCityName() + "下" + hotelStatusEntityBean.getHotel() + "起价入库开始");
        //修改对应酒店信息
        for ( int i = 1; i <= 2 ; i++)
        {
            BHotelDetail bHotelDetail = new BHotelDetail();
            bHotelDetail.setHotelId(hotelStatusEntityBean.getHotel());
            //原币种价格
            bHotelDetail.setMinprice(hotelStatusEntityBean.getMinPrice_MyCurrency());
            //人民币价格
            bHotelDetail.setMinpricermb(hotelStatusEntityBean.getMinPrice());
            //原币种
            bHotelDetail.setMinpricecurrency(hotelStatusEntityBean.getCurrency_minPrice());
            //最低价房型ID
            bHotelDetail.setMinpriceroomid(hotelStatusEntityBean.getMinPriceRoom());
            //起价最后同步时间
            bHotelDetail.setMinpricelastsynctime(new Date());
            if ( numEntity != null )
            {
                //城市id
                bHotelDetail.setCityId(Long.valueOf(numEntity.getCity()));
            }
            try {
                boolean sign = ibHotelDetailService.saveOrUpdate(bHotelDetail);
                if ( sign )
                {
                    break;
                }
            }catch (Exception sce)
            {
                try
                {
                    ibHotelDetailService.updateById(bHotelDetail);
                }catch (Exception e)
                {
                    continue;
                }
            }
        }
        //封装起价信息
        //查询对应房型数据
        BHotelBaseprice bHotelBaseprice = new BHotelBaseprice();
        //查oracle逻辑 start 暂时停用
//        bHotelBaseprice = saveHotelDetailOracle(hotelStatusEntityBean, bHotelBaseprice);
        //查oracle逻辑 end 暂时停用
        //查mongodb start 暂时启用
        bHotelBaseprice = saveHotelDetailMongodb(hotelStatusEntityBean, bHotelBaseprice);
        //查mongodb end 暂时启用
        //酒店id
        bHotelBaseprice.setHotelId(hotelStatusEntityBean.getHotel());
        //房型id
        bHotelBaseprice.setRoomId(hotelStatusEntityBean.getMinPriceRoom());
        if ( numEntity != null )
        {
            //城市id
            bHotelBaseprice.setCityId(Long.valueOf(numEntity.getCity()));
            //城市中文名
            bHotelBaseprice.setCityName(numEntity.getCityName());
            //城市英文名
            bHotelBaseprice.setCityEName(numEntity.getCityEName());
        }
        //税后且返现后最低价房型的日均最低价，币种为人民币
        bHotelBaseprice.setMinPrice(hotelStatusEntityBean.getMinPriceInfoEntity().getMinPrice_AfterTax().getMinPrice_CashBack().getMinPrice());
        //税后且返现后最低价房型的日均最低价，币种为原币种
        bHotelBaseprice.setMinPriceOriginalCurrency(hotelStatusEntityBean.getMinPriceInfoEntity().getMinPrice_AfterTax().getMinPrice_CashBack().getMinPrice_OriginalCurrency());
        //原币种
        bHotelBaseprice.setOriginalCurrency(hotelStatusEntityBean.getMinPriceInfoEntity().getMinPrice_AfterTax().getMinPrice_CashBack().getOriginalCurrency());
        //酒店类型
        bHotelBaseprice.setSearchType(SearchType);
        //插入或修改对应信息
        boolean sign = false;
        try
        {
             sign = ibHotelBasepriceService.saveOrUpdate(bHotelBaseprice);
        }catch (Exception sce)
        {
            ibHotelBasepriceService.updateById(bHotelBaseprice);
        }

         logger.info(numEntity.getCity() + ":" + numEntity.getCityName() + "下" + hotelStatusEntityBean.getHotel() + "起价入库结束");
         return sign;
    }

    /**
     * 起价入库--筛选条件查mongodb逻辑--停用
     * @param hotelStatusEntityBean
     * @return
     */
    private BHotelBaseprice saveHotelDetailMongodb(HotelStatusEntityBean hotelStatusEntityBean, BHotelBaseprice bHotelBaseprice)
    {
        HotelRoomDetailResModel hotelRoomDetailResModel = new HotelRoomDetailResModel();
        Query query = new Query(Criteria.where("hotelId").is(hotelStatusEntityBean.getHotel().toString()));
        List<HotelRoomMongoModel> hotelRoomMongoModel = mongoTemplate.find(query, HotelRoomMongoModel.class);
        if ( hotelRoomMongoModel == null || hotelRoomMongoModel.size() <= 0 )
        {
            JSONObject jsonObject =
                    iHotelStaticInformationService.queryRoomDetailNotInMongo(hotelStatusEntityBean.getHotel().toString(),"1", "0");
            hotelRoomDetailResModel = JSONObject.parseObject(jsonObject.toJSONString(), HotelRoomDetailResModel.class);
        }else
        {
            hotelRoomDetailResModel = JSONObject.parseObject(hotelRoomMongoModel.get(0).getHotelRoomDetail(),
                    HotelRoomDetailResModel.class);
        }
        //获取对应物理,售卖房型信息
        HotelRoomDetailResModel.RoomStaticInfosBean.RoomInfosBean roomInfosBeanParam =
                new HotelRoomDetailResModel.RoomStaticInfosBean.RoomInfosBean();
        HotelRoomDetailResModel.RoomStaticInfosBean.RoomTypeInfoBean roomTypeInfoBeanParam =
                new HotelRoomDetailResModel.RoomStaticInfosBean.RoomTypeInfoBean();
        for ( HotelRoomDetailResModel.RoomStaticInfosBean roomStaticInfosBean :
                hotelRoomDetailResModel.getRoomStaticInfos() )
        {
            for ( HotelRoomDetailResModel.RoomStaticInfosBean.RoomInfosBean roomInfosBean :
                    roomStaticInfosBean.getRoomInfos() )
            {
                if ( roomInfosBean.getRoomID() == hotelStatusEntityBean.getMinPriceRoom() )
                {
                    roomInfosBeanParam = roomInfosBean;
                    roomTypeInfoBeanParam = roomStaticInfosBean.getRoomTypeInfo();
                }
            }
        }
        if ( roomTypeInfoBeanParam != null )
        {
            //儿童人数上限
            bHotelBaseprice.setChildrenNumberList(roomTypeInfoBeanParam.getChildLimit().getMaxOccupancy());
            //儿童年龄下限
            bHotelBaseprice.setChildMinage(roomTypeInfoBeanParam.getChildLimit().getMinAge());
            //儿童年龄上限
            bHotelBaseprice.setChildMaxage(roomTypeInfoBeanParam.getChildLimit().getMaxAge());
        }
        if ( roomInfosBeanParam != null )
        {
            //人数限制
            bHotelBaseprice.setFilterRoomByPerson(roomInfosBeanParam.getMaxOccupancy());
            //房型支付类型  OnlyFGPrice: 到付   OnlyPPPrice: 预付
            if ( roomInfosBeanParam.getPayType().equals("FG") )
            {
                bHotelBaseprice.setPaymentType("OnlyFGPrice");
            }else if ( roomInfosBeanParam.getPayType().equals("PP") )
            {
                bHotelBaseprice.setPaymentType("OnlyPPPrice");
            }
        }

        return bHotelBaseprice;
    }

    /**
     * 起价入库--筛选条件查oracle逻辑
     * @param hotelStatusEntityBean
     * @return
     */
    private BHotelBaseprice saveHotelDetailOracle(HotelStatusEntityBean hotelStatusEntityBean, BHotelBaseprice bHotelBaseprice)
    {
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("hotelId", hotelStatusEntityBean.getHotel());
        paramMap.put("roomId", hotelStatusEntityBean.getMinPriceRoom());
        BSaleRoomDto bSaleRoomDto = bSaleRoomMapper.selectBSaleRoomType(paramMap);
        //获取起价所需信息
        //儿童人数上限
        bHotelBaseprice.setChildrenNumberList(bSaleRoomDto.getChildMaxoccupancy());
        //儿童年龄下限
        bHotelBaseprice.setChildMinage(bSaleRoomDto.getChildMinage());
        //儿童年龄上限
        bHotelBaseprice.setChildMaxage(bSaleRoomDto.getChildMaxage());
        //人数限制
        bHotelBaseprice.setFilterRoomByPerson(bSaleRoomDto.getMaxOccupancy());
        //房型支付类型  OnlyFGPrice: 到付   OnlyPPPrice: 预付
        bHotelBaseprice.setPaymentType(bSaleRoomDto.getPaytype());
        return bHotelBaseprice;
    }

    /**
     * @Description: 酒店入离起价请求封装
     * @Author: 王俊文
     * @Date: 19-7-18 上午9:45
     * @Param: [
     *                      jsonObject--请求主体
     *                      , bHotelCityList--酒店列表
     *                      , SearchType--酒店类型(WirelessSearch 国内, OnLineIntlGTASearch 国际)
     *                      , SearchTypeEntity--请求类型及分页信息
     *                      , PublicSearchParameter--酒店条件,城市,入离时间
     *                      ]
     * @returns: void
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-18 上午9:45          1.0          酒店入离起价请求封装
     */
    private void restHotelBaseprice(JSONObject jsonObject, List<BHotelCity> bHotelCityList, String SearchType,
                                       JSONObject SearchTypeEntity, JSONObject PublicSearchParameter) throws Exception
    {
        for ( BHotelCity bHotelCity : bHotelCityList )
        {
            logger.info(bHotelCity.getCountryName()+":"+bHotelCity.getCityName()+"起价开始");
//            //校验当前城市是否已入库  入库时使用  结束后删除 start
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("CITY_ID", bHotelCity.getCityId());
            List<BHotelBaseprice> bHotelBasepriceList = ibHotelBasepriceService.list(queryWrapper);
            if ( bHotelBasepriceList != null && bHotelBasepriceList.size() > 0 )
            {
                logger.info(bHotelCity.getCountryName()+":"+bHotelCity.getCityName()+"起价已入库,跳过");
                continue;
            }
//            //校验当前城市是否已入库  入库时使用  结束后删除 end
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //初始化页码
            Integer PageIndex = 1;
            //国际/国内酒店
            SearchTypeEntity.put("SearchType", SearchType);
            PublicSearchParameter.put("City", bHotelCity.getCityId());
            while (true)
            {
                //页码
                SearchTypeEntity.put("PageIndex", PageIndex);
                jsonObject.put("SearchTypeEntity", SearchTypeEntity);
                jsonObject.put("PublicSearchParameter", PublicSearchParameter);
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
                            logger.info(bHotelCity.getCountryName()+":"+bHotelCity.getCityName()+"无返回数据");
                            redisTemplate.expire("hotelBasepriceErrorDataList",60*60*24*30,TimeUnit.SECONDS);
                            redisTemplate.opsForList().leftPush("hotelBasepriceErrorDataList",jsonObject.toJSONString());
                            break;
                        }
                        redisTemplate.opsForValue().set("hotelbaseprice_"+SearchType+"_"+PublicSearchParameter.getString("CheckInDate")+PageIndex+"_"+bHotelCity.getCityId(),
                                returnJSONObject.toJSONString(), 60*60*24, TimeUnit.SECONDS);
                        redisTemplate.convertAndSend("IStaticInformationChange",
                                "hotelbaseprice_"+SearchType+"_"+PublicSearchParameter.getString("CheckInDate")+PageIndex+"_"+bHotelCity.getCityId());
                        logger.info(bHotelCity.getCountryName()+":"+bHotelCity.getCityName()+"起价完成");
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
