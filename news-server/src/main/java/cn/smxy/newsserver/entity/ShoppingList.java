package cn.smxy.newsserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName(value ="shopping_list")
@Data
public class ShoppingList implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String uuid;
    private Integer userId;
    private Integer goodsId;
    private String goodsType;
    private String goodsName;
    private Integer count;
    private BigDecimal price;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    public ShoppingList() {
    }

    public ShoppingList(Integer id, String uuid, Integer userId, Integer goodsId, String goodsType, String goodsName, Integer count, BigDecimal price, Date createTime) {
        this.id = id;
        this.uuid = uuid;
        this.userId = userId;
        this.goodsId = goodsId;
        this.goodsType = goodsType;
        this.goodsName = goodsName;
        this.count = count;
        this.price = price;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", goodsType='" + goodsType + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", createTime=" + createTime +
                '}';
    }
}
