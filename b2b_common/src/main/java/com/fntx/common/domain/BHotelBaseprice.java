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
 * 酒店入离起价
 * </p>
 *
 * @author wang
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_BASEPRICE")
public class BHotelBaseprice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 酒店id
     */
    @TableId("HOTEL_ID")
    private Long hotelId;

    /**
     * 房型id
     */
    @TableField("ROOM_ID")
    private Long roomId;

    /**
     * 城市id
     */
    @TableField("CITY_ID")
    private Long cityId;

    /**
     * 税后且返现后最低价房型的日均最低价，币种为人民币
     */
    @TableField("MIN_PRICE")
    private Double minPrice;

    /**
     * 税后且返现后最低价房型的日均最低价，币种为原币种
     */
    @TableField("MIN_PRICE_ORIGINAL_CURRENCY")
    private Double minPriceOriginalCurrency;

    /**
     * 原币种
     */
    @TableField("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /**
     * 是否可立即确认  T:可立即确认   F:不可立即确认
     */
    @TableField("IS_JUSTIFY_CONFIRM")
    private String isJustifyConfirm;

    /**
     * 房型支付类型  OnlyFGPrice: 到付   OnlyPPPrice: 预付
     */
    @TableField("PAYMENT_TYPE")
    private String paymentType;

    /**
     * 人数限制 表示需支持最少起始人数 传 ”N,999”，表示返回入住人数为N或N以上的房型
     */
    @TableField("FILTER_ROOM_BY_PERSON")
    private Integer filterRoomByPerson;

    /**
     * 酒店标识  国内:WirelessSearch; 国际:OnLineIntlGTASearch
     */
    @TableField("SEARCH_TYPE")
    private String searchType;

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
     * 儿童入住人数上限
     */
    @TableField("CHILDREN_NUMBER_LIST")
    private Integer childrenNumberList;

    /**
     * 城市中文名
     */
    @TableField("CITY_NAME")
    private String CityName;

    /**
     * 城市英文名
     */
    @TableField("CITY_ENAME")
    private String CityEName;

}
