package hc.controller;

import hc.pojos.Respeonse.ResponseResult;
import hc.pojos.User;
import hc.pojos.dto.UserDTO;
import hc.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(value="用户登录",tags="用户登录")
@CrossOrigin(origins = "*")
public class UserController {
    @Resource
    private IUserService userService;

    /**
     * 用户登录
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ResponseResult login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public ResponseResult register(@RequestBody UserDTO userDTO){
        return userService.register(userDTO);
    }
}


