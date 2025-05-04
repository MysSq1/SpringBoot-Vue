package cn.smxy.newsserver.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.smxy.newsserver.entity.GoodsType;
import cn.smxy.newsserver.mapper.GoodsTypeMapper;
import org.springframework.stereotype.Service;


@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService{
}




