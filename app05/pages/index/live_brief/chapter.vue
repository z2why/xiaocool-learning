<template>
	<view class="box">
		<h3>第{{chapterContent.chaIndex}}章 {{chapterContent.chaTitle}}</h3>
		<video id="videoWrap" class="video-wrap" :src="chapterContent.chaUrl" :controls="isControls" :show-center-play-btn="true"
			object-fit="contain" :poster="photo" @timeupdate="videoTimeUpdateEvent" :custom-cache="false" @pause="handlePause"
			@play="onPlayerPlay($event)" :initial-time="initialTime" show-mute-btn :muted="isMuted" @ended="handleEnded"
			:show-play-btn="true" :enable-play-gesture="true" :enable-progress-gesture="false"  
			@error="videoErrorCallback" @fullscreenchange="fullscreenchange"></video>

		<!-- 控制按钮 - 播放 -->
		<view v-if="!isPlaying" @tap="handlePlay"></view>
		<!-- 控制按钮 - 暂停 -->
		<view v-else @tap="handlePause"></view>

		<!-- 音量开关 - 取消静音 -->
		<view v-if="isMuted" @tap="setIsMuted(false)"></view>
		<!-- 音量开关 - 静音 -->
		<view v-else @tap="setIsMuted(true)"></view>

		<!-- 点击遮罩 -->
		<view class="mask" @click.stop="requestFullScreen"></view>

		<view class="LessonDetails-jianjie">
			<view class="LessonDetails-jianjie-top">

				<view class="LessonDetails-jianjie-top-item" v-for="(item,index) in list" :key="index"
					:class="[active===index?'addnav':'']" @click="cutNav(index,item.navid)">
					{{item.navname}}
				</view>
			</view>
			<!-- <view class="LessonDetails-jianjie-bottom"></view> -->

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
						<text class="text">{{item.chaAppraise}}</text>
						<view class="delete" @click="deleteCom(item.id)" v-if="item.stuId == user.stuId">删除</view>
					</view>
				</view>
			</view>
			<!-- 新增评价 -->
			<button v-if="navMain == 2" class="addComment" @click="addComment">+</button>
		</view>
		
		<uni-popup ref="inputDialog" type="dialog">
			<uni-popup-dialog ref="inputClose" title="您确定要删除这条评论吗？" @confirm="dialogInputConfirm">
			</uni-popup-dialog>
		</uni-popup>
	</view>



</template>

<script>
	export default {
		data() {
			return {
				video_real_time: 0, //实时播放进度
				nitial_time: '', //视频跳转进度 秒
                qwq:'',
				couId: '',
				chaId: '',
				chaApp:{},
				courseConsultList: {},
				newList: [
					'1'
				],
				LEARNING: '',
				COMPALETE: '',
				stuchapter: [],
				user: {},
				active: 0,
				navMain: 1,
				secondDate: [],
				list: [{
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
				initialTime:'',
				is_fast_switch: 0,
				chapterContent: {},
				chapter: {},
				videoUrl: "",
				photo: 'https://static.roncoos.com/os/000.jpg',
				// 正在播放
				isPlaying: true,
				// 静音
				isMuted: false,
				// 控制条
				isControls: true,
				duration:'',
				currentTime:'',
				time:0,
				watchTime:'',
				progress:{},
				student:{}
			}
		},
		onLoad(option) {
			this.couId = option.couId
			this.chaId = option.chaId
			this.user = uni.getStorageSync("user")
			this.initial_time = '0' //视频进度
			let that=this
			setTimeout(that.load(),1000)
            
		},
		mounted() {
			this.videoContext = uni.createVideoContext("videoWrap", this);
		},
		created(){
			    const stu={
					stuId:this.user.stuId
				}
				this.$api('findstudent', stu).then(res => {
								this.student=res.data

							})
		},
		onShow(){
			this.load()
		},
		methods: {

			load() {

								
				this.$api('learningProgress', {
								couId: this.couId,
								stuId: this.user.stuId
				 }).then(res => {
						this.stuchapter = res.data		
					
				})
								this.$api('coursechapter', {
									courseId: this.couId
								}).then(res => {
									this.chapter = res.data
									//console.log(this.chapter,'chapter')
									for(var i=0;i<this.stuchapter.length;i++){
												for(var j=0;j<this.chapter.length;j++){
												   if(this.chapter[j].chaId===this.stuchapter[i].chaId){
													   this.chapter[j].learnedStatus=this.stuchapter[i].learnedStatus
										          }
									}	}
									
								})
				this.$api('learningProgress', {
								couId: this.couId,
								stuId: this.user.stuId,
								chaId: this.chaId
				 }).then(res => {
					 if(res.data.length!==0){	
						 this.initialTime=res.data[0].mediaProgress
					     
				 }
				 else{
					 this.initialTime=0
				 }}
				 )
				
				this.$api('findChaApp', {
					couId: this.couId,
					chaId: this.chaId
				}).then(res => {
					console.log(res)
					this.courseConsultList = res.data
				})

				this.$api('coursechapter', {
					courseId: this.couId,
					chaId: this.chaId
				}).then(res => {
					this.chapterContent = res.data[0]
					console.log(this.chapterContent.chaUrl)
				})


			},
			
			//新增课程评价
			addComment() {
				uni.navigateTo({
					url: '/pages/index/live_brief/addCouChaComment?couId=' + this.couId +'&chaId='+this.chaId
				})
			},
			
			deleteCom(chaAppId) {
				// console.log(cId, "cId")
				this.chaApp.id = chaAppId
				this.$refs.inputDialog.open()
			},
			
			dialogInputConfirm(val) {
				this.$api('deleteChaApp', this.chaApp).then(res => {
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
			
			// 播放视频
			handlePlay() {
				this.isPlaying = true;
				// 调用 play() 必须使用 uni.createVideoContext 创建 video
				this.videoContext.play();
			},

			// 暂停视频
			handlePause() {
				console.log("我暂停了")
				this.isPlaying = false;
				clearInterval(this.qwq)
				this.load()
				// 调用 pause() 必须使用 uni.createVideoContext 创建 video
				this.videoContext.pause();
			},

			// 设置静音
			setIsMuted(val) {
				this.isMuted = val;
				// 注意: 必须在组件写上 muted 属性
				this.videoContext.muted = val;
			},
            
	


			// 开始播放
			onPlayerPlay(player) {
				console.log("开始播放了")
				setTimeout(this.load(),10000)	
                this.qwq= setInterval(this.watch,2000);
				if (this.is_fast_switch == 0) {
			        //#ifdef H5  		
					document.querySelector(".uni-video-progress-container").style.pointerEvents = "none";
			        //#endif

			
				}
				
				
				
			},

			// 播放结束
			handleEnded() {
				this.isPlaying = false;
				clearInterval(this.q)
				this.load()
		              this.student.stuScore=this.student.stuScore+5
					this.$api('savestudent', this.student).then(res => {
					 console.log(res)
					 uni.showToast({
					 	title: '恭喜您已获取'+5+'积分！',
					 	icon: 'none'
					 })
				})
			},

			// 播放错误
			videoErrorCallback(e) {
			 console.log(e+"2222222222222")
			},

			// 全屏
			requestFullScreen() {
				this.videoContext.requestFullScreen();
			},
			//跳转到章节页面
			cha(chaId) {
				uni.navigateTo({
					url: '/pages/index/live_brief/chapter?couId=' + this.couId + '&chaId=' + chaId
				})
			},

			cutNav(index, id) {
				this.active = index
				this.navMain = id
				const res = this.list[id].store
				this.secondDate = res
			},
	       watch(){
			  
			  if(this.time===0){
				  this.time=this.watchTime
			  }
			   const form1={
				   couId: this.couId,
				   chaId: this.chaId,
				   stuId: this.user.stuId,
				   mediaDuration:this.duration,
				   mediaProgress:this.watchTime,
				   time: 2
			   }
				this.$api('watch', form1).then(res => {
	
				})
			},
			
			

			videoTimeUpdateEvent: function(e) { // 播放进度改变


				var isReady = 1; // 是否开启可以视频快进 1 禁止开启
				//跳转到指定播放位置 initial-time 时间为秒
				let that = this;
				//播放的总时长
				that.duration = e.detail.duration
				
				that.watchTime= e.detail.currentTime
				//实时播放进度 秒数
				that.currentTime = parseInt(e.detail.currentTime)
				//当前视频进度
				// console.log("视频播放到第" + currentTime + "秒")//查看正在播放时间，以秒为单位
				if (that.video_real_time == 0) {
					var jump_time = parseInt(that.initial_time) + parseInt(that.video_real_time)
				} else {
					var jump_time = parseInt(that.video_real_time)
				}
				if (isReady == 1) {
					if (that.currentTime > jump_time && that.currentTime - jump_time > 3) {
						let videoContext = wx.createVideoContext('myVideo')
						videoContext.seek(that.video_real_time)
				
					}
				}
				
				
				console.log('视频播放时长', that.currentTime, '视频总时长', that.duration)
				that.video_real_time = that.currentTime //实时播放进度   
			},

			// 全屏监听
			fullscreenchange(e) {
				if (e.detail.fullScreen) {
					// 全屏
					this.videoContext.play();
					this.isControls = true;
				} else {
					// 退出全屏
					// this.videoContext.pause();
					this.isControls = false;
				}
			},
		},
	}
</script>

<style lang="scss">
	.box {
		padding: 25rpx;
        width: 100%;
		h3 {
			text-align: center;
			margin-bottom: 30rpx;
		}

		.video-wrap {
				
			min-width: 700rpx;
			height: 400rpx;
		}
	}


	.LessonDetails-jianjie {

		.LessonDetails-jianjie-top {
			width: 100%;
			height: 80rpx;
			display: flex;
			justify-content: center;
			align-items: center;
			background-color: #ffffff;

			.LessonDetails-jianjie-top-item {
				width: 33vh;
				height: 80rpx;
				text-align: center;
				padding-top: 10rpx;
			}

			.LessonDetails-jianjie-top-item::before {
				position: absolute;
				content: '|';
				top: 5rpx;
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

	.bigbox {
		height: 650rpx;
		margin-bottom: 15px;

		.nolearn {
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
				width: 100rpx;
				height: 100rpx;
				padding-right: 20rpx;

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

			text {
				height: 50rpx;
			}

			.name {
				color: #d58e00;
			}

			.text {
			    font-size: unset;
				color: #4f4f4f;
				
			}
			.delete {
				color: firebrick;
				font-size: x-small;
				
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
