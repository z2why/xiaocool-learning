<template>
	<view class="infomation">
		<view class="infomationMain" v-for="item in infomation" :key="item.infoId">
			<view class="title">{{item.infoTitle}}</view>
			<view class="top">
				<image :src="item.avatar"></image>
				<text>{{item.infoAuthor}}</text>
			</view>
			<view class="content">
				<u-parse v-if="item.infoMain" :content="item.infoMain"></u-parse>
			</view>
			<view class="data">{{item.infoData}}</view>
			<view class="info">
				<text class="iconfont icon-dianzan" @click="onLike" id="iLike"
					:class="[hideing === 0 ? 'actineclass' : 'errorclass']">{{item.infoLike}}点赞</text>
				<text class="iconfont icon-dianzan1" @click="onLike" id="iLike"
					:class="[hideing === 1 ? 'actineclass' : 'errorclass']">{{item.infoLike}}点赞</text>
				<text class="iconfont icon-fenxiang" @click="shareToggle">分享</text>
			</view>
		</view>
		<view class=" comments">
			<view class="ctitel">观点</view>
			<view class="comments_item" v-for="item in commentlist" :key="item.cid">
				<image :src="item.avatar"></image>
				<view class="right">
					<view class="cname">
						<text>{{item.truename}}</text>
						<text>{{item.cDate}}</text>
					</view>
					<view class="comment">{{item.ccontent}}</view>
					<view class="delete" @click="deleteCom(item.cid)" v-if="item.uid == addCom.uid">删除</view>
				</view>
			</view>
		</view>
		<view class="comment-bottom">
			<input placeholder="欢迎发表你的观点" type="text" @input="onInput" :v-module='inputcontent' :value="inputcontent" />
			<view @click="send" class="postbutton">发表</view>
		</view>
		<view>
			<!-- 分享示例 -->
			<uni-popup ref="share" type="share" safeArea backgroundColor="#fff">
				<uni-popup-share></uni-popup-share>
			</uni-popup>
		</view>
		<view>
			<!-- 输入框示例 -->
			<uni-popup ref="inputDialog" type="dialog">
				<uni-popup-dialog ref="inputClose" title="您确定要删除这条评论吗？" @confirm="dialogInputConfirm">
				</uni-popup-dialog>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				vIf: 'flase',
				inputcontent: '',
				hideing: 0,
				addCom: {
					infoId: '',
					uid: '',
					ccontent: '',
					role: 1
				},
				info: {
					infoId: '',
					pageSize: 1,
					pageNum: 1
				},
				infoCom: {
					infoId: '',
					pageSize: 20,
					pageNum: 1
				},
				likeNum: '',
				content: '',
				// imgArr: []
				infomation: {},
				commentlist: [],
				deleteC: {
					cId: ''
				},
				dianLike: {
					infoId: '',
					infoLike: ''
				}
			}
		},
		// created() {
		// 	this.informationPage()
		// 	this.infoComment()
		// },
		methods: {
			//从Storage取数据
			getInfo() {
				let vm = this
				uni.getStorage({
					key: 'user',
					success(res) {
						console.log('获取成功', res.data);
						console.log("id", res.data.id)
						vm.addCom.uid = res.data.id
						if (res.data.actor == "学生") {
							vm.addCom.role = 1
						} else if (res.data.actor == "老师") {
							vm.addCom.role = 0
						}
					}
				})
				console.log("add", this.addCom)
			},
			//分享
			shareToggle() {
				this.$refs.share.open()
			},
			//获取文本框的内容
			onInput(e) {
				this.inputcontent = e.target.value
			},
			//点击确认按钮
			send() {
				if (this.inputcontent == '') {
					uni.showToast({
						title: '请输入您的观点！',
						icon: 'none',
						duration: 2000
					})
				} else if (this.inputcontent != '') {
					this.addCom.ccontent = this.inputcontent
					// console.log(this.addCom)
					this.$api('addComment', this.addCom).then(res => {
						if (res.code == 200) {
							uni.showToast({
								title: "评论成功",
								duration: 2000
							})
							this.infoComment()
						} else {
							uni.showToast({
								title: "评论失败",
								icon: 'none',
								duration: 2000
							})
						}
					})
					this.inputcontent = ''
				}
			},
			//根据infoId获取资讯信息
			informationPage() {
				this.$api('informationPage', this.info).then(res => {
					// console.log(res)
					if (res.code == 200) {
						// console.log("获取到数据了")
						this.infomation = res.data.records
						console.log(this.infomation, "资讯信息")
						this.likeNum = res.data.records[0].infoLike
						this.dianLike.infoId = res.data.records[0].infoId
						if (res.data.records[0].infoLike == null) {
							this.dianLike.infoLike = 0
						} else if (res.data.records[0].infoLike != null) {
							this.dianLike.infoLike = res.data.records[0].infoLike
						}
						// console.log(this.dianLike, "dianLike")
					}
				})
			},
			infoComment() {
				this.$api('infoComment', this.infoCom).then(res => {
					// console.log(res, "Com")
					if (res.code == 200) {
						this.commentlist = res.data.records
						// console.log(this.commentlist,"评论信息")
					}
				})
			},
			deleteCom(cId) {
				// console.log(cId, "cId")
				this.deleteC.cId = cId
				this.$refs.inputDialog.open()
			},
			dialogInputConfirm(val) {
				this.$api('deleteCom', this.deleteC).then(res => {
					if (res.code == 200) {
						uni.showToast({
							title: "删除评论成功！",
							duration: 2000
						})
						this.infoComment()
					} else {
						uni.showToast({
							title: "删除评论失败！",
							icon: 'none',
							duration: 2000
						})
						this.infoComment()
					}
				})
			},
			onLike() {
				if (this.hideing == 0) {
					this.dianLike.infoLike = parseInt(this.dianLike.infoLike) + 1
					// console.log(this.dianLike.infoLike)
					this.$api('addinfoLike', this.dianLike).then(res => {
						if (res.code == 200) {
							this.hideing = 1
							this.informationPage()
						} else {
							uni.showToast({
								title: "点赞失败！",
								icon: 'none',
								duration: 2000
							})
						}
					})
				} else if (this.hideing == 1) {
					this.dianLike.infoLike = parseInt(this.dianLike.infoLike) - 1
					// console.log(this.dianLike.infoLike)
					this.$api('addinfoLike', this.dianLike).then(res => {
						if (res.code == 200) {
							this.hideing = 0
							this.informationPage()
						} else {
							uni.showToast({
								title: "取消点赞失败！",
								icon: 'none',
								duration: 2000
							})
						}
					})
				}
			}
		},
		onShow() {
			// console.log("onShow")
		},
		onLoad(options) {
			console.log("informationSonLoad")
			// console.log(options.infoId,"infoId")
			this.info.infoId = options.infoId
			this.infoCom.infoId = options.infoId
			this.addCom.infoId = options.infoId
			this.informationPage()
			this.infoComment()
			this.getInfo()
		}
	}
</script>

<style lang="scss">
	.infomation {
		line-height: 60rpx;
		padding-bottom: 120rpx;

		image {
			min-width: 100rpx;
			max-width: 100rpx;
			height: 100rpx;
			border-radius: 50%;
		}

		.infomationMain {
			padding: 20rpx 20rpx 20rpx 20rpx;
			flex-direction: column;

			.title {
				font-size: 40rpx;
				font-weight: bold;
				padding-bottom: 20rpx;
			}

			.top {
				line-height: 100rpx;
				padding-bottom: 15rpx;
				font-size: 35rpx;
				font-weight: bold;
				display: flex;

				image,
				text {
					align-items: center;
				}

				text {
					margin-left: 15rpx;
				}
			}

			.content {
				font-size: 33rpx;
				padding-bottom: 15rpx;
			}

			.data {
				font-size: 35rpx;
				color: gray;
				padding-bottom: 15rpx;
			}

			.info {
				font-size: 35rpx;

				.actineclass {
					display: block;
				}

				.errorclass {
					display: none;
				}
			}
		}

		.comments {
			padding: 20rpx 20rpx 20rpx 20rpx;

			.ctitel {
				padding-top: 30rpx;
				font-weight: bold;
				padding-bottom: 15rpx;
				font-size: 40rpx;
				border-bottom: 1px solid gainsboro;
			}

			.comments_item {
				border-bottom: 1px solid gainsboro;
				display: flex;
				padding-bottom: 15rpx;
				font-size: 35rpx;
				padding-top: 20rpx;

				.right {
					margin-left: 15rpx;

					.cname {
						text:nth-child(1) {
							font-weight: bold;
						}

						text:nth-child(2) {
							margin-left: 100rpx;
							font-size: 35rpx;
							color: gray;
						}

						text:nth-child(3) {
							margin-left: 100rpx;
							font-size: 35rpx;
							color: gray;
						}
					}

					.delete {
						color: firebrick;
						font-size: 30rpx;
					}
				}
			}
		}

		.comment-bottom {
			border-top: 1px solid gainsboro;
			padding: 20rpx 20rpx 20rpx 20rpx;
			width: 100%;
			position: fixed;
			bottom: 0;
			background-color: #fff;
			height: 90rpx;
			font-size: 35rpx;
			display: flex;

			input {
				background-color: lightgrey;
				border-radius: 10rpx;
				width: 500rpx;
				height: 90rpx;
				padding-left: 10rpx;
				padding-right: 10rpx;
			}

			.postbutton {
				flex-direction: column;
				width: 150rpx;
				height: 90rpx;
				background-color: #6495ED;
				color: #fff;
				border-radius: 10rpx;
				text-align: center;
				line-height: 90rpx;
				margin-left: 30rpx;
			}
		}
	}
</style>
