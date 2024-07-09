<template>
    <div > 
        <!-- 回到顶部 -->
        <div class="scroll" :class="{show:isActive}">
            <el-button icon="el-icon-caret-top" id="toTop" @click="toTop(step)"  circle></el-button>
        </div>  
        
        <div class="addCourse">
            <!-- 搜索 -->
           <el-input size="mini"  style="width: 200px" suffix-icon="el-icon-search"  placeholder="请输入课程名称" v-model="couName" @clear="load" clearable></el-input>
           <el-input size="mini"  style="width: 200px;margin-left: 10px" suffix-icon="el-icon-search"  placeholder="请输入作者" v-model="tName" @clear="load" clearable></el-input>
           <el-button size="mini" style="margin-left: 10px" type="primary" @click="load">搜索</el-button> 
           <!-- 刷新按钮 -->
           <el-button size="mini" style="margin-left: 10px" type="primary" @click="reast"><i class="el-icon-refresh"></i></el-button> 
           <!-- 新增课程按钮 -->
            <el-button type="primary" @click="addCourse" >新增课程 <i class="el-icon-circle-plus-outline"></i></el-button>
            

        </div>

        <div class="courseDiv">
            <div class="course-show" v-for="item in tableData" :key="item.couId">
                <div class="course-show-top">   
                    <img :src="item.cover" > 
                </div>
                <div class="course-show-content">
                    <h3>{{item.couName}}</h3>
                    <p class="couIntroduction">{{item.couIntroduction}}</p>
                    <p class="course-number">
                        <span>价格：{{item.couPrice}}</span>
                        <span>章节：{{item.couChaNum}}</span>
                        <span>收藏：{{item.couCollNum}}</span>
                    </p>
                </div>
                <div class="course-show-bottom">
                    <el-tooltip class="item" effect="light" content="修改课程" placement="bottom">
                        <el-button type="primary" icon="el-icon-edit" @click="editCourse(item)" circle></el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="light" content="删除课程" placement="bottom">
                        <el-button type="danger" @click="deleteCourse(item)" icon="el-icon-delete" circle></el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="light" content="章节管理" placement="bottom">
                        <el-button type="success" icon="el-icon-c-scale-to-original"  @click="chapterManager(item)"  circle></el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="light" content="创建线下实践" placement="bottom">
                        <el-button type="warning" style="margin-right:10px ;" icon="el-icon-s-opportunity" 
                            @click="addPractice(item.couId,item.teaId,item.teaName)" circle></el-button>             
                    </el-tooltip>
                </div>
            </div>
    
        </div>
      
        <!-- 分页 -->
         <div class="block">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.="pageNum"
              :page-sizes="[6, 9, 12]"
              :page-size="pageSize"
              layout=" total,sizes , prev,  pager, next, jumper"
              :total="total">
            </el-pagination>
        </div>

        <!-- 新增/修改课程弹窗 -->
        <el-dialog
            title="课程"
            :visible.sync="dialogVisibleAdd"
            :before-close="handleClose"
            width="30%" center>
            
            <el-form label-width="90px" ref="courseFrom" :rules="rules" :model="form" >
                <el-form-item  label="课程名称" prop="couName">
                    <el-input style="width:250px"  v-model="form.couName" placeholder="请输入课程名称" clearable></el-input>
                </el-form-item>
                <el-form-item label="课程类型">
                    <el-cascader style="width:250px"  v-model="courseType" :key="modalKey" :options="options" clearable></el-cascader>
                </el-form-item>
                <!-- <el-form-item >
                    <el-input type="hidden" v-model="form.teaId = user.teaId" :disabled="true" placeholder="请输入课程作者" clearable></el-input>
                </el-form-item> -->
                <el-form-item label="课程价格" prop="couPrice">
                    <el-input style="width:250px"  v-model="form.couPrice" placeholder="请输入课程价格" clearable></el-input>
                </el-form-item>
                <el-form-item label="课程介绍" prop="couIntroduction">
                    <el-input  
                    style="width:250px" 
                        autosize
                        type="textarea" 
                        v-model="form.couIntroduction" 
                        placeholder="请输入课程介绍" 
                        clearable>
                    </el-input>
                </el-form-item>
                <el-form-item  prop="cover" label="课程封面" >
                    <el-upload
                        class="avatarUploader"
                        action="http://localhost:9090/service-coustype/file/upload"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess">
                      <img v-if="form.cover" :src="form.cover" class="avatar" >
                      <i v-else class="el-icon-plus avatar-uploader-icon" ></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="resetDialogAdd">重 置</el-button>
                <el-button type="primary" @click="saveConurse">确 定</el-button>
            </span>
        </el-dialog>


        <!-- 新增线下实践弹窗 -->
        <el-dialog
            title="创建线下实践"
            :visible.sync="dialogVisibleAddPractice"
            width="30%" center>
            
            <el-form label-width="90px" ref="practiceFrom" :rules="rulesPractice" :model="forms" >
                <!-- <el-form-item > 
                    <el-input type="hidden" v-model="forms.couId = couId" :disabled="true" ></el-input>
                </el-form-item> 
                <el-form-item  >
                    <el-input type="hidden" v-model="forms.teaId = teaId" :disabled="true" ></el-input>
                </el-form-item> -->
                <el-form-item label="标  题" prop="praName">
                    <el-input  style="width: 250px;" v-model="forms.praName" placeholder="请输入标题" clearable></el-input>
                </el-form-item>
                <el-form-item label="地  址" prop="praSite">
                    <el-input  style="width: 250px;" v-model="forms.praSite" placeholder="请输入地址" clearable></el-input>
                </el-form-item>
                <el-form-item label="任务内容" prop="praMain">
                    <el-input  
                        style="width: 250px;"
                        type="textarea" 
                        v-model="forms.praMain" 
                        placeholder="请输入任务内容" 
                        autosize
                        clearable>
                    </el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleAddPractice = false">取 消</el-button>
                <el-button type="primary" @click="savePractice">确 定</el-button>
            </span>
        </el-dialog>
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
    data() {
        return{
           isActive: false,
           total: 0, //分页总条数
           pageNum: 1,//页码
           pageSize:6,//每页显示多少条数据
           couId: '',//课程idid
           teaId: '',//课程对应的教师id
           couName: '',
           tName: '',
           cover: '',//封面
           tableData: [], //课程列表
           dialogVisibleAdd: false,//新增课程弹窗
           dialogVisibleEdit: false,
           dialogVisibleAddPractice: false,//新增线下实践弹窗
           coursename: '', //点击线下实践按钮获取当前课程名称
           form: {
               couName: '',
               couIntroduction: '',
               couType: '',//课程大类
               couChileType: '',//课程小类
               couPrice: '',
               couCataNum: 0, //章节数
               teaId: 0,
           },  
           forms: {},
           courseType:[],//课程类型
           modalKey:0,
           user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},        
           rules:{//新增弹窗表单验证
               couName: [
                   {required: true, message: '请输入课程名称', trigger: 'blur'},
               ],
               teaId: [
                   {required: true, message: '请输入课程作者', trigger: 'blur'},
               ],
               couPrice: [
                   {required: true, message: '请输入课程价格', trigger: 'blur'},
               ],
               couIntroduction: [
                   {required: true, message: '请输入课程介绍', trigger: 'blur'},
               ],
           },
           rulesPractice: {//新增线下实践表单验证
                praName: [
                    {required: true, message: '请输入线下实践标题', trigger: 'blur'},
                ],
                praSite: [
                    {required: true, message: '请输入线下实践地址', trigger: 'blur'},
                ],
                praMain: [
                    {required: true, message: '请输入线下实践任务内容', trigger: 'blur'},
                ],
                cover: [
                    {required: true, message: '请上传头像', trigger: 'change'},
                ],
           },
           options:[//课程类型下拉框
               {
                   value: '1',
                   label: '康复',
                   children: [
                        {value: '1', label: '老年疾病康复'},
                       {value: '2', label: '儿科疾病康复'},
                       {value: '3', label: '运动创伤康复'},
                       {value: '4', label: '神经系统疾病康复'},
                       {value: '5', label: '其他康复'},
                   ]
               },              
               {
                   value: '2',
                   label: '护理',
                   children: [
                       {value: '1', label: '老年护理'},
                       {value: '2', label: '儿童护理'},
                       {value: '3', label: '内科护理'},
                       {value: '4', label: '外科护理'},
                       {value: '5', label: '其他护理'},
                   ]
               },
               {
                   value: '3',
                   label: '养生',
                   children: [
                       {value: '1', label: '饮食保健'},
                       {value: '2', label: '运动保健'},
                       {value: '3', label: '中医针灸推拿养生'},
                       {value: '3', label: '其他'},
                   ]
               },
               {
                   value: '4',
                   label: '科普',
                   children: [
                       {value: '1', label: '急救'},
                       {value: '2', label: '传染病'},
                       {value: '3', label: '其他'},
                   ]
               },
           ],
        }
    },
    created () {
       this.load()
    },
    mounted () {
        window.addEventListener('scroll', this.handleScroll)//回到顶部
        
    },
    methods: {
       load(){
           this.request.get("/service-class/class/findCourse",{
               params: {
                   couName: this.couName,
                   teaName: this.tName,
                   pageSize: this.pageSize,
                   pageNum: this.pageNum,
                   deleted: 0,
               }
           }).then(res => {
                //console.log(res,res.data.total)
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
       //新增课程按钮
       addCourse(){
           this.dialogVisibleAdd = true;
       },
       //新增课程弹窗确定按钮
       saveConurse(){
           this.$refs['courseFrom'].validate((valid) => {          
               if(valid ){                   
                    if(this.courseType.length !== 0){
                        this.dialogVisibleAdd = false;
                        this.form.teaId = this.user.teaId //教师id
                        this.form.couType = this.courseType[0]
                        this.form.couChileType = this.courseType[1]
                        this.request.post("/service-course/course", this.form).then(res => {
                            //console.log(res,'res')
                            if (res.code === '200') {
                                this.$message.success("新增或修改课程成功！！！")
                                this.load()
                            } else {
                                this.$message.error("新增或修改课程失败！！！")
                            }
                        })
                    } else {
                        this.$message.info("请选择课程类型！！！")
                    }
               }
           })
           
       },
       handleAvatarSuccess(res){
            for (let i=0 ;i< res.data.length ;i++){
                this.form.cover = res.data[i].url
                console.log(this.form.cover,'this.form.cover')
            }
        },
       //新增课程弹窗关闭按钮
       handleClose(done) {
        this.form = {}
        this.courseType=[]
        done();
        },
       //新增课程弹窗重置按钮
       resetDialogAdd(){
            this.form = {},
            this.courseType=[]
       },
       //刷新按钮
       reast(){
           this.couName = '',
           this.load()
        },
       //修改课程按钮
       editCourse(row){    
            if(this.user.truename === row.teaName){
                this.form = row
                this.courseType[0] = row.couType
                this.courseType[1] = row.couChileType
                this.modalKey++;//改变key值，组件重新渲染，实现回填功能
                this.dialogVisibleAdd = true  
            }else{
                this.$message.info("该课程不是您创建的，您没有权限进行修改！")
            }        
       },
       //删除课程按钮
       deleteCourse(row){
            
            if(this.user.truename === row.teaName){
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.request.post("/service-course/course",
                        {
                            couId: row.couId,
                            deleted: 1,
                        }
                    ).then(res => {
                        console.log(res, 'res')
                        if (res.code === '200') {
                            this.$message.success("删除成功")
                            this.load()
                        } else {
                            this.$message.error("删除失败")
                        }
                    }) 
                }).catch(() => {
                    this.$message.info("已取消删除")         
                });    
            }else{
                this.$message.info("该课程不是您创建的，您没有权限进行删除！")
            }  

              
       },
       //章节管理按钮
       chapterManager(row){
           this.$router.push({
               path: '/chapterManager',
               query:{
                //    courseName: courseName,
                //    courseId: courseId,
                    courseData: JSON.stringify(row)
               }
          })
       },
       //新增线下实践按钮
       addPractice(couId,teaId,teaName){     
            if(this.user.truename === teaName){
                this.couId = couId
                this.teaId = teaId
                this.dialogVisibleAddPractice = true       
            }else{
                this.$message.info('该课程不是您创建的，您没有权限创建线下实践！')
            }     
              
       },
       //线下实践弹窗确定按钮
       savePractice(){
           this.$refs['practiceFrom'].validate((valid) => {
               if(valid){
                    this.forms.couId = this.couId //课程id
                    this.forms.teaId = this.teaId //教师id
                    this.request.post("/service-practice/practice",this.forms).then(res => {
                        if(res.code === '200'){
                            this.dialogVisibleAddPractice = false
                            this.$message.success("创建线下实践成功！")
                            this.forms = {}
                        }else{
                            this.$message.success("创建线下实践失败！")
                        }
                    })
                  
               }
           })        
       },
       //回到顶部
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
.addCourse{
    margin-top: -10px;
    margin-bottom: 20px;
}
.courseDiv{
    display: flex;
    flex-wrap: wrap;

    .course-show{
        width: 28%;
        height: 350px;
        box-shadow: 0 0 2px #cac6c6;
        border-radius: 10px;
        margin-right: 40px;
        margin-bottom: 40px;
        .course-show-top{
            width: 100%;
            height: 50%;
            border-radius: 10px;
            
            img{
                width: 100%;
                height: 100%;
                border-radius: 10px 10px 0px 0px;
            }
            
        }
        .course-show-content{
            width: 93%;
            height: 32%;
            margin: 10px;
          
    
            .couIntroduction{
                display: -webkit-box;
                -webkit-box-orient: vertical;   
                -webkit-line-clamp: 2;
                overflow: hidden;
                font-size: 14px;
                color: #464444;
            }
            .course-number{             
                font-size: 14px;
                color: #a5a5a5;
                display: flex;
                justify-content: space-between;
            }
        }
        .course-show-bottom{
            border-top: 1px solid #f0f0f0;
            width: 100%;
            height: 13%;
            display: flex;
            align-items: center;
            justify-content: flex-end;
        }
    }
}

.block{
    margin-top: 20px;
}
/deep/.el-table{
    .cell {
        display: -webkit-box;
        -webkit-box-orient: vertical;   
        -webkit-line-clamp: 2;
    }
}
.avatarUploader{
    border: 1px dashed #d9d9d9;
    width: 100px;
    height: 80px;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
.avatar{
    height: 80px;
    width: 100px;
}
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    text-align: center;
    width: 100px;
    height: 80px;
    line-height: 80px;
  }


</style>