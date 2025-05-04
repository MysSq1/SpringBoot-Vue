package cn.smxy.newsserver.controller;

import cn.smxy.newsserver.common.Result;
import cn.smxy.newsserver.entity.User;
import cn.smxy.newsserver.service.RoleService;
import cn.smxy.newsserver.untils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public Result findAll(){
        return Result.success(roleService.findAll());
    }

    @GetMapping("/powerCheck") // 处理GET请求，路径为"/powerCheck"，用于用户权限检查
    public Result powerCheck(){
        try {// 获取当前登录的用户信息
            User currentUser = TokenUtil.getCurrentUser();
            // 如果当前用户不为空，返回包含用户角色ID的成功响应
            if (currentUser != null){
                return Result.success(currentUser.getRoleId());
            }
            return Result.error("请登录");
        } catch (Exception e){
            return Result.error("系统错误");
        }
    }

}
