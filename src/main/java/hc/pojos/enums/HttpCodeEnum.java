package hc.pojos.enums;

public enum HttpCodeEnum {
    SUCCESS(200,"操作成功"),
    NEED_LOGIN(300,"请先登录后操作"),
    NO_FOUND_PARAM(400,"未找到参数"),
    PARAM_INVALID(401,"无效参数"),
    SERVER_ERROR(500,"服务器内部出错"),
    DATA_NOT_EXIST(600,"数据不存在"),
    DATA_EXIST(601,"数据已存在"),
    DATA_FAILURE(602,"数据过期"),
    FILE_CREATE_FAIL(700,"文件创建失败"),
    FILE_UPLOAD_FAIL(701,"文件上传失败"),
    FILE_TEXT_FAIL(702,"文本保存失败"),
    JWT_VERIFICATION_ERROR(800,"jwt认证出错"),
    GPT_API_FAIL(900,"gpt-api调用接口失败"),
    GPT_RESPONSE_INVALID(901,"gpt回答失效"),
    UNKNOWN_ERROR(1000,"自定义类型");


    private Integer code;
    private String errorMsg;
    HttpCodeEnum(Integer code, String errorMsg){
        this.code=code;
        this.errorMsg=errorMsg;
    }
    public int getCode(){return code;}
    public String getErrorMsg(){return errorMsg;}
}
