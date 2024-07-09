<template>
	<view class="shop-cart">


		<template v-if="list.length>0">
			<!-- 自定义导航栏 -->
	<!-- 		<uniNavBar title="购物车" :rightText='isNavBar?"完成":"编辑"' fixed='true' statusBar='true'
				@clickRight='isNavBar = !isNavBar'>
			</uniNavBar> -->

			<!-- 商品内容 -->
			<view class='shop-list' v-for='(item,index) in list' :key="index">
				<view class='shop-item'>
					<label class="radio" style="padding-left: 10rpx;" @tap='selectedItem(index)'>
						<radio value="" color="#FF3333" :checked="item.checked" /><text></text>

					</label>
					<image class='shop-img' :src=item.cover mode=""></image>
					<view class="shop-text">
						<view class='shop-name'>{{item.couName}}</view>
						<view class='shop-color' style="padding-top: 10rpx; height: 100rpx; width: 400rpx;"> <text
								class="couIntroduction" style="display: -webkit-box;
					 overflow: hidden;-webkit-line-clamp:2;
					  -webkit-box-orient: vertical; 
					  text-overflow: ellipsis;">{{item.couIntroduction}}</text></view>
						<view class='shop-price'>
							<view style="position: relative;bottom: 5px;">￥{{item.couPrice}}</view>


							<template v-if="!isNavBar">
								<!-- <view>*{{item.num}}</view> -->
							</template>
							<template v-else>
								<uniNumberBox>
									:value='item.num'
									min='1'
								</uniNumberBox>
							</template>
						</view>
					</view>
				</view>
			</view>

			<!-- 底部 -->

			<view class="shop-foot">
				<label class="radio font-radio">
					<radio value="" color="#FF3333" :checked="checkedAll" @tap="checkedAllFn" /><text>全选</text>
				</label>


				<template v-if="!isNavBar">
					<view class="foot-total">
						<view class="foot-count" style="padding-right: 50rpx;">合计：￥{{totalCount.couPrice}}</view>
						<view class="foot-num" @tap="buy()">结算({{totalCount.num}})</view>

					</view>
				</template>

				<template v-else="!isNavBar">
					<view class="foot-total">
						<!-- 	<view class="foot-num" style="background-color: black;">移入收藏夹</view> -->
						<view class="foot-num" @tap="delDoodsFn">删除</view>
					</view>
				</template>

			</view>
		</template>
		<template v-else>
			<uniNavBar title="购物车" statusBar="true" fixed="true"></uniNavBar>
			<view class="shop-else-list">
				<text>囧~购物车还是空的~</text>
			</view>
		</template>
	</view>
</template>

<script>
	import uniNavBar from "@/components/uni/uni-nav-bar/components/uni-nav-bar/uni-nav-bar.vue"
	import {
		mapState,
		mapActions,
		mapGetters,
		mapMutations
	} from "vuex"
	import uniNumberBox from "@/components/uni/uni-number-box/components/uni-number-box/uni-number-box.vue"
	import api from '../../common/request/api.js'
	export default {
		data() {
			return {
				isNavBar: false,
				user: {},
				shop: {},
				thing: [],
				orderId: '',
			    EditStatus: '',
				couId:'',
				stuId:''
			}
		},
		mounted() {
		//#ifdef H5	
		document.querySelector('.uni-page-head .uni-page-head-ft .uni-page-head-btn .uni-btn-icon').setAttribute("style","margin-top: 5px;position: relative;;right: 8px;")
	    //#endif

		},
		computed: {
			...mapState({
				list: state => state.cart.list,
				selectedList: state => state.cart.selectedList


			}),
			...mapGetters(['checkedAll', 'totalCount'])
		},
		components: {
			uniNavBar,
			uniNumberBox
		},
		created() {
			this.user = uni.getStorageSync("user") //从浏览器缓存中获取
			
			this.shopping()
			console.log(this.user, 'user')
			
		},
		
			// 按钮监听函数
		onNavigationBarButtonTap(e) {
			console.log(e);
				this.isNavBar = this.isNavBar == false ? true : false;
				let str = this.isNavBar == false ? '编辑' : '完成';
		//#ifdef H5			
       document.querySelectorAll('.uni-page-head .uni-page-head-ft .uni-page-head-btn')[0].querySelector('.uni-btn-icon').innerText=str;
// 注：[1]为按钮index
      //#endif

		},

		
		methods: {
			...mapActions(['checkedAllFn', 'delDoodsFn']),
			...mapMutations(['selectedItem', 'initGetList']),
			// 购物车
			// getData(){

			// },
			delDoodsFn() {
				console.log(this.selectedList, "se")
				 const thing1=[]
				 var data={}
			     for(var i=0;i<this.selectedList.length;i++){
					 console.log(this.selectedList[i],"2222222222222") 
			          data.couId=this.selectedList[i]
					  data.stuId=this.user.stuId
					  thing1.push(JSON.parse(JSON.stringify(data)))
					 
				 }
		
				this.$api("deleteCart", thing1).then(res => {
					if (res.code == 200) {
						uni.showToast({
							title: "移除成功！",
						})
						this.shopping()
					} else {
						uni.showToast({
							title: "移除错误！",
							icon: "none"
						})
					}
				})

			},
			buy() {

				if (this.selectedList.length === 0) {
					return uni.showToast({
						title: "请至少选择一件商品",
						icon: "none"
					})
				} else {
					console.log(this.selectedList, "this.selectedList") 
					const arr1 =this.selectedList
					const arr2 = []
					const getAbsentValues = (arr1,arr2) => {
					   let qwq = [];
					   qwq = arr1.filter(el => {
					      return !arr2.find(obj => {
					         return el=== obj.couId ;
					      });
					   });
					   return qwq;
					};
				    const things=getAbsentValues(arr1,arr2)
					const shops=[]
		            const shop={}
					console.log(getAbsentValues(arr1,arr2), '++++')
					for(var i=0;i<things.length;i++)
					{
						shop.couId=things[i]
						shop.stuId=this.user.stuId,	
						shop.uid=this.user.id
						shops.push(shop)
					}
					this.$api('buyCourse', shops).then(res => {
						if (res.code == 200) {
							console.log(res,"res")
							
							uni.showToast({
								title: '购买商品成功！',
								duration: 2000
							})
							this.shopping()
						
						
						}else{
							
							uni.showToast({
								title: res.msg,
								icon: 'none',
								duration: 2000
							})
						}

					})
				}
			},
			shopping() {
				this.$api('shopping', {
					pageNum: 1,
					pageSize: 10,
					stuId: this.user.stuId,
				}).then(res => {
					this.initGetList(res.data.records)

				})
			}
		}

	}
</script>

<style>
	*{
		font-family: Arial, Helvetica, sans-serif
	}
</style>
<style scoped lang="scss">

	
	// 数量过多最底部距离
	.shop-list {
		margin: 20rpx;
	}

	.shop-item {
		display: flex;
		padding: 20 rpx;
		align-items: center;
		background-color: #f7f7f7;
		border-radius: 35rpx;
		margin-bottom: 10rpx;
	}

	.shop-img {
		width: 250rpx;
		height: 200rpx;
	}

	.shop-text {
		flex: 1;
		padding-left: 20rpx;
		padding-top: 20rpx;
	}

	.shop-color {
		font-size: 24rpx;
		color: #ccc;
	}

	.shop-price {
		display: flex;
		justify-content: space-between;
	}

	.shop-foot {
		border-top: 2rpx solid #f7f7f7;
		background-color: #FFFFFf;
		position: fixed;
		bottom: 0;
		left: 0;
		width: 100%;
		height: 100rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.font-radio {
		padding-left: 40rpx;
	}

	.foot-total {
		display: flex;
	}

	.foot-count {
		line-height: 100rpx;
	}

	.foot-num {
		background-color: red;
		color: #FFFFFF;
		padding: 0 60rpx;
		line-height: 100rpx;
	}

	.shop-else-list {
		position: absolute;
		margin-left: 200rpx;
		margin-top: 300rpx;
		background-color: ghostwhite;
		display: flex;
		align-items: center;
		justify-content: center;
	}
</style>
