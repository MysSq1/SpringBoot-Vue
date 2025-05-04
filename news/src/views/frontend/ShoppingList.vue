<template>
  <div>
    <!-- 卡片试图-->
    <el-card style="margin-top:20px">
      <el-row :gutter="30">
        <el-col :span="8">
          <el-input placeholder="请输入订单编号"  v-model="uuid" clearable >
            <el-button slot="append" @click="load" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
      </el-row>
      <!--订单列表-->
      <el-table :data="dataList" border stripe  style="margin-top:20px">
        <el-table-column width="50" label="序号" prop="id"></el-table-column>
        <el-table-column label="订单编号"  prop="uuid"></el-table-column>
        <el-table-column label="商品名称"  prop="goodsName"></el-table-column>
        <el-table-column label="购买数量" prop="count"></el-table-column>
        <el-table-column label="商品单价" prop="price"></el-table-column>
        <el-table-column label="订单总计" >
          <template slot-scope="scope">
            {{scope.row.price * scope.row.count}}
          </template>
        </el-table-column>
        <el-table-column label="购买时间" prop="createTime"></el-table-column>
      </el-table>
    </el-card>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>
<script>
export default {
  name: "ShoppingList",
  data() {
    return {
      pageNum: 1,
      total: 0,
      pageSize: 10,
      dataList: [],
      uuid: "",
    }
  },
  created() {
    this.load()
  },

  methods: {
     // 加载用户的购物列表数据
     // 根据用户唯一标识符（uuid）向服务器请求购物列表的分页数据
     // 包括pageNum：请求页数，pageSize：每页数据条数，uuid：用户唯一标识符
     // 如果请求成功（状态码为200），则更新页面上的购物列表数据和总数
     // 如果请求失败，则显示错误消息
    load(){
      this.request.get("/shoppingList/pageByUser", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          uuid: this.uuid,
        }
      }).then(res => {
        if(res.code == 200){
          // 如果请求成功，更新页面上的购物列表数据和总数
          this.dataList = res.data.records;
          this.total = res.data.total;
        } else {
          // 如果请求失败，则显示错误消息
          this.$message.error(res.msg)
        }
      })
    },

    // load(){
    //   this.request.get("/shoppingList/pageByUser", {
    //     params: {
    //       pageNum: this.pageNum,
    //       pageSize: this.pageSize,
    //       uuid: this.uuid,
    //     }
    //   }).then(res => {
    //     if(res.code==200){
    //       this.dataList = res.data.records;
    //       this.total = res.data.total;
    //     }else{
    //       this.$message.error(res.msg)
    //     }
    //   })
    // },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load();
    },
  },
}
</script>
<style scoped>
.mod-comm {
  padding: 10px 20px 20px;
}
</style>
