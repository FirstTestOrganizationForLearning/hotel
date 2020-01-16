package com.fntx.common.utils;

/**
 * 接口签名校验工具类
 *
 * @Copyright (C), 2019, 琥珀丶时光
 * @FileName: SignCheckUtil
 * @Author: 王俊文
 * @Date: 19-6-4 上午10:31
 * @Description: 接口签名校验工具类
 */
public class SignCheckUtil
{
    /*
    签名规则：
    SIGN=MD5(CID+MODULEID+TIMESTAMP+REQUESTTYPE+SECURYKEY)

    CID	String	Y	渠道ID，平台分配的唯一凭证
    MODULEID	String	Y	渠道的业务模块（机票，酒店，火车票）
    TIMESTAMP	String	Y	时间戳，当前时间与协调世界时 1970 年 1 月 1 日午夜之间的时间差（以毫秒为单位测量）
    SECURYKEY	String		平台给应用分配的安全码
    REQUESTTYPE	String	Y	API接口名称
    SIGN	String	Y	API输入参数签名结果
     */

    /**
     * @Description: 签名生成
     * @Author: 王俊文
     * @Date: 19-6-4 上午10:50
     * @Param: [cid 渠道ID, moduleid 渠道的业务模块, timestamp 时间戳, requesttype API接口名称, securykey 平台给应用分配的安全码]
     * @returns: java.lang.String
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-6-4 上午10:50          1.0            签名生成
     */
    public static String signGenerate(String cid, String moduleid, String timestamp,
                                      String requesttype, String securykey)
    {
        String sign = cid + moduleid + timestamp + requesttype + securykey;
        return MD5Util.encrypt(cid + moduleid + timestamp + requesttype + securykey);
    }

    /**
     * @Description: 签名生成
     * @Author: 王俊文
     * @Date: 19-6-4 上午10:50
     * @Param: [cid 渠道ID, timestamp 时间戳, requesttype API接口名称, securykey 平台给应用分配的安全码]
     * @returns: java.lang.String
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-6-4 上午10:50          1.0            签名生成
     */
    public static String signGenerate(String cid, String timestamp,
                                      String requesttype, String securykey)
    {
        return MD5Util.encrypt(cid + timestamp + requesttype + securykey);
    }

    /**
     * @Description: 签名生成
     * @Author: 王俊文
     * @Date: 19-7-8 下午4:28
     * @Param:  [cid 渠道ID, timestamp 时间戳, requesttype API接口名称]
     * @returns: java.lang.String
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-8 下午4:28          1.0          签名生成
     */
    public static String signGenerate(String cid, String timestamp,
                                      String requesttype)
    {
        return MD5Util.encrypt(cid + timestamp + requesttype);
    }

    /**
     * @Description: 签名校验
     * @Author: 王俊文
     * @Date: 19-6-4 上午11:01
     * @Param: [sign 接口传入签名, interfaceSign 本地生成签名]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-6-4 上午11:01          1.0            签名校验
     */
    public static boolean signCheck(String sign, String localSign)
    {
        return sign.equals(localSign);
    }

}
