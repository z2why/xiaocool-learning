// }
/**
 * 接口列表文件
 */
export default {

	//获取sessionkey
	// login:{
	// 	url: 'service-user/user/login',
	// 	auth: false,
	// 	method: 'POST'
	// }
	// 首页
	coursenum: {
		url: 'service-course/course/findcou',
		auth: false,
		method: 'GET'
	},
	//  章节目录
	coursechapter: {
		url: 'service-chapter/chapter/allchapter',
		auth: false,
		method: 'GET'
	},
	

	// 用户已观看章节
	learningProgress:{
		url: 'service-progress/progress/learningProgress',
		auth: false,
		method: 'GET'
	},
	
	// 评价
	//课程评价
	pingjia: {
		url: 'service-course/couAppraise/findStuCouApp',
		auth: false,
		method: 'GET'
	},
	//新增课程评价
	addpingjia: {
		url: 'service-course/couAppraise',
		auth: false,
		method: 'POST'
	},
	//根据stu_id查找我的课程
	mycourse: {
		url: 'service-course/course/findStuCourse',
		auth: false,
		method: 'GET'
	},
	//查找课程
	course: {
		url: 'service-course/course/page',
		auth: false,
		method: 'GET'
	},
	//点赞课程
	recommendCourse: {
		url: 'service-course/courseInteraction/recommend',
		auth: false,
		method: 'GET'
	},
	//收藏课程
	collectCourse: {
		url: 'service-course/courseInteraction/collect',
		auth: false,
		method: 'GET'
	},
	//查找我的收藏课程
	myCollect: {
		url: 'service-course/course/findCoursePage',
		auth: false,
		method: 'GET'
	},
	coursepage: {
		url: 'service-course/course/findCoursePage',
		auth: false,
		method: 'GET'
	},
	//查找该课程的所有线下实践
	//线下实践
	practive: {
		url: 'service-practice/practice/findpractice',
		auth: false,
		method: 'GET'
	},
	//查找学生报名线下实践
	findStuPractive: {
		url: 'service-practice/practice/findpracticepage',
		auth: false,
		method: 'GET'
	},
	//报名线下实践
	addPractive: {
		url: 'service-practice/practice/addPractice',
		auth: false,
		method: 'GET'
	},
	//获取sessionkey
	login: {
		url: 'service-user/user/login',
		auth: false,
		method: 'POST'
	},


	coursenum: {
		url: 'service-course/course/findcou',
		auth: false,
		method: 'GET'
	},

	//获取所有某课程章节
	coursechapter: {
		url: 'service-chapter/chapter/allchapter',
		auth: false,
		method: 'GET'
	},

	//获取某个学生信息
	findstudent: {
		url: 'service-student/student/findStudent',
		auth: false,
		method: 'GET'
	},
	
	//	修改学生信息
	savestudent: {
		url: 'service-student/student',
		auth: false,
		method: 'POST'
	},
	//上传
	upload: {
		url: 'service-coustype/file/upload',
		auth: false,
		method: 'POST'
	},

	//修改用户信息
	reviseUserInfo: {
		url: 'service-user/user',
		auth: false,
		method: 'POST'
	},



	//签到
	signIn: {
		url: 'service-progress/progress/signIn',
		auth: false,
		method: 'POST'
	},


	//签到情况	
	signInList: {
		url: 'service-progress/progress/signInList',
		auth: false,
		method: 'GET'
	},

	// 购物车里面的课程
	shopping: {
		url: 'service-order/stuCouCar/findCouCar',
		auth: false,
		method: 'GET'
	},
	//加入购物车
	addShopping: {
		url: 'service-order/stuCouCar',
		auth: false,
		method: 'POST'
	},
	// 发现模块
	information: {
		url: 'service-information/information/page',
		auth: false,
		method: 'GET'
	},
	informationPage: {
		url: 'service-information/information/infoUserPage',
		auth: false,
		method: 'GET'
	},
	addinfoLike: {
		url: 'service-information/information',
		auth: false,
		method: 'POST'
	},
	infoComment: {
		url: 'service-comment/comment/commentPage',
		auth: false,
		method: 'Get',
	},
	addComment: {
		url: 'service-comment/comment',
		auth: false,
		method: 'POST'
	},
	deleteCom: {
		url: 'service-comment/comment/delete',
		auth: false,
		method: 'GET'
	},
	questionInfo: {
		url: 'service-comment/topicQuestion/tqUserPage',
		auth: false,
		method: 'GET'
	},
	addAnswer: {
		url: 'service-comment/topicQuestion',
		auth: false,
		method: 'POST'
	},
	deleteAnswer: {
		url: 'service-comment/topicQuestion/delete',
		auth: false,
		method: 'GET'
	},
	addQuestion: {
		url: 'service-comment/topicQuestion',
		auth: false,
		method: 'POST'
	},
	addCaina: {
		url: 'service-comment/topicQuestion',
		auth: false,
		method: 'POST'
	},
	// 全部订单
	dingdan: {
		url: 'service-order/order/findorderdetail',
		auth: false,
		method: 'Get',
	},
	// 购买课程
	buyCourse: {
		url: 'service-order/order/buy',
		auth: false,
		method: 'POST',
	},
	// 消息
	couEvaluate: {
		url: 'service-course/couAppraise/findStuCouApp',
		auth: false,
		method: 'GET'
	},
	selectCouName: {
		url: 'service-course/course/findStuCourse',
		auth: false,
		method: 'GET'
	},
	// 退货
	returnGoods: {
		url: 'service-order/order',
		auth: false,
		method: 'POST'
	},
	// 删除购物车的课程
	deleteCart: {
		url: 'service-order/stuCouCar/del/batch',
		auth: false,
		method: 'POST'
	},



//获取所有章节评论
	findChaApp: {
		url: 'service-chapter/couChaAppraise/findStuCouChaApp',
		auth: false,
		method: 'GET'
	},
	//删除课程评价
	 deleteCouApp:{
		 url: 'service-course/couAppraise/deleteCouApp',
		 auth: false,
		 method: 'GET'
	 },
	 //删除章节评价
	 deleteChaApp:{
	 		 url: 'service-chapter/couChaAppraise/deleteChaApp',
	 		 auth: false,
	 		 method: 'GET'
	 },
	 
	
	//保存章节评价
	saveChaApp:{
		url: 'service-chapter/couChaAppraise',
		auth: false,
		method: 'POST'
	},
 
 
 //获取所有的课程观看信息
 findCouProgress: {
 	url: 'service-progress/progress/findCouProgress',
 	auth: false,
 	method: 'GET'
 },
 
 //观看视频
	watch: {
		url: 'service-progress/progress/watch',
		auth: false,
		method: 'POST'
	},
	
	
    couData: {
		url: 'service-progress/progress/CouData',
		auth: false,
		method: 'GET'
	},
	
	
	findProgress: {
		url: 'service-progress/progress/findProgress',
		auth: false,
		method: 'GET'
	},
	
	
	

};
