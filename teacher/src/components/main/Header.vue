<template>
    <div class="header-container">
        <div class="left-content">
            <!-- <el-button @click="handleMenu"  icon="el-icon-menu" size="mini"></el-button> -->
            <i @click="handleMenu" :class="this.$store.state.tab.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"  ></i>
            <!-- 面包屑 -->
            <el-breadcrumb style="margin-left: 10px;" separator="/">
                <el-breadcrumb-item v-for="item in tags" :key="item.path" :to="{ path: item.path }">{{ item.label }}</el-breadcrumb-item>
            </el-breadcrumb>

        </div>
        <div class="right-content">
            <div class="full" @click="handleFullScreen">
                <template v-if="fullscreen">
                    <el-tooltip class="item" effect="light" content="退出全屏" placement="bottom">
                        <!-- <i class="el-icon-copy-document"  ></i> -->
                        <img src="@/assets/leavescreen.png"/>
                    </el-tooltip>
                </template>
                <template v-else>
                    <el-tooltip class="item" effect="light" content="全屏显示" placement="bottom">
                        <!-- <i class="el-icon-full-screen"  ></i> -->
                        <img src="@/assets/fullscreen.png"/>
                    </el-tooltip>
                </template>
            </div>
            <el-dropdown size="medium"  @command="handleClick">
                <div>
                    <el-avatar v-if="user.avatar" class="userImg" :src="user.avatar"></el-avatar>
                    <el-avatar v-else icon="el-icon-user-solid"></el-avatar>
                    <span class="el-dropdown-link">
                        {{user.truename}}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                </div>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="person">个人中心</el-dropdown-item>
                  <el-dropdown-item command="loginout">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>
<script>
import { mapState } from 'vuex'
import Cookie from 'js-cookie'
export default {
    name: 'Header',
    props:{
        user: Object //父组件传过来的值
    },
    data () {
        return {
            fullscreen: false  // 是否全屏
        }
    },
    mounted () {
        console.log(this.user,'user');
    },
    computed: {
        ...mapState({
            tags: state => state.tab.tabsList //返回一个对象
        })
    },
    methods: {
        //点击触发菜单收缩或展开
       handleMenu() {
        this.$store.commit('collapseMenu');
       },
       //退出登录
       handleClick(command){
        if(command === 'loginout'){
            localStorage.removeItem("user")
            sessionStorage.removeItem("menu")
            // Cookie.remove('token')
            this.$router.push('/login')
        }
        if(command === 'person'){
            this.$router.push('/person')
        }
       },
        // 全屏事件
        handleFullScreen(){
            let element = document.documentElement;
            // 判断是否已经是全屏
            // 如果是全屏，退出
            if (this.fullscreen) {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
                console.log('已还原！');
            } else {    // 否则，进入全屏
                if (element.requestFullscreen) {
                    element.requestFullscreen();
                } else if (element.webkitRequestFullScreen) {
                    element.webkitRequestFullScreen();
                } else if (element.mozRequestFullScreen) {
                    element.mozRequestFullScreen();
                } else if (element.msRequestFullscreen) {
                    // IE11
                    element.msRequestFullscreen();
                }
                console.log('已全屏！');
            }
            // 改变当前全屏状态
            this.fullscreen = !this.fullscreen;
        }
    },
}

</script>
<style lang="less" scoped>
    *{
       user-select: none;
    }
    .header-container{
        padding: 0 20px;
        height: 60px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: 2px solid #cdcbcb;
       
        //面包屑
        .text{
            color: rgb(2, 2, 2);
            font-size: 16px;
            margin-left: 10px;
            line-height: 60px;
        }
        .right-content{
            display: flex;
            .full{
                font-size: 30px;
                margin-right: 20px;
                position: relative;
                top: 15px;
                color: #949393;
                width: 40px;
                height: 40px;
                img{
                    width: 25px;
                    height: 25px;
                }
            }
            .el-dropdown{
                display: flex;
                .userImg{
                    position: relative;
                    top: 13px;
                    right: 10px;
                }
    
            }
            .el-dropdown-link{
                color: black;
                line-height: 60px;
                font-size: 16px;
            }
           
            .el-dropdown-menu{
                font-size: 16px;
            }
        }
       .left-content{
            display: flex;
            align-items: center;
       }
    }
</style>