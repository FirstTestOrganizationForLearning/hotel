package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 有酒的城市信息表
 * </p>
 *
 * @author wang
 * @since 2019-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_CITY")
public class BHotelCity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 城市ID
     */
    @TableId("CITY_ID")
    private Long cityId;

    /**
     * 城市名称
     */
    @TableField("CITY_NAME")
    private String cityName;

    /**
     * 父级城市ID
     */
    @TableField("PARENT_ID")
    private String parentId;

    /**
     * 父级城市名称
     */
    @TableField("PARENT_NAME")
    private String parentName;

    /**
     * 所属省份ID
     */
    @TableField("PROVINCE_ID")
    private String provinceId;

    /**
     * 所属省份名称
     */
    @TableField("PROVINCE_NAME")
    private String provinceName;

    /**
     * 国家ID
     */
    @TableField("COUNTRY_ID")
    private String countryId;

    /**
     * 国家名称
     */
    @TableField("COUNTRY_NAME")
    private String countryName;

    /**
     * 是否是热点城市
     */
    @TableField("IS_HOT")
    private String isHot;


}
