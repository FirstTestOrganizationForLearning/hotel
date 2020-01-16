package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 酒店主题关联表
 * </p>
 *
 * @author kang
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_THEME")
@KeySequence(value = "B_HOTEL_THEME_KEYID_SEQ",clazz = Long.class)
public class BHotelTheme implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID",type = IdType.INPUT)
    private Long id;

    /**
     * 主题ID
     */
    @TableField("THEME_ID")
    private Long themeId;

    /**
     * 主题名称
     */
    @TableField("THEME_NAME")
    private String themeName;

    /**
     * 酒店ID
     */
    @TableField("HOTEL_ID")
    private Long hotelId;


}
