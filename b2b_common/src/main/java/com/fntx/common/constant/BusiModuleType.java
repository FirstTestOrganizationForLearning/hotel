package com.fntx.common.constant;

public enum BusiModuleType {
    TICKET("0000500001", "机票"),
    HOTEL("0000500002", "酒店"),
    TRAIN("0000500003", "火车票"),
    INTERNATIONAL_TICKET("0000500004", "国际机票"),
    INTERNATIONAL_HOTEL("0000500005", "国际酒店") ;
    private String code;
    private String name;

    BusiModuleType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public BusiModuleType setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public BusiModuleType setName(String name) {
        this.name = name;
        return this;
    }

    // 根据code返回枚举类型,主要在switch中使用
    public static BusiModuleType getByCode(String value) {
        for (BusiModuleType code : values())
        {
            if (code.getCode() .equals(value))
            {
                return code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PayType{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}