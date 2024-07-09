<template>
	<view >
		<view v-for="item in parctiveList" :key="item.id" @click="returnPractive(item.couId,item.praId)">
			<view class="box" v-if="item.audit != 3">
				<p>{{item.praName}}</p>
				<view class="content">
					<p>{{item.praMain}}</p>
				</view>
				<cl-tag  type="primary"  size="mini" v-if="item.audit === 0" plain >待审核</cl-tag>
				<cl-tag  type="success"  size="mini" v-if="item.audit === 1" plain >审核通过</cl-tag>
				<cl-tag  type="warning"  size="mini" v-if="item.audit === 2" plain >审核不通过</cl-tag>
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
			}
		},
		onLoad(){
			this.user = uni.getStorageSync("user")
		},
		created() {
			this.load()
		},
		methods: {
			load(){
				const form = {
					stuId: this.user.stuId,
					pageNum: 1,
					pageSize: 10,
				}
				this.$api('findStuPractive',form).then(res => {
					console.log(res)
					this.parctiveList = res.data.records
					console.log(this.parctiveList)
				})
			},
			//跳转到实践详情
			returnPractive(couId,praId){
				uni.navigateTo({
					url: '/pages/index/live_brief/practive?couId=' + couId + '&praId='+ praId 
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
	     background-color: #fcfcfc;
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