package cn.smxy.newsserver.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.smxy.newsserver.common.Result;
import cn.smxy.newsserver.entity.ShoppingList;
import cn.smxy.newsserver.entity.User;
import cn.smxy.newsserver.mapper.ShoppingListMapper;
import cn.smxy.newsserver.service.GoodsService;
import cn.smxy.newsserver.service.ShoppingListService;
import cn.smxy.newsserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/echarts")
@CrossOrigin
public class EchartsController {

    @Autowired  //将 UserService 这个服务（或者类）注入到当前的类中，
    // 这样可以在当前类中直接使用 UserService 提供的方法或功能而不需要手动创建 UserService 的实例。
    private UserService userService;

    @Autowired
    private ShoppingListMapper shoppingListMapper;

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/count")
    //获取用于 Echarts 可视化的各实体数量。
    public Result get(){
        int totalUser = (int) userService.count();   //获取用户总数
        int totalTurnoverCount = (int) shoppingListService.count();  //获取交易数
        BigDecimal totalTurnover = shoppingListMapper.totalTurnover();  //获取总交易额
        int totalGoods = (int) goodsService.count();  //获取商品总数
        return Result.success(CollUtil.newArrayList(totalUser,totalTurnoverCount,totalTurnover,totalGoods));
    }
}
