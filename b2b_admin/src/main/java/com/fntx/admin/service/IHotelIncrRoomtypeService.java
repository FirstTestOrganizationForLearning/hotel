package com.fntx.admin.service;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: IHotelIncrRoomtypeService
 * @Author: 王俊文
 * @Date: 19-8-9 下午3:12
 * @Description: 房型上下架
 */
public interface IHotelIncrRoomtypeService
{
    /**
     * @Description: 监测房型上下架
     * @Author: 王俊文
     * @Date: 19-7-15 下午4:54
     * @Param: [StartTime, EndTime]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-15 下午4:54          1.0          监测房型上下架
     */
    boolean getHotelIncrRoomtype(String StartTime, String EndTime);

    /**
     * @Description: 房型上下架-调用接口-入库
     * @Author: 王俊文
     * @Date: 19-7-16 上午9:36
     * @Param: [test]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-16 上午9:36          1.0          房型上下架-调用接口-入库
     */
    boolean hotelIncrRoomtypeWarehousing(String hotelIncrRoomTypeStr);
}
