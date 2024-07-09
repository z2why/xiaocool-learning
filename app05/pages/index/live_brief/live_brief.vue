<template>
	<view class="LessonDetails-content">
		<!-- 视频 -->
		<view class="LessonDetails-top">
			<image :src="coursedata.cover"></image>
		</view>
		<!-- 点赞转发等 -->
		<view class="LessonDetails-dianzan">
			<view class="LessonDetails-name">{{coursedata.couName}}</view>
			<view class="dianzan-shoucang-zhuanfa">
				<image class="dianzan" style="padding-left: 10px" v-if='isRecommend === 0' src="@/static/images/index/dianzan.png" mode="aspectFit"
					@click="recommend" />
				<image class="dianzan" style="padding-left: 10px" v-else-if="isRecommend === 1" src="@/static/images/index/isRecommend.png"
					mode="aspectFit" @click="recommend" />
			    <span>	{{coursedata.couRecoNum}} </span><!-- 点赞数 -->
				<image class="shoucang" v-if="isCollect === 0" src="@/static/images/index/noCollect.png"
					mode="aspectFit" @click="collect" />
				<image class="shoucang" v-else-if="isCollect === 1 " src="@/static/images/index/collect.png"
					mode="aspectFit" @click="collect" />
				{{coursedata.couCollNum}} <!-- 收藏数 -->
		             <span @click="returnPractive(coursedata.couName)" class="fenxiang"> <image  src="@/static/images/index/practive.png" mode="aspectFit"></image>线下实践</span>
			</view>
		</view>
		
		<!-- 课程简介、目录、评价 -->
		<view class="LessonDetails-jianjie">
			<view class="LessonDetails-jianjie-top">
				<view class="LessonDetails-jianjie-top-item" v-for="(item,index) in list" :key="index"
					:class="[active===index?'addnav':'']" @click="cutNav(index,item.navid)">
					{{item.navname}}
				</view>
			</view>
			<!-- <view class="LessonDetails-jianjie-bottom"></view> -->
			<view class="bigbox" v-if="navMain == 0">
				<view class="Course-introduction">
					<view class='Course-introduction-name' style="padding-top:4px ;"></view>
					<view class="Course-introduction-content"><text space="emsp" >{{'  '}}</text>{{coursedata.couIntroduction}}</view>
				</view>
	
			</view>
			<view class="bigbox" v-if="navMain == 1">
				<view v-if="chapter.length === 0" class="imgNull" style="margin: auto;">
					<img src="../../../static/comment2.jpg" style="padding: 30px;"></img>
				</view>
				<ul v-for="item in chapter" :key="item.chaId">
				    <li>	
					<span class="" style="padding-top: 17px;" @click="cha(item.chaId)">
						<image v-if="item.learnedStatus!==undefined&&item.learnedStatus==='COMPALETE'"  src="@/static/images/live_brief/learned.png" mode="aspectFit" class="nolearn">
						</image>
						<image v-if="item.learnedStatus!==undefined&&item.learnedStatus==='LEARNING'" src="@/static/images/live_brief/learning.png" mode="aspectFit" class="nolearn">
						</image>
					 <image v-if="item.learnedStatus===undefined" src="@/static/images/live_brief/nolearned.png" mode="aspectFit" class="nolearn">
					</image>
					第{{item.chaIndex}}章 {{item.chaTitle}}
					</span>
					</li>
				</ul>
			</view>

			<!-- 评价 -->
			<view class="bigbox consult" v-else-if="navMain == 2">
				<view v-if="courseConsultList.length === 0" class="imgNull" style="margin: auto;">
					<img src="../../../static/comment1.png"></img>
				</view>
				<view class="consult-content" v-else v-for="item in courseConsultList" :key="item.id">
					<view class="imgView">
						<image :src="item.avatar" mode=""></image>
					</view>
					<view class="textView">
						<text class="name">{{item.truename}}</text>
						<text class="text">{{item.couAppraise}}</text>
						<view class="delete" @click="deleteCom(item.id)" v-if="item.stuId == user.stuId">删除</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 新增评价 -->
		<button v-if="navMain == 2 && have === 1" class="addComment" @click="addComment">+</button>
		<!-- 最下边加入购物车 -->
		<view v-if="have === 0" class="LessonDetails-bottom">

		</view>

		<!-- 购物车 -->

		<view v-if=" have === 0" class="uni-container">
			<view class="goods-carts">
				<uni-goods-nav :options="options" :fill="true" :button-group="buttonGroup" @click="onClick"
					@buttonClick="buttonClick" />
			</view>
		</view>
	
			<uni-popup ref="inputDialog" type="dialog">
				<uni-popup-dialog ref="inputClose" title="您确定要删除这条评论吗？" @confirm="dialogInputConfirm">
				</uni-popup-dialog>
			</uni-popup>
	</view>
</template>

<script>
	import api from "@/common/request/api.js"
	export default {

		components: {},
		data() {
			return {
				orderId: '',
				isRecommend: 0, //是否点赞
				isCollect: 0, //是否收藏
				chapter: {}, //章节
				couId: '',
				coursedata: {},
				have: 0,
				pageNum: 1,
				pageSize: 10,
				user: {},
				stuchapter:[],
				thing: [],
				shop: {},
				courseConsultList: {},
				newList: [
					'1'
				],
				LEARNING:'',
				COMPALETE:'',
				options: [{
					icon: 'cart',
					text: '购物车',
					info: 0
				}],
				buttonGroup: [{
						text: '加入购物车',
						backgroundColor: 'linear-gradient(90deg, #FFCD1E, #FF8A18)',
						color: '#fff'
					},
					{
						text: '立即购买',
						backgroundColor: 'linear-gradient(90deg, #FE6035, #EF1224)',
						color: '#fff'
					}
				],
				active: 0,
				navMain: 0,
				secondDate: [],
				couApp:{},
				list: [{
						value: 1,
						navname: '简介',
						navid: 0,
						store: []
					},
					{
						value: 2,
						navname: '目录',
						navid: 1,
						store: []
					},
					{
						value: 3,
						navname: '评价',
						navid: 2,
						store: []
					},

				],
			}
		},
		onShow(){
			this.getshoppingNum()
			this.load()
		},
		onLoad(option) {
			this.category = option.category
			console.log(this.category, "category")
			this.couId = option.couId
			this.user = uni.getStorageSync("user")
			this.load()
			this.getshoppingNum()
		},
		created() {
			let that=this
        setTimeout(that.load(),3000)
		},
		methods: {
			//获取购物车的数量
			getshoppingNum() {
				this.$api("shopping", {
					pageSize: 50,
					pageNum: 1,
					stuId: this.user.stuId
				}).then(res => {
					if (res.code == 200) {
						this.options[0].info = res.data.total
					}
				})
			},
			// 点击跳转到购物车页面
			onClick(e) {
				uni.navigateTo({
					url: '/pages/my/Sorder'
				})
			},
			//底部购物车按钮+立即购买按钮
			buttonClick(e) {
				if (e.content.text === '加入购物车') {
					this.$api('addShopping', {
						stuId: this.user.stuId,
						couId: this.couId,
					}).then(res => {
						if (res.code === '200') {
							uni.showToast({
								title: '加入购物车成功！'
							})
							this.getshoppingNum()
						} else if (res.code === '600') {
							uni.showToast({
								title: res.msg,
								icon: 'none',
							})
						} else {
							uni.showToast({
								title: '加入购物车失败！'
							})
						}
					})
				} else if (e.content.text === '立即购买') {
					console.log(this.coursedata.couId, this.user.stuId, this.user.id, "couId")
					this.shop.couId = String(this.coursedata.couId)
					this.shop.stuId = this.user.stuId
					this.shop.uid = this.user.id
					console.log(this.shop, "shop")
					this.thing.push(JSON.parse(JSON.stringify(this.shop)))
					console.log(this.thing, "thing")
					this.$api('buyCourse', this.thing).then(res => {
						if (res.code == 200) {
							uni.showToast({
								title: '购买成功！',
								icon:"success"
							})
							this.orderId = res.data.id
							uni.navigateTo({
								url: '/pages/my/order?orderId='+this.orderId,
								duration:2000
							})
						} else if (res.code == 600) {
							uni.showToast({
								title: res.msg,
								icon: 'none',
								duration: 2000
							})
						}
						else{
							uni.showToast({
								title: '系统错误',
								icon: 'none',
								duration: 2000
							})
							
						}

					})
				}
			},
			deleteCom(couAppId) {
				// console.log(cId, "cId")
				this.couApp.id = couAppId
				this.$refs.inputDialog.open()
			},
			
			dialogInputConfirm(val) {
				this.$api('deleteCouApp', this.couApp).then(res => {
					if (res.code == 200) {
						uni.showToast({
							title: "删除评论成功！",
							duration: 2000
						})
						this.load()
					} else {
						uni.showToast({
							title: "删除评论失败！",
							icon: 'none',
							duration: 2000
						})
						this.load()
					}
				})
			},
			cutNav(index, id) {
				this.active = index
				this.navMain = id
				const res = this.list[id].store
				this.secondDate = res
			},
			load() {
				//课程
				const form = {
					stuId: this.user.stuId,
					couId: this.couId,
					pageSize: 1,
					pageNum: 1,
				}
				this.$api('coursepage', form).then(res => {
					console.log(res, '课程')
					if (res.data.total === 0) {
						this.$api('course', {
							couId: this.couId,
							pageSize: 1,
							pageNum: 1,
						}).then(res => {
							//console.log(res,)
							this.coursedata = res.data.records[0]
						})
					} else {
						this.coursedata = res.data.records[0]
						this.have = this.coursedata.have
						this.isCollect = this.coursedata.collect
						console.log(this.coursedata.collect, '收藏')
						this.isRecommend = this.coursedata.recommend
					}
				})

				//评价
				this.$api('pingjia', {
					couId: this.couId,
					pageNum: this.pageNum,
					pageSize: this.pageSize
				}).then(res => {
					//console.log(res, 'res')
					this.courseConsultList = res.data.records
					//console.log(this.courseConsultList, 'this.courseConsultList')
				})

				//章节
				this.$api('coursechapter', {
					courseId: this.couId
				}).then(res => {
					this.chapter = res.data
					//console.log(this.chapter,'chapter')
					for(var i=0;i<this.stuchapter.length;i++){
									for(var j=0;j<this.chapter.length;j++){
									   if(this.chapter[j].chaId===this.stuchapter[i].chaId){
										   this.chapter[j].learnedStatus=this.stuchapter[i].learnedStatus
						            	  console.log(this.chapter[j])
									   }
					}	}
				})
				
			   this.$api('learningProgress', {
				couId: this.couId,
				stuId: this.user.stuId
			    }).then(res => {
				this.stuchapter = res.data		
			   })
	
			
			   		
			},
			//点赞
			recommend() {
				//console.log(this.user.stuId,'this.user.stuId')
				this.$api('recommendCourse', {
					couId: this.couId,
					stuId: this.user.stuId
				}).then(res => {
					console.log(res, '点赞')
					if (res.code === '200') {
						this.load()
						this.coursedata.recommend = res.data.recommend
						if (this.coursedata.recommend === 1) {
							uni.showToast({
								title: '点赞成功！',
								icon: 'success'
							})
							this.isRecommend = res.data.recommend

						} else {
							uni.showToast({
								title: '取消点赞！',
							})
							this.isRecommend = res.data.recommend

						}
					} else {
						uni.showToast({
							title: '点赞失败！',
							icon: 'error'
						})
					}
				})

			},

			//收藏
			collect() {
				//console.log(this.couId, this.user.stuId, '收藏课程')
				const form = {
					couId: this.couId,
					stuId: this.user.stuId,
				}
				this.$api('collectCourse', form).then(res => {
					if (res.code === '200') {
						this.load()
						this.coursedata.collect = res.data.collect
						if (res.data.collect == 1) {
							uni.showToast({
								title: '收藏成功！',
								icon: 'success'
							})
							this.isCollect = res.data.collect
						} else {
							uni.showToast({
								title: '取消收藏！',
							})
							this.isCollect = res.data.collect
						}
					} else {
						uni.showToast({
							title: '收藏失败！',
							icon: 'error'
						})
					}
				})
			},
			//新增课程评价
			addComment() {
				uni.navigateTo({
					url: '/pages/index/live_brief/addCouComment?couId=' + this.couId
				})
			},
			//跳转到章节页面
			cha(chaId) {
				console.log(this.have,"1111111111111111111111")
				if(this.have===1){
				uni.navigateTo({
					url: '/pages/index/live_brief/chapter?couId=' + this.couId + '&chaId=' + chaId
				})}
				
				else{
					uni.showToast({
						title: '请先购买课程！！',
						icon: 'none'
					})
				}
			},
			//跳转到报名线下实践页面
			returnPractive(couName) {
				if (this.have === 0) {
					uni.showToast({
						title: '您还没购买该课程，不能报名线下实践！',
						icon: 'none'
					})
				} else {
					uni.navigateTo({
						url: '/pages/index/live_brief/allpractive?couId=' + this.couId + '&couName=' + couName
					})
				}
			}

		}

	}
</script>

<style lang="scss">
	.LessonDetails-content {
		background-color: #f5f3f8;

		.LessonDetails-top {
			width: 100%;
			height: 350rpx;
			// margin-top: 10rpx;
			background-color: #e8e8e8;
			display: flex;
			justify-content: center;
			align-items: center;

			uni-image {
				width: 100%;
			}

			image {
				width: 100%rpx;
				height: 350rpx;

			}
		}

		.LessonDetails-dianzan {
			padding-left: 10rpx;
			padding-right: 10rpx;
			width: 100%;
			height: 150rpx;
			background-color: #ffffff;
			margin-top: 10rpx;
			margin-bottom: 15rpx;

			.LessonDetails-name {
				padding-top: 10rpx;
				padding-bottom: 3rpx;

			}

			.dianzan-shoucang-zhuanfa {
				// display: flex;
				font-size: 30rpx;

				image {
					width: 50rpx;
					height: 50rpx;
					padding-right: 10rpx;
				}

				.shoucang {
					padding-left: 40rpx;
					padding-top: 20rpx;
				}

				.fenxiang {
					padding-left: 300rpx;
				}
			}
		}

		.LessonDetails-jianjie {

			.LessonDetails-jianjie-top {
				width: 100%;
				height: 92rpx;
				display: flex;
				justify-content: center;
				align-items: center;
				background-color: #ffffff;

				.LessonDetails-jianjie-top-item {
					width: 33vh;
					height: 70rpx;
					text-align: center;
					padding-top: 30rpx;
				}

				.LessonDetails-jianjie-top-item::before {
					position: absolute;
					content: '|';
					top: 15rpx;
					color: #eee;
					right: 0;
				}

				.LessonDetails-jianjie-top-item:last-child::before {
					position: absolute;
					content: '|';
					top: 5rpx;
					color: #fff;
					right: 0;
				}

				.addnav {
					border-bottom: 4rpx solid #48c680;
					color: #31b26a;
				}

				.active {
					color: #aace9e;
					border-bottom: 4rpx solid #aace9e;
				}
			}
		}

		.Course-introduction {
			background-color: #ffffff;
			margin-bottom: 10rpx;
			padding-left: 10rpx;
			padding-right: 10rpx;

			.Course-introduction-name {
				margin-top: 10rpx;
				margin-bottom: 10rpx;
				letter-spacing: 8rpx;
			}

			.Course-introduction-content {
				padding-bottom: 30rpx;
				line-height: 60rpx;
				letter-spacing: 8rpx;
			}
		}

		.develop {
			background-color: #ffffff;
			margin-bottom: 10rpx;
			padding-left: 10rpx;
			padding-right: 10rpx;

			.develop-name {
				margin-top: 10rpx;
				margin-bottom: 10rpx;

			}

			.develop-content {
				padding-bottom: 30rpx;

			}
		}

		.Course {
			background-color: #ffffff;
			padding-left: 10rpx;
			padding-right: 10rpx;

			.Course-name {
				margin-top: 10rpx;
				margin-bottom: 10rpx;
			}

			.Course-content {
				display: flex;
				padding-bottom: 50rpx;

				.Course-content-left {
					width: 300rpx;
					height: 150rpx;
					background-color: #e8e8e8;
					display: flex;
					justify-content: center;
					align-items: center;

					image {
						width: 50rpx;
						height: 50rpx;
					}
				}

				.Course-content-right {
					padding-left: 10rpx;

					.Course-content-right-name {
						padding-bottom: 10rpx;
					}

					.Course-content-right-details {
						font-size: 35rpx;
						color: #acb2b2;
					}

				}
			}

		}

	}

	.bigbox {
		height: 650rpx;
		margin-bottom: 15px;
         .nolearn{
		   height: 23px;
		   width: 23px;
		    position: relative;
		    top: 5px;
			padding-left: 10px;
			padding-right: 15px;
			
	      }
		ul {
			margin: 0;
			padding: 0;
			list-style-type: none;

			li {
				padding: 25rpx 20rpx 20rpx 20rpx;
				background-color: white;
			}
		}

	}

	.consult {
		display: flex;
		flex-direction: column;
		width: 100%;
		margin-top: 10rpx;

		.consult-content {
			border-bottom: 1rpx solid #f0f0f0;
			padding: 20rpx;
			display: flex;
			justify-content: flex-start;
			background-color: white;


			.imgView {
				width: 113rpx;
				height: 113rpx;
				padding-right: 25rpx;
				padding-left: 5rpx;
				    margin-top: 7px;

				image {
					width: 100%;
					height: 100%;
					border-radius: 50%;
				}
			}
		}

		.textView {
			display: flex;
			flex-direction: column;
            padding-left:10rpx ;
			text {
				height: 50rpx;
			}

			.name {
				 color: #d58e00;
				 font-size: 32rpx;
				 
			}

			.text {
				font-size: 32rpx;
				
				color: #4f4f4f;
				padding-bottom: 7rpx;
				padding-top: 5rpx;
				
			}
			.delete {
				
				color: firebrick;
				font-size: small;
			}
		}
	}

	.uni-container {
		position: fixed;
		bottom: 0;
		width: 100%
	}

	.addComment {
		width: 100rpx;
		height: 100rpx;
		background-color: white;
		border-radius: 50%;
		font-size: 50rpx;
		line-height: 100rpx;
		box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
		position: fixed;
		bottom: 100rpx;
		right: 100rpx;
		color: #ababab;
	}

	.addComment:hover {
		box-shadow: 0 4px 8px rgba(0, 85, 255, 0.1), 0 0 12px rgba(0, 85, 255, 0.1);
		color: #4a4a4a;
	}
</style>
