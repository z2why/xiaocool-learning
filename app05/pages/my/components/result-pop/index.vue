<template>
	<view class="result-pop">
		<cl-dialog :visible.sync="visible" width="520rpx">
			<!-- 提现成功 -->
			<view class="success" v-if="status">
				<image class="bg" src="@/static/images/withdraw/success-bg.png" mode="aspectFill"></image>
				<view class="content">
					<cl-text
						:size="36"
						value="提现成功"
						color="#282828"
						bold
						block
						:margin="[50, 0, 0, 0]"
					></cl-text>
					<cl-text
						:size="24"
						value="预计会在2个工作日内到达您的账户"
						color="#B8B8B8"
						bold
						block
						:margin="[16, 0, 40, 0]"
					></cl-text>
					<view class="btn" @tap="close">确认</view>
				</view>
			</view>
			<!-- 提现失败 -->
			<view class="fail" v-else>
				<image class="bg" src="@/static/images/withdraw/fail-bg.png" mode="aspectFill"></image>
				<view class="content">
					<cl-text
						:size="36"
						value="提现失败"
						color="#282828"
						bold
						block
						:margin="[50, 0, 0, 0]"
					></cl-text>
					<cl-text
						:size="24"
						value="请联系工作人员询问失败具体原因"
						color="#B8B8B8"
						bold
						block
						:margin="[16, 0, 40, 0]"
					></cl-text>
					<view class="btn">确认</view>
				</view>
			</view>
		</cl-dialog>
	</view>
</template>

<script>
export default {
	data() {
		return {
			visible: false,//控制弹出框的显隐
            status: null,//提现结果
		};
	},
	methods: {
		// 打开
        open(data) {
            let result = data.result;//接收提现结果
            if(result) {
                this.status = true;
            } else {
                this.status = false;
            }
            this.visible = true;
        },
		//关闭
		close(){
            this.visible = false;
			this.$emit('close', )
		}
    },
};
</script>

<style scoped lang="scss">
.result-pop {
	.success {
		padding-bottom: 30rpx;
		height: 472rpx;
		.bg {
			width: 520rpx;
			height: 190rpx;
		}
		.content {
			.btn {
				margin: 0 auto;
				width: 280rpx;
				font-size: 32rpx;
				line-height: 48rpx;
				color: #ffffff;
				height: 78rpx;
				line-height: 78rpx;
				background: #80a3ff;
				border-radius: 39rpx;
			}
		}
	}
	.fail {
		padding-bottom: 30rpx;
		height: 472rpx;
		.bg {
			width: 520rpx;
			height: 190rpx;
		}
		.content {
			.btn {
				margin: 0 auto;
				width: 280rpx;
				font-size: 32rpx;
				line-height: 48rpx;
				color: #ffffff;
				height: 78rpx;
				line-height: 78rpx;
				background: #ff8080;
				border-radius: 39rpx;
			}
		}
	}
}
</style>
