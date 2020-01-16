package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: BStaticChangeDetailDto
 * @Author: 王俊文
 * @Date: 19-8-6 下午2:16
 * @Description: TODO
 */
public class BStaticChangeDetailDto implements Serializable
{
    private static final long serialVersionUID = -4638553302343533272L;
    /**
     * 增量类型
     */
    @JSONField(name = "ChangeDetails")
    private String name;

    /**
     * 增量内容
     */
    @JSONField(name = "ChangeDetails")
    private String newvalue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof BStaticChangeDetailDto)) {return false;}
        BStaticChangeDetailDto that = (BStaticChangeDetailDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(newvalue, that.newvalue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, newvalue);
    }

    @Override
    public String toString() {
        return "BStaticChangeDetailDto{" +
                "name='" + name + '\'' +
                ", newvalue='" + newvalue + '\'' +
                '}';
    }
}
