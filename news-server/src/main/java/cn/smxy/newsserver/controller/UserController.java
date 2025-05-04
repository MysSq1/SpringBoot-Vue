package cn.smxy.newsserver.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.smxy.newsserver.common.Code;
import cn.smxy.newsserver.common.Result;
import cn.smxy.newsserver.entity.User;
import cn.smxy.newsserver.entity.UserDto;
import cn.smxy.newsserver.exception.ServiceException;
import cn.smxy.newsserver.service.UserService;
import cn.smxy.newsserver.untils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin

//将对 /user 路径的 HTTP 请求映射到被注解的方法或控制器类上，从而使得当用户访问 /user 路径时，能够调用相应的方法或处理对应的业务逻辑。
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //登入功能
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Code.PARAM_ERR, "参数错误");
        }
        UserDto dto = userService.login(user);
        dto.setPassword(null);
        return Result.success(dto);
    }

    //注册功能
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){
        userService.register(userDto);
                return Result.success("注册成功");
    }

    //根据提供的用户名从数据库中检索用户信息
    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        // 从数据库中获取符合条件的用户信息
        User user = userService.getOne(queryWrapper);
        // 如果找到了用户信息，则将其转换为 DTO 对象，并返回成功的响应
        if (user != null) {
            UserDto userDto = new UserDto();
            BeanUtil.copyProperties(user, userDto, true);
            return Result.success(userDto);
        }
        return Result.error("无效的用户名");
    }
    //根据提供的 ID 删除用户信息
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if( userService.removeById(id) ){
            return Result.success("删除成功");
        }
        return Result.error(Code.ERROR,"删除失败");
    }
    //用于批量删除用户信息
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        // 根据提供的 ID 列表批量删除用户信息
        if( userService.removeByIds(ids) ){
            return Result.success("批量删除成功");
        }
        return Result.error(Code.ERROR,"批量删除失败");
    }
    //用于分页查询用户信息
    @GetMapping("/page")
    public Result findAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String email, @RequestParam(defaultValue = "") String address){
        List<User> list = userService.getAllWithRoleByCondition((pageNum - 1) * pageSize, pageSize, username, email, address);
        // 构建返回结果的 Map，包含查询到的用户信息列表和总记录数
        Map<String, Object> map = new HashMap<>();
        map.put("records", list);  // 用户信息列表
        map.put("total", userService.count());  // 总记录数
        return Result.success(map);
    }
    //充值功能
    @GetMapping("/recharge/{bigDecimal}")
    public Result recharge(@PathVariable BigDecimal bigDecimal){
        User currentUser = TokenUtil.getCurrentUser();  // 获取当前登录用户信息
        // 判断当前用户是否存在
        if (currentUser != null) {
            currentUser.setMoney(currentUser.getMoney().add(bigDecimal));  // 修改当前用户的余额（增加充值金额）
            userService.updateById(currentUser);  //更新用户信息
            return Result.success("充值成功!");
        }
        return Result.error("充值失败!");
    }






    //根据提供的 ID 从数据库中检索用户信息
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }
    //用于获取所有用户信息
    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }
    //用于保存或更新用户信息
    @PostMapping
    public Result saveOrUpdate(@RequestBody User user){
        String msg;
        if(user.getId()==null || user.getId()==0){
            msg = "添加";
        }else{
            msg = "更新";
        }
        try {
            // 调用 userService 的 saveOrUpdate 方法保存或更新用户信息
            userService.saveOrUpdate(user);
            return Result.success(msg + "成功", Code.SUCCESS);
        } catch (Exception e) {
            throw new ServiceException(Code.ERROR, msg + "失败");
        }
    }
}
