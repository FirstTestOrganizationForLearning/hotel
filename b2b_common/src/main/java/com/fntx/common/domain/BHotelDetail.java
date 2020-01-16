package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 酒店详情表
 * </p>
 *
 * @author wang
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_DETAIL")
public class BHotelDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 酒店ID
     */
    @TableId("HOTEL_ID")
    private Long hotelId;

    /**
     * 酒店名称
     */
    @TableField("HOTEL_NAME_CN")
    private String hotelNameCn;

    /**
     * 酒店英文名称
     */
    @TableField("HOTEL_NAME_EN")
    private String hotelNameEn;

    /**
     * 酒店星级
     */
    @TableField("STAR_RATE")
    private Integer starRate;

    /**
     * 是否政府机构评定
     */
    @TableField("ISOFFICIALRATING")
    private Integer isofficialrating;

    /**
     * 开店日期
     */
    @TableField("OPENYEAR")
    private String openyear;

    /**
     * 翻新日期
     */
    @TableField("RENOVATIONYEAR")
    private String renovationyear;

    /**
     * 客房数量
     */
    @TableField("ROOMQUANTITY")
    private Long roomquantity;

    /**
     * 是否在线加盟酒店
     */
    @TableField("ISONLINESIGNUP")
    private Integer isonlinesignup;

    /**
     * 是否可定
     */
    @TableField("BOOKABLE")
    private Integer bookable;

    /**
     * 城市ID
     */
    @TableField("CITY_ID")
    private Long cityId;

    /**
     * 行政区域ID
     */
    @TableField("REGION_ID")
    private Long regionId;

    /**
     * 所属商圈ID
     */
    @TableField("BUSINESS_DISTRICT_ID")
    private Long businessDistrictId;

    /**
     * 酒店邻近的路口
     */
    @TableField("ADJACENTINTERSECTION")
    private String adjacentintersection;

    /**
     * 酒店地址

     */
    @TableField("ADDRESS_LINE")
    private String addressLine;

    /**
     * 邮编

     */
    @TableField("POSTCODE")
    private String postcode;

    /**
     * 酒店电话

     */
    @TableField("TELEPHONE")
    private String telephone;

    /**
     * 酒店传真

     */
    @TableField("FAX")
    private String fax;

    /**
     * 集团ID

     */
    @TableField("GROUP_ID")
    private Long groupId;

    /**
     * 品牌ID

     */
    @TableField("BRAND_ID")
    private Long brandId;

    /**
     * 百度纬度

     */
    @TableField("BAIDULATITUDE")
    private Double baidulatitude;

    /**
     * 百度经度

     */
    @TableField("BAIDULONGITUDE")
    private Double baidulongitude;

    /**
     * 高德纬度

     */
    @TableField("GAODELATITUDE")
    private Double gaodelatitude;

    /**
     * 高德经度

     */
    @TableField("GAODELONGITUDE")
    private Double gaodelongitude;

    /**
     * 谷歌纬度

     */
    @TableField("GOOGLELATITUDE")
    private Double googlelatitude;

    /**
     * 谷歌经度

     */
    @TableField("GOOGLELONGITUDE")
    private Double googlelongitude;

    /**
     * OTA推荐级别
     */
    @TableField("OTARECOMLEVEL")
    private Double otarecomlevel;

    /**
     * 用户评分

     */
    @TableField("USERRATE")
    private Double userrate;

    /**
     * 酒店综合评分

     */
    @TableField("OVERALLRATE")
    private Double overallrate;

    /**
     * 支持的信用卡类型

     */
    @TableField("CREDITCARD_TYPE")
    private String creditcardType;

    /**
     * 重要提示

     */
    @TableField("IMPORTANTNOTICES")
    private String importantnotices;

    /**
     * 酒店点评－周边环境分类评分

     */
    @TableField("COMMSURROUNDINGRATE")
    private Double commsurroundingrate;

    /**
     * 酒店设施评分

     */
    @TableField("COMMFACILITYRATE")
    private Double commfacilityrate;

    /**
     * 酒店点评－房间卫生分类评分

     */
    @TableField("COMMCLEANRATE")
    private Double commcleanrate;

    /**
     * 酒店点评－酒店服务分类评分

     */
    @TableField("COMMSERVICERATE")
    private Double commservicerate;

    /**
     * 酒店常用的酒店标签和分类

     */
    @TableField("SEGMENTCATEGORY")
    private String segmentcategory;

    /**
     * 短篇描述

     */
    @TableField("SHORTDESC")
    private String shortdesc;

    /**
     * 酒店简介

     */
    @TableField("LONGDESC")
    private String longdesc;

    /**
     * 最早到店时间

     */
    @TableField("ARRIVALEARLIESTTIME")
    private String arrivalearliesttime;

    /**
     * 最晚到店时间

     */
    @TableField("ARRIVALLATESTTIME")
    private String arrivallatesttime;

    /**
     * 是否强制

     */
    @TableField("ISMUSTBE")
    private Integer ismustbe;

    /**
     * 最早离店时间

     */
    @TableField("LEAVEEARLIESTTIME")
    private String leaveearliesttime;

    /**
     * 最晚离店时间

     */
    @TableField("LEAVELATESTTIME")
    private String leavelatesttime;

    /**
     * 数据来源

     */
    @TableField("DATASOURCE")
    private Integer datasource;

    /**
     * 创建时间
     */
    @TableField("CREATEDATE")
    private Date createdate;

    /**
     * 最后同步时间

     */
    @TableField("LASTSYNCTIME")
    private Date lastsynctime;

    /**
     * 人民币价格

     */
    @TableField("MINPRICERMB")
    private Double minpricermb;

    /**
     * 原币种价格

     */
    @TableField("MINPRICE")
    private Double minprice;

    /**
     * 原币种

     */
    @TableField("MINPRICECURRENCY")
    private String minpricecurrency;

    /**
     * 最低价房型ID

     */
    @TableField("MINPRICEROOMID")
    private Long minpriceroomid;

    /**
     * 起价最后同步时间

     */
    @TableField("MINPRICELASTSYNCTIME")
    private Date minpricelastsynctime;


}
