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
@TableName("B_HOTEL_MEALS_POLICY")
@KeySequence(value = "B_HOTEL_MEALS_POLICY_KEYID_SEQ",clazz = Long.class)
public class BHotelMealsPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private Long id;

    /**
     * 接口文档中没有注释，但是接口有返回，先存库
     */
    @TableField("TYTE")
    private String tyte;

    /**
     * 酒店ID
     */
    @TableField("HOTEL_ID")
    private Long hotelId;

    @TableField("AMOUNT")
    private Double amount;

    /**
     * 货币
     */
    @TableField("CURRENCY")
    private String currency;

    @TableField("MEALTYPE")
    private String mealtype;


}
