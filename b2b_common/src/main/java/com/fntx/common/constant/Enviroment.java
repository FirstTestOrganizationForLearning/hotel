package com.fntx.common.constant;

import com.fntx.common.domain.BaseApiReq;
import com.fntx.common.domain.BaseReq;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

/**
 * 公共变量类
 *
 * @copyright (C), 2019, 弗恩天下
 * @fileName:
 * @author: 胡庆康
 * @date: 2019/7/4 15:56
 */
public enum Enviroment {
    FINANCE_SEARCH_BALANCE("http://btb.fetxdistribution.tianxiafen.com/finance/searchBalance"),
    FINANCE_PAY("http://btb.fetxdistribution.tianxiafen.com/finance/pay"),
    FINANCE_CANCLE_PAY("http://btb.fetxdistribution.tianxiafen.com/finance/cancelPay"),
    DISTRIBUTOR_CHECK_URL("http://btb.fetxdistribution.tianxiafen.com/compAccount/hotel"),
    TEST_DISTRIBUTOR_CHECK_URL("http://localhost:8089/compAccount/hotel"),
    TEST_AID("1"),
    TEST_SID("50"),
    TEST_KEY("123456789"),
    TEST_UUID("f71dee19-9e09-42d9-a39c-40757d9d76e4"),
    TEST_FORMAT("JSON"),
    TEST_URL("openservice.open.uat.ctripqa.com"),
    TEST_HOTEL_LIST("ccc56c891a4d4f4fa4fc78f0aec29d03"),
    TEST_HOTEL_DETAIL("62a8bb573582435bbe8b361334efe36f"),
    TEST_HOTEL_ROOMTYPE_LIST("c04d1d7b7af449999dba88896fcc0811"),
    TEST_HOTEL_INCR_DATA("d0e65061aef24f88854031c60cabfd91"),
    TEST_HOTEL_INCR_ROOMTYPE("ef89b1f6ec8e49d39d5debac065a9a4a"),
    TEST_HOTEL_BASEPRICE("c659a717bf23422b907ff7f9c75022ed"),
    TEST_HOTEL_IOPRICE("14a14763194648f5b9d2427f00b3ca04"),
    TEST_HOTEL_BOOKABLE_CHECK("d67d24cfdc2940cd858f506c708cf0d4"),
    TEST_HOTEL_ORDER_CREATE("acf5c44749234668ba3c539cc1d26c87"),
    TEST_HOTEL_ORDER_SUBMIT("5f3c81bc0c90458ba94bfe2dafea8459"),
    TEST_HOTEL_ORDER_STATECHANGE("61aebb32ae1347c5a274fc9fb4b4f962"),
    TEST_HOTEL_ORDER_DETAIL("5db9d7e805ef4b36a8cf0e57900e118a"),
    TEST_HOTEL_ORDER_CANCEL("519114d791b94babba31f32eb5d559eb"),
    TEST_HOTEL_ORDER_NIGHTCHECK("e72f4335e6504ed5a8ef107609f44bec"),
    TEST_HOTEL_ORDER_NIGHTCHECK_POSTBACK("57594644239d42a396ead14c7cd9aa84"),
    TEST_HOTEL_ORDER_REFUND("f9de5673998d47e2b9103912eab772db"),

    //国内酒店接口权限AID
    AID("1"),
    //弗恩AID
    FN_AID("1"),
    //国内酒店接口权限SID
    SID("50"),
    //弗恩SID
    FN_SID("101"),
    //国内酒店接口权限KEY
    KEY("F9541499825448A78F1CA6CBB9397757"),
    //弗恩KEY
    FN_KEY("F9541499825448A78F1CA6CBB9397757"),
    //国内酒店接口权限UUID
    UUID("A63FA038-32B2-46C7-A363-42E651378752"),
    //弗恩酒店接口权限UUID
    FN_UUID("A63FA038-32B2-46C7-A363-42E651378752"),
    //海外酒店接口权限AID
    OVERSEAS_AID("1094051"),
    //海外酒店接口权限SID
    OVERSEAS_SID("2188967"),
    //海外酒店接口权限KEY
    OVERSEAS_KEY("312e1cb8a8734f719057f79d34d32c9d"),
    //海外酒店接口权限UUID
    OVERSEAS_UUID("850ed8da-3bae-4e3a-9bfe-26a94ad043f6"),
    //格式
    FORMAT("JSON"),
    //URL前缀
    URL("openservice.ctrip.com"),
    //弗恩url
    FN_URL("hotel.tianxiafen.com:82"),
    //获取酒店清单
    HOTEL_LIST("e6fb53910a134cdd96fc4ba30f864c7d"),
    //获取酒店静态信息
    HOTEL_DETAIL("becf5b5008064a26be77cac065688ca7"),
    //获取房型静态信息
    HOTEL_ROOMTYPE_LIST("39122604bf0e4b33a9569658cf273161"),
    //监测静态信息变化
    HOTEL_INCR_DATA("66fd1e3089fb448e912808cccbe017c0"),
    //监测房型上下线
    HOTEL_INCR_ROOMTYPE("4536a4178961443a937423c1eb8fa2ba"),
    //查询某城市下各酒店的起价（国内+海外）
    HOTEL_BASEPRICE("4c6f5dcc4663443b8d8ae044ad8bee10"),
    //查询某酒店的直连/入离报价（国内+海外）
    HOTEL_IOPRICE("4c15cadb05f14aa9836260c53ab74526"),
    //检查房型是否可预订
    HOTEL_BOOKABLE_CHECK("419f7a688e3c45f481d295708b870323"),
    //创建订单
    HOTEL_ORDER_CREATE("2b0c7e0eebe2467a97be3d284313a129"),
    //提交订单
    HOTEL_ORDER_SUBMIT("f40c5c71f84745cbbc73238252d9d43c"),
    //监测订单状态变化
    HOTEL_ORDER_STATECHANGE("e5e981e2cf9340f99f9cbf79c82668ac"),
    //获取订单详情
    HOTEL_ORDER_DETAIL("e4a2a8ac057f4b378c9e153ef3f35249"),
    //取消订单
    HOTEL_ORDER_CANCEL("f40a36ac2dfe42518fb883d33fa33391"),
    //fn 取消订单
    FN_ORDER_CANCEL("hotel.order.cancel"),
    //获取订单夜审结果
    HOTEL_ORDER_NIGHTCHECK("e682635a18f54affae63eb4790796054"),
    //fn 获取订单夜审结果
    FN_ORDER_NIGHTCHECK("hotel.order.nightcheck"),
    //回传订单夜审状态
    HOTEL_ORDER_NIGHTCHECK_POSTBACK("d9d1ac19898e4b4d9b1099ecb5a9d954"),
    //fn 回传订单夜审状态
    FN_ORDER_NIGHTCHECK_POSTBACK("hotel.order.nightcheck.postback"),
    //获取订单部分退款
    HOTEL_ORDER_REFUND("740848180ff44c9ead1dcb5815744eb0"),
    //获取订单部分退款
    FN_ORDER_REFUND("hotel.order.refund"),
    //获取token接口url
    API_GetAccessToken_URL("http://{app_path}/openserviceauth/authorize.ashx?AID={aid}&SID={sid}&KEY={key}"),
    //刷新token接口url
    API_RefreshToken_URL("http://{app_path}/openserviceauth/refresh.ashx?AID={aid}&SID={sid}&refresh_token={refresh_token}"),
    //api调用url
    API_COMMON_URL("http://{app_path}/openservice/serviceproxy.ashx?AID={aid}&SID={sid}&ICODE={ICODE}&UUID={GUID}&Token={Access_Token}&mode=1&format={format}"),
    // 弗恩api调用url
    API_FN_COMMON_URL("http://{app_path}/OpenService/FenHotelService.ashx?AID={aid}&SID={sid}&ICODE={ICODE}&UUID={GUID}&Token={Access_Token}&mode=1&format={format}"),


    //token暂时用弗恩地址
    FN_GetToken_URL("http://hotel.tianxiafen.com:82/OpenService/Authorize.ashx?AID=1&SID=101&KEY=F9541499825448A78F1CA6CBB9397757"),
    //token刷新暂用弗恩地址
    FN_RefreshToken_URL("http://hotel.tianxiafen.com:82/OpenService/Authorize.ashx?AID=1&SID=101&KEY=F9541499825448A78F1CA6CBB9397757&refresh_token={Refresh_Token}");
    static {
        ResourceBundle resource = ResourceBundle.getBundle("application");
        IS_RELEASE_ENV = Boolean.parseBoolean(resource.getString("Is_Release_Env"));
    }

    Enviroment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
    public static final boolean IS_RELEASE_ENV;

    /**
     * 根据当前系统环境获取API调用信息
     *
     * @author: 胡庆康
     * @date: 2019/7/22 11:14
     * @param: []
     * @returns: com.fntx.common.domain.BseApiReq
     */
    public static BaseApiReq getBaseApiReq(String moduleId) {
        BaseApiReq baseApiReq = new BaseApiReq();
        if ("0".equals(moduleId)) {
            baseApiReq.setAID(AID.getValue());
            baseApiReq.setSID(SID.getValue());
            baseApiReq.setFormat(FORMAT.getValue());
            baseApiReq.setKEY(KEY.getValue());
            baseApiReq.setMode("1");
            baseApiReq.setUUID(UUID.getValue());
            baseApiReq.setUrl(URL.getValue());
        } else if ("1".equals(moduleId)){
            baseApiReq.setAID(OVERSEAS_AID.getValue());
            baseApiReq.setSID(OVERSEAS_SID.getValue());
            baseApiReq.setFormat(FORMAT.getValue());
            baseApiReq.setKEY(OVERSEAS_KEY.getValue());
            baseApiReq.setMode("1");
            baseApiReq.setUUID(OVERSEAS_UUID.getValue());
            baseApiReq.setUrl(URL.getValue());
        }else {
            System.out.println("非法moduleId");
            return null;
        }
        return baseApiReq;
    }

}
