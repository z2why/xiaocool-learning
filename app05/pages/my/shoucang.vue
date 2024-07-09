<template>
	<view class="recovery_list">
		<!-- 取消删除 -->
		<view class="cancal-delete" :class="show">
			<cl-button type="primary" fill @click="cancalDelete">取消删除</cl-button>
		</view>
		<view class="fenge">
			<view class="recovery_list2" v-for="item in collectCourseList" hover-stop-propagation :key="item.couId">
				<view :class="qwq" @click="deleteCollect(item.couId)">
					<!-- toast-error -->
					<cl-icon :name="icon" hover-stop-propagation></cl-icon>
				</view>
				<view class="recovery_list3 " :class="recovery" @click.stop="goLife(item.have,item.couId)">
					<view class="imgView">
						<img :src="item.cover"></img>
					</view>
					<view class="content">
						<view class="bianji">
							<view class="content-top">
								<text style="padding-left: 2px;padding-top: 4px;">{{item.couName}}</text>
								<view class="jifen">
									<view class="jfen">
										<text>{{item.couPrice}}</text>
										<image  src="@/static/images/recorvey/jifen.png"></image>
									</view>
								</view>
							</view>

							<view class="bianji2">
								<text>{{item.couIntroduction}}</text>
							</view>
							<view style="position: relative;bottom: 15rpx;font-size: 15px;font-weight: 200;" class="keshi">
								<view>
									<image style="position: relative; left: 4px;top: 4rpx;" class="kshi" src="@/static/images/recorvey/keshi.png"></image>
									<text>{{item.couChaNum}}课时</text>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>

</template>

<script>
	export default {
		data() {
			return {
				user: {},
				collectCourseList: [],
				category: '',
				delectView: '',
				recovery: '',
				icon: '',
				a: 0,
				show: '',
				qwq:''
			}
		},
		mounted() {
			//#ifdef H5
			document.querySelector('.uni-page-head .uni-page-head-ft .uni-page-head-btn .uni-btn-icon').setAttribute("style","margin-top: 5px;position: relative;")
			//#endif
		},
		created() {
			this.user = uni.getStorageSync("user")
			this.load()
			console.log(this.user, 'user')
		},
		onNavigationBarButtonTap(e) {
       this.cancalDelete()
		   },
		methods: {
			load() {
				const form = {
					stuId: this.user.stuId,
					collect: 1,
					pageNum: 1,
					pageSize: 10,
				}
				this.$api('mycourse', form).then(res => {
					console.log(res, 'load')
					this.collectCourseList = res.data.records

				})
			},
			//取消删除
			cancalDelete(){
	
					this.delectView = this.delectView == false ? true : false;
					let str = this.delectView == false ? '删除' : '完成'
					this.recovery = this.delectView == false ? '': 're'
					this.qwq = this.delectView == false ? '' : 'delect'
					this.icon= this.delectView == false ?'':'toast-error'  
					this.show = this.delectView == false ?  '':'show'
			//#ifdef H5		
	       document.querySelectorAll('.uni-page-head .uni-page-head-ft .uni-page-head-btn')[0].querySelector('.uni-btn-icon').innerText=str;
	// 注：[1]为按钮index
	
	      //#endif
	
			},
			//删除
			deleteCollect(couId) {
				//console.log(couId, "课程id")
				const form = {
					couId: couId,
					stuId: this.user.stuId,
				}
				this.$api('collectCourse', form).then(res => {
					if (res.code === '200') {
						this.load()
						uni.showToast({
							title: '删除成功！',
							icon: 'success'
						})
					} else {
						uni.showToast({
							title: '删除失败！',
							icon: 'error'
						})
					}
				})
			},
			//跳转到课程详情页面
			goLife(have, couId) {
				if (have === 1) {
					this.category = true
				} else {
					this.category = 'flase'
				}
				uni.navigateTo({
					url: '/pages/index/live_brief/live_brief?category=' + this.category + '&couId=' + couId
				})
			},
		}
	}
</script>

<style lang="scss">

	.recovery_list {
		margin-top: 8px;

		.cancal-delete{
			width:95%;
			position:fixed;
			bottom:0;
			margin: auto;
			z-index: 100;
			padding: 20rpx;
			background-color: #fcfcfc;
			display: none;
		}
		.show{
			display: block;
		}
		.fenge {
			margin: 20rpx;
		}

		.recovery_list2 {

			.delect {
				position: relative;
				top: 5px;
				left: 620rpx;
				z-index: 99;
				background-color: white;
				width: 100rpx;
				height: 200rpx;
				display: flex;
				justify-content: center;
				align-items: center;
			}

			.re {
				margin-top: -100px;
			}

			.recovery_list3 {

				width: 100%;
				display: flex;
				height: 210rpx;
				padding: 5px 0;
				border-bottom: 1px solid #f0f0f0;

				.imgView {
					width: 300rpx;
					height: 200rpx;
					background-color: #ccc;

					img {
						width: 300rpx;
						height: 200rpx;
					}
				}

				.content {

					.jfen {
						image {
							width: 30rpx;
							height: 30rpx;
						}
					}

					.bianji {
						margin-left: 20rpx;

						.content-top {
							display: flex;
							justify-content: space-between;

						}
					}

					.bianji2 {
						color: #ccc;
						font-size: 27rpx;
						margin-left: 7rpx;
						margin-top: 12rpx;
						display: -webkit-box;
						-webkit-box-orient: vertical;
						-webkit-line-clamp: 2;
						overflow: hidden;
					}

					.jifen {
						color: #d71345;
						padding-left: 48rpx;
						
					}

					.keshi {
						
						margin-top: 30rpx;
						display: flex;
						justify-content: space-between;

						image {
							margin-right: 25rpx;
							width: 35rpx;
							height: 35rpx;
						}

					}
				}
			}
		}
	}
</style>
