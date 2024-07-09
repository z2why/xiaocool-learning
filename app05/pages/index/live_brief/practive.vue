<template>

	<view class="box" >
		<view class="practive-content">
			<cl-list-item justify="center"  :border="false"><strong>{{practiveList.praName}}</strong></cl-list-item>
			<cl-list-item label="任务内容 :" justify="start"  :border="false">{{practiveList.praMain}}</cl-list-item>
			<cl-list-item label="所属课程 :" justify="start"  :border="false">{{practiveList.couName}}</cl-list-item>
			<cl-list-item label="指导老师 :" justify="start"  :border="false">{{practiveList.truename}}</cl-list-item>
			<cl-list-item label="地址 :" justify="start"  :border="false">{{practiveList.praSite}}</cl-list-item>
		</view>
		<view class="status" >
			<cl-list-item label="状态 :" justify="start"  :border="false" v-if="status === 0">待审核</cl-list-item>
			<cl-list-item label="状态 :" justify="start"  :border="false" v-if="status === 1">审核通过</cl-list-item>
			<cl-list-item label="状态 :" justify="start"  :border="false" v-if="status === 2">审核不通过</cl-list-item>
		</view>
		<cl-button  type="primary" v-if="status === 3" fill @click="addPractive">申请</cl-button>
		<cl-button type="primary" v-if="status === 0"  @click="addPractive" fill>取消报名</cl-button>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				couId: '',
				practiveList:{},
				user: {},
				praId: '',
				status: 3,
				audit:''
			}
		},
		onLoad(option) {
			this.couId = option.couId
			this.user = uni.getStorageSync("user")
			this.praId = option.praId
			this.couName = option.couName
		},
		created() {
			this.load()
		},
		methods:{
			load(){
				const form1 = {
					couId: this.couId,
					praId: this.praId,
					pageNum:1.,
					pageSize:1
				}
				this.$api('practive',form1).then(res => {
					console.log(res,'线下实践')
					this.practiveList = res.data.records[0]

					
					//查找学生报名线下实践
					const form2 = {
						stuId: this.user.stuId,
						praId: this.praId,
						couId: this.couId,
						pageNum: 1,
						pageSize: 1
					}
					this.$api('findStuPractive',form2).then(res => {
						console.log(res,'==================>')
						this.status = res.data.records[0].audit
					})
				})
				
				
			},
			//报名线下实践
			addPractive(){
				//console.log(this.practiveList.praId)
				const form = {
					stuId: this.user.stuId,
					praId: this.praId
				}
				this.$api('addPractive',form).then(res => {
					if(res.code === '200'){
						this.load()
						uni.showToast({
							title: res.data,
							icon: 'success',
							duration: 2000
						})
					}else{
						uni.showToast({
							title: '申请失败！',
							icon: 'error'
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	 page {
	      background-color: #fcfcfc;
	  }
	
	.box {
		padding: 0 30rpx;
		/deep/.practive-content {
			margin-top: 30rpx;
			margin-bottom: 30rpx;
			box-shadow: 0 1px 2px rgba(0, 0, 0, .11), 0 0 4px rgba(0, 0, 0, .04);
			.cl-list-item__label, .cl-list-item__content, .cl-list-item__append {
				font-size: 30rpx;
			}
		}
		/deep/.status{
			margin-bottom: 30rpx;
			background-color:  #fafafa;
			box-shadow: 0 1px 2px rgba(0, 0, 0, .11), 0 0 4px rgba(0, 0, 0, .04);
			.cl-list-item__label, .cl-list-item__content, .cl-list-item__append {
				font-size: 30rpx;
			}
		}	
	}

</style>