<template>
    <div style="margin-bottom: 10px;">
        <div class="addConsult">
            <!-- 搜索 -->
            <el-input size="mini" style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入作者" v-model="consultAuthor" @clear="load" clearable></el-input>
            <el-input size="mini" style="width: 200px; margin-left: 10px" suffix-icon="el-icon-search" placeholder="请输入标题" v-model="consultTitle"  @clear="load" clearable></el-input>
            <el-button size="mini" style="margin-left: 10px" type="primary" @click="load">搜索</el-button>
             <!-- 刷新按钮 -->
           <el-button size="mini" style="margin-left: 10px" type="primary" @click="reast"><i class="el-icon-refresh"></i></el-button> 
            <!-- 新增课程按钮 -->
            <el-button type="primary" @click="addConsult">发布 <i class="el-icon-circle-plus-outline"></i></el-button>
        </div>

        <div class="consult">
            <!-- 咨询列表 -->
            <div class="consult-box" v-for="item in consultData" :key="item.infoId">
                <!-- 咨询内容 -->
                <div class="consult-box-left">
                    <p class="title">{{ item.infoTitle }}</p>
                    <div class="consult-content"   v-html="item.infoMain">
                        <!-- {{ item.infoMain }} -->
                    </div>
                    <p class="author"  >
                        <span>{{ item.infoAuthor }}</span>
                        <span>点赞数： {{ item.infoLike}}</span>
                        <span>{{ item.infoData }}</span>
                    </p>
                </div>
                <!-- 按钮 -->
                <div class="consult-box-right">
                    <el-button type="success" icon="el-icon-view" @click="$router.push(
                        {
                            path: '/consultDetial',
                            query: {
                                consultData: JSON.stringify(item)
                            }
                        }
                        )" circle></el-button>
                    <el-button type="primary" icon="el-icon-edit" @click="editConsult(item)" circle></el-button>                    
                    <el-button size="mini"  type="danger" icon="el-icon-delete" @click="deleteCourse(item)" circle></el-button>
                                                     
                </div>
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

        <!-- 发布咨询弹窗 -->
        <el-dialog title="咨询" :visible.sync="dialogVisibleAdd" width="70%" center>
            <el-form ref="consultFrom" :rules="rules" :inline="true" :model="form">
                <el-form-item label="标题" prop="infoTitle">
                    <el-input v-model="form.infoTitle" placeholder="请输入标题"></el-input>
                </el-form-item>
                <el-form-item >
                    <el-input type="hidden" v-model="form.infoAuthor = user.truename " ></el-input>
                </el-form-item>
                <Editor v-model="form.infoMain" :isClear="isClear" @change="change"></Editor>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cencal">取 消</el-button>
                <el-button type="primary" @click="saveConsult">发 布</el-button>
            </span>
        </el-dialog>
    </div>

</template>
<script>
import Editor from '@/components/Editor.vue'
export default {
    components: {
        Editor,
     
    },
    data() {
        return {
            pageNum: 1,//页码
            pageSize: 100,//每页显示多少条数据
            total: 0,//总条数
            consultTitle: '',//搜索框-标题
            consultAuthor: '',//搜索框-作者
            consultData: [],
            dialogVisibleAdd: false,
            form: {},
            isClear: false,
            user: JSON.parse(localStorage.getItem('user')),
            // detail:"",//输入咨询内容
            rules: {
                infoTitle: [
                    { required: true, message: '请输入标题', trigger: 'blur' },
                ],
            }
        }
    },
   
    mounted() {
        
    },
    created () {
        this.load()
       
    },
    methods: {        
        load(){
            this.request.get("/service-information/information/page",{
               params: {
                   infoTitle: this.consultTitle,
                   infoAuthor: this.consultAuthor,
                   pageSize: this.pageSize,
                   pageNum: this.pageNum,
               }
           }).then(res => {
                console.log(res)
                this.consultData = res.data.records
                this.total = res.data.total
                this.form = {} //清空新增咨询弹窗
                //console.log(res.data.records[0].infoMain,res.data.total)
               //this.tableData = res.data
               
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
        //刷新按钮
        reast(){
            this.consultAuthor = ''
            this.consultTitle = ''
            this.load()
        },
        //发布咨询
        addConsult() {
            this.dialogVisibleAdd = true;
        },
        //新增咨询弹窗确定按钮
        saveConsult() {
            this.$refs['consultFrom'].validate((valid) => {
                //this.form.infoAuthor = this.user.truename
                this.form.uid = this.user.id
                console.log(this.form,'this.form')
                if (valid && this.form.infoMain !== '') {
                    this.dialogVisibleAdd = false;
                    this.request.post("/service-information/information",this.form).then(res => {
                        //console.log(res,'zx-res')
                        if(res.code === '200'){
                            this.$message.success("发布成功！")
                            this.load()
                        }else{
                            this.$message.error("发布失败！")
                        }                      
                    })
                } else {
                    this.$message.info("正文不能为空")
                }
            })
        },
         //新增咨询弹窗取消按钮
        cencal(){
            this.dialogVisibleAdd = false;
            this.form = {}
        },
        change(val) {
            //   console.log(val,'val')
        },
        //跳转到咨询详情页面
        read(item){
            console.log(ite,'item')
            this.$router.push({
                path: '/consultDetial',
                query: {
                        consultData: JSON.stringify(item)
                    }
            })
        },
        //修改咨询
        editConsult(item){     
            if(this.user.truename == item.infoAuthor){
                this.dialogVisibleAdd = true
                this.form = item
            }else{
                this.$message.info("这条咨询不是您创建的，您没有权限修改！")
            }         
        },
        //删除咨询
        deleteCourse(item){
            if (this.user.truename == item.infoAuthor) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.request.delete("/service-information/information/"+item.infoId).then(res => {
                    //console.log(res,'delete-res')
                    if(res.code === '200'){
                        this.$message.success("删除成功！")
                        this.load()
                    }else{
                        this.$message.error("删除失败！")
                    }
                })
                }).catch(() => {
                    this.$message.info("已取消删除")     
                });
             
                
            }else{
                this.$message.info("这条咨询不是您创建的，您没有权限删除！")
            }      
        }


    },
    
}
</script>
<style lang="less" scoped>
.addConsult{
    width: 95%;
    margin: auto;
    margin-bottom: 20px;
}
//分页
.block{
    margin-top: 10px;
    margin-left: 40px;
}
.consult {
    width: 90%;
    border-radius: 10px;
    box-shadow: 0 0 3px #cac6c6;
    margin: auto;
    padding: 5px 20px 20px 20px;
    position: relative;

    .addbtn {
        position: absolute;
        left: -70px;
        top: 0px;
        width: 50px;
        height: 50px;
        box-shadow: 0 4px 10px 0 rgba(162, 169, 210, 0.1);

    }

    .consult-box {
        display: flex;
        border-bottom: 2px solid #f0f0f0;
        margin-top: 10px;
        .consult-box-left {
            width: 90%;
            border-right: 2px solid #f0f0f0;
            padding-right: 20px;
            margin-bottom: 10px;
            margin-top: 10px;
            .consult-content{
                
                display: -webkit-box;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 2;
                overflow: hidden;
                color: rgb(82, 80, 80);
                margin-bottom: 10px;
            }

            .title {
                font-size: 25px;
                margin: 0;
            }

            .content {
                display: -webkit-box;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 2;
                overflow: hidden;
                color: rgb(82, 80, 80);
            }

            .author {
                display: flex;
                justify-content: space-between;
                color: rgb(170, 170, 170);
                font-size: 15px;
                margin: 0;
               
            }
            .authors{
                position: relative;
                top:25px;
            }
        }

        .consult-box-right {
            width: 7%;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            .el-button {
                width: 30px;
                height: 30px;
                margin: 3px;
            }

        }

    }
}
</style>