package com.fntx.admin.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: IHotelStaticInformationService
 * @Author: 魏世杰
 * @Date: 19-7-19 下午15:35
 * @Description: 酒店静态信息入库（包括 酒店清单、酒店详情、房间详情）
 */
public interface IHotelStaticInformationService {
    /**
     * 携程酒店新增酒店清单
     */
    void addHotelList(Integer current, Integer size, Integer count);

    /**
     * 获取携程酒店详情
     */
    void addHotelDetails(Integer current, Integer size, Integer count);

    /**
     * 缓存携程酒店详情数据到Mongo
     */
    void addHotelDetailsToMongo(Integer current, Integer size, Integer count);

    /**
     * 缓存携程酒店房间详情数据到Mongo
     */
    void addHotelRoomDetailsToMongo(Integer current, Integer size, Integer count);

    /**
     * 从mongo中未获取到对应的酒店详情
     *
     * @param hotelId
     * @param dealFlag 处理标识  0 标识 mongo中存在，但是Errcode,需要UPDATE ;1 标识新增
     * @param isIntel   国内 0 / 海外 1 酒店标识
     * @return
     */
    JSONObject queryHotelDetailNotInMongo(String hotelId, String dealFlag, String isIntel);

    /**
     * 从mongo中未获取到对应的房间详情
     *
     * @param hotelId
     * @param dealFlag 同上
     * @param isIntel   国内 0 / 海外 1 酒店标识
     * @return
     */
    JSONObject queryRoomDetailNotInMongo(String hotelId, String dealFlag, String isIntel);
}
