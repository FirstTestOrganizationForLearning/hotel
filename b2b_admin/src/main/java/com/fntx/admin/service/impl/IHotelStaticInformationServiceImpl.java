package com.fntx.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fntx.admin.service.IBHotelBasepriceService;
import com.fntx.admin.service.IBHotelDetailService;
import com.fntx.admin.service.IHotelStaticInformationService;
import com.fntx.admin.utils.CommonRedisHelper;
import com.fntx.admin.utils.FastJsonUtils;
import com.fntx.admin.utils.HttpClientUtil;
import com.fntx.admin.utils.StringUtil;
import com.fntx.common.constant.CtripApiErrorCode;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.dao.*;
import com.fntx.common.domain.*;
import com.fntx.common.domain.dto.*;
import com.fntx.common.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (C), 2019,
 * @ClassName: IHotelStaticInformationServiceImpl
 * @Author: 魏世杰
 * @Date: 19-7-19 下午15:35
 * @Description: 酒店静态信息入库（包括 酒店清单、酒店详情、房间详情）
 */
@Service
public class IHotelStaticInformationServiceImpl implements IHotelStaticInformationService {
    private static Logger logger = LoggerFactory.getLogger(IHotelStaticInformationServiceImpl.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BHotelCityMapper bHotelCityMapper;
    @Autowired
    private BHotelListMapper bHotelListMapper;
    @Autowired
    private BHotelDetailMapper bHotelDetailMapper;
    @Autowired
    private BHotelBusinessDistrictMapper bHotelBusinessDistrictMapper;
    @Autowired
    private BHotelPolicyMapper bHotelPolicyMapper;
    @Autowired
    private BHotelFacilityMapper bHotelFacilityMapper;
    @Autowired
    private BHotelImageMapper bHotelImageMapper;
    @Autowired
    private BHotelThemeMapper bHotelThemeMapper;
    @Autowired
    private LandmarkLngLatMapper landmarkLngLatMapper;
    @Autowired
    private IBHotelDetailService ibHotelDetailService;
    @Autowired
    private BHotelBasepriceMapper bHotelBasepriceMapper;
    @Autowired
    private IBHotelBasepriceService ibHotelBasepriceService;
    @Autowired
    private BHotelExtraPolicyMapper bHotelExtraPolicyMapper;
    @Autowired
    private BHotelMealsPolicyMapper bHotelMealsPolicyMapper;
    @Autowired
    private CommonRedisHelper commonRedisHelper;


    @Override
    public void addHotelList(Integer current, Integer size, Integer count) {
        try {
            logger.info("携程酒店同步插入酒店清单 开始." + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            // 1. 获取请求地址，并且拼接替换对应的参数
            String hotelListUrl = "http://hotel.tianxiafen.com:82/OpenService/FenHotelService.ashx?AID=1&SID=101&ICODE=hotel.list&UUID=A63FA038-32B2-46C7-A363-42E651378752&Token=token&mode=1&format=JSON";
            String accessToken = stringRedisTemplate.opsForValue().get("testToken");
            if (StringUtil.empty(accessToken)) {
                String tokenRes = HttpClientUtil.doGet("http://hotel.tianxiafen.com:82/OpenService/Authorize.ashx?AID=1&SID=101&KEY=F9541499825448A78F1CA6CBB9397757");
                JSONObject JSONObject = com.alibaba.fastjson.JSONObject.parseObject(tokenRes);
                accessToken = JSONObject.getString("Access_Token");
            }
            hotelListUrl = hotelListUrl.replace("token", accessToken);
            logger.info("拼接完成后酒店清单请求URL：" + hotelListUrl);

            // 2. 获取携程有酒城市静态数据
            List<BHotelCity> cityList = new ArrayList<BHotelCity>();
            QueryWrapper<BHotelCity> wrapper = new QueryWrapper<>();
            PageHelper.startPage(current, size);
            cityList = bHotelCityMapper.selectList(wrapper);
            // 存入mongo中的酒店清单对象
            HotelListSaveModel hotelListSaveModel = null;
            List<String> errHotel = new ArrayList<>();

            if (!cityList.isEmpty()) {
                logger.info("获取携程有酒城市静态数据不为空！！！" + cityList.size());
                // 拿到全部有酒城市，循环获取城市下的酒店ID信息入库(add)
                int totals = 0;
                int countPage = 1;
                int pageIndex = 1;
                int pageSize = 5000;
                for (BHotelCity hotelCity : cityList) {
                    logger.info("获取携程有酒城市静态数据,当前城市id:" + hotelCity.getCityId() + ",开始。"
                            + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));

                    long start = System.currentTimeMillis();
                    for (; pageIndex <= countPage; pageIndex++) {
                        HotelListReqModel hotelListRequestInfo = new HotelListReqModel(String.valueOf(hotelCity.getCityId()),
                                String.valueOf(pageIndex), String.valueOf(pageSize));
                        Query query = new Query(Criteria.where("hotelListReqModel.city").is(String.valueOf(hotelCity.getCityId())).and("hotelListReqModel.pageIndex").is(pageIndex).and("hotelListReqModel.pageSize").is(pageSize));
                        hotelListSaveModel = mongoTemplate.findOne(query, HotelListSaveModel.class);
                        if (null != hotelListSaveModel) {
                            logger.info("已经存在mongo的酒店清单数据！！！");
                            break;
                        }
                        String reqJson = FastJsonUtils.toJSONString(hotelListRequestInfo);
                        logger.info("酒店清单请求json:" + reqJson);
                        HotelListResponseInfo hotelListResponseInfo = new HotelListResponseInfo();
                        String res = null;
                        try {
                            res = HttpClientUtil.doPostJson(hotelListUrl, reqJson);
                        } catch (Exception e) {
                            logger.info("此处出现了连接超时异常，添加错误信息后，continue！！！让程序继续跑." + e.getMessage());
                            errHotel.add(hotelListRequestInfo.getCity());
                            continue;
                        }
                        // 处理完一个城市的酒店清单信息之后，需要给 pageIndex countPage重置初始值
                        if (pageIndex == countPage) {
                            if (!StringUtil.empty(res)) {
                                logger.info("酒店清单响应json:" + res);
                                hotelListResponseInfo = FastJsonUtils.toBean(res, HotelListResponseInfo.class);
                                if (StringUtil.empty(hotelListResponseInfo.getErrCode())) {
                                    totals = hotelListResponseInfo.getTotal();
                                    countPage = totals % pageSize == 0 ? totals / pageSize : totals / pageSize + 1;

                                    // 存入MongoDB中
                                    hotelListSaveModel = new HotelListSaveModel();
                                    hotelListSaveModel.setHotelListReqModel(hotelListRequestInfo);
                                    hotelListSaveModel.setHotelListResponseInfo(hotelListResponseInfo);
                                    mongoTemplate.save(hotelListSaveModel);
                                    count += 1;
                                } else {
                                    logger.info("调用酒店清单接口返回错误信息:" + hotelListResponseInfo.getErrCode() + ","
                                            + hotelListResponseInfo.getErrMsg());
                                    if (hotelListResponseInfo.getErrCode().equals("100006")) {
                                        String tokenRes = HttpClientUtil.doGet("http://hotel.tianxiafen.com:82/OpenService/Authorize.ashx?AID=1&SID=101&KEY=F9541499825448A78F1CA6CBB9397757");
                                        JSONObject JSONObject = com.alibaba.fastjson.JSONObject.parseObject(tokenRes);
                                        accessToken = JSONObject.getString("Access_Token");
                                        hotelListUrl.replace("token", accessToken);
                                        //res = HttpClientUtil.doPostJson(hotelListUrl, reqJson);
                                        try {
                                            res = HttpClientUtil.doPostJson(hotelListUrl, reqJson);
                                        } catch (Exception e) {
                                            logger.info("此处出现了连接超时异常，添加错误信息后，continue！！！让程序继续跑." + e.getMessage());
                                            errHotel.add(hotelListRequestInfo.getCity());
                                            continue;
                                        }
                                        hotelListResponseInfo = FastJsonUtils.toBean(res, HotelListResponseInfo.class);
                                        totals = hotelListResponseInfo.getTotal();
                                        countPage = totals % pageSize == 0 ? totals / pageSize : totals / pageSize + 1;
                                        // 存入MongoDB中
                                        hotelListSaveModel = new HotelListSaveModel();
                                        hotelListSaveModel.setHotelListReqModel(hotelListRequestInfo);
                                        hotelListSaveModel.setHotelListResponseInfo(hotelListResponseInfo);
                                        mongoTemplate.save(hotelListSaveModel);
                                        count += 1;
                                    }
                                }
                            }
                            pageIndex = 1;
                            countPage = 2;
                            break;
                        } else {
                            if (!StringUtil.empty(res)) {
                                logger.info("酒店清单响应json:" + res);
                                hotelListResponseInfo = FastJsonUtils.toBean(res, HotelListResponseInfo.class);
                                if (StringUtil.empty(hotelListResponseInfo.getErrCode())) {
                                    totals = hotelListResponseInfo.getTotal();
                                    if (totals == 0) {
                                        // 存入MongoDB中
                                        hotelListSaveModel = new HotelListSaveModel();
                                        hotelListSaveModel.setHotelListReqModel(hotelListRequestInfo);
                                        hotelListSaveModel.setHotelListResponseInfo(hotelListResponseInfo);
                                        mongoTemplate.save(hotelListSaveModel);
                                        count += 1;
                                        break;
                                    } else if (totals > 5000) {
                                        countPage = totals % pageSize == 0 ? totals / pageSize : totals / pageSize + 1;
                                        // 存入MongoDB中
                                        hotelListSaveModel = new HotelListSaveModel();
                                        hotelListSaveModel.setHotelListReqModel(hotelListRequestInfo);
                                        hotelListSaveModel.setHotelListResponseInfo(hotelListResponseInfo);
                                        mongoTemplate.save(hotelListSaveModel);
                                        count += 1;
                                    } else {
                                        // 存入MongoDB中
                                        hotelListSaveModel = new HotelListSaveModel();
                                        hotelListSaveModel.setHotelListReqModel(hotelListRequestInfo);
                                        hotelListSaveModel.setHotelListResponseInfo(hotelListResponseInfo);
                                        mongoTemplate.save(hotelListSaveModel);
                                        count += 1;
                                        break;
                                    }
                                } else {
                                    logger.info("调用酒店清单接口返回错误信息:" + hotelListResponseInfo.getErrCode() + ","
                                            + hotelListResponseInfo.getErrMsg());
                                    if (hotelListResponseInfo.getErrCode().equals("100006")) {
                                        String tokenRes = HttpClientUtil.doGet("http://hotel.tianxiafen.com:82/OpenService/Authorize.ashx?AID=1&SID=101&KEY=F9541499825448A78F1CA6CBB9397757");
                                        JSONObject JSONObject = com.alibaba.fastjson.JSONObject.parseObject(tokenRes);
                                        accessToken = JSONObject.getString("Access_Token");
                                        hotelListUrl.replace("token", accessToken);
                                        //res = HttpClientUtil.doPostJson(hotelListUrl, reqJson);
                                        try {
                                            res = HttpClientUtil.doPostJson(hotelListUrl, reqJson);
                                        } catch (Exception e) {
                                            logger.info("此处出现了连接超时异常，添加错误信息后，continue！！！让程序继续跑." + e.getMessage());
                                            errHotel.add(hotelListRequestInfo.getCity());
                                            continue;
                                        }
                                        hotelListResponseInfo = FastJsonUtils.toBean(res, HotelListResponseInfo.class);
                                        totals = hotelListResponseInfo.getTotal();

                                        if (totals == 0) {
                                            // 存入MongoDB中
                                            hotelListSaveModel = new HotelListSaveModel();
                                            hotelListSaveModel.setHotelListReqModel(hotelListRequestInfo);
                                            hotelListSaveModel.setHotelListResponseInfo(hotelListResponseInfo);
                                            mongoTemplate.save(hotelListSaveModel);
                                            count += 1;
                                            break;
                                        } else if (totals > 5000) {
                                            countPage = totals % pageSize == 0 ? totals / pageSize
                                                    : totals / pageSize + 1;
                                            // 存入MongoDB中
                                            hotelListSaveModel = new HotelListSaveModel();
                                            hotelListSaveModel.setHotelListReqModel(hotelListRequestInfo);
                                            hotelListSaveModel.setHotelListResponseInfo(hotelListResponseInfo);
                                            mongoTemplate.save(hotelListSaveModel);
                                            count += 1;
                                        } else {
                                            // 存入MongoDB中
                                            hotelListSaveModel = new HotelListSaveModel();
                                            hotelListSaveModel.setHotelListReqModel(hotelListRequestInfo);
                                            hotelListSaveModel.setHotelListResponseInfo(hotelListResponseInfo);
                                            mongoTemplate.save(hotelListSaveModel);
                                            count += 1;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    long end = System.currentTimeMillis();
                    logger.info("获取携程有酒城市静态数据,当前存入：" + count + "条，当前城市id:" + hotelCity.getCityId() + ",结束。"
                            + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss") + ",查询当前城市酒店清单并且处理存入MongoDB共花费时间："
                            + (end - start) + "ms！！");
                    Thread.sleep(1000);
                }
                current += 1;
                addHotelList(current, size, count);
            } else {
                logger.info("获取携程有酒城市静态数据为空！！！");
            }
            logger.info("携程酒店同步插入/更新 酒店清单 结束。" + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            logger.info("携程酒店新增酒店清单接口出现异常" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addHotelDetails(Integer current, Integer size, Integer count) {
        // TODO 所有的insert 主键ID ？？
        try {
            logger.info("携程酒店同步插入携程酒店详情静态 开始." + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+",current:"+current);
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter df2 = DateTimeFormatter.ofPattern("HH:mm");
                QueryWrapper<BHotelList> wrapper = new QueryWrapper<>();
                PageHelper.startPage(current, size);
                List<BHotelList> hotelLists = bHotelListMapper.selectList(wrapper);

                List<BHotelList> afterHotelLists  = getAfterHotelLists(hotelLists);

                for (int y = 0; y < afterHotelLists.size(); y++) {
                    BHotelList hotel = new BHotelList();
                    hotel = afterHotelLists.get(y);
                    String isIntel = hotel.getIsIntel();
                // TODO 切记不要在循环中查询数据库
                // BHotelDetail isHotel = bHotelDetailMapper.selectById(hotel.getHotelId());
                long HotelId = hotel.getHotelId();
                logger.info("**** 酒店ID ****：" + hotel.getHotelId());
                //if (isHotel == null) {
                SearchCandi candi = new SearchCandi();
                SearchCandidate searchCandidate = new SearchCandidate();
                searchCandidate.setHotelID(HotelId);
                Settings settings = new Settings();
                settings.setPrimaryLangID("zh-cn");
                List<String> setings = new ArrayList<String>();
                setings.add("HotelStaticInfo.GeoInfo");
                setings.add("HotelStaticInfo.NearbyPOIs");
                setings.add("HotelStaticInfo.TransportationInfos");
                setings.add("HotelStaticInfo.Brand");
                setings.add("HotelStaticInfo.Group");
                setings.add("HotelStaticInfo.Ratings");
                setings.add("HotelStaticInfo.Policies");
                setings.add("HotelStaticInfo.NormalizedPolicies.ChildAndExtraBedPolicy");
                setings.add("HotelStaticInfo.NormalizedPolicies.MealsPolicy");
                setings.add("HotelStaticInfo.AcceptedCreditCards");
                setings.add("HotelStaticInfo.ImportantNotices");
                setings.add("HotelStaticInfo.Facilities");
                setings.add("HotelStaticInfo.Pictures");
                setings.add("HotelStaticInfo.Descriptions");
                setings.add("HotelStaticInfo.Themes");
                setings.add("HotelStaticInfo.ContactInfo");
                setings.add("HotelStaticInfo.ArrivalTimeLimitInfo");
                setings.add("HotelStaticInfo.DepartureTimeLimitInfo");
                setings.add("HotelStaticInfo.HotelTags.IsBookable");
                settings.setExtendedNodes(setings);
                candi.setSearchCandidate(searchCandidate);
                candi.setSettings(settings);
                String reqJson = FastJsonUtils.toJSONString(candi);
                JSONObject params = JSON.parseObject(reqJson);
                String res = null;
                JSONObject resJso = null;

                try {
                    if ("0".equals(isIntel)) {
                        resJso = getResponse(params, "hotel.detail");
                    } else if ("1".equals(isIntel)) {
                        resJso = getResPonseOverseas(params, "hotel.detail");
                    }
                    res = JSON.toJSONString(resJso);
                } catch (Exception e) {
                    logger.info("此处出现了连接超时异常，continue！！！让程序继续跑." + e.getMessage());
                    e.printStackTrace();
                    Thread.sleep(1000);
                    continue;
                }
                HotelStaticResPonse hotelStatic = null;
                if (!StringUtil.empty(res)) {
                    logger.info("获取到的酒店静态信息数据:" + res);
                    hotelStatic = FastJsonUtils.toBean(res, HotelStaticResPonse.class);
                    if (StringUtil.empty(hotelStatic.getErrCode())) {
                        // 酒店信息 记录开始
                        BHotelDetail tHoteldescription = new BHotelDetail();
                        HotelStaticResPonse.HotelStaticInfoBean hotelStaticInfo = null;
                        HotelStaticResPonse.HotelStaticInfoBean.GeoInfoBean geoInfo = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.GeoInfoBean.BusinessDistrictBean> businessDistrict = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.GeoInfoBean.CoordinatesBean> coordinates = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.RatingsBean> ratings = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.AcceptedCreditCardsBean> acceptedCreditCards = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.DescriptionsBeanX> descriptions = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.PoliciesBean> policies = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.FacilitiesBean> facilities = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.PicturesBeanX> pictures = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.ThemesBean> themes = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.NearbyPOIsBean> nearbyPOIs = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.TransportationInfosBean> transportationInfos = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.NormalizedPoliciesBean.ChildAndExtraBedPolicyBean.DescriptionsBean> descriptionss = null;
                        List<HotelStaticResPonse.HotelStaticInfoBean.NormalizedPoliciesBean.MealsPolicyBean> mealsPolicy = null;

                        // 此处记录基础的信息
                        tHoteldescription.setHotelId(HotelId);
                        if (null != hotelStatic.getHotelStaticInfo()) {
                            if (hotelStatic.getHotelStaticInfo().getHotelID() == 0) {
                                logger.info("这个酒店有问题:" + HotelId);
                                if (y == afterHotelLists.size() - 1) {
                                    addHotelDetails(current + 1, size, count);
                                }
                                continue;
                            }
                            hotelStaticInfo = hotelStatic.getHotelStaticInfo();
                            tHoteldescription.setHotelNameCn(hotelStaticInfo.getHotelName());
                            tHoteldescription.setStarRate(hotelStaticInfo.getStarRating());
                            if (hotelStaticInfo.isIsOfficialRating()) {
                                tHoteldescription.setIsofficialrating(1);
                            } else {
                                tHoteldescription.setIsofficialrating(0);
                            }
//                                tHoteldescription.setOpenyear(LocalDateTime.parse(hotelStaticInfo.getOpenYear(), df));
//                                tHoteldescription.setRenovationyear(LocalDateTime.parse(hotelStaticInfo.getRenovationYear(), df));

                            tHoteldescription.setOpenyear(hotelStaticInfo.getOpenYear());
                            if (!StringUtil.empty(hotelStaticInfo.getRenovationYear())) {
                                tHoteldescription.setRenovationyear(hotelStaticInfo.getRenovationYear());
                            }

                            tHoteldescription.setRoomquantity(hotelStaticInfo.getRoomQuantity());
                            if (hotelStaticInfo.isIsOnlineSignUp()) {
                                tHoteldescription.setIsonlinesignup(1);
                            } else {
                                tHoteldescription.setIsonlinesignup(0);
                            }
                            if (hotelStaticInfo.getBookable()) {
                                tHoteldescription.setBookable(1);
                            } else {
                                tHoteldescription.setBookable(0);
                            }
                            if (hotelStaticInfo.getGeoInfo() != null) {
                                geoInfo = hotelStaticInfo.getGeoInfo();
                                tHoteldescription.setCityId(geoInfo.getCity().getCode());
                                if (null != geoInfo.getArea()) {
                                    tHoteldescription.setRegionId(geoInfo.getArea().getCode());
                                }
                                tHoteldescription.setAddressLine(geoInfo.getAddress());
                                tHoteldescription.setPostcode(geoInfo.getPostalCode());

                                // 处理酒店关联的商圈信息
                                if (null != geoInfo.getBusinessDistrict() && !geoInfo.getBusinessDistrict().isEmpty()) {
                                    businessDistrict = geoInfo.getBusinessDistrict();
                                    //   foreach  iter
                                    for (HotelStaticResPonse.HotelStaticInfoBean.GeoInfoBean.BusinessDistrictBean businessDistrictBean : businessDistrict) {
                                        BHotelBusinessDistrict bHotelBusinessDistrict = new BHotelBusinessDistrict();
                                        //bHotelBusinessDistrict.setId();
                                        bHotelBusinessDistrict.setBizDistId(Long.valueOf(businessDistrictBean.getCode()));
                                        bHotelBusinessDistrict.setHotelId(HotelId);
                                        bHotelBusinessDistrictMapper.insert(bHotelBusinessDistrict);
                                    }
                                }
                                // TODO 酒店邻近的路口？？ OTA推荐级别？？
                                // 酒店坐标信息   百度&高德&谷歌
                                if (null != geoInfo.getCoordinates() && !geoInfo.getCoordinates().isEmpty()) {
                                    coordinates = geoInfo.getCoordinates();
                                    for (HotelStaticResPonse.HotelStaticInfoBean.GeoInfoBean.CoordinatesBean coordinate : coordinates) {
                                        if (!StringUtil.empty(coordinate.getProvider()) && coordinate.getProvider().equals("Baidu")) {
                                            tHoteldescription.setBaidulatitude(coordinate.getLAT());
                                            tHoteldescription.setBaidulongitude(coordinate.getLNG());
                                        } else if (!StringUtil.empty(coordinate.getProvider()) && coordinate.getProvider().equals("Google")) {
                                            tHoteldescription.setGooglelatitude(coordinate.getLAT());
                                            tHoteldescription.setGooglelongitude(coordinate.getLNG());
                                        } else if (!StringUtil.empty(coordinate.getProvider()) && coordinate.getProvider().equals("AutoNavi")) {
                                            tHoteldescription.setGaodelatitude(coordinate.getLAT());
                                            tHoteldescription.setGaodelongitude(coordinate.getLNG());
                                        }
                                    }
                                }
                            } else {
                                logger.info("当前酒店id：" + HotelId + ",接口响应 GeoInfo信息为空!!!");
                            }
                            // 联系电话&传真
                            if (null != hotelStaticInfo.getContactInfo()) {
                                tHoteldescription.setTelephone(hotelStaticInfo.getContactInfo().getTelephone());
                                if (!StringUtil.empty(hotelStaticInfo.getContactInfo().getFax())) {
                                    tHoteldescription.setFax(hotelStaticInfo.getContactInfo().getFax());
                                }
                            }
                            // 酒店集团  groupId
                            if (null != hotelStaticInfo.getGroup()) {
                                tHoteldescription.setGroupId(Long.valueOf(hotelStaticInfo.getGroup().getCode()));
                            }
                            // 酒店品牌  brandId
                            if (null != hotelStaticInfo.getBrand()) {
                                tHoteldescription.setBrandId(Long.valueOf(hotelStaticInfo.getBrand().getCode()));
                            }
                            // 酒店评分
                            if (null != hotelStaticInfo.getRatings() && !hotelStaticInfo.getRatings().isEmpty()) {
                                ratings = hotelStaticInfo.getRatings();
                                for (HotelStaticResPonse.HotelStaticInfoBean.RatingsBean rating : ratings) {
                                    if (!StringUtil.empty(rating.getType()) && rating.getType().equals("CtripUserRating")) {
                                        tHoteldescription.setUserrate(Double.valueOf(rating.getValue()));
                                    } else if (!StringUtil.empty(rating.getType()) && rating.getType().equals("GuestOverallRating")) {
                                        tHoteldescription.setOverallrate(Double.valueOf(rating.getValue()));
                                    }
                                }
                            }
                            // 酒店接收信用卡
                            if (null != hotelStaticInfo.getAcceptedCreditCards() && !hotelStaticInfo.getAcceptedCreditCards().isEmpty()) {
                                acceptedCreditCards = hotelStaticInfo.getAcceptedCreditCards();
                                String cards = "";
                                for (HotelStaticResPonse.HotelStaticInfoBean.AcceptedCreditCardsBean acceptedCreditCard : acceptedCreditCards) {
                                    cards += acceptedCreditCard.getCardType() + ",";
                                }
                                tHoteldescription.setCreditcardType(cards);
                            }
                            // TODO 酒店重要提示信息、酒店点评－周边环境分类评分、房间卫生分类评分、酒店服务分类评分、酒店设施评分、酒店常用的酒店标签和分类
                            // 酒店简介 & 描述
                            if (null != hotelStaticInfo.getDescriptions() && !hotelStaticInfo.getDescriptions().isEmpty()) {
                                descriptions = hotelStaticInfo.getDescriptions();
                                for (HotelStaticResPonse.HotelStaticInfoBean.DescriptionsBeanX description : descriptions) {
                                    if (!StringUtil.empty(description.getCategory()) && description.getCategory().equals("1")) {
                                        tHoteldescription.setShortdesc(description.getText());
                                    } else if (!StringUtil.empty(description.getCategory()) && description.getCategory().equals("2")) {
                                        tHoteldescription.setLongdesc(description.getText());
                                    }
                                }
                            }
                            // 最早&最晚到离店时间
                            if (null != hotelStaticInfo.getArrivalTimeLimitInfo()) {
                                tHoteldescription.setArrivalearliesttime(hotelStaticInfo.getArrivalTimeLimitInfo().getEarliestTime());
                                if (!StringUtil.empty(hotelStaticInfo.getArrivalTimeLimitInfo().getLatestTime())) {
                                    tHoteldescription.setArrivallatesttime(hotelStaticInfo.getArrivalTimeLimitInfo().getLatestTime());
                                }
                                if (!StringUtil.empty(hotelStaticInfo.getArrivalTimeLimitInfo().getIsMustBe())) {
                                    if (hotelStaticInfo.getArrivalTimeLimitInfo().getIsMustBe().equals("F")) {
                                        tHoteldescription.setIsmustbe(0);
                                    } else {
                                        tHoteldescription.setIsmustbe(1);
                                    }
                                }
                                if (null != hotelStaticInfo.getDepartureTimeLimitInfo()) {
                                    tHoteldescription.setLeavelatesttime(hotelStaticInfo.getDepartureTimeLimitInfo().getLatestTime());
                                    if (!StringUtil.empty(hotelStaticInfo.getDepartureTimeLimitInfo().getEarliestTime())) {
                                        tHoteldescription.setLeaveearliesttime(hotelStaticInfo.getDepartureTimeLimitInfo().getEarliestTime());
                                    }
                                }
                            }
                            // 政策&设施&图片&主题&地标
                            if (null != hotelStaticInfo.getNearbyPOIs() && !hotelStaticInfo.getNearbyPOIs().isEmpty()) {
                                nearbyPOIs = hotelStaticInfo.getNearbyPOIs();
                                for (HotelStaticResPonse.HotelStaticInfoBean.NearbyPOIsBean poIs : nearbyPOIs) {
                                    List<HotelStaticResPonse.HotelStaticInfoBean.NearbyPOIsBean.CoordinatesBeanX> nearbyPOICoordinates = poIs.getCoordinates();
                                    for (HotelStaticResPonse.HotelStaticInfoBean.NearbyPOIsBean.CoordinatesBeanX nearbyPOICoordinate : nearbyPOICoordinates) {
                                        LandmarkLngLat nearbyPOILandmarkLngLat = new LandmarkLngLat();
                                        nearbyPOILandmarkLngLat.setHotelId(HotelId);
                                        nearbyPOILandmarkLngLat.setType(poIs.getType());
                                        nearbyPOILandmarkLngLat.setName(poIs.getName());
                                        nearbyPOILandmarkLngLat.setDistance(Double.valueOf(poIs.getDistance()));
                                        if (!StringUtil.empty(nearbyPOICoordinate.getProvider())) {
                                            nearbyPOILandmarkLngLat.setProvider(nearbyPOICoordinate.getProvider());
                                        }
                                        nearbyPOILandmarkLngLat.setLat(Double.valueOf(nearbyPOICoordinate.getLAT()));
                                        nearbyPOILandmarkLngLat.setLng(Double.valueOf(nearbyPOICoordinate.getLNG()));
                                        landmarkLngLatMapper.insert(nearbyPOILandmarkLngLat);
                                    }
                                }
                            }
                            if (null != hotelStaticInfo.getTransportationInfos() && !hotelStaticInfo.getTransportationInfos().isEmpty()) {
                                transportationInfos = hotelStaticInfo.getTransportationInfos();
                                for (HotelStaticResPonse.HotelStaticInfoBean.TransportationInfosBean transportationInfo : transportationInfos) {
                                    List<HotelStaticResPonse.HotelStaticInfoBean.TransportationInfosBean.CoordinatesBeanXX> transCoordinates = transportationInfo.getCoordinates();
                                    for (HotelStaticResPonse.HotelStaticInfoBean.TransportationInfosBean.CoordinatesBeanXX transCoordinate : transCoordinates) {
                                        LandmarkLngLat landmarkLngLat = new LandmarkLngLat();
                                        landmarkLngLat.setHotelId(HotelId);
                                        landmarkLngLat.setType(transportationInfo.getType());
                                        landmarkLngLat.setName(transportationInfo.getName());
                                        landmarkLngLat.setDistance(Double.valueOf(transportationInfo.getDistance()));
                                        if (!StringUtil.empty(transCoordinate.getProvider())) {
                                            landmarkLngLat.setProvider(transCoordinate.getProvider());
                                        }
                                        landmarkLngLat.setLat(Double.valueOf(transCoordinate.getLAT()));
                                        landmarkLngLat.setLng(Double.valueOf(transCoordinate.getLNG()));
                                        landmarkLngLat.setDirections(transportationInfo.getDirections());
                                        landmarkLngLat.setTransportationtype(transportationInfo.getTransportationType());
                                        landmarkLngLat.setTimetaken(Double.valueOf(transportationInfo.getTimeTaken()));
                                        landmarkLngLatMapper.insert(landmarkLngLat);
                                    }
                                }
                            }
                            // 正常的政策  儿童 婴儿加床等 & MealsPolicy
                            if (null != hotelStaticInfo.getNormalizedPolicies()) {
                                if (null != hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().getDescriptions()
                                        && !hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().getDescriptions().isEmpty()) {
                                    descriptionss = hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().getDescriptions();
                                    for (HotelStaticResPonse.HotelStaticInfoBean.NormalizedPoliciesBean.ChildAndExtraBedPolicyBean.DescriptionsBean descriptionsBean : descriptionss) {
                                        BHotelExtraPolicy bHotelExtraPolicy = new BHotelExtraPolicy();
                                        bHotelExtraPolicy.setHotelId(HotelId);
                                        if (null != hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().getExtraBed()) {
                                            if (!StringUtil.empty(hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().getExtraBed().getMaxQuantity())) {
                                                bHotelExtraPolicy.setMaxQuantity(Long.valueOf(hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().getExtraBed().getMaxQuantity()));
                                            }
                                            if (!StringUtil.empty(hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().getExtraBed().getMaxCribQuantity())) {
                                                bHotelExtraPolicy.setMaxCribQuantity(Long.valueOf(hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().getExtraBed().getMaxCribQuantity()));
                                            }
                                        }
                                        bHotelExtraPolicy.setCategory(descriptionsBean.getCategory());
                                        bHotelExtraPolicy.setText(descriptionsBean.getText());
                                        if (hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().isAllowChildrenToStay()) {
                                            bHotelExtraPolicy.setAllowchildrentostay(1);
                                        } else {
                                            bHotelExtraPolicy.setAllowchildrentostay(0);
                                        }
                                        if (hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().isAllowUseExistingBed()) {
                                            bHotelExtraPolicy.setAllowuseexistingbed(1);
                                        } else {
                                            bHotelExtraPolicy.setAllowuseexistingbed(0);
                                        }
                                        if (hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().isAllowExtraBed()) {
                                            bHotelExtraPolicy.setAllowextrabed(1);
                                        } else {
                                            bHotelExtraPolicy.setAllowextrabed(0);
                                        }
                                        if (hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().isAllowExtraCrib()) {
                                            bHotelExtraPolicy.setAllowextracrib(1);
                                        } else {
                                            bHotelExtraPolicy.setAllowextracrib(0);
                                        }
                                        bHotelExtraPolicyMapper.insert(bHotelExtraPolicy);
                                    }
                                } else {
                                    BHotelExtraPolicy bHotelExtraPolicy = new BHotelExtraPolicy();
                                    bHotelExtraPolicy.setHotelId(HotelId);
                                    if (hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().isAllowChildrenToStay()) {
                                        bHotelExtraPolicy.setAllowchildrentostay(1);
                                    } else {
                                        bHotelExtraPolicy.setAllowchildrentostay(0);
                                    }
                                    if (hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().isAllowUseExistingBed()) {
                                        bHotelExtraPolicy.setAllowuseexistingbed(1);
                                    } else {
                                        bHotelExtraPolicy.setAllowuseexistingbed(0);
                                    }
                                    if (hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().isAllowExtraBed()) {
                                        bHotelExtraPolicy.setAllowextrabed(1);
                                    } else {
                                        bHotelExtraPolicy.setAllowextrabed(0);
                                    }
                                    if (hotelStaticInfo.getNormalizedPolicies().getChildAndExtraBedPolicy().isAllowExtraCrib()) {
                                        bHotelExtraPolicy.setAllowextracrib(1);
                                    } else {
                                        bHotelExtraPolicy.setAllowextracrib(0);
                                    }
                                    bHotelExtraPolicyMapper.insert(bHotelExtraPolicy);
                                }
                                if (null != hotelStaticInfo.getNormalizedPolicies().getMealsPolicy() && !hotelStaticInfo.getNormalizedPolicies().getMealsPolicy().isEmpty()) {
                                    mealsPolicy = hotelStaticInfo.getNormalizedPolicies().getMealsPolicy();
                                    for (HotelStaticResPonse.HotelStaticInfoBean.NormalizedPoliciesBean.MealsPolicyBean mealsPolicyBean : mealsPolicy) {
                                        List<HotelStaticResPonse.HotelStaticInfoBean.NormalizedPoliciesBean.MealsPolicyBean.Amount> amount = mealsPolicyBean.getAmount();
                                        for (HotelStaticResPonse.HotelStaticInfoBean.NormalizedPoliciesBean.MealsPolicyBean.Amount amountBean : amount) {
                                            BHotelMealsPolicy bHotelMealsPolicy = new BHotelMealsPolicy();
                                            bHotelMealsPolicy.setHotelId(HotelId);
                                            bHotelMealsPolicy.setTyte(amountBean.getType());
                                            bHotelMealsPolicy.setAmount(amountBean.getAmount());
                                            bHotelMealsPolicy.setCurrency(amountBean.getCurrency());
                                            bHotelMealsPolicy.setMealtype(mealsPolicyBean.getMealType());
                                            bHotelMealsPolicyMapper.insert(bHotelMealsPolicy);
                                        }
                                    }
                                }
                            }
                            if (null != hotelStaticInfo.getPolicies() && !hotelStaticInfo.getPolicies().isEmpty()) {
                                policies = hotelStaticInfo.getPolicies();
                                for (HotelStaticResPonse.HotelStaticInfoBean.PoliciesBean policy : policies) {
                                    BHotelPolicy bHotelPolicy = new BHotelPolicy();
                                    bHotelPolicy.setType(policy.getCode());
                                    bHotelPolicy.setHotelId(HotelId);
                                    bHotelPolicy.setContent(policy.getText());
                                    bHotelPolicyMapper.insert(bHotelPolicy);
                                }
                            }
                            if (null != hotelStaticInfo.getFacilities() && !hotelStaticInfo.getFacilities().isEmpty()) {
                                facilities = hotelStaticInfo.getFacilities();
                                for (HotelStaticResPonse.HotelStaticInfoBean.FacilitiesBean facility : facilities) {
                                    List<HotelStaticResPonse.HotelStaticInfoBean.FacilitiesBean.FacilityItemBean> facilityItem = facility.getFacilityItem();
                                    for (HotelStaticResPonse.HotelStaticInfoBean.FacilitiesBean.FacilityItemBean facilityItemBean : facilityItem) {
                                        BHotelFacility bHotelFacility = new BHotelFacility();
                                        bHotelFacility.setStatus(facilityItemBean.getStatus());
                                        bHotelFacility.setHotelId(HotelId);
                                        bHotelFacility.setFacilityId(Long.valueOf(facilityItemBean.getID()));
                                        bHotelFacilityMapper.insert(bHotelFacility);
                                    }
                                }
                            }
                            if (null != hotelStaticInfo.getPictures() && !hotelStaticInfo.getPictures().isEmpty()) {
                                pictures = hotelStaticInfo.getPictures();
                                for (HotelStaticResPonse.HotelStaticInfoBean.PicturesBeanX picture : pictures) {
                                    BHotelImage bHotelImage = new BHotelImage();
                                    bHotelImage.setHotelId(HotelId);
                                    bHotelImage.setImageType(Long.valueOf(picture.getType()));
                                    bHotelImage.setImageCaption(picture.getCaption());
                                    bHotelImage.setImageUrl(picture.getURL());
                                    bHotelImageMapper.insert(bHotelImage);
                                }
                            }
                            if (null != hotelStaticInfo.getThemes() && !hotelStaticInfo.getThemes().isEmpty()) {
                                themes = hotelStaticInfo.getThemes();
                                for (HotelStaticResPonse.HotelStaticInfoBean.ThemesBean theme : themes) {
                                    BHotelTheme bHotelTheme = new BHotelTheme();
                                    bHotelTheme.setThemeId(Long.valueOf(theme.getCode()));
                                    bHotelTheme.setThemeName(theme.getName());
                                    bHotelTheme.setHotelId(HotelId);
                                    bHotelThemeMapper.insert(bHotelTheme);
                                }
                            }
                            tHoteldescription.setCreatedate(new Date());
                            tHoteldescription.setLastsynctime(new Date());
                            int insert = 0;
                            try {
                                insert = bHotelDetailMapper.insert(tHoteldescription);
                            } catch (Exception e) {
                                logger.info("此处出现了插入数据异常，continue！！！让程序继续跑." + e.getMessage());
                                e.printStackTrace();
                                continue;
                            }
                            count += insert;// 记录添加条数
                            logger.info("酒店：" + HotelId + "，已经入库成功，当前已插入酒店静态详情：" + count + "条");
                            Thread.sleep(1000);
                        } else {
                            logger.info("当前酒店id：" + HotelId + ",接口响应 未查到酒店详情静态信息!!!");
                        }
                    }
                }
                //}
//                else {
//                    logger.info("当前酒店id：" + HotelId + ",已存在酒店静态信息表中！！！");
//                }
                if (y == afterHotelLists.size() - 1) {
                    addHotelDetails(current + 1, size, count);
                }
            }
            logger.info("携程酒店同步插入携程酒店详情静态 结束." + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")+".当前current："+current);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/10/10 18:14
     * @Description: 处理去重，避免循环里面查询数据库 一次查完，处理返回，处理后续业务
     */
    private List<BHotelList> getAfterHotelLists(List<BHotelList> hotelLists) {

        // 第一步，先获取BHotelList 的数据
        // 第二步，把 BHotelList当中的hotelId取出来放到list   beforeHotelIds  中
        List<Long> beforeHotelIds = new ArrayList<>();
        for (BHotelList hotelList : hotelLists) {
            beforeHotelIds.add(hotelList.getHotelId());
        }
        //logger.info("查询到的beforeHotelIds:" + beforeHotelIds.toString());

        //第三步，用beforeHotelIds查询存在 BHotelDetail 数据表中的数据
        List<BHotelDetail> hotelDetails = bHotelDetailMapper.selectBatchIds(beforeHotelIds);

        //第四步，把查询到的hotelDetails的hotelIdS 放入  afterHotelIds中
        List<Long> afterHotelIds = new ArrayList<>();

        for (BHotelDetail hotelDetail : hotelDetails) {
            afterHotelIds.add(hotelDetail.getHotelId());
        }
        //logger.info("查询到的afterHotelIds:" + afterHotelIds.toString());

        //第五步 去重
        HashSet h1 = new HashSet(beforeHotelIds);
        HashSet h2 = new HashSet(afterHotelIds);
        h1.removeAll(h2);
        afterHotelIds.clear();
        afterHotelIds.addAll(h1);

        // 第六步 用去重后的 afterHotelIds 查 BHotelList
        List<BHotelList> afterHotelLists = new ArrayList<BHotelList>();
        long startTime = System.currentTimeMillis();
        for (BHotelList hotelList : hotelLists) {
            for (Long afterHotelId : afterHotelIds) {
                if(afterHotelId.equals(hotelList.getHotelId())){
                    afterHotelLists.add(hotelList);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("程序运行时间：" + (endTime - startTime) + "ms");
        return afterHotelLists;
    }

    @Override
    public void addHotelDetailsToMongo(Integer current, Integer size, Integer count) {
        try {
            logger.info("携程酒店详情静态 插入Mongo 开始." + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            QueryWrapper<BHotelList> wrapper = new QueryWrapper<>();
            PageHelper.startPage(current, size);
            List<BHotelList> hotelLists = bHotelListMapper.selectList(wrapper);
            HotelMongoModel MongoModel = null;
            List<String> errHotel = new ArrayList<>();

            for (int y = 0; y < hotelLists.size(); y++) {
                BHotelList hotel = new BHotelList();
                hotel = hotelLists.get(y);
                Query query = new Query(Criteria.where("hotelId").is(String.valueOf(hotel.getHotelId())));
                MongoModel = mongoTemplate.findOne(query, HotelMongoModel.class);
                long HotelId = hotel.getHotelId();
                logger.info("**** 酒店ID ****：" + hotel.getHotelId());
                if (MongoModel == null) {
                    logger.info("**** 酒店ID ****：" + hotel.getHotelId() + "在mongo中未获取到,执行插入操作~");
                    // 1. 获取请求地址，并且拼接替换对应的参数
                    String accessToken = stringRedisTemplate.opsForValue().get("testToken");
//                    if (StringUtil.empty(accessToken)) {
//                        String tokenRes = HttpClientUtil.doGet("http://hotel.tianxiafen.com:82/OpenService/Authorize.ashx?AID=1&SID=101&KEY=F9541499825448A78F1CA6CBB9397757");
//                        JSONObject JSONObject = com.alibaba.fastjson.JSONObject.parseObject(tokenRes);
//                        accessToken = JSONObject.getString("Access_Token");
//                    }
                    String hotelListUrl = "http://hotel.tianxiafen.com:82/OpenService/FenHotelService.ashx?AID=1&SID=101&ICODE=hotel.detail&UUID=A63FA038-32B2-46C7-A363-42E651378752&Token=token&mode=1&format=JSON";
                    hotelListUrl = hotelListUrl.replace("token", accessToken);
                    SearchCandi candi = new SearchCandi();
                    SearchCandidate searchCandidate = new SearchCandidate();
                    searchCandidate.setHotelID(HotelId);
                    Settings settings = new Settings();
                    settings.setPrimaryLangID("zh-cn");
                    List<String> setings = new ArrayList<String>();
                    setings.add("HotelStaticInfo.GeoInfo");
                    setings.add("HotelStaticInfo.NearbyPOIs");
                    setings.add("HotelStaticInfo.TransportationInfos");
                    setings.add("HotelStaticInfo.Brand");
                    setings.add("HotelStaticInfo.Group");
                    setings.add("HotelStaticInfo.Ratings");
                    setings.add("HotelStaticInfo.Policies");
                    setings.add("HotelStaticInfo.NormalizedPolicies.ChildAndExtraBedPolicy");
                    setings.add("HotelStaticInfo.NormalizedPolicies.MealsPolicy");
                    setings.add("HotelStaticInfo.AcceptedCreditCards");
                    setings.add("HotelStaticInfo.ImportantNotices");
                    setings.add("HotelStaticInfo.Facilities");
                    setings.add("HotelStaticInfo.Pictures");
                    setings.add("HotelStaticInfo.Descriptions");
                    setings.add("HotelStaticInfo.Themes");
                    setings.add("HotelStaticInfo.ContactInfo");
                    setings.add("HotelStaticInfo.ArrivalTimeLimitInfo");
                    setings.add("HotelStaticInfo.DepartureTimeLimitInfo");
                    setings.add("HotelStaticInfo.HotelTags.IsBookable");
                    settings.setExtendedNodes(setings);
                    candi.setSearchCandidate(searchCandidate);
                    candi.setSettings(settings);
                    String reqJson = FastJsonUtils.toJSONString(candi);
                    String res = null;
                    try {
                        res = HttpClientUtil.doPostJson(hotelListUrl, reqJson);
                    } catch (Exception e) {
                        logger.info("此处出现了连接超时异常，添加错误信息后，continue！！！让程序继续跑." + e.getMessage());
                        errHotel.add(String.valueOf(HotelId));
                        continue;
                    }
                    HotelStaticResPonse hotelStatic = null;
                    if (!StringUtil.empty(res)) {
                        logger.info("获取到的酒店静态信息数据:" + res);
                        MongoModel = new HotelMongoModel();
                        hotelStatic = FastJsonUtils.toBean(res, HotelStaticResPonse.class);
                        if (hotelStatic.getErrCode() != null && hotelStatic.getErrCode().equals("100006")) {
//                            String tokenRes = HttpClientUtil.doGet("http://hotel.tianxiafen.com:82/OpenService/Authorize.ashx?AID=1&SID=101&KEY=F9541499825448A78F1CA6CBB9397757");
//                            JSONObject JSONObject = com.alibaba.fastjson.JSONObject.parseObject(tokenRes);

                            accessToken = stringRedisTemplate.opsForValue().get("testToken");
                            logger.info("token失效了,重新获取一下。" + accessToken + ",之前的url:" + hotelListUrl);
                            hotelListUrl = hotelListUrl.replace("token", accessToken);
                            try {
                                res = HttpClientUtil.doPostJson(hotelListUrl, reqJson);
                                hotelStatic = FastJsonUtils.toBean(res, HotelStaticResPonse.class);
                                if (hotelStatic.getErrCode() != null && hotelStatic.getErrCode().equals("100006")) {
                                    logger.info("token失效了,重新获取后请求依旧失败。continue！！！替换token后的URL:" + hotelListUrl);
                                    continue;
                                }
                            } catch (Exception e) {
                                logger.info("此处出现了连接超时异常，添加错误信息后，continue！！！让程序继续跑." + e.getMessage());
                                errHotel.add(String.valueOf(HotelId));
                                continue;
                            }
                            MongoModel.setHotelId(String.valueOf(HotelId));
                            MongoModel.setHotelDetail(res);
                            mongoTemplate.save(MongoModel);
                            count += 1;
                            Thread.sleep(2000);
                        }
                        if (StringUtil.empty(hotelStatic.getErrCode())) {
                            MongoModel.setHotelId(String.valueOf(HotelId));
                            MongoModel.setHotelDetail(res);
                            mongoTemplate.save(MongoModel);
                            count += 1;
                            logger.info("酒店：" + HotelId + "，插入 Mongo 成功，当前已插入：" + count + "条");
                            Thread.sleep(2000);
                        }
                    } else {
                        logger.info("当前酒店id：" + HotelId + ",接口响应 未查到酒店详情静态信息!!!");
                    }
                } else {
                    logger.info("当前酒店id：" + HotelId + " ,Mongo 中已存在酒店详情静态信息!!!");
                }
            }
            addHotelDetailsToMongo(current + 1, size, count);
            logger.info("携程酒店详情静态 插入Mongo 结束." + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addHotelRoomDetailsToMongo(Integer current, Integer size, Integer count) {
        try {
            logger.info("携程酒店房间详情静态 插入Mongo 开始." + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            QueryWrapper<BHotelList> wrapper = new QueryWrapper<>();
            PageHelper.startPage(current, size);
            List<BHotelList> hotelLists = bHotelListMapper.selectList(wrapper);
            HotelRoomMongoModel roomMongoModel = null;
            List<String> errHotel = new ArrayList<>();

            for (int y = 0; y < hotelLists.size(); y++) {
                BHotelList hotel = new BHotelList();
                hotel = hotelLists.get(y);
                String isIntel = hotel.getIsIntel();
                Query query = new Query(Criteria.where("hotelId").is(String.valueOf(hotel.getHotelId())));
                roomMongoModel = mongoTemplate.findOne(query, HotelRoomMongoModel.class);
                long HotelId = hotel.getHotelId();
                logger.info("**** 酒店ID ****：" + hotel.getHotelId());
                if (roomMongoModel == null) {
                    logger.info("**** 酒店ID ****：" + hotel.getHotelId() + "在mongo中未获取到,执行插入操作~");
                    HotelRoomDetailReqModel hotelRoomDetailReqModel = new HotelRoomDetailReqModel();
                    HotelRoomDetailReqModel.SearchCandidateBean SearchCandidate = new HotelRoomDetailReqModel.SearchCandidateBean();
                    SearchCandidate.setHotelID(HotelId);
                    hotelRoomDetailReqModel.setSearchCandidate(SearchCandidate);
                    List<String> setings = new ArrayList<String>();
                    setings.add("RoomTypeInfo.Facilities");
                    setings.add("RoomTypeInfo.Pictures");
                    setings.add("RoomTypeInfo.Descriptions");
                    setings.add("RoomTypeInfo.Smoking");
                    setings.add("RoomTypeInfo.BroadNet");
                    setings.add("RoomTypeInfo.ChildLimit");
                    setings.add("RoomTypeInfo.RoomBedInfos");
                    setings.add("RoomInfo.ApplicabilityInfo");
                    setings.add("RoomInfo.AreaApplicabilityInfo");
                    setings.add("RoomInfo.Smoking");
                    setings.add("RoomInfo.BroadNet");
                    setings.add("RoomInfo.RoomBedInfos");
                    setings.add("RoomInfo.RoomFGToPPInfo");
                    setings.add("RoomInfo.RoomGiftInfos");
                    setings.add("RoomInfo.ChannelLimit");
                    setings.add("RoomInfo.ExpressCheckout");
                    setings.add("RoomInfo.RoomTags");
                    setings.add("RoomInfo.BookingRules");
                    setings.add("RoomInfo.Descriptions");
                    HotelRoomDetailReqModel.SettingsBean SettingsBean = new HotelRoomDetailReqModel.SettingsBean();
                    SettingsBean.setPrimaryLangID("en");
                    SettingsBean.setExtendedNodes(setings);
                    hotelRoomDetailReqModel.setSettings(SettingsBean);

                    String reqJson = FastJsonUtils.toJSONString(hotelRoomDetailReqModel);
                    JSONObject params = JSON.parseObject(reqJson);
                    String res = null;
                    JSONObject resJso = null;

                    try {
                        if ("0".equals(isIntel)) {
                            resJso = getResponse(params, "hotel.roomtype.list");
                        } else if ("1".equals(isIntel)) {
                            resJso = getResPonseOverseas(params, "hotel.roomtype.list");
                        }
                        res = JSON.toJSONString(resJso);
                    } catch (Exception e) {
                        logger.info("此处出现了连接超时异常，添加错误信息后，continue！！！让程序继续跑." + e.getMessage());
                        errHotel.add(String.valueOf(HotelId));
                        continue;
                    }

                    HotelRoomDetailResModel hotelRoomDetailResModel = null;
                    if (!StringUtil.empty(res)) {
                        logger.info("获取到的酒店房型静态信息数据:" + res);
                        roomMongoModel = new HotelRoomMongoModel();
                        try {
                            hotelRoomDetailResModel = FastJsonUtils.toBean(res, HotelRoomDetailResModel.class);
                        } catch (Exception e) {
                            logger.info("此处出现了json转换异常，continue！！！让程序继续跑." + e.getMessage());
                            continue;
                        }
                        if (hotelRoomDetailResModel.getErrCode() != null && hotelRoomDetailResModel.getErrCode().equals("100006")) {
                            getToken();
                            try {
                                if ("0".equals(isIntel)) {
                                    resJso = getResponse(params, "hotel.roomtype.list");
                                } else if ("1".equals(isIntel)) {
                                    resJso = getResPonseOverseas(params, "hotel.roomtype.list");
                                }
                                res = JSON.toJSONString(resJso);
                            } catch (Exception e) {
                                logger.info("此处出现了连接超时异常，添加错误信息后，continue！！！让程序继续跑." + e.getMessage());
                                errHotel.add(String.valueOf(HotelId));
                                continue;
                            }
                            roomMongoModel.setHotelId(String.valueOf(HotelId));
                            roomMongoModel.setHotelRoomDetail(res);
                            mongoTemplate.save(roomMongoModel);
                            count += 1;
                            Thread.sleep(500);
                        } else {
                            roomMongoModel.setHotelId(String.valueOf(HotelId));
                            roomMongoModel.setHotelRoomDetail(res);
                            mongoTemplate.save(roomMongoModel);
                            count += 1;
                            logger.info("酒店：" + HotelId + "，插入Mongo 成功，当前已插入房型静态详情：" + count + "条");
                            Thread.sleep(500);
                        }
                    } else {
                        logger.info("当前酒店id：" + HotelId + ",接口响应 未查到房型详情静态信息!!!");
                    }
                } else {
                    logger.info("当前酒店id：" + HotelId + ",Mongo中 已存在房间详情静态信息!!!");
                }
            }
            addHotelRoomDetailsToMongo(current + 1, size, count);
            logger.info("携程房间详情静态 插入Mongo 结束." + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject queryHotelDetailNotInMongo(String hotelId, String dealFlag, String isIntel) {
        JSONObject returnJSONObject = new JSONObject(16);
        HotelMongoModel MongoModel = null;
        SearchCandi candi = new SearchCandi();
        SearchCandidate searchCandidate = new SearchCandidate();
        Update update = new Update();
        Query query = new Query(Criteria.where("hotelId").is(hotelId));
        String collectionName = "hotelMongoModel";
        try {
            searchCandidate.setHotelID(Long.valueOf(hotelId));
            Settings settings = new Settings();
            settings.setPrimaryLangID("zh-cn");
            List<String> setings = new ArrayList<String>();
            setings.add("HotelStaticInfo.GeoInfo");
            setings.add("HotelStaticInfo.NearbyPOIs");
            setings.add("HotelStaticInfo.TransportationInfos");
            setings.add("HotelStaticInfo.Brand");
            setings.add("HotelStaticInfo.Group");
            setings.add("HotelStaticInfo.Ratings");
            setings.add("HotelStaticInfo.Policies");
            setings.add("HotelStaticInfo.NormalizedPolicies.ChildAndExtraBedPolicy");
            setings.add("HotelStaticInfo.NormalizedPolicies.MealsPolicy");
            setings.add("HotelStaticInfo.AcceptedCreditCards");
            setings.add("HotelStaticInfo.ImportantNotices");
            setings.add("HotelStaticInfo.Facilities");
            setings.add("HotelStaticInfo.Pictures");
            setings.add("HotelStaticInfo.Descriptions");
            setings.add("HotelStaticInfo.Themes");
            setings.add("HotelStaticInfo.ContactInfo");
            setings.add("HotelStaticInfo.ArrivalTimeLimitInfo");
            setings.add("HotelStaticInfo.DepartureTimeLimitInfo");
            setings.add("HotelStaticInfo.HotelTags.IsBookable");
            settings.setExtendedNodes(setings);
            candi.setSearchCandidate(searchCandidate);
            candi.setSettings(settings);
            String reqJson = FastJsonUtils.toJSONString(candi);
            JSONObject params = JSON.parseObject(reqJson);

            //弗恩测试  正式情况下注释掉
            String icode = "hotel.detail";
            if ("0".equals(isIntel)) {
                returnJSONObject = getResponse(params, icode, "600");
            } else if ("1".equals(isIntel)) {
                returnJSONObject = getResponseOverseas(params, icode, "600");
            }
            if (StringUtil.empty(returnJSONObject.getString("ErrCode"))) {

                if (dealFlag.equals("0")) {
                    update.set("hotelDetail", returnJSONObject);
                    mongoTemplate.updateFirst(query, update, collectionName);
                } else {
                    MongoModel = mongoTemplate.findOne(query, HotelMongoModel.class);
                    if (null == MongoModel) {
                        MongoModel = new HotelMongoModel();
                        MongoModel.setHotelId(String.valueOf(candi.getSearchCandidate().getHotelID()));
                        MongoModel.setHotelDetail(JSON.toJSONString(returnJSONObject));
                        mongoTemplate.save(MongoModel);
                    } else {
                        logger.info("当前酒店：" + hotelId + "的酒店详情,已存在mongo中~~~执行更新最新信息操作");
                        update.set("hotelDetail", returnJSONObject);
                        mongoTemplate.updateFirst(query, update, collectionName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnJSONObject;
    }

    @Override
    public JSONObject queryRoomDetailNotInMongo(String hotelId, String dealFlag, String isIntel) {
        JSONObject returnJSONObject = new JSONObject(16);
        HotelRoomMongoModel roomMongoModel = null;
        Query query = new Query(Criteria.where("hotelId").is(hotelId));
        Update update = new Update();
        String collectionName = "roomMongoModel";
        try {
            HotelRoomDetailReqModel hotelRoomDetailReqModel = new HotelRoomDetailReqModel();
            HotelRoomDetailReqModel.SearchCandidateBean SearchCandidate = new HotelRoomDetailReqModel.SearchCandidateBean();
            SearchCandidate.setHotelID(Long.valueOf(hotelId));
            hotelRoomDetailReqModel.setSearchCandidate(SearchCandidate);
            List<String> setings = new ArrayList<String>();
            setings.add("RoomTypeInfo.Facilities");
            setings.add("RoomTypeInfo.Pictures");
            setings.add("RoomTypeInfo.Descriptions");
            setings.add("RoomTypeInfo.Smoking");
            setings.add("RoomTypeInfo.BroadNet");
            setings.add("RoomTypeInfo.ChildLimit");
            setings.add("RoomTypeInfo.RoomBedInfos");
            setings.add("RoomInfo.ApplicabilityInfo");
            setings.add("RoomInfo.AreaApplicabilityInfo");
            setings.add("RoomInfo.Smoking");
            setings.add("RoomInfo.BroadNet");
            setings.add("RoomInfo.RoomBedInfos");
            setings.add("RoomInfo.RoomFGToPPInfo");
            setings.add("RoomInfo.RoomGiftInfos");
            setings.add("RoomInfo.ChannelLimit");
            setings.add("RoomInfo.ExpressCheckout");
            setings.add("RoomInfo.RoomTags");
            setings.add("RoomInfo.BookingRules");
            setings.add("RoomInfo.Descriptions");
            HotelRoomDetailReqModel.SettingsBean SettingsBean = new HotelRoomDetailReqModel.SettingsBean();
            SettingsBean.setPrimaryLangID("en");
            SettingsBean.setExtendedNodes(setings);
            hotelRoomDetailReqModel.setSettings(SettingsBean);
            String reqJson = FastJsonUtils.toJSONString(hotelRoomDetailReqModel);

            JSONObject params = JSON.parseObject(reqJson);

            //弗恩测试  正式情况下注释掉
            String icode = "hotel.roomtype.list";
            if ("0".equals(isIntel)) {
                returnJSONObject = getResponse(params, icode, "600");
            } else if ("1".equals(isIntel)) {
                returnJSONObject = getResponseOverseas(params, icode, "600");
            }

            if (StringUtil.empty(returnJSONObject.getString("ErrCode"))) {
                if (dealFlag.equals("0")) {
                    update.set("hotelRoomDetail", returnJSONObject);
                    mongoTemplate.updateFirst(query, update, collectionName);
                } else {
                    roomMongoModel = mongoTemplate.findOne(query, HotelRoomMongoModel.class);
                    if (null == roomMongoModel) {
                        roomMongoModel = new HotelRoomMongoModel();
                        roomMongoModel.setHotelId(hotelId);
                        roomMongoModel.setHotelRoomDetail(JSON.toJSONString(returnJSONObject));
                        mongoTemplate.save(roomMongoModel);
                    } else {
                        logger.info("当前酒店：" + hotelId + "的房间详情,已存在mongo中~~~执行更新最新信息操作");
                        update.set("hotelRoomDetail", returnJSONObject);
                        mongoTemplate.updateFirst(query, update, collectionName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnJSONObject;
    }


    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/9/16 17:23
     * @Description: 获取国内静态详情
     */
    private JSONObject getResponse(JSONObject param, String icode) {
        RestTemplate restTemplate = new RestTemplate();
        String token = "";
        JSONObject body;
        if (Enviroment.IS_RELEASE_ENV) {
            token = stringRedisTemplate.opsForValue().get("token");
        } else {
            token = stringRedisTemplate.opsForValue().get("token");
        }
        BaseApiReq baseApiReq = Enviroment.getBaseApiReq("0");
        // String url = Enviroment.API_COMMON_URL.getValue();
        String url = Enviroment.API_FN_COMMON_URL.getValue();
//        url = url.replace("{app_path}", baseApiReq.getUrl())
//                .replace("{aid}", baseApiReq.getAID())
//                .replace("{sid}", baseApiReq.getSID())
//                .replace("{key}", baseApiReq.getKEY())
//                .replace("{format}", baseApiReq.getFormat())
//                .replace("{GUID}", baseApiReq.getUUID())
//                .replace("{Access_Token}", token)
//                .replace("{ICODE}", icode);

        //测试时使用  调用弗恩
        url = url.replace("{app_path}", "hotel.tianxiafen.com:82")
                .replace("{aid}", "1")
                .replace("{sid}", "101")
                .replace("{key}", Enviroment.KEY.getValue())
                .replace("{format}", Enviroment.FORMAT.getValue())
                .replace("{GUID}", "A63FA038-32B2-46C7-A363-42E651378752")
                .replace("{Access_Token}", redisTemplate.opsForValue().get("token").toString())
                .replace("{ICODE}", icode);

        logger.info("国内请求URL：" + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
        body = restTemplate.exchange(url, HttpMethod.POST, formEntity, JSONObject.class).getBody();
        return body;
    }

    private JSONObject getResponse(JSONObject param, String icode, String inRedisValue) {
        Long sleepS = (Long) Math.round(1 + Math.random() * (100));
//        try {
//            Thread.sleep(sleepS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        JSONObject resultModel = new JSONObject(16);
        boolean sign = false;
        Long value = 0L;

        String key = icode;
        boolean lock = commonRedisHelper.lock(key);

        if (lock) {
            // 执行逻辑操作
            sign = true;
        } else {
            // 设置失败次数计数器, 当到达3次时, 返回失败
            int failCount = 1;
            while (failCount <= 3) {
                // 等待100ms重试
                try {
                    Thread.sleep(sleepS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (commonRedisHelper.lock(key)) {
                    // 执行逻辑操作
                    sign = true;
                    break;
                } else {
                    failCount++;
                }
            }
//            logger.info("现在创建的人太多了, 请稍等再试");
        }

        if (sign) {
            redisTemplate.opsForValue().setIfAbsent(key, inRedisValue, 60,
                    TimeUnit.SECONDS);
            value = redisTemplate.opsForValue().increment(key, -1);
            if (value.compareTo(0L) <= 0) {
                commonRedisHelper.delete(key);
                resultModel.put("ErrCode", CtripApiErrorCode.RateLimit.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.RateLimit.getName());
            } else {
                commonRedisHelper.delete(key);
                RestTemplate restTemplate = new RestTemplate();
                JSONObject body;
                String token = stringRedisTemplate.opsForValue().get("token");
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
        } else {
            resultModel.put("ErrCode", CtripApiErrorCode.Timestampistimeout.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.Timestampistimeout.getName());
        }
        return resultModel;
    }

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/9/16 16:51
     * @Description: 获取国外静态详情
     */
    private JSONObject getResPonseOverseas(JSONObject param, String icode) {

        RestTemplate restTemplate = new RestTemplate();
        String token = stringRedisTemplate.opsForValue().get("overseasToken");
        JSONObject body;
        BaseApiReq baseApiReq = Enviroment.getBaseApiReq("1");
//                String url = Enviroment.API_COMMON_URL.getValue();
//                url = url.replace("{app_path}", Enviroment.URL.getValue())
//                        .replace("{aid}", Enviroment.OVERSEAS_AID.getValue())
//                        .replace("{sid}", Enviroment.OVERSEAS_SID.getValue())
//                        .replace("{key}", Enviroment.OVERSEAS_KEY.getValue())
//                        .replace("{format}", Enviroment.FORMAT.getValue())
//                        .replace("{GUID}", Enviroment.OVERSEAS_UUID.getValue())
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
        logger.info("海外请求URL：" + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
        body = restTemplate.exchange(url, HttpMethod.POST, formEntity, JSONObject.class).getBody();
        return body;
    }

    private JSONObject getResponseOverseas(JSONObject param, String icode, String inRedisValue) {
        Long sleepS = (Long) Math.round(1 + Math.random() * (100));
//        try {
//            Thread.sleep(sleepS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        JSONObject resultModel = new JSONObject(16);
        boolean sign = false;
        Long value = 0L;

        String key = icode + "_overseas";
        boolean lock = commonRedisHelper.lock(key);

        if (lock) {
            // 执行逻辑操作
            sign = true;
        } else {
            // 设置失败次数计数器, 当到达3次时, 返回失败
            int failCount = 1;
            while (failCount <= 3) {
                // 等待100ms重试
                try {
                    Thread.sleep(sleepS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (commonRedisHelper.lock(key)) {
                    // 执行逻辑操作
                    sign = true;
                    break;
                } else {
                    failCount++;
                }
            }
//            logger.info("现在创建的人太多了, 请稍等再试");
        }

        if (sign) {
            redisTemplate.opsForValue().setIfAbsent(key, inRedisValue, 60,
                    TimeUnit.SECONDS);
            value = redisTemplate.opsForValue().increment(key, -1);
            if (value.compareTo(0L) <= 0) {
                commonRedisHelper.delete(key);
                resultModel.put("ErrCode", CtripApiErrorCode.RateLimit.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.RateLimit.getName());
            } else {
                commonRedisHelper.delete(key);
                RestTemplate restTemplate = new RestTemplate();
                String token = stringRedisTemplate.opsForValue().get("overseasToken");
                JSONObject body;
                BaseApiReq baseApiReq = Enviroment.getBaseApiReq("1");
//                String url = Enviroment.API_COMMON_URL.getValue();
//                url = url.replace("{app_path}", Enviroment.URL.getValue())
//                        .replace("{aid}", Enviroment.OVERSEAS_AID.getValue())
//                        .replace("{sid}", Enviroment.OVERSEAS_SID.getValue())
//                        .replace("{key}", Enviroment.OVERSEAS_KEY.getValue())
//                        .replace("{format}", Enviroment.FORMAT.getValue())
//                        .replace("{GUID}", Enviroment.OVERSEAS_UUID.getValue())
//                        .replace("{Access_Token}", token)
//                        .replace("{ICODE}", icode);

                //测试时使用  调用弗恩
                String url = "http://{app_path}/OpenService/FenHotelService" +
                        ".ashx?AID={aid}&SID={sid}&ICODE={ICODE}&UUID={GUID}&Token={Access_Token}&mode=1&format={format}";
                url = url.replace("{app_path}", "hotel.tianxiafen.com:83")
//                        .replace("{aid}", Enviroment.OVERSEAS_AID.getValue())
//                        .replace("{sid}", Enviroment.OVERSEAS_SID.getValue())
//                        .replace("{key}", Enviroment.OVERSEAS_KEY.getValue())
//                        .replace("{format}", Enviroment.FORMAT.getValue())
//                        .replace("{GUID}", Enviroment.OVERSEAS_UUID.getValue())
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
        } else {
            resultModel.put("ErrCode", CtripApiErrorCode.Timestampistimeout.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.Timestampistimeout.getName());
        }
        return resultModel;
    }

    /**
     * 重新获取一下token
     *
     * @return
     */
    public JSONObject getToken() {
        System.out.println("我被执行了");
        String sid = "";
        String aid = "";
        String key = "";
        String appPath = "";
        String apiUrl = "";
        String overseasAid = "";
        String overseasSid = "";
        String overseasKey = "";
        String overseasAppPath = "";
        String overseasApiUrl = "";
        JSONObject result = new JSONObject();
        RestTemplate restTemplate = new RestTemplate();
        aid = Enviroment.AID.getValue();
        sid = Enviroment.SID.getValue();
        key = Enviroment.KEY.getValue();
        apiUrl = Enviroment.API_GetAccessToken_URL.getValue();
        appPath = Enviroment.URL.getValue();
        apiUrl = apiUrl.replace("{app_path}", appPath)
                .replace("{aid}", aid)
                .replace("{sid}", sid)
                .replace("{key}", key);

        //@TODO 此处为了测试使用的弗恩获取token的URL
        apiUrl = Enviroment.FN_GetToken_URL.getValue();
        overseasAid = Enviroment.OVERSEAS_AID.getValue();
        overseasSid = Enviroment.OVERSEAS_SID.getValue();
        overseasKey = Enviroment.OVERSEAS_KEY.getValue();
        overseasAppPath = Enviroment.URL.getValue();
        overseasApiUrl = Enviroment.API_GetAccessToken_URL.getValue().replace("{app_path}", overseasAppPath)
                .replace("{aid}", overseasAid)
                .replace("{sid}", overseasSid)
                .replace("{key}", overseasKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        //@TODO 先改国外为弗恩的url
        overseasApiUrl = apiUrl.replace("hotel.tianxiafen.com:82", "hotel.tianxiafen.com:83");
        JSONObject strBody = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, JSONObject.class).getBody();
        JSONObject overseasBody = restTemplate.exchange(overseasApiUrl, HttpMethod.GET, entity, JSONObject.class).getBody();
        result.put("strBody", strBody);
        result.put("overseasBody", overseasBody);
        if (strBody.containsKey("ErrCode") || overseasBody.containsKey("ErrCode")) {
            return result;
        }
        //token
        stringRedisTemplate.opsForValue().set("token", strBody.get("Access_Token").toString(), Long.parseLong(strBody.get("Expires_In").toString()), TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set("overseasToken", overseasBody.get("Access_Token").toString(), Long.parseLong(overseasBody.get("Expires_In").toString()), TimeUnit.SECONDS);
        //refreshToken
        stringRedisTemplate.opsForValue().set("refreshToken", strBody.get("Refresh_Token").toString());
        stringRedisTemplate.opsForValue().set("overseasRefreshToken", overseasBody.get("Refresh_Token").toString());
        return result;
    }
}
