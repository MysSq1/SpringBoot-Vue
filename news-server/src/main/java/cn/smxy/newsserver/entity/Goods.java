package cn.smxy.newsserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer typeId;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private String image;
    private Integer state;
    private Integer isDeleted;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    public Goods() {
    }

    public Goods(Integer id, String name, Integer typeId, BigDecimal price, Integer stock, String description, String image, Integer state, Integer isDeleted, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.image = image;
        this.state = state;
        this.isDeleted = isDeleted;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", state=" + state +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
