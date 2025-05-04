<template>
  <div>
    <div style="width: 80%;margin: auto;display: flex">
      <div style="margin: auto;display: flex">
        <input type="text" v-model="goodsName" placeholder="输入要搜索的内容" id="inp">
        <el-button id="btn" style="margin-left: 5px;" @click="load(-1)">搜索</el-button>
      </div>
    </div>

    <div style="margin-top: 20px" v-if="pictureList&&pictureList.length > 0">
      <div style="width:90%;margin:0 auto;padding-bottom:0;">
        <el-carousel :interval="5000" arrow="always" indicator-position="none" @change="changeCampus">
          <el-carousel-item v-for="(img, index) in pictureList" :key="index">
            <el-image :class="current===index ? 'campus-active': 'campus' " :src="img.url"
                      style="cursor:pointer;position:absolute;top:0;bottom:0;left:0;right:0;width:100%;margin:auto;"
                      fit="contain"

                      @click="carouselClick(img.name)"></el-image>
          </el-carousel-item>
        </el-carousel>
      </div>
    </div>

    <el-divider></el-divider>

    <div style="width: 80%;margin: auto;display: flex">
      <h3 class="sort_title">商品种类</h3>
      <el-select v-model="select_value" placeholder="请选择" style="margin-left: 10px" @change="load">
        <el-option
            v-for="item in typeList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>

      <el-radio-group v-model="radio" @change="radio_get" size="small" style="margin-left: 10px">
        <el-radio-button label="最新"></el-radio-button>
        <el-radio-button label="销量"></el-radio-button>
        <el-radio-button label="价格">价格<i class="el-icon-arrow-down" size="small" style="margin-left: 5px"></i></el-radio-button>
        <el-radio-button label="重置"></el-radio-button>
      </el-radio-group>

    </div>
    <!--    <el-divider></el-divider>-->
    <div class="parent" style="">
      <el-card class="child" v-for="item in goodsList">
        <el-image :src="item.image" style="width: 100%;height: 192px"></el-image>
        <h3 class="title">
          {{ item.name }}
        </h3>
        <p class="desc">{{ item.description }}</p>
        <div>
          <span class="price-value"><em style="margin-right: 5px">¥</em>{{ item.price }}</span>
          <el-button type="warning" class="btn" @click="openDrawer(item)">查看详情</el-button>
        </div>
      </el-card>
    </div>

    <div style="padding: 10px 0;">
      <el-pagination style="margin: auto"
                     @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="pageNum"
                     :page-sizes="[12, 16, 20, 24]"
                     :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total">
      </el-pagination>
    </div>

    <el-drawer
        title="商品详情"
        :visible.sync="drawer"
        :direction="'rtl'"
        :destroy-on-close="true"
        size="900px"
    >
      <div style="display: flex;">
        <div style="flex:1;">
          <el-image :src="drawerForm.image" style="width: 350px; float: right;margin-right: 10px"></el-image>
        </div>
        <div style="margin-left: 10px;flex:1;">
          <div style="width: 400px;">
            <h2 class="drawer_title">{{ drawerForm.name }}</h2>
          </div>
          <div style="width: 400px;">
            <p>{{ drawerForm.description }}</p>
          </div>
          <hr style="width: 380px;margin: 10px">

          <div style="display: flex;padding: 5px">
            <strong class="price_font">
              <em class="">¥</em>
              <em>{{ numFilter(drawerForm.price) }}</em>
            </strong>
            <div style="margin-left: 50px;margin-top: 5px">
              <el-input-number @change="handleChange(drawerForm.price)" v-model="num" :min="1"
                               :max="10"></el-input-number>
            </div>
          </div>

          <div class="total_price">
            总计：{{ numFilter(totalPrice) }}元
          </div>
          <el-button type="warning" class="addCar_btn" @click="addCar">加入购物车</el-button>

        </div>
      </div>
    </el-drawer>


  </div>
</template>

<script>

export default {
  name: "Home",
  data() {
    return {
      drawer: false,
      select_value: "",
      typeList: [],
      radio: "",
      goodsName: "",
      pageNum: 1,
      pageSize: 12,
      total: 0,

      num: 1,
      totalPrice: 0,
      current: 0,
      pictureList: [
        {url: "http://localhost:8089/news-server/file/ad999f3d1370444c8945c6d5ca7cedb3.png",name: "Xiaomi 14"},
        {url: "http://localhost:8089/news-server/file/bd04248273a842f58f442ecae3434a50.png",name: "小米平板5 Pro 12.4"},
        {url: "http://localhost:8089/news-server/file/f562ff49fdde4b6c88f65b3739968ce6.png",name: "Redmi G Pro 游戏本 i9"},
        {url: "http://localhost:8089/news-server/file/9840caad10bf4f38a272843292089f30.jpg",name: "米家声波电动牙刷T700"}
      ],//存放图片的数组
      goodsList: [],
      drawerForm: {
      },
    }
  },
  created() {
    this.load();
    this.getGoodType();
  },
  methods: {
    radio_get(){
      if( this.radio =="重置" ){
        this.goodsName ="";
        this.select_value ="";
        this.load();
      }
      if( this.radio =="最新" ){
        this.load(1);
      }
      if( this.radio =="销量" ){
        this.load(2);
      }
      if( this.radio =="价格" ){
        this.load(3);
      }
    },

     /* 从服务器加载符合特定条件的商品信息
     * @param {object} condition - 符合请求条件的对象*/
    load(condition) {
      this.request.get("/goods/pageByUser", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.goodsName,
          typeId: this.select_value,
          condition: condition
        }
      }).then(res => {
        // 将获取到的商品记录存储到goodsList中，并更新总数total
        this.goodsList = res.data.records;
        this.total = res.data.total;
      })
    },

    //获取商品类型
    getGoodType() {
      this.request.get("/goodsType").then(res => {
        if (res.code == 200) {
          this.typeList = res.data;
        }
      })
    },
    //向购物车添加商品
    addCar(row){
      this.request.post("/shoppingCar/addCarHome",{
        goodsId: this.drawerForm.id,
        goodsType: this.drawerForm.typeId,
        price: this.drawerForm.price,
        count: this.num,
        image: this.drawerForm.image,
        goodsName: this.drawerForm.name
      }).then(res=>{
        if(res.code==200){
          // 添加成功时显示成功消息并关闭
          this.$message.success(res.data)
          this.drawer = false;
        }else{
          // 添加失败时显示错误消息
          this.$message.error(res.msg)
        }
      })
    },
    //点击主页上方的轮播图片，根据商品名称获取商品信息
    carouselClick(name){
      // console.log(name)
      this.request.get("/goods/getByName/" + name).then(res=>{
        if(res.code==200){
          this.openDrawer(res.data)
        }
      })
    },

    handleChange(price) {
      this.totalPrice = this.numFilter((this.num * price));
    },
    changeCampus(val) {
      this.current = val
    },
    openDrawer(item) {
      this.drawerForm = item
      this.num = 1
      this.totalPrice = this.numFilter(this.num * item.price);
      this.drawer = true
    },
    handleSizeChange(pageSize) {
      // console.log(pageSize)
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      // console.log(pageNum)
      this.pageNum = pageNum
      this.load();
    },

    numFilter(value){
      const realVal = parseFloat(value).toFixed(2)
      return realVal;
    },
  }
}
</script>

<style scoped>
.campus {
  opacity: 0;
}

.campus-active {
  opacity: 1;
}

.parent {
  width: 100%;
  padding: 5px;
  margin-top: 30px;
  display: flex;
  align-content: flex-start;
  flex-wrap: wrap;
  justify-content: center;
  /*justify-content:space-around;*/
}

.child {
  width: 234px;
  height: 300px;
  /*border: red solid;*/
  margin: 5px;
}

.title {
  margin: 0 10px 2px;
  font-size: 16px;
  font-weight: 400;
  color: #333;
  text-align: center;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;

}

.desc {
  text-align: center;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  font-size: 12px;
  color: #b0b0b0;
}

.price-value {
  margin-top: 10px;
  display: inline-block;
  font-size: 22px;
  line-height: 22px;
  color: #ff5000;
}

.btn {
  float: right;
  margin-top: 8px;
  color: #FFFFFF;
  background-color: #ff6700;
}

.drawer_title {
  text-align: center;
  font-size: 24px;
  font-weight: 400;
  color: #212121;
  display: block;
  margin-block-start: 0.83em;
  margin-block-end: 0.83em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
}

.price_font {
  /*font-style: normal;*/
  /*display: inline;*/
  /*line-height: 26px;*/
  /*margin-right: 5px;*/
  /*vertical-align: baseline;*/
  font-size: 28px;
  font-weight: 720;
  /*font-family: Tahoma,Arial,Helvetica,sans-serif;*/
  color: #F40;
}

.total_price {
  color: #ff6700;
  font-size: 24px;
  padding-top: 20px;
}

.addCar_btn {
  width: 298px;
  height: 52px;
  line-height: 52px;
  font-size: 16px;
  background: #ff6700;
  border-color: #ff6700;
  color: #fff;

  display: inline-block;

  padding: 0;
  margin-top: 20px;

  text-align: center;

  cursor: pointer;
  -webkit-transition: all .4s;
  transition: all .4s;
}

.tags span {
  display: inline-block;
  padding: 6px 12px;
}

#inp {
  width: 620px;
  height: 48px;
  padding-left: 12px;
  outline: none;
  border: #f35 solid;


}

#btn {
  width: 80px;
  height: 48px;
  color: white;
  background: #f35;
  border: none;
}

.sort_title {
  font-weight: 400;
  color: #666;
  padding-left: 20px;
  width: 98px;
}
</style>
