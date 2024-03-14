package hc.pojos.Respeonse;


import hc.pojos.enums.HttpCodeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 结果返回类
 * @param <T>
 */
@ToString
@Data
public class ResponseResult<T> implements Serializable {
    private String msg;

    private Integer code;

    private T data;

    public ResponseResult(){
        this.code=200;
    }
    public ResponseResult(Integer code,T data){
        this.code=code;
        this.data=data;
    }
    public ResponseResult(Integer code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
    public ResponseResult(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public static ResponseResult errorResult(Integer code,String msg){
        ResponseResult responseResult=new ResponseResult();
        return responseResult.error(code,msg);
    }

    public static ResponseResult errorResult(HttpCodeEnum enums){
        return setAppHttpCodeEnum(enums,enums.getErrorMsg());
    }
    public static ResponseResult errorResult(HttpCodeEnum enums, String errorMsg){
        return setAppHttpCodeEnum(enums,errorMsg);
    }

    public static ResponseResult okResult(Integer code, String msg) {
        ResponseResult result=new ResponseResult();
        return result.ok(code,null,msg);
    }

    public static ResponseResult okResult(Object data){
        ResponseResult result=setAppHttpCodeEnum(HttpCodeEnum.SUCCESS, HttpCodeEnum.SUCCESS.getErrorMsg());
        if(data!=null){
            result.setData(data);
        }
        return result;
    }

    public static ResponseResult okResult(){
        ResponseResult result=setAppHttpCodeEnum(HttpCodeEnum.SUCCESS, HttpCodeEnum.SUCCESS.getErrorMsg());

        return result;
    }

    public ResponseResult<?> ok(Integer code, T data, String msg) {
        this.code=code;
        this.data=data;
        this.msg=msg;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }
    public ResponseResult<?> ok(T data){
        this.data=data;
        return this;
    }

    public ResponseResult error(Integer code, String errorMsg) {
        this.code=code;
        this.msg=errorMsg;
        return this;
    }


    private static ResponseResult setAppHttpCodeEnum(HttpCodeEnum enums, String errorMsg) {
        return okResult(enums.getCode(),errorMsg);
    }
}
