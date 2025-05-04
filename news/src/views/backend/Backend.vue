<template>
  <div>
    <el-container style="height: 100vh;">
      <el-aside width="200px">
        <Aside/>
      </el-aside>
      <el-container>
        <el-header style="border-bottom: 1px solid #ccc; ">
          <Top/>
        </el-header>

        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Top from "@/components/Top"
import Aside from "@/components/Aside";

export default {
  name: "Backend",
  components: {
    Aside,
    Top
  },
  data() {
    return {

    }
  },
  created() {
    this.global.isFrontend = false;
  }
}
</script>

<style scoped>

</style>
