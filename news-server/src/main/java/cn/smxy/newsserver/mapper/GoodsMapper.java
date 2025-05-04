package cn.smxy.newsserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.smxy.newsserver.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

//  在mapper包中创建xxMapper的接口，该接口继承BaseMapper类即可，不需要编写额外的代码
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

}




