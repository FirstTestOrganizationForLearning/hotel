package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelIncrDataDto
 * @Author: 王俊文
 * @Date: 19-8-6 下午2:23
 * @Description: 静态信息增量-mongodb逻辑实体 -- 国际
 */
public class InternationalHotelIncrDataDto implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * ChangeDetails : [{"Name":"IsOpen","NewValue":"F"}]
     * Category : RoomType
     * HotelID : 6268029
     * RoomTypeID : 33234614
     * RoomID : 0
     * ChangeTime : 2018-03-09T07:45:00.000+08:00
     */

    /**
    发生变化的实体类型，类别如下：
    Hotel-酒店
    RoomType-物理房型
    Room-售卖房型
     */
    @JSONField(name = "Category")
    private String Category;
    /**
    酒店ID
     */
    @JSONField(name = "HotelID")
    private String HotelID;
    /**
    物理房型ID
     */
    @JSONField(name = "RoomTypeID")
    private String RoomTypeID;
    /**
    售卖房型ID
     */
    @JSONField(name = "RoomID")
    private String RoomID;
    /**
    静态信息发生变化的时间
     */
    @JSONField(name = "ChangeTime")
    private String ChangeTime;
    /**
     增量明细信息，请求体传T时才能获取到，否则返回null；Name的枚举值如下：
     */
    @JSONField(name = "ChangeDetails")
    private List<ChangeDetailsBeanMg> ChangeDetails;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getHotelID() {
        return HotelID;
    }

    public void setHotelID(String HotelID) {
        this.HotelID = HotelID;
    }

    public String getRoomTypeID() {
        return RoomTypeID;
    }

    public void setRoomTypeID(String RoomTypeID) {
        this.RoomTypeID = RoomTypeID;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String RoomID) {
        this.RoomID = RoomID;
    }

    public String getChangeTime() {
        return ChangeTime;
    }

    public void setChangeTime(String ChangeTime) {
        this.ChangeTime = ChangeTime;
    }

    public List<ChangeDetailsBeanMg> getChangeDetails() {
        return ChangeDetails;
    }

    public void setChangeDetails(List<ChangeDetailsBeanMg> ChangeDetails) {
        this.ChangeDetails = ChangeDetails;
    }
}
