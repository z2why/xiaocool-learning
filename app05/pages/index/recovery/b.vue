<template>
	<view class="LessonDetails-content">
		<!-- 视频 -->
		<view class="LessonDetails-top">
			<image :src="courseList.cover"></image>
		</view>
		<!-- 点赞转发等 -->
		<view class="LessonDetails-dianzan">
			<view class="LessonDetails-name">{{courseList.couName}}</view>
			<view class="dianzan-shoucang-zhuanfa">
				<image class="dianzan" src="@/static/images/index/dianzan.png" mode="aspectFit">
					{{courseList.couRecoNum}}
				</image>
				<image class="shoucang" src="@/static/images/index/shoucang.png" mode="aspectFit">
					{{courseList.couCollNum}}
				</image>
				<image class="fenxiang" src="@/static/images/index/fenxiang.png" mode="aspectFit">51</image>
			</view>
		</view>
		<!-- 课程简介、目录、评价 -->
		<view class="LessonDetails-jianjie">
			<view class="LessonDetails-jianjie-top">
				<view class="LessonDetails-jianjie-top-item" v-for="(item,index) in list" :key="index"
					:class="[active===index?'addnav':'']" @click="cutNav(index,item.navid)">
					{{item.navname}}
				</view>

			</view>
			<!-- <view class="LessonDetails-jianjie-bottom"></view> -->
			<view class="bigbox" v-if="navMain == 0">
				<view class="Course-introduction">
					<view class='Course-introduction-name'>课程简介:</view>
					<view class="Course-introduction-content">{{courseList.couIntroduction}}</view>
				</view>
			</view>
			<view class="bigbox" v-if="navMain == 1">
				目录
				<ul v-for="item in chapter" :key="item.chaId">
					<li>第{{item.chaIndex}}章 {{item.chaTitle}}</li>
				</ul>
			</view>

			<!-- 评价 -->
			<view class="bigbox consult" v-else-if="navMain == 2">
				<view v-if="courseConsultList.length === 0" class="imgNull" style="width:100%">
					<img style="width: 100%;height:620rpx" src="../../../static/comment.png"></img>
				</view>
				<view class="consult-content" v-else v-for="item in courseConsultList" :key="item.id">
					<view class="imgView">
						<image :src="item.avatar" mode=""></image>
					</view>
					<view class="textView">
						<text class="name">{{item.truename}}</text>
						<text class="text">{{item.couAppraise}}</text>
					</view>
				</view>
			</view>
		</view>
		<!-- 新增评价 -->
		<button v-if="navMain == 2 && category == 'true'" class="addComment">+</button>
		<!-- 最下边加入购物车 -->
		<view v-if="!category" class="LessonDetails-bottom">

		</view>

		<!-- 购物车 -->

		<view v-if="category === 'flase'" class="uni-container">
			<view class="goods-carts">
				<uni-goods-nav :options="options" :fill="true" :button-group="buttonGroup" @click="onClick"
				@tap="addCart" @buttonClick="buttonClick" />
			</view>

		</view>
	</view>
</template>

<script>
	import api from "@/common/request/api.js"
	export default {

		components: {},
		data() {
			return {
				category: 'flase', //category=true 表示是从category跳转过来的
				chapter: {}, //章节
				courseList: {},
				pageNum: 1,
				pageSize: 10,
				courseConsultList: {},
				user: {},
				newList: [
					'1'
				],
				options: [{
					icon: 'chat',
					text: '客服'
				}, 
				// {
				// 	icon: 'shop',
				// 	text: '店铺',
				// 	info: 2,
				// 	infoBackgroundColor: '#007aff',
				// 	infoColor: "#f5f5f5"
				// },
				{
					icon: 'cart',
					text: '购物车',
					url: 'pages/my/Sorder',
					// path:'pages/my/Sorder',
					info: 2
				}],
				buttonGroup: [{
						text: '加入购物车',
						backgroundColor: 'linear-gradient(90deg, #FFCD1E, #FF8A18)',
						color: '#fff'
					},
					{
						text: '立即购买',
						backgroundColor: 'linear-gradient(90deg, #FE6035, #EF1224)',
						color: '#fff'
					}
				],
				text: '',
				active: 0,
				navMain: 0,
				secondDate: [],
				list: [{
						value: 1,
						navname: '简介',
						navid: 0,
						store: []
					},
					{
						value: 2,
						navname: '目录',
						navid: 1,
						store: []
					},
					{
						value: 3,
						navname: '评价',
						navid: 2,
						store: []
					},

				],


			}
		},

		created() {
			this.user = uni.getStorageSync("user")
		},
		onLoad(option) {
			this.category = option.category
			this.courseList = JSON.parse(option.courseList)
			//console.log(JSON.parse(option.courseList),'====>option.courseList')
			//console.log(this.couId,'======>option.courseId')
			console.log(this.category, 'category')
			console.log(this.courseList.couId, 'couId')
			this.pingjia()
		},
		methods: {
			onClick(e) {
				let vm = this
				uni.showToast({
					title: `点击${e.content.text}`,
					icon: 'none',
				})
				console.log(text, "text")
			},
			buttonClick(e) {
				console.log(e)
				this.options[2].info++
			},
			cutNav(index, id) {
				this.active = index
				this.navMain = id
				const res = this.list[id].store
				this.secondDate = res
			},
			//评价
			pingjia() {
				console.log(this.courseList.couId, 'this.courseList.couId')
				this.$api('pingjia', {
					couId: this.courseList.couId,
					pageNum: this.pageNum,
					pageSize: this.pageSize
				}).then(res => {
					//console.log(res, 'res')
					this.courseConsultList = res.data.records
					console.log(this.courseConsultList, 'this.courseConsultList')
				})

				//章节
				this.$api('coursechapter', {
					courseId: this.courseList.couId
				}).then(res => {
					this.chapter = res.data
					console.log(this.chapter, 'chapter')
				})
			},
			// 跳转到购物车
			// goSorder() {
			// 	uni.switchTab({
			// 		url: 'pages/my/Sorder'
			// 	})
			// },
			//加入购物车
			addCart() {

				this.$api('addShopping', {
					stuId: this.user.stuId,
					couId: this.courseList.couId,
				}).then(res => {
					if (res.code === '200') {
						uni.showToast({
							title: '加入购物车成功！'
						})
					} else if (res.code === '600') {
						uni.showToast({
							title: res.msg
						})
					} else {
						uni.showToast({
							title: '加入购物车失败！'
						})
					}
				})
			}
		}

	}
</script>

<style lang="scss">
	.LessonDetails-content {
		background-color: #f5f3f8;

		.LessonDetails-top {
			width: 100%;
			height: 350rpx;
			// margin-top: 10rpx;
			background-color: #e8e8e8;
			display: flex;
			justify-content: center;
			align-items: center;

			uni-image {
				width: 100%;
			}

			image {
				width: 100%rpx;
				height: 350rpx;

			}
		}

		.LessonDetails-dianzan {
			padding-left: 10rpx;
			padding-right: 10rpx;
			width: 100%;
			height: 150rpx;
			background-color: #ffffff;
			margin-top: 10rpx;
			margin-bottom: 15rpx;

			.LessonDetails-name {
				padding-top: 10rpx;
				padding-bottom: 20rpx;

			}

			.dianzan-shoucang-zhuanfa {
				// display: flex;
				font-size: 30rpx;

				image {
					width: 50rpx;
					height: 50rpx;
					padding-right: 10rpx;
				}

				.shoucang {
					padding-left: 40rpx;
					padding-top: 20rpx;
				}

				.fenxiang {
					padding-left: 350rpx;
				}
			}
		}

		.LessonDetails-jianjie {

			.LessonDetails-jianjie-top {
				width: 100%;
				height: 80rpx;
				display: flex;
				justify-content: center;
				align-items: center;
				background-color: #ffffff;

				.LessonDetails-jianjie-top-item {
					width: 33vh;
					height: 80rpx;
					text-align: center;
					padding-top: 10rpx;
				}

				.LessonDetails-jianjie-top-item::before {
					position: absolute;
					content: '|';
					top: 5rpx;
					color: #eee;
					right: 0;
				}

				.LessonDetails-jianjie-top-item:last-child::before {
					position: absolute;
					content: '|';
					top: 5rpx;
					color: #fff;
					right: 0;
				}

				.addnav {
					border-bottom: 4rpx solid #48c680;
					color: #31b26a;
				}

				.active {
					color: #aace9e;
					border-bottom: 4rpx solid #aace9e;
				}
			}
		}

		.Course-introduction {
			background-color: #ffffff;
			margin-bottom: 10rpx;
			padding-left: 10rpx;
			padding-right: 10rpx;

			.Course-introduction-name {
				margin-top: 10rpx;
				margin-bottom: 10rpx;
				letter-spacing: 8rpx;
			}

			.Course-introduction-content {
				padding-bottom: 30rpx;
				line-height: 60rpx;
				letter-spacing: 8rpx;
			}
		}

		.develop {
			background-color: #ffffff;
			margin-bottom: 10rpx;
			padding-left: 10rpx;
			padding-right: 10rpx;

			.develop-name {
				margin-top: 10rpx;
				margin-bottom: 10rpx;

			}

			.develop-content {
				padding-bottom: 30rpx;

			}
		}

		.Course {
			background-color: #ffffff;
			padding-left: 10rpx;
			padding-right: 10rpx;

			.Course-name {
				margin-top: 10rpx;
				margin-bottom: 10rpx;
			}

			.Course-content {
				display: flex;
				padding-bottom: 50rpx;

				.Course-content-left {
					width: 300rpx;
					height: 150rpx;
					background-color: #e8e8e8;
					display: flex;
					justify-content: center;
					align-items: center;

					image {
						width: 50rpx;
						height: 50rpx;
					}
				}

				.Course-content-right {
					padding-left: 10rpx;

					.Course-content-right-name {
						padding-bottom: 10rpx;
					}

					.Course-content-right-details {
						font-size: 35rpx;
						color: #acb2b2;
					}

				}
			}

		}

	}

	.bigbox {
		height: 650rpx;
		margin-bottom: 5px;

		ul {
			margin: 0;
			padding: 0;
			list-style-type: none;

			li {
				padding: 20rpx 20rpx 20rpx 100rpx;
				background-color: white;
			}
		}

	}

	.consult {
		display: flex;
		flex-direction: column;
		width: 100%;
		margin-top: 10rpx;

		.consult-content {
			border-bottom: 1rpx solid #f0f0f0;
			padding: 20rpx;
			display: flex;
			justify-content: flex-start;
			background-color: white;


			.imgView {
				width: 100rpx;
				height: 100rpx;
				padding-right: 20rpx;

				image {
					width: 100%;
					height: 100%;
					border-radius: 50%;
				}
			}
		}

		.textView {
			display: flex;
			flex-direction: column;

			text {
				height: 50rpx;
			}

			.name {
				color: #d58e00;
			}

			.text {
				font-size: 22rpx;
				color: #4f4f4f;
			}
		}
	}

	.uni-container {
		position: fixed;
		bottom: 0;
		width: 100%
	}

	.addComment {
		width: 100rpx;
		height: 100rpx;
		background-color: white;
		border-radius: 50%;
		font-size: 50rpx;
		line-height: 100rpx;
		box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
		position: fixed;
		bottom: 100rpx;
		right: 100rpx;
		color: #ababab;
	}

	.addComment:hover {
		box-shadow: 0 4px 8px rgba(0, 85, 255, 0.1), 0 0 12px rgba(0, 85, 255, 0.1);
		color: #4a4a4a;
	}
</style>
