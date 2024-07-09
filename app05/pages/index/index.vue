<template>
	<view class="page-index">
		<view class="header">
			<!-- 自定义导航 -->
			<cl-topbar
				title="选课中心"
				background-color="rgba(0,0,0,0)"
				color="#FFFFFF"
				:border="false"
				:show-back="false"
			></cl-topbar>
	
			<view class="banner">
				<cl-swiper
					v-model="selectIndex"
					:list="swiperList"
					height="348rpx"
					radius="0rpx"
					indicator-dots
					circular
					autoplay
				></cl-swiper>
			</view>
		</view>
        <!-- 按钮 -->
		<view class="btns">
			<view @click="returnCourse(1)" >
				<view class="btn1 btn">
					<image src="@/static/font/kangfu.png" mode="aspectFit"></image>
				</view>
				<p>康复</p>
			</view>
			<view @click="returnCourse(2)">
				<view class="btn2 btn">
					<image src="@/static/font/huli.png" mode="aspectFit"></image>
				</view>
				<p>护理</p>
			</view>
			<view @click="returnCourse(3)">
				<view class="btn3 btn">
					<image src="@/static/font/yangsheng.png" mode="aspectFit"></image>
				</view>
				<p>养生</p>
			</view>
			<view @click="returnCourse(4)">
				<view class="btn4 btn">
					<image src="@/static/font/kepu.png" mode="aspectFit"></image>
				</view>
				<p>科普</p>
			</view>
			
		</view>
		
		<!-- 列表 -->
		<view class="content">
			<cl-tabs
				v-model="active"
				:labels="list"
				:border="false"
		        
				:gutter="40"
				color="#5E8AFF"
				un-color="#747474"
				:swipeable="true"
				@tab-change="toChange"
			>
			</cl-tabs>
			<view class="list">
				
					<view
					style="padding-top: 15rpx;padding-bottom: 10rpx;"
						class="subject"
						v-for="(item, couId) in courseList[active]"
						:key="couId"
						@click="goRecovery(item)"
					>
						<cl-image class="picture" @click="goRecovery(item)"  :src="item.cover" mode="aspectFill" ></cl-image>
						<view class="detail">
							<cl-text
							     
								:size="32"
								:value="item.couName"
								color="#303030"
								bold
								:ellipsis="2"
								
							></cl-text>
							<cl-text
								:size="20"
								:value="item.couIntroduction"
								:ellipsis="2"
						
								color="#C5C5C5"
								bold
								block
								:margin="[8, 0, 0, 0]"
							></cl-text>
							<cl-text :size="24" value="单价:  " color="#303030"></cl-text>
							<cl-text
								:size="40"
								:value="item.couPrice"
								color="#FF724E"
								:margin="[10,4,0, 12]"
							></cl-text>
									<cl-text :size="24" value="积分" color="#FF724E"></cl-text>
						</view>
					</view>
				</view>
		
		</view>
	</view>
</template>

<script>
	import api from "@/common/request/api.js"
export default {
	data() {
		return {	
			info:{
	         pageSize:3,
			 pageNum: 1,
			 deleted:0
		
			},
			user: {},
			stuId:'',
			dw:{cId:6},   
			selectIndex: 0,
			swiperList: [
				{ url: require("@/static/images/index/banner1.png") },
				{ url: require("@/static/images/index/banner2.png") },
				{ url: require("@/static/images/index/banner3.png") },
			],
			courseList: [],
			keyword: "",
			active: 0,
			list: [
				{
					label: "康 复",
					name: 0,
				},
				{
					label: "护 理",
					name: 1,
				},
				{
					label: "养 生",
					name: 2,
				},
				{
					label: "科 普",
					name: 3,
				},
				{
					label: "推 荐",
					name: 4,
				},
			],
		};
	},
	created(){
		this.user = uni.getStorageSync("user"),
		this.coursenum()
		
	},
	methods: {
		toChange() {
			console.log(this.active);
		},
		//跳转到课程详情页面
		goRecovery(item){
			uni.navigateTo({
				url:'/pages/index/live_brief/live_brief?category=true&couId=' + item.couId
			})
		},
		coursenum(){
			this.info.stuId=this.user.stuId
			this.$api('coursenum',this.info).then(res =>{
				//console.log(res)
				this.courseList=res.data
				//console.log(this.courseList,"courseList")
			})
		},	
		deletedone(){
			// console.log("进入cou")
			this.$api('deletedone',this.dw).then(res =>{
				console.log(res)
				
			})
		},	
		//跳转到课程分类页面
		returnCourse(couType){
			uni.navigateTo({
			    url:'/pages/index/course?couType=' + couType
			})
		}
		
	},
	
};
</script>

<style scoped lang="scss">
page {
	background: #f5f5f5;
}
/deep/ .cl-search {
	background-color: rgba(0, 0, 0, 0);
}
/deep/ .cl-button.cl-button--primary {
	border-radius: 32rpx;
	background: linear-gradient(90deg, #8ebbff 0%, #618cfc 100%);
}
/deep/ .cl-search__input .cl-button {
	right: 12rpx;
	top: 8rpx;
	height: 64rpx;
}
/deep/ .cl-input {
	height: 80rpx;
}
/deep/ .cl-tabs__bar-item.is-active {
	// font-size: 32rpx;
	font-weight: 600;
}
// /deep/ .cl-tabs__bar-item {
// 	padding: 0px 15rpx !important;
// }
// /deep/ .cl-tabs.is-border .cl-tabs__header {
// 	border-bottom: none;
// }
.btns{
	height: 142rpx;
	background-color: #ffffff;
	padding: 20rpx;
	display: flex;
	justify-content: space-around;
	.btn{
		width: 92rpx;
		height: 92rpx;
		border-radius: 50%;
		display: flex;
		justify-content: center;
		align-items: center;
		image{
			width: 60rpx;
			height: 60rpx;
		}
	}
	.btn1{
		background-color: #a0c194;
	}
	.btn2{
		background-color: #e8d098;
	}
	.btn3{
		background-color: #f6e3b7;
	}
	.btn4{
		image{
			width: 80rpx;
			height: 80rpx;
		}
		background-color: #5ab4cc;
	}
	p{
		margin-top: 10rpx;
		text-align: center;
		font-size: 24rpx;
		color: #3c3c3c;
	}
}
.cl-tabs__bar-item is-active{
	padding: 20px;
}
.page-index {
	.header {
		padding-bottom: 16rpx;
		background: linear-gradient(180deg, #618cfc 0%, #f5f5f5 100%);
		.banner {
			margin: 0 auto;
			margin-top: 25rpx;
			width: 696rpx;
			height: 348rpx;
		}
	}

	.content {
		margin-top: 15rpx;
		background: #ffffff;
		.title {
			padding-left: 10px;
			height: 98rpx;
		}
		.list {
			display: flex;
			flex-direction: column;
			.subject {
			
				margin: 0 auto;
				display: flex;
				justify-content: space-between;
				align-items: center;
				height: 258rpx;
				width: 700rpx;
				border-bottom: 2px solid #f5f5f5;
				.picture {
					max-width: 360rpx;
					width: 100%;
					height: 230rpx;
				}
				.detail {
					width: 155px;
					margin-left: 30rpx;
				}
			}
			
				.nav {
		display: flex;
		background-color: #ffffff;
		margin-top: 15rpx;
		margin-bottom: 15rpx;
		
		

		.nav-content {
			// display: flex;
			width: 25%;
			text-align: center;
			padding-top: 20rpx;
			padding-bottom: 20rpx;

			view {
				width: 120rpx;
				height: 120rpx;
				background-color: #aace9e;
				border-radius: 60rpx;
				margin: 10rpx auto;
				line-height: 170rpx;

				image {
					width: 75rpx;
					height: 75rpx;
					text-align: center;

				}
			}

			text {
				font-size: 30rpx;
			}
		}
	}
		}
	}
}
</style>
