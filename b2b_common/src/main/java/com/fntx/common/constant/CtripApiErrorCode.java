package com.fntx.common.constant;
//携程错误返回码
public enum CtripApiErrorCode {
    //API处理过程中系统错误
    SystemError("100000","API处理过程中系统错误"),
    InvalidHeadInformation("100001","请求Header节点错误"),
    Timestampistimeout("100002","时间戳超时"),
    RequestTypeerror("100003","请求类型错误"),
    AbsolutePatherror("100004","解析请求URL错误"),
    InvalidSignatrue("100005","签名错误"),
    InvalidRequest("100006","无效请求"),
    InvalidSIdOrAllianceId("100006","客户端未传入参数或者参数值不正确"),
    tokeninvalid("100006", "token失效,请重新生成!"),
    InvalidAlliance("100006","无效联盟站点，SID或者AllianceID未注册"),
    RequestRejected("100006","请求被拒绝，联盟站点已被列入黑名单"),
    InvalidIP("100006","无效IP地址，客户端IP地址未注册"),
    PermissionDenied("100006","无权限访问"),
    RateLimit("100006","超出调用频率限制"),
    FormatJsonDeSerializeFail("100007","请求JSON序列化失败"),
    MissingRequestBody("100008","请求JSON未正确包含业务请求JSON，或业务请求JSON同请求类型不符合"),
    ApiInternalError("100009","API内部处理错误"),
    ACCESS_TIMEOUT("100010","访问超时");

    private String code;
    private String name;

    CtripApiErrorCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}