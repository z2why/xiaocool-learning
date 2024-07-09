<template>
    <div class="scroll" :class="{show:isActive}">
        <el-button icon="el-icon-caret-top" id="toTop" @click="toTop(step)"  circle></el-button>
    </div>  
</template>
<script>
export default{
    props: {
        step:{   //控制速度
            type: Number,
            default: 40  
        }
    },
    data () {
        return {
            isActive: false,
        }
    },
    mounted () {
        window.addEventListener('scroll', this.handleScroll)
    },
    methods: {
        handleScroll (e) {
            if (document.documentElement.scrollTop > 50) {
                this.isActive = true
            } else {
                this.isActive = false
            }
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
    },
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
    display: none;  
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
</style>