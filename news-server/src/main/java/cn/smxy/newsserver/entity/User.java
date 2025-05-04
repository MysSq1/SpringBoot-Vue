package cn.smxy.newsserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName(value ="user")
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "role_id")
    private Integer roleId;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private BigDecimal money;
    private String address;
    @TableField(value = "avatar_url")
    private String avatarUrl;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    @TableField(exist = false)
    private Role role;

    public User() {
    }

    public User(Integer id, Integer roleId, String username, String password, String nickname, String email, BigDecimal money, String address, String avatarUrl, Date createTime, Date updateTime, Role role) {
        this.id = id;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.money = money;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", money=" + money +
                ", address='" + address + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", role=" + role +
                '}';
    }
}
