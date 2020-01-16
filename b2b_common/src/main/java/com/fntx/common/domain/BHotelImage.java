package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 酒店图片表
 * </p>
 *
 * @author kang
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_IMAGE")
@KeySequence(value = "B_HOTEL_IMAGE_KEYID_SEQ",clazz = Long.class)
public class BHotelImage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "IMAGE_ID",type = IdType.INPUT)
    private Long imageId;

    /**
     * 图片类型
     */
    @TableField("IMAGE_TYPE")
    private Long imageType;

    /**
     * 图片标题
     */
    @TableField("IMAGE_CAPTION")
    private String imageCaption;

    /**
     * 图片URL
     */
    @TableField("IMAGE_URL")
    private String imageUrl;

    /**
     * 酒店ID
     */
    @TableField("HOTEL_ID")
    private Long hotelId;


}
