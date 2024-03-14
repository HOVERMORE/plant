package hc.controller;

import hc.pojos.Respeonse.ResponseResult;
import hc.service.IGptApiService;
import hc.service.impl.GptApiServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/gptapi")
@Api(value = "GPT窗口", tags = "GPT窗口")
@CrossOrigin(origins = "*")
@Slf4j
public class GptApiController {
    @Resource
    private IGptApiService gptApiService;

    @GetMapping("/savechat")
    @ApiOperation("保存聊天内容")
    public ResponseResult saveChat(@RequestParam String content){
        return gptApiService.saveChat(content);
    }

    @GetMapping("/getchat")
    @ApiOperation("获取聊天内容")
    public ResponseResult getChat(){
        return gptApiService.getChat();
    }
}
