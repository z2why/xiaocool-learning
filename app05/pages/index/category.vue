<template>
		<view>
			<view class="bground">
				 <!-- <view class="item_t1">
					  <text>今日学习时长</text>
				 </view> -->
				<view class="item_t2">
					 
				 </view>
				 <view class="item_t3" @click="goQiandao" >
					 	<!-- <button v-show="!today" type="primary" @click="toggle('center')" class="btn1">立即签到</button> -->
					 	<button v-show="today" type="primary" class="btn2">已签到</button>
				 </view>
				 
			</view>
			<!-- 进度 -->
		<view class="item_bottom" v-for="item in myCourseData" :key="item.couId" @click="courseDetail(item)">
			<view>
				<image :src="item.cover"></image>
			</view>
			<view class="item_kecheng">
				<view class="item_kecheng1">
					<text>{{item.couName}}</text>
				</view>
				<view class="item_kecheng2">
					<text>授课老师: {{item.truename}}</text>
				</view>
				<view class="item_jindu">
				       <progress percent="80" show-info="true" font-size="12"></progress>
				</view>
			</view>
		</view>
		
		<!-- 底部 -->
		<!-- <view>
			<image src="../../static/images/qiandao/huankecheng.png"></image>
			<text>积分换课程</text>
		</view> -->
	</view>
	
	
 
</template>

<script>
	import api from "@/common/request/api.js"
export default {
	data(){
		return{
			myCourseData: [],
			pageNum: 1,
			pageSize: 10,			
			user: {},//从浏览器缓存获取
		}
	},
	created(){
		
		
	},
	onLoad(){
		this.user = uni.getStorageSync("user")
		this.load()
		//console.log(this.user,'user')
	},
	methods: {	
		toggle(type) {
			this.type = type
			// open 方法传入参数 等同在 uni-popup 组件上绑定 type属性
			this.$api('signIn', this.user.stuId).then(res => {
				console.log(res)
				if (res.code == 200) {
					this.visible = true
					this.today = true
					this.showUp = !this.showUp
					this.List = res.data
					this.signInList();
					const student = {
						stuId: this.user.stuId
					}
					this.$api('findstudent', student).then(res => {
						if (res.code == 200) {
							this.user.stuScore = res.data.stuScore
							uni.setStorage({
								key: 'user',
								data: this.user,
								success: function() {
									console.log('success');
								},
							})
						}
					})
				} else {
					this.$refs["message"].open({
						message: res.msg,
						type: "error"
					});
				}
			})
		},
		load(){
			//console.log(this.user.stuId,'stuId')
			this.$api('mycourse',{
				stuId: this.user.stuId,
				have:1,
				pageSize: this.pageSize,
				pageNum: this.pageNum
			}).then(res =>{
				console.log(res,'load')
				this.myCourseData = res.data.records				
			})
		},
		//跳转到签到页面
		// goQiandao(){
		// 	uni.navigateTo({
		// 		url:'/pages/learn/qiandao'
		// 	})
		// },
		//跳转到课程详情页面
		courseDetail(item){
			//console.log(item.couId,'==>couId')
			const courseList = {
				couId: item.couId,
				couIntroduction: item.couIntroduction,
				couName: item.couName,
				cover: item.cover,
				couCollNum: item.couCollNum,
				couRecoNum: item.couRecoNum
			}
			uni.navigateTo({
				url:'/pages/index/live_brief/live_brief?category=true&courseList=' + JSON.stringify(courseList)
			})
		}
	}
}
</script>

<style lang="scss">
	.bground{
		background-image: url('@/static/images/index/beijing.png');
		background-size: 100% 100%;
	    height:400rpx;
	}
.item_t1{
	padding-top: 30rpx;
	margin-left: 40rpx;
	font-size: 40rpx;
}
.item_t2{
	margin-top: 60rpx;
	margin-left: 40rpx;
    font-size: 50rpx;
}
.item_t3{
	
		// background-color: yellow;
		border-radius: 25px;
		width: 200rpx;
		height: 100rpx;
	   margin-top: 60rpx;
	   margin-left: 60rpx;
	   font-size: 35rpx;
	   display: flex;
	   justify-content: center;
	   align-items: center;
}
.item_bottom{
	display: flex;
}
image{
	width: 300rpx;
	height: 200rpx;
	margin-left: 10rpx;
	margin-top: 10rpx;
}
.item_kecheng{
	margin-left: 20rpx;
	margin-top: 10rpx;
	.item_kecheng1{
		font-size: 30rpx;
	}
	.item_kecheng2{
		font-size: 30rpx;
		color: #ccc;
		margin-top: 10rpx;
	}
}
.item_jindu{
	width: 400rpx;
	margin-top: 100rpx;
}
</style>
