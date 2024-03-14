package hc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hc.pojos.Token;

public interface ITokenService extends IService<Token> {
    void timedDeleteToken();
}
