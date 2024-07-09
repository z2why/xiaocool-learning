<template>
	<view >
		<view class="null-data" v-if="total === 0">
			<image src="@/static/null.png"></image>
			<p>该课程暂无线下实践</p>
		</view>
		<view v-else v-for="item in parctiveList" :key="item.id" @click="returnPractive(item.couId,item.praId)">
			<view class="box" >
				<p>{{item.praName}}</p>
				<view class="content">
					<p>{{item.praMain}}</p>
				</view>
				<p class="time">{{item.praTime}}</p>
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				user: {},
				parctiveList: [],
				couId: '',
				couName: '',
				total: '',
			}
		},
		onLoad(option){
			this.user = uni.getStorageSync("user")
			this.couId = option.couId
			this.couName = option.couName
			uni.setNavigationBarTitle({
				title: this.couName + '线下实践'
			})
		},
		created() {
			this.load()
		},
		methods: {
			load(){
				console.log(this.couId)
				const form = {
					couId: this.couId,
					pageNum: 1,
					pageSize: 10,
					deleted: 0,
				}
				this.$api('practive',form).then(res => {
					//console.log(res)
					this.parctiveList = res.data.records
					this.total = res.data.total
					//console.log(this.parctiveList)
					
				})
			},
			//跳转到实践详情
			returnPractive(couId,praId){
				uni.navigateTo({
					url: '/pages/index/live_brief/practive?couId=' + couId +'&praId=' + praId 
				})
			},
			
		}
	}
</script>

<style lang="scss">
	page {
	     background-color: #fcfcfc;
	 }
	 .null-data{
	 	margin-top: 200rpx;
	 	display: flex;
	 	flex-direction: column;
	 	align-items: center;
	 	image{
	 		width: 70%;
	 	}
	 	
	 }
	.box{
		padding: 30rpx;
		margin: 20rpx;
		background-color: white;
		box-shadow: 0 1px 2px rgba(0, 0, 0, .11), 0 0 4px rgba(0, 0, 0, .04);
		p{
			width: 91%;
			line-height: 60rpx;
			display:-webkit-box; 
			-webkit-box-orient:vertical; 
			-webkit-line-clamp:2; 
			overflow: hidden;
		}
		.time{
			font-size: 30rpx;
			color: #d3d3d3;
		}
		.content{
			margin: 16rpx 0;
			color: gray;
			font-size: 30rpx;
		}
		.s1{
			color: blue;
		}
		.s2{
			color: red;
		}
	}
</style>