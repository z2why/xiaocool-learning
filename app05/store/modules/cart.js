export default{
	state:{
		list:[],
		selectedList:[]
	},
	getters:{
		// 判断是否全选
		checkedAll(state){
			return state.list.length === state.selectedList.length;
		},
		// 合计结算
		totalCount(state){
			let total = {
				couPrice:0,
				num:0
			}
			state.list.forEach(v=>{
				// 是否选中
				if(state.selectedList.indexOf(v.couId) > -1){
					// 合计
					// total.pprice += v.pprice*v.num;
					total.couPrice += v.couPrice
					// 结算
					total.num = state.selectedList.length;
				}
			})
			return total;
		}
	},
	mutations:{
		initGetList(state,list){
			state.list = list;
		},
		// 全选
		checkAll(state){
			state.selectedList = state.list.map(v=>{
				v.checked = true;
				return v.couId;
			})
		},
		// 全不选
		unCheckAll(state){
			state.list.forEach(v=>{
				v.checked = false;
			})
			state.selectedList = [];
		},
		// 单选
		selectedItem(state,index){
			
			let couId = state.list[index].couId;
			let i = state.selectedList.indexOf(couId);
			if( i > -1){
				state.list[index].checked = false;
				return state.selectedList.splice(i,1);
			}
			
			state.list[index].checked = true;
			
			state.selectedList.push(couId);
		},
		delDoods(state){
			state.list = state.list.filter(v=>{
				return state.selectedList.indexOf(v.couId) === -1;
				
			})
		}
		
	},
	actions:{
		checkedAllFn({commit,getters}){
			getters.checkedAll  ? commit("unCheckAll") : commit("checkAll")
		},
		delDoodsFn({commit}){
			commit('delDoods');
			commit('unCheckAll');
			uni.showToast({
				title:'删除成功',
				icon:"none"
			}) 
		}
	}
}