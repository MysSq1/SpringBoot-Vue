package cn.smxy.newsserver.mapper;

import cn.smxy.newsserver.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("select * from role where id = #{id}")
    public Role getById(Integer id);

    @Select("select * from role")
    public List<Role> findAll();
}
