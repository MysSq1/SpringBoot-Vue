package cn.smxy.newsserver.controller;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.smxy.newsserver.common.Result;
import cn.smxy.newsserver.entity.Files;
import cn.smxy.newsserver.mapper.FilesMapper;
import cn.smxy.newsserver.service.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Resource
    private FilesService fileService;
    @Resource
    private FilesMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestBody MultipartFile file) throws IOException {
        // 获取原始文件名、文件类型和文件大小
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //定义一个文件唯一的标示码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        // 设置文件上传路径
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 创建文件的父目录（如果不存在）
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        //获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        Files files = getFileByMd5(md5);
        //获取文件的url
        String url;
        if (files != null) {
            url = files.getUrl();
        }else{
            // 如果文件不存在，将文件存储到磁盘目录
            file.transferTo(uploadFile);
            url = "http://localhost:8089/news-server/file/" + fileUUID;
        }
        // 将文件信息存储到数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);// 将文件大小从字节转换为KB
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileService.save(saveFile);// 保存文件信息到数据库
        return url;  // 返回文件的URL地址
    }

    //处理上传图片请求的方法
    @PostMapping("/uploadImg")
    public Object uploadImg(@RequestBody MultipartFile file) throws IOException {
        // 获取上传图片的相关信息
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //定义一个文件唯一的标示码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        // 设置图片上传路径
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 创建文件的父目录（如果不存在）
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        //获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        Files files = getFileByMd5(md5);
        //获取文件的url
        String url;
        if (files != null) {
            url = files.getUrl();
        }else{
            // 如果图片不存在，将图片存储到磁盘目录
            file.transferTo(uploadFile);
            url = "http://localhost:8089/news-server/file/" + fileUUID;
        }
        // 将图片信息存储到数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);// b => kb
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileService.save(saveFile);
        // 返回包含图片上传结果的 JSON 对象
        return JSONUtil.parseObj("{ \"errno\": 0, \"data\": [{ \"url\": \"" + url + "\"}]}");
    }

    //处理文件下载请求的方法
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        //根据文件的位翼标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        ServletOutputStream os = response.getOutputStream();
        // 设置响应头，告诉浏览器这是一个文件下载的响应
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流并写入到响应输出流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    //通过文件的 MD5 值查询文件是否存在于数据库中
    private Files getFileByMd5(String md5){
        // 创建查询条件，根据文件的 MD5 值查询数据库中是否存在对应记录
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        // 执行查询操作，获取符合条件的文件记录列表
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        // 如果查询结果为空则返回 null，否则返回第一个符合条件的文件记录
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        // 创建分页对象，指定页码和每页数据条目数
        IPage<Files> page = new Page<>(pageNum, pageSize);
        // 创建查询条件对象
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);  // 查询未删除的文件
        queryWrapper.like(!name.equals(""), "name", name);   // 根据文件名进行模糊查询
        queryWrapper.orderByDesc("id");  // 根据文件名进行模糊查询
        // 调用 fileService 的分页查询方法，执行查询操作
        IPage<Files> filesPage = fileService.page(page, queryWrapper);
        // 将查询结果封装为成功的响应
        return Result.success(filesPage);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        // 根据文件 ID 获取文件信息
        Files files = fileService.getById(id);
        // 设置文件为已删除状态（假删除，标记为已删除，而非物理删除）
        files.setIsDelete(1);
        // 执行更新操作，更新文件状态为已删除
        return Result.success(fileService.updateById(files));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        // 创建查询条件，以待删除文件的 ID 列表为条件
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        // 针对每个待删除的文件，设置状态为已删除（假删除）
        for (Files file : fileService.list(queryWrapper)) {
            file.setIsDelete(1);
            fileService.updateById(file);
        }
        return Result.success();
    }
    // 更新文件信息
    @PostMapping("/update")
    public Result save(@RequestBody Files files){
        return Result.success(fileService.updateById(files));
    }
}
