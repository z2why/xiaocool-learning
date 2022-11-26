<template>
	<view class="order">
		<!-- 自定义导航 -->
		<cl-topbar
			title="成员列表"
			background-color="#FFFFFF"
			color="#282828"
			:border="false"
		></cl-topbar>
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
					<!-- 成员标题 -->
					<view class="title">
						<view class="detail">
							<image class="icon" :src="item2.icon" mode="aspectFill"></image>
							<cl-text
								:size="32"
								:value="item2.name"
								color="#282828"
								bold
								:margin="[0, 0, 0, 16]"
							></cl-text>
						</view>
						<view class="status">
							<cl-text
								:size="28"
								:value="item2.status"
								:color="item2.statusColor"
								bold
								:margin="[0, 30, 0, 0]"
							></cl-text>
						</view>
					</view>
					<!-- 成员详情 -->
					<view class="msg">
						<cl-text
							:size="28"
							:value="`直属上级：${item2.name}`"
							color="#979797"
							bold
							block
						></cl-text>
						<cl-text
							:size="28"
							:value="`邀请时间：${item2.time}`"
							color="#979797"
							bold
							block
							:margin="[18, 0, 0, 0]"
						></cl-text>
					</view>
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
					title: "全部成员",
					content: [
						{
							name: "用户25425****",
							superior: "本人姓名",
							time: "2021.06.17   12:56:00",
							icon: require("@/static/images/avatar.png"),
							status: "直属成员",
							statusColor: "#00D1A6",
						},
						{
							name: "用户25425****",
							superior: "直属成员姓名",
							time: "2021.06.17   12:56:00",
							icon: require("@/static/images/avatar.png"),
							status: "非直属成员",
							statusColor: "#FF9A00",
						},
					],
				},
				{
					title: "直属成员",
					content: [
						{
							name: "用户25425****",
							superior: "直属成员姓名",
							time: "2021.06.17   12:56:00",
							icon: require("@/static/images/avatar.png"),
							status: "直属成员",
							statusColor: "#00D1A6",
						},
					],
				},
				{
					title: "非直属成员",
					content: [
						{
							name: "用户25425****",
							superior: "直属成员姓名",
							time: "2021.06.17   12:56:00",
							icon: require("@/static/images/avatar.png"),
							status: "非直属成员",
							statusColor: "#FF9A00",
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
		.title {
			display: flex;
			align-items: center;
			justify-content: space-between;
			.detail {
				display: flex;
				align-items: center;
				.icon {
					width: 60rpx;
					height: 60rpx;
				}
			}
		}
		.msg {
			margin-top: 36rpx;
		}
	}
}
</style>
