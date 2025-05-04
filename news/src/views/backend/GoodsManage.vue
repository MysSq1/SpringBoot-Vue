<template>
  <div>

    <div style="padding: 10px 0">
      <el-input v-model="goodsName" style="width: 200px" placeholder="请输入商品名称" suffix-icon="el-icon-search"></el-input>
      <el-select v-model="typeId" placeholder="商品类别" style="margin-left: 5px">
        <el-option
            v-for="item in typeList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-button @click="load()" class="ml-5" type="primary">搜索</el-button>
      <el-button @click="reset()" class="ml-5" type="warning">重置</el-button>
    </div>

    <div style="padding: 10px 0">
      <el-button type="primary" @click="handleAdd">
        新增
        <i class="el-icon-circle-plus-outline"/>
      </el-button>


    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="'headerBg'"
              @selection-change="handleSelectionChange"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}">
<!--      <el-table-column type="selection" width="55"></el-table-column>-->
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column label="商品图片" width="100">
        <template slot-scope="scope">
          <img :src="scope.row.image" alt="" style="width: 50px;margin: 5px 15px;"/>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="商品名称" width="80"></el-table-column>
      <el-table-column prop="price" label="价格" width="140"></el-table-column>
      <el-table-column prop="stock" label="库存" width="120"></el-table-column>
      <el-table-column prop="createTime" label="上架时间"></el-table-column>
      <el-table-column  width="70" label="销售状态">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.state"
                     @change="change_switch(scope.row)"
                     :active-value="1"
                     :inactive-value="0"
                     active-color="#13ce66"
                     inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="170">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"/></el-button>

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

    <el-dialog title="商品信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="80px" size="small">
        <el-form-item>
          <el-upload action="http://localhost:8089/news-server/file/upload" :show-file-list="false" :on-success="handleAvatarSuccess">
            <img style="width: 100px; margin-left: 60px" v-if="form.image" :src="form.image" v-model="form.image" class="avatar" alt="">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品类别">
          <el-select v-model="form.typeId" placeholder="请选择">
            <el-option
                v-for="item in typeList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="商品描述">
          <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入内容"
              v-model="form.description">
          </el-input>
        </el-form-item>
        <el-form-item label="商品价格">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品库存">
          <el-input v-model="form.stock" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="销售状态">
          <el-switch
              v-model="form.state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="1"
              :inactive-value="0">
          </el-switch>
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
  name: "GoodsManage",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      goodsName: "",
      typeId: "",
      dialogFormVisible: false,
      form: {
        id: "",
        image: "",
        name: "",
        typeId: "",
        description: "",
        state: "",
        price: "",
        stock: ""
      },
      multipleSelection: [],
      headerBg: 'headerBg',
      typeList: []
    }
  },
  created() {
    this.load();
    this.getAllType();
  },
  methods: {
    //加载商品数据
    //向服务器请求商品列表的分页数据，根据条件进行筛选
    //包括pageNum：请求页数，pageSize：每页数据条数，name：商品名称，typeId：商品类型ID
    load() {
      this.request.get("/goods/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.goodsName,
          typeId: this.typeId
        }
      }).then(res => {
        if(res.code == 200){
          // 如果请求成功，更新页面上的商品表格数据和总数
          this.tableData = res.data.records;
          this.total = res.data.total;
        } else {
          // 如果请求失败，则显示错误消息
          this.$message.error(res.code)
        }
      })
    },
     //理删除商品操作
     //向服务器发送删除商品的请求，根据商品ID（id）进行删除
    handleDel(id) {
      this.request.delete("/goods/" + id).then(res => {
        if (res.code === 200) {
          // 如果删除成功，显示删除成功消息，并关闭对话框，并重新加载商品数据
          this.$message.warning("删除成功")
          this.dialogFormVisible = false;
          this.load();
        } else {
          // 如果删除失败，则显示删除失败消息
          this.$message.error("删除失败")
        }
      })
    },
    // 处理批量删除商品操作
    // 构建商品ID数组，根据选中的多个商品数据（multipleSelection）提取其ID组成数组
    delBatch() {
      // 提取多个商品数据的ID组成数组
      let ids = this.multipleSelection.map(v => v.id) //[{},{},{}] => [1,2,3]
      // 向服务器发送批量删除商品的请求，根据商品ID数组进行删除
      this.request.post("/goods/del/batch", ids).then(res => {
        if (res.code === 200) {
          // 如果批量删除成功，显示批量删除成功消息，并关闭对话框，并重新加载商品数据
          this.$message.warning("批量删除成功")
          this.dialogFormVisible = false;
          this.load();
        } else {
          // 如果批量删除失败，则显示批量删除失败消息
          this.$message.error("批量删除失败")
        }
      })
    },
    // 获取所有商品类型
    getAllType() {
      // 向服务器请求所有商品类型的数据
      this.request.get("/goodsType").then(res => {
        if (res.code === 200) {
          // 如果请求成功，更新页面上的商品类型列表数据
          this.typeList = res.data;
        }
      })
    },
    // 更改商品状态是否在售的状态
    change_switch(row) {
      // 向服务器发送更新商品状态的请求
      this.request.post("/goods", row).then(res => {
        if (res.code === 200) {
          // 如果更新成功，显示成功消息，并重新加载商品数据
          this.$message.success(res.msg)
          this.load();
        } else {
          // 如果更新失败，则显示错误消息
          this.$message.error(res.msg)
        }
      })
    },
    // 保存商品信息
    save() {
      // 向服务器发送保存商品信息的请求
      this.request.post("/goods", this.form).then(res => {
        if (res.code === 200) {
          // 如果保存成功，显示成功消息，并关闭对话框，并重新加载商品数据
          this.$message.success(res.msg)
          this.dialogFormVisible = false;
          this.load();
        } else {
          // 如果保存失败，则显示错误消息
          this.$message.error(res.msg)
        }
      })
    },

    // 更新页面尺寸大小
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    // 更新当前页码重新加载对应页面数据
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load();
    },
    // 清空商品名称和商品类型ID的搜索条件，重新加载数据
    reset() {
      this.goodsName = "";
      this.typeId = "";
      this.load();
    },
    //处理添加操作
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {
        id: "",
        image: "",
        name: "",
        typeId: "",
        description: "",
        state: "",
        price: "",
        stock: ""
      };
    },
    //处理编辑操作
    handleEdit(row) {
      this.form = {
        id: "",
        image: "",
        name: "",
        typeId: "",
        description: "",
        state: "",
        price: "",
        stock: ""
      }
      Object.assign(this.form,row)
      this.dialogFormVisible = true;
    },
    //批量删除
    handleSelectionChange(val) {
      // console.log(val)
      this.multipleSelection = val;
    },
    //当头像上传成功后，将返回的上传结果赋值给表单中的图片字段（image）
    handleAvatarSuccess(res) {
      console.log(res)
      this.form.image = res;
    },
  }
}
</script>
<style scoped>
</style>
