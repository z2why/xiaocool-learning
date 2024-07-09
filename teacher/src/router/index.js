import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)


//1.创建路由组件
//2.将路由与组件进行映射
//3.创建router实例

const routes = [
    //主路由
    {
        path: '/',
        component: () => import('../views/Main.vue'),
        redirect: '/classes', //重定向
        //子路由（嵌套路由）
        children: [
            { path: 'classes', name: 'classes', component: () => import('../views/class/Classes.vue') }, //班级管理
            { path: 'coursG', name: 'coursG', component: () => import('../views/class/coursG.vue') },  //班级分配课程 
            { path: 'studentG', name: 'studentG', component: () => import('../views/class/studentG.vue')}, //班级分配学生
            { path: 'course', name: 'course', component: () => import('../views/Course/Course.vue')}, //课程管理             
            { path: 'chapterManager', name: 'chapterManager', component: () => import('../views/Course/ChapterManager.vue')},  //章节管理
            { path: 'practice', name: 'practice', component: () => import('../views/Practice/practice.vue')}, //线下实践管理
            { path: 'practiceStudent', name: 'practiceStudent', component: () => import('../views/Practice/practiceStudent.vue')}, //线下实践学生报名信息
            { path: 'find', name: 'find', component: () => import('../views/Find/Find.vue')}, //发现 
            { path: 'person', name: 'person', component: () => import('../views/Person.vue')}, //个人信息
            { path: 'order', name: 'order', component: () => import('../views/order/order.vue')}, //订单管理
        ]
    },
    //登录
    { 
        path:'/login', 
        name: 'login',
        component: () => import('../views/Login.vue')
    },
    //咨询详情页面
    {
        path: '/consultDetial',
        name: 'consultDetial',
        component: () => import('../views/Find/ConsultDetial.vue')
    },
    //问题详情页面
    {
        path: '/problemDetial',
        name: 'problemDetial',
        component: () => import('../views/Find/ProblemDetial.vue')
    },
    
    {
        path: '/ex',
        name: 'ex',
        component: () => import('../views/ex.vue')
    },
]

const router = new VueRouter({
    routes
})

export default router



