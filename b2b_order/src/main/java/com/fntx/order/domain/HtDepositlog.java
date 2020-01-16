package com.fntx.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 充值日志
 * </p>
 *
 * @author kang
 * @since 2019-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HT_DEPOSITLOG")
public class HtDepositlog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "KEYID",type = IdType.INPUT)
    private Double keyid;

    /**
     * 分销商身份标识
     */
    @TableField("UUID")
    private String uuid;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    private Double amount;

    /**
     * 余额
     */
    @TableField("BALANCE")
    private Double balance;

    /**
     * 说明
     */
    @TableField("NOTE_MSG")
    private String noteMsg;

    /**
     * 订单号
     */
    @TableField("ORDER_ID")
    private String orderId;

    /**
     * 创建人
     */
    @TableField("CREATER")
    private String creater;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 备注
     */
    @TableField("MEMO")
    private String memo;


}
