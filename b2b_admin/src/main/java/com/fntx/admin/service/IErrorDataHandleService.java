package com.fntx.admin.service;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: IErrorDataHandleService
 * @Author: 王俊文
 * @Date: 19-8-9 上午11:14
 * @Description: 增量静态信息
 */
public interface IErrorDataHandleService
{
    /**
     * @Description: 消息队列--从房型数据中获取筛选字段的值--直连报价
     * @Author: 王俊文
     * @Date: 19-8-13 上午9:53 
     * @Param: [] 
     * @returns: boolean 
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-13 上午9:53          1.0          消息队列--从房型数据中获取筛选字段的值--直连报价
     */
    boolean hotelIopriceScreeningReceiver();
    
    /**
     * @Description: 消息队列--从房型数据中获取筛选字段的值--入离起价
     * @Author: 王俊文
     * @Date: 19-8-12 下午4:46
     * @Param: [bHotelBasepriceList]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-12 下午4:46          1.0      消息队列--从房型数据中获取筛选字段的值--入离起价
     */
    boolean hotelBasepriceScreeningReceiver();

    /**
     * @Description: 直连入离报价异常错误数据处理
     * @Author: 王俊文
     * @Date: 19-8-8 上午10:24
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-8 上午10:24          1.0         直连入离报价异常错误数据处理
     */
    boolean hotelIopriceErrorHandle();

    /**
     * @Description: 直连入离报价二次异常错误数据处理
     * @Author: 王俊文
     * @Date: 19-8-9 上午10:45
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-9 上午10:45          1.0          直连入离报价二次异常错误数据处理
     */
    boolean hoteliopricErrorSecondaryHandle();

    /**
     * @Description: 城市入离起价异常数据处理
     * @Author: 王俊文
     * @Date: 19-8-9 下午1:40
     * @Param: []
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-9 下午1:40          1.0          城市入离起价异常数据处理
     */
    boolean hotelBasepriceErrorDataList();

}
