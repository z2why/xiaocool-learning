<template>
    <div>
        <el-page-header @back="$router.push('/practice')" content="线下实践管理"></el-page-header>

        <div class="practice" >
            <el-descriptions :title="practiveData.praName">
                <el-descriptions-item label="所属课程名">{{practiveData.couName}}</el-descriptions-item>
                <el-descriptions-item label="开始时间">{{practiveData.praTime}}</el-descriptions-item>
                <el-descriptions-item label="地址">{{practiveData.praSite}}</el-descriptions-item>
                <el-descriptions-item label="任务内容">{{practiveData.praMain}}</el-descriptions-item>
            </el-descriptions>
        </div>

        <div class="top">
            <el-select v-model="audit" clearable  placeholder="请选择">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" ></el-option>
            </el-select>
            <el-button size="mini" style="margin-left: 10px" type="primary" @click="load">搜索</el-button> 
            <!-- 刷新按钮 -->
            <el-button size="mini" style="margin-left: 10px" type="primary" @click="reast"><i class="el-icon-refresh"></i></el-button> 

            <el-table :data="getData" :header-cell-style="{ backgroundColor: 'rgb(244, 244, 245)' }"
                style="margin-bottom: 10px" border stripe>

                <el-table-column prop="stuName" label="学生姓名" width="150" align="center"></el-table-column>
                <el-table-column prop="praDate" label="报名时间" width="150" align="center"></el-table-column>
                <el-table-column prop="audit" key="audit" label="报名审核" width="180" align="center">
                    <template slot-scope="scope" >
                        <template v-if="scope.row.audit === 0">申请报名</template>
                        <template v-else-if="scope.row.audit === 1">报名通过</template>
                        <template v-else-if="scope.row.audit === 2">报名不通过</template>   
                        <template v-else-if="scope.row.audit === 3">取消报名</template>                </template>
                </el-table-column>
                <el-table-column label="操作" prop="audit"  align="center">
                    <template slot-scope="scope" >
                        <template v-if="scope.row.audit != 3 ">
                            <el-button icon="el-icon-check" type="success" 
                                active-color="#13ce66" inactive-color="#ccc" @click="check(scope.row.id)" plain>通过
                            </el-button>
                            <el-button icon="el-icon-close" type="warning" 
                                active-color="#13ce66" inactive-color="#ccc"
                                @click="checkout(scope.row.id)"
                                plain>不通过
                            </el-button>
                        </template>                   
                    </template>
                </el-table-column>

            </el-table>

            <!-- 分页 -->
            <div class="block">
                <el-pagination 
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page.="pageNum" :page-sizes="[8, 10, 12]" 
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper" 
                    :total="total">
                </el-pagination>
            </div>
        </div>
        
       
        
    </div>
    
</template>
<script>
export default{
    data () {
        return {           
            pageNum: 1,
            pageSize: 8,
            total: 0,
            options: [
                {
                    value: 0,
                    label: '申请报名'
                },
                {
                    value: 1,
                    label: '报名通过'
                }, 
                {
                    value: 2,
                    label: '报名不通过'
                },
                {
                    value: 3,
                    label: '取消报名'
                },
            ],
            audit: '',//搜索选择框
            getData: [],
            practiveData: '',//章节id
        }
    },
    created() {
        //
        this.practiveData =  JSON.parse(this.$route.query.practiveData)
        //console.log(this.practiveData)

        //请求分页查询数据
        this.load()
    },
    methods: {
        load() {
            this.request.get("/service-practice/practice/findpracticepage", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    praId: this.practiveData.praId,
                    audit: this.audit,
                }

            }).then(res => {
                this.getData = res.data.records
                this.total = res.data.total
                console.log(res,'res')
            })
        },
        //每页显示多少条数据
        handleSizeChange(pageSize) {
            // console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        //页码
        handleCurrentChange(pageNum) {
            // console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
        //刷新
        reast(){
            this.audit = '',
            this.load()
        },
       
        //审核通过
        check(ids) {
            this.request.post("/service-practice/practice/auditPractice",{
                id:ids,
                audit: 1,
            }).then(res => {
                //console.log(res,'报名')
                if(res.code === '200'){
                    this.$message.success("审核通过成功！")
                    this.load()
                }else{
                    this.$message.error("审核失败！")
                }
            })
        },
        //审核不通过
        checkout(ids) {
            this.request.post("/service-practice/practice/auditPractice",{
                id:ids,
                audit: 2,
            }).then(res => {
                console.log(res,'报名')
                if(res.code === '200'){
                    this.$message.success("审核通过成功！")
                    this.load()
                }else{
                    this.$message.error("审核失败！")
                }
            })
        },
    }
}
</script>
<style lang="less" scoped>
.el-page-header{
    margin-bottom: 15px;
    margin-top: -10px;
}
.practice{
    width: 65%;
    margin: auto;
    margin-bottom: 10px;
    /deep/.el-descriptions{
        
        .el-descriptions__title{
            font-size: 16px;
        }
        .el-descriptions__body .el-descriptions__table {
            font-size: 14px;
        }
    }
    
}


.top{
    border-top: 2px solid rgb(228, 225, 225);
    padding-top: 15px;
    margin-top: 20px;
    width: 65%;
    margin: auto;
    /deep/.el-table {      
        margin-bottom: 20px;
        margin-top: 20px;
           .cell {
               display: -webkit-box;
               -webkit-box-orient: vertical;
               -webkit-line-clamp: 2;
               overflow: hidden;
           }
       }
}



</style>