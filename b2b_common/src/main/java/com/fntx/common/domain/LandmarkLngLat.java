package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 地标经纬度表
 * </p>
 *
 * @author kang
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("LANDMARK_LNG_LAT")
@KeySequence(value = "LANDMARK_LNG_LAT_KEYID_SEQ",clazz = Long.class)
public class LandmarkLngLat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID",type = IdType.INPUT)
    private Long id;

    /**
     * 酒店ID
     */
    @TableField("HOTEL_ID")
    private Long hotelId;

    /**
     * 坐标提供者
     */
    @TableField("PROVIDER")
    private String provider;

    /**
     * 地标经度
     */
    @TableField("LNG")
    private Double lng;

    /**
     * 地标纬度
     */
    @TableField("LAT")
    private Double lat;

    /**
     * 地标名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 地标类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 酒店与地标的距离
     */
    @TableField("DISTANCE")
    private Double distance;

    /**
     * 到达方式
     */
    @TableField("DIRECTIONS")
    private String directions;

    /**
     * 交通类型
     */
    @TableField("TRANSPORTATIONTYPE")
    private String transportationtype;

    /**
     * 到达花费的时间
     */
    @TableField("TIMETAKEN")
    private Double timetaken;


}
