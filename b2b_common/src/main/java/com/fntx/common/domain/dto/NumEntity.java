package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: NumEntity
 * @Author: 王俊文
 * @Date: 19-7-18 下午4:10
 * @Description: 起价城市信息
 */
public class NumEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * City : 85
     * CityName : 永嘉
     * CityNum : 1
     * CityEName : Yongjia
     */
    @JSONField(name="City")
    private int City;
    @JSONField(name="CityName")
    private String CityName;
    @JSONField(name="CityNum")
    private int CityNum;
    @JSONField(name="CityEName")
    private String CityEName;

    public int getCity() {
        return City;
    }

    public void setCity(int City) {
        this.City = City;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public int getCityNum() {
        return CityNum;
    }

    public void setCityNum(int CityNum) {
        this.CityNum = CityNum;
    }

    public String getCityEName() {
        return CityEName;
    }

    public void setCityEName(String CityEName) {
        this.CityEName = CityEName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumEntity)) return false;
        NumEntity that = (NumEntity) o;
        return City == that.City &&
                CityNum == that.CityNum &&
                CityName.equals(that.CityName) &&
                CityEName.equals(that.CityEName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(City, CityName, CityNum, CityEName);
    }

    @Override
    public String toString() {
        return "NumEntity{" +
                "City=" + City +
                ", CityName='" + CityName + '\'' +
                ", CityNum=" + CityNum +
                ", CityEName='" + CityEName + '\'' +
                '}';
    }
}
