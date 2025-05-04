package cn.smxy.newsserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.smxy.newsserver.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//  在mapper包中创建xxMapper的接口，该接口继承BaseMapper类即可，不需要编写额外的代码
@Mapper
public interface UserMapper extends BaseMapper<User> {
    public List<User> getAllWithRole();

    public List<User> getAllWithRoleByCondition(@Param("pageNum")Integer pageNum,
                                                @Param("pageSize")Integer pageSize,
                                                @Param("username")String username,
                                                @Param("email")String email,
                                                @Param("address")String address);
}
