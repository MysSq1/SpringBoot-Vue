import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';
import axios from "axios";
import './assets/gloable.css';
import request from "@/untils/request";
import global from "@/untils/global.js"
import SliderVerify from 'vue-monoplasty-slide-verify';

Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.request = request
Vue.prototype.global = global
Vue.use(SliderVerify);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
