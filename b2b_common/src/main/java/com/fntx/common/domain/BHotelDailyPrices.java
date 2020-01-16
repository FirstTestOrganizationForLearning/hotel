package com.fntx.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fntx.common.utils.StringToJsonSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 直连入离报价-日价表
 * </p>
 *
 * @author kang
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_HOTEL_DAILY_PRICES")
public class BHotelDailyPrices implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房型id
     */
    @TableId("ROOM_ID")
    private Integer roomId;

    /**
     * 物理房型id
     */
    @TableField("ROOM_TYPE_ID")
    private Integer roomTypeId;

    /**
     * 当前月
     */
    @TableField("CURRENT_MONTH")
    private String currentMonth;

    /**
     * 当前月1号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_1")
    private String currentMonth1;

    /**
     * 当前月2号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_2")
    private String currentMonth2;

    /**
     * 当前月3号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_3")
    private String currentMonth3;

    /**
     * 当前月4号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_4")
    private String currentMonth4;

    /**
     * 当前月5号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_5")
    private String currentMonth5;

    /**
     * 当前月6号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_6")
    private String currentMonth6;

    /**
     * 当前月7号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_7")
    private String currentMonth7;

    /**
     * 当前月8号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_8")
    private String currentMonth8;

    /**
     * 当前月9号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_9")
    private String currentMonth9;

    /**
     * 当前月10号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_10")
    private String currentMonth10;

    /**
     * 当前月11号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_11")
    private String currentMonth11;

    /**
     * 当前月12号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_12")
    private String currentMonth12;

    /**
     * 当前月13号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_13")
    private String currentMonth13;

    /**
     * 当前月14号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_14")
    private String currentMonth14;

    /**
     * 当前月15号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_15")
    private String currentMonth15;

    /**
     * 当前月16号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_16")
    private String currentMonth16;

    /**
     * 当前月17号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_17")
    private String currentMonth17;

    /**
     * 当前月18号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_18")
    private String currentMonth18;

    /**
     * 当前月19号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_19")
    private String currentMonth19;

    /**
     * 当前月20号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_20")
    private String currentMonth20;

    /**
     * 当前月21号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_21")
    private String currentMonth21;

    /**
     * 当前月22号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_22")
    private String currentMonth22;

    /**
     * 当前月23号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_23")
    private String currentMonth23;

    /**
     * 当前月24号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_24")
    private String currentMonth24;

    /**
     * 当前月25号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_25")
    private String currentMonth25;

    /**
     * 当前月26号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_26")
    private String currentMonth26;

    /**
     * 当前月27号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_27")
    private String currentMonth27;

    /**
     * 当前月28号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_28")
    private String currentMonth28;

    /**
     * 当前月29号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_29")
    private String currentMonth29;

    /**
     * 当前月30号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_30")
    private String currentMonth30;

    /**
     * 当前月31号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("CURRENT_MONTH_31")
    private String currentMonth31;

    /**
     * 下一月
     */
    @TableField("NEXT_MONTH")
    private String nextMonth;

    /**
     * 下一月1号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_1")
    private String nextMonth1;

    /**
     * 下一月2号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_2")
    private String nextMonth2;

    /**
     * 下一月3号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_3")
    private String nextMonth3;

    /**
     * 下一月4号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_4")
    private String nextMonth4;

    /**
     * 下一月5号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_5")
    private String nextMonth5;

    /**
     * 下一月6号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_6")
    private String nextMonth6;

    /**
     * 下一月7号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_7")
    private String nextMonth7;

    /**
     * 下一月8号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_8")
    private String nextMonth8;

    /**
     * 下一月9号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_9")
    private String nextMonth9;

    /**
     * 下一月10号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_10")
    private String nextMonth10;

    /**
     * 下一月11号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_11")
    private String nextMonth11;

    /**
     * 下一月12号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_12")
    private String nextMonth12;

    /**
     * 下一月13号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_13")
    private String nextMonth13;

    /**
     * 下一月14号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_14")
    private String nextMonth14;

    /**
     * 下一月15号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_15")
    private String nextMonth15;

    /**
     * 下一月16号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_16")
    private String nextMonth16;

    /**
     * 下一月17号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_17")
    private String nextMonth17;

    /**
     * 下一月18号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_18")
    private String nextMonth18;

    /**
     * 下一月19号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_19")
    private String nextMonth19;

    /**
     * 下一月20号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_20")
    private String nextMonth20;

    /**
     * 下一月21号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_21")
    private String nextMonth21;

    /**
     * 下一月22号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_22")
    private String nextMonth22;

    /**
     * 下一月23号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_23")
    private String nextMonth23;

    /**
     * 下一月24号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_24")
    private String nextMonth24;

    /**
     * 下一月25号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_25")
    private String nextMonth25;

    /**
     * 下一月26号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_26")
    private String nextMonth26;

    /**
     * 下一月27号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_27")
    private String nextMonth27;

    /**
     * 下一月28号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_28")
    private String nextMonth28;

    /**
     * 下一月29号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_29")
    private String nextMonth29;

    /**
     * 下一月30号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_30")
    private String nextMonth30;

    /**
     * 下一月31号
     */
    @JSONField(serializeUsing= StringToJsonSerializer.class)
    @TableField("NEXT_MONTH_31")
    private String nextMonth31;

    /**
     * 最新同步时间
     */
    @TableField("LASTSYNCTIME")
    private Date lastsynctime;


}
