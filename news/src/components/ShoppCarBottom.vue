<template>

  <div style="padding: 5px;font-size: 20px;" >

    <el-row>
      <el-col :span="2">
        <div style="margin-top: 8px">
          <el-button type="danger" @click="removeBatch">批量移出</el-button>
        </div>
      </el-col>
      <el-col :span="20" style="display: flex">
        <div style="width: 40%;margin: 8px auto;text-align: center">
          已选<span class="sumStyle">{{global.car_selection.length}}</span>件商品

        </div>

        <div style="margin-top: 8px">
          合计<span class="sumStyle">￥{{numFilter(global.totalPrice) }}</span>
        </div>

      </el-col>

      <el-col :span="2">
        <div style="float: right;">
          <el-button @click="settle" type="danger" class="btn">结算</el-button>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  name: "ShoppCarBottom",
  data(){
    return {

    }
  },
  created() {

  },
  methods: {
    //实现将商品批量移除购物车
    removeBatch() {
      if (this.global.car_selection.length === 0) {
        this.$message.error("请至少选中一件商品！");
      } else {
        this.$confirm("确认批量移出购物车？").then(_ => {
          // 提取多个商品数据的ID组成数组
          let ids = this.global.car_selection.map(v => v.id) //[{},{},{}] => [1,2,3]
          // 向服务器发送批量移出购物车的请求，根据商品ID数组进行操作
          this.request.post("/shoppingCar/del/batch", ids).then(res => {
            if (res.code === 200) {
              // 如果批量移出购物车成功，刷新页面并显示批量删除成功消息
              location.reload();
              this.$message.warning("批量删除成功");
            } else {
              // 如果批量移出购物车失败，则显示批量删除成功消息
              this.$message.error("批量删除成功");
            }
          });
        }).catch(_ => {});
      }
    },

    //结算购物车
    settle() {
      if (this.global.car_selection.length === 0) {
        this.$message.error("请至少选中一件商品！");
      } else {
        this.$confirm("确定是否结算购物车？", "提示", {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 向服务器发送购物车结算的请求，传递选中的商品数据
          this.request.post('/shoppingCar/settle', this.global.car_selection).then(res => {
            if (res.code === 200) {
              // 如果购物车结算成功，刷新页面并显示结算成功消息
              this.$message.success(res.data);
              location.reload();
            } else {
              // 如果购物车结算失败，则显示结算失败消息
              this.$message.error(res.msg);
            }
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消结算'
          });
        });
      }
    },

    numFilter(value) {
      // 将传入的值转换为浮点数，并保留小数点后两位
      const realVal = parseFloat(value).toFixed(2);
      return realVal;
    }

  }
}
</script>

<style scoped>
.sumStyle{
  font-weight: 700;
  font-size: 22px;
  padding: 0 3px;
  color: #f40;
}
.btn{
  text-align: center;
  font-family: 'Lantinghei SC','Microsoft Yahei';
  display: inline-block;
  width: 74px;
  height: 42px;
  color: #fff;
  background: #FF5000;
  -webkit-border-radius: 21px;
  -moz-border-radius: 21px;
  -ms-border-radius: 21px;
  border-radius: 21px;
  font-size: 16px;
  text-decoration: none;
  cursor: pointer;
  margin-top: 5px;
}
</style>
