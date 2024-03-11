package hc.exception;



import hc.pojos.enums.HttpCodeEnum;
import lombok.Data;

@Data
public class ServerErrorException extends RuntimeException{
    private Integer code;
    private String errorMsg;
    public ServerErrorException(HttpCodeEnum enums){
        this.code=enums.getCode();
        this.errorMsg=enums.getErrorMsg();
    }
}
