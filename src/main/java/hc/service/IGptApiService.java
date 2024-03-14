package hc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hc.pojos.GptApi;
import hc.pojos.Respeonse.ResponseResult;

public interface IGptApiService extends IService<GptApi> {

    ResponseResult saveChat(String content);

    ResponseResult getChat();
}
