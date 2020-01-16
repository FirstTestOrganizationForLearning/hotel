package com.fntx.common.constant;

// 订单取消理由
public enum OrderCancelReason {
    CXR_501(501, "行程改变"),
    CXR_502(502, "无法满足需求"),
    CXR_503(503, "酒店价格倒挂"),
    CXR_504(504, "其它途径预订"),
    CXR_505(505, "价格不准确"),
    CXR_506(506, "重复预订"),
    CXR_507(507, "入住登记不详细"),
    CXR_508(508, "其它（用户取消）"),
    CXR_509(509, "客户通知取消"),
    CXR_510(510, "变价"),
    CXR_511(511, "满房"),
    CXR_512(512, "未选择"),
    CXR_513(513, "隐藏订单"),
    CXR_514(514, "未提交"),
    CXR_515(515, "未收到预付款"),
    CXR_516(516, "noshow确实未入住"),
    CXR_517(517, "其它"),
    CXR_518(518, "房间更改"),
    CXR_519(519, "测试取消"),
    CXR_520(520, "现改预"),
    CXR_521(521, "无效，需要去除"),
    CXR_522(522, "后追担保"),
    CXR_523(523, "变更");
    private Integer code;
    private String name;

    OrderCancelReason(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}