import Vue from 'vue'
import VueRouter from 'vue-router'

import  Login from "@/views/Login.vue";
import Index from "@/views/frontend/Index";
import Home from "@/views/frontend/Home";
import AdminHome from "@/views/backend/AdminHome";
import PersonCenter from "@/views/frontend/PersonCenter";
import Backend from "@/views/backend/Backend";
import Register from "@/views/Register";
import ShoppingCart from "@/views/frontend/ShoppingCart";
import ShoppingList from "@/views/frontend/ShoppingList";
import ChangePassword from "@/views/frontend/ChangePassword";
import Wallet from "@/views/frontend/Wallet";
import UserManage from "@/views/backend/UserManage";
import GoodsManage from "@/views/backend/GoodsManage";
import request from "@/untils/request";
import 'element-ui/lib/theme-chalk/index.css';
import TypeManage from "@/views/backend/TypeManage";
import ElementUI from "element-ui";

Vue.use(VueRouter)

const routes = [
  {
    path:'/login',
    name:'Login',
    component:Login
  },

  //前台
  {
    //使用了 meta 对象来定义路由的元信息
    path: '/',
    name: 'Index',
    component: Index,
    meta: {
      title: '前台'
    },
    redirect: "/home",
    children: [
      {
        path: '/home',
        name: 'Home',
        component: Home,
        meta: {
          title: '主页'
        }
      },
      {
        path: '/personCenter',
        name: 'PersonCenter',
        component: PersonCenter,
        meta: {
          title: '个人中心'
        }
      },
      {
        path: '/shoppingCart',
        name: 'ShoppingCart',
        component: ShoppingCart,
        meta: {
          title: '购物车'
        }
      },
      {
        path: '/shoppingList',
        name: 'ShoppingList',
        component: ShoppingList,
        meta: {
          title: '消费记录'
        }
      },
      {
        path: '/changePassword',
        name: 'ChangePassword',
        component: ChangePassword,
        meta: {
          title: '修改密码'
        }
      },

      {
        path: '/wallet',
        name: 'Wallet',
        component: Wallet,
        meta: {
          title: '充值'
        }
      }
    ]
  },
  //后台
  {
    path: '/backend',
    name: '',
    component: Backend,
    redirect: "/backend/adminHome",
    meta: {
      title: '后台管理'
    },
    children: [
      {
        path: '/backend/adminHome',
        name: 'AdminHome',
        component: AdminHome,
        meta: {
          title: '主页'
        },
      },
      {
        path: '/backend/userManage',
        name: 'UserManage',
        component: UserManage,
        meta: {
          title: '用户管理'
        },
      },
      {
        path: '/backend/typeManage',
        name: 'TypeManage',
        component: TypeManage,
        meta: {
          title: '类别管理'
        },
      },
      {
        path: '/backend/goodsManage',
        name: 'GoodsManage',
        component: GoodsManage,
        meta: {
          title: '商品管理'
        },
      },

    ]
  },
  //登录
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  //注册
  {
    path: '/register',
    name: 'Register',
    component: Register,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes


})

router.beforeEach(function (to, from, next) {
  if( to.path === '/register' ){
    return next()
  }

  if (!localStorage.getItem("user")) {
    if (to.path !== '/login' ) {

      ElementUI.Message.error("请登录")
      return next('/login')
    }
  }

  if (to.matched[0].path == '/backend') {
    request.get("/role/powerCheck").then(res => {
      // console.log(res.data)
      if (res.data != 1) {
        ElementUI.Message.error("权限不足")
        return next('/')
      }
    })
  }

  next()
})
export default router
