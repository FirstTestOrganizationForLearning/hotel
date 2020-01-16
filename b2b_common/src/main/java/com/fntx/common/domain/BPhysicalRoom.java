package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 物理房型表
 * </p>
 *
 * @author kang
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_PHYSICAL_ROOM")
public class BPhysicalRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物理房型ID
     */
    @TableField("ROOM_TYPE_ID")
    private Long roomTypeId;

    /**
     * 房型名称
     */
    @TableField("ROOM_NAME")
    private String roomName;

    /**
     * 子床型代码

     */
    @TableField("SUB_BED_ID")
    private String subBedId;

    /**
     * 床型名称

     */
    @TableField("BED_NAME")
    private String bedName;

    /**
     * 床的数量

     */
    @TableField("BED_NUMBER")
    private Integer bedNumber;

    /**
     * 床的尺寸

     */
    @TableField("BED_SIZE")
    private String bedSize;

    /**
     * 房间面积

     */
    @TableField("AREARANGE")
    private String arearange;

    /**
     * 楼层

     */
    @TableField("FLOOR_RANGE")
    private String floorRange;

    /**
     * 是否有窗
     0-无窗；1-部分有窗；2-有窗；4-内窗；5-天窗；6-封闭窗；7-飘窗；-100未知
     */
    @TableField("HAS_WINDOW")
    private String hasWindow;

    /**
     * 加床费

     */
    @TableField("EXTRABEDFEE")
    private String extrabedfee;

    /**
     * 无线宽带

     */
    @TableField("WIRELESSBROADNET")
    private Integer wirelessbroadnet;

    /**
     * 有线宽带

     */
    @TableField("WIREDBROADNET")
    private Integer wiredbroadnet;

    /**
     * 儿童入住人数上限

     */
    @TableField("CHILD_MAXOCCUPANCY")
    private Integer childMaxoccupancy;

    /**
     * 儿童年龄上限

     */
    @TableField("CHILD_MAXAGE")
    private Integer childMaxage;

    /**
     * 儿童年龄下限

     */
    @TableField("CHILD_MINAGE")
    private Integer childMinage;

    /**
     * 房间的数量

     */
    @TableField("ROOM_QUANTITY")
    private Integer roomQuantity;

    /**
     * 最大入住人数

     */
    @TableField("MAX_OCCUPANCY")
    private Integer maxOccupancy;

    /**
     * 描述类型

     */
    @TableField("DESCRIPTION_TYPE")
    private Integer descriptionType;

    /**
     * 描述的内容

     */
    @TableField("DESCRIPTION_TEXT")
    private String descriptionText;

    /**
     * 卫浴类型

     */
    @TableField("BATHROOM_TYPE")
    private Integer bathroomType;


}
