package com.fntx.order.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分销商
 * </p>
 *
 * @author kang
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HT_DISTRIBUTOR")
@KeySequence(value = "B_HOTEL_DISTRIBUTOR_SEQ", clazz = Integer.class)
public class HtDistributor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableField("KEYID")
    private Integer keyid;

    /**
     * aid
     */
    @TableId("AID")
    private Integer aid;

    /**
     * sid
     */
    @TableField("SID")
    private Integer sid;

    /**
     * 认证关键
     */
    @TableField("AUTH_KEY")
    private String authKey;

    /**
     * uuid
     */
    @TableField("UUID")
    private String uuid;

    /**
     * 分销商名称
     */
    @TableField("DIST_NAME")
    private String distName;

    /**
     * 联系方式
     */
    @TableField("CONTACTS")
    private String contacts;

    /**
     * 座机
     */
    @TableField("TELPHONE")
    private String telphone;

    /**
     * 手机号
     */
    @TableField("MOBILE")
    private String mobile;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * active_flag
     */
    @TableField("ACTIVE_FLAG")
    private Integer activeFlag;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 创建者
     */
    @TableField("CREATER")
    private String creater;

    /**
     * predeposit
     */
    @TableField("PREDEPOSIT")
    private Double predeposit;

    /**
     * 回调
     */
    @TableField("PAYMENT_CALLBACK")
    private String paymentCallback;


}
