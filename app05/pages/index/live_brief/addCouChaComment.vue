<template>
	<view class="box">
		<view class="text">
			<textarea v-model="comment" maxlength="100"  placeholder="请填写100字以内课程评价，以便我们更好的帮助您解决问题，提高课程质量" />
			<view class="num">{{ comment.length }}/100</view>
		</view>
		<cl-button type="primary" fill @click="addcomment">发布</cl-button>
	</view>
</template>

<script>
	import api from "@/common/request/api.js"
	export default{
		data(){
			return{
				comment: '',
				couId: '',
				user: {},
				chaId:''
			}
		},
		onLoad(option) {
			this.chaId = option.chaId,
			this.couId = option.couId,
			console.log(this.chaId)
			console.log(this.couId)
			this.user = uni.getStorageSync("user")
		},
		methods: {
			addcomment(){
				//console.log(this.couId,this.user.stuId)
				if(this.comment.length<1){
					uni.showToast({
						icon:"error",
					    title:"请勿提交空值！"
					})
				}
				else{
					
				const form = {
					chaId: this.chaId,
					couId:this.couId,
					stuId: this.user.stuId,
					chaAppraise: this.comment
				}
				this.$api('saveChaApp',form).then(res => {
					console.log(res)
					if(res.code==200){
						uni.showToast({
							title:"发布成功！",
							duration:2000
						})
						// this.Back()
					}else{
						uni.showToast({
							title:"发布失败！",
							duration:2000
						})
					}
				})
				// // 跳转到课程详情页面
				// uni.navigateTo({
				// 	url:'/pages/index/live_brief/chapter?couId=' + this.couId+'&chaId='+this.chaId
				// })
			}}
		}
	}
</script>

<style  lang="scss">
	.box{
		padding: 30rpx;
		.text{		
			background-color: #f9f9f9;
			padding: 30rpx;
			font-size: 30rpx;
			/deep/ .uni-textarea-textarea{
				width: 630rpx;
				letter-spacing: 5rpx;
				line-height: 50rpx;
			}
			.num{
					text-align: right;
					color: gray
				}
		}
	
		.cl-button{
			margin-top: 30rpx;
		}
	}
</style>