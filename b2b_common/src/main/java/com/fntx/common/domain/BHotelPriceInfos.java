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
 * 直连入离报价-售卖房型表
 * </p>
 *
 * @author kang
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_PRICE_INFOS")
public class BHotelPriceInfos implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 售卖房型ID/子房型ID 主键
     */
    @TableId("ROOM_ID")
    private Integer roomId;

    /**
     * 物理房型名称-id
     */
    @TableField("ROOM_TYPE_ID")
    private Integer roomTypeId;

    /**
     * 离店税前后信息
存放Prices--jsonarray串
     */
    @TableField("PRICES")
    private String prices;

    /**
     * 税项
存放Taxes--jsonarray串
     */
    @TableField("TAXES")
    private String taxes;

    /**
     * 费项
存放Fees--jsonarray串
     */
    @TableField("FEES")
    private String fees;

    /**
     * 支付类型
PP-预付，FG-到付
     */
    @TableField("PAY_TYPE")
    private String payType;

    /**
     * 该售卖房型的所属分类
详情:501-标准预付房型;501-标准预付房型;502-促销预付房型;16-标准到付房型;14-促销到付房型;
     */
    @TableField("RATE_PLAN_CATEGORY")
    private String ratePlanCategory;

    /**
     * 售卖房型是否可预订
1--true-可预订，0--false-不可预订
     */
    @TableField("IS_CAN_RESERVE")
    private Integer isCanReserve;

    /**
     * 售卖房型是否需担保
1--true-需担保，0--false-不需担保
     */
    @TableField("IS_GUARANTEE")
    private Integer isGuarantee;

    /**
     * 售卖房型是否可立即确认
(仅代表订单确认速度，分销商仍需通过相关接口同步弗恩订单状态)。
1--True-该房型为立即确认房型，0--false-该房型非立即确认房型"
     */
    @TableField("IS_INSTANT_CONFIRM")
    private Integer isInstantConfirm;

    /**
     * 可定房量
10间以内显示真实房量，大于10间显示 10+
     */
    @TableField("REMAINING_ROOMS")
    private String remainingRooms;

    /**
     * 可定检查流水号
部分国家酒店的售卖房型会返回该参数
     */
    @TableField("BOOKING_CODE")
    private String bookingCode;

    /**
     * 价格计划ID
部分国家酒店的售卖房型会返回该参数
     */
    @TableField("RATE_PLAN_ID")
    private String ratePlanId;

    /**
     * 固定返回F
     */
    @TableField("IS_PROMOTION")
    private String isPromotion;

    /**
     * 上次同步时间
     */
    @TableField("LASTSYNCTIME")
    private Date lastsynctime;


}
