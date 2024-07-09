import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

import cart from '@/store/modules/cart.js'
const store = new Vuex.Store({
	modules:{
		cart
	},
	state: {
		
		serverUrl: 'http://localhost:9090/', //服务器地址
		systemInfo:null,
		authorizeState: true, //是否授权
		config:null,
		token:null,
		userInfo:null,
		showLoginTip: false,
	}
	,
	mutations: {
		LOGIN_TIP(state, data) {
			state.showLoginTip = data
		},
	},
	
	actions: {
		
	}
})

export default store
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
