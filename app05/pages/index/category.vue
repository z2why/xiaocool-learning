<template>
	<view style="background-color:whitesmoke;height: 100%;">
		<view class="banner" style="display: flex;flex-direction:column">
			<view class="progress_box">
				<canvas class="progress_bg" canvas-id="cpbg"
					:style="{width:progress_width+'px',height:progress_height+'px'}"></canvas>

				<canvas class="progress_bar" canvas-id="cpbar"
					:style="{width:progress_width+'px',height:progress_height+'px'}"></canvas>
			</view>
			<view style="position: relative;bottom: 74px;align-self: center;color:dimgray">已学课程</view>
			<view style="position: relative;bottom: 78px;align-self: center;font-weight: 600;font-size: 50px;">
				{{hadlearned}}</view>
			<view style="position: relative;bottom: 78px;align-self: center;font-size: 15px;color:grey">/{{have}}</view>
		</view>

		<view style="display: flex;flex-direction:column;">
			<view class="center">
				<view style="flex-direction:row;padding-left:20px;padding-top:10px;">
					<view class="text1" style="color: #55aa00;margin-right:33px ;">{{learnedCha}}</view>
					<view class="text">今日奖励积分</view>
				</view>
				<view style="flex-direction:row;padding-top: 10px">
					<view class="text1" style="color: #ff5500;margin-left: 8px;">{{count}}%</view>
					<view class="text" style="margin-left:26px;">已击败用户</view>
				</view>
				<view style="flex-direction:row;padding-right:24px;padding-top: 21rpx">
					<view class="text1" style="color: #5353f9;font-size: 19px;">{{todayTime}}</view>
					<view class="text" style="padding-left: 16px;">今日学习时长</view>
				</view>

			</view>

		</view>
		<view class="title">
			<view class="left">
				<text style="font-size: 22px;padding-left: 15px;">我的课程</text>
			</view>
			<view class="right" @tap="tomyCourse" style="z-index: 1;">
				<cl-text :size="27" value="查看全部" color="#8D8D8D" bold :margin="[-2, 0, 0, 0]"></cl-text>
				<cl-icon name="cl-icon-arrow-right"></cl-icon>
			</view>
		</view>


		<view class="item_bottom" v-for="item in CouProgress" :key="item.couId" @click="courseDetail(item)"
			style="display: flex;flex-direction:column ">

			<view class="item_kecheng" style="display: flex;flex-direction:row;align-self:center;height:250rpx;">
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
		props: {
			progress_time: {
				type: Number,
				default: 1500
			},
			progress_width: {
				type: Number,
				default: 250
			},
			progress_height: {
				type: Number,
				default: 200
			},
			border_width: {
				type: Number,
				default: 20
			},
			bg_color: {
				type: String,
				default: '#EDEDED'
			},
			start_color: {
				type: String,
				default: '#55aaff'
			},
			end_color: {
				type: String,
				default: '#009deb'
			},
		},

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
				count: '',
				hadlearned: '',
				have: '',
				learnedCha: '',
				todayTime: '',
                qwq:'',
	           myCourseCounts:''

			}
		},
		mounted() {
			
		},
		created() {


		},
		onLoad() {
			this.user = uni.getStorageSync("user")
			//console.log(this.user,'user')
			this.load()
		},
		methods: {
			drawProgressbg: function() {
				// 自定义组件实例 this ，表示在这个自定义组件下查找拥有 canvas-id 的 <canvas/>
				let ctx = uni.createCanvasContext('cpbg', this);
				ctx.setLineWidth(this.border_width);
				ctx.setStrokeStyle(this.bg_color);
				ctx.setLineCap('round');
				ctx.beginPath();
				ctx.arc(125, 125, 100, 0.99 * Math.PI, 0.01 * Math.PI, false);
				ctx.stroke();
				ctx.draw();
			},
			// 画圆（递归调用）
			drawCircle: function(step) {
				if (step === 0) return;
				let time = Math.floor(this.progress_time / 100)
				let ctx = uni.createCanvasContext('cpbar', this);
				let gradient = ctx.createLinearGradient(28, 55, 192, 55);
				gradient.addColorStop('0', this.start_color);
				gradient.addColorStop('1.0', this.end_color);
				ctx.setLineWidth(this.border_width);
				ctx.setStrokeStyle(gradient);
				ctx.setLineCap('round');
				ctx.beginPath();
				step = 0.01 * step + 0.99;
				if (step === 1) {
					step = 0.99
				}
				if (step >= 2) {
					step = step % 2;
				}
				if (step === 1.99) {
					step = 0.01
				}
				ctx.arc(125, 125, 100, 0.99 * Math.PI, step * Math.PI, false);
				ctx.stroke();
				ctx.draw();
				if (this.qwq > this.percent) {
					this.percent++
					setTimeout(() => {
							this.drawCircle(this.percent)
						},
						time)
				}
			},

			tomyCourse() {
				//查看我的课程
				uni.navigateTo({
					url: "/pages/my/myCourse",
				});

			},
			load() {
				this.$api('couData', {
					stuId: this.user.stuId
				}).then(res => {
					console.log(res)
					this.count = Math.floor(res.data.count).toFixed(2) 
					this.hadlearned = res.data.hadlearned
					this.have = res.data.have
					this.learnedCha = res.data.learnedCha * 5
					// this.todayTime=res.data.todayTime
					let result = res.data.todayTime
					this.todayTime = Math.floor(result / 60) + '分' + (result % 60 / 100).toFixed(2).slice(-2) +'秒'
					this.qwq = this.hadlearned/ this.have *100
					console.log(this.have,"have")
					this.drawProgressbg();
					console.log(this.qwq,"qwq")
					this.drawCircle(this.qwq);
					
				})
				
				this.$api('mycourse', {
									stuId: this.user.stuId,
									have: 1,
									pageSize: this.pageSize,
									pageNum: this.pageNum
					
								}).	then(res => {
						this.myCourseData = res.data.records
						this.myCourseCounts=res.data.total
							})
				this.$api('findProgress', {
					stuId: this.user.stuId,
					pageSize: 3,
					pageNum: 1
				}).then(res => {
					this.CouProgress = res.data.records
					if(res.data.total<3){	
						if(this.myCourseCounts<3){
							this.CouProgress=this.myCourseData
						}
						else{
						var progressLength=3-res.data.total
						var pro=res.data.total
						const arr1 =this.myCourseData
						const arr2 = this.CouProgress
						const getAbsentValues = (arr1,arr2) => {
						   let qwq = [];
						   qwq = arr1.filter(el => {
						      return !arr2.find(obj => {
						         return el.couId === obj.couId;
						      });
						   });
						   return qwq;
						};
					     for(var q=0;q<progressLength;q++){
							 this.CouProgress[pro]=getAbsentValues(arr1,arr2)[q]
							 pro++
						 }
					
						console.log(this.CouProgress);
							
						for (var i = 0; i < this.CouProgress.length; i++) {
				
								
							if(this.CouProgress[i].progress===undefined){
							if (this.CouProgress[i].learnedStatus !== undefined) {
								if (this.CouProgress[i].learnedStatus !== "LEARNING") {
									this.CouProgress[i].progress = 100
								} else {
									this.CouProgress[i].progress = Number(this.CouProgress[i]
										.learnedSectionCount / this.CouProgress[i].couChaNum * 100).toFixed(0)
								}
							}
							}
		
					}}}
					else{
							for (var i = 0; i < this.CouProgress.length; i++) {
								if (this.CouProgress[i].learnedStatus !== undefined) {
									if (this.CouProgress[i].learnedStatus !== "LEARNING") {
										this.CouProgress[i].progress = 100
									} else {
										this.CouProgress[i].progress = Number(this.CouProgress[i]
									.learnedSectionCount / this.CouProgress[i].couChaNum * 100).toFixed(0)
									}										
										}
									}																
					}
	
				})

			},


			//跳转到课程详情页面
			courseDetail(item) {
				uni.navigateTo({
					url: '/pages/index/live_brief/live_brief?couId=' + item.couId
				})
			}
		}
	}
</script>

<style lang="scss">
	.progress_box {
		position: relative;
		top: 55px;
		width: 100%;
		height: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
		text-align: center;
	}
	
	.progress_bg {
		position: absolute;
	
	}
	.banner {
		position: relative;
		top: 5px;
		height: 320rpx;
		padding-top: 20rpx;
		display: flex;
		justify-content: space-around;
	}

	.center {
		box-shadow: 0px 1px 2px #b3b0b0;
		position: relative;
		height: 220rpx;
		background-color: #ffffff;
		margin: 0px 11px 30px 10px;
		display: flex;
		border-radius: 8px;
		width: 95%;
		justify-content: space-around;
	}

	.title {
		display: flex;
		align-items: center;
		justify-content: space-between;


		.right {
			padding-right: 14px;
			display: flex;
			align-items: center;
			font-size: 24rpx;
			color: #8d8d8d;
		}
	}



	.text {
		font-size: 13px;
		color: #7c7c7c;
	}

	.text1 {
		padding-top: 27px;
		padding-bottom: 5px;
		padding-left: 20px;
		font-family: auto;
		font-size: 20px;
		font-weight: 600;
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
			margin-bottom: 10px;
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
