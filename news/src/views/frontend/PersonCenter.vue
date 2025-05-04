<template>
  <div>
    <el-card style="width: 500px; ">
      <el-form label-width="80px" size="small">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:8089/news-server/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
          <img style="width: 200px;margin-left: 130px;margin-bottom: 20px" v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar" alt="">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="账户余额">
          <el-input v-model="form.money" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-left: 100px" @click="save">保存</el-button>
          <el-button @click="$router.push('/')">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "PersonCenter",
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      edit: false,
    }
  },
  created() {
    this.getUser()
  },
  methods: {
    //  获取用户信息并填充表单数据
    getUser() {
      // 向服务器请求当前用户的信息
      this.request.get("/user/" + this.user.id).then(res=>{
        if( res.code == 200 ){
          // 如果成功获取到用户信息，则填充表单数据
          this.form = res.data
        } else {
          // 如果未成功获取到用户信息，则提示用户登录
          this.$message.error("请登录")
        }
      })
    },
    // 保存修改后的个人信息，并在确认后发送请求至服务器如果保存成功，显示成功消息并刷新页面
    save() {
      // 弹出确认框，确认是否修改个人信息
      this.$confirm('确实是否修改个人信息？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 向服务器发送请求，保存修改后的个人信息
        this.request.post("/user", this.form).then(res => {
          if (res) {
            // 如果保存成功，则显示成功消息并刷新页面
            this.$message.success("保存成功")
            location.reload();
          } else {
            // 如果保存失败，则显示错误消息
            this.$message.error("保存失败")
          }
        })
      }).catch(() => {
        // 如果取消修改，则显示提示消息
        this.$message({
          type: 'info',
          message: '已取消修改'
        });
      });
    },

    handleAvatarSuccess(res) {
      this.form.avatarUrl = res;
    }
  }
}
</script>

<style scoped>

</style>
