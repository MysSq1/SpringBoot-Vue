package cn.smxy.newsserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.smxy.newsserver.entity.ShoppingList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

//  在mapper包中创建xxMapper的接口，该接口继承BaseMapper类即可，不需要编写额外的代码
@Mapper
public interface ShoppingListMapper extends BaseMapper<ShoppingList> {
    @Select("SELECT SUM(count*price) FROM shopping_list")
    public BigDecimal totalTurnover();
}




