package cn.smxy.newsserver.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.smxy.newsserver.common.Code;
import cn.smxy.newsserver.entity.User;
import cn.smxy.newsserver.entity.UserDto;
import cn.smxy.newsserver.exception.ServiceException;
import cn.smxy.newsserver.mapper.UserMapper;
import cn.smxy.newsserver.untils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    private static final Log LOG = Log.get();
    private User getUserInfo(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);//从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Code.SYSTEM_ERR, "系统错误");
        }
        return one;
    }

    @Override
    public UserDto login(User user) {
        User one = getUserInfo(user);
        UserDto userDto;
        if (one != null) {
            userDto = new UserDto();
            BeanUtil.copyProperties(one, userDto, true); //把one的参数复制到userDto，忽略大小写
            //设置token
            String token = TokenUtil.getToken(one);
            userDto.setToken(token);
            return userDto;
        } else {
            throw new ServiceException(Code.ERROR, "用户名或密码错误");
        }
    }

    @Override
    public int register(UserDto userDto) {
        QueryWrapper<User> q1 = new QueryWrapper<>();
        q1.eq("username", userDto.getUsername());
        q1.eq("email", userDto.getEmail());

        try {
            User u1 = userMapper.selectOne(q1);;//从数据库查询用户信息
            if(u1==null){
                User res = new User();
                BeanUtil.copyProperties(userDto, res, true); //把one的参数复制到userDto，忽略大小写
                res.setRoleId(2);
                res.setNickname(res.getUsername());
                return userMapper.insert(res);
            }
            return 0;
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Code.ERROR, "该用户已存在");
        }
    }

    @Override
    public List<User> getAllWithRoleByCondition(Integer pageNum, Integer pageSize, String username, String email, String address) {
        return userMapper.getAllWithRoleByCondition(pageNum,pageSize,username,email,address);
    }

}
