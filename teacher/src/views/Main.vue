<template>
    <div>
        <el-container style="min-height: 100vh">
            <!-- 侧边栏 -->
            <el-aside style="width: auto;">
                <Aside></Aside>
            </el-aside>
            <el-container>
                <!-- 头部 -->
                <el-header>
                    <Header :user="user"></Header>
                </el-header>
                <!-- tag -->
                <Tag></Tag>
                <!-- 内容（Main） -->
                <el-main>
                    <!-- 路由出口 -->
                    <!-- 路由匹配到的组件将渲染在这里 -->
                    <router-view @refrehUser="getUser"></router-view>
                </el-main>
            </el-container>
        </el-container>

        

    </div>
</template>

<script>
import Aside from '../components/main/Aside.vue'
import Header from '../components/main/Header.vue'
import Tag from '../components/main/Tag.vue'
export default{
    data() {
        return {
            user: {}
        }
    },
    //引入组件
    components: {
        Aside,
        Header,
        Tag,
    },
    created(){
        this.getUser()
    },
    methods: {
        getUser(){
            this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem('user')) : {}
            // console.log(this.user,'user-main')
        }
    },
}

</script>
<style lang="less" scoped>
.el-header {
    padding: 0;  
    margin: 0;
    
}

</style>