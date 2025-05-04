package cn.smxy.newsserver.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.smxy.newsserver.entity.Goods;
import cn.smxy.newsserver.mapper.GoodsMapper;
import org.springframework.stereotype.Service;


@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService{
}




