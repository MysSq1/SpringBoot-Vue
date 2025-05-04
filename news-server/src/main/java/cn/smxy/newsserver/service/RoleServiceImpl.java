package cn.smxy.newsserver.service;

import cn.smxy.newsserver.entity.Role;
import cn.smxy.newsserver.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getById(Integer id) {
        return roleMapper.getById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }
}
