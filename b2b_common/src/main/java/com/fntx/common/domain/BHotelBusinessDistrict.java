package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 酒店商圈信息表
 * </p>
 *
 * @author kang
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_BUSINESS_DISTRICT")
@KeySequence(value = "B_HOTEL_BUSINESS_DISTRICT_KEYID_SEQ",clazz = Long.class)
public class BHotelBusinessDistrict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID",type = IdType.INPUT)
    private Long id;

    /**
     * 商圈ID
     */
    @TableField("BIZ_DIST_ID")
    private Long bizDistId;

    /**
     * 酒店ID
     */
    @TableField("HOTEL_ID")
    private Long hotelId;


}
