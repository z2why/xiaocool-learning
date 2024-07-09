import Vue from 'vue';
import App from './App.vue';
//全局引入
import ElementUI from 'element-ui';
//按需引入
// import {Row, Button} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router';
import store from './store';
import request from "@/utils/request";
import Cookie from 'js-cookie';
import vueMiniPlayer from 'vue-mini-player'
import 'vue-mini-player/lib/vue-mini-player.css'
Vue.use(vueMiniPlayer)

Vue.config.productionTip = false;

//全局引入
Vue.use(ElementUI,{size:"mini"});
//按需引入
// Vue.use(Row)
// Vue.use(BUtton)

Vue.prototype.request = request;


//添加全局前置导航守卫
router.beforeEach((to, from, next) => {
   //判断token存不存在
    //const token = Cookie.get('token')
    //token不存在。说明当前用户是未登录，应该跳转到登录页面
    //console.log(token,"token")
    const user = JSON.parse(localStorage.getItem('user')) 
    //console.log(user)
    if(!user && to.name !== 'login'){
      next({name: 'login'})
    }else if(user && to.name === 'login'){//token存在，说明用户已经登录，此时应跳转至首页
      next({name : 'classes'})
    }else{
      next()
    }
  
})

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
