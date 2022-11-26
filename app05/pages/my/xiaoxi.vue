<template>
	<view class="order">
		<cl-tabs
			v-model="active"
			:gutter="70"
			:swipeable="false"
			un-color="#A7A7A7"
			color="#80A3FF"
		>
			<cl-tab-pane
				:label="item.title"
				:refresher-enabled="false"
				:name="index"
				v-for="(item, index) in list"
				:key="index"
			>
				<view class="person" v-for="(item2, index2) in item.content" :key="index2">
		        <text>我的消息我的消息我的消息我的消息我的消息我的消息我的消息我的消息我的消息</text>
				</view>
			</cl-tab-pane>
		</cl-tabs>
	</view>
</template>

<script>
export default {
	data() {
		return {
			active: 0,
			list: [
				{
					title: "回答",
					content: [
						{
							
							
						},
						{
							
						},
					],
				},
				{
					title: "提问",
					content: [
						{
						
						},
					],
				},
				{
					title: "回复",
					content: [
						{
						
						},
					],
				},
			],
		};
	},
};
</script>

<style scoped lang="scss">
page {
	background: #f5f5f5;
}
.order {
	/deep/ .cl-tabs__container {
		background-color: rgba(0, 0, 0, 0);
	}
	/deep/ .cl-tabs__bar-item.is-active {
		font-weight: bold;
	}
	padding-bottom: 50rpx;
	.person {
		margin-top: 26rpx;
		padding: 36rpx 30rpx;
		box-sizing: border-box;
		background: #ffffff;
		// .title {
		// 	display: flex;
		// 	align-items: center;
		// 	justify-content: space-between;
		// 	.detail {
		// 		display: flex;
		// 		align-items: center;
		// 		.icon {
		// 			width: 60rpx;
		// 			height: 60rpx;
		// 		}
		// 	}
		// }
		// .msg {
		// 	margin-top: 36rpx;
		// }
	}
}
</style>
