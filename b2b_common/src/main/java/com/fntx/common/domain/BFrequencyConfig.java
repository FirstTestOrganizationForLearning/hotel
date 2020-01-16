package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 分销商接口频次配置表
 * </p>
 *
 * @author 王俊文
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_FREQUENCY_CONFIG")
public class BFrequencyConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分销商ID
     */
    @TableField("COMPID")
    private String compid;

    /**
     * 分销商sid
     */
    @TableField("SID")
    private String sid;

    /**
     * 起价频次
     */
    @TableField("BASE_PRICE")
    private Long basePrice;

    /**
     * 直连报价频次
     */
    @TableField("DIRECT_PRICE")
    private Long directPrice;

    /**
     * 房型是否可预定频次
     */
    @TableField("IS_BOOKROOM")
    private Long isBookroom;

    /**
     * 创建订单频次
     */
    @TableField("CREATE_ORDER")
    private Long createOrder;

    /**
     * 提交订单频次
     */
    @TableField("SUBMIT_ORDER")
    private Long submitOrder;

    /**
     * 获取支付确认结果频次
     */
    @TableField("PAY_RESULT")
    private Long payResult;

    /**
     * 监测订单状态变化频次
     */
    @TableField("ORDERSTATUS_CHANGE")
    private Long orderstatusChange;

    /**
     * 获取订单详情频次
     */
    @TableField("ORDER_DETAIL")
    private Long orderDetail;

    /**
     * 取消订单频次
     */
    @TableField("CANCEL_ORDER")
    private Long cancelOrder;

    /**
     * 是否容许超过频次  0：不容许   1：容许
     */
    @TableField("IS_EXCEED")
    private Long isExceed;

    /**
     * 收费标准
     */
    @TableField("CHARG_STANDARD")
    private Double chargStandard;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;


}
