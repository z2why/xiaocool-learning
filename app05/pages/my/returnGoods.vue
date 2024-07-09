<template>
	<view class="big">
		<view style="color: brown;font-size: 33rpx;">*退货原因</view>
		<view class="text">
			<textarea v-model="content" maxlength="200" placeholder="请详细地填写您选择退货的原因" />
			<view class="num">{{ content.length }}/200</view>
		</view>
		<view class="button">
			<button @click="pose()">提交</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				content: '',
				returnInfo:{
					id: '',
					orderFormP:1,
					orderFormDetail:''
				}
			}
		},
		methods: {
			// 返回上一个页面
			Back(){
				uni.navigateBack({
					delta:1,//返回层数，2则上上页
				})
			},
			// 发布问题
			pose(){
				this.returnInfo.orderFormDetail=this.content
				console.log(this.returnInfo.orderFormDetail,"orderFormDetail")
				this.$api('returnGoods',this.returnInfo).then(res =>{
					if(res.code==200){
						uni.showToast({
							title: "提交退货成功！",
							duration: 2000
						})
					}else{
						uni.showToast({
							title: "提交退货失败！",
							icon:'none',
							duration: 2000
						})
					}
				})
			}
		},
		onLoad(options) {
			this.returnInfo.id=options.orderId
			console.log(this.returnInfo.id,"orderId")
		}
	}
</script>

<style lang="scss">
	.big {
		margin: 20rpx;
		.text {
			margin-top: 20rpx;
			background-color: #f9f9f9;
			padding: 30rpx;
			font-size: 30rpx;
	
			/deep/ .uni-textarea-textarea {
				width: 630rpx;
				letter-spacing: 5rpx;
				line-height: 50rpx;
			}
	
			.num {
				text-align: right;
				color: gray
			}
		}
	
		.button {
			margin-top: 30rpx;
	
			button {
				background-color: #6495ED;
				color: #fff;
				font-size: 38rpx;
			}
		}
	}
</style>
