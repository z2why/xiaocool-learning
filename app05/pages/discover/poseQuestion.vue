<template>
	<view class="big">
		<view class="text">
			<textarea v-model="content" maxlength="200" placeholder="请详细的表述您的问题 并以问号结尾" />
			<view class="num">{{ content.length }}/200</view>
		</view>
		<view class="button">
			<button @click="pose()">发布问题</button>
		</view>
	</view>
</template>

<script>
	import api from "@/common/request/api.js"
		export default{
			data(){
				return{
					content: '',
					couId: '',
					user: {},
					addQuest:{
						uid:'',
						truename:'',
						isQuestion:1,
						content:''
					}
				}
			},
			onLoad(){
				this.user = uni.getStorageSync("user")
				console.log(this.user,"user")
			},
			methods:{
				// 返回上一个页面
				Back(){
					uni.navigateBack({
						delta:1,//返回层数，2则上上页
					})
				},
				// 发布问题
				pose(){
					console.log(this.content[this.content.length-1]+"text")
						
					if(this.content[this.content.length-1]=="？"||this.content[this.content.length-1]=="?"){
						this.addQuest.uid=this.user.id
						this.addQuest.truename=this.user.truename
						this.addQuest.content=this.content
						this.$api("addQuestion",this.addQuest).then(res =>{
							if(res.code==200){
								uni.showToast({
									title:"发布问题成功！",
									duration:2000
								})
								this.Back()
							}else{
								uni.showToast({
									title:"发布问题失败！",
									duration:2000
								})
							}
						})
					}else{
						uni.showToast({
							title:"请以问号为结尾",
							duration:2000
						})
					}
				}
			}
		}
</script>

<style lang="scss">
	.big {
		margin: 20rpx;
		.text {
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
				border-radius: 20rpx;
			}
		}
	}
</style>
