package cn.smxy.newsserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.smxy.newsserver.common.Code;
import cn.smxy.newsserver.common.Result;
import cn.smxy.newsserver.entity.ShoppingList;
import cn.smxy.newsserver.entity.User;
import cn.smxy.newsserver.exception.ServiceException;
import cn.smxy.newsserver.service.ShoppingListService;
import cn.smxy.newsserver.untils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingList")
@CrossOrigin
public class ShoppingListController {
    @Autowired
    private ShoppingListService shoppingListService;

    @GetMapping("/pageByUser")
    public Result pageByUser(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String uuid) {
        try {// 获取当前登录用户信息
            User currentUser = TokenUtil.getCurrentUser();
            // 创建分页对象
            IPage<ShoppingList> page = new Page<>(pageNum, pageSize);
            // 构建查询条件
            QueryWrapper<ShoppingList> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(!uuid.equals(""), "uuid", uuid); // 若 uuid 不为空则加入模糊查询条件
            queryWrapper.eq("user_id", currentUser.getId()); // 添加根据当前用户 ID 查询条件
            queryWrapper.orderByDesc("create_time"); // 按创建时间降序排列
            // 查询并返回结果
            IPage<ShoppingList> pages = shoppingListService.page(page, queryWrapper);
            return Result.success(pages);
        } catch (Exception e) {
            throw new ServiceException(Code.ERROR, "查询失败");
        }
    }



}
