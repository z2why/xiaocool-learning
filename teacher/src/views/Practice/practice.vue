<template>
    <div>
        <div class="practice-top">
            <!-- 搜索 -->
            <el-input style="width: 200px" suffix-icon="el-icon-search"  placeholder="请输入课程标题" v-model="practiiceTitle" @clear="load" clearable></el-input>
            <el-button style="margin-left: 10px" type="primary" @click="load">搜索</el-button> 
             <!-- 刷新按钮 -->
           <el-button size="mini" style="margin-left: 10px" type="primary" @click="reast"><i class="el-icon-refresh"></i></el-button> 
        </div>
        <!-- 线下实践表 -->
        <el-table  
            :header-cell-style="{ backgroundColor: 'rgb(244, 244, 245)' }" 
            :data="tableData" 
            border stripe>
            
            <el-table-column prop="praName" label="实践标题" width="150"></el-table-column>
            <el-table-column prop="couName" label="所属课程" width="150"></el-table-column>
            <el-table-column prop="praMain" label="任务内容" width="180"></el-table-column>
            <el-table-column prop="praTime" label="开始时间" width="150"> </el-table-column>
            <el-table-column prop="praSite" label="地址" width="230"> </el-table-column>
            <el-table-column prop="" label="操作">
                <template slot-scope="scope">
                    <!-- 修改 线下实践按钮 -->
                    <el-button type="primary" @click="editPractice(scope.row)"  icon="el-icon-edit" ></el-button>
                    <!--  删除 线下实践按钮-->

                    <el-button type="danger" @click="deletePractice(scope.row.praId)" icon="el-icon-delete" ></el-button>
                 
                    <!-- 查看报名学生信息 -->
                    <el-button style="margin-left: 10px" type="success" @click="student(scope.row)"  >
                        <i class="el-icon-user"></i>                      
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <div class="block">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.="pageNum"
              :page-sizes="[8, 10, 12]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
        </div>

        <!-- 修改线下实践弹窗 -->
        <el-dialog
            title="修改线下实践"
            :visible.sync="dialogVisibleEditPractice"
            width="30%" center>
            <el-form label-width="90px" ref="practiceFrom" :rules="rulesPractice" :model="form">
                <el-form-item label="标题" prop="praName">
                    <el-input style="width: 250px;" v-model="form.praName" placeholder="请输入标题" clearable></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="praSite">
                    <el-input style="width: 250px;" v-model="form.praSite" placeholder="请输入地址" clearable></el-input>
                </el-form-item>
                <el-form-item label="任务内容" prop="praMain">
                    <el-input  
                        style="width: 250px;"
                        type="textarea" 
                        v-model="form.praMain" 
                        placeholder="请输入任务内容" 
                        autosize
                        clearable>
                    </el-input>
                </el-form-item>
            </el-form>
            
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleEditPractice = false">取 消</el-button>
                <el-button type="primary" @click="savePractice">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>
<script>
export default{
    data () {
        return {
            total: 0, //分页总条数
            pageNum: 1,//页码
            pageSize: 8,//每页显示多少条数据
            practiiceTitle: '',//实践标题=>搜索框
            tableData: [],
            dialogVisibleEditPractice: false,//修改线下实践弹窗
            user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {},
            rulesPractice: {//修改线下实践表单验证
                praName: [
                    {required: true, message: '请输入线下实践标题', trigger: 'blur'},
                ],
                praSite: [
                    {required: true, message: '请输入线下实践地址', trigger: 'blur'},
                ],
                praMain: [
                    {required: true, message: '请输入线下实践任务内容', trigger: 'blur'},
                ]
            },
            form: {},
        }
    },
    created(){
        //console.log(this.user,'user')
        this.load()
    },
    methods: {
        load(){
            this.request.get("/service-practice/practice/findpractice",{
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    teaId: this.user.teaId,
                    praName: this.practiiceTitle,
                    deleted: 0,
                }
            }).then(res => {
                console.log(res,'res')
                this.tableData = res.data.records
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
        //刷新
        reast(){
            this.practiiceTitle = '',
            this.load()
        },
        //修改线下实践按钮
        editPractice(row){
            this.dialogVisibleEditPractice = true
            this.form = row
        },
        //修改线下实践弹窗确定按钮
        savePractice(){
            this.$refs['practiceFrom'].validate((valid) => {
                if(valid){
                    this.request.post("/service-practice/practice",this.form).then(res => {
                        if(res.code === '200'){
                            this.dialogVisibleEditPractice = false
                            this.$message.success("修改线下实践成功！")
                            this.load()
                        }else{
                            this.$message.success("修改线下实践失败！")
                        }
                    })
                }
            })      
        },
        //删除线下实践
        deletePractice(praId){
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.post("/service-practice/practice",
                    {
                        praId: praId,
                        deleted: 1,
                    }
                ).then(res => {
                    if (res.code === '200') {
                        this.$message.success("删除线下实践成功！")
                        this.load()
                    } else {
                        this.$message.success("删除线下实践失败！")
                    }
                })
            }).catch(() => {
                this.$message.info('已取消删除')
            });
           
        },
        //跳转到学生报名信息页面
        student(row){
            //console.log(praId)
            this.$router.push({
                path: '/practiceStudent',
                query: {
                    practiveData: JSON.stringify(row)
                }
            })
        }
    }
}
</script>
<style lang="less" scoped>
.practice-top{
    margin-bottom: 20px;
    margin-top: -10px;
}
/deep/.el-table{
    .cell {
        display: -webkit-box;
        -webkit-box-orient: vertical;   
        -webkit-line-clamp: 2;
        overflow: hidden;
    }
}
.block{
    margin-top: 20px;
}
.el-form{
    
        .el-form-item__label{
            text-align: none;
        
    }
  
}
</style>