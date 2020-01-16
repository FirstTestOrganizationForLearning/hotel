package com.fntx.admin.service;

import com.fntx.common.domain.BHotelBaseprice;

import java.util.List;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: IHotelBasepriceService
 * @Author: 王俊文
 * @Date: 19-8-9 下午3:16
 * @Description: 城市入离起价
 */
public interface IHotelBasepriceService
{
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
    boolean hotelBasepriceScreening(List<BHotelBaseprice> bHotelBasepriceList);

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
    boolean hotelBaseprice(String City);

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
    boolean getHotelBaseprice();

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
    boolean hotelBasepriceStorageWirelessSearch(String HotelBasepriceDto);

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
    boolean hotelBasepriceStorageOnLineIntlGTASearch(String HotelBasepriceDto);
}
