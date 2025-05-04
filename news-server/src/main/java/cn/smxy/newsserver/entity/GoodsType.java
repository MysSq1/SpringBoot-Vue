package cn.smxy.newsserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value ="goods_type")
@Data

public class GoodsType implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;

    public GoodsType() {
    }

    public GoodsType(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "GoodsType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
