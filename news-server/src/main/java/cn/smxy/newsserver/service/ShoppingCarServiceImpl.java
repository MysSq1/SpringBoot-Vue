package cn.smxy.newsserver.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.smxy.newsserver.common.Code;
import cn.smxy.newsserver.entity.Goods;
import cn.smxy.newsserver.entity.ShoppingCar;
import cn.smxy.newsserver.entity.ShoppingList;
import cn.smxy.newsserver.entity.User;
import cn.smxy.newsserver.exception.ServiceException;
import cn.smxy.newsserver.mapper.GoodsMapper;
import cn.smxy.newsserver.mapper.ShoppingCarMapper;
import cn.smxy.newsserver.mapper.UserMapper;
import cn.smxy.newsserver.untils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class ShoppingCarServiceImpl extends ServiceImpl<ShoppingCarMapper, ShoppingCar> implements ShoppingCarService {

    @Autowired
    private ShoppingCarMapper shoppingCarMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ShoppingListService shoppingListService;

    @Override
    public int addCar(ShoppingCar shoppingCar) {
        try {
            User currentUser = TokenUtil.getCurrentUser();
            if (currentUser == null) {
                return 0;
            }
            Integer goodsId = shoppingCar.getGoodsId();
            Integer userId = currentUser.getId();
            QueryWrapper<ShoppingCar> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            queryWrapper.eq("goods_id", goodsId);
            queryWrapper.eq("is_account", 0);
            ShoppingCar one = shoppingCarMapper.selectOne(queryWrapper);
            if (one != null) {
                one.setCount(shoppingCar.getCount());
                shoppingCarMapper.updateById(one);
            } else {
                shoppingCar.setUserId(userId);
                shoppingCarMapper.insert(shoppingCar);
            }
            return 1;
        } catch (Exception e) {
            throw new ServiceException(Code.ERROR, "未知错误！");
        }
    }

    public int addCarHome(ShoppingCar shoppingCar) {
        try {
            User currentUser = TokenUtil.getCurrentUser();
            if (currentUser == null) {
                return 0;
            }
            Integer goodsId = shoppingCar.getGoodsId();
            Integer userId = currentUser.getId();
            QueryWrapper<ShoppingCar> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            queryWrapper.eq("goods_id", goodsId);
            queryWrapper.eq("is_account", 0);
            ShoppingCar one = shoppingCarMapper.selectOne(queryWrapper);
            if (one != null) {
                one.setCount(shoppingCar.getCount() + one.getCount());
                shoppingCarMapper.updateById(one);
            } else {
                shoppingCar.setUserId(userId);
                shoppingCarMapper.insert(shoppingCar);
            }
            return 1;
        } catch (Exception e) {
            throw new ServiceException(Code.ERROR, "未知错误！");
        }
    }


    @Override
    public int settle(List<ShoppingCar> lists) {
        User currentUser = TokenUtil.getCurrentUser();
        BigDecimal userMoney = currentUser.getMoney();
        BigDecimal totalPrice = new BigDecimal(0.0);

        List<Goods> updateGoodsList = new ArrayList<>();
        List<ShoppingCar> updateShoppingCarList = new ArrayList<>();
        List<ShoppingList> insertShoppingList = new ArrayList<>();

        for (ShoppingCar item : lists) {
            //库存查询
            Integer goodsId = item.getGoodsId();
            Goods goods = goodsMapper.selectById(goodsId);
            Integer count = item.getCount();
            if( goods.getStock() < count ){
                throw new ServiceException(Code.ERROR,goods.getName()+"库存不足");
            }

            //待更新 商品库存
            goods.setStock(goods.getStock()-count);
            updateGoodsList.add(goods);

            //计算小计金额
            BigDecimal m = item.getPrice().multiply(new BigDecimal(count));
            totalPrice = totalPrice.add(m);

            //待更新 购物车结算状态
            item.setIsAccount(1);
            updateShoppingCarList.add(item);

            //待插入 生成当前商品购物记录
            ShoppingList shoppingList = new ShoppingList();
            shoppingList.setUserId(currentUser.getId());
            shoppingList.setUuid(IdUtil.fastSimpleUUID());
            shoppingList.setGoodsId(item.getGoodsId());
            shoppingList.setGoodsName(item.getGoodsName());
            shoppingList.setGoodsType(String.valueOf(item.getGoodsType()));
            shoppingList.setCount(item.getCount());
            shoppingList.setPrice(item.getPrice());
            insertShoppingList.add(shoppingList);

        }

        if (totalPrice.compareTo(userMoney) == 1) {
            throw new ServiceException(Code.ERROR, "账户金额不足！");
        }

        //待更新 用户账户金额
        currentUser.setMoney(userMoney.subtract(totalPrice));

        try {
            //更新商品库存
            goodsService.updateBatchById(updateGoodsList);
            //更新购物车
            this.updateBatchById(updateShoppingCarList);
            //更新用户账户金额
            userMapper.updateById(currentUser);
            //插入购物记录
            shoppingListService.saveBatch(insertShoppingList);


            return 1;
        }catch (Exception e){
            throw new ServiceException(Code.ERROR,"结算失败！");
        }



    }
}




