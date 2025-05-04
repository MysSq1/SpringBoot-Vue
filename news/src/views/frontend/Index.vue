<template>
  <el-container style="height: 100vh;">
    <el-aside width="200px">
      <Aside/>
    </el-aside>
    <el-container>
      <el-header style="border-bottom: 1px solid #ccc;">
        <Top/>
      </el-header>
      <el-main>
        <router-view @handleUpdateClick="handleUpdateClick" />
      </el-main>
      <el-footer v-show="$route.path=='/shoppingCart'" style="border-top: #b0b0b0 solid 1px;height: 60px;width: 100%">
        <shopp-car-bottom :key="key"/>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
import Top from "@/components/Top"
import Aside from "@/components/Aside";
import router from "@/router";
import ShoppCarBottom from "@/components/ShoppCarBottom";
export default {
  name: "Index",
  components: {
    ShoppCarBottom,
    Aside,
    Top
  },
  created() {
  },
  data(){
    return {
      key: 1,
    }
  },
  methods:{
    handleUpdateClick(param) {
      this.key += 1
      // console.log(param)
    },
  }
}
</script>
<style scoped>
</style>
