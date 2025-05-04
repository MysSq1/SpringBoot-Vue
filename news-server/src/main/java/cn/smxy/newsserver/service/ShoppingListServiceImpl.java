package cn.smxy.newsserver.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.smxy.newsserver.entity.ShoppingList;
import cn.smxy.newsserver.mapper.ShoppingListMapper;
import org.springframework.stereotype.Service;


@Service
public class ShoppingListServiceImpl extends ServiceImpl<ShoppingListMapper, ShoppingList>
    implements ShoppingListService{

}




