package com.fntx.admin.service;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: IErrorDataHandleService
 * @Author: 王俊文
 * @Date: 19-8-9 下午3:08
 * @Description:
 */
public interface IHotelIncrDataService
{
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
    boolean getHotelIncrData(String StartTime);

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
    boolean getHotelIncrDataInternational(String StartTime);

    /**
     * @Description: 静态信息-调用接口-入库
     * @Author: 王俊文
     * @Date: 19-7-16 上午9:36
     * @Param: [test]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-16 上午9:36          1.0          静态信息-调用接口-入库
     */
    boolean hotelIncrDataWarehousing(String hotelIncrDataStr, String isInte);

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
    boolean IncrDataHotelWarehousing();

    /**
     * @Description: 静态信息-对应物理房型变动同步--国内
     * @Author: 王俊文
     * @Date: 19-8-2 下午6:07
     * @Param: [hotelIncrDataStr]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-2 下午6:07          1.0          对应物理房型变动同步
     */
    boolean IncrDataRoomTypeWarehousing();

    /**
     * @Description: 静态信息-对应售卖房型变动同步--国内
     * @Author: 王俊文
     * @Date: 19-8-2 下午6:07
     * @Param: [hotelIncrDataStr]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-2 下午6:07          1.0
     */
    boolean IncrDataRoomWarehousing();

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
    boolean IncrDataHotelWarehousingInternational();

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
    boolean IncrDataRoomTypeWarehousingInternational();

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
    boolean IncrDataRoomWarehousingInternational();
}
