package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 静态信息增量更新表
 * </p>
 *
 * @author wang
 * @since 2019-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_STATIC_CHANGE_INFO")
public class BStaticChangeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键

     */
    @TableField("CHANGE_ID")
    private Long changeId;

    /**
     * 实体类型

     */
    @TableField("CATEGORY")
    private String category;

    /**
     * 酒店ID

     */
    @TableField("HOTEL_ID")
    private Long hotelId;

    /**
     * 物理房型ID

     */
    @TableField("ROOMTYPE_ID")
    private Long roomtypeId;

    /**
     * 售卖房型ID

     */
    @TableField("ROOM_ID")
    private Long roomId;

    /**
     * 静态信息发生变化的时间

     */
    @TableField("CHANGETIME")
    private Date changetime;


}
