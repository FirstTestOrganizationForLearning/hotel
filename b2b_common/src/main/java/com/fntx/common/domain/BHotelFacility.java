package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 酒店设施关联表
 * </p>
 *
 * @author kang
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_FACILITY")
@KeySequence(value = "B_HOTEL_FACILITY_KEYID_SEQ",clazz = Long.class)
public class BHotelFacility implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID",type = IdType.INPUT)
    private Long id;

    /**
     * 设施ID
     */
    @TableField("FACILITY_ID")
    private Long facilityId;

    /**
     * 酒店ID
     */
    @TableField("HOTEL_ID")
    private Long hotelId;

    /**
     * 设施可用性
     */
    @TableField("STATUS")
    private String status;


}
