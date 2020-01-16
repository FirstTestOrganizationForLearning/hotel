package com.fntx.common.domain.dto;


import com.fntx.common.domain.ErrorResp;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 魏世杰
 * @Date: 2019/7/22 16:50
 * @Description: 酒店详情响应
 */
public class HotelStaticResPonse extends ErrorResp implements Serializable {


    /**
     * ResponseStatus : {"Timestamp":"2019-07-10T16:55:33.636+08:00","Ack":"Success","Errors":[],"Extension":[]}
     * HotelStaticInfo : {"GeoInfo":{"City":{"Code":"28","Name":"成都"},"Area":{"Code":"396","Name":"锦江区"},"PostalCode":"610016","Address":"人民南路二段55号","BusinessDistrict":[{"Code":"189","Name":"天府广场、盐市口商业区"},{"Code":"187","Name":"春熙路、太古里商业区"}],"Coordinates":[{"Provider":"Baidu","LNG":104.07336228847,"LAT":30.654747544611},{"Provider":"Google","LNG":104.0668666,"LAT":30.6486934},{"Provider":"AutoNavi","LNG":104.0668666,"LAT":30.6486934}],"Province":{"Code":"22","Name":"四川"},"Country":{"Code":"1","Name":"中国"}},"NearbyPOIs":[{"Coordinates":[{"Provider":"Unknown","LNG":104.06603,"LAT":30.6472}],"Name":"美美力诚百货","Type":"102","Distance":"0.1845463"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06656,"LAT":30.65153}],"Name":"成都百货大楼","Type":"103","Distance":"0.317131"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06638,"LAT":30.64531}],"Name":"锦江宾馆","Type":"201","Distance":"0.3795114"},{"Coordinates":[{"Provider":"Unknown","LNG":104.065895080566,"LAT":30.6574726104736}],"Name":"天府广场","Type":"201","Distance":"0.9817157"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06661,"LAT":30.63798}],"Name":"华西坝","Type":"201","Distance":"1.1928639"},{"Coordinates":[{"Provider":"Unknown","LNG":104.080535888672,"LAT":30.6531791687012}],"Name":"春熙路","Type":"201","Distance":"1.4010704"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06597,"LAT":30.66175}],"Name":"骡马市","Type":"201","Distance":"1.4559877"},{"Coordinates":[{"Provider":"Unknown","LNG":104.057083129883,"LAT":30.6595554351807}],"Name":"人民公园","Type":"201","Distance":"1.5296568"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06677,"LAT":30.62867}],"Name":"省体育馆","Type":"201","Distance":"1.702528"},{"Coordinates":[{"Provider":"Unknown","LNG":104.087104797363,"LAT":30.6482257843018}],"Name":"东门大桥","Type":"201","Distance":"1.9388955"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06179,"LAT":30.63863}],"Name":"四川大学华西医院","Type":"104","Distance":"1.2212156"},{"Coordinates":[{"Provider":"Unknown","LNG":104.0663,"LAT":30.6376}],"Name":"四川大学华西口腔医院","Type":"104","Distance":"1.236104"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06367,"LAT":30.66519}],"Name":"成都市第三人民医院","Type":"104","Distance":"1.8617313"},{"Coordinates":[{"Provider":"Unknown","LNG":104.07785,"LAT":30.62925}],"Name":"四川大学","Type":"101","Distance":"1.9302567"}],"TransportationInfos":[{"Coordinates":[{"Provider":"Unknown","LNG":104.076889038086,"LAT":30.6551647186279},{"Provider":"Baidu","LNG":104.083419799805,"LAT":30.6610546112061}],"Name":"春熙路、太古里商业区","Type":"1","Distance":"2.19000005722046","Directions":"驾车约2.19公里(约5.0分钟)","TransportationType":"0","TimeTaken":"5.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.065811157227,"LAT":30.6573905944824},{"Provider":"Baidu","LNG":104.072296142578,"LAT":30.663459777832}],"Name":"天府广场、盐市口商业区","Type":"1","Distance":"1.05999994277954","Directions":"步行约1.06公里(约15.0分钟)","TransportationType":"1","TimeTaken":"15.0"},{"Coordinates":[{"Provider":"Unknown","LNG":103.957841,"LAT":30.57346},{"Provider":"Baidu","LNG":103.964359,"LAT":30.57933}],"Name":"双流国际机场","Type":"2","Distance":"18.9500007629395","Directions":"驾车约18.95公里(约26.0分钟)","TransportationType":"0","TimeTaken":"26.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.0734024,"LAT":30.6967602},{"Provider":"Baidu","LNG":104.0798111,"LAT":30.7028008}],"Name":"成都站","Type":"3","Distance":"5.76999998092651","Directions":"驾车约5.77公里(约25.0分钟)","TransportationType":"0","TimeTaken":"25.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.068618,"LAT":30.605776},{"Provider":"Baidu","LNG":104.0743,"LAT":30.61316}],"Name":"成都南站","Type":"3","Distance":"6.05999994277954","Directions":"驾车约6.06公里(约22.0分钟)","TransportationType":"0","TimeTaken":"22.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.065844,"LAT":30.657423},{"Provider":"Baidu","LNG":104.0723267245,"LAT":30.663491162}],"Name":"市中心","Type":"4","Distance":"1.04999995231628","Directions":"步行约1.05公里(约15.0分钟)","TransportationType":"1","TimeTaken":"15.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.055688,"LAT":30.663081},{"Provider":"Baidu","LNG":104.061617,"LAT":30.669481}],"Name":"宽窄巷子","Type":"5","Distance":"2.50999999046326","Directions":"驾车约2.51公里(约18.0分钟)","TransportationType":"0","TimeTaken":"18.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.076981,"LAT":30.655145},{"Provider":"Baidu","LNG":104.083563,"LAT":30.661093}],"Name":"春熙路","Type":"5","Distance":"2.19000005722046","Directions":"驾车约2.19公里(约9.0分钟)","TransportationType":"0","TimeTaken":"9.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.066053,"LAT":30.650046},{"Provider":"Baidu","LNG":104.0725065,"LAT":30.6562414}],"Name":"锦江宾馆","Type":"7","Distance":"0.409999996423721","Directions":"步行约0.41公里(约6.0分钟)","TransportationType":"1","TimeTaken":"6.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.066279,"LAT":30.642821},{"Provider":"Baidu","LNG":104.0727431,"LAT":30.6490443}],"Name":"华西坝","Type":"7","Distance":"0.660000026226044","Directions":"步行约0.66公里(约10.0分钟)","TransportationType":"1","TimeTaken":"10.0"}],"Brand":{"Pictures":{"Picture":[{"Code":"Small","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"},{"Code":"Mid","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"},{"Code":"Big","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"}],"Category":"logo"},"Code":"153","Name":"岷山饭店"},"Group":{"Code":"339","Name":"岷山"},"Ratings":[{"Type":"CtripRecommendLevel","Value":5},{"Type":"GuestOverallRating","Value":4.7},{"Type":"CtripUserRating","Value":5}],"Policies":[{"Text":"入住时间：14:00以后&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;离店时间：12:00以前","Code":"CheckInCheckOut"},{"Text":"不接受18岁以下客人单独入住。<br/>不接受18岁以下客人在无监护人陪同的情况下入住<br/>加床政策、儿童人数请参见您所选定的客房政策，若超过房型限定人数，可能需收取额外费用。提出的任何要求均需获得酒店的确认，所有服务详情以酒店告知为准。","Code":"Child"},{"Text":"自助早餐&nbsp;RMB&nbsp;88","Code":"Meal"},{"Text":"不可携带宠物。","Code":"Pet"},{"Text":"14:00","Code":"CheckInFrom"},{"Text":"","Code":"CheckInTo"},{"Text":"","Code":"CheckOutFrom"},{"Text":"12:00","Code":"CheckOutTo"},{"Text":"","Code":"CheckInOutContent"},{"Text":"F","Code":"IsLimitCheckInOut"}],"NormalizedPolicies":{"ChildAndExtraBedPolicy":{"ExtraBed":{"MaxQuantity":0,"MaxCribQuantity":0},"Descriptions":[{"Text":"加床政策、儿童人数请参考您所选择的客房政策，若超过房型限定人数，可能需要收取额外费用。提出的任何请求均需要获得酒店的确认，所有服务详情以酒店告知为准。","Category":"SpecialTips"},{"Text":"不接受18岁以下客人在无监护人陪同的情况下入住","Category":"ChildStayNotice"}],"AllowChildrenToStay":true,"AllowUseExistingBed":false,"AllowExtraBed":false,"AllowExtraCrib":false,"AllowExtraBedV2":"unknown","AllowExtraCribV2":"unknown"},"MealsPolicy":[{"Amount":[{"Type":"OriginalAmount","Amount":88,"Currency":"RMB"}],"MealType":"3"}]},"AcceptedCreditCards":[{"CardType":"6","CardName":"万事达(Master)"},{"CardType":"7","CardName":"威士(VISA)"},{"CardType":"8","CardName":"运通(AMEX)"},{"CardType":"9","CardName":"大来(Diners Club)"},{"CardType":"10","CardName":"JCB"},{"CardType":"14","CardName":"国内发行银联卡"}],"ImportantNotices":[],"Facilities":[{"FacilityItem":[{"ID":"1","Name":"中餐厅","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"2","Name":"西餐厅","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"3","Name":"咖啡厅","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"5","Name":"酒吧","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"6","Name":"会议厅","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"8","Name":"商务中心","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"11","Name":"外币兑换服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"13","Name":"国内长途电话","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"14","Name":"国际长途电话","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"15","Name":"洗衣服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"16","Name":"送餐服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"19","Name":"残疾人客房","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"24","Name":"棋牌室","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"41","Name":"理发美容中心","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"42","Name":"健身室","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"55","Name":"叫车服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"60","Name":"穿梭机场班车","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"61","Name":"室外游泳池","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"62","Name":"夜总会","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"63","Name":"茶室","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"67","Name":"烧烤","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"69","Name":"邮政服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"75","Name":"拖鞋","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"76","Name":"雨伞","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"77","Name":"书桌","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"78","Name":"浴室化妆放大镜","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"79","Name":"24小时热水","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"80","Name":"电热水壶","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"81","Name":"咖啡壶/茶壶","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"82","Name":"免费洗漱用品(6样以上)","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"83","Name":"免费瓶装水","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"85","Name":"迷你吧","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"86","Name":"熨衣设备","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"87","Name":"小冰箱","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"88","Name":"浴衣","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"89","Name":"多种规格电源插座","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"90","Name":"110V电压插座","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"91","Name":"浴缸","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"93","Name":"吹风机","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"94","Name":"房内保险箱","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"95","Name":"前台贵重物品保险柜","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"96","Name":"专职行李员","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"97","Name":"行李寄存","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"98","Name":"叫醒服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"99","Name":"自动取款机","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"100","Name":"免费停车场","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"103","Name":"免费旅游交通图(可赠送)","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"105","Name":"接机服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"106","Name":"大堂吧","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"107","Name":"空调","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"110","Name":"电梯","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"120","Name":"有可无线上网的公共区域 免费","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"123","Name":"租车服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"127","Name":"礼宾服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"131","Name":"办理私人登记入住/退房手续","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"137","Name":"婚宴服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"138","Name":"多国语言工作人员","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"139","Name":"管家服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"140","Name":"24小时前台服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"145","Name":"大堂免费报纸","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"160","Name":"专职门童","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"162","Name":"信用卡结算服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"163","Name":"一次性账单结算服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"164","Name":"多功能厅","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"165","Name":"24小时大堂经理","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"167","Name":"旅游票务专柜","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"170","Name":"公共音响系统","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"171","Name":"非经营性客人休息区","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"173","Name":"无烟楼层","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"174","Name":"行政楼层","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"176","Name":"多媒体演示系统","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"177","Name":"公共区域闭路电视监控系统","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"180","Name":"有线频道","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"182","Name":"液晶电视机","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"212","Name":"闹钟","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"213","Name":"针线包","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"215","Name":"220V电压插座","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"217","Name":"遮光窗帘","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"219","Name":"手动窗帘","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"223","Name":"备用床具","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"224","Name":"床具:鸭绒被","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"230","Name":"语音留言","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"231","Name":"欢迎礼品","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"252","Name":"洗浴间电话","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"253","Name":"沙发","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"256","Name":"免费报纸","Status":"2"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"258","Name":"电子秤","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"259","Name":"房间内高速上网","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"266","Name":"客房WIFI覆盖免费","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"331","Name":"儿童拖鞋","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"333","Name":"儿童牙刷","Status":"1"}],"CategoryName":"服务项目"}],"Pictures":[{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/5ff3ccf821be4c6fbab48a9e3c5739a7_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/78ee26c5f9334652b0f652daf69edc97_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g4/M07/32/BB/CggYHlYD9WKAFeGMAAQFmSxsDKE225_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//200r070000002r07725CF_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//200n070000002r07e1627_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//20030w000000kcexw5C7D_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//200q0w000000kbz4104AA_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/c1472eccee6949988ba10be019619088_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/db42c62939474ed6b28e950f08eb2787_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g4/M04/32/BB/CggYHlYD9WKAf-hyAALk41244VM067_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g4/M0A/32/7B/CggYHFYD9WKAbYARAAIlF4rj4O0308_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//2005080000003366v66FE_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//200c070000002r07p2E48_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//200w070000002r07d4DDB_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g5/M00/63/EE/CggYsVcghjCAOzYJAAFAqFHSdPU320_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g5/M08/62/AA/CggYsFcgiHKASZgZAAGE3SCbbTM065_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//200p070000002r07h4FB2_R_550_412.jpg"},{"Type":"3","Caption":"健身娱乐设施","URL":"http://dimg04.c-ctrip.com/images//200n0w000000k9qnp0676_R_550_412.jpg"},{"Type":"3","Caption":"健身娱乐设施","URL":"http://dimg04.c-ctrip.com/images//20060w000000kbavv0622_R_550_412.jpg"},{"Type":"3","Caption":"健身娱乐设施","URL":"http://dimg04.c-ctrip.com/images//200a0w000000kc01w29A9_R_550_412.jpg"},{"Type":"3","Caption":"健身娱乐设施","URL":"http://dimg04.c-ctrip.com/images//200e0o000000eoji792D8_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/f2448054f8d94fedbda035f1eeded99e_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g5/M05/20/8F/CggYsVb0jeqADvrWAARmiias7jo737_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//200s070000002r07a9D9F_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//200b0b0000005zlp4089C_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//2005070000002r07g9320_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//200w0b0000005zliaDCB0_R_550_412.jpg"},{"Type":"1","Caption":"Logo","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/5ff3ccf821be4c6fbab48a9e3c5739a7_R_550_412.jpg"}],"Descriptions":[{"Text":"四川岷山饭店座落于成都市市中区，毗邻繁华商业区，商务休闲购物方便快捷。近在咫尺的机场专线，在酒店附近穿梭市内观光巴士，不仅让您的商旅之行方便快捷，更使您与市区的繁荣同在。","Category":"1"},{"Text":"四川岷山饭店位于成都市的繁华中心地带\u2014\u2014人民南路二段，饭店紧邻机场巴士1号线始发站，地铁1号线，更有武侯祠、锦里、宽窄巷子、天府广场、春熙路、太古里约10分钟繁华商圈环抱。<br>岷山饭店隶属于四川岷山集团，整体设计风格富含巴蜀文化元素并与国际视野融合有致，设计型、风格化展现淋漓。是四川省首届及历届国家一类会议\u2014\u2014\u201c西博会\u201d的指定接待酒店，是大多数国内和国际世界500强企业的指定供应商，是各类外事、政务、商务接待的不二之选。位于酒店21楼的the river house西餐厅，连续两年上榜大众点评黑珍珠餐厅，已是成都必打卡西餐厅。","Category":"2"},{"Text":"","Category":"3"}],"Themes":[{"Code":"19","Name":"休闲度假"},{"Code":"21","Name":"设计师酒店"},{"Code":"119","Name":"浪漫情侣"},{"Code":"121","Name":"亲子酒店"},{"Code":"177","Name":"会议酒店"},{"Code":"190","Name":"商务出行"},{"Code":"219","Name":"特价频道"}],"ContactInfo":{"Telephone":"028-85583333","Fax":""},"ArrivalTimeLimitInfo":{"EarliestTime":"14:00","LatestTime":"","IsMustBe":"F"},"DepartureTimeLimitInfo":{"EarliestTime":"","LatestTime":"12:00"},"ApplicabilityInfo":{"HotelApplicability":"5"},"HotelID":391650,"HotelName":"四川岷山饭店","StarRating":5,"IsOfficialRating":true,"OpenYear":"1988-01-01","RenovationYear":"2018-04-01","RoomQuantity":305,"IsOnlineSignUp":false,"MaintainerCode":"1","Bookable":"T"}
     */

    private ResponseStatusBean ResponseStatus;
    private HotelStaticInfoBean HotelStaticInfo;

    public ResponseStatusBean getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(ResponseStatusBean ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    public HotelStaticInfoBean getHotelStaticInfo() {
        return HotelStaticInfo;
    }

    public void setHotelStaticInfo(HotelStaticInfoBean HotelStaticInfo) {
        this.HotelStaticInfo = HotelStaticInfo;
    }

    public static class ResponseStatusBean {
        /**
         * Timestamp : 2019-07-10T16:55:33.636+08:00
         * Ack : Success
         * Errors : []
         * Extension : []
         */

        private String Timestamp;
        private String Ack;
        private List<?> Errors;
        private List<?> Extension;

        public String getTimestamp() {
            return Timestamp;
        }

        public void setTimestamp(String Timestamp) {
            this.Timestamp = Timestamp;
        }

        public String getAck() {
            return Ack;
        }

        public void setAck(String Ack) {
            this.Ack = Ack;
        }

        public List<?> getErrors() {
            return Errors;
        }

        public void setErrors(List<?> Errors) {
            this.Errors = Errors;
        }

        public List<?> getExtension() {
            return Extension;
        }

        public void setExtension(List<?> Extension) {
            this.Extension = Extension;
        }
    }

    public static class HotelStaticInfoBean {
        /**
         * GeoInfo : {"City":{"Code":"28","Name":"成都"},"Area":{"Code":"396","Name":"锦江区"},"PostalCode":"610016","Address":"人民南路二段55号","BusinessDistrict":[{"Code":"189","Name":"天府广场、盐市口商业区"},{"Code":"187","Name":"春熙路、太古里商业区"}],"Coordinates":[{"Provider":"Baidu","LNG":104.07336228847,"LAT":30.654747544611},{"Provider":"Google","LNG":104.0668666,"LAT":30.6486934},{"Provider":"AutoNavi","LNG":104.0668666,"LAT":30.6486934}],"Province":{"Code":"22","Name":"四川"},"Country":{"Code":"1","Name":"中国"}}
         * NearbyPOIs : [{"Coordinates":[{"Provider":"Unknown","LNG":104.06603,"LAT":30.6472}],"Name":"美美力诚百货","Type":"102","Distance":"0.1845463"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06656,"LAT":30.65153}],"Name":"成都百货大楼","Type":"103","Distance":"0.317131"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06638,"LAT":30.64531}],"Name":"锦江宾馆","Type":"201","Distance":"0.3795114"},{"Coordinates":[{"Provider":"Unknown","LNG":104.065895080566,"LAT":30.6574726104736}],"Name":"天府广场","Type":"201","Distance":"0.9817157"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06661,"LAT":30.63798}],"Name":"华西坝","Type":"201","Distance":"1.1928639"},{"Coordinates":[{"Provider":"Unknown","LNG":104.080535888672,"LAT":30.6531791687012}],"Name":"春熙路","Type":"201","Distance":"1.4010704"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06597,"LAT":30.66175}],"Name":"骡马市","Type":"201","Distance":"1.4559877"},{"Coordinates":[{"Provider":"Unknown","LNG":104.057083129883,"LAT":30.6595554351807}],"Name":"人民公园","Type":"201","Distance":"1.5296568"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06677,"LAT":30.62867}],"Name":"省体育馆","Type":"201","Distance":"1.702528"},{"Coordinates":[{"Provider":"Unknown","LNG":104.087104797363,"LAT":30.6482257843018}],"Name":"东门大桥","Type":"201","Distance":"1.9388955"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06179,"LAT":30.63863}],"Name":"四川大学华西医院","Type":"104","Distance":"1.2212156"},{"Coordinates":[{"Provider":"Unknown","LNG":104.0663,"LAT":30.6376}],"Name":"四川大学华西口腔医院","Type":"104","Distance":"1.236104"},{"Coordinates":[{"Provider":"Unknown","LNG":104.06367,"LAT":30.66519}],"Name":"成都市第三人民医院","Type":"104","Distance":"1.8617313"},{"Coordinates":[{"Provider":"Unknown","LNG":104.07785,"LAT":30.62925}],"Name":"四川大学","Type":"101","Distance":"1.9302567"}]
         * TransportationInfos : [{"Coordinates":[{"Provider":"Unknown","LNG":104.076889038086,"LAT":30.6551647186279},{"Provider":"Baidu","LNG":104.083419799805,"LAT":30.6610546112061}],"Name":"春熙路、太古里商业区","Type":"1","Distance":"2.19000005722046","Directions":"驾车约2.19公里(约5.0分钟)","TransportationType":"0","TimeTaken":"5.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.065811157227,"LAT":30.6573905944824},{"Provider":"Baidu","LNG":104.072296142578,"LAT":30.663459777832}],"Name":"天府广场、盐市口商业区","Type":"1","Distance":"1.05999994277954","Directions":"步行约1.06公里(约15.0分钟)","TransportationType":"1","TimeTaken":"15.0"},{"Coordinates":[{"Provider":"Unknown","LNG":103.957841,"LAT":30.57346},{"Provider":"Baidu","LNG":103.964359,"LAT":30.57933}],"Name":"双流国际机场","Type":"2","Distance":"18.9500007629395","Directions":"驾车约18.95公里(约26.0分钟)","TransportationType":"0","TimeTaken":"26.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.0734024,"LAT":30.6967602},{"Provider":"Baidu","LNG":104.0798111,"LAT":30.7028008}],"Name":"成都站","Type":"3","Distance":"5.76999998092651","Directions":"驾车约5.77公里(约25.0分钟)","TransportationType":"0","TimeTaken":"25.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.068618,"LAT":30.605776},{"Provider":"Baidu","LNG":104.0743,"LAT":30.61316}],"Name":"成都南站","Type":"3","Distance":"6.05999994277954","Directions":"驾车约6.06公里(约22.0分钟)","TransportationType":"0","TimeTaken":"22.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.065844,"LAT":30.657423},{"Provider":"Baidu","LNG":104.0723267245,"LAT":30.663491162}],"Name":"市中心","Type":"4","Distance":"1.04999995231628","Directions":"步行约1.05公里(约15.0分钟)","TransportationType":"1","TimeTaken":"15.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.055688,"LAT":30.663081},{"Provider":"Baidu","LNG":104.061617,"LAT":30.669481}],"Name":"宽窄巷子","Type":"5","Distance":"2.50999999046326","Directions":"驾车约2.51公里(约18.0分钟)","TransportationType":"0","TimeTaken":"18.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.076981,"LAT":30.655145},{"Provider":"Baidu","LNG":104.083563,"LAT":30.661093}],"Name":"春熙路","Type":"5","Distance":"2.19000005722046","Directions":"驾车约2.19公里(约9.0分钟)","TransportationType":"0","TimeTaken":"9.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.066053,"LAT":30.650046},{"Provider":"Baidu","LNG":104.0725065,"LAT":30.6562414}],"Name":"锦江宾馆","Type":"7","Distance":"0.409999996423721","Directions":"步行约0.41公里(约6.0分钟)","TransportationType":"1","TimeTaken":"6.0"},{"Coordinates":[{"Provider":"Unknown","LNG":104.066279,"LAT":30.642821},{"Provider":"Baidu","LNG":104.0727431,"LAT":30.6490443}],"Name":"华西坝","Type":"7","Distance":"0.660000026226044","Directions":"步行约0.66公里(约10.0分钟)","TransportationType":"1","TimeTaken":"10.0"}]
         * Brand : {"Pictures":{"Picture":[{"Code":"Small","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"},{"Code":"Mid","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"},{"Code":"Big","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"}],"Category":"logo"},"Code":"153","Name":"岷山饭店"}
         * Group : {"Code":"339","Name":"岷山"}
         * Ratings : [{"Type":"CtripRecommendLevel","Value":5},{"Type":"GuestOverallRating","Value":4.7},{"Type":"CtripUserRating","Value":5}]
         * Policies : [{"Text":"入住时间：14:00以后&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;离店时间：12:00以前","Code":"CheckInCheckOut"},{"Text":"不接受18岁以下客人单独入住。<br/>不接受18岁以下客人在无监护人陪同的情况下入住<br/>加床政策、儿童人数请参见您所选定的客房政策，若超过房型限定人数，可能需收取额外费用。提出的任何要求均需获得酒店的确认，所有服务详情以酒店告知为准。","Code":"Child"},{"Text":"自助早餐&nbsp;RMB&nbsp;88","Code":"Meal"},{"Text":"不可携带宠物。","Code":"Pet"},{"Text":"14:00","Code":"CheckInFrom"},{"Text":"","Code":"CheckInTo"},{"Text":"","Code":"CheckOutFrom"},{"Text":"12:00","Code":"CheckOutTo"},{"Text":"","Code":"CheckInOutContent"},{"Text":"F","Code":"IsLimitCheckInOut"}]
         * NormalizedPolicies : {"ChildAndExtraBedPolicy":{"ExtraBed":{"MaxQuantity":0,"MaxCribQuantity":0},"Descriptions":[{"Text":"加床政策、儿童人数请参考您所选择的客房政策，若超过房型限定人数，可能需要收取额外费用。提出的任何请求均需要获得酒店的确认，所有服务详情以酒店告知为准。","Category":"SpecialTips"},{"Text":"不接受18岁以下客人在无监护人陪同的情况下入住","Category":"ChildStayNotice"}],"AllowChildrenToStay":true,"AllowUseExistingBed":false,"AllowExtraBed":false,"AllowExtraCrib":false,"AllowExtraBedV2":"unknown","AllowExtraCribV2":"unknown"},"MealsPolicy":[{"Amount":[{"Type":"OriginalAmount","Amount":88,"Currency":"RMB"}],"MealType":"3"}]}
         * AcceptedCreditCards : [{"CardType":"6","CardName":"万事达(Master)"},{"CardType":"7","CardName":"威士(VISA)"},{"CardType":"8","CardName":"运通(AMEX)"},{"CardType":"9","CardName":"大来(Diners Club)"},{"CardType":"10","CardName":"JCB"},{"CardType":"14","CardName":"国内发行银联卡"}]
         * ImportantNotices : []
         * Facilities : [{"FacilityItem":[{"ID":"1","Name":"中餐厅","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"2","Name":"西餐厅","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"3","Name":"咖啡厅","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"5","Name":"酒吧","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"6","Name":"会议厅","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"8","Name":"商务中心","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"11","Name":"外币兑换服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"13","Name":"国内长途电话","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"14","Name":"国际长途电话","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"15","Name":"洗衣服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"16","Name":"送餐服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"19","Name":"残疾人客房","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"24","Name":"棋牌室","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"41","Name":"理发美容中心","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"42","Name":"健身室","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"55","Name":"叫车服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"60","Name":"穿梭机场班车","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"61","Name":"室外游泳池","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"62","Name":"夜总会","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"63","Name":"茶室","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"67","Name":"烧烤","Status":"1"}],"CategoryName":"活动设施"},{"FacilityItem":[{"ID":"69","Name":"邮政服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"75","Name":"拖鞋","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"76","Name":"雨伞","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"77","Name":"书桌","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"78","Name":"浴室化妆放大镜","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"79","Name":"24小时热水","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"80","Name":"电热水壶","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"81","Name":"咖啡壶/茶壶","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"82","Name":"免费洗漱用品(6样以上)","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"83","Name":"免费瓶装水","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"85","Name":"迷你吧","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"86","Name":"熨衣设备","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"87","Name":"小冰箱","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"88","Name":"浴衣","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"89","Name":"多种规格电源插座","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"90","Name":"110V电压插座","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"91","Name":"浴缸","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"93","Name":"吹风机","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"94","Name":"房内保险箱","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"95","Name":"前台贵重物品保险柜","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"96","Name":"专职行李员","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"97","Name":"行李寄存","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"98","Name":"叫醒服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"99","Name":"自动取款机","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"100","Name":"免费停车场","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"103","Name":"免费旅游交通图(可赠送)","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"105","Name":"接机服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"106","Name":"大堂吧","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"107","Name":"空调","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"110","Name":"电梯","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"120","Name":"有可无线上网的公共区域 免费","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"123","Name":"租车服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"127","Name":"礼宾服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"131","Name":"办理私人登记入住/退房手续","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"137","Name":"婚宴服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"138","Name":"多国语言工作人员","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"139","Name":"管家服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"140","Name":"24小时前台服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"145","Name":"大堂免费报纸","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"160","Name":"专职门童","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"162","Name":"信用卡结算服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"163","Name":"一次性账单结算服务","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"164","Name":"多功能厅","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"165","Name":"24小时大堂经理","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"167","Name":"旅游票务专柜","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"170","Name":"公共音响系统","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"171","Name":"非经营性客人休息区","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"173","Name":"无烟楼层","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"174","Name":"行政楼层","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"176","Name":"多媒体演示系统","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"177","Name":"公共区域闭路电视监控系统","Status":"1"}],"CategoryName":"通用设施"},{"FacilityItem":[{"ID":"180","Name":"有线频道","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"182","Name":"液晶电视机","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"212","Name":"闹钟","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"213","Name":"针线包","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"215","Name":"220V电压插座","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"217","Name":"遮光窗帘","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"219","Name":"手动窗帘","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"223","Name":"备用床具","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"224","Name":"床具:鸭绒被","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"230","Name":"语音留言","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"231","Name":"欢迎礼品","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"252","Name":"洗浴间电话","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"253","Name":"沙发","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"256","Name":"免费报纸","Status":"2"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"258","Name":"电子秤","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"259","Name":"房间内高速上网","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"266","Name":"客房WIFI覆盖免费","Status":"1"}],"CategoryName":"客房设施和服务"},{"FacilityItem":[{"ID":"331","Name":"儿童拖鞋","Status":"1"}],"CategoryName":"服务项目"},{"FacilityItem":[{"ID":"333","Name":"儿童牙刷","Status":"1"}],"CategoryName":"服务项目"}]
         * Pictures : [{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/5ff3ccf821be4c6fbab48a9e3c5739a7_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/78ee26c5f9334652b0f652daf69edc97_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g4/M07/32/BB/CggYHlYD9WKAFeGMAAQFmSxsDKE225_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//200r070000002r07725CF_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//200n070000002r07e1627_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//20030w000000kcexw5C7D_R_550_412.jpg"},{"Type":"1","Caption":"外观","URL":"http://dimg04.c-ctrip.com/images//200q0w000000kbz4104AA_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/c1472eccee6949988ba10be019619088_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/db42c62939474ed6b28e950f08eb2787_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g4/M04/32/BB/CggYHlYD9WKAf-hyAALk41244VM067_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g4/M0A/32/7B/CggYHFYD9WKAbYARAAIlF4rj4O0308_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//2005080000003366v66FE_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//200c070000002r07p2E48_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//200w070000002r07d4DDB_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g5/M00/63/EE/CggYsVcghjCAOzYJAAFAqFHSdPU320_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g5/M08/62/AA/CggYsFcgiHKASZgZAAGE3SCbbTM065_R_550_412.jpg"},{"Type":"2","Caption":"公共区域","URL":"http://dimg04.c-ctrip.com/images//200p070000002r07h4FB2_R_550_412.jpg"},{"Type":"3","Caption":"健身娱乐设施","URL":"http://dimg04.c-ctrip.com/images//200n0w000000k9qnp0676_R_550_412.jpg"},{"Type":"3","Caption":"健身娱乐设施","URL":"http://dimg04.c-ctrip.com/images//20060w000000kbavv0622_R_550_412.jpg"},{"Type":"3","Caption":"健身娱乐设施","URL":"http://dimg04.c-ctrip.com/images//200a0w000000kc01w29A9_R_550_412.jpg"},{"Type":"3","Caption":"健身娱乐设施","URL":"http://dimg04.c-ctrip.com/images//200e0o000000eoji792D8_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/f2448054f8d94fedbda035f1eeded99e_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//fd/hotel/g5/M05/20/8F/CggYsVb0jeqADvrWAARmiias7jo737_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//200s070000002r07a9D9F_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//200b0b0000005zlp4089C_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//2005070000002r07g9320_R_550_412.jpg"},{"Type":"5","Caption":"其他","URL":"http://dimg04.c-ctrip.com/images//200w0b0000005zliaDCB0_R_550_412.jpg"},{"Type":"1","Caption":"Logo","URL":"http://dimg04.c-ctrip.com/images//hotel/3000/2628/5ff3ccf821be4c6fbab48a9e3c5739a7_R_550_412.jpg"}]
         * Descriptions : [{"Text":"四川岷山饭店座落于成都市市中区，毗邻繁华商业区，商务休闲购物方便快捷。近在咫尺的机场专线，在酒店附近穿梭市内观光巴士，不仅让您的商旅之行方便快捷，更使您与市区的繁荣同在。","Category":"1"},{"Text":"四川岷山饭店位于成都市的繁华中心地带\u2014\u2014人民南路二段，饭店紧邻机场巴士1号线始发站，地铁1号线，更有武侯祠、锦里、宽窄巷子、天府广场、春熙路、太古里约10分钟繁华商圈环抱。<br>岷山饭店隶属于四川岷山集团，整体设计风格富含巴蜀文化元素并与国际视野融合有致，设计型、风格化展现淋漓。是四川省首届及历届国家一类会议\u2014\u2014\u201c西博会\u201d的指定接待酒店，是大多数国内和国际世界500强企业的指定供应商，是各类外事、政务、商务接待的不二之选。位于酒店21楼的the river house西餐厅，连续两年上榜大众点评黑珍珠餐厅，已是成都必打卡西餐厅。","Category":"2"},{"Text":"","Category":"3"}]
         * Themes : [{"Code":"19","Name":"休闲度假"},{"Code":"21","Name":"设计师酒店"},{"Code":"119","Name":"浪漫情侣"},{"Code":"121","Name":"亲子酒店"},{"Code":"177","Name":"会议酒店"},{"Code":"190","Name":"商务出行"},{"Code":"219","Name":"特价频道"}]
         * ContactInfo : {"Telephone":"028-85583333","Fax":""}
         * ArrivalTimeLimitInfo : {"EarliestTime":"14:00","LatestTime":"","IsMustBe":"F"}
         * DepartureTimeLimitInfo : {"EarliestTime":"","LatestTime":"12:00"}
         * ApplicabilityInfo : {"HotelApplicability":"5"}
         * HotelID : 391650
         * HotelName : 四川岷山饭店
         * StarRating : 5
         * IsOfficialRating : true
         * OpenYear : 1988-01-01
         * RenovationYear : 2018-04-01
         * RoomQuantity : 305
         * IsOnlineSignUp : false
         * MaintainerCode : 1
         * Bookable : T
         */

        private GeoInfoBean GeoInfo;
        private BrandBean Brand;
        private GroupBean Group;
        private NormalizedPoliciesBean NormalizedPolicies;
        private ContactInfoBean ContactInfo;
        private ArrivalTimeLimitInfoBean ArrivalTimeLimitInfo;
        private DepartureTimeLimitInfoBean DepartureTimeLimitInfo;
        private ApplicabilityInfoBean ApplicabilityInfo;
        private int HotelID;
        private String HotelName;
        private int StarRating;
        private boolean IsOfficialRating;
        private String OpenYear;
        private String RenovationYear;
        private long RoomQuantity;
        private boolean IsOnlineSignUp;
        private String MaintainerCode;
        private boolean Bookable;
        private List<NearbyPOIsBean> NearbyPOIs;
        private List<TransportationInfosBean> TransportationInfos;
        private List<RatingsBean> Ratings;
        private List<PoliciesBean> Policies;
        private List<AcceptedCreditCardsBean> AcceptedCreditCards;
        private List<?> ImportantNotices;
        private List<FacilitiesBean> Facilities;
        private List<PicturesBeanX> Pictures;
        private List<DescriptionsBeanX> Descriptions;
        private List<ThemesBean> Themes;

        public GeoInfoBean getGeoInfo() {
            return GeoInfo;
        }

        public void setGeoInfo(GeoInfoBean GeoInfo) {
            this.GeoInfo = GeoInfo;
        }

        public BrandBean getBrand() {
            return Brand;
        }

        public void setBrand(BrandBean Brand) {
            this.Brand = Brand;
        }

        public GroupBean getGroup() {
            return Group;
        }

        public void setGroup(GroupBean Group) {
            this.Group = Group;
        }

        public NormalizedPoliciesBean getNormalizedPolicies() {
            return NormalizedPolicies;
        }

        public void setNormalizedPolicies(NormalizedPoliciesBean NormalizedPolicies) {
            this.NormalizedPolicies = NormalizedPolicies;
        }

        public ContactInfoBean getContactInfo() {
            return ContactInfo;
        }

        public void setContactInfo(ContactInfoBean ContactInfo) {
            this.ContactInfo = ContactInfo;
        }

        public ArrivalTimeLimitInfoBean getArrivalTimeLimitInfo() {
            return ArrivalTimeLimitInfo;
        }

        public void setArrivalTimeLimitInfo(ArrivalTimeLimitInfoBean ArrivalTimeLimitInfo) {
            this.ArrivalTimeLimitInfo = ArrivalTimeLimitInfo;
        }

        public DepartureTimeLimitInfoBean getDepartureTimeLimitInfo() {
            return DepartureTimeLimitInfo;
        }

        public void setDepartureTimeLimitInfo(DepartureTimeLimitInfoBean DepartureTimeLimitInfo) {
            this.DepartureTimeLimitInfo = DepartureTimeLimitInfo;
        }

        public ApplicabilityInfoBean getApplicabilityInfo() {
            return ApplicabilityInfo;
        }

        public void setApplicabilityInfo(ApplicabilityInfoBean ApplicabilityInfo) {
            this.ApplicabilityInfo = ApplicabilityInfo;
        }

        public int getHotelID() {
            return HotelID;
        }

        public void setHotelID(int HotelID) {
            this.HotelID = HotelID;
        }

        public String getHotelName() {
            return HotelName;
        }

        public void setHotelName(String HotelName) {
            this.HotelName = HotelName;
        }

        public int getStarRating() {
            return StarRating;
        }

        public void setStarRating(int StarRating) {
            this.StarRating = StarRating;
        }

        public boolean isIsOfficialRating() {
            return IsOfficialRating;
        }

        public void setIsOfficialRating(boolean IsOfficialRating) {
            this.IsOfficialRating = IsOfficialRating;
        }

        public String getOpenYear() {
            return OpenYear;
        }

        public void setOpenYear(String OpenYear) {
            this.OpenYear = OpenYear;
        }

        public String getRenovationYear() {
            return RenovationYear;
        }

        public void setRenovationYear(String RenovationYear) {
            this.RenovationYear = RenovationYear;
        }

        public long getRoomQuantity() {
            return RoomQuantity;
        }

        public void setRoomQuantity(long RoomQuantity) {
            this.RoomQuantity = RoomQuantity;
        }

        public boolean isIsOnlineSignUp() {
            return IsOnlineSignUp;
        }

        public void setIsOnlineSignUp(boolean IsOnlineSignUp) {
            this.IsOnlineSignUp = IsOnlineSignUp;
        }

        public String getMaintainerCode() {
            return MaintainerCode;
        }

        public void setMaintainerCode(String MaintainerCode) {
            this.MaintainerCode = MaintainerCode;
        }

        public boolean getBookable() {
            return Bookable;
        }

        public void setBookable(boolean Bookable) {
            this.Bookable = Bookable;
        }

        public List<NearbyPOIsBean> getNearbyPOIs() {
            return NearbyPOIs;
        }

        public void setNearbyPOIs(List<NearbyPOIsBean> NearbyPOIs) {
            this.NearbyPOIs = NearbyPOIs;
        }

        public List<TransportationInfosBean> getTransportationInfos() {
            return TransportationInfos;
        }

        public void setTransportationInfos(List<TransportationInfosBean> TransportationInfos) {
            this.TransportationInfos = TransportationInfos;
        }

        public List<RatingsBean> getRatings() {
            return Ratings;
        }

        public void setRatings(List<RatingsBean> Ratings) {
            this.Ratings = Ratings;
        }

        public List<PoliciesBean> getPolicies() {
            return Policies;
        }

        public void setPolicies(List<PoliciesBean> Policies) {
            this.Policies = Policies;
        }

        public List<AcceptedCreditCardsBean> getAcceptedCreditCards() {
            return AcceptedCreditCards;
        }

        public void setAcceptedCreditCards(List<AcceptedCreditCardsBean> AcceptedCreditCards) {
            this.AcceptedCreditCards = AcceptedCreditCards;
        }

        public List<?> getImportantNotices() {
            return ImportantNotices;
        }

        public void setImportantNotices(List<?> ImportantNotices) {
            this.ImportantNotices = ImportantNotices;
        }

        public List<FacilitiesBean> getFacilities() {
            return Facilities;
        }

        public void setFacilities(List<FacilitiesBean> Facilities) {
            this.Facilities = Facilities;
        }

        public List<PicturesBeanX> getPictures() {
            return Pictures;
        }

        public void setPictures(List<PicturesBeanX> Pictures) {
            this.Pictures = Pictures;
        }

        public List<DescriptionsBeanX> getDescriptions() {
            return Descriptions;
        }

        public void setDescriptions(List<DescriptionsBeanX> Descriptions) {
            this.Descriptions = Descriptions;
        }

        public List<ThemesBean> getThemes() {
            return Themes;
        }

        public void setThemes(List<ThemesBean> Themes) {
            this.Themes = Themes;
        }

        public static class GeoInfoBean {
            /**
             * City : {"Code":"28","Name":"成都"}
             * Area : {"Code":"396","Name":"锦江区"}
             * PostalCode : 610016
             * Address : 人民南路二段55号
             * BusinessDistrict : [{"Code":"189","Name":"天府广场、盐市口商业区"},{"Code":"187","Name":"春熙路、太古里商业区"}]
             * Coordinates : [{"Provider":"Baidu","LNG":104.07336228847,"LAT":30.654747544611},{"Provider":"Google","LNG":104.0668666,"LAT":30.6486934},{"Provider":"AutoNavi","LNG":104.0668666,"LAT":30.6486934}]
             * Province : {"Code":"22","Name":"四川"}
             * Country : {"Code":"1","Name":"中国"}
             */

            private CityBean City;
            private AreaBean Area;
            private String PostalCode;
            private String Address;
            private ProvinceBean Province;
            private CountryBean Country;
            private List<BusinessDistrictBean> BusinessDistrict;
            private List<CoordinatesBean> Coordinates;

            public CityBean getCity() {
                return City;
            }

            public void setCity(CityBean City) {
                this.City = City;
            }

            public AreaBean getArea() {
                return Area;
            }

            public void setArea(AreaBean Area) {
                this.Area = Area;
            }

            public String getPostalCode() {
                return PostalCode;
            }

            public void setPostalCode(String PostalCode) {
                this.PostalCode = PostalCode;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }

            public ProvinceBean getProvince() {
                return Province;
            }

            public void setProvince(ProvinceBean Province) {
                this.Province = Province;
            }

            public CountryBean getCountry() {
                return Country;
            }

            public void setCountry(CountryBean Country) {
                this.Country = Country;
            }

            public List<BusinessDistrictBean> getBusinessDistrict() {
                return BusinessDistrict;
            }

            public void setBusinessDistrict(List<BusinessDistrictBean> BusinessDistrict) {
                this.BusinessDistrict = BusinessDistrict;
            }

            public List<CoordinatesBean> getCoordinates() {
                return Coordinates;
            }

            public void setCoordinates(List<CoordinatesBean> Coordinates) {
                this.Coordinates = Coordinates;
            }

            public static class CityBean {
                /**
                 * Code : 28
                 * Name : 成都
                 */

                private long Code;
                private String Name;

                public long getCode() {
                    return Code;
                }

                public void setCode(long Code) {
                    this.Code = Code;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }
            }

            public static class AreaBean {
                /**
                 * Code : 396
                 * Name : 锦江区
                 */

                private long Code;
                private String Name;

                public long getCode() {
                    return Code;
                }

                public void setCode(long Code) {
                    this.Code = Code;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }
            }

            public static class ProvinceBean {
                /**
                 * Code : 22
                 * Name : 四川
                 */

                private String Code;
                private String Name;

                public String getCode() {
                    return Code;
                }

                public void setCode(String Code) {
                    this.Code = Code;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }
            }

            public static class CountryBean {
                /**
                 * Code : 1
                 * Name : 中国
                 */

                private String Code;
                private String Name;

                public String getCode() {
                    return Code;
                }

                public void setCode(String Code) {
                    this.Code = Code;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }
            }

            public static class BusinessDistrictBean {
                /**
                 * Code : 189
                 * Name : 天府广场、盐市口商业区
                 */

                private String Code;
                private String Name;

                public String getCode() {
                    return Code;
                }

                public void setCode(String Code) {
                    this.Code = Code;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }
            }

            public static class CoordinatesBean {
                /**
                 * Provider : Baidu
                 * LNG : 104.07336228847
                 * LAT : 30.654747544611
                 */

                private String Provider;
                private double LNG;
                private double LAT;

                public String getProvider() {
                    return Provider;
                }

                public void setProvider(String Provider) {
                    this.Provider = Provider;
                }

                public double getLNG() {
                    return LNG;
                }

                public void setLNG(double LNG) {
                    this.LNG = LNG;
                }

                public double getLAT() {
                    return LAT;
                }

                public void setLAT(double LAT) {
                    this.LAT = LAT;
                }
            }
        }

        public static class BrandBean {
            /**
             * Pictures : {"Picture":[{"Code":"Small","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"},{"Code":"Mid","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"},{"Code":"Big","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"}],"Category":"logo"}
             * Code : 153
             * Name : 岷山饭店
             */

            private PicturesBean Pictures;
            private String Code;
            private String Name;

            public PicturesBean getPictures() {
                return Pictures;
            }

            public void setPictures(PicturesBean Pictures) {
                this.Pictures = Pictures;
            }

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public static class PicturesBean {
                /**
                 * Picture : [{"Code":"Small","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"},{"Code":"Mid","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"},{"Code":"Big","URL":"http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg"}]
                 * Category : logo
                 */

                private String Category;
                private List<PictureBean> Picture;

                public String getCategory() {
                    return Category;
                }

                public void setCategory(String Category) {
                    this.Category = Category;
                }

                public List<PictureBean> getPicture() {
                    return Picture;
                }

                public void setPicture(List<PictureBean> Picture) {
                    this.Picture = Picture;
                }

                public static class PictureBean {
                    /**
                     * Code : Small
                     * URL : http://dimg04.c-ctrip.com/images//2k0g0x000000lgys6FE08_R_550_412.jpg
                     */

                    private String Code;
                    private String URL;

                    public String getCode() {
                        return Code;
                    }

                    public void setCode(String Code) {
                        this.Code = Code;
                    }

                    public String getURL() {
                        return URL;
                    }

                    public void setURL(String URL) {
                        this.URL = URL;
                    }
                }
            }
        }

        public static class GroupBean {
            /**
             * Code : 339
             * Name : 岷山
             */

            private String Code;
            private String Name;

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }

        public static class NormalizedPoliciesBean {
            /**
             * ChildAndExtraBedPolicy : {"ExtraBed":{"MaxQuantity":0,"MaxCribQuantity":0},"Descriptions":[{"Text":"加床政策、儿童人数请参考您所选择的客房政策，若超过房型限定人数，可能需要收取额外费用。提出的任何请求均需要获得酒店的确认，所有服务详情以酒店告知为准。","Category":"SpecialTips"},{"Text":"不接受18岁以下客人在无监护人陪同的情况下入住","Category":"ChildStayNotice"}],"AllowChildrenToStay":true,"AllowUseExistingBed":false,"AllowExtraBed":false,"AllowExtraCrib":false,"AllowExtraBedV2":"unknown","AllowExtraCribV2":"unknown"}
             * MealsPolicy : [{"Amount":[{"Type":"OriginalAmount","Amount":88,"Currency":"RMB"}],"MealType":"3"}]
             */

            private ChildAndExtraBedPolicyBean ChildAndExtraBedPolicy;
            private List<MealsPolicyBean> MealsPolicy;

            public ChildAndExtraBedPolicyBean getChildAndExtraBedPolicy() {
                return ChildAndExtraBedPolicy;
            }

            public void setChildAndExtraBedPolicy(ChildAndExtraBedPolicyBean ChildAndExtraBedPolicy) {
                this.ChildAndExtraBedPolicy = ChildAndExtraBedPolicy;
            }

            public List<MealsPolicyBean> getMealsPolicy() {
                return MealsPolicy;
            }

            public void setMealsPolicy(List<MealsPolicyBean> MealsPolicy) {
                this.MealsPolicy = MealsPolicy;
            }

            public static class ChildAndExtraBedPolicyBean {
                /**
                 * ExtraBed : {"MaxQuantity":0,"MaxCribQuantity":0}
                 * Descriptions : [{"Text":"加床政策、儿童人数请参考您所选择的客房政策，若超过房型限定人数，可能需要收取额外费用。提出的任何请求均需要获得酒店的确认，所有服务详情以酒店告知为准。","Category":"SpecialTips"},{"Text":"不接受18岁以下客人在无监护人陪同的情况下入住","Category":"ChildStayNotice"}]
                 * AllowChildrenToStay : true
                 * AllowUseExistingBed : false
                 * AllowExtraBed : false
                 * AllowExtraCrib : false
                 * AllowExtraBedV2 : unknown
                 * AllowExtraCribV2 : unknown
                 */

                private ExtraBedBean ExtraBed;
                private boolean AllowChildrenToStay;
                private boolean AllowUseExistingBed;
                private boolean AllowExtraBed;
                private boolean AllowExtraCrib;
                private String AllowExtraBedV2;
                private String AllowExtraCribV2;
                private List<DescriptionsBean> Descriptions;

                public ExtraBedBean getExtraBed() {
                    return ExtraBed;
                }

                public void setExtraBed(ExtraBedBean ExtraBed) {
                    this.ExtraBed = ExtraBed;
                }

                public boolean isAllowChildrenToStay() {
                    return AllowChildrenToStay;
                }

                public void setAllowChildrenToStay(boolean AllowChildrenToStay) {
                    this.AllowChildrenToStay = AllowChildrenToStay;
                }

                public boolean isAllowUseExistingBed() {
                    return AllowUseExistingBed;
                }

                public void setAllowUseExistingBed(boolean AllowUseExistingBed) {
                    this.AllowUseExistingBed = AllowUseExistingBed;
                }

                public boolean isAllowExtraBed() {
                    return AllowExtraBed;
                }

                public void setAllowExtraBed(boolean AllowExtraBed) {
                    this.AllowExtraBed = AllowExtraBed;
                }

                public boolean isAllowExtraCrib() {
                    return AllowExtraCrib;
                }

                public void setAllowExtraCrib(boolean AllowExtraCrib) {
                    this.AllowExtraCrib = AllowExtraCrib;
                }

                public String getAllowExtraBedV2() {
                    return AllowExtraBedV2;
                }

                public void setAllowExtraBedV2(String AllowExtraBedV2) {
                    this.AllowExtraBedV2 = AllowExtraBedV2;
                }

                public String getAllowExtraCribV2() {
                    return AllowExtraCribV2;
                }

                public void setAllowExtraCribV2(String AllowExtraCribV2) {
                    this.AllowExtraCribV2 = AllowExtraCribV2;
                }

                public List<DescriptionsBean> getDescriptions() {
                    return Descriptions;
                }

                public void setDescriptions(List<DescriptionsBean> Descriptions) {
                    this.Descriptions = Descriptions;
                }

                public static class ExtraBedBean {
                    /**
                     * MaxQuantity : 0
                     * MaxCribQuantity : 0
                     */

                    private int MaxQuantity;
                    private int MaxCribQuantity;

                    public int getMaxQuantity() {
                        return MaxQuantity;
                    }

                    public void setMaxQuantity(int MaxQuantity) {
                        this.MaxQuantity = MaxQuantity;
                    }

                    public int getMaxCribQuantity() {
                        return MaxCribQuantity;
                    }

                    public void setMaxCribQuantity(int MaxCribQuantity) {
                        this.MaxCribQuantity = MaxCribQuantity;
                    }
                }

                public static class DescriptionsBean {
                    /**
                     * Text : 加床政策、儿童人数请参考您所选择的客房政策，若超过房型限定人数，可能需要收取额外费用。提出的任何请求均需要获得酒店的确认，所有服务详情以酒店告知为准。
                     * Category : SpecialTips
                     */

                    private String Text;
                    private String Category;

                    public String getText() {
                        return Text;
                    }

                    public void setText(String Text) {
                        this.Text = Text;
                    }

                    public String getCategory() {
                        return Category;
                    }

                    public void setCategory(String Category) {
                        this.Category = Category;
                    }
                }
            }

            public static class MealsPolicyBean {
                /**
                 * Amount : [{"Type":"OriginalAmount","Amount":88,"Currency":"RMB"}]
                 * MealType : 3
                 */

                private String MealType;
                private List<Amount> Amount;

                public String getMealType() {
                    return MealType;
                }

                public void setMealType(String MealType) {
                    this.MealType = MealType;
                }

                public List<Amount> getAmount() {
                    return Amount;
                }

                public void setAmount(List<Amount> Amount) {
                    this.Amount = Amount;
                }

                public static class Amount {
                    /**
                     * Type : OriginalAmount
                     * Amount : 88.0
                     * Currency : RMB
                     */

                    private String Type;
                    private double Amount;
                    private String Currency;

                    public String getType() {
                        return Type;
                    }

                    public void setType(String Type) {
                        this.Type = Type;
                    }

                    public double getAmount() {
                        return Amount;
                    }

                    public void setAmount(double Amount) {
                        this.Amount = Amount;
                    }

                    public String getCurrency() {
                        return Currency;
                    }

                    public void setCurrency(String Currency) {
                        this.Currency = Currency;
                    }
                }
            }
        }

        public static class ContactInfoBean {
            /**
             * Telephone : 028-85583333
             * Fax :
             */

            private String Telephone;
            private String Fax;

            public String getTelephone() {
                return Telephone;
            }

            public void setTelephone(String Telephone) {
                this.Telephone = Telephone;
            }

            public String getFax() {
                return Fax;
            }

            public void setFax(String Fax) {
                this.Fax = Fax;
            }
        }

        public static class ArrivalTimeLimitInfoBean {
            /**
             * EarliestTime : 14:00
             * LatestTime :
             * IsMustBe : F
             */

            private String EarliestTime;
            private String LatestTime;
            private String IsMustBe;

            public String getEarliestTime() {
                return EarliestTime;
            }

            public void setEarliestTime(String EarliestTime) {
                this.EarliestTime = EarliestTime;
            }

            public String getLatestTime() {
                return LatestTime;
            }

            public void setLatestTime(String LatestTime) {
                this.LatestTime = LatestTime;
            }

            public String getIsMustBe() {
                return IsMustBe;
            }

            public void setIsMustBe(String IsMustBe) {
                this.IsMustBe = IsMustBe;
            }
        }

        public static class DepartureTimeLimitInfoBean {
            /**
             * EarliestTime :
             * LatestTime : 12:00
             */

            private String EarliestTime;
            private String LatestTime;

            public String getEarliestTime() {
                return EarliestTime;
            }

            public void setEarliestTime(String EarliestTime) {
                this.EarliestTime = EarliestTime;
            }

            public String getLatestTime() {
                return LatestTime;
            }

            public void setLatestTime(String LatestTime) {
                this.LatestTime = LatestTime;
            }
        }

        public static class ApplicabilityInfoBean {
            /**
             * HotelApplicability : 5
             */

            private String HotelApplicability;

            public String getHotelApplicability() {
                return HotelApplicability;
            }

            public void setHotelApplicability(String HotelApplicability) {
                this.HotelApplicability = HotelApplicability;
            }
        }

        public static class NearbyPOIsBean {
            /**
             * Coordinates : [{"Provider":"Unknown","LNG":104.06603,"LAT":30.6472}]
             * Name : 美美力诚百货
             * Type : 102
             * Distance : 0.1845463
             */

            private String Name;
            private String Type;
            private String Distance;
            private List<CoordinatesBeanX> Coordinates;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getType() {
                return Type;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String Distance) {
                this.Distance = Distance;
            }

            public List<CoordinatesBeanX> getCoordinates() {
                return Coordinates;
            }

            public void setCoordinates(List<CoordinatesBeanX> Coordinates) {
                this.Coordinates = Coordinates;
            }

            public static class CoordinatesBeanX {
                /**
                 * Provider : Unknown
                 * LNG : 104.06603
                 * LAT : 30.6472
                 */

                private String Provider;
                private double LNG;
                private double LAT;

                public String getProvider() {
                    return Provider;
                }

                public void setProvider(String Provider) {
                    this.Provider = Provider;
                }

                public double getLNG() {
                    return LNG;
                }

                public void setLNG(double LNG) {
                    this.LNG = LNG;
                }

                public double getLAT() {
                    return LAT;
                }

                public void setLAT(double LAT) {
                    this.LAT = LAT;
                }
            }
        }

        public static class TransportationInfosBean {
            /**
             * Coordinates : [{"Provider":"Unknown","LNG":104.076889038086,"LAT":30.6551647186279},{"Provider":"Baidu","LNG":104.083419799805,"LAT":30.6610546112061}]
             * Name : 春熙路、太古里商业区
             * Type : 1
             * Distance : 2.19000005722046
             * Directions : 驾车约2.19公里(约5.0分钟)
             * TransportationType : 0
             * TimeTaken : 5.0
             */

            private String Name;
            private String Type;
            private String Distance;
            private String Directions;
            private String TransportationType;
            private String TimeTaken;
            private List<CoordinatesBeanXX> Coordinates;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getType() {
                return Type;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String Distance) {
                this.Distance = Distance;
            }

            public String getDirections() {
                return Directions;
            }

            public void setDirections(String Directions) {
                this.Directions = Directions;
            }

            public String getTransportationType() {
                return TransportationType;
            }

            public void setTransportationType(String TransportationType) {
                this.TransportationType = TransportationType;
            }

            public String getTimeTaken() {
                return TimeTaken;
            }

            public void setTimeTaken(String TimeTaken) {
                this.TimeTaken = TimeTaken;
            }

            public List<CoordinatesBeanXX> getCoordinates() {
                return Coordinates;
            }

            public void setCoordinates(List<CoordinatesBeanXX> Coordinates) {
                this.Coordinates = Coordinates;
            }

            public static class CoordinatesBeanXX {
                /**
                 * Provider : Unknown
                 * LNG : 104.076889038086
                 * LAT : 30.6551647186279
                 */

                private String Provider;
                private double LNG;
                private double LAT;

                public String getProvider() {
                    return Provider;
                }

                public void setProvider(String Provider) {
                    this.Provider = Provider;
                }

                public double getLNG() {
                    return LNG;
                }

                public void setLNG(double LNG) {
                    this.LNG = LNG;
                }

                public double getLAT() {
                    return LAT;
                }

                public void setLAT(double LAT) {
                    this.LAT = LAT;
                }
            }
        }

        public static class RatingsBean {
            /**
             * Type : CtripRecommendLevel
             * Value : 5
             */

            private String Type;
            private int Value;

            public String getType() {
                return Type;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }
        }

        public static class PoliciesBean {
            /**
             * Text : 入住时间：14:00以后&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;离店时间：12:00以前
             * Code : CheckInCheckOut
             */

            private String Text;
            private String Code;

            public String getText() {
                return Text;
            }

            public void setText(String Text) {
                this.Text = Text;
            }

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }
        }

        public static class AcceptedCreditCardsBean {
            /**
             * CardType : 6
             * CardName : 万事达(Master)
             */

            private String CardType;
            private String CardName;

            public String getCardType() {
                return CardType;
            }

            public void setCardType(String CardType) {
                this.CardType = CardType;
            }

            public String getCardName() {
                return CardName;
            }

            public void setCardName(String CardName) {
                this.CardName = CardName;
            }
        }

        public static class FacilitiesBean {
            /**
             * FacilityItem : [{"ID":"1","Name":"中餐厅","Status":"1"}]
             * CategoryName : 通用设施
             */

            private String CategoryName;
            private List<FacilityItemBean> FacilityItem;

            public String getCategoryName() {
                return CategoryName;
            }

            public void setCategoryName(String CategoryName) {
                this.CategoryName = CategoryName;
            }

            public List<FacilityItemBean> getFacilityItem() {
                return FacilityItem;
            }

            public void setFacilityItem(List<FacilityItemBean> FacilityItem) {
                this.FacilityItem = FacilityItem;
            }

            public static class FacilityItemBean {
                /**
                 * ID : 1
                 * Name : 中餐厅
                 * Status : 1
                 */

                private String ID;
                private String Name;
                private String Status;

                public String getID() {
                    return ID;
                }

                public void setID(String ID) {
                    this.ID = ID;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getStatus() {
                    return Status;
                }

                public void setStatus(String Status) {
                    this.Status = Status;
                }
            }
        }

        public static class PicturesBeanX {
            /**
             * Type : 1
             * Caption : 外观
             * URL : http://dimg04.c-ctrip.com/images//hotel/3000/2628/5ff3ccf821be4c6fbab48a9e3c5739a7_R_550_412.jpg
             */

            private String Type;
            private String Caption;
            private String URL;

            public String getType() {
                return Type;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public String getCaption() {
                return Caption;
            }

            public void setCaption(String Caption) {
                this.Caption = Caption;
            }

            public String getURL() {
                return URL;
            }

            public void setURL(String URL) {
                this.URL = URL;
            }
        }

        public static class DescriptionsBeanX {
            /**
             * Text : 四川岷山饭店座落于成都市市中区，毗邻繁华商业区，商务休闲购物方便快捷。近在咫尺的机场专线，在酒店附近穿梭市内观光巴士，不仅让您的商旅之行方便快捷，更使您与市区的繁荣同在。
             * Category : 1
             */

            private String Text;
            private String Category;

            public String getText() {
                return Text;
            }

            public void setText(String Text) {
                this.Text = Text;
            }

            public String getCategory() {
                return Category;
            }

            public void setCategory(String Category) {
                this.Category = Category;
            }
        }

        public static class ThemesBean {
            /**
             * Code : 19
             * Name : 休闲度假
             */

            private String Code;
            private String Name;

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }
    }
}