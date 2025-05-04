<template>
  <div>

    <div style="padding: 10px 0">
      <el-input v-model="name" style="width: 200px" placeholder="请输入类别" suffix-icon="el-icon-search"></el-input>
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
      <el-table-column prop="id" label="ID"sortable></el-table-column>
      <el-table-column prop="name" label="类别"></el-table-column>
      <el-table-column label="操作">
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

    <el-dialog title="类别信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="类别">
          <el-input v-model="form.name" autocomplete="off"></el-input>
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
  name: "TypeManage",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      headerBg: 'headerBg',
    }
  },
  created() {
    this.load();
    this.getAllRole();
  },
  methods: {
    //加载商品类型数据
    load() {
      // 向服务器请求商品类型列表的分页数据，根据条件进行筛选
      this.request.get("/goodsType/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        // 更新页面上的商品类型表格数据和总数
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
    //处理删除商品类型操作
    handleDel(id) {
      // 向服务器发送删除商品类型的请求
      this.request.delete("/goodsType/" + id).then(res => {
        if (res.code === 200) {
          // 如果删除成功，显示删除成功消息，并关闭对话框，并重新加载商品类型数据
          this.$message.success("删除成功")
          this.dialogFormVisible = false;
          this.load();
        } else {
          // 如果删除失败，则显示删除失败消息
          this.$message.error("删除失败")
        }
      })
    },
    //批量删除商品类型
    delBatch() {
      // 提取多个商品类型数据的ID组成数组
      let ids = this.multipleSelection.map(v => v.id) //[{},{},{}] => [1,2,3]
      // 向服务器发送批量删除商品类型的请求，根据商品类型ID数组进行删除
      this.request.post("/goodsType/del/batch", ids).then(res => {
        if (res.code === 200) {
          // 如果批量删除成功，显示批量删除成功消息，并关闭对话框，并重新加载商品类型数据
          this.$message.success("批量删除成功")
          this.dialogFormVisible = false;
          this.load();
        } else {
          // 如果批量删除失败，则显示批量删除失败消息
          this.$message.error("批量删除失败")
        }
      })
    },
    //保存商品类型信息
    save() {
      // 向服务器发送保存商品类型信息的请求
      this.request.post("/goodsType", this.form).then(res => {
        if (res.code === 200) {
          // 如果保存成功，显示成功消息，并关闭对话框，并重新加载商品类型数据
          this.$message.success(res.msg)
          this.dialogFormVisible = false;
          this.load();
        } else {
          // 如果保存失败，则显示错误消息
          this.$message.error(res.msg)
        }
      })
    },
    //重置搜索条件并重新加载数据
    reset() {
      this.name = "";
      this.load();
    },
    //处理添加操作
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {
        id: "",
        name: "",};
    },
    //处理编辑操作
    handleEdit(row) {
      this.form = {
        id: "",
        name: "",
      }
      Object.assign(this.form, row)
      this.roleId = row.roleId
      this.dialogFormVisible = true;
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
    //批量删除
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //当头像上传成功后，将返回的上传结果赋值给表单中的 avatarUrl 字段
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res;
    },
  },
}
</script>

<style scoped>

</style>
