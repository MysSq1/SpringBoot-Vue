<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px;">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid"></i>用户总数</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            {{ totalUser }}
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-money"></i>交易总数</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            {{ totalTurnoverCount }}
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div> <i class="el-icon-bank-card"></i>营业总额</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            ￥{{ totalTurnover }}
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop"></i>商品总数</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            {{ totalGoods }}
          </div>
        </el-card>
      </el-col>

    </el-row>


    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 450px;"></div>
      </el-col>

      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 450px;"></div>
      </el-col>

    </el-row>




  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "AdminHome",
  data(){
    return {
      totalUser: 0,//用户总数
      totalTurnoverCount: 0,//交易总数
      totalTurnover: 0,//营业总额
      totalGoods: 0,//商品总数
    }
  },
  created() {
    //发送请求，返回后端的 用户数量， 交易总数，总交易金额，商品总数
    this.request.get("/echarts/count").then(res=>{
      if(res.code==200){
        this.totalUser = res.data[0]
        this.totalTurnoverCount = res.data[1]
        this.totalTurnover = res.data[2]
        this.totalGoods = res.data[3]
      }
    })
  },
  mounted() {
  }
}
</script>

<style scoped>

</style>
