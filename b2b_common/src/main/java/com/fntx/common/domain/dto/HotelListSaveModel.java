package com.fntx.common.domain.dto;

import java.io.Serializable;

/**
 * @description: 携程酒店清单存入MongoDB中的对象
 * @author: 魏世杰
 * @date: Created in 2019/7/29 11:37
 * @version: 1.0
 * @modified By:
 */
public class HotelListSaveModel implements Serializable {

    private HotelListReqModel hotelListReqModel;
    private HotelListResponseInfo hotelListResponseInfo;

    public HotelListReqModel getHotelListReqModel() {
        return hotelListReqModel;
    }

    public void setHotelListReqModel(HotelListReqModel hotelListReqModel) {
        this.hotelListReqModel = hotelListReqModel;
    }

    public HotelListResponseInfo getHotelListResponseInfo() {
        return hotelListResponseInfo;
    }

    public void setHotelListResponseInfo(HotelListResponseInfo hotelListResponseInfo) {
        this.hotelListResponseInfo = hotelListResponseInfo;
    }
}
