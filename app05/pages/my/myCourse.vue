<template>
	<view style="background-color:whitesmoke;" >
		<view class="search">
			<cl-search v-model="keyword" :clearable="true" style="height: 59px;" placeholder="请输入关键词"  @search="onSearch"></cl-search>
		</view>

		<view class="item_bottom" v-for="item in myCourseData" :key="item.couId" @click="courseDetail(item)"
			style="display: flex;flex-direction:column ">

			<view class="item_kecheng"
				style="display: flex;flex-direction:row;align-self: center;height:250rpx;margin-bottom: 15px;">
				<view class="item_img">
					<image :src="item.cover"></image>
				</view>

				<view class="item_text">
					<text>{{item.couName}}</text>

					<view class="item_kecheng2">
						<text>授课老师: {{item.truename}}</text>
					</view>

					<view class="item_jindu">
						<progress :percent="item.progress" show-info="true" font-size="25"></progress>
					</view>
				</view>
			</view>

		</view>

		<!-- 底部 -->

	</view>



</template>
<script>
	import api from "@/common/request/api.js"
	export default {	
		data() {
			return {
				myCourseData: [],
				pageNum: 1,
				pageSize: 999,
				user: {}, //从浏览器缓存获取
				percent: 0, // 保存进度值的变化前后值，用于比较用
				CouProgress: [],
				couId: '',
				keyword: "",

			}
		},
		mounted() {
			this.drawProgressbg();
			this.drawCircle(this.value);
		},
		created() {


		},
		onLoad() {
			this.user = uni.getStorageSync("user")
			//console.log(this.user,'user')
			this.load()
		},
		methods: {
	
			onSearch(){
				console.log(this.keyword)
				
				this.$api('mycourse', {
					stuId: this.user.stuId,
					have: 1,
					pageSize: this.pageSize,
					pageNum: this.pageNum,
					couName:this.keyword
				}).then(res => {
					this.myCourseData = res.data.records
					for (var i = 0; i < this.CouProgress.length; i++) {
						for (var j = 0; j < this.myCourseData.length; j++)
							if (this.CouProgress[i].couId === this.myCourseData[j].couId) {
								if(this.myCourseData[j].progress===undefined){
								if (this.CouProgress[i].learnedStatus !== undefined) {
									if (this.CouProgress[i].learnedStatus !== "LEARNING") {
										this.myCourseData[j].progress = 100
									} else {
										this.myCourseData[j].progress = Number(this.CouProgress[i]
												.learnedSectionCount / this.myCourseData[j].couChaNum * 100)
											.toFixed(0)
									}
								}}
							}
				}	
				})
			},
			load() {
				this.$api('findCouProgress', {
					stuId: this.user.stuId
				}).then(res => {
					this.CouProgress = res.data
				})
				//console.log(this.user.stuId,'stuId')
				this.$api('mycourse', {
					stuId: this.user.stuId,
					have: 1,
					pageSize: this.pageSize,
					pageNum: this.pageNum
				}).then(res => {
					this.myCourseData = res.data.records
					for (var i = 0; i < this.CouProgress.length; i++) {
						for (var j = 0; j < this.myCourseData.length; j++)
							if (this.CouProgress[i].couId === this.myCourseData[j].couId) {
								console.log("+++++++++++++++++++++++")
								if (this.CouProgress[i].learnedStatus !== undefined) {
									if (this.CouProgress[i].learnedStatus !== "LEARNING") {
										this.myCourseData[j].progress = 100
									} else {
										this.myCourseData[j].progress = Number(this.CouProgress[i]
												.learnedSectionCount / this.myCourseData[j].couChaNum * 100)
											.toFixed(0)
									console.log(this.CouProgress[i].learnedSectionCount , "-----------------")
						 			console.log(this.myCourseData[j].progress, "+++++++++++++++++++++++")
									}
								}
							}
				}	
				})


			},			//跳转到课程详情页面
			courseDetail(item) {
				uni.navigateTo({
					url: '/pages/index/live_brief/live_brief?couId=' + item.couId
				})
			}
		}
	}
</script>

<style lang="scss">
	
	
	/deep/ .cl-search {
         height: 56px;
		background-color: rgba(0, 0, 0, 0);
	}

	/deep/ .cl-button.cl-button--primary {
		border-radius: 32rpx;
		background: linear-gradient(90deg, #8ebbff 0%, #618cfc 100%);
	}

	/deep/ .cl-search__input .cl-button {
		right: 12rpx;
		top: 12rpx;
		height: 70rpx;
	}

	/deep/ .cl-input {
		height: 90rpx;
	}

	/deep/ .cl-tabs__bar-item.is-active {
		// font-size: 32rpx;
		font-weight: 600;
	}
		 ::v-deep .cl-input__icon .cl-icon-close{
		position: relative;
	    right: 20px;
		margin-right: 20px;
	}

	.bground {
		background-image: url('@/static/images/index/beijing.png');
		background-size: 100% 100%;
		height: 400rpx;
	}

	.item_t1 {
		padding-top: 30rpx;
		margin-left: 40rpx;
		font-size: 40rpx;
	}

	.item_t2 {
		margin-top: 60rpx;
		margin-left: 40rpx;
		font-size: 50rpx;
	}

	.item_t3 {
		background-color: yellow;
		border-radius: 25px;
		width: 200rpx;
		height: 100rpx;
		margin-top: 60rpx;
		margin-left: 60rpx;
		font-size: 35rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.item_bottom {

		.item_kecheng {

			width: 94%;
			/* height: 160px; */
			margin-top: 10px;
			
			background-color: #fff;
			border-radius: 6px;
			padding: 14px 0px 0px 0px;

			image {
				border-radius: 6px;
				width: 300rpx;
				height: 200rpx;
				margin-left: 19rpx;
				margin-top: 10rpx;

			}


			.item_text {
				padding-top: 15px;
				padding-left: 20px;
				font-size: 30rpx;
			}

			.item_kecheng2 {
				font-size: 30rpx;
				color: #ccc;
				margin-top: 10rpx;
			}


			.item_jindu {
				width: 300rpx;
				margin-top: 40rpx;
			}
		}

	}
</style>


