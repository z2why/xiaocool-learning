<template>
    <div class="problem-container">

            <!-- 咨询列表 -->
            <div class="problem-box" @click="read(item)" v-for="item in problemData" :key="item.index">
                <!-- 咨询内容 -->
                <div class="problem-box-left">
                    <p class="title">{{ item.content }}</p>
                    <!-- <p class="content">{{ item.content }}</p> -->
                    <p class="author">
                        <span style="margin-right: 20px;">{{ item.truename }}</span>
                        <span>{{ item.createTime }}</span>
                    </p>
                </div>
            </div>
        
             <!-- 分页 -->
            <!-- <div class="block">
                <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.="pageNum"
                :page-sizes="[7, 10, 13]"
                :page-size="pageSize"
                layout=" total, sizes,   prev,  pager, next, jumper"
                :total="total">
                </el-pagination>
            </div> -->
    </div>
</template>
<script>
export default{
    data() {
        return {
            pageNum: 1,//页码
            pageSize: 100,//每页显示多少条数据
            total: 0,//总条数
            problemData:[]
        }
    },
    mounted () {
       
    },
    created(){
        this.load()
       
    },
    methods: {
        load(){
            this.request.get("/service-comment/topicQuestion/page",{
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    isQuestion: 1,
                }
            }).then(res => {
                //console.log(res)
                this.problemData = res.data.records
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
        //跳转到问题详情页面
        read(item){
            this.$router.push({
                path: '/problemDetial',
                query: {
                        problemData: JSON.stringify(item)
                }
            })
        },
    }
}
</script>
<style lang="less" scoped>
.problem-container{
    width: 90%;
    margin: 10px auto;
    border-radius: 10px;
    box-shadow: 0 0 3px #cac6c6;  
    padding: 5px 20px 20px 20px;  
    .block{
        margin-top: 10px;
    }
    .problem-box {
        display: flex;
        border-bottom: 2px solid #f0f0f0;
        margin-top: 10px;
        .problem-box-left {
            margin-top: -10px;
            width: 100%;
            .title {
                font-size: 25px;
                
            }   
            .content {
                display: -webkit-box;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 2;
                overflow: hidden;
                color: rgb(82, 80, 80);
            }   
           .author {
                color: rgb(170, 170, 170);
                font-size: 15px;
                margin: 0;
                margin-bottom: 10px;
            }
        }
    }
}
</style>