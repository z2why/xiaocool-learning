<template>
    
    <el-container>
            <!-- 回到顶部 -->
            <!-- <div class="scroll" :class="{show:isActive}">
                <el-button icon="el-icon-caret-top" id="toTop" @click="toTop(step)"  circle></el-button>
            </div>  -->
        <el-header>
            <el-tabs v-model="activeName"  ref="nav" class="nav">
                <template @click="consultClick">
                    <el-tab-pane  label="咨询" name="first"><Consult/></el-tab-pane>
                </template>
                <el-tab-pane label="问题" name="second"><Problem/></el-tab-pane>
              </el-tabs>
        </el-header>
    </el-container>
    
</template>
<script>
import Consult from '@/components/find/Consult.vue'
import Problem from "@/components/find/Problem.vue"
export default{
    components: {
        Consult,
        Problem,
    },
    props: {
        step:{   //控制速度
            type: Number,
            default: 40  
        }
    },
    data() {
        return {
            
            isActive: false,
            activeName: 'first',
            activeN: '',
        }
    },  
    mounted () {
        //this.activeName = this.$route.query.activeName
        // this.activeName = this.$route.query.activeName
        window.addEventListener('scroll', this.handleScroll, true)//回到顶部
        
    },
    created(){
        // this.activeN = this.$route.query.q
        // console.log(this.activeN)
        // if(this.activeN === 'second'){
        //     this.activeName = 'second'
        // }else if(this.activeN === ''){
        //     this.activeName = 'first'
        // }

    },
    methods: {
        // consultClick(){
        //     this.activeN = '11'
        //     console.log(this.activeN,'activeN')
        // }
        consultClick(){
            console.log("hh")
        },
      
        //回到顶部
        handleScroll (e) {
            const scrollTop = document.querySelector(".nav").scrollTop;
            //console.log(scrollTop,'scrollTop')
            if (document.documentElement.scrollTop > 50) {
                this.isActive = true
            } else {
                this.isActive = false
            }

            // this.$nextTick(()=>{
            //     var top = document.body.scrollTop || document.documentElement.scrollTop || window.pageYOffset
            //     console.log(top,'top')
            // })
            const top = Math.floor(document.body.scrollTop || document.documentElement.scrollTop || window.pageYOffset)
            //console.log(top,'top')

            
        },
        toTop(step){
            //参数step表示时间间隔的幅度大小，以此来控制速度
            //当回到页面顶部的时候需要将定时器清除
            document.documentElement.scrollTop-=step;
            if (document.documentElement.scrollTop>0) {
                var time=setTimeout(()=>this.toTop(step),15);
            }else {
                clearTimeout(time);
            }
        },
    }
}
</script>
<style lang="less" scoped>
.scroll{
    position: fixed;
    right: 50px;
    bottom: 60px;
    width: 45px;
    height: 90px;
    cursor: pointer;
    display: block;  
    .el-button{
        width: 50px;
        height: 50px;
        font-size: 20px;
        color: #409eff;
    }
}
.scroll>div{
    width: 45px;
    height: 45px;
    transform: rotate(90deg);
    line-height: 45px;
    text-align: center;
    font-size: 35px;
    font-family: "黑体";
    background-color: rgba(0,0,0,.2);
    color: #fff;
}
.scroll>div:hover{
    background-color: rgba(0,0,0,.5);
}
.show{
    display: block;
}
.el-container{
    
    width: 70%;
    margin: auto;
    /deep/.el-tabs{
        .el-tabs__header{
            .el-tabs__nav-wrap {
                display: flex;
                justify-content: center;
            }
        }
    }
 
    
}


</style>