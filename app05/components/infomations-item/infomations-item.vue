<template>
	<view>
		<view class="infomations_item" @click="navigator(item.info_id)" v-for="item in list" :key="item.info_id">
			<view class="title">{{item.info_title}}</view>
			<view class="author">{{item.info_author}}</view>
			<view class="main">{{item.info_main}}</view>
			<view class="info">
				<text>{{item.info_like}}点赞</text>
				<text>{{item.comment_num}}评论</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: ['list'],
		methods: {
			navigator(info_id) {
				this.$emit('itemClick',info_id,)
			}
		}
	}
</script>

<style lang="scss">
	.infomations_item{
		padding: 20rpx 20rpx;
		border-bottom: 3px solid gainsboro;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		line-height: 45rpx;
		.title{
			font-size: 35rpx;
			font-weight: bold;
		}
		.author{
			font-size: 25rpx;
			color: gray;
		}
		.main{
			font-size: 30rpx;
			overflow: hidden;
			-webkit-line-clamp: 2;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			text-overflow: ellipsis;
		}
		.info{
			font-size: 25rpx;
			color: gray;
			text:nth-child(2){
				margin-left: 30rpx;
			}
		}
	}
</style>