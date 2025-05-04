package cn.smxy.newsserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.smxy.newsserver.common.Code;
import cn.smxy.newsserver.common.Result;
import cn.smxy.newsserver.entity.ShoppingCar;
import cn.smxy.newsserver.entity.User;
import cn.smxy.newsserver.exception.ServiceException;
import cn.smxy.newsserver.service.ShoppingCarService;
import cn.smxy.newsserver.untils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/shoppingCar")
public class ShoppingCarController {
    @Autowired
    private ShoppingCarService shoppingCarService;

    @GetMapping("/findAllByUser")
    public Result findAllByUser(){
        try{
            // 获取当前用户信息
            User currentUser = TokenUtil.getCurrentUser();
            QueryWrapper<ShoppingCar> queryWrapper = new QueryWrapper<>();   // 创建条件查询器
            queryWrapper.eq("user_id",currentUser.getId());       // 设置查询条件：用户ID与当前用户的ID匹配
            queryWrapper.eq("is_account",0); // 设置查询条件：未结算的商品
            queryWrapper.orderByDesc("create_time");  // 设置查询结果按照创建时间降序排序
            List<ShoppingCar> list = shoppingCarService.list(queryWrapper);  // 通过条件查询获取购物车中未结算的商品列表
            return Result.success(list);
        }catch (Exception e){
            throw new ServiceException(Code.ERROR,"查询失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // // 调用 shoppingCarService 的 removeById 方法，根据传入的 ID 删除购物车中的商品
        if( shoppingCarService.removeById(id) ){
            return Result.success("移除成功");
        }
        return Result.error(Code.ERROR,"删除失败");
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        //  // 调用 shoppingCarService 的 removeByIds 方法，根据传入的 ID 列表批量删除购物车中的商品
        if( shoppingCarService.removeByIds(ids) ){
            return Result.success("批量移除成功");
        }
        return Result.error(Code.ERROR,"批量删除失败");
    }

    @PostMapping("/addCarHome")
    public Result addCarHome(@RequestBody ShoppingCar shoppingCar){
        //  // 调用 shoppingCarService 的 addCarHome 方法，将商品信息添加到购物车中
        if( shoppingCarService.addCarHome(shoppingCar)==1 ){
            return Result.success("添加成功");
        }
        return Result.error("添加成功");
    }

    @PostMapping("/settle")
    // 处理POST请求，路径为"/settle"，用于对购物车中的商品进行结算操作
    public Result settle(@RequestBody List<ShoppingCar> lists){
        if( lists == null ){
            return Result.error("请先勾选购物车中的商品");
        }
        //  // 调用 shoppingCarService 的 settle 方法，对购物车中的商品进行结算操作
        if (shoppingCarService.settle(lists) == 1){
            return Result.success("结算成功!");
        }
        return Result.error("结算失败");
    }





    @PostMapping
    //调用 shoppingCarService 的 addCar 方法，将商品信息添加到购物车中
    public Result add(@RequestBody ShoppingCar shoppingCar){
        if( shoppingCarService.addCar(shoppingCar)==1 ){
            return Result.success("添加成功");
        }
        return Result.error("添加成功");
    }
    @GetMapping
    // 调用 shoppingCarService 的 list 方法，获取购物车中所有商品信息并返回
    public Result findAll() {
        return Result.success(shoppingCarService.list());
    }
}
