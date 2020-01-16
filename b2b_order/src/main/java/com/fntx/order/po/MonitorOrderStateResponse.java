package com.fntx.order.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 监测订单状态变化响应
 * @Author: 胡庆康
 * @Date: 2019/7/11 10:30
 */
@NoArgsConstructor
@Data
public class MonitorOrderStateResponse {

    /**
     * Warnings : []
     * HotelReservations : [{"ResGlobalInfo":{"HotelReservationIDs":[{"ResID_Type":"501","ResID_Value":"2170144983"}]},"ResStatus":"S","LastModifyDateTime":"2018-01-10T19:01:07.9400000"},{"ResGlobalInfo":{"HotelReservationIDs":[{"ResID_Type":"501","ResID_Value":"2170152506"},{"ResID_Type":"502","ResID_Value":"109012"}]},"ResStatus":"A","LastModifyDateTime":"2018-01-10T19:08:08.0000000"},{"ResGlobalInfo":{"HotelReservationIDs":[{"ResID_Type":"501","ResID_Value":"2170152508"},{"ResID_Type":"509","ResID_Value":"2170152566"}]},"ResStatus":"S","LastModifyDateTime":"2018-01-10T19:53:09.9800000"},{"ResGlobalInfo":{"HotelReservationIDs":[{"ResID_Type":"501","ResID_Value":"2170152579"},{"ResID_Type":"509","ResID_Value":"2170152590"}]},"ResStatus":"S","LastModifyDateTime":"2018-01-10T19:58:09.9800000"}]
     * Errors : []
     * TimeStamp : 2018-01-10T20:00:00.1021891+08:00
     * Version : 1
     * PrimaryLangID : en
     */
    @JSONField(name = "ResponseStatus")
    public GeneralEntity.ResponseStatus ResponseStatus;
    @JSONField(name = "Warnings")
    public List<Object> Warnings;
    @JSONField(name = "HotelReservations")
    public List<HotelReservation> HotelReservations;
    @JSONField(name = "Errors")
    public List<Object> Errors;
    /**
     * 响应报文时间戳
     */
    @JSONField(name = "TimeStamp")
    public String TimeStamp;
    /**
     * API版本信息
     */
    @JSONField(name = "Version")
    public String Version;
    /**
     * 定义返回报文的显示语种(例如中文、英文)。备注：目前仅仅支持“zh-cn”和“en”。更多语种的支持仍在开发中。
     */
    @JSONField(name = "PrimaryLangID")
    public String PrimaryLangID;
    @Data
    public static class HotelReservationID {
        //可能的值包括：501；502；507；509；
        public String ResIDType;
        //501，则该节点的值表示“新订单号”；
        //502，则该节点的值表示“酒店确认号”；
        //507，则该节点的值表示被修改的“原始订单号”，对应修改后订单号为“新订单号”。
        //509，则该节点的值表示被补单的“原始订单号”，与补单后生成的“新订单号”相对应。
        //备注：存在多个订单号的情况下，建议分销商将关联的订单号也落到本地，每个订单号有独立的订单处理流程。
        @JsonProperty
        public String ResIDValue;
    }
    @Data
    public static class ResGlobalInfo {
        public List<HotelReservationID> HotelReservationIDs;
    }
    @Data
    public static class HotelReservation {
        public ResGlobalInfo ResGlobalInfo;
        //废弃！请勿使用！
        public String ResStatus;
        //订单状态发送变化的具体时间点
        public String LastModifyDateTime;
    }


}