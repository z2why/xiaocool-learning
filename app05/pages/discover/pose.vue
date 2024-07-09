<template>
	<view class="poses">
		<view class="button" @click="poseQ">发布问题</view>
		<view class="top">输入问题并以问号结尾</view>
		<view class="tips">
			<view>
				<text class="iconfont icon-yiwen"> 让你的问题得到更多解答</text>
			</view>
			<view>· 保持文字简练，表述清晰问题的关键点</view>
			<view>· 添加合适的话题，让问题更好地沟通</view>
			<view>· 确保问题没有被提问过</view>
		</view>
		<view class="supplement">对问题补充说明，可以更快获得解答（选项）</view>
		<input placeholder="请提出您的问题" type="text" @input="onInput" :v-module='inputcontent' :value="inputcontent"/>
		<view class="classification" @click="poseclass">+分类（至少添加一个）</view>
		<view class="big" v-if="show">
			<view class="hide">
				<view class="hidetop" @click="show=false">
					<text>添加分类</text>
				</view>
				<view class="hidebottom">
					<view class="left">
						<view class="recovery">康复护理</view>
						<view class="law">法律法规</view>
						<view class="life">生活常识</view>
					</view>
					<view class="right">
						<view>基础护理</view>
						<view>功能训练治疗</view>
						<view>预防并发症</view>
						<view>心理护理</view>
						<view>健康教育</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				show:false,
				inputcontent:'',
				addQuest:{
					uid:'',
					truename:'',
					isQuestion:1,
					content:''
				}
			}
		},
		onLoad(){
			this.getInfo()
		},
		methods: {
			//从Storage取数据
			getInfo(){
				let vm=this
				uni.getStorage({     
					key:'user',
					success(res) {
						console.log('获取成功',res.data);
						vm.addQuest.uid=res.data.id
						vm.addQuest.truename=res.data.truename
					}
				})
				console.log("Q",this.addQuest)
			},
			poseclass(){
				this.show=true
			},
			//获取文本框的内容
			onInput(e){
				this.inputcontent = e.target.value
			},
			Back(){
				//uni.navigateBack()//默认delta:1
				uni.navigateBack({
					delta:1,//返回层数，2则上上页
				})
			},
			//发布问题
			poseQ(){
				this.addQuest.content=this.inputcontent
				this.$api("addQuestion",this.addQuest).then(res =>{
					if(res.code==200){
						uni.showToast({
							title:"发布问题成功！",
							duration:2000
						})
						this.Back()
						// uni.reLaunch({
						// 	url: '/pages/index/discover'
						// })
					}else{
						uni.showToast({
							title:"发布问题失败！",
							duration:2000
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.poses{
		justify-content: space-between;
		line-height: 60rpx;
		.button{
			position: fixed;
			right: 30rpx;
			bottom: 1100rpx;
			width: 200rpx;
			height: 70rpx;
			font-size: 35rpx;
			text-align: center;
			line-height: 70rpx;
			color: #7CCD7C;
		}
		.top{
			color: dimgray;
			font-size: 35rpx;
			padding: 90rpx 50rpx 20rpx 50rpx;
		}
		.tips{
			color: steelblue;
			background-color: aliceblue;
			padding: 20rpx 20rpx 20rpx 20rpx;
			margin-left: 50rpx;
			margin-right: 50rpx;
			font-size: 30rpx;
			border-radius: 10rpx;
		}
		.supplement{
			color: grey;
			padding: 20rpx 50rpx 20rpx 50rpx;
			font-size: 30rpx;
		}
		input{
			font-size: 35rpx;
			padding: 20rpx 50rpx 20rpx 50rpx;
		}
		.classification{
			position: fixed;
			bottom: 20rpx;
			font-size: 40rpx;
			color: #7CCD7C;
			left: 50rpx;
			font-size: 40rpx;
		}
		.big{
			position: fixed;
			left: 0%;
			right: 0%;
			top: 0%;
			bottom: 0%;
			background: rgba(0, 0, 0, 0.5); 
			.hide{
				width: 100%;
				font-size: 35rpx;
				height: 500rpx;
				position: fixed;
				bottom: 0rpx;
				.hidetop{
					height: 90rpx;
					text-align: center;
					line-height: 90rpx;
					border: 1px solid gainsboro;
					background-color: #fff;
				}
				.hidebottom{
					display: flex;
					.left{
						background-color: gainsboro;
						width: 30%;
						height: 400rpx;
						text-align: center;
						line-height: 60rpx;
						padding-top: 10rpx;
					}
					.right{
						width: 70%;
						height: 400rpx;
						text-align: left;
						line-height: 60rpx;
						padding-top: 10rpx;
						padding-left: 30rpx;
						background-color: #fff;
					}
				}	
			}
		}
	}
</style>