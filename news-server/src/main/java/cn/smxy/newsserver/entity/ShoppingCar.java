package cn.smxy.newsserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName(value ="shopping_car")
@Data
public class ShoppingCar implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer goodsId;
    private String goodsName;
    private Integer goodsType;
    private Integer count;
    private BigDecimal price;
    private Integer isAccount;
    private String image;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    public ShoppingCar() {
    }

    public ShoppingCar(Integer id, Integer userId, Integer goodsId, String goodsName, Integer goodsType, Integer count, BigDecimal price, Integer isAccount, String image, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsType = goodsType;
        this.count = count;
        this.price = price;
        this.isAccount = isAccount;
        this.image = image;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
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

    public Integer getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(Integer isAccount) {
        this.isAccount = isAccount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ShoppingCar{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsType=" + goodsType +
                ", count=" + count +
                ", price=" + price +
                ", isAccount=" + isAccount +
                ", image='" + image + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
