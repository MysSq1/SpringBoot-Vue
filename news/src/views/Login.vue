<template>
  <div>

    <div
        style="width: 750px; margin: 200px auto; border-radius: 10px; box-shadow: 0 0 10px -2px cornflowerblue; display: flex">
      <div style="flex:1;padding: 30px">
        <img src="../assets/img/Xiaomi.png" alt="" style="width: 300px;  margin-top: 30px">
      </div>

      <div style="flex:1;padding: 50px">
        <div class="form-toggle">
          <b :class="{'active': activeToggle=='account'}" @click="toggle('account')">账号登录</b>
        </div>

        <el-form :rules="rules" ref="userForm" :model="loginForm" v-if="activeToggle==='account'">
          <el-form-item prop="username">
            <el-input size="medium" v-model="loginForm.username" prefix-icon="el-icon-user"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input size="medium" show-password v-model="loginForm.password" prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
          <div>
            <el-button @click="login" type="primary" size="medium" style="width: 100%">登录</el-button>
          </div>
          <div style="margin: 10px 0;">
<!--            <a href="/" style="flex: 1; margin-left: 0;">忘记密码</a>-->
            <a href="/register" style="flex: 1; float: right; color: var(--colorRed)">立即注册</a>
          </div>
        </el-form>

        <el-form ref="userForm" :rules="rules" :model="emailForm" v-else>
          <el-form-item prop="email">
            <el-input size="medium" v-model="emailForm.email" prefix-icon="el-icon-message"></el-input>
          </el-form-item>

          <div style="margin: 10px 0;text-align: right;">
            <a href="/" style="color: var(--colorRed)">立即注册</a>
          </div>
        </el-form>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {
        token: "",
      },
      loginForm: {
        username: "",
        password: ""
      },
      emailForm: {
        email: "",
        code: ""
      },
      activeToggle: 'account',
      second: 0,
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],

      }
    }
  },
  methods: {
    toggle(active) {
      this.$nextTick(() => {
        this.$refs['userForm'].resetFields();
      })
      this.activeToggle = active;
    },
     //用户登录函数
     //验证用户输入的表单数据，发送登录请求至服务器
     //如果登录成功（状态码为200），将用户信息存储到浏览器本地存储，并跳转到首页
     //如果登录失败，则显示错误消息
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          // 向服务器发送登录请求
          this.$axios({
            method: 'post',
            url: 'http://localhost:8089/news-server/user/login',
            data: this.loginForm
          }).then(res => {
            if (res.data.code === 200) {
              // 如果登录成功，将用户信息存储到浏览器本地存储，并跳转到首页
              localStorage.setItem("user", JSON.stringify(res.data.data));
              this.$router.push("/");
              this.$message.success("登录成功");
            } else {
              // 如果登录失败，则显示错误消息
              this.$message.error(res.data.msg);
            }
          }).catch(error => {
            // 如果请求出错，则显示登录失败消息
            this.$message.error("登录失败：" + error.message);
          });
        }
      });
    },
  }
}
</script>

<style scoped>
.form-toggle {
  margin: 20px 0;
  text-align: center;
}

.form-toggle b {
  font-size: 20px;
  cursor: pointer;
}

.active {
  color: var(--colorRed);
}


</style>
