package com.fntx.sdk.feign;


import com.alibaba.fastjson.JSONObject;
import com.fntx.common.domain.BaseInfo;
import com.fntx.sdk.feign.hystrix.SearchHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 查询相关服务调用
 * @Author: 王俊文
 * @Date: 2019/7/29 17:39
 * @Description:
 */
@FeignClient(value = "b2bSearch", fallback = SearchHystrix.class)
public interface SearchFeign {
    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/1 14:36
     * @Description: 对外-分销商api-酒店清单接口
     */
    @PostMapping("hotel.list")
    JSONObject hotelList(@RequestBody JSONObject parasMap);

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/1 14:36
     * @Description: 对外-分销商api-酒店详情接口
     */
    @PostMapping("hotel.detail")
    JSONObject hotelDetail(@RequestBody JSONObject parasMap);

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 魏世杰
     * @Date: 2019/8/1 14:36
     * @Description: 对外-分销商api-房型静态接口
     */
    @PostMapping("hotel.roomtype.list")
    JSONObject hotelRoomtypeList(@RequestBody JSONObject parasMap);

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
    JSONObject hotelIncrData(@RequestBody JSONObject map);

    /**
     * @Description: 对外-分销商api-监测房型上下线
     * @Author: 王俊文
     * @Date: 19-7-19 上午9:31
     * @Param: [parasMap, request, response]
     * @returns: java.util.Map<java.lang.String, java.lang.Object>
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-19 上午9:31          1.0          对外-分销商api-监测房型上下线
     */
    @PostMapping("hotel.incr.roomtype")
    JSONObject getHotelIncrRoomtype(@RequestBody JSONObject map);

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
    JSONObject getHotelBaseprice(@RequestBody JSONObject map);

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
    JSONObject getHotelIoprice(@RequestBody JSONObject map);

    /**
     * @Description: 直连报价--验价
     * @Author: 王俊文
     * @Date: 19-7-26 下午4:33
     * @Param: [parasMap, request, response]
     * @returns: java.util.Map<java.lang.String, java.lang.Object>
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-26 下午4:33          1.0     直连报价--验价  --提交订单时调用
     */
    @PostMapping("hotel.testprice")
    JSONObject hotelIpriceTestPrice(@RequestBody JSONObject map);
}
