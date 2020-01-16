package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kang
 * @since 2019-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_EXTRA_POLICY")
@KeySequence(value = "B_HOTEL_EXTRA_POLICY_KEYID_SEQ",clazz = Long.class)
public class BHotelExtraPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private Long id;

    /**
     * 最多允许的加床数量
     */
    @TableField("MAX_QUANTITY")
    private Long maxQuantity;

    /**
     * 最大加婴儿床数量
     */
    @TableField("MAX_CRIB_QUANTITY")
    private Long maxCribQuantity;

    /**
     * 类别（SpecialTIps：特别提示）
     */
    @TableField("CATEGORY")
    private String category;

    /**
     * 正文
     */
    @TableField("TEXT")
    private String text;

    /**
     * 是否可携带儿童入住 0 false 1 true(下同)
     */
    @TableField("ALLOWCHILDRENTOSTAY")
    private Integer allowchildrentostay;

    /**
     * 儿童是否可使用现有床位
     */
    @TableField("ALLOWUSEEXISTINGBED")
    private Integer allowuseexistingbed;

    /**
     * 是否提供加床
     */
    @TableField("ALLOWEXTRABED")
    private Integer allowextrabed;

    /**
     * 是否提供加婴儿床
     */
    @TableField("ALLOWEXTRACRIB")
    private Integer allowextracrib;

    @TableField("ALLOWEXTRABEDV2")
    private Integer allowextrabedv2;

    @TableField("ALLOWEXTRACRIBV2")
    private Integer allowextracribv2;

    /**
     * 酒店ID
     */
    @TableField("HOTEL_ID")
    private Long hotelId;


}
