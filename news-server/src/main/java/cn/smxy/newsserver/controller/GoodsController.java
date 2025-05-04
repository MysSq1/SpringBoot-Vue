package cn.smxy.newsserver.controller;

import cn.hutool.core.util.StrUtil;
import cn.smxy.newsserver.common.Code;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.smxy.newsserver.common.Code;
import cn.smxy.newsserver.common.Result;
import cn.smxy.newsserver.entity.Goods;
import cn.smxy.newsserver.exception.ServiceException;
import cn.smxy.newsserver.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    // 处理DELETE请求，路径为"/{id}"，用于删除特定ID的商品信息
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 调用 goodsService 的 removeById 方法，根据提供的ID删除商品信息
        if( goodsService.removeById(id) ){
            return Result.success("删除成功");
        }
        return Result.error(Code.ERROR,"删除失败");
    }

    // 处理POST请求，路径为"/del/batch"，用于批量删除商品信息
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        // 调用 goodsService 的 removeByIds 方法，根据提供的ID列表批量删除商品信息
        if( goodsService.removeByIds(ids) ){
            return Result.success("批量删除成功");
        }
        return Result.error(Code.ERROR,"批量删除失败");
    }

    // 分页显示
    @GetMapping("/page")
    public Result findAll(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize,
                          @RequestParam(defaultValue = "") String name,
                          @RequestParam(defaultValue = "") String typeId,
                          @RequestParam(defaultValue = "") String condition){
                            //condition: 1 最新, 2 销量,3 价格
        try {
            IPage<Goods> page = new Page<>(pageNum,pageSize);
            QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(!name.equals(""), "name", name);
            queryWrapper.eq(!typeId.equals(""), "type_id", typeId);
            IPage<Goods> goodsPage = goodsService.page(page,queryWrapper);

            return Result.success(goodsPage);
        }catch (Exception e){
            throw new ServiceException(Code.ERROR,"查询失败");
        }
    }

    // 处理GET请求，路径为"/pageByUser"，用于根据用户条件进行商品信息分页查询
    @GetMapping("/pageByUser")
    public Result findAllByUser(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize,
                          @RequestParam(defaultValue = "") String name,
                          @RequestParam(defaultValue = "") String typeId,
                          @RequestParam(defaultValue = "") String condition){
        //condition: 1 最新, 2 销量,3 价格
        try {
            // 创建分页对象，并指定当前页和每页的数据量
            IPage<Goods> page = new Page<>(pageNum,pageSize);
            QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();    // 创建查询条件构造器
            queryWrapper.like(!name.equals(""), "name", name);
            queryWrapper.eq(!typeId.equals(""), "type_id", typeId);
            queryWrapper.eq("state", "1");  // 添加查询条件：state=1（假设1表示商品可用状态）
            // 根据不同的condition参数进行排序
            queryWrapper.orderByDesc(condition.equals("1"),"create_time");
            queryWrapper.orderByDesc(condition.equals("2"),"stock");
            queryWrapper.orderByAsc(condition.equals("3"),"price");
            IPage<Goods> goodsPage = goodsService.page(page,queryWrapper);

            return Result.success(goodsPage);
        }catch (Exception e){
            throw new ServiceException(Code.ERROR,"查询失败");
        }
    }

    // 处理GET请求，路径为"/getByName/{name}"，用于根据商品名称获取商品信息
    @GetMapping("/getByName/{name}")
    public Result getByName(@PathVariable String name){
        if(StrUtil.isNotBlank(name)){  // 判断商品名称是否不为空或不为空格  isNotBlank()是一个字符串工具类方法，通常用于检查字符串是否不为空且不是由空格组成。
            QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",name);  // 根据商品名称构建查询条件
            Goods one = goodsService.getOne(queryWrapper);
            return Result.success(one);
        }
        return Result.error("查询失败");
    }






    @GetMapping
    public Result findAll() {
        return Result.success(goodsService.list());
    }

    @PostMapping
    public Result saveOrUpdate(@RequestBody Goods goods){
        String msg;
        if(goods.getId()==null || goods.getId()==0){
            msg = "添加";
        }else{
            msg = "更新";
        }
        try {
            goodsService.saveOrUpdate(goods);
            return Result.success(msg+"成功",Code.SUCCESS);
        }catch (Exception e){
            throw new ServiceException(Code.ERROR,msg+"失败");
        }
    }
}
