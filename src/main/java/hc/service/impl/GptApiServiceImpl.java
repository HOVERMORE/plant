package hc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hc.exception.ParamErrorException;
import hc.mapper.GptApiMapper;
import hc.pojos.GptApi;
import hc.pojos.Respeonse.ResponseResult;
import hc.pojos.dto.GptDTO;
import hc.pojos.enums.HttpCodeEnum;
import hc.service.IGptApiService;
import hc.service.ILogsService;
import hc.util.FileUtil;
import hc.util.GptChatClient;
import hc.util.constants.GptConstants;
import hc.util.thread.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class GptApiServiceImpl extends ServiceImpl<GptApiMapper, GptApi> implements IGptApiService {

    @Resource
    private ILogsService logsService;

    @Override
    public ResponseResult saveChat(String content) {
        if(StrUtil.isEmpty(content)){
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName()+":"+"content参数缺失");
            logsService.markLogs(HttpCodeEnum.NO_FOUND_PARAM,
                    this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                    "content参数缺失",
                    UserHolder.getUser().getNickName(), UserHolder.getUser().getId()
                    );
            throw new ParamErrorException(HttpCodeEnum.NO_FOUND_PARAM);
        }
        String fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String userFilepath = FileUtil.createFiles(GptConstants.USER_FILE);
        String userFileName = FileUtil.saveText(fileName, content, userFilepath);
        String chatGptResponse = GptChatClient.getChatGptResponse(content);
        if("返回值为空".equals(chatGptResponse)){
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName()+":"+
                    HttpCodeEnum.GPT_RESPONSE_INVALID.getErrorMsg());
            logsService.markLogs(HttpCodeEnum.GPT_RESPONSE_INVALID,
                    this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                    HttpCodeEnum.GPT_RESPONSE_INVALID.getErrorMsg(),
                    UserHolder.getUser().getNickName(), UserHolder.getUser().getId()
            );
            return new ResponseResult().error(HttpCodeEnum.GPT_RESPONSE_INVALID.getCode(), HttpCodeEnum.GPT_RESPONSE_INVALID.getErrorMsg());
        }
        String gptFilepath = FileUtil.createFiles(GptConstants.GPT_FILE);
        String gptFileName = FileUtil.saveText(fileName, chatGptResponse, gptFilepath);
        if(StrUtil.isNotBlank(userFileName)&&StrUtil.isNotBlank(gptFileName)){
            GptApi gptApi=new GptApi()
                    .setUserId(UserHolder.getUser().getId())
                    .setUserFileUrl(userFileName)
                    .setGptFileUrl(gptFileName)
                    .setCreateUser(UserHolder.getUser().getNickName());
            save(gptApi);
            return new ResponseResult().ok(HttpCodeEnum.SUCCESS.getCode(),chatGptResponse,"对话保存成功");
        }else {
            return new ResponseResult().error(HttpCodeEnum.FILE_TEXT_FAIL.getCode(),HttpCodeEnum.FILE_TEXT_FAIL.getErrorMsg());
        }
    }

    @Override
    public ResponseResult getChat() {
        List<GptApi> list = query().eq("user_id", UserHolder.getUser().getId()).orderByDesc("create_time").list();
        List<GptDTO> gptDTOlist=new ArrayList<>();
        if(!list.isEmpty()) {
            for(GptApi g:list) {
                GptDTO gptDTO=new GptDTO()
                        .setId(g.getId())
                        .setNickName(UserHolder.getUser().getNickName())
                        .setTime(g.getCreateTime());
                String userContent = FileUtil.getContent(g.getUserFileUrl());
                String gptContent = FileUtil.getContent(g.getGptFileUrl());
                gptDTO.setUserContent(userContent);
                gptDTO.setGptContent(gptContent);
                gptDTOlist.add(gptDTO);
            }
        }
        return new ResponseResult().ok(HttpCodeEnum.SUCCESS.getCode(),gptDTOlist,"获取对话成功");
    }

}
