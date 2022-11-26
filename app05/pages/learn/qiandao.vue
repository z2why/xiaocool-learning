<template>
	<view class="clock-content">
		<view class="clock-top">
			<!-- 左边：头像 -->
			<view class="clock-touxiang">
				<image src="@/static/images/avatar.png" mode="aspectFit"></image>
			</view>
			<!-- 右边 -->
			<view class="clock-top-right">
				<view class="clock-top-right-text1">已签到2天</view>
				<view class="clock-top-right-text2">明日签到可得3积分</view>
			</view>
		</view>
		<view class="clock-two"></view>
		<view class="clock-three">
			<view class="clock-three-text1">签到规则</view>
			<view class="clock-three-text2">1.用户每日登录小程序进行签到，可获得积分奖励</view>
			<view class="clock-three-text2">2.每日签到可获得1积分，每连续一天，额外奖励1积分，签到每日的上限为6积分;
				如连续签到中断，则以2积分重新开始重计。</view>
			<view class="clock-three-text2">3.如连续签到一个月，则下月签到额外奖励翻倍，以此类推。</view>
			<view class="clock-three-text3">(例:用户甲10月连续签到一整月，则11月的签到积分为1积分，每连续一天，标外奖励2积分)</view>
		</view>
		<view class="clock-bottom">
			<view class="clock-font">
				<view class="clock-font-item1">
					<image class="clock-font-picture" src="@/static/images/qiandao/huankecheng.png" mode="aspectFit"></image>
				</view>
				<view class="clock-font-text">积分换课程</view>
			</view>
			<view class="clock-font">
				<view class="clock-font-item2">
					<image class="clock-font-picture" src="@/static/images/qiandao/wenti.png" mode="aspectFit"></image>
				</view>
				<view class="clock-font-text">积分换问题</view>
			</view>
			<view class="clock-font">
				<view class="clock-font-item3">
					<image class="clock-font-picture" src="@/static/images/qiandao/shebei.png" mode="aspectFit"></image>
				</view>
				<view class="clock-font-text">积分换设备</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {

		}
	}
</script>

<style lang="scss">
	page {
		background-color: #f5f3f8;
	}

	.clock-content {
		.clock-top {
			background-image: url('https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fgss0.baidu.com%2F9fo3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2F0e2442a7d933c8954b63fd81d11373f08202002e.jpg&refer=http%3A%2F%2Fgss0.baidu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1670922726&t=994fd2a27788d6766edc0e8962e6c1e7');
			background-size: 100% 100%;
			height: 400rpx;
			display: flex;
			// overflow:hidden;

			.clock-touxiang {
				width: 140rpx;
				height: 140rpx;
				border-radius: 70rpx;
				background-color: #fff;
				display: flex;
				justify-content: center;
				align-items: center;
				margin-left: 80rpx;
				margin-top: 80rpx;

				image {
					width: 130rpx;
					height: 130rpx;
				}
			}

			.clock-top-right {
				margin-top: 80rpx;
				margin-left: 40rpx;
				height: 130rpx;
				padding-top: 15rpx;

				.clock-top-right-text1 {}

				.clock-top-right-text2 {
					padding-top: 10rpx;
					font-size: 35rpx;
				}
			}
		}

		.clock-two {
			height: 200rpx;
			margin-top: -110rpx;
			width: 700rpx;
			margin-left: 20rpx;
			background-color: #ffffff;
			border-radius: 20rpx;
			margin-bottom: 40rpx;
		}

		.clock-three {
			width: 700rpx;
			margin-left: 20rpx;
			background-color: #ffffff;
			border-radius: 20rpx;
			margin-bottom: 40rpx;
			padding-top: 20rpx;
			padding-bottom: 50rpx;

			.clock-three-text1 {
				padding-bottom: 15rpx;
				margin-left: 15rpx;
			}

			.clock-three-text2 {
				padding-bottom: 15rpx;
				margin-left: 15rpx;
				margin-right: 15rpx;
				font-size: 35rpx;
			}

			.clock-three-text3 {
				margin-left: 15rpx;
				margin-right: 15rpx;
				font-size: 35rpx;
				color:#acb2b2;
			}
		}

		.clock-bottom {
			height: 250rpx;
			width: 700rpx;
			margin-left: 20rpx;
			background-color: #ffffff;
			border-radius: 20rpx;
			display: flex;
			justify-content: center;
			align-items: center;
			margin-bottom: 20rpx;

			.clock-font {
				width: 30vh;
				display: flex;
				justify-content: center;
				align-items: center;
				flex-direction: column;

				image {
					width: 100rpx;
					height: 100rpx;

				}

				.clock-font-text {
					width: 200rpx;
					height: 50rpx;
					background-color: #fcd586;
					margin-top: 20rpx;
					font-size: 35rpx;
					border-radius: 40rpx;
					display: flex;
					justify-content: center;
					align-items: center;
					color: #fff;
				}

				.clock-font-item1 {
					width: 130rpx;
					height: 130rpx;
					border-radius: 60rpx;
					background-color: #e3d0d6;
					display: flex;
					justify-content: center;
					align-items: center;


				}

				.clock-font-item2 {
					width: 130rpx;
					height: 130rpx;
					border-radius: 60rpx;
					background-color: #aaaa7f;
					display: flex;
					justify-content: center;
					align-items: center;

				}

				.clock-font-item3 {
					width: 130rpx;
					height: 130rpx;
					border-radius: 60rpx;
					background-color: #fcd586;
					display: flex;
					justify-content: center;
					align-items: center;
					

				}
			}
		}
	}
</style>
