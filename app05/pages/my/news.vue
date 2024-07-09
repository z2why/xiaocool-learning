<template>
	<view>
		<view class="ordering">
			<block v-for="(item,index) in order" :key="index">
				<text :class="{activetext:index==num}" @click="menubtn(index)">{{item}}</text>
			</block>
		</view>
		<view :class="[hideing === 1 ? 'actineclass' : 'errorclass']">
			<view class="Answers" v-for="item in AnswersList" @click="goQuestion(item.answeredTqId,item.questionCon)">
				<view class="answerTop">{{item.content}}</view>
				<view class="answerButtom">
					<view class="QuestionT">{{item.questionCon}}</view>
					<view class="alseCaina" v-if="item.isSelect==1&item.questionCon!='该问题已删除'">已采纳</view>
				</view>
			</view>
		</view>
		<view :class="[hideing === 0? 'actineclass' : 'errorclass']">
			<view class="Questions" v-for="item in QuestionList" @click="getQuestion(item.tqId)">
				<view class="question">{{item.content}}</view> 
				<view class="data">{{item.createTime}}</view>
			</view>
		</view>
		<view :class="[hideing === 2? 'actineclass' : 'errorclass']">
			<view class="Evaluates" v-for="item in EvaluateList" @click="getCourse(item.couId)">
				<view class="evaluate">{{item.couAppraise}}</view>
				<view class="CourseName">{{item.couName}}</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: [],
				num: 0,
				hideing: 0,
				order: ['提问', '回答', '评价'],
				//回答
				answerinfo: {
					pageSize: 20,
					pageNum: 1,
					uId: '',
					isQuestion: 0
				},
				answeredTqId: '',
				answerinfotwo: {
					pageSize: 1,
					pageNum: 1,
					tqId: ''
				},
				AnswersList: [],
				questionInfo: {
					pageSize: 20,
					pageNum: 1,
					uId: '',
					isQuestion: 1
				},
				couEvaluate: {
					pageSize: 20,
					pageNum: 1,
					stuId: ''
				},
				selectCouName: {
					pageSize: 1,
					pageNum: 1,
					couId: ''
				},
				QuestionList: [],
				EvaluateList: []
			}
		},
		created() {
			// this.user = uni.getStorageSync("user")
		},
		onLoad() {
			// this.getInfo()
			this.getAnswerList()
			this.getQuestionList()
			this.getEvaluateList()
		},
		methods: {
			menubtn(index) {
				this.num = index
				this.hideing = index
			},
			getAnswerList() {
				this.user = uni.getStorageSync("user")
				console.log("执行了getAnswerList")
				this.answerinfo.uId = this.user.id
				// console.log(this.answerinfo, "answerinfo")
				this.$api("questionInfo", this.answerinfo).then(res => {
					if (res.code == 200) {
						this.AnswersList = res.data.records
						for (let i = 0; i < this.AnswersList.length; i++) {
							// console.log("进入循环")
							// console.log(this.AnswersList[i].answeredTqId,"answeredTqId")
							this.answerinfotwo.tqId = this.AnswersList[i].answeredTqId
							// console.log(this.answerinfotwo,"answerinfotwo")
							this.$api("questionInfo", this.answerinfotwo).then(res => {
								if (res.code == 200) {
									// console.log(res.data.records[0], "content")
									if (res.data.records.length == 0) {
										this.AnswersList[i].questionCon = "该问题已删除"
									} else {
										this.AnswersList[i].questionCon = res.data.records[0].content
									}
									// console.log(this.AnswersList[i])
								}
							})
						}
					}
				})
			},
			getQuestionCon() {
				this.$api("questionInfo", this.answerinfotwo).then(res => {
					if (res.code == 200) {
						console.log(res.data.records[0], "content")
					}
				})
			},
			// 提问
			getQuestionList() {
				this.user = uni.getStorageSync("user")
				this.questionInfo.stuId = this.user.stuId
				console.log("addA", this.questionInfo)
				this.$api("questionInfo", this.questionInfo).then(res => {
					if (res.code == 200) {
						this.QuestionList = res.data.records
					}
				})
			},
			getEvaluateList() {
				this.user = uni.getStorageSync("user")
				console.log(this.user,"user")
				this.couEvaluate.stuId = this.user.stuId
				console.log(this.couEvaluate,"couEvaluate")
				this.$api("couEvaluate", this.couEvaluate).then(res => {
					if (res.code == 200) {
						this.EvaluateList = res.data.records
						console.log(this.EvaluateList,"EvaluateList")
						// console.log(this.EvaluateList, "this.EvaluateList")
						for (let i = 0; i < this.EvaluateList.length; i++) {
							this.selectCouName.couId = this.EvaluateList[i].couId
							this.$api("selectCouName", this.selectCouName).then(res => {
								if (res.code == 200) {
									if (res.data.records.length == 0) {
										this.EvaluateList[i].couName = "该课程已删除"
									} else {
										this.EvaluateList[i].couName = res.data.records[0].couName
									}
								}
								// console.log(this.EvaluateList[i], "this.EvaluateList[i]")
							})
						}
					}
				})
			},
			// 点击答案跳转到问题详细页
			goQuestion(tqId, questionCon) {
				if (questionCon == "该问题已删除") {
					uni.showToast({
						title: "该问题已被删除！！！",
						duration: 2000
					})
				} else {
					uni.navigateTo({
						url: '/pages/discover/questionS?tqId=' + tqId
					})
				}
			},
			getQuestion(tqId) {
				uni.navigateTo({
					url: '/pages/discover/questionS?tqId=' + tqId
				})
			},
			getCourse(couId) {
				uni.navigateTo({
					url: '/pages/index/live_brief/live_brief?category=flase&couId=' + couId
				})
			}
		}
	}
</script>

<style lang="scss">
	.ordering {
		margin: 20rpx;
		margin-bottom: 0rpx;
		display: flex;

		text {
			margin-right: 60rpx;
			display: block;
			height: 60rpx;
			line-height: 60rpx;
			font-size: 33rpx;
			width: 100rpx;
			text-align: center;
		}

		.activetext {
			color: #6495ED;
			font-weight: bold;
			border-bottom: 5rpx solid #6495ED;
		}
	}

	.actineclass {
		display: block;
	}

	.errorclass {
		display: none;
	}

	.Answers {
		border-bottom: 2px solid gainsboro;
		margin: 20rpx;
		margin-bottom: 0rpx;
		flex-direction: column;
		justify-content: space-between;

		.answerTop {
			line-height: 50rpx;
			font-size: 38rpx;
			overflow: hidden;
			-webkit-line-clamp: 2;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			text-overflow: ellipsis;
		}

		.answerButtom {
			line-height: 70rpx;

			.QuestionT {
				font-size: 33rpx;
				color: gray;
				overflow: hidden;
				-webkit-line-clamp: 1;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				text-overflow: ellipsis;
			}

			.alseCaina {
				font-size: 28rpx;
				background-color: #6495ED;
				border-radius: 30rpx;
				color: #fff;
				width: 120rpx;
				height: 55rpx;
				line-height: 55rpx;
				margin-left: 0rpx;
				margin-right: auto;
				text-align: center;
				margin-top: 5rpx;
				font-weight: bold;
				margin-bottom: 15rpx;
			}
		}
	}

	.Questions {
		border-bottom: 2px solid gainsboro;
		margin: 20rpx;
		margin-bottom: 0rpx;
		flex-direction: column;
		justify-content: space-between;

		.question {
			line-height: 50rpx;
			line-height: 50rpx;
			font-size: 38rpx;
			overflow: hidden;
			-webkit-line-clamp: 2;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			text-overflow: ellipsis;
		}

		.data {
			line-height: 70rpx;
			font-size: 33rpx;
			color: gray;
		}
	}

	.Evaluates {
		border-bottom: 2px solid gainsboro;
		margin: 20rpx;
		margin-bottom: 0rpx;
		flex-direction: column;
		justify-content: space-between;

		.evaluate {
			line-height: 50rpx;
			font-size: 38rpx;
			overflow: hidden;
			-webkit-line-clamp: 2;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			text-overflow: ellipsis;
		}

		.CourseName {
			line-height: 70rpx;
			font-size: 33rpx;
			color: gray;
		}
	}
</style>
