package hc.exception;



import hc.pojos.enums.HttpCodeEnum;
import lombok.Data;

@Data
public class DataException extends RuntimeException{
    private Integer code;
    private String errorMsg;
    public DataException(HttpCodeEnum enums){
        this.code=enums.getCode();
        this.errorMsg=enums.getErrorMsg();
    }
}
