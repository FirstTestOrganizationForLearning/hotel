package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fntx.common.domain.ErrorResp;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 魏世杰
 * @Date: 2019/7/22 14:39
 * @Description: 获取酒店详细信息 请求model
 */
public class Settings extends ErrorResp implements Serializable {

    private static final long serialVersionUID = 1L;
    @JSONField(name = "PrimaryLangID")
    private String PrimaryLangID;
    @JSONField(name = "ExtendedNodes")
    private List<String> ExtendedNodes;

    public void setPrimaryLangID(String PrimaryLangID) {
        this.PrimaryLangID = PrimaryLangID;
    }

    public String getPrimaryLangID() {
        return PrimaryLangID;
    }

    public void setExtendedNodes(List<String> ExtendedNodes) {
        this.ExtendedNodes = ExtendedNodes;
    }

    public List<String> getExtendedNodes() {
        return ExtendedNodes;
    }

}