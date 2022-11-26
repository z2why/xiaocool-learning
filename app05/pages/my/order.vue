<template>
	<view class="order">
		<!-- 自定义导航 -->
	<!-- 	<cl-topbar
		
			background-color="#FFFFFF"
			color="#282828"
			:border="false"
		></cl-topbar> -->
		<cl-tabs
			v-model="active"
			:gutter="90"
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
				<view class="course" v-for="(item2, index2) in item.content" :key="index2">
					<!-- 订单标题 -->
					<view class="title">
						<view class="detail">
							<image class="icon" :src="item2.cover"  mode="aspectFill"></image>
						<!-- 订单名称 -->
							<!-- <cl-text
								:size="32"
								:value="item2.couName"
								color="#282828"
								bold
								:margin="[0, 0, 0, 16]"
							></cl-text> -->
						</view>
					<!-- 	<view class="status">
							<cl-text
								:size="28"
								
								:color="item2.statusColor"
								bold
								:margin="[0, 30, 0, 0]"
							></cl-text> -->
						<!-- </view> -->
					</view>
					<!-- 订单详情 -->
					<view class="msg">
						<cl-text
							:size="28"
							:value="`订单描述：${item2.couIntroduction}`"
							color="#979797"
							bold
							block
						></cl-text>
						<cl-text
							:size="28"
							:value="`订单编号：${item2.orderId}`"
							color="#979797"
							bold
							block
							:margin="[18, 0, 0, 0]"
						></cl-text>
						<cl-text
							:size="28"
							:value="`创建时间：${item2.orderFormDate}`"
							color="#979797"
							bold
							block
							:margin="[18, 0, 0, 0]"
						></cl-text>
					</view>
					<!-- 总金额 -->
					<cl-text
						:size="28"
						:value="`订单金额：积分${item2.couPrice}`"
						color="#2E2E2E"
						bold
						block
						:margin="[36, 0, 0, 0]"
					></cl-text>
				</view>
			</cl-tab-pane>
		</cl-tabs>
	</view>
</template>

<script>
		import api from "@/common/request/api.js"
export default {
	data() {
		return {
			thing:[],
			pageSize:20,
			pageNum:1,
			courseList: [],
			active: 0,
			orderId:'',
			list: [
				{
					title: "",
					content: [
						// {
						// 	type: "压疮的预防与护理",
						// 	name: "临床护理的一大课题",
						// 	id: "265415446545165",
						// 	time: "2021.06.17   12:56:00",
						// 	icon: require("@/static/icon/order/work.png"),
						// 	// status: "全部",
						// 	// statusColor: "#00D1A6",
						// 	money: 1,
						// },
						// {
						// 	type: "生活自理能力训练",
						// 	name: "帮助病人重新适应生活",
						// 	id: "265415446545165",
						// 	time: "2021.06.17   12:56:00",
						// 	icon: require("@/static/icon/order/education.png"),
						// 	// status: "待结算",
						// 	statusColor: "#FF9A00",
						// 	money: 1,
						// },
					],
				},
			],
		}
		},
		methods:{
			getOrder(page,num){
				console.log(this.orderId,'orderId')
				if(this.orderId==null){
					console.log("进入循环")
					this.$api('dingdan',{
						pageSize:page,
						pageNum:num,
						uId:3,
					}).then(res=>{
						console.log(res,'wwsssss')
						this.list[0].content = res.data.records
					})
				}else if(this.orderId!=0){
					this.$api('dingdan',{
						pageSize:page,
						pageNum:num,
						orderId:this.orderId
					}).then(res=>{
						console.log(res,'wwsssss')
						this.list[0].content = res.data.records
					})
				}
			}
		},
		onLoad(options){
			if(options.orderId!=''){
				this.orderId=options.orderId
			}
			this.getOrder(this.pageSize,this.pageNum)
		}
};
</script>
<style scoped lang="scss">
page {
	background: #f5f5f5;
}
.order {
	/deep/ .cl-tabs__container {
		// background-color: rgba(0, 0, 0, 0);
	}
	/deep/ .cl-tabs__bar-item.is-active {
		font-weight: bold;
	}
	padding-bottom: 50rpx;
	.course {
		margin-top: 26rpx;
		padding: 36rpx 30rpx;
		box-sizing: border-box;
		background: #ffffff;
		.title {
			display: flex;
			align-items: center;
			justify-content: space-between;
			.detail {
				// display: flex;
				// align-items: center;
				.icon {
					width: 650rpx;
					height: 400rpx;
				}
			}
			
			
		}
		.msg {
			margin-top: 36rpx;
			padding-bottom: 36rpx;
			border-bottom: 2rpx solid #f2f2f2;
		}
	}
}
</style>
