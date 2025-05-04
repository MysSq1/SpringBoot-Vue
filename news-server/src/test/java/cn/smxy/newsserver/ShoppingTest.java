package cn.smxy.newsserver;

import cn.smxy.newsserver.mapper.GoodsMapper;
import cn.smxy.newsserver.mapper.ShoppingListMapper;
import cn.smxy.newsserver.mapper.UserMapper;
import cn.smxy.newsserver.service.ShoppingListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShoppingTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    private ShoppingListService shoppingListService;
    @Autowired
    private ShoppingListMapper shoppingListMapper;
    @Test
    void contextLoads() {
    }
}
