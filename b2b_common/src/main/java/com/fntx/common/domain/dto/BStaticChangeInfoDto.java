package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: BStaticChangeInfoDto
 * @Author: 王俊文
 * @Date: 19-8-6 下午2:11
 * @Description: TODO
 */
public class BStaticChangeInfoDto implements Serializable
{
    private static final long serialVersionUID = 1551689877111674589L;
    @JSONField(name = "ChangeDetails")
    List<BStaticChangeDetailDto> ChangeDetails;

    /**
     * 实体类型

     */
    @JSONField(name = "Category")
    private String category;

    /**
     * 酒店ID

     */
    @JSONField(name = "HotelID")
    private Long hotelId;

    /**
     * 物理房型ID

     */
    @JSONField(name = "RoomTypeID")
    private Long roomtypeId;

    /**
     * 售卖房型ID

     */
    @JSONField(name = "RoomID")
    private Long roomId;

    /**
     * 静态信息发生变化的时间

     */
    @JSONField(name = "ChangeTime")
    private Date changetime;

    public List<BStaticChangeDetailDto> getChangeDetails() {
        return ChangeDetails;
    }

    public void setChangeDetails(List<BStaticChangeDetailDto> changeDetails) {
        ChangeDetails = changeDetails;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getRoomtypeId() {
        return roomtypeId;
    }

    public void setRoomtypeId(Long roomtypeId) {
        this.roomtypeId = roomtypeId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Date getChangetime() {
        return changetime;
    }

    public void setChangetime(Date changetime) {
        this.changetime = changetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof BStaticChangeInfoDto)) {return false;}
        BStaticChangeInfoDto that = (BStaticChangeInfoDto) o;
        return Objects.equals(ChangeDetails, that.ChangeDetails) &&
                Objects.equals(category, that.category) &&
                Objects.equals(hotelId, that.hotelId) &&
                Objects.equals(roomtypeId, that.roomtypeId) &&
                Objects.equals(roomId, that.roomId) &&
                Objects.equals(changetime, that.changetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ChangeDetails, category, hotelId, roomtypeId, roomId, changetime);
    }

    @Override
    public String toString() {
        return "BStaticChangeInfoDto{" +
                "ChangeDetails=" + ChangeDetails +
                ", category='" + category + '\'' +
                ", hotelId=" + hotelId +
                ", roomtypeId=" + roomtypeId +
                ", roomId=" + roomId +
                ", changetime=" + changetime +
                '}';
    }
}
