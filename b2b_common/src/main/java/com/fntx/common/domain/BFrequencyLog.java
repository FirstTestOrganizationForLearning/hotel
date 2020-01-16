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
 * 分销商接口频次日志表
 * </p>
 *
 * @author 王俊文
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_FREQUENCY_LOG")
public class BFrequencyLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分销商访问频次日志id
     */
    @TableId("COMPID_TIME_ID")
    private String compidTimeId;

    /**
     * 起价频次
     */
    @TableField("BASE_PRICE")
    private Long basePrice;

    /**
     * 起价超过频次
     */
    @TableField("BASE_PRICE_EXCEED")
    private Long basePriceExceed;

    /**
     * 直连报价频次
     */
    @TableField("DIRECT_PRICE")
    private Long directPrice;

    /**
     * 直连报价超过频次
     */
    @TableField("DIRECT_PRICE_EXCEED")
    private Long directPriceExceed;

    /**
     * 房型是否可预定频次
     */
    @TableField("IS_BOOKROOM")
    private Long isBookroom;

    /**
     * 房型是否可预定超过频次
     */
    @TableField("IS_BOOKROOM_EXCEED")
    private Long isBookroomExceed;

    /**
     * 创建订单频次
     */
    @TableField("CREATE_ORDER")
    private Long createOrder;

    /**
     * 创建订单超过频次
     */
    @TableField("CREATE_ORDER_EXCEED")
    private Long createOrderExceed;

    /**
     * 提交订单频次
     */
    @TableField("SUBMIT_ORDER")
    private Long submitOrder;

    /**
     * 提交订单超过频次
     */
    @TableField("SUBMIT_ORDER_EXCEED")
    private Long submitOrderExceed;

    /**
     * 监测订单状态变化频次
     */
    @TableField("ORDERSTATUS_CHANGE")
    private Long orderstatusChange;

    /**
     * 监测订单状态变化超过频次
     */
    @TableField("ORDERSTATUS_CHANGE_EXCEED")
    private Long orderstatusChangeExceed;

    /**
     * 获取订单详情频次
     */
    @TableField("ORDER_DETAIL")
    private Long orderDetail;

    /**
     * 获取订单详情超过频次
     */
    @TableField("ORDER_DETAIL_EXCEED")
    private Long orderDetailExceed;

    /**
     * 取消订单频次
     */
    @TableField("CANCEL_ORDER")
    private Long cancelOrder;

    /**
     * 取消订单超过频次
     */
    @TableField("CANCEL_ORDER_EXCEED")
    private Long cancelOrderExceed;

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

    /**
     * 分销商id
     */
    @TableField("COMPID")
    private String compid;


}
