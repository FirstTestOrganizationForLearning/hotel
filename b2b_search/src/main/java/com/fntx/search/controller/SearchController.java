package com.fntx.search.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fntx.common.constant.CtripApiErrorCode;
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.domain.ErrorResp;
import com.fntx.common.domain.dto.*;
import com.fntx.common.utils.*;
import com.fntx.search.feign.AdminFeign;
import com.fntx.search.service.ISearchService;
import com.fntx.search.utils.ReturnUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("")
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private ISearchService iSearchService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    AdminFeign adminFeign;
    @Autowired
    private ReturnUtils returnUtils;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/1 11:02
     * @Description: 对外-分销商api-酒店清单接口
     */
    @PostMapping("hotel.list")
    public JSONObject hotelList(BaseInfo baseInfo, @RequestBody JSONObject paramMap) {
        JSONObject resultModel = new JSONObject(16);
        try {
            //验参数
            JSONObject parasMap = paramMap;
            if (baseInfo == null || baseInfo.getAID() == null || baseInfo.getAID().equals("")) {
                baseInfo = JSONObject.parseObject(paramMap.getJSONObject("baseInfo").toJSONString(), BaseInfo.class);
                if (paramMap.getJSONObject("param") != null) {
                    parasMap = paramMap.getJSONObject("param");
                }
            }
            logger.info("酒店清单接口 hotel.list/入参baseInfo:" + baseInfo.toString() + "param:" + paramMap);
            //  验token
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                resultModel.put("ErrCode", CtripApiErrorCode.tokeninvalid.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.tokeninvalid.getName());
                return resultModel;
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), "");
            JSONObject isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            // 酒店清单的请求 & 响应
            HotelListReqModel hotelListRequestInfo = null;
            HotelListSaveModel hotelListSaveModel = null;
            HotelListResponseInfo hotelListResponseInfo = null;
            // 验param
            hotelListRequestInfo = JSONObject.toJavaObject(parasMap, HotelListReqModel.class);
            if (null != hotelListRequestInfo) {
                // TODO 分销商请求入参正常转换javaObj 后续处理从缓存拿数据/缓存没有，去调携程接口获取后入缓存 并响应
                Query query = new Query(Criteria.where("hotelListReqModel.city").is(hotelListRequestInfo.getCity()).and("hotelListReqModel.pageIndex").is(hotelListRequestInfo.getPageIndex()).and("hotelListReqModel.pageSize").is(hotelListRequestInfo.getPageSize()));
                Update update = new Update();
                String collectionName = "hotelListSaveModel";
                hotelListSaveModel = mongoTemplate.findOne(query, HotelListSaveModel.class);
                if (null != hotelListSaveModel) {
                    hotelListResponseInfo = hotelListSaveModel.getHotelListResponseInfo();
                    if (!StringUtil.empty(hotelListResponseInfo.getErrCode())) {
                        // TODO 入缓存的时候就出错了，重新去携程获取一下。并且更新到MongoDB里面
                        resultModel = iSearchService.getHotelList(parasMap, " 0");
                        if (null != resultModel) {
                            hotelListResponseInfo = JSON.toJavaObject(resultModel, HotelListResponseInfo.class);
                            update.set("hotelListResponseInfo", hotelListResponseInfo);
                            mongoTemplate.updateFirst(query, update, collectionName);
                        }
                    } else {
                        String res = JSONObject.toJSONString(hotelListResponseInfo);
                        resultModel = JSONObject.parseObject(res);
                    }
                } else {
                    logger.info("缓存中存的酒店清单的数据为空·需要重新去接口获取。");
                    resultModel = iSearchService.getHotelList(parasMap, "0");
                    hotelListResponseInfo = JSON.toJavaObject(resultModel, HotelListResponseInfo.class);
                    hotelListSaveModel = new HotelListSaveModel();
                    hotelListSaveModel.setHotelListReqModel(hotelListRequestInfo);
                    hotelListSaveModel.setHotelListResponseInfo(hotelListResponseInfo);
                    mongoTemplate.save(hotelListSaveModel);
                }
            } else {
                logger.info("酒店清单接口 入参转化javaObj 为空！！！");
            }
        } catch (Exception e) {
            //返回错误信息
            logger.error("hotel.list  发生异常，异常信息为：" + e.toString());
            resultModel.put("ErrCode", CtripApiErrorCode.ApiInternalError.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.ApiInternalError.getName());
            e.printStackTrace();
        }
        return resultModel;
    }

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/1 11:02
     * @Description: 对外-分销商api-酒店详情接口
     */
    @PostMapping("hotel.detail")
    public JSONObject hotelDetail(BaseInfo baseInfo, @RequestBody JSONObject paramMap) {
        JSONObject resultModel = new JSONObject(16);
        try {
            //验参数
            JSONObject isError = new JSONObject();
            JSONObject parasMap = paramMap;
            if (baseInfo == null || baseInfo.getAID() == null || baseInfo.getAID().equals("")) {
                baseInfo = JSONObject.parseObject(paramMap.getJSONObject("baseInfo").toJSONString(), BaseInfo.class);
                if (paramMap.getJSONObject("param") != null) {
                    parasMap = paramMap.getJSONObject("param");
                }
            }
            logger.info("酒店详情接口 hotel.detail/入参baseInfo:" + baseInfo.toString() + "param:" + paramMap);
            //  验token
            SearchCandi searchCandi = null;
            searchCandi = JSON.toJavaObject(parasMap, SearchCandi.class);
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                resultModel.put("ErrCode", CtripApiErrorCode.tokeninvalid.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.tokeninvalid.getName());
                return resultModel;
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), String.valueOf(searchCandi.getSearchCandidate().getHotelID()));
            isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            // 酒店详情的请求 & 响应
            HotelMongoModel HotelMongoModel = null;
            Query query = new Query(Criteria.where("hotelId").is(String.valueOf(searchCandi.getSearchCandidate().getHotelID())));
            Update update = new Update();
            String collectionName = "hotelMongoModel";
            HotelMongoModel = mongoTemplate.findOne(query, HotelMongoModel.class);

            if (null != HotelMongoModel) {
                if (null != HotelMongoModel.getHotelDetail()) {
                    HotelStaticResPonse hotelStatic = FastJsonUtils.toBean(HotelMongoModel.getHotelDetail(), HotelStaticResPonse.class);
                    if (!StringUtil.empty(hotelStatic.getErrCode())) {
                        logger.info("缓存中存的酒店静态详情的数据有误·需要重新去接口获取。");
                        resultModel = iSearchService.getHotelDetail(parasMap, moduleId);
                        if (null != resultModel) {
                            update.set("hotelDetail", resultModel);
                            mongoTemplate.updateFirst(query, update, collectionName);
                        }
                    } else {
                        resultModel = JSON.parseObject(HotelMongoModel.getHotelDetail());
                    }
                }
            } else {
                logger.info("缓存中存的酒店静态详情的数据为空·需要重新去接口获取。");
                resultModel = iSearchService.getHotelDetail(parasMap, "0");
                HotelMongoModel = new HotelMongoModel();
                HotelMongoModel.setHotelId(String.valueOf(searchCandi.getSearchCandidate().getHotelID()));
                HotelMongoModel.setHotelDetail(JSON.toJSONString(resultModel));
                mongoTemplate.save(HotelMongoModel);
            }
        } catch (Exception e) {
            logger.error("hotel.detail  发生异常，异常信息为：" + e.getMessage());
            resultModel.put("ErrCode", CtripApiErrorCode.ApiInternalError.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.ApiInternalError.getName());
            e.printStackTrace();
        }
        return resultModel;
    }

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/6 13:52
     * @Description: 对外-分销商api-房型静态接口
     */
    @PostMapping("hotel.roomtype.list")
    public JSONObject hotelRoomtypeList(BaseInfo baseInfo, @RequestBody JSONObject paramMap) {
        JSONObject resultModel = new JSONObject(16);
        try {
            //验参数
            JSONObject isError = new JSONObject();
            JSONObject parasMap = paramMap;
            if (baseInfo == null || baseInfo.getAID() == null || baseInfo.getAID().equals("")) {
                baseInfo = JSONObject.parseObject(paramMap.getJSONObject("baseInfo").toJSONString(), BaseInfo.class);
                if (paramMap.getJSONObject("param") != null) {
                    parasMap = paramMap.getJSONObject("param");
                }
            }
            logger.info("房型静态接口 hotel.roomtype.list/入参baseInfo:" + baseInfo.toString() + "param:" + paramMap);
            //  验token
            HotelRoomDetailReqModel hotelRoomDetailReqModel = null;
            hotelRoomDetailReqModel = JSON.toJavaObject(parasMap, HotelRoomDetailReqModel.class);
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                resultModel.put("ErrCode", CtripApiErrorCode.tokeninvalid.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.tokeninvalid.getName());
                return resultModel;
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), String.valueOf(hotelRoomDetailReqModel.getSearchCandidate().getHotelID()));
            isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }

            // 酒店详情的请求 & 响应
            List<?> RoomIDs = hotelRoomDetailReqModel.getSearchCandidate().getRoomIDs();

            HotelRoomMongoModel hotelRoomMongoModel = null;
            Query query = new Query(Criteria.where("hotelId").is(String.valueOf(hotelRoomDetailReqModel.getSearchCandidate().getHotelID())));
            Update update = new Update();
            String collectionName = "roomMongoModel";
            hotelRoomMongoModel = mongoTemplate.findOne(query, HotelRoomMongoModel.class);

            if (null != RoomIDs && RoomIDs.size() > 0) {
                resultModel = iSearchService.getRoomtypeList(parasMap, moduleId);
            } else {
                if (null != hotelRoomMongoModel) {
                    if (null != hotelRoomMongoModel.getHotelRoomDetail()) {
                        HotelRoomDetailResModel HotelRoomDetailResModel = FastJsonUtils.toBean(hotelRoomMongoModel.getHotelRoomDetail(), HotelRoomDetailResModel.class);
                        if (!StringUtil.empty(HotelRoomDetailResModel.getErrCode())) {
                            logger.info("缓存中存的房型静态详情的数据有误·需要重新去接口获取。");
                            resultModel = iSearchService.getRoomtypeList(parasMap, moduleId);
                            if (null != resultModel) {
                                update.set("hotelRoomDetail", resultModel);
                                mongoTemplate.updateFirst(query, update, collectionName);
                            }
                        } else {
                            resultModel = JSON.parseObject(hotelRoomMongoModel.getHotelRoomDetail());
                        }
                    }
                } else {
                    logger.info("缓存中存的房型静态详情的数据为空·需要重新去接口获取。");
                    resultModel = iSearchService.getRoomtypeList(parasMap, moduleId);
                    hotelRoomMongoModel = new HotelRoomMongoModel();
                    hotelRoomMongoModel.setHotelId(String.valueOf(hotelRoomDetailReqModel.getSearchCandidate().getHotelID()));
                    hotelRoomMongoModel.setHotelRoomDetail(JSON.toJSONString(resultModel));
                    mongoTemplate.save(hotelRoomMongoModel);
                }
            }
        } catch (Exception e) {
            logger.error("hotel.roomtype.list  发生异常，异常信息为：" + e.getMessage());
            resultModel.put("ErrCode", CtripApiErrorCode.ApiInternalError.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.ApiInternalError.getName());
            e.printStackTrace();
        }
        return resultModel;
    }

    /**
     * @Description: 对外-分销商api-监测静态信息变化
     * @Author: 王俊文
     * @Date: 19-7-8 上午11:22
     * @Param: [parasMap, request, response]
     * @returns: java.util.Map<java.lang.String, java.lang.Object>
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-8 上午11:22          1.0
     */
    @PostMapping("hotel.incr.data")
    public JSONObject hotelIncrData(BaseInfo baseInfo, @RequestBody JSONObject paramMap) {
        JSONObject parasMap = paramMap;
        if (baseInfo == null || baseInfo.getAID() == null || baseInfo.getAID().equals("")) {
            baseInfo = JSONObject.parseObject(paramMap.getJSONObject("baseInfo").toJSONString(), BaseInfo.class);
            if (paramMap.getJSONObject("param") != null) {
                parasMap = paramMap.getJSONObject("param");
            }
        }
        JSONObject resultModel = new JSONObject(16);

        String AID = baseInfo.getAID();
        String SID = baseInfo.getSID();
        String UUID = baseInfo.getUUID();
        String Token = baseInfo.getToken();
        String ICODE = baseInfo.getICODE();

        logger.info(
                "\n***************************************" + "\n" +
                        "start hotelIncrData" + "\n" +
                        "对外-分销商api-监测静态信息变化" + "\n" +
                        "AID = " + AID + "\n" +
                        "SID = " + SID + "\n" +
                        "UUID = " + UUID + "\n" +
                        "Token = " + Token + "\n" +
                        "ICODE = " + ICODE + "\n" +
                        "\n********************************************"
        );

        try {
            //校验签名信息
            Assert.isTrue(StringUtils.isNotBlank(AID), "传入参数有误!");
            Assert.isTrue(StringUtils.isNotBlank(SID), "传入参数有误!");
            Assert.isTrue(StringUtils.isNotBlank(UUID), "传入参数有误!");
            Assert.isTrue(StringUtils.isNotBlank(ICODE), "传入参数有误!");
            //token校验
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() +
                    ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                resultModel.put("ErrCode", CtripApiErrorCode.tokeninvalid.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.tokeninvalid.getName());
                return resultModel;
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), null);
            if (!"0".equals(moduleId) && !"1".equals(moduleId)) {
                ErrorResp errorResp = new ErrorResp();
                errorResp.setErrCode(CtripApiErrorCode.PermissionDenied.getCode());
                errorResp.setErrMsg(CtripApiErrorCode.PermissionDenied.getName());
                return JSON.parseObject(JSONObject.toJSONString(errorResp));
            }

            //校验接口信息
            String ICODEs = "hotel.incr.data";
            Assert.isTrue(ICODEs.equals(ICODE), "传入参数有误!");
            //校验传入参数


            //请求服务
            resultModel = iSearchService.getHotelIncrData(parasMap, moduleId);

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
            resultModel.put("ErrCode", CtripApiErrorCode.InvalidSIdOrAllianceId.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.InvalidSIdOrAllianceId.getName());
        } catch (Exception e) {
            e.printStackTrace();
            resultModel.put("ErrCode", CtripApiErrorCode.ApiInternalError.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.ApiInternalError.getName());
        }

        logger.info(
                "\n***************************************" + "\n" +
                        "end hotelIncrData" + "\n" +
                        "对外-分销商api-监测静态信息变化" + "\n" +
//            "resultModel = " + resultModel + "\n" +
                        "\n********************************************"
        );
        return resultModel;
    }

    /**
     * @Description: 对外-分销商api-监测房型上下线-停用
     * @Author: 王俊文
     * @Date: 19-7-19 上午9:31
     * @Param: [parasMap, request, response]
     * @returns: java.util.Map<java.lang.String, java.lang.Object>
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-19 上午9:31          1.0          对外-分销商api-监测房型上下线
     */
    @PostMapping("hotel.incr.roomtype")
    public JSONObject getHotelIncrRoomtype(BaseInfo baseInfo, @RequestBody JSONObject paramMap) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", 100000);
        resultModel.put("ErrMsg", "当前接口已停用");
        return resultModel;

        /**  接口停用,逻辑封印
         JSONObject parasMap = paramMap;
         if ( baseInfo == null || baseInfo.getAID() == null || baseInfo.getAID().equals("") )
         {
         baseInfo = JSONObject.parseObject(paramMap.getJSONObject("baseInfo").toJSONString(), BaseInfo.class);
         if ( paramMap.getJSONObject("param") != null )
         {
         parasMap = paramMap.getJSONObject("param");
         }
         }

         String AID = baseInfo.getAID();
         String SID = baseInfo.getSID();
         String UUID = baseInfo.getUUID();
         String Token = baseInfo.getToken();
         String ICODE = baseInfo.getICODE();

         logger.info(
         "\n***************************************" + "\n" +
         "start getHotelIncrRoomtype" + "\n" +
         "对外-分销商api-监测房型上下线" + "\n" +
         "AID = " + AID + "\n" +
         "SID = " + SID + "\n" +
         "UUID = " + UUID + "\n" +
         "Token = " + Token + "\n" +
         "ICODE = " + ICODE + "\n" +
         "\n********************************************"
         );

         try
         {
         //校验签名信息
         Assert.isTrue(StringUtils.isNotBlank(AID), "传入参数有误!");
         Assert.isTrue(StringUtils.isNotBlank(SID), "传入参数有误!");
         Assert.isTrue(StringUtils.isNotBlank(UUID), "传入参数有误!");
         Assert.isTrue(StringUtils.isNotBlank(ICODE), "传入参数有误!");
         //token校验
         String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":token");
         if ( !StringUtils.isNotBlank(redisToken) )
         {
         resultModel.put("ErrCode", CtripApiErrorCode.tokeninvalid.getCode());
         resultModel.put("ErrMsg", CtripApiErrorCode.tokeninvalid.getName());
         return resultModel;
         }
         String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), null);
         if (!"0".equals(moduleId) && !"1".equals(moduleId))
         {
         ErrorResp errorResp = new ErrorResp();
         errorResp.setErrCode(CtripApiErrorCode.PermissionDenied.getCode());
         errorResp.setErrMsg(CtripApiErrorCode.PermissionDenied.getName());
         return JSON.parseObject(JSONObject.toJSONString(errorResp));
         }

         //校验接口信息
         String ICODEs = "hotel.incr.roomtype";
         Assert.isTrue(ICODEs.equals(ICODE) , "传入参数有误!");
         //校验传入参数


         //请求服务
         resultModel = iSearchService.getHotelIncrRoomtype(parasMap, moduleId);
         }
         catch (IllegalArgumentException ie)
         {
         ie.printStackTrace();
         resultModel.put("ErrCode", CtripApiErrorCode.InvalidSIdOrAllianceId.getCode());
         resultModel.put("ErrMsg", CtripApiErrorCode.InvalidSIdOrAllianceId.getName());
         }
         catch (Exception e)
         {
         e.printStackTrace();
         resultModel.put("ErrCode", CtripApiErrorCode.ApiInternalError.getCode());
         resultModel.put("ErrMsg", CtripApiErrorCode.ApiInternalError.getName());
         }

         logger.info(
         "\n***************************************" + "\n" +
         "end getHotelIncrRoomtype" + "\n" +
         "对外-分销商api-监测房型上下线" + "\n" +
         //            "resultModel = " + resultModel + "\n" +
         "\n********************************************"
         );
         return resultModel;
         */
    }

    /**
     * @Description: 对外-分销商api-查询某城市下各酒店的起价（国内+海外）
     * @Author: 王俊文
     * @Date: 19-7-19 上午9:32
     * @Param: [parasMap, request, response]
     * @returns: java.util.Map<java.lang.String, java.lang.Object>
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-19 上午9:32          1.0          对外-分销商api-查询某城市下各酒店的起价（国内+海外）
     */
    @PostMapping("hotel.baseprice")
    public JSONObject getHotelBaseprice(BaseInfo baseInfo, @RequestBody JSONObject paramMap) {
        JSONObject parasMap = paramMap;
        if (baseInfo == null || baseInfo.getAID() == null || baseInfo.getAID().equals("")) {
            baseInfo = JSONObject.parseObject(paramMap.getJSONObject("baseInfo").toJSONString(), BaseInfo.class);
            if (paramMap.getJSONObject("param") != null) {
                parasMap = paramMap.getJSONObject("param");
            }
        }
        JSONObject resultModel = new JSONObject(16);

        String AID = baseInfo.getAID();
        String SID = baseInfo.getSID();
        String UUID = baseInfo.getUUID();
        String Token = baseInfo.getToken();
        String ICODE = baseInfo.getICODE();

        logger.info(
                "\n***************************************" + "\n" +
                        "start getHotelBaseprice" + "\n" +
                        "对外-分销商api-查询某城市下各酒店的起价（国内+海外）" + "\n" +
                        "AID = " + AID + "\n" +
                        "SID = " + SID + "\n" +
                        "UUID = " + UUID + "\n" +
                        "Token = " + Token + "\n" +
                        "ICODE = " + ICODE + "\n" +
                        "\n********************************************"
        );

        try {
            //校验签名信息
            Assert.isTrue(StringUtils.isNotBlank(AID), "传入参数有误!");
            Assert.isTrue(StringUtils.isNotBlank(SID), "传入参数有误!");
            Assert.isTrue(StringUtils.isNotBlank(UUID), "传入参数有误!");
            Assert.isTrue(StringUtils.isNotBlank(ICODE), "传入参数有误!");
            //token校验
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() +
                    ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                resultModel.put("ErrCode", CtripApiErrorCode.tokeninvalid.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.tokeninvalid.getName());
                return resultModel;
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), null);

            if (!"0".equals(moduleId) && !"1".equals(moduleId)) {
                ErrorResp errorResp = new ErrorResp();
                errorResp.setErrCode(CtripApiErrorCode.PermissionDenied.getCode());
                errorResp.setErrMsg(CtripApiErrorCode.PermissionDenied.getName());
                return JSON.parseObject(JSONObject.toJSONString(errorResp));
            }


            //校验接口信息
            String ICODEs = "hotel.baseprice";
            Assert.isTrue(ICODEs.equals(ICODE), "传入参数有误!");

            //请求服务
            resultModel = iSearchService.getHotelBaseprice(parasMap, moduleId);
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
            resultModel.put("ErrCode", CtripApiErrorCode.InvalidSIdOrAllianceId.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.InvalidSIdOrAllianceId.getName());
        } catch (Exception e) {
            e.printStackTrace();
            resultModel.put("ErrCode", CtripApiErrorCode.ApiInternalError.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.ApiInternalError.getName());
        }

        logger.info(
                "\n***************************************" + "\n" +
                        "end getHotelBaseprice" + "\n" +
                        "对外-分销商api-查询某城市下各酒店的起价（国内+海外）" + "\n" +
//            "resultModel = " + resultModel + "\n" +
                        "\n********************************************"
        );
        return resultModel;
    }

    /**
     * @Description: 对外-分销商api-查询某酒店的直连/入离报价（国内+海外）
     * @Author: 王俊文
     * @Date: 19-7-19 上午9:33
     * @Param: [parasMap, request, response]
     * @returns: java.util.Map<java.lang.String, java.lang.Object>
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-19 上午9:33          1.0          对外-分销商api-查询某酒店的直连/入离报价（国内+海外）
     */
    @PostMapping("hotel.ioprice")
    public JSONObject getHotelIoprice(BaseInfo baseInfo, @RequestBody JSONObject paramMap) {
        JSONObject parasMap = paramMap;
        if (baseInfo == null || baseInfo.getAID() == null || baseInfo.getAID().equals("")) {
            baseInfo = JSONObject.parseObject(paramMap.getJSONObject("baseInfo").toJSONString(), BaseInfo.class);
            if (paramMap.getJSONObject("param") != null) {
                parasMap = paramMap.getJSONObject("param");
            }
        }
        JSONObject resultModel = new JSONObject(16);

        String AID = baseInfo.getAID();
        String SID = baseInfo.getSID();
        String UUID = baseInfo.getUUID();
        String Token = baseInfo.getToken();
        String ICODE = baseInfo.getICODE();

        logger.info(
                "\n***************************************" + "\n" +
                        "start getHotelIoprice" + "\n" +
                        "对外-分销商api-查询某酒店的直连/入离报价（国内+海外）" + "\n" +
                        "AID = " + AID + "\n" +
                        "SID = " + SID + "\n" +
                        "UUID = " + UUID + "\n" +
                        "Token = " + Token + "\n" +
                        "ICODE = " + ICODE + "\n" +
                        "\n********************************************"
        );

        try {
            //校验签名信息
            Assert.isTrue(StringUtils.isNotBlank(AID), "传入参数有误!");
            Assert.isTrue(StringUtils.isNotBlank(SID), "传入参数有误!");
            Assert.isTrue(StringUtils.isNotBlank(UUID), "传入参数有误!");
            Assert.isTrue(StringUtils.isNotBlank(ICODE), "传入参数有误!");
            //token校验
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() +
                    ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                resultModel.put("ErrCode", CtripApiErrorCode.tokeninvalid.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.tokeninvalid.getName());
                return resultModel;
            }

            //校验接口信息
            String ICODEs = "hotel.ioprice";
            Assert.isTrue(ICODEs.equals(ICODE), "传入参数有误!");
            //校验传入参数
            JSONObject checkModel = hotelIpricePriceCheck(parasMap);
            JSONObject responseStatus = checkModel.getJSONObject("responseStatus");
            if (responseStatus == null || responseStatus.getString("Ack").compareTo("Success") != 0) {
                return checkModel;
            }
            //token酒店类型校验
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), checkModel.getString("HotelID"));

            if (!"0".equals(moduleId) && !"1".equals(moduleId)) {
                resultModel.put("ErrCode", CtripApiErrorCode.PermissionDenied.getCode());
                resultModel.put("ErrMsg", CtripApiErrorCode.PermissionDenied.getName());
                return resultModel;
            }

            //校验当前请求是否为重复请求
            //md5转换当前请求体
            String md5ParasMap = MD5Util.encrypt(parasMap.toJSONString());
            Object returnMap = redisTemplate.opsForValue().get("HotelIoprice_" + md5ParasMap);
            if (returnMap != null) {
                resultModel = JSONObject.parseObject(returnMap.toString());
            } else {
                //请求服务
                resultModel = iSearchService.getHotelIoprice(checkModel, parasMap, moduleId);
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
            resultModel.put("ErrCode", CtripApiErrorCode.InvalidSIdOrAllianceId.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.InvalidSIdOrAllianceId.getName());
        } catch (Exception e) {
            e.printStackTrace();
            resultModel.put("ErrCode", CtripApiErrorCode.ApiInternalError.getCode());
            resultModel.put("ErrMsg", CtripApiErrorCode.ApiInternalError.getName());
        }

        logger.info(
                "\n***************************************" + "\n" +
                        "end getHotelIoprice" + "\n" +
                        "对外-分销商api-查询某酒店的直连/入离报价（国内+海外）" + "\n" +
//            "resultModel = " + resultModel + "\n" +
                        "\n********************************************"
        );
        return resultModel;
    }

    /**
     * @Description: 直连报价--验价-停用
     * @Author: 王俊文
     * @Date: 19-7-26 下午4:33
     * @Param: [parasMap, request, response]
     * @returns: java.util.Map<java.lang.String, java.lang.Object>
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-26 下午4:33          1.0     直连报价--验价  --提交订单时调用
     */
    @PostMapping("hotel.testprice")
    public JSONObject hotelIpriceTestPrice(BaseInfo baseInfo, @RequestBody JSONObject paramMap) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", 100000);
        resultModel.put("ErrMsg", "当前接口已停用");
        return resultModel;

        /**  接口停用,逻辑封印
         JSONObject parasMap = paramMap;
         if ( baseInfo == null || baseInfo.getAID() == null || baseInfo.getAID().equals("") )
         {
         baseInfo = JSONObject.parseObject(paramMap.getJSONObject("baseInfo").toJSONString(), BaseInfo.class);
         if ( paramMap.getJSONObject("param") != null )
         {
         parasMap = paramMap.getJSONObject("param");
         }
         }

         String AID = baseInfo.getAID();
         String SID = baseInfo.getSID();
         String UUID = baseInfo.getUUID();
         String Token = baseInfo.getToken();
         String ICODE = baseInfo.getICODE();

         logger.info(
         "\n***************************************" + "\n" +
         "start getHotelIoprice" + "\n" +
         "对外-分销商api-直连报价--验价" + "\n" +
         "AID = " + AID + "\n" +
         "SID = " + SID + "\n" +
         "UUID = " + UUID + "\n" +
         "Token = " + Token + "\n" +
         "ICODE = " + ICODE + "\n" +
         "\n********************************************"
         );

         try {
         //校验签名信息
         Assert.isTrue(StringUtils.isNotBlank(AID), "传入参数有误!");
         Assert.isTrue(StringUtils.isNotBlank(SID), "传入参数有误!");
         Assert.isTrue(StringUtils.isNotBlank(UUID), "传入参数有误!");
         Assert.isTrue(StringUtils.isNotBlank(ICODE), "传入参数有误!");
         //token校验
         String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":token");
         if ( !StringUtils.isNotBlank(redisToken) )
         {
         resultModel.put("ErrCode", CtripApiErrorCode.tokeninvalid.getCode());
         resultModel.put("ErrMsg", CtripApiErrorCode.tokeninvalid.getName());
         return resultModel;
         }

         //校验接口信息
         String ICODEs = "hotel.testprice";
         Assert.isTrue(ICODEs.equals(ICODE), "传入参数有误!");
         //校验传入参数
         JSONObject checkModel = hotelIpricePriceCheck(parasMap);
         JSONObject responseStatus = checkModel.getJSONObject("responseStatus");
         if ( responseStatus.getString("Ack").compareTo("Success") != 0 )
         {
         return checkModel;
         }

         //token酒店类型校验
         String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), checkModel.getString("HotelID"));

         if (!"0".equals(moduleId) && !"1".equals(moduleId))
         {
         resultModel.put("ErrCode", CtripApiErrorCode.PermissionDenied.getCode());
         resultModel.put("ErrMsg", CtripApiErrorCode.PermissionDenied.getName());
         return resultModel;
         }

         //请求服务
         resultModel = iSearchService.hotelIpriceTestPrice( checkModel, parasMap, moduleId);

         }
         catch (IllegalArgumentException ie)
         {
         ie.printStackTrace();
         resultModel.put("ErrCode", CtripApiErrorCode.InvalidSIdOrAllianceId.getCode());
         resultModel.put("ErrMsg", CtripApiErrorCode.InvalidSIdOrAllianceId.getName());
         }
         catch (Exception e)
         {
         e.printStackTrace();
         resultModel.put("ErrCode", CtripApiErrorCode.ApiInternalError.getCode());
         resultModel.put("ErrMsg", CtripApiErrorCode.ApiInternalError.getName());
         }

         logger.info(
         "\n***************************************" + "\n" +
         "end getHotelIoprice" + "\n" +
         "对外-分销商api-直连报价--验价" + "\n" +
         //            "resultModel = " + resultModel + "\n" +
         "\n********************************************"
         );
         return resultModel;
         */
    }

    /**
     * 直连报价参数校验
     *
     * @param parasJSON
     * @return
     */
    private JSONObject hotelIpricePriceCheck(JSONObject parasJSON) throws Exception {
        //返回参数
        JSONObject resultModel = new JSONObject(16);
        JSONObject responseStatus = returnUtils.responseStatus();
        resultModel.put("responseStatus", responseStatus);

        //校验请求参数
        if (parasJSON.getJSONObject("SearchCandidate").isEmpty()) {
            return returnUtils.parameterEmpty(responseStatus);
        }
        JSONObject SearchCandidate = parasJSON.getJSONObject("SearchCandidate");
        resultModel.put("SearchCandidate", SearchCandidate);

        //校验二级参数
        //无HotelId时错误信息
        if (SearchCandidate.get("HotelID") == null || "".equals(SearchCandidate.get("HotelID"))) {
            return returnUtils.InvalidHotelID(responseStatus);
        }
        //酒店id
        resultModel.put("HotelID", SearchCandidate.getString("HotelID"));

        //获取物理房型id 及 售卖房型id  用于给分销商返回预定房型的入离报价信息
        if (SearchCandidate.get("RoomTypeID") != null && !"".equals(SearchCandidate.get("RoomTypeID"))) {
            //物理房型id
            resultModel.put("RoomTypeID", SearchCandidate.getString("RoomTypeID"));
        }
        if (SearchCandidate.get("RoomID") != null && !"".equals(SearchCandidate.get("RoomID"))) {
            //售卖房型id
            resultModel.put("RoomID", SearchCandidate.getString("RoomID"));
        }
        //无DateRange时错误信息
        if (SearchCandidate.get("DateRange") == null || "".equals(SearchCandidate.get("DateRange"))) {
            return returnUtils.invalidDate(responseStatus);
        }
        JSONObject DateRange = SearchCandidate.getJSONObject("DateRange");
        if (DateRange.get("Start") == null || "".equals(DateRange.get("Start")) ||
                DateRange.get("End") == null || "".equals(DateRange.get("End"))) {
            return returnUtils.invalidDate(responseStatus);
        }
        Date Start = null, End = null;
        try {
            Start = TimeUtil.parseDate(DateRange.getString("Start"), TimeUtil.YEAR_MONTH_DAY);
            End = TimeUtil.parseDate(DateRange.getString("End"), TimeUtil.YEAR_MONTH_DAY);
            resultModel.put("Start", Start);
            resultModel.put("End", End);
        } catch (ParseException e) {
            logger.info("错误开始时间:" + DateRange.getString("Start"));
            logger.info("错误结束时间:" + DateRange.getString("End"));
            e.printStackTrace();
        }
        if (Start.compareTo(End) == 1) {
            return returnUtils.invalidDate(responseStatus);
        }
        return resultModel;
    }

    /**
     * 分销商token校验
     *
     * @param baseInfo
     * @return
     */
    private JSONObject tokenCheck(BaseInfo baseInfo) {
        String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":token");
        String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), null);
        return ValaditionUtils.tokenVerify(moduleId);
    }

    /**
     * 分销商token,及酒店类型校验
     *
     * @param baseInfo
     * @param HotelID
     * @return
     */
    private JSONObject tokenCheck(BaseInfo baseInfo, String HotelID) {
        String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":token");
        String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), HotelID);
        return ValaditionUtils.tokenVerify(moduleId);
    }
}
