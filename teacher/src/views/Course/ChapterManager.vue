<template>
  <div>
    <div class="chapter-top">
      <!-- 跳转到课程管理 -->
      <el-page-header @back="$router.push('/course')" :content="courseData.couName"></el-page-header>

      <div class="course-couIntroduction">
        <h3>{{courseData.couName}}</h3>
        <p>课程介绍： {{courseData.couIntroduction}}</p>
      </div>
    </div>

    <!-- 章节 -->
    <div class="chapter-bottom">
      <!-- 搜索 -->
      <el-input style="width: 200px" suffix-icon="el-icon-search"  placeholder="请输入章节名称" v-model="chaTitle" @clear="load" clearable></el-input>
      <el-button style="margin-left: 10px" type="primary" @click="load">搜索</el-button>
      <!-- 刷新按钮 -->
      <el-button size="mini" style="margin-left: 10px" type="primary" @click="reast"><i class="el-icon-refresh"></i></el-button>
      <!-- 新增章节按钮 -->
      <el-button v-show="user.truename === courseData.teaName " type="primary" @click="addChapter" >新增章节 <i class="el-icon-circle-plus-outline"></i></el-button>
      <!-- 章节详细表 -->
      <el-table
          :header-cell-style="{ backgroundColor: 'rgb(244, 244, 245)' }"
          row-key="id"
          :key="keys"
          :data="tableData"
          stripe border >

        <el-table-column  prop="chaIndex' " label="章节序号" width="120">
          <template slot-scope="scope">
            第{{scope.row.chaIndex}}章
          </template>
        </el-table-column>
        <el-table-column prop="chaTitle" label="章节名称" width="180"></el-table-column>
        <el-table-column prop="chaTime" label="章节时长" width="180">
          <template slot-scope="scope">
            {{Math.floor(scope.row.chaTime / 3600)}}时
            {{Math.floor( (scope.row.chaTime % 3600)/60 )}}分
            {{scope.row.chaTime % 60}}秒
          </template>
        </el-table-column>
        <el-table-column prop="chaUrl" label="视频地址"></el-table-column>
        <el-table-column prop="" label="操作" width="200">
          <template slot-scope="scope">
            <!-- 播放 -->
            <el-button type="info"  icon="el-icon-video-play" @click="play(scope.row.chaUrl)"></el-button>
            <!-- 修改章节按钮 -->
            <el-button v-show="user.truename === courseData.teaName " type="primary" @click="editChapter(scope.row)" icon="el-icon-edit"></el-button>

            <!--  删除章节按钮 -->
            <el-button v-show="user.truename === courseData.teaName " type="danger" icon="el-icon-delete" @click="deleteChapter(scope.row.chaId,scope.row.chaTitle)"></el-button>
          </template>
        </el-table-column>

      </el-table>


    </div>

    <!-- 新增章节弹窗 -->
    <el-dialog
        title="新增章节"
        @close="refresh"
        :visible.sync="dialogVisibleAdd"
        :before-close="handleClose"
        width="48%" center>
      <el-form  label-width="90px" ref="chapterFrom" :rules="rules" :model="form" :inline="true">
        <el-form-item  label="章节序号" prop="chaIndex" class="demo-form-inline">
          <el-input  v-model.number="form.chaIndex" placeholder="请输入章节序号" clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="章节名称" prop="chaTitle" class="demo-form-inline" >
          <el-input v-model="form.chaTitle" placeholder="请输入章节名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="视频地址" prop="chaUrl" style="" class="demo-form-inline">
          <el-input v-model="form.chaUrl" placeholder="请输入视频地址" clearable></el-input>
        </el-form-item>
        <el-form-item label="视频时长" class="demo-form-inline">
          <el-button type="primary"  v-if="show===true" @click="length">获取</el-button>
          <el-input v-model="videolength"  v-if="hide===true" ></el-input>
        </el-form-item>
        <!-- <el-form-item label="视频时长"   class="demo-form-inline">
            <el-input style="width: 45px;" v-model="hour"  /> 时
            <el-input style="width: 45px;" v-model="minute"  /> 分
            <el-input style="width: 45px;" v-model="second"  /> 秒

        </el-form-item> -->
        <!-- <el-form-item >
            <el-input type="hidden" v-model="form.courseId = courseData.couId" ></el-input>
        </el-form-item> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleAdd = false">取 消</el-button>
                <el-button type="primary" @click="saveChapter">确 定</el-button>
            </span>
    </el-dialog>

    <!-- 播放 -->
    <el-dialog
        title="视频播放"
        :visible.sync="dialogVisiblePlay"
        width="58%" center>
      <div style="width: 99%;height: 400px">
        <vueMiniPlayer ref="vueMiniPlayer" :video="video" :mutex="true" @fullscreen="handleFullscreen" />
      </div>
    </el-dialog>


  </div>
</template>
<script>
//导入Sortable组件
import Sortable from 'sortablejs'
export default{
  //注册Sortable组件
  components: {
    // eslint-disable-next-line vue/no-unused-components
    Sortable
  },
  data () {
    return {
      videolength: 0,//视频时长
      hide: '',//隐藏样式
      show: '',//显示样式
      keys:'',
      drag:false,
      hour:'',//时
      minute: '',//分钟
      second: '',//秒
      chaTitle: '',//章节名称
      tableData: [ ],
      dialogVisibleAdd: false,//新增课程弹窗
      dialogVisibleEdit: false,//修改课程弹窗
      dialogVisiblePlay: false,//播放
      chaNunber: 0,//章节数
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      courseData: {},
      rules:{//新增弹窗表单验证
        chaIndex: [
          {required: true, message: '请输入章节序号', trigger: 'blur'},
          { type: 'number', message: '章节序号必须为数字值'}
        ],
        chaTitle: [
          {required: true, message: '请输入章节名称', trigger: 'blur'},
        ],
        chaUrl: [
          {required: true, message: '请输入视频地址', trigger: 'blur'},
        ],
        minute:[
          {required: true, message: '请输入视频时长', trigger: 'blur'},
        ]
      },
      video: {
        // 视频播放源
        url: '',
        // 视频海报
        // cover: 'https://i.loli.net/2019/06/06/5cf8c5d9c57b510947.png',
        muted: false, // 是否静音播放
        loop: false, // 视频是否循环播放
        preload: 'auto', // 视频预加载
        poster: '', // 原生视频默认海报暂不设置
        volume: 1, // 默认音量
        autoplay: false, // 视频自动播放
      }
    }
  },
  mounted () {
    this.rowDrop()  //列表行拖拽后顺序数组
  },
  created(){
    this.courseData = JSON.parse(this.$route.query.courseData)
    this.chaNunber = this.courseData.couChaNum
    this.hide=false
    console.log(this.hide)
    this.show=true
    this.load()
    console.log(this.courseData.teaName,this.user.truename)
  },
  computed:{
    $video() {
      return this.$refs.vueMiniPlayer.$video;
    }
  },
  methods: {
    load(){
      this.request.get("/service-chapter/chapter/allchapter",{
        params:{
          courseId: this.courseData.couId,
          chaTitle: this.chaTitle,
        }
      }).then(res => {
        //console.log(res,'res')
        this.tableData = res.data
        this.rowDrop()
      })
    },
    //刷新
    reast(){
      this.chaTitle = ''
      this.load()
    },
    //新增章节按钮
    addChapter(){
      this.dialogVisibleAdd = true;
    },
    //新增章节弹窗确定按钮
    saveChapter(){
      this.$refs['chapterFrom'].validate((valid) => {
        if(valid){

          this.form.chaTime = this.videolength
          this.form.courseId = this.courseData.couId //课程id
          //this.form.chaTime = this.hour*3600 + this.minute*60 + this.second*1,//视频时长
          this.request.post("/service-chapter/chapter",this.form).then(res => {
            if(res.code === '200'){
              this.dialogVisibleAdd = false;
              this.$message.success("新增章节成功！！！")
              this.keys = Math.random()//用于改变key，使得视图重新渲染
              this.load()
              this.form = {}
              this.hour = ''
              this.minute = '',
                  this.second = ''
            }else{
              this.$message.error("新增章节失败！！！")
            }
          })



        }
      })
    },
    //新增章节弹窗关闭按钮
    handleClose(done) {
      this.form = {}
      this.hour = ''
      this.minute = ''
      this.second = ''
      done();
    },
    //修改章节按钮
    editChapter(row){
      this.form = row

      //console.log(row.chaTime,this.hour,this.minute,this.second)
      this.dialogVisibleAdd = true
    },
    //删除章节按钮
    deleteChapter(chaId,chaName){
      this.$confirm('此操作将永久删除《'+chaName+'》是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.request.delete("/service-chapter/chapter/" + chaId).then(res => {
          if (res.code === '200') {
            this.$message.success("删除成功！！！")
            this.keys = Math.random()//用于改变key，使得视图重新渲染
            this.load()
          } else {
            this.$message.error("删除失败！！！！")
          }
        })


      }).catch(() => {
        this.$message.info("已取消删除")
      });

    },
    //获取视频时长
    length(){
      var audio = new Audio(this.form.chaUrl)
      let that = this
      // eslint-disable-next-line no-unused-vars
      audio.addEventListener("loadedmetadata", function (e) {
        that.videolength = Math.floor(audio.duration)  // 通过添加监听来获取视频总时长字段，即duration
        console.log('视频时长为:' ,that.videolength)
      });
      that.show = false
      that.hide=true
    },
    refresh(){
      this.show = true
      this.hide=false
      this.videolength=0

    },
    //播放
    play(chaUrl){
      this.dialogVisiblePlay = true
      this.video.url = chaUrl
    },
    //视频播放
    handleFullscreen(){

    },
    // 行拖拽
    rowDrop () {
      const tbody = document.querySelector('.el-table__body-wrapper tbody')
      Sortable.create(tbody, {
        // filter: '.filtered',
        onEnd: ({ newIndex, oldIndex }) => {
          // handle: '.allowDray'
          // console.log(`newIndex=${newIndex}`,`oldIndex=${oldIndex}`)
          this.request.get("/service-chapter/chapter/drag",{
            params: {
              couId: this.courseData.couId,
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              newIndex: newIndex,
              oldIndex: oldIndex
            }
            // eslint-disable-next-line no-unused-vars
          }).then(res => {
            this.keys = Math.random()//用于改变key，使得视图重新渲染
            this.load()
            // _this.$nextTick(() => {
            //     //重新初始化
            //     _this.sortable()
            // })
            //console.log(res,'拖拽')

          })
        }
      })
    },

  }

}
</script>
<style lang="less" scoped>
.block{
  margin-top: 20px;
}
.chapter-top{
  margin-top: -10px;
  .el-page-header{
    margin-bottom: 20px;
  }
  .course-couIntroduction{
    width: 80%;
    margin: auto;
    h3{
      text-align: center;
      line-height: 30px;
    }
    p{
      margin-top: 15px;
      line-height: 30px;
      color: rgb(79, 78, 78);
    }
  }
}
.chapter-bottom{
  width: 80%;
  margin: auto;
  .demo-form-inline{
    margin-top: 20px;
  }
  /deep/.el-table{
    margin-top: 20px;
    .cell {
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 1;
    }
  }
}
.el-form{
  .el-form--inline .el-form-item{
    margin-right: 0;
  }

}
/deep/._time-current {
  width: 50px;
}

/deep/ ._time-amount {
  width: 50px;
}


</style>