<template>
    <div >
        <!-- 返回问题页面 -->
        <p class="returnProblem" @click="$router.push(
            {
                path: '/find', 
                query: {
                    q: 'second'
                }
            })">
            <i class="el-icon-back"></i>  返回
        </p>

        <!-- 问题详情 -->
        <div class="Problem">
            <div class="Problem-top">
                <!-- 标题 -->
                <h3> {{problemData.content}}</h3>
                <!-- 作者 -->
                <span >{{problemData.truename}}</span>
                <!-- 发布日期 -->
                <span style="margin-left: 20px;">{{problemData.createTime}}</span>   
            </div>
            <div >
                <!-- 问题内容 -->
                <!-- <p>{{problemData.content}}</p> -->
            </div>    
        </div> 

        
        <div class="comment" >
           <!-- 发布评论 -->
            <div class="deliverProblem">
                <el-form>
                    <el-input  size="medium" v-model="deliverProblem" placeholder="请输入问题回复"></el-input>
                    <el-button size="medium"  type="primary" @click="addAnswer">发布</el-button>
                </el-form>
            </div>
            <p>全部回答</p>
            <el-empty  v-if="commentData.length === 0" description="暂无回答"></el-empty>
            <div class="comment-content" v-else v-for="item in commentData" :key="item.index">
                <div class="commen-content-top">
                    <div class="img">
                        <el-avatar :src="item.avatar" ></el-avatar>
                    </div>
                    <div class="text">
                        <p >{{item.truename}} </p>
                        <p class="time" >{{item.createTime}}</p>
                    </div>
                </div>
                <!-- 评论内容 -->
                <div class="comment-content-bottom">
                    <p>{{item.content}}</p>
                </div>
            </div>
            <!-- 分页 -->
            <!-- <div v-if="commentData.length !== 0" class="block">
                <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.="pageNum"
                :page-sizes="[7, 10, 13]"
                :page-size="pageSize"
                layout=" total, sizes, prev, pager, next, jumper"
                :total="total"
                >
                </el-pagination>
            </div>  -->
        </div>
       
        
    </div>
</template>
<script>
export default{
    data () {
        return {
            pageNum: 1,//页码
            pageSize: 100,//每页显示多少条数据
            total: 0,
            problemData: {},//咨询数据
            commentData: [],
            deliverProblem: '',//发布评论输入框
            user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {}
        }
    },
    created(){
        this.problemData = JSON.parse(this.$route.query.problemData); //获取从咨询管理传过来的
        this.load()
    },
    methods: {
        load(){
            this.request.get("service-comment/topicQuestion/tqUserPage",{
                params: {
                    answeredTqId: this.problemData.tqId,
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                }
            }).then(res => {
                console.log(res)
                this.commentData = res.data.records
                this.total = res.data.total
            })
        },
         //每页显示多少条数据
         handleSizeChange(pageSize){
            // console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        //页码
        handleCurrentChange(pageNum){
            // console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
        //回复问题
        addAnswer(){
            this.request.post("/service-comment/topicQuestion",{
                uid: this.user.id,
                truename: this.user.truename,
                content: this.deliverProblem,
                answeredTqId: this.problemData.tqId,
            }).then(res => {
                if(res.code === '200'){
                    this.$message.success("发布成功！")
                    this.load()
                }else{
                    this.$message.error("发布失败！")
                }
            })
        }

    }
}
</script>
<style lang="less" scoped>
.returnProblem{
    cursor: pointer;
    margin-left: 20px;
}
//咨询详情
.Problem{
    width: 60%;
    box-shadow: 0 0 3px #cac6c6;
    margin: auto;
    padding: 20px 20px 20px 20px;
    .Problem-top{
        height: 65px;
       
        h3{
            margin-bottom: 10px;
        }
        span{
            color:rgb(170, 170, 170);
            font-size: 14px;
        }
    }

}

//评论
.comment{
    width: 60%;
    box-shadow: 0 0 3px #cac6c6;
    margin: auto;
    padding: 20px 20px 20px 20px;
    margin-top: 20px;
    //发布评论
    .deliverProblem{
        .el-input{
            width: 88%;
            margin-right:20px ;
            padding: 0;
        }
    }
    .comment-content{
        margin-bottom: 10px;
        border-bottom: 2px solid #f0f0f0;
        .commen-content-top{
            height: 70px;
            display: flex;
            align-items: center;
            .img .el-avatar{
                width: 70px;
                height: 70px;
            }
            .text{
                border: none;
                min-height: 0;
                margin-left: 20px;
                p{
                    color:#E6A23C
                }
                .time{
                    color:rgb(170, 170, 170);
                    font-size: 14px;
                    margin-bottom: 10px;
                }
            }
        }
        .comment-content-bottom{
            margin-top: 10px;
            p{
                margin: 0px 10px 10px 90px;
            }
        }
    }
    
}

</style>