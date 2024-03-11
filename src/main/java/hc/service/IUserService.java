package hc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hc.pojos.Respeonse.ResponseResult;
import hc.pojos.User;
import hc.pojos.dto.UserDTO;


public interface IUserService extends IService<User> {
    /**
     * 用户登录功能
     * @param userDTO
     * @return
     */
    ResponseResult login(UserDTO userDTO);

    /**
     * 用户注册功能
     * @param userDTO
     * @return
     */
    ResponseResult register(UserDTO userDTO);
}
