<template>
	<view class="question">
		<view class="questionMain" v-for="item in question" :key="item.tqId">
			<view class="top">
				<image :src="item.avatar"></image>
				<text>{{item.truename}}</text>
			</view>
			<view class="content">
				<view>{{item.content}}</view>
			</view>
			<view class="deleteQ" @click="deleteQuestion(item.tqId)" v-if="item.uid==addAnswer.uid">删除</view>
			<view class="data">{{item.createTime}}</view>
			<view class="info iconfont icon-fenxiang" @click="shareToggle">分享</view>
		</view>
		<view class=" answerMain">
			<view class="atitel">观点</view>
			<view class="answers_item" v-for="item in answerslist" :key="item.tqId">
				<view class="image">
					<image :src="item.avatar"></image>
				</view>
				<view class="right">
					<view class="aname">
						<text>{{item.truename}}</text>
						<text>{{item.createTime}}</text>
					</view>
					<view class="answer">
						<text>{{item.content}}</text>
					</view>
					<view class="deletetCai">
						<view class="delete" @click="deleteAn(item.tqId)" v-if="item.uid==addAnswer.uid">删除</view>
						<view class="caina" @click="addCaina(item.tqId,item.isSelect,item.answeredTqId,item.uid)" v-if="item.uid!=addAnswer.uid&question[0].uid==addAnswer.uid">{{item.isSelect}}</view>
					</view>
				</view>
			</view>
		</view>
		<view class="question-bottom">
			<input placeholder="欢迎发表你的观点" type="text" @input="onInput" :v-module='inputcontent' :value="inputcontent" />
			<view @click="send" class="postbutton">发表</view>
		</view>
		<view>
			<!-- 分享示例 -->
			<uni-popup ref="share" type="share" safeArea backgroundColor="#fff">
				<uni-popup-share></uni-popup-share>
			</uni-popup>
		</view>
		<view>
			<!-- 输入框示例 -->
			<uni-popup ref="inputAnswer" type="dialog">
				<uni-popup-dialog ref="inputClose" title="您确定要删除这条评论吗？" @confirm="dialogInputAn">
				</uni-popup-dialog>
			</uni-popup>
		</view>
		<view>
			<!-- 输入框示例 -->
			<uni-popup ref="inputQuestion" type="dialog">
				<uni-popup-dialog ref="inputClose" title="您确定要删除这条问题吗？" @confirm="dialogInputQuest">
				</uni-popup-dialog>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				inputcontent: '',
				getquesttion: {
					tqId: '',
					pageSize: 1,
					pageNum: 1,
				},
				deleteQuest: {
					tqId: '',
				},
				answer: {
					pageSize: 20,
					pageNum: 1,
					isQuestion: 0,
					answeredTqId: ''
				},
				addAnswer: {
					uid: '',
					truename: '',
					content: '',
					answeredTqId: ''
				},
				question: [],
				answerslist: [],
				deleteA: {
					tqId: ''
				},
				Acaina:{
					tqId:'',
					isSelect:'',
					answeredTqId:'',
					uid:''
				}
			}
		},
		created() {
			// this.getQuestion()
			// this.getAnswer()
		},
		methods: {
			//从Storage取数据
			getInfo(){
				let vm=this
				uni.getStorage({     
					key:'user',
					success(res) {
						console.log('获取成功',res.data);
						vm.addAnswer.uid=res.data.id
						vm.addAnswer.truename=res.data.truename
					}
				})
				console.log("addA",this.addAnswer)
			},
			//分享
			shareToggle() {
				this.$refs.share.open()
			},
			//获取文本框的内容
			onInput(e) {
				this.inputcontent = e.target.value
			},
			//点击确认按钮
			send() {
				if (this.inputcontent == '') {
					uni.showToast({
						title: '请输入您的观点！',
						icon: 'none',
						duration: 2000
					})
				} else if (this.inputcontent != '') {
					this.addAnswer.content = this.inputcontent
					this.$api("addAnswer", this.addAnswer).then(res => {
						if (res.code == 200) {
							uni.showToast({
								title: "评论成功",
								duration: 2000
							})
							this.getAnswer()
						} else {
							uni.showToast({
								title: "评论失败",
								duration: 2000
							})
						}
					})
					this.inputcontent = ''
				}
			},
			deleteAn(tqId) {
				this.deleteA.tqId = tqId
				// console.log(this.deleteA, "deleteA")
				this.$refs.inputAnswer.open()
			},
			dialogInputAn(){
				this.$api("deleteAnswer", this.deleteA).then(res => {
					if (res.code == 200) {
						// console.log(res)
						uni.showToast({
							title: "删除评论成功！",
							duration: 2000
						})
						this.getAnswer()
					} else {
						uni.showToast({
							title: "删除评论失败！",
							icon: 'none',
							duration: 2000
						})
						this.getAnswer()
					}
				})
			},
			deleteQuestion(tqId) {
				this.deleteQuest.tqId = tqId
				// console.log(this.deleteQuest, "deleteQuest")
				this.$refs.inputQuestion.open()
			},
			Back(){
				//uni.navigateBack()//默认delta:1
				uni.navigateBack({
					delta:1,//返回层数，2则上上页
				})
			},
			dialogInputQuest(){
				this.$api("deleteAnswer", this.deleteQuest).then(res => {
					if (res.code == 200) {
						// console.log(res)
						uni.showToast({
							title: "删除问题成功！",
							duration: 2000
						})
						this.Back()
					} else {
						uni.showToast({
							title: "删除问题失败！",
							icon: 'none',
							duration: 2000
						})
					}
				})
			},
			getQuestion() {
				this.$api("questionInfo", this.getquesttion).then(res => {
					if (res.code == 200) {
						this.question = res.data.records
						console.log(this.question,"question")
					}
				})
			},
			getAnswer() {
				this.$api("questionInfo", this.answer).then(res => {
					if (res.code == 200) {
						this.answerslist = res.data.records
						console.log(this.answerslist)
						for(let i=0;i<this.answerslist.length;i++){
							if(this.answerslist[i].isSelect==0){
								this.answerslist[i].isSelect='采纳'
							}else if(this.answerslist[i].isSelect==1){
								this.answerslist[i].isSelect='已采纳'
							}
						}
						console.log(this.answerslist,"answerslist")
					}
				})
			},
			// 采纳回答
			addCaina(tqId,isSelect,answeredTqId,uid){
				if(isSelect=='采纳'){
					this.Acaina.isSelect=1
					this.Acaina.tqId=tqId
					this.Acaina.answeredTqId=answeredTqId
					this.Acaina.uid=uid
					console.log(this.Acaina)
					this.$api("addCaina",this.Acaina).then(res =>{
						if(res.code==200){
							uni.showToast({
								title: "采纳答案成功！",
								duration: 2000
							})
							this.getAnswer()
						}
					})
				}else if(isSelect!='采纳'){
					this.Acaina.isSelect=0
					this.Acaina.tqId=tqId
					this.Acaina.answeredTqId=answeredTqId
					this.Acaina.uid=uid
					console.log(this.Acaina)
					this.$api("addCaina",this.Acaina).then(res =>{
						if(res.code==200){
							uni.showToast({
								title: "取消采纳成功！",
								duration: 2000
							})
							this.getAnswer()
						}
					})
				}
			}
		},
		onShow(){
			// console.log("onShow")
		},
		onLoad(options) {
			// console.log(options.tqId, "tqId")
			this.getquesttion.tqId = options.tqId
			this.answer.answeredTqId = options.tqId
			this.addAnswer.answeredTqId = options.tqId
			this.getQuestion()
			this.getAnswer()
			this.getInfo()
		}
	}
</script>

<style lang="scss">
	.question {
		line-height: 60rpx;
		padding-bottom: 120rpx;

		.questionMain {
			padding: 20rpx 20rpx 20rpx 20rpx;
			flex-direction: column;

			.top {
				line-height: 100rpx;
				padding-bottom: 15rpx;
				font-size: 35rpx;
				font-weight: bold;
				display: flex;

				image,
				text {
					align-items: center;
				}

				text {
					margin-left: 15rpx;
				}

				image {
					min-width: 100rpx;
					max-width: 100rpx;
					height: 100rpx;
					border-radius: 50%;
				}
			}

			.content {
				font-size: 35rpx;
				padding-bottom: 15rpx;
				// image{
				// 	min-width: 100%;
				// 	max-width: 100%;
				// 	height: 400rpx;
				// 	margin-top: 20rpx;
				// }
			}

			.deleteQ {
				color: firebrick;
				font-size: 30rpx;
			}

			.data {
				font-size: 35rpx;
				color: gray;
				padding-bottom: 15rpx;
			}

			.info {
				font-size: 35rpx;
			}
		}

		.answerMain {
			padding: 20rpx 20rpx 20rpx 20rpx;

			.atitel {
				padding-top: 30rpx;
				font-weight: bold;
				padding-bottom: 15rpx;
				border-bottom: 1px solid gainsboro;
				font-size: 40rpx;
			}

			.answers_item {
				border-bottom: 1px solid gainsboro;
				display: flex;
				padding-bottom: 15rpx;
				font-size: 35rpx;
				padding-top: 20rpx;
				.image{
					width: 18%;
					image {
						min-width: 100rpx;
						max-width: 100rpx;
						height: 100rpx;
						border-radius: 50%;
					}
				}
				.right {
					width: 82%;
					margin-left: auto;
					margin-right: 0rpx;
					font-size: 35rpx;
					.answer{
						width: 98%;
						text{
							word-wrap: 
							break-word;
							width: 100px;
						}
					}
					.aname {
						text:nth-child(1) {
							font-weight: bold;
						}

						text:nth-child(2) {
							margin-left: 50rpx;
							margin-right: 0rpx;
							color: gray;
						}
					}
					.deletetCai{
						// background-color: darkcyan;
						position: relative;
						height: 55rpx;
						.caina{
							font-size: 28rpx;
							border: 3px solid #6495ED;
							border-radius: 30rpx;
							width: 100rpx;
							color: #6495ED;
							height: 40rpx;
							line-height: 36rpx;
							text-align: center;
							font-weight: bold;
							margin-left: auto;
							margin-right: 0rpx;
						}
						.delete {
							color: firebrick;
							font-size: 30rpx;
						}
						.selectCaina{
							color: #6495ED;
							font-size: 30rpx;
						}
					}
				}
			}
		}

		.question-bottom {
			border-top: 1px solid gainsboro;
			padding: 20rpx 20rpx 20rpx 20rpx;
			width: 100%;
			position: fixed;
			bottom: 0;
			background-color: #fff;
			height: 90rpx;
			font-size: 35rpx;
			display: flex;

			input {
				background-color: lightgrey;
				border-radius: 10rpx;
				width: 500rpx;
				height: 90rpx;
				padding-left: 10rpx;
				padding-right: 10rpx;
			}

			.postbutton {
				flex-direction: column;
				width: 150rpx;
				height: 90rpx;
				background-color: #6495ED;
				color: #fff;
				border-radius: 10rpx;
				text-align: center;
				line-height: 90rpx;
				margin-left: 30rpx;
			}
		}
	}
</style>
