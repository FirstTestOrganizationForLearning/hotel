package com.fntx.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fntx.admin.service.IHotelStaticInformationService;
import com.fntx.admin.utils.FastJsonUtils;
import com.fntx.admin.utils.HttpClientUtil;
import com.fntx.common.dao.BHotelBusinessDistrictMapper;
import com.fntx.common.dao.BHotelDetailMapper;
import com.fntx.common.dao.BHotelListMapper;
import com.fntx.common.domain.BHotelDetail;
import com.fntx.common.domain.BHotelList;
import com.fntx.common.domain.dto.*;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTests {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BHotelListMapper bHotelListMapper;

    @Autowired
    private BHotelDetailMapper bHotelDetailMapper;

    @Autowired
    private BHotelBusinessDistrictMapper bHotelBusinessDistrictMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IHotelStaticInformationService iHotelStaticInformationService;


    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
        strs.add("String");
        for (int y = 0; y < strs.size(); y++) {
            if (y == strs.size() - 1) {
                System.out.println("1111111111111");
            }else if(y == strs.size()){
                System.out.println("222222222222");
            }
        }


    }

    @Test
    public void contextLoads() {

        Page<BHotelList> page = new Page<>(1, 10);
        page.setSearchCount(false);
        QueryWrapper<BHotelList> wrapper = new QueryWrapper<>();
//        wrapper.eq("ID",1439634);
        //wrapper.eq("ORDER_STATE",0);
        PageHelper.startPage(1, 10);
        // IPage<BHotelList> result = bHotelListMapper.selectPage(page,wrapper);
        List<BHotelList> result1 = bHotelListMapper.selectList(wrapper);
//        System.out.println(bHotelListMapper.selectList(wrapper));
        System.out.println("-----------------------------------------" + JSON.toJSONString(result1));
    }


    @Test
    public void testRedis() {

        String accessToken = stringRedisTemplate.opsForValue().get("testToken");

        System.out.println(accessToken);
    }

    @Test
    public void testMongo() {

        String hotelListUrl = "http://hotel.tianxiafen.com:82/OpenService/FenHotelService.ashx?AID=1&SID=101&ICODE=hotel.detail&UUID=A63FA038-32B2-46C7-A363-42E651378752&Token=token&mode=1&format=JSON";
        hotelListUrl = hotelListUrl.replace("token", "1ddacd9323fe4deabdf542c1707e9470");
        SearchCandi candi = new SearchCandi();
        SearchCandidate searchCandidate = new SearchCandidate();
        searchCandidate.setHotelID(4633577);
        Settings settings = new Settings();
        settings.setPrimaryLangID("zh-cn");
        List<String> setings = new ArrayList<String>();
        setings.add("HotelStaticInfo.GeoInfo");
        setings.add("HotelStaticInfo.NearbyPOIs");
        setings.add("HotelStaticInfo.TransportationInfos");
        setings.add("HotelStaticInfo.Brand");
        setings.add("HotelStaticInfo.Group");
        setings.add("HotelStaticInfo.Ratings");
        setings.add("HotelStaticInfo.Policies");
        setings.add("HotelStaticInfo.NormalizedPolicies.ChildAndExtraBedPolicy");
        setings.add("HotelStaticInfo.NormalizedPolicies.MealsPolicy");
        setings.add("HotelStaticInfo.AcceptedCreditCards");
        setings.add("HotelStaticInfo.ImportantNotices");
        setings.add("HotelStaticInfo.Facilities");
        setings.add("HotelStaticInfo.Pictures");
        setings.add("HotelStaticInfo.Descriptions");
        setings.add("HotelStaticInfo.Themes");
        setings.add("HotelStaticInfo.ContactInfo");
        setings.add("HotelStaticInfo.ArrivalTimeLimitInfo");
        setings.add("HotelStaticInfo.DepartureTimeLimitInfo");
        setings.add("HotelStaticInfo.HotelTags.IsBookable");
        settings.setExtendedNodes(setings);
        candi.setSearchCandidate(searchCandidate);
        candi.setSettings(settings);
        String reqJson = FastJsonUtils.toJSONString(candi);
        HotelMongoModel HotelMongoModel = new HotelMongoModel();

        String hotelDetailJson = HttpClientUtil.doPostJson(hotelListUrl, reqJson);
        System.out.println("设置熔断时间后的json:"+hotelDetailJson);


        HotelMongoModel.setHotelId(String.valueOf(4633577));
        HotelMongoModel.setHotelDetail(hotelDetailJson);
        mongoTemplate.save(HotelMongoModel);

        HotelMongoModel MongoModel = null;
        Query query = new Query(Criteria.where("hotelId").is("37064731"));
        MongoModel = mongoTemplate.findOne(query, HotelMongoModel.class);


        System.out.println("从MongoDB中获取酒店详情json：" + MongoModel.getHotelDetail());

    }

    @Test
    public void testHotelInfo() {
        iHotelStaticInformationService.addHotelDetails(785, 1000, 1);
    }


    @Test
    public void testHotelList() {

        iHotelStaticInformationService.addHotelList(93, 1000, 1);

    }
    @Test
    public void tese1() {

        while (true){

        }

    }


    @Test
    public void testHotelDetailMongo() {

        iHotelStaticInformationService.addHotelDetailsToMongo(1119, 1000, 1);

    }

    @Test
    public void testRoomDetailMongo() {

      iHotelStaticInformationService.addHotelRoomDetailsToMongo(396, 1000, 1);

    }



    @Test
    public void testGetMongo() {
//        HotelListSaveModel hotelListSaveModel = null;
//        Query query = new Query(Criteria.where("hotelListReqModel.city").is("680311").and("hotelListReqModel.pageIndex").is(1).and("hotelListReqModel.pageSize").is(5000));
//
//        hotelListSaveModel = mongoTemplate.findOne(query, HotelListSaveModel.class);
//        System.out.println("从MongoDB中获取酒店详情json：" + FastJsonUtils.toJSONString(hotelListSaveModel.getHotelListResponseInfo()));


        HotelMongoModel MongoModel = null;
        Query query = new Query(Criteria.where("hotelId").is("37064731"));
        MongoModel = mongoTemplate.findOne(query, HotelMongoModel.class);


        System.out.println("从MongoDB中获取酒店详情json：" + MongoModel.getHotelDetail());
    }



    @Test
    public  void testQuery(){
        // 第一步，先获取BHotelList 的数据
        QueryWrapper<BHotelList> wrapper = new QueryWrapper<>();
        PageHelper.startPage(600, 10);
        List<BHotelList> hotelLists = bHotelListMapper.selectList(wrapper);

        // 第二步，把 BHotelList当中的hotelId取出来放到list   beforeHotelIds  中
        List<Long>  beforeHotelIds = new ArrayList<>();
        for (BHotelList hotelList : hotelLists) {
            beforeHotelIds.add(hotelList.getHotelId());
        }
        System.out.println("查询到的beforeHotelIds:"+beforeHotelIds.toString());

        //第三步，用beforeHotelIds查询存在 BHotelDetail 数据表中的数据
        List<BHotelDetail> hotelDetails  = bHotelDetailMapper.selectBatchIds(beforeHotelIds);

        //第四步，把查询到的hotelDetails的hotelIdS 放入  afterHotelIds中
        List<Long>  afterHotelIds = new ArrayList<>();
        for (BHotelDetail hotelDetail : hotelDetails) {
            afterHotelIds.add(hotelDetail.getHotelId());
        }
        System.out.println("查询到的afterHotelIds:"+afterHotelIds.toString());

        //第五步 去重
        HashSet h1 = new HashSet(beforeHotelIds);
        HashSet h2 = new HashSet(afterHotelIds);
        h1.removeAll(h2);
        afterHotelIds.clear();
        afterHotelIds.addAll(h1);

        System.out.println("去重后的afterHotelIds:"+afterHotelIds.toString());

        // 第六步 用去重后的 afterHotelIds 查 BHotelList
        List<BHotelList> afterHotelLists = bHotelListMapper.selectBatchIds(afterHotelIds);

        // 直接调第三方接口去获取酒店详情，插入数据库


    }

}
