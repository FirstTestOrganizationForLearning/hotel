package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 直连入离报价-物理房型表
 * </p>
 *
 * @author kang
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_ROOM_PRICE_INFOS")
public class BHotelRoomPriceInfos implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 售卖房型ID/子房型ID
     */
    @TableId("ROOM_ID")
    private Integer roomId;

    /**
     * 酒店id 主键
     */
    @TableField("HOTEL_ID")
    private Integer hotelId;

    /**
     * 物理房型名称-id
     */
    @TableField("ROOM_TYPE_ID")
    private Integer roomTypeId;

    /**
     * 售卖房型名称/子房型名称
     */
    @TableField("ROOM_NAME")
    private String roomName;

    /**
     * 取消政策
存放CancelPolicyInfos--jsonarray串
     */
    @TableField("CANCEL_POLICY_INFOS")
    private String cancelPolicyInfos;

    /**
     * 最晚预订时间
存放ReserveTimeLimitInfo--jsonobject串
     */
    @TableField("RESERVE_TIME_LIMIT_INFO")
    private String reserveTimeLimitInfo;

    /**
     * 房间保留时间
存放HoldDeadline--jsonobject串
     */
    @TableField("HOLD_DEADLINE")
    private String holdDeadline;

    /**
     * 上次同步时间
     */
    @TableField("LASTSYNCTIME")
    private Date lastsynctime;

    /**
     * 人数限制
用于查询时条件筛选
     */
    @TableField("ADULT")
    private Integer adult;

    /**
     * 酒店状态
     */
    @TableField("HOTEL_STATUS")
    private Integer hotelStatus;

    /**
     * 售卖房型状态
     */
    @TableField("ROOM_STATUS")
    private Integer roomStatus;


}
