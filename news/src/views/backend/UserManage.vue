<template>
  <div>

    <div style="padding: 10px 0">
      <el-input v-model="username" style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-search"></el-input>
      <el-input v-model="email" style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message"
                class="ml-5"></el-input>
      <el-input v-model="address" style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position"
                class="ml-5"></el-input>
      <el-button @click="load()" class="ml-5" type="primary">搜索</el-button>
      <el-button @click="reset()" class="ml-5" type="warning">重置</el-button>
    </div>

    <div style="padding: 10px 0">
      <el-button type="primary" @click="handleAdd">
        新增
        <i class="el-icon-circle-plus-outline"/>
      </el-button>

      <el-popconfirm
          class="ml-5 mr-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" @click="">批量删除<i class="el-icon-circle-plus-outline"/></el-button>
      </el-popconfirm>

    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="'headerBg'"
              :header-cell-style="{}"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="role.name" label="角色" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名" width="140"></el-table-column>
      <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
      <el-table-column label="头像" width="100">
        <template slot-scope="scope">
          <img :src="scope.row.avatarUrl" alt="" style="width: 50px;margin: 5px 15px;"></img>
        </template>
      </el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="money" label="金额"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="操作" width="170">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"/></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="handleDel(scope.row.id)"
          >
            <el-button type="danger" slot="reference" @click="">删除<i class="el-icon-remove-outline"/></el-button>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item>
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8089/news-server/file/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img style="width: 100px; margin-left: 60px" v-if="form.avatarUrl" :src="form.avatarUrl" v-model="form.avatarUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.roleId" placeholder="请选择" :disabled="roleId=='1'">
            <el-option
                v-for="item in roleList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" show-password autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="金额">
          <el-input v-model="form.money" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "UserManage",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      email: "",
      address: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      headerBg: 'headerBg',
      roleList: [],
      roleId: 2,
    }
  },
  created() {
    this.load();
    this.getAllRole();
  },
  methods: {
    //加载用户数据
    load() {
      // 向服务器请求用户列表的分页数据，根据条件进行筛选
      this.request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          email: this.email,
          address: this.address
        }
      }).then(res => {
        // 更新页面上的用户表格数据和总数
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
    //删除用户
    handleDel(id) {
      // 向服务器发送删除用户的请求
      this.request.delete("/user/" + id).then(res => {
        if (res.code === 200) {
          // 如果删除成功，显示删除成功消息，并关闭对话框，并重新加载用户数据
          this.$message.success("删除成功")
          this.dialogFormVisible = false;
          this.load();
        } else {
          // 如果删除失败，则显示删除失败消息
          this.$message.error("删除失败")
        }
      })
    },
    //批量删除用户
    delBatch() {
      // 提取多个用户数据的ID组成数组
      let ids = this.multipleSelection.map(v => v.id) //[{},{},{}] => [1,2,3]
      // 向服务器发送批量删除用户的请求，根据用户ID数组进行删除
      this.request.post("/user/del/batch", ids).then(res => {
        if (res.code === 200) {
          // 如果批量删除成功，显示批量删除成功消息，并关闭对话框，并重新加载用户数据
          this.$message.success("批量删除成功")
          this.dialogFormVisible = false;
          this.load();
        } else {
          // 如果批量删除失败，则显示批量删除失败消息
          this.$message.error("批量删除失败")
        }
      })
    },
    //保存用户信息
    save() {
      // 向服务器发送保存用户信息的请求
      this.request.post("/user", this.form).then(res => {
        if (res.code === 200) {
          // 如果保存成功，显示成功消息，并关闭对话框，并重新加载用户数据
          this.$message.success(res.msg)
          this.dialogFormVisible = false;
          this.load();
        } else {
          // 如果保存失败，则显示错误消息
          this.$message.error(res.msg)
        }
      })
    },
    //获取所有用户的信息，以List列表形式展示
    getAllRole() {
      // 向服务器请求所有角色的数据
      this.request.get("/role").then(res => {
        if (res.code === 200) {
          // 如果请求成功，更新页面上的角色列表信息
          this.roleList = res.data;
        }
      })
    },
    //处理页面尺寸变化
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    //处理页码变化
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load();
    },
    //更新表单中的头像 URL
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res;
    },
    //批量删除
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //重置搜索条件并重新加载数据
    reset() {
      this.username = "";
      this.email = "";
      this.address = "";
      this.load();
    },
    //处理添加操作
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {
        id: "",
        username: "",
        password: "",
        nickname: "",
        email: "",
        avatarUrl: "",
        address: "",
        money: "",
        roleId: "",};
    },
    //处理编辑操作
    handleEdit(row) {
      this.form = {
        id: "",
        username: "",
        password: "",
        nickname: "",
        email: "",
        avatarUrl: "",
        address: "",
        money: "",
        roleId: "",
      }
      Object.assign(this.form, row)
      this.roleId = row.roleId
      this.dialogFormVisible = true;
    },

  },
}
</script>

<style scoped>

</style>
