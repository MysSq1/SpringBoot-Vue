package cn.smxy.newsserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value ="files")
@Data
public class Files implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private Long size;
    private String url;
    private String md5;
    private Integer isDelete;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    public Files() {
    }

    public Files(Integer id, String name, String type, Long size, String url, String md5, Integer isDelete, Date createTime) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.url = url;
        this.md5 = md5;
        this.isDelete = isDelete;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Files{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                ", md5='" + md5 + '\'' +
                ", isDelete=" + isDelete +
                ", createTime=" + createTime +
                '}';
    }
}
