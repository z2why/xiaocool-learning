<template>
	<view class="page-my">
		<!-- 头部 -->
		<view class="header">
			<image class="bg" src="@/static/images/my/header-bg.png" mode="aspectFill"></image>
			<!-- 个人信息 -->
			<view class="info">
				<cl-avatar size="130">
					<image class="avatar" :src="user.avatar" mode="aspectFill" @tap="openChooseImg"></image>
				</cl-avatar>
				<view class="detail">

					<cl-text :size="36" :value="user.truename" color="#FFFFFF" block bold></cl-text>
					<view class="tag">{{user.actor}}</view>
				</view>
			</view>
		</view>
		<!-- 我的签到 -->
		<view class="SignIn">
			<text size="12" class="stext">我的积分</text>
			<text class="point">{{user.stuScore}}</text>
			<view class="btn">
				<button v-show="!today" type="primary" @click="toggle('center')" class="btn1">立即签到</button>
				<button v-show="today" type="primary" class="btn2">已签到</button>
				<button class="btn3" type="primary">积分商城</button>
			</view>

			<cl-dialog ref="popup" title="签到成功!" :visible.sync="visible" direction="center" width="50%">
				<view class="recom-popup">
					<text class="recom-popup-text1">您已连续签到 </text>
					<text class="recom-popup-text2" style="color: crimson;">{{List.continueDays}}天</text>

				</view>
			</cl-dialog>
		</view>


		<view class="middle-contain">
			<text class="text-day">{{newList[0].continueDays}}天</text>
			<text class="text-point">{{score}}分</text>
			<view class="view-week">
				<block v-for="(item, index) in newList" :key="index">
					<view class="block-contain">
						<view class="radio-circle" :class="[item.flag? 'background1':'background2']">
							<image class="radio-circle-img" src="../../static/iconfont/right2.png"></image>
						</view>
						<!-- <text class="week-name">{{item.weekName}}</text> -->
					</view>
				</block>
			</view>
		</view>


		<!-- 我的订单 -->
		<view class="order">
			<!-- 订单标题 -->
			<view class="title">
				<view class="left">
					<cl-text :size="32" value="我的订单" color="#292929" bold></cl-text>
				</view>
				<view class="right" @tap="lookOrder">
					<cl-text :size="24" value="查看全部" color="#8D8D8D" bold :margin="[-2, 0, 0, 0]"></cl-text>
					<cl-icon name="cl-icon-arrow-right"></cl-icon>
				</view>
			</view>
			<view class="type">
				<view class="item" v-for="(item, index) in orderList" :key="index" @click="goShopping(item.path)">
					<image :src="item.icon" mode="aspectFill"></image>
					<cl-text :size="24" :value="item.title" color="#585858" bold block></cl-text>
				</view>
			</view>
		</view>
		<!-- 其他服务 -->
		<view class="other">
			<view class="item" v-for="(item, index) in otherList" :key="index" @tap="toService(item.url)">
				<image class="mask" :src="item.icon" mode="aspectFill"></image>
				<view :class="index == otherList.length - 1 ? 'content active' : 'content'">
					<cl-text :size="32" :value="item.title" color="#373737" bold></cl-text>
					<cl-icon name="cl-icon-arrow-right"></cl-icon>
				</view>
			</view>
		</view>
		<cl-message ref="message"></cl-message>
	</view>
</template>

<script>
	import api from "@/common/request/api.js"
	export default {
		data() {
			return {
				type: 'center',
				dayNum: 0,
				List: {},
				continueDays: '',
				newList: [],
				user: '',
				today: true,
				score: 5,
				visible: false,
				orderList: [{
						title: "购物车",
						icon: require("@/static/images/my/await.png"),

						path: '/pages/my/Sorder'
					},
					{
						title: "客服",
						icon: require("@/static/images/my/service.png"),
					},
				],
				otherList: [{
						title: "收藏",
						icon: require("@/static/images/my/help.png"),
						url: "/pages/my/shoucang"

					},
					{
						title: "消息",
						icon: require("@/static/images/my/help.png"),
						url: "/pages/my/news"
					},

					{
						title: "设置",
						icon: require("@/static/images/my/help.png"),
					},
					{
						title: "退出登录",
						icon: require("@/static/images/my/help.png"),
					},
				],
			};
		},
		created() {
			this.user = uni.getStorageSync("user")
			this.signInList()

		},
		methods: {

			//跳转购物车页面
			goShopping(url) {
				uni.navigateTo({
					url
				})
			},
			//跳转提现页面
			toWithdraw() {
				uni.navigateTo({
					url: "/pages/my/take",
				});
			},
			//查看订单
			lookOrder() {
				uni.navigateTo({
					url: "/pages/my/order",
				});
			},
			//去其他服务页
			toService(url) {
				uni.navigateTo({
					url: url,
				});
			},


			//修改头像
			openChooseImg() {
				uni.chooseImage({
					count: 1, //最多可选择的张数
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album', 'camera'], //从相册选择，和摄像头功能，默认二者都有
					success: res => {
						console.log(res.tempFilePaths[0])
						uni.uploadFile({
							url: 'http://172.20.192.112:9090/service-coustype/file/upload',
							filePath: res.tempFilePaths[0],
							name: 'file',
							// formData: {
							// 	'user': 'test'
							// },
							success: res => {
								this.user.avatar = JSON.parse(res.data).data[0].url
								this.$api('reviseUserInfo', this.user).then(res => {
									if (res.code == 200) {
										this.$refs["message"].open({
											message: "修改成功！",
											type: "success"
										});
									} else {
										this.$refs["message"].open({
											message: res.msg,
											type: "error"
										});
									}

								})
								uni.setStorage({
									key: 'user',
									data: this.user,
									success: function() {
										console.log('success');
									},
								})

							}
						});

					}
				});
			},


			signInList() {
				const student = {
					stuId: this.user.stuId
				}
				this.$api('signInList', student).then(res => {
					console.log(res)
					this.newList = res.data
					console.log(this.newList)
					if (this.newList[0].todaySign != 1) {
						this.today = false
						console.log(this.today)
					}
					if (this.newList[0].continueDays >= 1) {
						this.score = 10
						console.log(this.score)
					}

				})
			},


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
			}
		}

	};
</script>

<style scoped lang="scss">
	page {
		background: #f5f5f5;
	}

	.page-my {
		padding-bottom: 50rpx;

		.header {
			position: relative;
			height: 508rpx;

			.bg {
				width: 100%;
				height: 100%;
			}

			.info {
				position: absolute;
				display: flex;
				align-items: center;
				left: 4%;
				top: 203rpx;
				padding: 0 32rpx;




				.avatar {
					width: 130rpx;
					height: 130rpx;
				}

				.detail {
					margin-left: 30rpx;

					.tag {
						margin-top: 18rpx;
						display: inline-block;
						position: relative;
						padding: 6rpx 18rpx;
						color: #ffffff;
						font-size: 28rpx;
						background: rgba(255, 255, 255, 0.3);
						border-radius: 26rpx;
					}
				}
			}
		}

		.middle-contain {
			width: 94%;
			/* height: 160px; */
			margin-top: 10px;
			margin-left: 3%;
			background-color: #fff;
			border-radius: 6px;
			display: flex;
			flex-direction: column;
			padding: 14px 0px 0px 0px;
		}

		.text-day {
			font-size: 18px;
			color: rgb(80, 107, 248);
			font-weight: bold;
		}

		.text-day::before {
			content: '已连续签到';
			font-size: 18px;
			color: rgb(57, 57, 57);
			font-weight: 500;
			margin-right: 5px;
			margin-top: 20px;
			margin-left: 20px;
		}

		.text-point {
			margin-top: 5px;
			font-size: 14px;
			color: rgb(157, 180, 255);

		}

		.text-point::before {
			content: '明日签到可获得';
			font-size: 12px;
			color: rgb(172, 172, 172);
			font-weight: 500;
			margin-right: 5px;
			margin-top: 20px;
			margin-left: 20px;
		}

		.view-week {
			margin: 20px 10px;
			display: grid;
			grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
		}

		.block-contain {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
		}

		.radio-circle {
			width: 30px;
			height: 30px;
			border-radius: 50%;
			display: flex;
			align-items: center;
			justify-content: center;

		}

		.background1 {
			background: linear-gradient(to right, rgb(48, 113, 255), rgb(116, 113, 254));
		}

		.background2 {
			background: #b6b6b6;
		}

		.radio-circle-img {
			width: 20px;
			height: 20px;
		}

		.week-name {
			margin-top: 4px;
			font-size: 12px;
			color: #7f7f7f;
		}



		.SignIn {
			position: relative;
			padding: 40rpx 40rpx;
			box-sizing: border-box;
			margin: -120rpx auto 0 auto;
			width: 690rpx;
			background: #ffffff;
			border-radius: 16rpx;

			.btn {

				position: absolute;
				right: 230rpx;
				bottom: 80rpx;

				.btn1 {
					position: relative;
					top: 61rpx;
					box-shadow: 2px 3px 8px 5px #9e9e9e45;
					background: #F7B500;
					color: #FFFFFF;
					width: 160rpx;
					height: 60rpx;
					font-size: 26rpx;
				}

				.btn2 {
					position: relative;
					top: 61rpx;
					box-shadow: 2px 3px 8px 5px #9e9e9e45;
					background: #999999;
					color: #FFFFFF;
					width: 160rpx;
					height: 60rpx;
					font-size: 26rpx;
				}

				.btn3 {

					position: relative;
					left: 180rpx;
					box-shadow: 2px 3px 8px 5px #9e9e9e45;
					background: rgb(80, 107, 248);
					color: #FFFFFF;
					width: 160rpx;
					height: 60rpx;
					font-size: 26rpx;
				}
			}

			.point {
				font-size: 30px;
				color: rgb(80, 107, 248);
				font-weight: bold;
			}

			.stext {
				color: #93928b;
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding-bottom: 30rpx;
				border-bottom: 1px solid #f5f5f5;

				.right {
					display: flex;
					padding: 4rpx 16rpx 4rpx 20rpx;
					align-items: center;
					background: #80a3ff;
					font-size: 28rpx;
					color: #ffffff;
					border-radius: 25rpx;
				}
			}

			.msg {
				display: flex;

				.item {
					margin-top: 50rpx;
					flex: 1;
					text-align: center;
				}
			}
		}

		.advice {
			margin: 0 auto;
			text-align: center;
			margin-top: 24rpx;
			width: 690rpx;
			height: 180rpx;

			image {
				width: 100%;
				height: 100%;
			}
		}

		.order {
			margin: 0 auto;
			margin-top: 24rpx;
			padding: 40rpx 30rpx;
			box-sizing: border-box;
			width: 690rpx;
			background: #ffffff;
			border-radius: 16rpx;

			.title {
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding-bottom: 30rpx;
				border-bottom: 1px solid #f5f5f5;

				.right {
					display: flex;
					align-items: center;
					font-size: 24rpx;
					color: #8d8d8d;
				}
			}

			.type {
				display: flex;
				margin-top: 60rpx;
				padding: 0 25rpx;

				.item {
					text-align: center;
					margin-right: 80rpx;

					image {
						width: 54rpx;
						height: 66rpx;
					}
				}
			}
		}

		.other {
			margin: 0 auto;
			display: flex;
			flex-direction: column;
			margin-top: 24rpx;
			width: 690rpx;
			background: #ffffff;
			border-radius: 16rpx;

			.item {
				display: flex;
				padding: 40rpx 30rpx 0 30rpx;
				box-sizing: border-box;

				.mask {
					width: 44rpx;
					height: 44rpx;
				}

				.content {
					margin-left: 8rpx;
					display: flex;
					width: 100%;
					align-items: center;
					padding-bottom: 31rpx;
					justify-content: space-between;
					border-bottom: 1px solid #f5f5f5;
				}

				.active {
					border-bottom: none;
				}
			}
		}
	}
</style>
