package com.fntx.order.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单日志
 * </p>
 *
 * @author kang
 * @since 2019-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HT_ORDER_OPLOG")
@KeySequence(value = "HT_ORDER_OPLOG_SEQ",clazz = Integer.class)
public class HtOrderOplog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "KEYID", type = IdType.INPUT)
    private Integer keyid;

    /**
     * 操作类型
     */
    @TableField("OP_TYPE")
    private Integer opType;

    /**
     * 订单号
     */
    @TableField("ORDER_ID")
    private String orderId;

    /**
     * icode
     */
    @TableField("OP_CODE")
    private String opCode;

    /**
     * 操作内容
     */
    @TableField("OP_CONTENT")
    private String opContent;

    /**
     * 操作时间
     */
    @TableField("OP_TIME")
    private Date opTime;

    /**
     * 操作人
     */
    @TableField("CREATER")
    private String creater;

    /**
     * 备注
     */
    @TableField("MEMO")
    private String memo;


}
