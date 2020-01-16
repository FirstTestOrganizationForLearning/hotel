package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 魏世杰
 * @Date: 2019/7/22 14:37
 * @Description: 获取酒店详细信息 顶级请求model
 */
public class SearchCandi implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @JSONField(name = "SearchCandidate")
    private com.fntx.common.domain.dto.SearchCandidate SearchCandidate;
    @JSONField(name = "Settings")
    private com.fntx.common.domain.dto.Settings Settings;

    public void setSearchCandidate(com.fntx.common.domain.dto.SearchCandidate SearchCandidate) {
        this.SearchCandidate = SearchCandidate;
    }

    public com.fntx.common.domain.dto.SearchCandidate getSearchCandidate() {
        return SearchCandidate;
    }

    public void setSettings(com.fntx.common.domain.dto.Settings Settings) {
        this.Settings = Settings;
    }

    public com.fntx.common.domain.dto.Settings getSettings() {
        return Settings;
    }

}