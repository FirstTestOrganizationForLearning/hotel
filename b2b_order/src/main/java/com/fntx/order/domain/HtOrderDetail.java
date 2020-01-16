package com.fntx.order.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author kang
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HT_ORDER_DETAIL")
public class HtOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 不知什么id，找不到注释
     */
    @TableField("KEYID")
    private Integer keyid;

    /**
     * 订单id
     */
    @TableId(value = "ORDER_ID",type = IdType.INPUT)
    private String orderId;

    /**
     * 返回json
     */
    @TableField("ORDER_JSON")
    private String orderJson;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 备注
     */
    @TableField("MEMO")
    private String memo;

    /**
     * 房间类型名称
     */
    @TableField("ROOMTYPENAME")
    private String roomtypename;

    /**
     * 基础房型ID
     */
    @TableField("ROOMTYPECODE")
    private String roomtypecode;

    /**
     * 父床型ID
     */
    @TableField("BEDTYPECODE")
    private String bedtypecode;

    /**
     * 售卖房型的预定间数
     */
    @TableField("NUMBEROFUNITS")
    private Integer numberofunits;

    /**
     * 格式化的描述信息
     */
    @TableField("DETAILDESCRIPTION")
    private String detaildescription;

    /**
     * 售卖房型ID/子房型ID
     */
    @TableField("ROOMID")
    private String roomid;

    /**
     * 酒店ID
     */
    @TableField("HOTELCODE")
    private String hotelcode;

    /**
     * 酒店名称
     */
    @TableField("HOTELNAME")
    private String hotelname;

    /**
     * 基础税前价格
     */
    @TableField("BASEEXCLUSIVEAMOUNT")
    private Float baseexclusiveamount;

    /**
     * 基础税后价格
     */
    @TableField("BASEINCLUSIVEAMOUNT")
    private Float baseinclusiveamount;

    /**
     * 基础货币代码
     */
    @TableField("BASECURRENCYCODE")
    private String basecurrencycode;

    /**
     * 是否含早餐
     */
    @TableField("BREAKFAST")
    private Integer breakfast;

    /**
     * 早餐份数
     */
    @TableField("NUMBEROFBREAKFAST")
    private Integer numberofbreakfast;

    /**
     * 有效日期
     */
    @TableField("EFFECTIVEDATE")
    private Date effectivedate;

    /**
     * PP-该订单为预付订单；FG-该订单为到付订单
     */
    @TableField("BILLINGCODE")
    private String billingcode;

    /**
     * 客人人数
     */
    @TableField("GUESTCOUNT")
    private Integer guestcount;

    /**
     * 开始时间
     */
    @TableField("STARTDATE")
    private Date startdate;

    /**
     * 结束时间
     */
    @TableField("ENDDATE")
    private Date enddate;

    /**
     * DP税前价格
     */
    @TableField("DP_EXCLUSIVEAMOUNT")
    private Float dpExclusiveamount;

    /**
     * DP税后价格
     */
    @TableField("DP_INCLUSIVEAMOUNT")
    private Float dpInclusiveamount;

    /**
     * DP_货币币种
     */
    @TableField("DP_CURRENCYCODE")
    private String dpCurrencycode;

    /**
     * DP金额
     */
    @TableField("DP_AMOUNT")
    private Float dpAmount;

    /**
     * 担保类型
     */
    @TableField("GUARANTEETYPE")
    private String guaranteetype;

    /**
     * 担保政策代码
     */
    @TableField("GUARANTEECODE")
    private String guaranteecode;

    /**
     * CP税前价格
     */
    @TableField("CP_EXCLUSIVEAMOUNT")
    private Float cpExclusiveamount;

    /**
     * CP税后价格
     */
    @TableField("CP_INCLUSIVEAMOUNT")
    private Float cpInclusiveamount;

    /**
     * CP货币币种
     */
    @TableField("CP_CURRENCYCODE")
    private String cpCurrencycode;

    /**
     * CP金额
     */
    @TableField("CP_AMOUNT")
    private Float cpAmount;

    /**
     * CP开始时间
     */
    @TableField("CP_STARTDATE")
    private Date cpStartdate;

    /**
     * CP结束时间
     */
    @TableField("CP_ENDDATE")
    private Date cpEnddate;

    /**
     * 放弃预订
     */
    @TableField("NOSHOWPAID")
    private Float noshowpaid;

    /**
     * 税前价格总计
     */
    @TableField("TOTALEXCLUSIVEAMOUNT")
    private Float totalexclusiveamount;

    /**
     * 税后价格总计
     */
    @TableField("TOTALINCLUSIVEAMOUNT")
    private Float totalinclusiveamount;

    /**
     * 总计币种
     */
    @TableField("TOTALCURRENCYCODE")
    private String totalcurrencycode;

    /**
     * 总计金额
     */
    @TableField("TOTALAMOUNT")
    private Float totalamount;

    /**
     * 客户名称
     */
    @TableField("CUSTOMERNAME")
    private String customername;

    /**
     * 联系人
     */
    @TableField("CONTACTPERSON")
    private String contactperson;

    /**
     * 联系人手机号
     */
    @TableField("CONTACTPHONE")
    private String contactphone;

    /**
     * 联系人邮箱
     */
    @TableField("CONTACTEMAIL")
    private String contactemail;

    /**
     * 联系人类型
     */
    @TableField("CONTACTTYPE")
    private String contacttype;

    /**
     * 最晚抵店时间
     */
    @TableField("LATEARRIVALTIME")
    private Date latearrivaltime;

    /**
     * 最早抵店时间
     */
    @TableField("ARRIVALTIME")
    private Date arrivaltime;

    /**
     * 订单是否已付款
     */
    @TableField("ISPAID")
    private Integer ispaid;

    /**
     * 该单是否使用了闪住
     */
    @TableField("ISUSEANTICIPATION")
    private Integer isuseanticipation;

    /**
     * 房型促销策略ID (马甲ID)
     */
    @TableField("SHADOWID")
    private String shadowid;

    /**
     * Code为AllowCancel，代表该字段含义为“是否可取消”
     */
    @TableField("ORDERTAGSCODE")
    private String ordertagscode;

    /**
     * 为T时，该订单可取消。为F时，该订单不可取消
     */
    @TableField("ORDERTAGSVALUE")
    private String ordertagsvalue;

    /**
     * 订单创建时间
     */
    @TableField("CREATEDATE")
    private Date createdate;

    /**
     * 最后一次修改订单的时间
     */
    @TableField("LASTMODIFYDATE")
    private Date lastmodifydate;

    /**
     * 订单状态
     */
    @TableField("ORDERSTATUS")
    private String orderstatus;


}
