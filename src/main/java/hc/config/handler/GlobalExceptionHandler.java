package hc.config.handler;



import hc.pojos.Respeonse.ResponseResult;
import hc.pojos.enums.HttpCodeEnum;
import hc.exception.CustomizeException;
import hc.exception.DataException;
import hc.exception.ParamErrorException;
import hc.exception.ServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;




@Slf4j
@RestControllerAdvice()
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(CustomizeException.class)
    public ResponseResult CustomizeException(CustomizeException e){
        log.error("自定义异常： "+e.getMessage());
        return ResponseResult.errorResult(HttpCodeEnum.UNKNOWN_ERROR,
               e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(DataException.class)
    public ResponseResult DataException(DataException e){
        log.error("数据异常： "+e.getErrorMsg());
        return ResponseResult.errorResult(e.getCode(),e.getErrorMsg());
    }

    @ResponseBody
    @ExceptionHandler(ParamErrorException.class)
    public ResponseResult ParamErrorException(ParamErrorException e){
        log.error("参数异常： "+e.getErrorMsg());
        return ResponseResult.errorResult(e.getCode(),e.getErrorMsg());
    }

    @ResponseBody
    @ExceptionHandler(ServerErrorException.class)
    public ResponseResult ServerErrorException(ServerErrorException e){
        log.error("服务器异常： "+e.getErrorMsg());
        return ResponseResult.errorResult(e.getCode(),e.getErrorMsg());
    }

}
