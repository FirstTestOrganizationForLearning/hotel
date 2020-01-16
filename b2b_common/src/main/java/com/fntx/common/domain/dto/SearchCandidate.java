package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 魏世杰
 * @Date: 2019/7/22 14:38
 * @Description: 获取酒店详细信息 请求model
 */
public class SearchCandidate implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name = "HotelID")
    private long HotelID;
    @JSONField(name = "RoomIDs")
    private List<String> RoomIDs;

    public void setHotelID(long HotelID) {
        this.HotelID = HotelID;
    }

    public long getHotelID() {
        return HotelID;
    }

    public void setRoomIDs(List<String> RoomIDs) {
        this.RoomIDs = RoomIDs;
    }

    public List<String> getRoomIDs() {
        return RoomIDs;
    }
}