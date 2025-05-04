package cn.smxy.newsserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.smxy.newsserver.entity.User;
import cn.smxy.newsserver.entity.UserDto;

import java.util.List;


public interface UserService extends IService<User> {

    UserDto login(User user);
    int register(UserDto userDto);
    List<User> getAllWithRoleByCondition(Integer pageNum,
                                         Integer pageSize,
                                         String username,
                                         String email,
                                         String address);

}
