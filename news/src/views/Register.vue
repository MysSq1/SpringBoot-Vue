<template>
  <div>
    <div
        style="width: 750px; margin: 200px auto; border-radius: 10px; box-shadow: 0 0 10px -2px cornflowerblue; display: flex">
      <div style="flex:1;padding: 30px">
        <img src="../assets/img/Xiaomi.png" alt="" style="width: 300px;  margin-top: 60px">
      </div>
      <div style="flex:1;padding: 50px">
        <div class="form-toggle">
          <b :class="{'active': activeToggle=='account'}" @click="toggle('account')">账号注册</b>
        </div>
        <el-form :rules="rules" ref="regForm" :model="regForm">
          <el-form-item prop="username">
            <el-input size="medium" v-model="regForm.username" prefix-icon="el-icon-user"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input size="medium" show-password v-model="regForm.password" prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
          <el-form-item prop="email">
            <el-input size="medium" v-model="regForm.email" prefix-icon="el-icon-message"></el-input>
          </el-form-item>
          <div>
            <el-button @click="reg" type="primary" size="medium" style="width: 100%">注册</el-button>
          </div>
          <div style="margin: 10px 0;">
          </div>
          <div style="margin: 10px 0;text-align: right;">
            <a href="/login" style="color: var(--colorRed)">返回登录</a>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Register",
  data() {
    return {
      user: {
        token: "",
      },
      regForm: {
        username: "",
        password: "",
        email: "",
        code: "",
      },
      loginAdmin: '',
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
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {
            pattern: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9_\.\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
            message: '请输入正确的邮箱格式', trigger: 'blur'
          }
        ],
      }
    }
  },
  methods: {
    //用户注册函数
    //验证用户输入的注册表单数据，发送注册请求至服务器
    //如果注册成功（状态码为200），显示注册成功消息，并跳转到登录页面
    // 如果注册失败，则显示错误消息
    reg() {
      this.$refs['regForm'].validate((valid) => {
        if (valid) {
          // 向服务器发送注册请求
          this.$axios({
            method: 'post',
            url: 'http://localhost:8089/news-server/user/register',
            data: this.regForm
          }).then(response => {
            if (response.data.code === 200) {
              // 如果注册成功，显示注册成功消息，并跳转到登录页面
              this.$message.success(response.data.data);
              this.$router.push('/login');
            } else {
              // 如果注册失败，则显示错误消息
              this.$message.error(response.data.msg);
            }
          }).catch(error => {
            // 如果请求出错，则显示注册失败消息
            this.$message.error("注册失败：" + error.message);
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

.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}

</style>
