package hc.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hc.mapper.TokenMapper;
import hc.pojos.Token;
import hc.service.ITokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements ITokenService {
}
