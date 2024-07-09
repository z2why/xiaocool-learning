<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="网站名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getDataList()">查询</el-button>
        <!-- <el-button type="success" @click="addOrUpdateHandle()">新增</el-button> -->
        <el-button type="danger"  @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
        <el-button type="warning" @click="goBack()">返回</el-button>
      </el-form-item>
    </el-form>
    <el-table
      row-key="id"
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="left"
        align="left"
        width="50">
      </el-table-column>
      <!-- <el-table-column
        width="150"
        prop="orderNumber"
        header-align="left"
        align="left"
        label="排列序号">
      </el-table-column> -->
      <el-table-column
        prop="name"
        header-align="left"
        align="left"
        label="网站名称">
        <template slot-scope="scope">
          <a :href="scope.row.netAddress" target="_blank" style="text-decoration: none; color: black;">{{scope.row.name}}</a>
        </template>
      </el-table-column>
      <el-table-column
        prop="netAddress"
        header-align="left"
        align="left"
        label="网站地址">
      </el-table-column>
      <el-table-column
        prop="operation"
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[5, 10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
  </div>
</template>
 
<script>
  import Sortable from 'sortablejs'
  export default {
    data () {
      return {
        dataForm: {
          id: '',
          name: '',
          classify: '',
          orderNumber: '',
          netAddress: ''
        },
        dataList: [
          {
            name: '1111',
            netAddress: 'www.111'
          },
          {
            name: '222',
            netAddress: 'www.222'
          }
        ],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate,
      Sortable
    },
    // 组件实例化完毕，但页面还未显示,接收上个页面传过来的参数
    created () {
      //this.classify = this.$route.params.classifyId
    },
    activated () {
      this.getDataList()
    },
    mounted () {
      this.rowDrop()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        // this.$http({
        //   url: this.$http.adornUrl('/xxx/outdata/list'),
        //   method: 'get',
        //   params: this.$http.adornParams({
        //     'page': this.pageIndex,
        //     'limit': this.pageSize,
        //     'classify': this.classify,
        //     'name': this.dataForm.name
        //   })
        // }).then(({data}) => {
        //   if (data && data.code === 0) {
        //     this.dataList = data.page.list
        //     this.totalPage = data.page.totalCount
        //   } else {
        //     this.dataList = []
        //     this.totalPage = 0
        //   }
        //   this.dataListLoading = false
        // })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        // this.$nextTick(() => {
        //   this.$refs.addOrUpdate.init(id)
        // })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        // this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        //   confirmButtonText: '确定',
        //   cancelButtonText: '取消',
        //   type: 'warning'
        // }).then(() => {
        //   this.$http({
        //     url: this.$http.adornUrl('/xxx/outdata/delete'),
        //     method: 'post',
        //     data: this.$http.adornData(ids, false)
        //   }).then(({data}) => {
        //     if (data && data.code === 0) {
        //       this.$message({
        //         message: '操作成功',
        //         type: 'success',
        //         duration: 1500,
        //         onClose: () => {
        //           this.getDataList()
        //         }
        //       })
        //     } else {
        //       this.$message.error(data.msg)
        //     }
        //   })
        // })
      },
      // 行拖拽
      rowDrop () {
        const tbody = document.querySelector('.el-table__body-wrapper tbody')
        Sortable.create(tbody, {
          onEnd: ({ newIndex, oldIndex }) => {
        //     this.$http({
        //       url: this.$http.adornUrl('/xxx/outdata/drag'),
        //       method: 'get',
        //       params: this.$http.adornParams({
        //         'pageIndex': this.pageIndex,
        //         'pageSize': this.pageSize,
        //         'newIndex': newIndex,
        //         'oldIndex': oldIndex
        //       })
        //     }).then(() => {
        //       this.getDataList()
        //     })
          }
        })
      },
      goBack () {
        this.$router.back(-1)
      }
    }
  }
</script>