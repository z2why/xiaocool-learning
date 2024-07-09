<template>
	<view>
		<!-- 搜索款 -->
		<view class="search-box">
			<cl-input class="inp" prefix-icon="cl-icon-search" v-model="courseName" @clear="load" @confirm="load()" clearable>主要</cl-input>
			<button class="btn" @click="load">搜索</button>
		</view>
		
		<!-- 课程 -->
		<view class="box" v-for="item in courselist" :key="item.couId" @click="courseDetail(item.couId)">
			<!-- 课程封面 -->
			<view class="imgView">
				<image :src="item.cover"></image>
			</view>
			<!-- 课程内容 -->
			<view class="content">
				<p>{{item.couName}}</p>
				<p class="course-introduction">{{item.couIntroduction}}</p>
				<p>
					积分：<span>{{item.couPrice}}</span>
					<image src="@/static/iconfont/jifen.png" mode="aspectFit">
						</imgge>
				</p>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				couType: '',
				courselist: [],
				courseName: '',//搜索框
			}
		},
		onLoad(option) {
			this.couType = option.couType
		},
		created() {
			this.load()
		},
		methods: {
			load() {
				const form = {
					couType: this.couType,
					couName: this.courseName,
					pageNum: 1,
					pageSize: 999,
					deleted: 0,
				}
				this.$api('course', form).then(res => {
					console.log(res, '课程分类')
					this.courselist = res.data.records
				})
			},
			//跳转到课程详情页面
			courseDetail(couId) {
				uni.navigateTo({
					url: '/pages/index/live_brief/live_brief?couId=' + couId
				})
			},
			

		}
	}
</script>

<style lang="scss">
	.search-box {
		width: 90%;
		height: 70rpx;
		margin: 20rpx auto;
		border: 5rpx solid #00a8cc;
		border-radius: 50rpx;
		display: flex;
		justify-content: space-around;

		.inp{
			width: 480rpx;
			height: 70rpx;
			border-radius: 30rpx;
			padding-left: 20px;
			border: none;
			margin-left: 2rpx;
		}
		.btn {
			background-color: #00a8cc;
			color: white;
			text-align: center;
			font-size: 35rpx;
			width: 200rpx;
			height: 70rpx;
			line-height: 70rpx;
			border-radius: 30rpx;
		}
	}

	.box {
		height: 200rpx;
		border-bottom: 1px solid #f0f0f0;
		padding: 20rpx;
		display: flex;

		.imgView {
			width: 40%;
			height: 200rpx;

			image {
				width: 100%;
				height: 100%;
			}

		}

		.content {
			width: 55%;
			height: 200rpx;
			margin-left: 20rpx;

			p {
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 2;
				overflow: hidden;
				line-height: 50rpx;

				image {
					width: 34rpx;
					height: 34rpx;
					position: relative;
					top: 6rpx;
					left: 10rpx;
				}

				span {
					color: #E6A23C;
					font-size: 40rpx;
				}
			}

			.course-introduction {
				font-size: 26rpx;
				color: #c4c4c4;
				line-height: 40rpx;
				margin: 10rpx 0;
			}
		}
	}
</style>
