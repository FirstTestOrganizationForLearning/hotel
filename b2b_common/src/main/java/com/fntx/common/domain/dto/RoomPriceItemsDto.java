package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: RoomPriceItemsDto
 * @Author: 王俊文
 * @Date: 19-7-22 下午5:42
 * @Description: TODO
 */
public class RoomPriceItemsDto  implements Serializable
{
    private static final long serialVersionUID = 1L;
    @JSONField(name = "RoomTypeID")
    private int RoomTypeID;
    @JSONField(name = "RoomPriceInfos")
    private List<RoomPriceInfosBeanDto> RoomPriceInfos;

    public int getRoomTypeID() {
        return RoomTypeID;
    }

    public void setRoomTypeID(int RoomTypeID) {
        this.RoomTypeID = RoomTypeID;
    }

    public List<RoomPriceInfosBeanDto> getRoomPriceInfos() {
        return RoomPriceInfos;
    }

    public void setRoomPriceInfos(List<RoomPriceInfosBeanDto> RoomPriceInfos) {
        this.RoomPriceInfos = RoomPriceInfos;
    }
}
