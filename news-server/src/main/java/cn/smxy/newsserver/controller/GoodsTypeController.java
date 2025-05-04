package cn.smxy.newsserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.smxy.newsserver.common.Code;
import cn.smxy.newsserver.common.Result;
import cn.smxy.newsserver.entity.GoodsType;
import cn.smxy.newsserver.exception.ServiceException;
import cn.smxy.newsserver.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/goodsType")
public class GoodsTypeController {
    @Autowired
    private GoodsTypeService goodsTypeService;

    //处理DELETE请求，路径为"/{id}"，用于删除特定ID的商品类型
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        goodsTypeService.removeById(id);    // 调用 goodsTypeService 的 removeById 方法，根据提供的ID删除商品类型
        return Result.success();
    }

    // 处理POST请求，路径为"/del/batch"，用于批量删除商品类型
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        goodsTypeService.removeByIds(ids);      // 调用 goodsTypeService 的 removeByIds 方法，根据提供的ID列表批量删除商品类型
        return Result.success();
    }

    // 处理GET请求，路径为"/{id}"，用于查询特定ID的商品类型信息
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(goodsTypeService.getById(id));
    }

    // 处理GET请求，路径为"/page"，用于查询商品类型的分页数据
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        IPage<GoodsType> page = new Page<>(pageNum,pageSize);     // 创建分页对象，并指定当前页和每页的数据量
        QueryWrapper<GoodsType> queryWrapper = new QueryWrapper<>();  // 创建查询条件构造器
        queryWrapper.orderByDesc("id");
        // 如果传入了名称参数name，则使用like方式进行模糊查询
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        IPage<GoodsType> goodsType = goodsTypeService.page(page,queryWrapper);
        return Result.success(goodsType);
    }

    // 调用 goodsTypeService 的 list 方法，获取所有商品类型信息并返回成功的响应
    @GetMapping
    public Result findAll(){
        return Result.success(goodsTypeService.list());
    }
    @PostMapping
    public Result save(@RequestBody GoodsType goodsType) {
        String msg;
        // 根据商品类型是否存在ID来判断是添加还是更新操作
        if (goodsType.getId() == null || goodsType.getId()==0) {
            msg = "添加";
        }else{
            msg = "更新";
        }
        try {
            // 调用 goodsTypeService 的 saveOrUpdate 方法，保存或更新商品类型信息
            goodsTypeService.saveOrUpdate(goodsType);
            return Result.success(msg+"成功", Code.SUCCESS);
        }catch (Exception e){
            throw new ServiceException(Code.ERROR,msg+"失败");
        }
    }
}
