<template>
	<view>
		<view class="ordering">
			<block v-for="(item,index) in order" :key="index">
				<text :class="{activetext:index==num}" @click="menubtn(index)">{{item}}</text>
			</block>
		</view>
		<view :class="[hideing === 0 ? 'actineclass' : 'errorclass']">
			<view class="select">
				<input placeholder=" 请输入您想搜索的内容" placeholder-class="iconfont icon-sousuo" type="text" @input="onInput"
					@confirm="InfoDoSearch" :v-module='inputcontent' :value="inputcontent" />
			</view>
			<view class="infomations_item" @click="navigator(item.infoId)" v-for="(item,infoId) in infomationsList"
				:key="infoId">
				<view class="title">{{item.infoTitle}}</view>
				<view class="author">{{item.infoAuthor}}</view>
				<view class="main">
					<u-parse v-if="item.infoMain" :content="item.infoMain"></u-parse>
				</view>
				<view class="info">
					<text class="iconfont icon-dianzan1">{{item.infoLike}}点赞</text>
					<text>{{item.infoData}}</text>
				</view>
			</view>
		</view>
		<view :class="[hideing === 1 ? 'actineclass' : 'errorclass']">
			<view class="select">
				<input placeholder=" 请输入您想搜索的内容" placeholder-class="iconfont icon-sousuo" type="text" @input="onInput"
					@confirm="doSearch" :v-module='inputcontent' :value="inputcontent" />
			</view>
			<view class="questions-item" @click="goDetail(item.tqId)" v-for="item in questionsList" :key="item.tqId">
				<view class="left">
					<view class="author">提问者：{{item.truename}}</view>
					<view class="main">{{item.content}}</view>
					<view class="info">
						<text>{{item.createTime}}</text>
					</view>
				</view>
			</view>
			<view class="addquestion iconfont icon-jia" @click="gopose"></view>
		</view>
	</view>
</template>
<script>
	import uParse from "../../components/u-parse/u-parse.vue"
	import api from "@/common/request/api.js"
	export default {
		data() {
			return {
				num: 0,
				hideing: 0,
				order: ['资讯', '提问'],
				// 资讯
				infomationsList: [],
				content: '',
				inputcontent: '',
				selectInfo: {
					pageSize: 50,
					pageNum: 1,
					infoTitle: '',
					infoAuthor: ''
				},
				// 问答
				questionsList: [],
				inputcontent: '',
				questInfo: {
					pageSize: 50,
					pageNum: 1,
					isQuestion: 1
				},
				selectQuest: {
					pageSize: 50,
					pageNum: 1,
					isQuestion: 1,
					truename: '',
					content: ''
				}
			}
		},
		created() {
			this.infomations()
			this.questionInfo()
		},
		onShow(){
			this.infomations()
			this.questionInfo()
		},
		methods: {
			menubtn(index) {
				this.num = index
				this.hideing = index
				console.log(index,"index")
				if(index==0){
					console.log(index,"进入循环")
					this.inputcontent=''
					this.selectInfo.infoAuthor = ''
					this.selectInfo.infoTitle = ''
					this.infomations()
				}else if(index==1){
					this.inputcontent=''
					this.questionInfo()
				}
			},

			// 资讯
			//获取文本框的内容
			onInput(e) {
				this.inputcontent = e.target.value
			},
			//按下回车键搜索
			InfoDoSearch() {
				if (this.inputcontent == '') {
					this.selectInfo.infoAuthor = ''
					this.selectInfo.infoTitle = ''
					this.infomations()
				} else if (this.inputcontent != '') {
					this.selectInfo.infoAuthor = ''
					this.selectInfo.infoTitle = this.inputcontent
					this.$api('information', this.selectInfo).then(res => {
						// console.log(res)
						if (res.code == 200) {
							// console.log("获取到数据了")
							this.infomationsList = res.data.records
							if (this.infomationsList.length == 0) {
								this.selectInfo.infoTitle = ''
								this.selectInfo.infoAuthor = this.inputcontent
								this.$api('information', this.selectInfo).then(res => {
									// console.log(res)
									if (res.code == 200) {
										// console.log("获取到数据了")
										this.infomationsList = res.data.records
										if (this.infomationsList.length == 0) {
											uni.showToast({
												title: "查询结果为空！",
												duration: 2000
											})
										}
									} else {
										uni.showToast({
											title: '搜索失败！',
											icon: 'none',
											duration: 2000
										})
									}
								})
							}
						} else {
							uni.showToast({
								title: '搜索失败！',
								icon: 'none',
								duration: 2000
							})
						}
					})
				}
			},
			navigator(infoId) {
				console.log(infoId, "info页面跳转到infoS")
				uni.navigateTo({
					url: '/pages/discover/infomationS?infoId=' + infoId
				})
			},
			//获取所有的资讯信息
			infomations() {
				this.$api('information', this.selectInfo).then(res => {
					// console.log(res)
					if (res.code == 200) {
						// console.log("获取到数据了")
						this.infomationsList = res.data.records
					}
				})
			},

			// 问答
			//获取文本框的内容
			onInput(e) {
				this.inputcontent = e.target.value
			},
			//按下回车键搜索
			doSearch() {
				if (this.inputcontent == '') {
					this.selectQuest.truename = ''
					this.selectQuest.content = ''
					this.questionInfo()
				} else if (this.inputcontent != '') {
					this.selectQuest.truename = ''
					this.selectQuest.content = this.inputcontent
					this.$api('questionInfo', this.selectQuest).then(res => {
						// console.log(res)
						if (res.code == 200) {
							console.log("获取到数据了")
							this.questionsList = res.data.records
							if (this.questionsList.length == 0) {
								this.selectQuest.content = ''
								this.selectQuest.truename = this.inputcontent
								this.$api('questionInfo', this.selectQuest).then(res => {
									// console.log(res)
									if (res.code == 200) {
										// console.log("获取到数据了")
										this.questionsList = res.data.records
										if (this.questionsList.length == 0) {
											uni.showToast({
												title: "查询结果为空！",
												duration: 2000
											})
										}
									} else {
										uni.showToast({
											title: '搜索失败！',
											icon: 'none',
											duration: 2000
										})
									}
								})
							}
						} else {
							uni.showToast({
								title: '搜索失败！',
								icon: 'none',
								duration: 2000
							})
						}
					})
				}
			},
			goDetail(tqId) {
				uni.navigateTo({
					url: '/pages/discover/questionS?tqId=' + tqId
				})
			},
			gopose() {
				uni.navigateTo({
					url: "/pages/discover/poseQuestion"
				})
			},
			questionInfo() {
				this.$api('questionInfo', this.questInfo).then(res => {
					if (res.code == 200) {
						this.questionsList = res.data.records
					}
				})
			}
		},
		components: {
			uParse
		},
	}
</script>

<style lang="scss">
	@import url("@/components/u-parse/u-parse.css");

	.ordering {
		margin: 20rpx;
		margin-bottom: 0rpx;
		display: flex;

		text {
			margin-right: 60rpx;
			display: block;
			height: 60rpx;
			line-height: 60rpx;
			font-size: 33rpx;
			width: 100rpx;
			text-align: center;
		}

		.activetext {
			color: #6495ED;
			font-weight: bold;
			border-bottom: 5rpx solid #6495ED;
		}
	}

	.actineclass {
		display: block;
	}

	.errorclass {
		display: none;
	}

	// 资讯
	.select {
		padding-top: 30rpx;

		input {
			background-color: gainsboro;
			border-radius: 30rpx;
			width: 90%;
			height: 80rpx;
			font-size: 35rpx;
			margin: auto;
			border: 1px solid gainsboro;
			text-align: center;
			line-height: 90rpx;
		}
	}

	.infomations_item {
		padding: 20rpx 20rpx;
		border-bottom: 2px solid gainsboro;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		line-height: 60rpx;

		.title {
			font-size: 40rpx;
			font-weight: bold;
		}

		.author {
			font-size: 35rpx;
			color: gray;
		}

		.main {
			font-size: 33rpx;
			overflow: hidden;
			-webkit-line-clamp: 2;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			text-overflow: ellipsis;
		}

		.info {
			color: gray;
			font-size: 35rpx;

			text:nth-child(2) {
				margin-left: 30rpx;
			}
		}
	}

	// 问答
	.select {
		input {
			background-color: gainsboro;
			border-radius: 30rpx;
			width: 90%;
			height: 80rpx;
			font-size: 35rpx;
			margin: auto;
			border: 1px solid gainsboro;
			text-align: center;
			line-height: 90rpx;
		}
	}

	.questions-item {
		padding: 20rpx 20rpx;
		border-bottom: 2px solid gainsboro;

		.left {
			flex-direction: column;
			margin-right: 30rpx;
			justify-content: space-between;
			line-height: 60rpx;

			.author {
				font-size: 35rpx;
				font-weight: bold;
			}

			.main {
				font-size: 35rpx;
				overflow: hidden;
				-webkit-line-clamp: 2;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				text-overflow: ellipsis;
			}

			.info {
				font-size: 35rpx;
				color: gray;
			}
		}
	}

	.addquestion {
		background-color: #6495ED;
		position: fixed;
		bottom: 200rpx;
		right: 70rpx;
		width: 100rpx;
		height: 100rpx;
		border-radius: 50%;
		font-size: 100rpx;
		text-align: center;
		line-height: 100rpx;
		color: #fff;
	}
</style>
