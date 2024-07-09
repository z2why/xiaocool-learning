<template>
    <div>
        <!-- 搜索与添加区域 -->
        <div class="coursG-top">
            <!-- 跳转到班级管理 -->
            <el-page-header @back="$router.push('/classes')" :content="this.classIN.className"></el-page-header>
            <!-- 搜索与添加区域 -->
            <el-row :gutter="30">
                <el-col :span="5">
                    <el-input placeholder="请输入内容" v-model="condition" clearable @clear="togetCouList">
                        <el-button slot="append" icon="el-icon-search" @click="getCoursList"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" @click="addDialogVisible = true">添加课程</el-button>
                </el-col>
            </el-row>
        </div>

        <!-- 班级列表区域 -->
        <el-table :header-cell-style="{ backgroundColor: 'rgb(244, 244, 245)' }" :data="courslist" border stripe>
            <el-table-column prop="couName" label="课程名称"></el-table-column>
            <el-table-column prop="couType" label="课程类型">
              <template scope="scope">
                <p v-if="scope.row.couType==='1'">康复</p>
                <p v-if="scope.row.couType==='2'">护理</p>
                <p v-if="scope.row.couType==='3'">养生</p>
                <p v-if="scope.row.couType==='4'">科普</p>
              </template>
            </el-table-column>
            <el-table-column prop="couChileType" label="课程类别">
              <template scope="scope">
                <p v-if="scope.row.couType==='1'&scope.row.couChileType==='1'">老年疾病康复</p>
                <p v-if="scope.row.couType==='1'&scope.row.couChileType==='2'">儿科疾病康复</p>
                <p v-if="scope.row.couType==='1'&scope.row.couChileType==='3'">运动创伤康复</p>
                <p v-if="scope.row.couType==='1'&scope.row.couChileType==='4'">神经系统疾病康复</p>
                <p v-if="scope.row.couType==='1'&scope.row.couChileType==='5'">其他康复

                <p v-if="scope.row.couType==='2'&scope.row.couChileType==='1'">老年护理</p>
                <p v-if="scope.row.couType==='2'&scope.row.couChileType==='2'">儿童护理</p>
                <p v-if="scope.row.couType==='2'&scope.row.couChileType==='3'">内科护理</p>
                <p v-if="scope.row.couType==='2'&scope.row.couChileType==='4'">外科护理</p>
                <p v-if="scope.row.couType==='2'&scope.row.couChileType==='5'">其他护理</p>

                <p v-if="scope.row.couType==='3'&scope.row.couChileType==='1'">饮食保健</p>
                <p v-if="scope.row.couType==='3'&scope.row.couChileType==='2'">运动保健</p>
                <p v-if="scope.row.couType==='3'&scope.row.couChileType==='3'">中医针灸推拿养生</p>
                <p v-if="scope.row.couType==='3'&scope.row.couChileType==='4'">其他</p>

                <p v-if="scope.row.couType==='4'&scope.row.couChileType==='1'">急救</p>
                <p v-if="scope.row.couType==='4'&scope.row.couChileType==='2'">传染病</p>
                <p v-if="scope.row.couType==='4'&scope.row.couChileType==='3'">其他</p>
              </template>
            </el-table-column>
            <el-table-column prop="teaName" label="课程作者"></el-table-column>
            <el-table-column prop="couIntroduction" label="课程简介" width="400rpx"></el-table-column>
            <el-table-column prop="couChaNum" label="课程章节数"></el-table-column>
            <el-table-column prop="couPrice" label="课程价格"></el-table-column>
            <el-table-column prop="joinDate" label="加入时间"></el-table-column>
            <el-table-column prop="operation" label="操作">
                <template slot-scope="scope">
                    <!-- 删除按钮 -->
                    <el-button type="danger" icon="el-icon-delete" @click="removeCoursById(scope.row.id)">
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页区域 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="queryInfo.pageNum" :page-sizes="[1, 4, 6]" :page-size="queryInfo.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
        <!-- 添加班级-课程的对话框 -->
        <el-dialog title="添加课程" :visible.sync="addDialogVisible" width="80%" @close="addDialogClosed">
            <!-- 内容主体区域 -->
            <!-- 搜索框 -->
            <el-input placeholder="请输入想要搜索课程的名称" v-model="DXcondition" clearable @clear="tonullCondition">
                <el-button slot="append" icon="el-icon-search" @click="getfindCourse"></el-button>
            </el-input>
            <!-- 多选表格 -->
            <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column label="课程Id" width="120">
                    <template slot-scope="scope">{{ scope.row.couId }}</template>
                </el-table-column>
                <el-table-column prop="couName" label="课程名称" width="120">
                </el-table-column>
                <el-table-column prop="teaName" label="作者" width="120">
                </el-table-column>
                <el-table-column prop="couIntroduction" label="简介" width="600">
                </el-table-column>
            </el-table>
            <!-- 分页区域 -->
            <el-pagination @size-change="handleSizeChangeDX" @current-change="handleCurrentChangeDX"
                :current-page="queryfindCourse.pageNum" :page-size="queryfindCourse.pageSize"
                layout="total, prev, pager, next" :total="DXtotal">
            </el-pagination>
            <!-- 底部区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="toggleSelection()">取消选择</el-button>
                <el-button type="primary" @click="addCours">确定选择</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            //获取班级-课程列表的参数对象
            queryInfo: {
                classId: '',
                // 当前的页数
                pageNum: 1,
                // 当当前每页显示多少条数据
                pageSize: 6,
                couId: '',
                couName: ''
            },
            condition: '',
            search: '',
            queryfindCourse: {
                classId: '',
                couName: '',
                pageSize: 5,
                pageNum: 1,
                deleted: 0
            },
            findCourse: [],
            courslist: [],
            total: 0,
            // 控制添加班级-课程对话框的显示与隐藏
            addDialogVisible: false,
            // 添加班级-课程的表单数据
            addForm: {
                couName: ''
            },
            // 添加表单的验证规则对象
            addFormRules: {
                couName: [
                    { required: true, message: '请输入课程名称', trigger: 'blur' }
                ]
            },
            restaurants: [],
            timeout: null,
            classIN: {
                classId: '',
                className: ''
            },
            findCourseOb: [],
            addCourse: {
                classId: '',
                couId: ''
            },
            DXcondition: '',
            tableData: [],
            DXtotal: ''
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
            this.addCourse.classId = this.classIN.classId
            this.queryfindCourse.classId = this.classIN.classId
        }
        this.getCoursList()
        this.getfindCourse()
    },
    methods: {
        //查询所有可选择的课程
        getfindCourse() {
            if (this.DXcondition === '') {
                console.log(this.queryfindCourse, "queryfindCourse")
                this.request.get('/service-class/class/findCourse', {
                    params: this.queryfindCourse
                }).then(res => {
                    if (res.code === '200') {
                        this.tableData = res.data.records
                        console.log(this.tableData, "tableData")
                        this.DXtotal = res.data.total
                    } else {
                        this.$message.error("获取可选班级信息失败！")
                    }
                })
            } else if (this.DXcondition !== '') {
                console.log(this.DXcondition, "DXcondition")
                this.queryfindCourse.couName = this.DXcondition
                this.request.get('/service-class/class/findCourse', {
                    params: this.queryfindCourse
                }).then(res => {
                    if (res.code === '200') {
                        this.tableData = res.data.records
                        console.log(this.tableData, "tableData")
                        this.DXtotal = res.data.total
                    } else {
                        this.$message.error("获取可选班级信息失败！")
                    }
                })
            }
        },
      //第二个
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
        //添加课程的搜索框清除
        tonullCondition() {
            this.queryfindCourse.couName = ''
            this.DXcondition = ''
            this.getfindCourse()
        },
        //查询该班级已经添加的课程
        getCoursList() {
            this.search = this.condition
            if (this.search === '') {
                this.queryInfo.couName = ''
                this.queryInfo.couId = ''
                this.request.get('/service-class/class/findClassCourse', {
                    params: this.queryInfo
                }).then(res => {
                    if (res.code === '200') {
                        this.courslist = res.data.records
                        console.log(this.courslist,"courslist")
                        this.total = res.data.total
                    } else {
                        this.$message.error("获取" + this.classIN.className + "课程列表失败！")
                    }
                })
            } else if (this.search !== '') {
                this.queryInfo.couId = this.search
                this.queryInfo.pageSize = 6
                this.queryInfo.pageNum = 1
                this.request.get('/service-class/class/findClassCourse', {
                    params: this.queryInfo
                }).then(res => {
                    if (res.code === '200') {
                        this.courslist = res.data.records
                        if (this.courslist.length === 0) {
                            this.queryInfo.couId = ''
                            this.queryInfo.couName = this.search
                            this.request.get('/service-class/class/findClassCourse', {
                                params: this.queryInfo
                            }).then(res => {
                                if (res.code === '200') {
                                    this.courslist = res.data.records
                                    this.total = res.data.total
                                    if (this.courslist.length === 0) {
                                        this.$message.info("搜索结果为空！")
                                    }
                                } else {
                                    this.$message.error("获取课程列表失败！")
                                }
                            })
                        } else {
                            this.total = res.data.total
                            if (this.courslist.length === 0) {
                                this.$message.info("搜索结果为空！")
                            }
                        }
                    } else {
                        this.$message.error("获取课程数据失败！")
                    }
                })
            }

        },
        togetCouList() {
            this.condition = ''
            this.getCoursList()
        },
        // 点击按钮，添加新班级-课程
        addCours() {
            console.log(this.multipleSelection, "multipleSelection")
            for (let i = 0; i < this.multipleSelection.length; i++) {
                this.multipleSelection[i].classId = this.addCourse.classId
            }
            console.log(this.multipleSelection, "multipleSelection")
            let total = this.multipleSelection.map(v => ({ classId: v.classId, couId: v.couId }))
            console.log(total)
            this.request.post('/service-course/course/saveBatch', total).then(res => {
                if (res.code === '200') {
                    this.$message.success("添加课程成功！")
                    this.getfindCourse()
                    this.getCoursList()

                }
            })
        },
        loadAll() {
            return this.findCourseOb;
        },
        querySearchAsync(queryString, cb) {
            var restaurants = this.restaurants;
            var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;

            clearTimeout(this.timeout);
            this.timeout = setTimeout(() => {
                cb(results);
            }, 3000 * Math.random());
        },
        createStateFilter(queryString) {
            return (state) => {
                return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            };
        },
        handleSelect(item) {
            // console.log(item,"item");
            this.addCourse.couId = item.id + ''
            // console.log(this.addCourse)
        },
        // 监听添加班级-课程对话框的关闭事件
        addDialogClosed() {
            this.$refs.addFormRef.resetFields()
        },
        // 监听pagesize改变的事件
        handleSizeChange(newSize) {
            // console.log(newSize)
            this.queryInfo.pageSize = newSize
            this.getCoursList()
        },
        // 监听页码值改变的事件
        handleCurrentChange(newPage) {
            // console.log(newPage)
            this.queryInfo.pageNum = newPage
            this.getCoursList()
        },
        // 监听pagesize改变的事件
        handleSizeChangeDX(newSize) {
            // console.log(newSize)
            this.queryfindCourse.pageSize = newSize
            this.getfindCourse()
        },
        // 监听页码值改变的事件
        handleCurrentChangeDX(newPage) {
            // console.log(newPage)
            this.queryfindCourse.pageNum = newPage
            console.log(this.queryfindCourse.pageNum, "pageNum")
            this.getfindCourse()
        },
        // 根据cou_id,class_id删除对应的班级-课程信息
        async removeCoursById(id) {
            // console.log(id)
            const confirmResult = await this.$confirm('此操作将永久删除该班级, 是否继续?', '提示', {
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
                this.request.delete('/service-course/course/leaveCourse/' + id).then(res => {
                    // console.log(res,"res")
                    if (res.code === '200') {
                        this.$message.success("删除成功！")
                        this.getCoursList()
                    } else {
                        this.$message.error("删除失败！")
                    }
                })
            }
        },
      //第一个
      //   toggleSelection(rows) {
      //       if (rows) {
      //           rows.forEach(row => {
      //               this.$refs.multipleTable.toggleRowSelection(row);
      //           });
      //       } else {
      //           this.$refs.multipleTable.clearSelection();
      //       }
      //   },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        }
    },
    mounted() {
        this.restaurants = this.loadAll();
        // console.log(this.findCourseOb,'mounted')
    }
}
</script>

<style lang="less" scoped>
.coursG-top {
    margin-top: -10px;

    .el-page-header {
        margin-bottom: 20px;
    }

}

/deep/.el-table {
    margin-top: 20px;

    .cell {
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
    }
}

.el-pagination {
    margin-top: 20px;
}

h3 {
    text-align: center;
}
</style>