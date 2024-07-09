<template>
    <div >
        <!-- 返回咨询页面 -->
        <p class="returnConsult" @click="$router.push('/find')">
            <i class="el-icon-back"></i>  返回
        </p>

        <!-- 咨询详情 -->
        <div class="consult">
            <div class="consult-top">
                <!-- 标题 -->
                <h3> {{consultData.infoTitle}}</h3>
                <!-- 作者 -->
                <span >{{consultData.infoAuthor}}</span>
                <!-- 发布日期 -->
                <span style="margin-left: 20px;">{{consultData.infoData}}</span>   
            </div>
            <!-- 咨询内容 -->
            <div  v-html="consultData.infoMain"></div>    
        </div> 

        <!-- 评论 -->
        <div class="comment" >
           <!-- 发布评论 -->
            <div class="deliverConsult">
                <el-form>
                    <el-input  size="medium" v-model="deliverConsult" placeholder="请输入评论"></el-input>
                    <el-button size="medium"  type="primary" @click="add">发布</el-button>
                </el-form>
            </div>
            <p>全部评论</p>
            <el-empty  v-if="commentData.length === 0" description="暂无评论"></el-empty>
            <div class="comment-content" v-else v-for="item in commentData" :key="item.index">
                <div class="commen-content-top">
                    <div class="img">
                        <el-avatar :src="item.avatar" ></el-avatar>
                    </div>
                    <div class="text">
                        <p>{{item.truename}}</p>
                        <p class="time" >{{item.cDate}}</p>
                    </div>
                </div>
                <!-- 评论内容 -->
                <div class="comment-content-bottom">
                    <p>{{item.ccontent}}</p>
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
            </div>         -->
        </div>
       
        <!-- 点赞按钮 -->
        <el-tooltip class="item" effect="light" content="点赞" placement="top">
            <el-button  class="info-like " :class="infoLikeOn" :icon="icon" @click="like" circle></el-button>
        </el-tooltip>
        
    </div>
</template>
<script>
export default{
    data () {
        return {
            icon: 'el-icon-star-off', //点赞图标
            infoLikeOn: '',//点赞图标样式
            likeNum: 0,
            pageNum: 1,//页码
            pageSize: 100,//每页显示多少条数据
            total: 0,
            consultData: {},//咨询数据
            commentData: [],//评论数据
            deliverConsult: '',//发布评论输入框
            user: JSON.parse(localStorage.getItem('user'))
        }
    },
    created () {   
        this.consultData = JSON.parse(this.$route.query.consultData); //获取从咨询管理传过来的     
        this.load() 
        //console.log(this.consultData)
        // console.log(this.infoLike,'this.infoLike')
    },
    methods: {
        //点赞
        like(){
            //点赞
            if(this.icon !== 'el-icon-star-on'){
                this.icon = 'el-icon-star-on'
                this.infoLikeOn = 'info-like-on'
                const info_like = this.consultData.infoLike * 1 + 1
                this.request.post("/service-information/information",{
                    infoId: this.consultData.infoId,
                    infoLike: info_like
                }).then(res => {
                    if(res.code === '200'){
                        this.$message.success("点赞成功！")
                        this.load()
                    }else{
                        this.$message.info("点赞失败！")
                    }                      
                })

            }else{//取消点赞
                this.icon = 'el-icon-star-off'
                this.infoLikeOn = ''
                const info_like = this.consultData.infoLike * 1 - 1
                this.request.post("/service-information/information",{
                    infoId: this.consultData.infoId,
                    infoLike: info_like
                }).then(res => {
                    if(res.code === '200'){
                        this.$message.success("取消点赞！")
                        this.load()
                    }else{
                        this.$message.info("取消点赞失败！")
                    }                      
                })          
            }
        },
        load(){
            console.log(this.consultData.infoId,'this.consultData.infoId')
           this.request.get("/service-comment/comment/commentPage",{
            params:{
                infoId: this.consultData.infoId,
                pageSize: this.pageSize,
                pageNum: this.pageNum,
            }
           }).then(res => {
                console.log(res,'commentData')
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
        //新增评论
        add(){
            this.request.post('/service-comment/comment',{
                infoId: this.consultData.infoId,
                uid: this.user.id,
                role: 0,
                ccontent: this.deliverConsult               
            }).then(res => {
                console.log(res, 'res')
                this.load()
                this.deliverConsult = ''
                this.$message.success("发布成功！！")
            })
        }
    }
}
</script>
<style lang="less" scoped>
//点赞按钮
.info-like{
    position: fixed;
    top: 550px;
    right: 10%;
    width: 50px;
    height: 50px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    font-size: 30px;
 
}
.info-like-on{
    color: #d8ea89;
}
.returnConsult{
    cursor: pointer;
    margin-left: 20px;
}
//咨询详情
.consult{
    width: 60%;
    box-shadow: 0 0 3px #cac6c6;
    margin: auto;
    padding: 20px 20px 20px 20px;
    .consult-top{
        height: 65px;
        border-bottom: 2px solid #f0f0f0;
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
    .deliverConsult{
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
                    color: #E6A23C;
                }
                .time{
                    color:rgb(170, 170, 170);
                    font-size: 14px;
                    
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