<template>
    <div>
        <el-card class="box-card">
            <el-col :span="10" >              
                <el-avatar    :src="this.user.avatar" ></el-avatar>
                <div>
                    <el-upload
                        class="avatar-uploader"
                        action="http://localhost:9090/service-coustype/file/upload"
                        :show-file-list="false"     
                                         
                        :on-success="handleAvatarSuccess" style="float: left;">

                        <el-button type="success" plain>更换头像</el-button>
                    </el-upload>
                    <el-button style="margin-left:20px ;" type="warning" @click="changePassword" plain>修改密码</el-button>
                </div>
                
            </el-col>
            <el-col :span="14">
                <el-descriptions size="medium" :title="user.truename" :column="1" >
                    <el-descriptions-item  label="所教班级" >
                        <template v-for="item in classes" >
                            <span>{{item.className}}，</span>
                        </template>
                    </el-descriptions-item>                   
                </el-descriptions>
            </el-col>
        </el-card>

        <!-- 修改密码弹窗 -->
        <el-dialog 
            title="修改密码"
            :visible.sync="dialogVisible"
            width="26%" center>

            <el-form :model="form" :rules="rules" ref="userForm" >
                <el-form-item  >
                 <el-input size="medium" :disabled="true" prefix-icon="el-icon-user"  v-model="user.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="oldPassword" >
                    <el-input size="medium"  prefix-icon="el-icon-lock"  v-model="form.oldPassword" placeholder="请输入旧密码" show-password autocomplete="off"></el-input>
                  </el-form-item>
                <el-form-item prop="password" >
                  <el-input size="medium"  prefix-icon="el-icon-lock"  v-model="form.password" placeholder="请输入新密码" show-password autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="confirmPassword" >
                  <el-input size="medium"  prefix-icon="el-icon-lock" v-model="form.confirmPassword" placeholder="请确定新密码" show-password  autocomplete="off"></el-input>
                </el-form-item>
              </el-form>
              
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="savePassword">确 定</el-button>
            </span>
        </el-dialog>

    </div>
       

   
</template>
<script>
export default{
    data () {
        return {
            dialogVisible: false,
            user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {},
            photo: '',
            classes: [],
            form:{},
            rules: {
                oldPassword: [
                    {required: true, message: '请输入旧密码', trigger: 'blur'},
                    {min: 4, max: 12, message: '密码长度在 4 到 12 个字符', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入新密码', trigger: 'blur'},
                    {min: 4, max: 12, message: '密码长度在 4 到 12 个字符', trigger: 'blur'}
                ],
                confirmPassword: [
                     {required: true, message: '请确定密码', trigger: 'blur'},
                    {min: 4, max: 12, message: '密码长度在 4 到 12 个字符', trigger: 'blur'}
                ],
            }
        }
    },
    created(){
        this.load()
    },
    methods: {
        load(){
            this.request.get("/service-class/class/page",{
                params: {
                    teaName: this.user.truename,
                    pageNum: 1,
                    pageSize: 100,
                }
            }).then(res => {
                // console.log(res.data.records[0].className)
                this.classes = res.data.records
            })
        },
       
        //修改头像
        handleAvatarSuccess(res){
            // console.log(res)
            for (let i=0 ;i< res.data.length ;i++){
              this.photo = res.data[i].url
              console.log(this.photo,'url')
            }     
            
            this.request.post("/service-user/user",{
                id:this.user.id,
                avatar:  this.photo
            }).then(res => {
                if(res.code === '200'){
                    //console.log(res)
                    this.$message.success("更改头像成功!")
                    //更新浏览器缓存user
                    this.user.avatar = this.photo
                    localStorage.setItem("user",JSON.stringify(this.user))
                    //触发父级Main.vue,更新user
                    this.$emit("refrehUser")
                }else{
                    this.$message.error("更改头像失败！")
                }
            })
        },
         //修改密码按钮
        changePassword(){
            this.dialogVisible = true
        },
        //修改密码弹窗的确定按钮
        savePassword(){
            this.$refs['userForm'].validate((valid) => {
                if(valid){
                    if(this.form.oldPassword !== this.user.password){
                        this.$message.error("您输入的旧密码错误！")      
                        this.dialogVisible = true                 
                    }else if(this.form.password !== this.form.confirmPassword){
                        this.$message.error("您输入的新密码与再次输入的新密码不一致！")      
                        this.dialogVisible = true 
                    }else{
                        this.request.post("/service-user/user",{
                            id:this.user.id,
                            password: this.form.password
                        }).then(res => {
                            if(res.code === '200'){
                                this.$message.success("修改密码成功！") 
                                this.dialogVisible = false 
                                //更新浏览器缓存user
                                this.user.password = this.form.password
                                localStorage.setItem("user",JSON.stringify(this.user))
                                //触发父级Main.vue,更新user
                                this.$emit("refrehUser")
                            }else{
                                this.$message.error("修改密码失败！") 
                            }
                            
                            // this.$refs.userForm.resetFields();//重置输入框
                        })                      
                    }                 
                }
            })
           
        }
    }
}
    
</script>
<style lang="less" scoped>
.box-card {
    width: 60%;
    margin: auto;
    padding: 20px;
    .el-col{
        /deep/.el-descriptions{
            font-size: 18px;
            .el-descriptions__title{
                font-size: 22px;
            }
            .el-descriptions-item__content{
                display: flex;
                flex-wrap: wrap;
            }
        }
        .el-avatar{
            width: 200px;
            height: 200px;
            margin-bottom: 20px;
        }
    }
}
</style>