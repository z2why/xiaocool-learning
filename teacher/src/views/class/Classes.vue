<template>
    <div>
        
        <!-- 搜索与添加区域 -->
        <el-row :gutter="30">
            <el-col :span="5">
                <el-input placeholder="请输入内容"
                    v-model="condition" clearable
                    @clear="togetUserList">
                    <el-button slot="append" icon="el-icon-search"
                    @click="getUserList"></el-button>
                </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" @click="addDialogVisible = true">添加班级</el-button>
                </el-col>
        </el-row>
        <!-- 班级列表区域 -->
        <el-table  :header-cell-style="{ backgroundColor: 'rgb(244, 244, 245)' }" :data="classlist" border stripe>
            <el-table-column prop="classId" label="班级id"></el-table-column>
            <el-table-column prop="className" label="班级名称"></el-table-column>
            <el-table-column prop="teaName" label="教师名称"></el-table-column>
            <el-table-column prop="classStudentNum" label="班级人数"></el-table-column>
            <el-table-column prop="operation" label="操作" width="400">
            <template slot-scope="scope">
                <!-- 修改班级信息按钮 -->
                <el-button type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.classId,scope.row.className)"></el-button>
                <!-- 分配学生按钮 -->
                <el-tooltip effect="dark" content="分配学生" placement="top" :enterable="false">
                    <el-button type="success" icon="el-icon-s-custom" @click="studentManage(scope.row.classId,scope.row.className)"></el-button>
                </el-tooltip>
                <!-- 分配课程按钮 -->
                <el-tooltip effect="dark" content="分配课程" placement="top" :enterable="false">
                     <el-button type="warning" icon="el-icon-reading" @click="coursManage(scope.row.classId,scope.row.className)"></el-button>
                </el-tooltip>
                <!-- 删除按钮 -->
                <el-button type="danger" icon="el-icon-delete" @click="removeClassById(scope.row.classId)"></el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页区域 -->
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="queryInfo.pageNum"
            :page-sizes="[10, 12, 14]"
            :page-size="queryInfo.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
        <!-- 添加班级的对话框 -->
        <el-dialog
            title="添加班级"
            :visible.sync="addDialogVisible"
            width="40%" @close="addDialogClosed">
            <!-- 内容主体区域 -->
            <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="80px">
                <el-form-item label="班级名称" prop="className">
                    <el-input v-model="addForm.className"></el-input>
                </el-form-item>
                <el-form-item label="教师名称">
                    <el-input v-model="addForm.teaName" disabled></el-input>
                </el-form-item>
            </el-form>
            <!-- 底部区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addClass">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 修改班级的对话框 -->
        <el-dialog
            title="修改班级"
            :visible.sync="editDialogVisible"
            width="40%" @close="editDialogClosed">
            <!-- 内容主体区域 -->
            <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="80px">
                <el-form-item label="班级名称" prop="className">
                    <el-input v-model="editForm.className"></el-input>
                </el-form-item>
            </el-form>
            <!-- 底部区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editClassInfo">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
export default {
    data() {
        return {
            //获取班级列表的参数对象
            queryInfo: {
                // 当前的页数
                pageNum: 1,
                // 当前每页显示多少条数据
                pageSize: 10,
                teaId: '',
                classId: '',
                className: '',
                deleted: 0
            },
            search:'',
            condition: '',
            user: {},
            classlist: [],
            total: 0,
            // 控制添加班级对话框的显示与隐藏
            addDialogVisible: false,
            // 添加班级的表单数据
            addForm: {
                className: '',
                teaName: '',
                classStudentNum: 0,
                teaId: '',
                deleted: 0
            },
            // 添加表单的验证规则对象
            addFormRules: {
                className: [
                    { required: true, message: '请输入班级名称', trigger: 'blur' }
                ]
            },
            // 修改表单的验证规则对象
            editFormRules: {
                className: [
                    { required: true, message: '请输入班级名称', trigger: 'blur' }
                ]
            },
            // 控制修改班级对话框的显示与隐藏
            editDialogVisible: false,
            // 查询到的班级信息对象
            editForm: {
                classId: '',
                className: '',
                teaId: '',
                classStudentNum: 0,
                deleted: 0
            },
            delete:{
                classId:'',
                deleted:1
            }
        }
    },
    mounted () {
        
    },
    created() {
        this.getUserList()
    },
    methods: {
        togetUserList(){
            this.condition = ''
            this.getUserList()
        },
        //查询班级列表
        getUserList() {
            this.user = JSON.parse(localStorage.getItem("user"));
            // console.log(this.user);
            this.queryInfo.teaId = parseInt(this.user.teaId)
            this.addForm.teaId = this.user.teaId
            this.editForm.teaId = this.user.teaId
            this.addForm.teaName = this.user.truename
            // console.log(this.addForm)
            // console.log("condition"+this.condition)
            this.search = this.condition
            if(this.search === ''){
                this.queryInfo.classId = ''
                this.queryInfo.className = ''
                this.request.get('/service-class/class/page',{
                    params : this.queryInfo
                }).then(res =>{
                    if(res.code === '200'){
                        this.classlist = res.data.records
                        this.total = res.data.total
                    }else{
                        this.$message.error("获取班级列表失败！")
                    }
                })
            }else if(this.search !== ''){
                this.queryInfo.classId = this.search
                this.queryInfo.pageSize = 6
                this.queryInfo.pageNum = 1
                this.request.get('/service-class/class/page',{
                    params : this.queryInfo
                }).then(res =>{
                    if(res.code === '200'){
                        this.classlist = res.data.records
                        // console.log("输入的搜索条件是classId")
                        // console.log(this.classlist)
                        if(this.classlist.length === 0){
                            this.queryInfo.classId = ''
                            this.queryInfo.className = this.search
                            this.request.get('/service-class/class/page',{
                                params : this.queryInfo
                            }).then(res =>{
                                if(res.code === '200'){
                                    // console.log(this.queryInfo,"qu")
                                    this.classlist = res.data.records
                                    // console.log("输入的搜索条件是className")
                                    // console.log(this.classlist)
                                    this.total = res.data.total
                                    if(this.classlist.length === 0){
                                        this.$message.info("搜索结果为空！")
                                    }
                                }else{
                                    this.$message.error("获取班级列表失败！")
                                }
                            })
                        }else{
                            this.total = res.data.total
                            if(this.classlist.length === 0){
                                this.$message.info("搜索结果为空！")
                            }
                        }
                    }else{
                        this.$message.error("获取班级列表失败！")
                    }
                })
            }
        },
        // 监听pagesize改变的事件
        handleSizeChange(newSize) {
            // console.log("pageSize="+newSize)
            this.queryInfo.pageSize = newSize
            this.getUserList()
        },
        // 监听页码值改变的事件
        handleCurrentChange(newPage) {
            // console.log("pageNum="+newPage)
            this.queryInfo.pageNum = newPage
            this.getUserList()
        },
        // 监听添加班级对话框的关闭事件
        addDialogClosed() {
            this.$refs.addFormRef.resetFields()
        },
        // 点击按钮，添加新班级
        addClass() {
            // console.log(this.addForm)
            this.$refs['addFormRef'].validate((valid) => {
                if(valid){
                    this.request.post("/service-class/class",this.addForm).then(res =>{
                        if(res.code === '200'){
                            this.$message.success("新增班级成功！")
                            // 隐藏添加班级的对话框
                            this.addDialogVisible = false
                            // 重新获取班级列表数据
                            this.getUserList()
                        }else{
                            this.$message.error("新增班级失败!")
                        }
                    })
                }
            })
        },
        // 展示编辑班级的对话框
       showEditDialog(classId,className) {
            this.editDialogVisible = true
            this.editForm.className = className
            this.editForm.classId = classId
            // console.log(this.editForm)
            this.editDialogVisible = true
        },
        // 监听修改班级对话框的关闭事件
        editDialogClosed() {
            this.$refs.editFormRef.resetFields()
        },
        // 修改信息并提交
        editClassInfo() {
            this.$refs['editFormRef'].validate((valid) => {
                if(valid){
                    this.request.post("/service-class/class",this.editForm).then(res =>{
                        if(res.code === '200'){
                            this.$message.success("修改班级成功！")
                            // 隐藏修改班级的对话框
                            this.editDialogVisible = false
                            // 重新获取班级列表数据
                            this.getUserList()
                        }else{
                            this.$message.error("修改班级失败!")
                        }
                    })
                }
            })
        },
        // 根据class_id删除对应的班级信息
        async removeClassById(classId) {
            const confirmResult = await this.$confirm('此操作将永久删除该班级, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).catch(err => err)
            // 如果用户确认删除，则返回值为字符串:confirm
            // 如果用户取消了删除，则返回值为字符串:cancel
            if(confirmResult === 'cancel') {
                return this.$message.info('已取消删除')
            }
            if(confirmResult === 'confirm'){
                // console.log("用户点击了确定按钮")
                this.delete.classId = classId
                this.request.post('/service-class/class',this.delete).then(res =>{
                    if(res.code === '200'){
                        this.$message.success('删除成功！')
                        this.getUserList()
                    }else{
                         this.$message.error('删除失败！')
                    }
                })
            }
        },
        studentManage(classId,className) {
            // console.log("向studentG传送数据"+classId,className)
            this.$router.push({path: '/studentG',query:{classId:classId,className:className}})
        },
        coursManage(classId,className) {
            this.$router.push({path: '/coursG',query:{classId:classId,className:className}})
        }
    }

}
</script>

<style lang="less" scoped>
.el-row{
    margin-top: -10px;
}
.el-table{
    margin-top: 20px;
}
.el-pagination{
    margin-top: 20px;
}
</style>