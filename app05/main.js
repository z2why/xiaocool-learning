import Vue from "vue";
import App from "./App";
import'./style/iconfont.css';
import Page from "./components/page";

import ClUni from "cl-uni";
import'./static/iconfont/iconfont.css'
import api from 'common/request/index.js'
import store from '@/store/index.js'


Vue.use(ClUni);
Vue.component("page", Page);
Vue.prototype.$api=api;
Vue.prototype.$store=store;
Vue.config.productionTip = false;

App.mpType = "app";

const app = new Vue({
	store,
	...App,
});
app.$mount();
