package cn.smxy.newsserver.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.smxy.newsserver.entity.Files;
import cn.smxy.newsserver.mapper.FilesMapper;
import org.springframework.stereotype.Service;

// 针对表的数据库操作Service实现
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements FilesService{
}




