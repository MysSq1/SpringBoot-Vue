<template>
  <div>
    <el-card style="width: 500px; ">
      <el-form label-width="80px" size="small" :model="resetForm"
               :rules="resetFormRules"
               ref="resetForm">

        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="旧密码" prop="password">
          <el-input v-model="resetForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newpwd">
          <el-input v-model="resetForm.newpwd" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="验证密码" prop="newpassword1">
          <el-input v-model="resetForm.newpassword1" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-left: 100px" @click="save">修改</el-button>
          <el-button @click="$router.push('/')">返回</el-button>
        </el-form-item>
      </el-form>

    </el-card>
  </div>
</template>

<script>

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入旧密码"));
      } else if (value.toString() !== this.form.password) {
        callback(new Error("旧密码不正确"));
      } else {
        callback();
      }
    };
    var validatePass1 = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入新密码"));
      } else if (value.toString().length < 3 || value.toString().length > 18) {
        callback(new Error("密码长度为3-18位"));
      } else if (value === this.resetForm.password) {
        callback(new Error("新密码与旧密码一致"));
      }else {
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.resetForm.newpwd) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      edit: false,
      resetForm: {
        newpassword1: "",
        password: "",
        username: ""
      },
      resetFormRules: {
        password: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        newpwd: [
          { required: true, validator: validatePass1, trigger: 'blur' }
        ],
        newpassword1: [
          { required: true, validator: validatePass2, trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getUser()
  },
  methods: {

    //获取用户信息并填充表单数据
    getUser() {
      // 通过用户ID向服务器请求特定用户的信息
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
     // 保存表单数据，修改用户密码，并在确认后发送请求至服务器
    save() {
      this.$refs['resetForm'].validate((valid) => {
        if(valid){
          // 将表单中的新密码赋值给用户密码
          this.form.password = this.resetForm.newpassword1;
          // 弹出确认框，确认是否修改密码
          this.$confirm('确实是否修改密码？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            // 向服务器发送请求，保存表单数据和修改用户密码
            this.request.post("/user", this.form).then(res => {
              if (res) {
                // 如果修改成功，则显示成功消息并跳转到登录页面
                this.$message.success("修改成功")
                this.$router.push({
                  name:'Login'
                });
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
        }
      });
    },


    // save() {
    //   this.$refs['resetForm'].validate((valid) => {
    //     if(valid){
    //       this.form.password=this.resetForm.newpassword1;
    //       this.$confirm('确实是否修改密码？', '提示', {
    //         confirmButtonText: '确定',
    //         cancelButtonText: '取消',
    //         type: 'warning'
    //       }).then(() => {
    //         this.request.post("/user", this.form).then(res => {
    //           if (res) {
    //             this.$message.success("修改成功")
    //             this.$router.push({
    //               name:'Login'
    //             });
    //           } else {
    //             this.$message.error("保存失败")
    //           }
    //         })
    //       }).catch(() => {
    //         this.$message({
    //           type: 'info',
    //           message: '已取消修改'
    //         });
    //       });
    //     }
    //   });
    // },

    // save() {
    //   this.$refs['resetForm'].validate((valid) => {
    //     if(valid){
    //       this.form.password = this.resetForm.newpassword1;
    //       this.$confirm('确实是否修改密码？', '提示', {
    //         confirmButtonText: '确定',
    //         cancelButtonText: '取消',
    //         type: 'warning'
    //       }).then(() => {
    //         this.$axios({
    //           method: 'post',
    //           url: "http://localhost:8089/news-server/user",
    //           data: this.form
    //         }).then(res => {
    //           if (res) {
    //             this.$message.success("修改成功")
    //             this.$router.push({
    //               name:'Login'
    //             });
    //           } else {
    //             this.$message.error("保存失败")
    //           }
    //         })
    //       }).catch(() => {
    //         this.$message({
    //           type: 'info',
    //           message: '已取消修改'
    //         });
    //       });
    //     }
    //   });
    // },

    handleAvatarSuccess(res) {
      this.form.avatarUrl = res;
    },
  },
};
</script>

<style scoped>

</style>
