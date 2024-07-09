<template>
    <div>

        <div class="studentG-top">
            <!-- 跳转到班级管理 -->
            <el-page-header @back="$router.push('/classes')" :content="this.classIN.className"></el-page-header>
            <!-- 搜索与添加区域 -->
            <el-row :gutter="30">
                <el-col :span="5">
                    <el-input placeholder="请输入内容" v-model="condition" clearable @clear="togetStuList">
                        <el-button slot="append" icon="el-icon-search" @click="getStudentList"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" @click="addDialogStudent">添加学生</el-button>
                </el-col>
            </el-row>
        </div>

        <!-- 班级列表区域 -->
        <el-table :header-cell-style="{ backgroundColor: 'rgb(244, 244, 245)' }" :data="studentlist" border stripe>
            <!-- <el-table-column prop="query.class_name" label="学生班级"></el-table-column> -->
            <el-table-column prop="stuId" label="学生id"></el-table-column>
            <el-table-column prop="stuName" label="学生姓名"></el-table-column>
            <el-table-column prop="joinDate" label="加入时间"></el-table-column>
            <el-table-column prop="stuStudyTime" label="学生学习时长"></el-table-column>
            <el-table-column prop="stuScore" label="积分"></el-table-column>
            <el-table-column prop="operation" label="操作">
                <template slot-scope="scope">
                    <!-- 删除按钮 -->
                    <el-button type="danger" icon="el-icon-delete" @click="removeStudentById(scope.row.id)">
                    </el-button>
                    <!-- 学习进度 -->
                    <el-button type="success" @click="study(scope.row.stuName,scope.row.stuId)" >学习进度</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页区域 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="queryInfo.pageNum" :page-sizes="[2, 4, 6]" :page-size="queryInfo.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>

        <!-- 添加班级-学生的对话框 -->
        <el-dialog title="添加学生" :visible.sync="addDialogVisible" width="40%" @close="addDialogClosed">
            <!-- 搜索框 -->
            <el-input placeholder="请输入想要搜索学生的姓名" v-model="DXcondition" clearable @clear="tonullCondition">
                <el-button slot="append" icon="el-icon-search" @click="selectStudentClass"></el-button>
            </el-input>
            <!-- 多选表格 -->
            <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column label="用户Id" width="150">
                    <template slot-scope="scope">{{ scope.row.stuId }}</template>
                </el-table-column>
                <el-table-column prop="stuName" label="学生姓名" width="120">
                </el-table-column>
            </el-table>
            <!-- 分页区域 -->
            <el-pagination @size-change="handleSizeChangeDX" @current-change="handleCurrentChangeDX"
                :current-page="queryfindStudent.pageNum" :page-size="queryfindStudent.pageSize"
                layout="total, prev, pager, next" :total="DXtotal">
            </el-pagination>
            <div style="margin-top: 20px">
                <el-button @click="toggleSelection()">取消选择</el-button>
                <el-button @click="addStudentClass()">确定添加</el-button>
            </div>
        </el-dialog>

        <!-- 学习进度弹窗 -->
        <el-dialog
            :title="stuName"
            :visible.sync="studyDialogVisible"
            width="34%"
            :before-close="handleClose">
          <el-table class="study-table"  :header-cell-style="{ backgroundColor: 'rgb(244, 244, 245)' }" :data="theData" >
            <el-table-column prop="couName" label="课程名称" ></el-table-column>
            <el-table-column prop="progress" label="学习进度" width="220">
              <template slot-scope="scope">
                <el-progress :percentage="scope.row.progress" :color="customColors"></el-progress>
              </template>
            </el-table-column>
          </el-table>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            customColors: [
              { color: '#F44336', percentage: 20 },
              { color: '#FFB74D', percentage: 40 },
              { color: '#8BC34A', percentage: 60 },
              { color: '#2196F3', percentage: 80 },
              { color: '#F8BBD0', percentage: 100 }
            ],
            //获取班级-学生列表的参数对象
            queryInfo: {
                classId: '',
                // 当前的页数
                pageNum: 1,
                // 当当前每页显示多少条数据
                pageSize: 6,
                stuId: '',
                stuName: ''
            },
            condition: '',
            DXcondition: '',
            search: '',
            studentlist: [],
            total: 0,
            DXtotal: 0,
            // 控制添加班级-学生对话框的显示与隐藏
            addDialogVisible: false,
            //学习进度弹框
            studyDialogVisible: false,
            stuName: '',
            studyTableData: [],
            // 添加班级-学生的表单数据
            addForm: {
                stuName: ''
            },
            // 添加表单的验证规则对象
            addFormRules: {
                stuName: [
                    { required: true, message: '请输入学生姓名', trigger: 'blur' }
                ]
            },
            restaurants: [],
            timeout: null,
            classIN: {
                classId: '',
                className: ''
            },
            queryfindStudent: {
                stuName: '',
                classId: '',
                pageSize: 6,
                pageNum: 1
            },
            findStudent: [],
            findStudentOb: [],
            Stuitem: {
                classId: ''
                // stuId: ''
            },
            tableData: [],
            multipleSelection: [],
            allCourseLength: 0,
            allCourse:[],
            progress:'',
            count:'',
            theData:[],
            Data:[]
        }

    },
    created() {
        const classIN = this.$route.query;
        // console.log(classIN);
        if (classIN) {
            // var 参数 = query.参数名;
            this.classIN = classIN
            this.queryInfo.classId = this.classIN.classId
            // console.log(this.queryInfo)
            this.Stuitem.classId = this.classIN.classId
            this.queryfindStudent.classId = this.classIN.classId
        }
        this.getStudentList()
        this.getfindStudent()

    },
    methods: {
        //打开添加学生框
        addDialogStudent(){
            this.addDialogVisible = true
            this.getfindStudent()
        },
        //查询所有可加入班级的学生
        getfindStudent() {
            if (this.DXcondition == '') {
                this.request.get('/service-student/student/page', {
                    params: this.queryfindStudent
                }).then(res => {
                    if (res.code === '200') {
                        this.tableData = res.data.records
                        console.log(this.tableData, "tableData")
                        this.DXtotal = res.data.total
                    } else {
                        this.$message.error("获取可选学生信息失败！")
                    }
                })
            } else if (this.DXcondition != '') {
                console.log(this.DXcondition, "DXcondition")
                this.queryfindStudent.stuName = this.DXcondition
                this.request.get('/service-student/student/page', {
                    params: this.queryfindStudent
                }).then(res => {
                    if (res.code === '200') {
                        this.tableData = res.data.records
                        console.log(this.tableData, "tableData")
                        this.DXtotal = res.data.total
                    } else {
                        this.$message.error("获取可选学生信息失败！")
                    }
                })
            }
        },
        tonullCondition() {
            this.queryfindStudent.stuName = ''
            this.DXcondition = ''
            this.getfindStudent()
        },
        // 添加学生的搜索功能
        selectStudentClass() {
            this.getfindStudent()
        },
        //查询班级学生列表
        getStudentList() {
            this.search = this.condition
            if (this.search === '') {
                this.queryInfo.stuName = ''
                this.queryInfo.stuId = ''
                this.request.get('/service-student/student/findStuClass', {
                    params: this.queryInfo
                }).then(res => {
                    if (res.code === '200') {
                        this.studentlist = res.data.records
                        // console.log(this.studentlist)
                        this.total = res.data.total
                    } else {
                        this.$message.error("获取" + this.classIN.className + "学生列表失败！")
                    }
                })
            } else if (this.search !== '') {
                this.queryInfo.stuId = this.search
                this.queryInfo.pageSize = 6
                this.queryInfo.pageNum = 1
                // console.log(this.queryInfo)
                this.request.get('/service-student/student/findStuClass', {
                    params: this.queryInfo
                }).then(res => {
                    if (res.code === '200') {
                        this.studentlist = res.data.records
                        // console.log("输入的搜索条件是",this.queryInfo.stuId)
                        if (this.studentlist.length === 0) {
                            this.queryInfo.stuId = ''
                            this.queryInfo.stuName = this.search
                            this.request.get('/service-student/student/findStuClass', {
                                params: this.queryInfo
                            }).then(res => {
                                if (res.code === '200') {
                                    this.studentlist = res.data.records
                                    // console.log("输入的搜索条件是",this.queryInfo.stuName)
                                    this.total = res.data.total
                                    if (this.studentlist.length === 0) {
                                        this.$message.info("搜索结果为空！")
                                    }
                                } else {
                                    this.$message.error("获取学生列表失败！")
                                }
                            })
                        } else {
                            this.total = res.data.total
                            if (this.studentlist.length === 0) {
                                this.$message.info("搜索结果为空！")
                            }
                        }
                    } else {
                        this.$message.error("获取学生数据失败！")
                    }
                })
            }
        },
        togetStuList() {
            this.condition = ''
            this.getStudentList()
        },
        // loadAll() {
        //     return this.findStudentOb;
        // },
        // querySearchAsync(queryString, cb) {
        //     var restaurants = this.restaurants;
        //     var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;

        //     clearTimeout(this.timeout);
        //     this.timeout = setTimeout(() => {
        //         cb(results);
        //     }, 3000 * Math.random());
        // },
        // createStateFilter(queryString) {
        //     return (state) => {
        //         return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        //     };
        // },
        // handleSelect(item) {
        //     // console.log(item,"item")
        //     this.Stuitem.stuId = item.id + ''
        // },
        // 监听添加班级-学生对话框的关闭事件
        addDialogClosed() {
            this.$refs.addFormRef.resetFields()
        },
        // 点击按钮，添加新班级-学生
        addStudentClass() {
            console.log(this.multipleSelection, "multipleSelection")
            for (let i = 0; i < this.multipleSelection.length; i++) {
                this.multipleSelection[i].classId = this.Stuitem.classId
            }
            console.log(this.multipleSelection, "multipleSelection")
            let total = this.multipleSelection.map(v => ({ classId: v.classId, stuId: v.stuId }))
            console.log(total)
            this.request.post('/service-student/student/saveBatch', total).then(res => {
                if (res.code === '200') {
                    this.$message.success("添加学生成功！")
                    this.getfindStudent()
                    this.getStudentList()

                }
            })
        },
        // 监听pagesize改变的事件
        handleSizeChange(newSize) {
            // console.log(newSize)
            this.queryInfo.pageSize = newSize
            this.getStudentList()
        },
        // 监听页码值改变的事件
        handleCurrentChange(newPage) {
            // console.log(newPage)
            this.queryInfo.pageNum = newPage
            this.getStudentList()
        },
        // 监听pagesize改变的事件
        handleSizeChangeDX(newSize) {
            // console.log(newSize)
            this.queryfindStudent.pageSize = newSize
            this.getfindStudent()
        },
        // 监听页码值改变的事件
        handleCurrentChangeDX(newPage) {
            // console.log(newPage)
            this.queryfindStudent.pageNum = newPage
            this.getfindStudent()
        },
        // 根据stu_id,class_id删除对应的班级-学生信息
        async removeStudentById(id) {
            // console.log(id)
            const confirmResult = await this.$confirm('此操作将该位学生移出该班级, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).catch(err => err)
            // 如果用户确认删除，则返回值为字符串:confirm
            // 如果用户取消了删除，则返回值为字符串:cancel
            // console.log(confirmResult)
            if (confirmResult === 'cancel') {
                return this.$message.info('已取消删除')
            }
            if (confirmResult === 'confirm') {
                this.request.delete('/service-student/student/leaveclass/' + id).then(res => {
                    // console.log(res,"res")
                    if (res.code === '200') {
                        this.$message.success("删除成功！")
                        this.getStudentList()
                    } else {
                        this.$message.error("删除失败！")
                    }
                })
            }
        },
        toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
            } else {
                this.$refs.multipleTable.clearSelection();
            }
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        //学习进度按钮
        study(stuName,stuId){
          this.studyDialogVisible = true;
          this.stuName = stuName
          //查找
          this.request.get('service-progress/progress/findClassProgress',{
            params:{
              stuId: stuId,
              classId: this.queryInfo.classId,
              pageNum: 1,
              pageSize: 100
            }
          }).then(ress => {
            this.studyTableData=ress.data.records
            this.count=ress.data.total

           for (var i=0;i<this.studyTableData.length;i++){
             this.studyTableData[i].progress =  Math.floor((ress.data.records[i].learnedSectionCount / ress.data.records[i].couChaNum)*100)
           }
              console.log( this.studyTableData,'学习记录')

              //查找改班级的所有课程
              this.request.get('/service-class/class/findClassCourse',{
                params:{
                  classId:  this.queryInfo.classId,
                  pageNum: 1,
                  pageSize: 100
                }
              }).then(res => {
                this.allCourse = res.data.records
                console.log(this.allCourse,"所有课程")
                for(let i=0;i<this.allCourse.length;i++){
                    this.allCourse[i].progress=0
                }
                const arr1 =this.allCourse
                const arr2 =this.studyTableData
                const getAbsentValues = (arr1,arr2) => {
                  let qwq = [];
                  qwq = arr1.filter(el => {
                    return !arr2.find(obj => {
                      return el.couId === obj.couId;
                    });
                  });
                  return qwq;
                };
                this.Data=getAbsentValues(arr1,arr2)
                for(let i=0;i<this.Data.length;i++){


                  this.studyTableData[this.count]=this.Data[i]
                  this.count++
                }
                this.theData=this.studyTableData
                console.log(this.studyTableData)

              })
          })
        },
        handleClose(done) {
          done();
          this.studyTableData = []
          this.allCourseLength = 0
        }
    },
    mounted() {
        this.restaurants = this.loadAll();
    }
}
</script>

<style lang="less" scoped>
.studentG-top {
    margin-top: -10px;

    .el-page-header {
        margin-bottom: 20px;
    }
}

.el-table {
    margin-top: 20px;
}

.el-pagination {
    margin-top: 20px;
}

h3 {
    text-align: center;
}

.study-table{
  margin-top: -20px;
  margin-bottom: 10px;
  width: 100%;
}
</style>