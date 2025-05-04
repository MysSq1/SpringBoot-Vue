package cn.smxy.newsserver.service;

import cn.smxy.newsserver.entity.Role;

import java.util.List;

public interface RoleService {
    public Role getById(Integer id);
    public List<Role> findAll();
}
