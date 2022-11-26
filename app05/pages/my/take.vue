<template>
	<view class="take">
		<!-- 头部 -->
		<view class="header">
			<view class="back">
				<!-- 自定义导航 -->
				<cl-topbar  :title="null" background-color="rgba(0,0,0,0)" :border="false" color="#ffffff">
				</cl-topbar>
			</view>
			<!-- 返回键 -->
			<!-- <image
				class="back"
				src="@/static/images/withdraw/back.png"
				mode="aspectFill"
				@tap="back"
			></image> -->
			<image class="bg" src="@/static/images/withdraw/bg.png" mode="aspectFill"></image>
		</view>
		<!-- 可提现金额 -->
		<view class="money">
			<view class="left">
				<cl-text :size="32" value="可提现金额" color="#292929" bold block></cl-text>
				<cl-text :size="32" value="￥" color="#292929" bold></cl-text>
				<cl-text :size="80" :value="balance" color="#292929" bold></cl-text>
			</view>
			<view class="right">
				<cl-text :size="28" value="提现规则" color="#292929" bold></cl-text>
			</view>
		</view>
		<!-- 提现 -->
		<view class="take">
			<view class="title">
				<cl-text :size="32" value="提现金额" color="#292929" bold block></cl-text>
			</view>
			<!-- 提现输入框 -->
			<view class="content">
				<cl-input
					placeholder="请输入要提现金额"
					:border="true"
					:fill="true"
					clearable
					v-model="moneyNum"
				>
					<view slot="append" @tap="takeAll">
						<cl-text :size="28" value="全部提现" color="#80A3FF" bold></cl-text>
					</view>
				</cl-input>
			</view>
			<!-- 提现按钮 -->
			<view class="btn" @tap="withdraw">申请提现</view>
			<view class="tip">
				<cl-text
					:size="24"
					value="满1元即可提现"
					color="#B7B7B7"
					bold
					block
					:margin="[24, 0, 0, 0]"
				></cl-text>
			</view>
		</view>
		<!-- 提现结果弹出框 -->
		<view class="result">
			<result-pop ref="result-pop" @close="close"></result-pop>
		</view>
	</view>
</template>

<script>
import ResultPop from "./components/result-pop";
export default {
	components: {
		ResultPop,
	},
	data() {
		return {
			moneyNum: "", //要提现的金额
			balance: 3600, //可提现金额
			result: true, //审核是否通过
		};
	},
	methods: {
		//返回
		back() {
			uni.switchTab({
				url: "/pages/index/my",
			});
		},
		//全部提现
		takeAll() {
			this.moneyNum = this.balance;
		},
		//申请提现
		withdraw() {
			if (!this.moneyNum) {
				uni.showToast({
					title: "提现金额不能为空",
					icon: "none",
				});
				return false;
			}
			if (this.moneyNum < 1) {
				uni.showToast({
					title: "提现金额不能小于1元",
					icon: "none",
				});
				return false;
			}
			this.$refs["result-pop"].open({
				result: this.result,
			});
		},
		//关闭弹窗
		close() {
			this.moneyNum = "";
		},
	},
};
</script>

<style scoped lang="scss">
page {
	background: #f5f5f5;
}
.take {
	/deep/ .cl-input {
		height: 100%;
		background: #f5f5f5;
	}
	/deep/ .uni-input-placeholder {
		color: #b8b8b8;
	}

	.header {
		position: relative;
		height: 306rpx;

		.bg {
			width: 100%;
			height: 100%;
		}
		.back {
			position: absolute;
			// z-index: 2;
			left: 0rpx;
			top: 0rpx;
			width: 100%;
		}
	}
	.money {
		display: flex;
		align-items: center;
		justify-content: space-between;
		position: relative;
		margin: -101rpx auto 0 auto;
		padding: 50rpx 40rpx;
		box-sizing: border-box;
		width: 690rpx;
		background: #e8eefe;
		border-radius: 16rpx;
	}
	.take {
		margin: 0 auto;
		margin-top: 24rpx;
		padding: 40rpx 30rpx;
		box-sizing: border-box;
		width: 690rpx;
		background: #ffffff;
		border-radius: 16rpx;
		.title {
			padding-bottom: 30rpx;
			border-bottom: 1px solid #f5f5f5;
		}
		.content {
			margin-top: 50rpx;
			width: 630rpx;
			height: 100rpx;
		}
		.btn {
			margin: 0 auto;
			margin-top: 80rpx;
			width: 258rpx;
			padding: 16rpx 56rpx;
			text-align: center;
			box-sizing: border-box;
			font-size: 32rpx;
			color: #ffffff;
			background: #80a3ff;
			border-radius: 39rpx;
		}
		.tip {
			text-align: center;
		}
	}
	.result {
		/deep/ .cl-dialog__wrapper .cl-dialog__container {
			padding: 0;
		}
	}
}
</style>
