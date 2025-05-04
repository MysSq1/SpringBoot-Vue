package cn.smxy.newsserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.smxy.newsserver.entity.ShoppingCar;

import java.util.List;

public interface ShoppingCarService extends IService<ShoppingCar> {
    public int addCar(ShoppingCar shoppingCar);

    public int addCarHome(ShoppingCar shoppingCar);

    public int settle(List<ShoppingCar> lists);
}
