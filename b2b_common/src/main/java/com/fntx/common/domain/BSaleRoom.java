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
 * 售卖房型表
 * </p>
 *
 * @author kang
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_SALE_ROOM")
public class BSaleRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 售卖房型ID
     */
    @TableField("ROOM_ID")
    private Long roomId;

    /**
     * 房型名称
     */
    @TableField("ROOM_NAME")
    private String roomName;

    /**
     * 酒店ID
     */
    @TableField("HOTEL_ID")
    private Long hotelId;

    /**
     * 标准入住人数
     */
    @TableField("STANDARD_OCCUPANCY")
    private Integer standardOccupancy;

    /**
     * 物理房型ID
     */
    @TableField("ROOM_TYPE_ID")
    private Long roomTypeId;

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
    private String bedNumber;

    /**
     * 床的尺寸
     */
    @TableField("BED_SIZE")
    private String bedSize;

    /**
     * 房型状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 房型描述信息
     */
    @TableField("DESCRIPTIVETEXT")
    private String descriptivetext;

    /**
     * 是否可预定
     * 1：是  0：不是
     */
    @TableField("IS_ALLOW_BOOKING")
    private Integer isAllowBooking;

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
     * 房间大小
     */
    @TableField("ROOM_SIZE")
    private Integer roomSize;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 最后同步时间
     */
    @TableField("LASTSYNC_TIME")
    private Date lastsyncTime;

    /**
     * 现付房型可否转成预付
     * 1：是  0：不是
     */
    @TableField("CANFGTOPP")
    private Integer canfgtopp;

    /**
     * 适用人群
     * 1:持身份证的中国居民 2:持中国护照或回乡证的香港居民 3:持护照或台胞证的台湾居民 空:不限
     */
    @TableField("APPLICABILITY_INFO")
    private String applicabilityInfo;

    /**
     * 是否可吸烟
     * 1可吸烟，2禁烟，-100未知
     */
    @TableField("IS_ALLOW_SMOKING")
    private Integer isAllowSmoking;

    /**
     * 无线宽带
     * 0没有，1全部房间有且收费，2全部房间有且免费，3部分房间有且收费，4部分房间有且免费
     */
    @TableField("WIRELESSBROADNET")
    private Integer wirelessbroadnet;

    /**
     * 有线宽带
     * 0没有，1全部房间有且收费，2全部房间有且免费，3部分房间有且收费，4部分房间有且免费
     */
    @TableField("WIREDBROADNET")
    private Integer wiredbroadnet;

    /**
     * 是否在App售卖
     * 1：是  0：不是
     */
    @TableField("IS_APP")
    private Integer isApp;

    /**
     * 是否在Online售卖
     * 1：是  0：不是
     */
    @TableField("IS_WEB")
    private Integer isWeb;

    /**
     * 是否在微信端售卖   1：是  0：不是
     */
    @TableField("IS_WECHAT")
    private Integer isWechat;

    /**
     * 是否支持闪住             1：是  0：不是
     */
    @TableField("EXPRESSCHECKOUT_ISSUPPORTED")
    private Integer expresscheckoutIssupported;

    /**
     * 闪住押金系数
     */
    @TableField("DEPOSITRATIO")
    private Double depositratio;

    /**
     * 是否是钟点房
     * 1：是  0：不是
     */
    @TableField("IS_HOURLYROOM")
    private Integer isHourlyroom;

    /**
     * 是否是直连房型
     * 1：是  0：不是
     */
    @TableField("IS_FROMAPI")
    private Integer isFromapi;

    /**
     * 是否展示代理商标签
     * 1：是  0：不是
     */
    @TableField("IS_SHOWAGENCYTAG")
    private Integer isShowagencytag;

    /**
     * 发票类型
     * 1-弗恩可提供发票；2-酒店可提供发票
     */
    @TableField("INVOICE_TYPE")
    private Integer invoiceType;

    /**
     * 酒店是否提供专票
     * 1：是  0：不是
     */
    @TableField("IS_SUPPORT_SPECIALINVOICE")
    private Integer isSupportSpecialinvoice;

    /**
     * 是否接受客人自定义备注
     * 1：是  0：不是
     */
    @TableField("RECEIVETEXT_REMARK")
    private Integer receivetextRemark;

    /**
     * FG现付，PP预付
     */
    @TableField("PAYTYPE")
    private String paytype;


}
