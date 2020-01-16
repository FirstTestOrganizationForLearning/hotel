package com.fntx.admin.service;

import com.fntx.common.domain.BHotelRoomPriceInfos;
import com.fntx.common.domain.dto.HotelRoomDetailResModel;

import java.util.List;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: IHotelIopriceService
 * @Author: 王俊文
 * @Date: 19-7-15 上午10:07
 * @Description: 直连报价
 */
public interface IHotelIopriceService
{
    /**
     * @Description: 从房型数据中获取筛选字段的值
     * @Author: 王俊文
     * @Date: 19-8-13 上午9:51
     * @Param: [bHotelRoomPriceInfosList]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-13 上午9:51          1.0          从房型数据中获取筛选字段的值
     */
    boolean hotelIopriceScreening(List<BHotelRoomPriceInfos> bHotelRoomPriceInfosList);

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
    boolean getHotelIoprice(String HotelID);

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
    boolean hotelIopriceStorage(String RoomPriceItems, String HotelID);

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
    List<HotelRoomDetailResModel.RoomStaticInfosBean> hotelIopriceStorageMongodbRoomTypeList(String HotelID,
                                                                                             String isIntel);

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
    boolean hotelHotCitiesIoprice();

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
    boolean hotelIopriceCache(String HotelID);

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
    List<Long> hotelCityCache();

    //热门城市酒店报价入库
    boolean hotelIoprice();
}
