package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 酒店政策表
 * </p>
 *
 * @author kang
 * @since 2019-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_POLICY")
@KeySequence(value = "B_HOTEL_POLICY_KEYID_SEQ",clazz = Long.class)
public class BHotelPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID",type = IdType.INPUT)
    private Long id;

    /**
     * 类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 政策内容
     */
    @TableField("CONTENT")
    private String content;

    /**
     * 酒店ID
     */
    @TableField("HOTEL_ID")
    private Long hotelId;


}
