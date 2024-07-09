<template>
	<view class="order">
		<!-- 自定义导航 -->
		<!-- 	<cl-topbar
		
			background-color="#FFFFFF"
			color="#282828"
			:border="false"
		></cl-topbar> -->
		<!-- <cl-tabs v-model="active" :gutter="90" :swipeable="false" un-color="#A7A7A7" color="#80A3FF"> -->
		<view :label="item.title" :refresher-enabled="false" :name="index" v-for="(item, index) in list" :key="index">
			<view class="orderhhh" v-for="item3 in orderIdList">
				<view class="padd">
					<view class="orderId">订单编号：{{item3.orderId}}</view>
					<view class="course" v-for="(item2, index2) in item.content" :key="index2"
						v-if="item2.orderId==item3.orderId">
						<!-- 订单标题 -->
						<view class="title">
							<view class="detail">
								<image class="icon" :src="item2.cover" mode="aspectFill"></image>
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
							<!-- <cl-text :size="28" :value="`订单描述：${item2.couIntroduction}`" color="#979797" bold block>
								</cl-text> -->
							<!-- <cl-text :size="28" :value="`订单编号：${item2.orderId}`" color="#979797" bold block
									:margin="[18, 0, 0, 0]"></cl-text> -->
							<cl-text v-if="item2.orderFormP==1" :size="28" :value="`正在申请退货中...`" color="brown" bold
								block :margin="[18, 0, 0, 0]"></cl-text>
							<cl-text v-if="item2.orderFormP==2" :size="28" :value="`退货成功`" color="brown" bold block
								:margin="[18, 0, 0, 0]"></cl-text>
							<cl-text v-if="item2.orderFormP==3" :size="28" :value="`退货失败`" color="brown" bold block
								:margin="[18, 0, 0, 0]"></cl-text>
							<cl-text :size="28" :value="`课程名称：${item2.couName}`" color="#979797" bold block
								:margin="[18, 0, 0, 0]"></cl-text>
							<cl-text :size="28" :value="`创建时间：${item2.orderFormDate}`" color="#979797" bold block
								:margin="[18, 0, 0, 0]"></cl-text>
						</view>
						<!-- 总金额 -->
						<cl-text :size="28" :value="`订单金额：积分${item2.couPrice}`" color="#2E2E2E" bold block
							:margin="[36, 0, 0, 0]"></cl-text>
					</view>
					<!-- 退货 -->
					<view class="tuihou">
						<button type="default" @click="toGoods(item3.orderId)"
							v-if="item3.orderFormP==0||item3.orderFormP==3">退货</button>
						<button type="default" @click="getGoods(item3.orderId)" v-if="item3.orderFormP==1">取消退货</button>
					</view>
				</view>
			</view>
		</view>
		<!-- </cl-tabs> -->
		<view>
			<!-- 输入框示例 -->
			<uni-popup ref="inputAnswer" type="dialog">
				<uni-popup-dialog ref="inputClose" title="您确定要取消退货吗？" @confirm="dialogInputAn">
				</uni-popup-dialog>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	import api from "@/common/request/api.js"
	export default {
		data() {
			return {
				thing: [],
				pageSize: 50,
				pageNum: 1,
				courseList: [],
				active: 0,
				orderId: '',
				user: {},
				uId: '',
				orderIdList: [],
				cennal: '',
				list: [{
					title: "",
					content: [

					],
				}, ],
			}
		},
		methods: {
			dialogInputAn() {
				console.log(this.cennal, "cennal")
				this.$api('returnGoods', {
					id: this.cennal,
					orderFormP: 0,
					orderFormDetail: ''
				}).then(res => {
					if (res.code == 200) {
						uni.showToast({
							title: "取消退货成功！",
							duration: 2000
						})
						this.getOrder(this.pageSize, this.pageNum)
					} else {
						uni.showToast({
							title: "取消退货失败！",
							icon: 'none',
							duration: 2000
						})
					}
				})
			},
			removeDup(dataList, orderId) {
				var result = []
				var tem = {}
				for (var i = 0; i < dataList.length; i++) {
					if (!tem[dataList[i][orderId]]) {
						result.push(dataList[i])
						tem[dataList[i][orderId]] = 1
					}
				}
				return result
			},
			getOrder(page, num) {
				console.log(this.orderId, 'orderId')
				if (this.orderId == null) {
					// console.log("进入循环")
					this.$api('dingdan', {
						pageSize: page,
						pageNum: num,
						uId: this.uId,
					}).then(res => {
						// console.log(res, 'wwsssss')
						this.list[0].content = res.data.records
						// console.log(this.list[0].content, "content")
						const listD = []
						let listP = this.list[0].content.map(v => ({
							orderId: v.orderId,
							orderFormP: v.orderFormP
						}))
						// console.log(listP, "listP")
						this.orderIdList = this.removeDup(listP, "orderId")
						// console.log(this.orderIdList, "orderIdList")
						// for (let i = 0; i < this.list[0].content.length; i++) {
						// 	const orderId = this.list[0].content[i].orderId
						// 	listD.push(orderId)
						// }
						// console.log(listD, "listD")
						// this.orderIdList = [...new Set(listD)]
						// console.log(this.orderIdList, "orderIdList")
					})
				} else if (this.orderId != undefined ) {
					this.$api('dingdan', {
						pageSize: page,
						pageNum: num,
						orderId: this.orderId
					}).then(res => {
						console.log(res, 'wwsssss')
						this.list[0].content = res.data.records
						
						let listP = this.list[0].content.map(v => ({
							orderId: v.orderId,
							orderFormP: v.orderFormP
						}))
						// console.log(listP, "listP")
						this.orderIdList = this.removeDup(listP, "orderId")
					})
					uni.showToast({
						title: '购买商品成功！',
						duration: 2000
					})
				}
			},
			getGoods(orderId) {
				this.cennal = orderId
				this.$refs.inputAnswer.open()
			},
			toGoods(orderId) {
				console.log(orderId, "orderId")
				uni.navigateTo({
					url: "/pages/my/returnGoods?orderId=" + orderId
				})
			}
		},
		onShow() {
			this.getOrder(this.pageSize, this.pageNum)
		},
		onLoad(options) {
			this.user = uni.getStorageSync("user")
			this.uId = this.user.id
			if (options.orderId != '') {
				this.orderId = options.orderId
			}
			this.getOrder(this.pageSize, this.pageNum)
		}
	};
</script>
<style scoped lang="scss">
	page {
		background: #f5f5f5;
	}

	.order {
		// /deep/ .cl-tabs__container {
		// 	// background-color: rgba(0, 0, 0, 0);
		// }

		// /deep/ .cl-tabs__bar-item.is-active {
		// 	font-weight: bold;
		// }

		// padding-bottom: 50rpx;

		.course {
			margin-top: 26rpx;
			padding: 36rpx 30rpx;
			box-sizing: border-box;
			background: #ffffff;
			border: 1rpx solid gainsboro;

			.title {
				display: flex;
				align-items: center;
				justify-content: space-between;

				.detail {

					// display: flex;
					// align-items: center;
					.icon {
						width: 608rpx;
						height: 400rpx;
						text-align: center;
					}
				}


			}

			.msg {
				margin-top: 36rpx;
				padding-bottom: 36rpx;
				border-bottom: 2rpx solid #f2f2f2;
			}
		}

		.tuihou {
			margin-top: 30rpx;
			margin-bottom: 10rpx;
		}
	}

	.orderhhh {
		margin: 20rpx;
		font-size: 38rpx;
		background-color: #fff;
		border-radius: 20rpx;
		// border-bottom: 5rpx solid gainsboro;
	}

	.padd {
		padding: 20rpx;
	}
</style>
