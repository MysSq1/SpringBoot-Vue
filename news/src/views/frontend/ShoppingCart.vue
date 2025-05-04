<template>
  <div>
    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}"
        style="width: 100%;font-size: 16px"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          label="图片"
          width="170">
        <template slot-scope="scope">
          <el-image :src="scope.row.image"></el-image>
        </template>

      </el-table-column>
      <el-table-column
          prop="goodsName" label="商品名称" width="170">
      </el-table-column>

      <el-table-column
          prop="price" label="单价" show-overflow-tooltip>
      </el-table-column>

      <el-table-column prop="count" label="数量" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.count" @change="handleChange(scope.row)" :min="1" :max="10"></el-input-number>
        </template>
      </el-table-column>

      <el-table-column label="小计" show-overflow-tooltip>
        <template slot-scope="scope">
          <em class="number">￥{{ numFilter(scope.row.count * scope.row.price) }}</em>
        </template>
      </el-table-column>

      <el-table-column label="操作" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-button type="danger" @click="remove(scope.row.id)">移出购物车</el-button>
        </template>

      </el-table-column>

    </el-table>


  </div>
</template>

<script>
export default {
  name: "ShoppingCart",
  props: {
    car_selection: Object
  },
  data() {
    return {
      rowPrice: 0,
      tableData: [],
      multipleSelection: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    handleChange(row) {
      this.request.post("/shoppingCar", row).then(res => {
        if (res.code == 200) {
          this.load();
          this.$message.success("商品数量修改成功")


          this.global.totalPrice = 0;
          for (let i = 0; i < this.global.car_selection.length; i++) {
            this.global.totalPrice += this.global.car_selection[i].count * this.global.car_selection[i].price
          }
        }else{
          this.$message.error(res.msg)
        }
      })



      this.$emit("handleUpdateClick")
    },
    handleSelectionChange(val) {
      // this.multipleSelection = val;
      this.global.totalPrice = 0;
      this.global.car_selection = val;
      for (let i = 0; i < val.length; i++) {
        this.global.totalPrice += val[i].count * val[i].price
      }
      this.$emit("handleUpdateClick")
    },
    load() {
      this.request("/shoppingCar/findAllByUser").then(res => {
        if (res.code == 200) {
          this.tableData = res.data
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    numFilter(value) {
      const realVal = parseFloat(value).toFixed(2)
      return realVal;
    },
    remove(id){
      this.$confirm("确认移出购物车？").then(_=>{
        this.request.delete("/shoppingCar/" + id).then(res=>{
          if(res.code==200){
            this.$message.warning(res.data)
            this.load()
          }else{
            this.$message.error(res.msg)
          }
        })
      }).catch(_ => {});
    },

  }
}
</script>

<style scoped>
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}

.number {
  color: #f40;
  font-weight: 700;
  font-family: Verdana, Tahoma, arial;
}
</style>
