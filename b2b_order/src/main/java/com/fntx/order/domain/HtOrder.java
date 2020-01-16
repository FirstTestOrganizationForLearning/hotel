package com.fntx.order.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author kang
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HT_ORDER")
public class HtOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ORDER_ID",type = IdType.INPUT)
    private String orderId;

    @TableField("ORDER_JSON")
    private String orderJson;

    @TableField("ORDER_STATE")
    private Integer orderState;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("SUBMIT_TIME")
    private Date submitTime;

    @TableField("CANCEL_TIME")
    private Date cancelTime;

    @TableField("DIST_ORDER_ID")
    private String distOrderId;

    @TableField("DIST_UUID")
    private String distUuid;

    @TableField("CREATER")
    private String creater;

    @TableField("CURRENCY_CODE")
    private String currencyCode;

    @TableField("AMOUNT")
    private Double amount;

    @TableField("PAY_AMOUNT")
    private Double payAmount;

    @TableField("PAY_TIME")
    private Date payTime;

    @TableField("ORDER_TYPE")
    private Integer orderType;

    @TableField("MEMO")
    private String memo;

    @TableField(value = "KEYID")
    private Integer keyid;

    @TableField("FEN_ORDER_ID")
    private String fenOrderId;

    @TableField("HOTEL_ID")
    private String hotelId;

    @TableField("HOTEL_NAME")
    private String hotelName;

    @TableField("ROOM_ID")
    private String roomId;

    @TableField("ROOM_NAME")
    private String roomName;

    @TableField("RATE_PLAN_ID")
    private String ratePlanId;

    @TableField("RATE_PLAN_NAME")
    private String ratePlanName;

    @TableField("BOOKING_CODE")
    private String bookingCode;

    @TableField("PREPAID_INDICATOR")
    private Integer prepaidIndicator;

    @TableField("NUMBER_OF_UNITS")
    private Integer numberOfUnits;

    @TableField("START_DATE")
    private Date startDate;

    @TableField("END_DATE")
    private Date endDate;

    @TableField("GUEST_COUNT")
    private Integer guestCount;

    @TableField("CUSTOMERS")
    private String customers;

    @TableField("CONTACT_NAME")
    private String contactName;

    @TableField("CONTACT_TELPHONE")
    private String contactTelphone;

    @TableField("CONTACT_EMAIL")
    private String contactEmail;

    @TableField("ARRIVAL_TIME")
    private Date arrivalTime;

    @TableField("ORDER_STATE_SYNC_FLAG")
    private Integer orderStateSyncFlag;

    @TableField("LAST_UPDATE_STATE_TIME")
    private Date lastUpdateStateTime;

    @TableField("ORDER_ID_501")
    private String orderId501;

    @TableField("ORDER_ID_502")
    private String orderId502;

    @TableField("ORDER_ID_507")
    private String orderId507;

    @TableField("ORDER_ID_509")
    private String orderId509;

    @TableField("CANCEL_REASON")
    private String cancelReason;

    @TableField("MANUAL_FLAG")
    private Integer manualFlag;

    @TableField("MANUAL_REASON")
    private String manualReason;

    @TableField("MANUAL_DONE")
    private Integer manualDone;


}
