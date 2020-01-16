package com.fntx.order.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 发票信息
 * </p>
 *
 * @author kang
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HT_ORDER_INVOICE")
@KeySequence(value = "B_HOTEL_ORDER_INVOICE_SEQ",clazz = Integer.class)
public class HtOrderInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "KEYID", type = IdType.INPUT)
    private Integer keyid;

    /**
     * order_id
     */
    @TableField("ORDER_ID")
    private String orderId;

    /**
     * PersonName
     */
    @TableField("PERSONNAME")
    private String personname;

    /**
     * PhoneTechType
     */
    @TableField("PHONETECHTYPE")
    private String phonetechtype;

    /**
     * PhoneNumber
     */
    @TableField("PHONENUMBER")
    private String phonenumber;

    /**
     * Email
     */
    @TableField("EMAIL")
    private String email;

    /**
     * AddressLine
     */
    @TableField("ADDRESSLINE")
    private String addressline;

    /**
     * PostalCode
     */
    @TableField("POSTALCODE")
    private String postalcode;

    /**
     * InvoiceBody
     */
    @TableField("INVOICEBODY")
    private String invoicebody;

    /**
     * FullDescription
     */
    @TableField("FULLDESCRIPTION")
    private String fulldescription;

    /**
     * ShortDescription
     */
    @TableField("SHORTDESCRIPTION")
    private String shortdescription;

    /**
     * PostAmount
     */
    @TableField("POSTAMOUNT")
    private String postamount;

    /**
     * PostCurrencyCode
     */
    @TableField("POSTCURRENCYCODE")
    private String postcurrencycode;

    /**
     * TaxpayerNumber
     */
    @TableField("TAXPAYERNUMBER")
    private String taxpayernumber;

    /**
     * InvoiceTitle
     */
    @TableField("INVOICETITLE")
    private String invoicetitle;

    /**
     * memo
     */
    @TableField("MEMO")
    private String memo;


}
