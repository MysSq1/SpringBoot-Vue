<template>
  <el-menu :default-openeds="['1']"
           default-active="/home"
           style="height: 100%; overflow-x: hidden"
           background-color="#303133"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           router
  >
    <!--        active-text-color 菜单选中颜色-->
    <!--    #FFD04B-->
    <div style="height: 60px; line-height: 60px; text-align: center; ">
      <img src="../assets/img/Xiaomi.png" style="width: 40px;position: relative; top: 12px;margin-right: 5px;">
      <b style="color: white; margin-left: 5px">小米商城</b>
    </div>

<!--    前台-->
    <div v-show="isShow">
      <el-menu-item index="/home" >
        <template slot="title">
          <i class="el-icon-house"></i>
          <span>主页</span>
        </template>
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>功能</span>
        </template>
        <el-menu-item index="/personCenter">
          <template slot="title">
            <i class="el-icon-user"></i>
            <span>个人信息</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/changePassword">
          <template slot="title">
            <i class="el-icon-setting"></i>
            <span>修改密码</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/wallet">
          <template slot="title">
            <i class="el-icon-bank-card"></i>
            <span>充值</span>
          </template>
        </el-menu-item>

      </el-submenu>
      <el-menu-item index="/shoppingCart">
        <template slot="title">
          <i class="el-icon-shopping-cart-full"></i>
          <span>购物车</span>
        </template>
      </el-menu-item>

      <el-menu-item index="/shoppingList">
        <template slot="title">
          <i class="el-icon-s-order"></i>
          <span>消费记录</span>
        </template>
      </el-menu-item>
    </div>

<!--    后台-->
<!--    点击直接回到index的位置-->
    <div v-show="!isShow">
      <el-menu-item index="/home" >
        <template slot="title">
          <i class="el-icon-house"></i>
          <span>主页</span>
        </template>
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>系统管理</span>
        </template>
        <el-menu-item index="/backend/userManage">
          <template slot="title">
            <i class="el-icon-user"></i>
            <span>用户管理</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/backend/typeManage">
          <template slot="title">
            <i class="el-icon-user"></i>
            <span>类别管理</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/backend/goodsManage">
          <template slot="title">
            <i class="el-icon-user"></i>
            <span>商品管理</span>
          </template>
        </el-menu-item>
      </el-submenu>


    </div>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isFrontend: Boolean,
  },
  data() {
    return {
      isShow: this.global.isFrontend
    }
  },
  created() {
    this.global.isFrontend = true;
  }

}
</script>

<style scoped>

</style>
