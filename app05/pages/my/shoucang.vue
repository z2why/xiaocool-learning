<template>
	<view class="recovery_list">
		<view class="fenge">
		<view class="recovery_list2" v-for="item in collectCourseList" :key="item.couId"  @click.stop="goLife(item.have,item.couId)" >
			<view class="recovery_list3" >
				<view class="imgView">
					<img :src="item.cover"></img>
				</view>	
				<view class="content">
					<view class="bianji">
						<view class="content-top">
							<text>{{item.couName}}</text>
							<view class="jifen">
								<view class="jfen">
									<text>{{item.couPrice}}</text>
								<image src="@/static/images/recorvey/jifen.png"></image>
								</view>
							</view>
						</view>
						
						<view class="bianji2">
							<text>{{item.couIntroduction}}</text>
						</view>
						<view class="keshi">
							<view >
								<image class="kshi" src="@/static/images/recorvey/keshi.png"></image>
								<text>{{item.couCataNum}}课时</text>
							</view>	
							<cl-icon style="margin-top: 12rpx;" name="toast-error" @click="deleteCollect(item.couId)"></cl-icon>
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
			}
		},
		created(){			
			this.user = uni.getStorageSync("user")
			this.load()
			console.log(this.user,'user')
		},
		methods: {
			
			load(){
				const form = {
					stuId: this.user.stuId,
					collect: 1,
					pageNum: 1,
					pageSize: 10,
				}
				this.$api('mycourse',form).then(res => {
					console.log(res,'load')
					this.collectCourseList = res.data.records

				})
			},
			//删除
			deleteCollect(couId){
				console.log(couId)
			},
			//跳转到课程详情页面
			goLife(have,couId){
				if(have === 1){
					this.category = true
				}else{
					this.category = 'flase'
				}
				uni.navigateTo({
					url:'/pages/index/live_brief/live_brief?category='+ this.category + '&couId=' + couId
				})
			},
		}
	}
</script>

<style lang="scss">
	// .daohang{
	// 	display: flex;
	// 	.daohang_item{
	// 		width: 33.3%;
	// 	}
	// }
	.recovery_list{
		margin-top: 8px;
		
	.fenge{
		margin: 20rpx;
	}
	.recovery_list2{
		display: flex;
		padding-left: 0%;
		
		.recovery_list3{
			
			width: 100%;
			display: flex;
			height: 210rpx;
			padding:  5px 0;
			border-bottom: 1px solid #f0f0f0;
			.imgView{
				width: 300rpx;
				height: 200rpx;
				background-color: #ccc;
				img{
					width: 300rpx;
					height: 200rpx;
				}
			}
			.content{
			
			.jfen{
				image{
					width: 30rpx;
					height: 30rpx;
				}
			}
			.bianji{
				margin-left: 30rpx;
				.content-top{
					display: flex;
					justify-content: space-between;
				}
			}
			.bianji2{			
				color: #ccc;
				font-size: 27rpx;
				margin-left: 7rpx;
				margin-top: 12rpx;
				display:-webkit-box; 				
				-webkit-box-orient:vertical; 				
				-webkit-line-clamp:2; 
				overflow: hidden;
			}
			
			.jifen{
				color:#d71345;
				padding-left: 76rpx;
			}
			.keshi{
				margin-top: 30rpx;
				display: flex;
				justify-content: space-between;
				image{
					margin-right: 25rpx;
					width: 40rpx;
					height: 40rpx;
				}
			
			}	
			}
		}
	}
}
</style>
