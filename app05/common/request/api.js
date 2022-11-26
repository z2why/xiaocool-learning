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
	// 目录
	coursechapter: {
		url: 'service-chapter/chapter/allchapter',
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
 course:{
  url: 'service-course/course/page',
  auth: false,
  method: 'GET'
 },
 //点赞课程
 recommendCourse:{
  url: 'service-course/courseInteraction/recommend',
  auth: false,
  method: 'GET'
 },
 //收藏课程
 collectCourse:{
  url: 'service-course/courseInteraction/collect',
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
		url: 'http://172.20.195.146:9090/service-student/student/findStudent',
		auth: false,
		method: 'GET'
	},

	//上传
	upload: {
		url: 'http://172.20.195.146:9090/service-coustype/file/upload',
		auth: false,
		method: 'POST'
	},

	//修改用户信息
	reviseUserInfo: {
		url: 'http://172.20.195.146:9090/service-user/user',
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
	// 收藏
	shoucang:{
		url:'service-course/course/findStuCourse',
		auth: false,
		method: 'Get',
	},
	// 全部订单
	dingdan:{
		url:'service-order/order/findorderdetail',
		auth: false,
		method: 'Get',
	},
	// 购买课程
	tijiao:{
		url:'service-order/order/buy',
		auth: false,
		method: 'POST',
	},
	// 消息
	couEvaluate:{
	  url:'service-course/couAppraise/findStuCouApp',
	  auth: false,
	  method:'GET'
	 },
	 selectCouName:{
	  url:'service-course/course/findStuCourse',
	  auth: false,
	  method:'GET'
	 }



};
