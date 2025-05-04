<template>
  <div style="font-size: 12px; line-height: 60px; display: flex;">
    <div class="ml-5" style="margin-top: 5px;flex: 1;">
      <el-breadcrumb separator="/" style="font-size: 20px;margin-top: 14px" separator-class="el-icon-arrow-right">
        <el-breadcrumb-item v-for="item in breadCrumbList" :key="item.path">
          <router-link :to="item.path">{{ item.meta.title }}</router-link>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 200px; cursor: pointer; text-align: right;">
      <div style="display: inline-block">
        <img :src="user.avatarUrl" alt=""
             referrerpolicy="no-referrer"
             style="width: 45px; border-radius: 50%; position: relative;top: 8px;right: 5px;">
        <span class="el-dropdown-link" style="font-size: 16px;">
          {{ user.nickname }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center;">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/personCenter" style="text-decoration: none;color: #606266"><i class="el-icon-s-custom"></i>个人信息
          </router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0" v-show="isShowBtn">
          <span style="text-decoration: none" @click="">
            <router-link to="/backend" style="text-decoration: none;color: #606266">
              <i class="el-icon-switch-button"></i>
              后台管理
            </router-link>
          </span>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="">
            <router-link to="/backend" style="text-decoration: none;color: #606266">
              <i class="el-icon-coin"></i>
            账户余额￥{{ user.money }}
            </router-link>
          </span>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="logout">
            <i class="el-icon-switch-button"></i>
            退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import Aside from "@/components/Aside";
import router from "@/router";

export default {
  name: "Top",
  data() {
    return {
      user: {
        nickname: "请登录",
        avatarUrl: "",
        roleId: 1,
      },
      isShowBtn: false,
    }
  },
  created() {
    this.getUser();
  },

  methods: {
     //获取用户信息
     //从本地存储获取用户信息，然后向后端获取对应用户的最新数据
     //根据用户角色设置按钮显示状态
    getUser() {
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : "";
      if (username) {
        // 根据用户名向后端请求用户数据
        this.request.get("/user/username/" + username).then(res => {
          // 更新本地的最新用户数据
          if (res.code == 200) {
            this.user = res.data;
            // 根据用户角色设置按钮显示状态
            this.isShowBtn = this.user.roleId == 1 ? true : false;
          } else {
            // 如果获取用户信息失败，则显示错误消息
            this.$message.error(res.msg);
          }
        });
      } else {
        // 如果未登录，则显示未登录消息，并跳转至登录页面
        this.$message.error("未登录！");
        router.push("/login");
      }
    },

    //退出登入
    logout() {
      // 将用户重定向至登录页面
      this.$router.push("/login");
      // 从本地存储中移除用户信息
      localStorage.removeItem("user");
      // 显示退出成功的警告消息
      this.$message.warning("退出成功");
    }

  },
  computed: {
    breadCrumbList() {
      return this.$route.matched;
    }
  }

}
</script>

<style scoped>

</style>
