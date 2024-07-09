<template>
	<view>
		<view>
			<image src="@/static/images/my/shuben.png"></image>
		</view>
		<view class="kecheng">
			<text>你已成功购买课程!</text>
		</view>
		
		<view class="xuexi2" >
			<text class="xuexi">立即学习</text>
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

<style>
	image{
		width: 300rpx;
		height: 300rpx;
		margin-top: 200rpx;
		margin-left: 200rpx;
		
	}
	.kecheng{
		margin-top: 50rpx;
		margin-left: 250rpx;
		
	}
	.xuexi2{
		margin-top: 80rpx;
	}
	.xuexi{
		background-color: gold;
		border-radius: 25rpx;
		
		margin-left: 300rpx;
		
	}
</style>