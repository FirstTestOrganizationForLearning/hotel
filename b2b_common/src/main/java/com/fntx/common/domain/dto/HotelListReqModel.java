package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @description: 酒店清单请求
 * @author: 魏世杰
 * @date: Created in 2019/7/29 11:19
 * @version: 1.0
 * @modified By:
 */
public class HotelListReqModel implements Serializable {

    @JSONField(name = "City")
    private String city;
    @JSONField(name = "PageIndex")
    private String pageIndex;
    @JSONField(name = "PageSize")
    private String pageSize;

    public HotelListReqModel(String city, String pageIndex, String pageSize) {
        super();
        this.city = city;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }
}
