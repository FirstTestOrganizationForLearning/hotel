package com.fntx.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fntx.common.constant.BusiModuleType;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.constant.HotelOrderState;
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.domain.BaseReq;
import com.fntx.common.domain.FinanceResponse;
import com.fntx.common.utils.DistributorCheck;
import com.fntx.order.dao.HtOrderMapper;
import com.fntx.order.dao.HtOrderOplogMapper;
import com.fntx.order.domain.HtOrder;
import com.fntx.order.feign.AdminFeign;
import com.fntx.order.po.PaymentCallbackResponse;
import com.fntx.order.service.IOrderService;
import com.fntx.order.utils.RemoteService;
import com.fntx.order.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class OrderApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    HtOrderOplogMapper bHotelOrderOplogMapper;
    @Autowired
    HtOrderMapper orderMapper;
    @Autowired
    IOrderService orderService;
    @Autowired
    AdminFeign adminFeign;
    @Autowired
    RemoteService remoteService;

    @Test
    public void pay() {
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setAID("hotelTest");
        baseInfo.setSID("hotel");
        baseInfo.setUUID("asdfghjk");
        baseInfo.setMode("1");
        baseInfo.setFormat("JSON");
        PaymentCallbackResponse payResult = TestUtils.getCallback(111.00);
        FinanceResponse  financeResponse = remoteService.pay(baseInfo, "9885216269", "-" + payResult.pay_amount,null);
        System.out.println(financeResponse.toString());
    }

    @Test
    public void test() {
        HtOrder htOrder = new HtOrder();
        htOrder.setOrderState(HotelOrderState.Process.getCode());
        QueryWrapper<HtOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID", "10358898327");
        orderMapper.update(htOrder, queryWrapper);
        System.out.println(orderMapper.selectById("10358898327").toString());
    }

    @Test
    public void test3() {
        QueryWrapper<HtOrder> queryWrapper = new QueryWrapper<>();
        System.out.println(orderMapper.selectCount(queryWrapper));
        System.out.println(orderMapper.selectList(queryWrapper));
    }

    /**
     * 订单表同步订单详情表
     *
     * @param
     * @author 胡庆康
     * @date 2019/7/25 15:28
     * @returns
     */
    @Test
    public void orderSync() {
        JSONObject response = orderService.orderSync();
        System.out.println(response.toJSONString());
    }

    @Test
    public void testDist() {
        BaseReq baseReqTemp = new BaseReq();
        baseReqTemp.setAID("hotelTest");
        baseReqTemp.setSID("hotel");
        baseReqTemp.setUUID("asdfghjk");
        baseReqTemp.setKEY("123");
        baseReqTemp.setModuleId(BusiModuleType.HOTEL.getCode());
        BaseReq baseReq = DistributorCheck.distributorCheck(baseReqTemp);
        System.out.println(baseReq.toString());
    }

    /**
     * 检查房型是否可预订测试
     *
     * @author 胡庆康
     * @date 2019/7/24 4:06
     * @returns []
     */
    @Test
    public void checkBookable() {
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setAID(Enviroment.TEST_AID.getValue());
        baseInfo.setSID(Enviroment.TEST_SID.getValue());
        baseInfo.setUUID(Enviroment.TEST_UUID.getValue());
        baseInfo.setFormat("JSON");
        baseInfo.setMode("1");
        baseInfo.setICODE("hotel.bookable.check");
        baseInfo.setToken(Enviroment.IS_RELEASE_ENV == true ? stringRedisTemplate.opsForValue().get("token") : stringRedisTemplate.opsForValue().get("testToken"));
        JSONObject param = JSONObject.parseObject("{\n" +
                "    \"AvailRequestSegments\": {\n" +
                "        \"AvailRequestSegment\": {\n" +
                "            \"HotelSearchCriteria\": {\n" +
                "                \"Criterion\": {\n" +
                "                    \"HotelRef\": {\n" +
                "                        \"HotelCode\": \"436187\"\n" +
                "                    },\n" +
                "                    \"StayDateRange\": {\n" +
                "                        \"Start\": \"2019-07-26\",\n" +
                "                        \"End\": \"2019-07-29\"\n" +
                "                    },\n" +
                "                    \"RatePlanCandidates\": {\n" +
                "                        \"RatePlanCandidate\": {\n" +
                "                            \"RoomID\": \"18162\",\n" +
                "                            \"RatePlanID\": \"11396002\",\n" +
                "                            \"RatePlanCategory\": \"501\",\n" +
                "                            \"PrepaidIndicator\": true\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"RoomStayCandidates\": {\n" +
                "                        \"RoomStayCandidate\": {\n" +
                "                            \"BookingCode\": \"105425599\",\n" +
                "                            \"GuestCounts\": {\n" +
                "                                \"GuestCount\": {\n" +
                "                                    \"Count\": 1\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"Quantity\": 1\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"TPA_Extensions\": {\n" +
                "                        \"LateArrivalTime\": \"2019-07-26T20:00:00+08:00\",\n" +
                "                        \"DisplayCurrency\": \"CNY\",\n" +
                "                        \"SpecialRequests\": [\n" +
                "                            {\n" +
                "                                \"Text\": \"\",\n" +
                "                                \"Name\": \"BedType\",\n" +
                "                                \"ParagraphNumber\": \"7\",\n" +
                "                                \"RequestCode\": \"7\",\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"ShadowRoomInfo\": {\n" +
                "                            \"ShadowID\": 1100012201\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"TimeStamp\": \"2018-11-11T00:00:00.0000000\",\n" +
                "    \"Version\": 1,\n" +
                "    \"PrimaryLangID\": \"en\",\n" +
                "    \"TransactionIdentifier\": \"7422CF9F311E48388D9ABA2AC7B54052\"\n" +
                "}");
        System.out.println(orderService.checkBookable(param));
    }

    /**
     * 创建订单测试
     *
     * @author 胡庆康
     * @date 2019/7/24 4:06
     * @returns []
     */
    @Test
    public void orderCreate() {

    }

    /**
     * 获取订单详情预订测试
     *
     * @author 胡庆康
     * @date 2019/7/24 4:06
     * @returns []
     */
    @Test
    public void apiDetail() {
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setAID(Enviroment.TEST_AID.getValue());
        baseInfo.setSID(Enviroment.TEST_SID.getValue());
        baseInfo.setUUID(Enviroment.TEST_UUID.getValue());
        baseInfo.setFormat("JSON");
        baseInfo.setMode("1");
        baseInfo.setICODE("hotel.detail");
        System.out.println(stringRedisTemplate.opsForValue().get("testToken"));
        baseInfo.setToken(stringRedisTemplate.opsForValue().get("testToken"));
        JSONObject param = JSONObject.parseObject("{\n" +
                "    \"UniqueID\": [\n" +
                "        {\n" +
                "            \"Type\": \"28\",\n" +
                "            \"ID\": \"1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Type\": \"503\",\n" +
                "            \"ID\": \"50\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Type\": \"501\",\n" +
                "            \"ID\": \"3019651299\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"TimeStamp\": \"2018-01-14T12:00:36.7002258\",\n" +
                "    \"Version\": 1,\n" +
                "    \"PrimaryLangID\": \"en\"\n" +
                "}");
        System.out.println(orderService.orderDetail(baseInfo, param));
    }

}
