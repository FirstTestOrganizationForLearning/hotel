package com.fntx.order.po;

import lombok.Data;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 监测订单状态变化请求
 * @Author: 胡庆康
 * @Date: 2019/7/11 10:30
 * @Description:
 */
@Data
public class MonitorOrderStateRequest {
    public List<HotelReservation> HotelReservations;
    /**
     *发起请求的时间
     */
    public String TimeStamp;
    /**
     * API版本信息，当前置为“1”
     */
    public String Version;
    /**
     *定义返回数据以何种语言显示。例如，zh-cn表示中文，en表示英文。
     */
    public String PrimaryLangID;

    @Data
    public static class UniqueID {
        //可能的值：28；503
        private String Type;
        //Type为28时，ID表示分销商的AllienceID；Type为503时，ID表示分销商的SiteID
        private String ID;
    }

    @Data
    public static class HotelReservation {
        public List<UniqueID> UniqueID;
        //定义订单状态发生变化的时间区间的起始时间;备注：起始时间必须在距离当前时间24小时以内。
        public String LastModifyDateTime;
        //定义订单状态发生变化的时间区间的结束时间;
        public String EndModifyDateTime;
    }

}
