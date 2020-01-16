package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 酒店清单信息表
 * </p>
 *
 * @author wang
 * @since 2019-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_LIST")
public class BHotelList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 酒店ID
     */
    @TableField("HOTEL_ID")
    private Long hotelId;

    /**
     * 城市ID
     */
    @TableField("CITY_ID")
    private Long cityId;

    /**
     * 0：国内   1：国际
     */
    @TableField("IS_INTEL")
    private String isIntel;


}
