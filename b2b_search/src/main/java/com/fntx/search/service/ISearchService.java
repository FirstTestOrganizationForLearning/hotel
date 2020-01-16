package com.fntx.search.service;

import com.alibaba.fastjson.JSONObject;

public interface ISearchService
{
    /**
     * @Description: 监测静态信息变化
     * @Author: 王俊文
     * @Date: 19-7-12 下午3:11
     * @Param: [
     *                      parasJSON
     *                       ,IsIntel    酒店类型 0：国内   1：国际
     *                  ]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-12 下午3:11          1.0          监测静态信息变化
     */
    JSONObject getHotelIncrData(JSONObject parasJSON, String IsIntel);

    /**
     * @Description: 监测房型上下线
     * @Author: 王俊文
     * @Date: 19-7-12 下午3:10
     * @Param: [
     *                      parasJSON
     *                      ,IsIntel    酒店类型 0：国内   1：国际
     *                  ]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-12 下午3:10          1.0          监测房型上下线
     */
    JSONObject getHotelIncrRoomtype(JSONObject parasJSON, String IsIntel);

    /**
     * @Description: 查询某城市下各酒店的起价（国内+海外）
     * @Author: 王俊文
     * @Date: 2019/9/11 上午11:42
     * @Param: [
     *                      parasJSON
     *                      ,IsIntel    酒店类型 0：国内   1：国际
     *                  ]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/9/11 上午11:42          1.0          查询某城市下各酒店的起价（国内+海外）
     */
    JSONObject getHotelBaseprice(JSONObject parasJSON, String IsInte);

    /**
     * @Description: 查询某酒店的直连/入离报价（国内+海外）
     * @Author: 王俊文
     * @Date: 2019/9/11 上午11:42
     * @Param: [checkModel, parasJSON, IsInte]
     * @Param: [
     *                      checkModel
     *                      ,parasJSON
     *                      ,IsIntel    酒店类型 0：国内   1：国际
     *                  ]
     * @returns: com.alibaba.fastjson.JSONObject
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/9/11 上午11:42          1.0          查询某酒店的直连/入离报价（国内+海外）
     */
    JSONObject getHotelIoprice(JSONObject checkModel, JSONObject parasJSON, String IsInte);

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
    JSONObject hotelIpriceTestPrice(JSONObject checkModel, JSONObject parasJSON, String IsInte);

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/2 14:58
     * @Description: 酒店清单接口
     */
    JSONObject getHotelList(JSONObject param, String IsInte);

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/2 15:04
     * @Description: 酒店静态信息详情接口
     */
    JSONObject getHotelDetail(JSONObject param, String IsInte);

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/6 14:54
     * @Description: 房型静态接口
     */
    JSONObject getRoomtypeList(JSONObject param, String IsInte);
}
