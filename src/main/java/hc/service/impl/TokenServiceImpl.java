package hc.service.impl;


import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hc.mapper.TokenMapper;
import hc.pojos.Token;
import hc.service.ITokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements ITokenService {
    @Override
    @Scheduled(fixedRate = 1000*60*12)
    public void timedDeleteToken() {
        List<String> tokenIds = query().lt("create_time", LocalDateTime.now().minusMinutes(60*24)).list()
                .stream().map(Token::getId).collect(Collectors.toList());
        if(!tokenIds.isEmpty())
            removeByIds(tokenIds);
    }
}
