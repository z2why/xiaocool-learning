<template>
    <div class="background">
        <div class="login">
            <div class="avatar_box">
                <img src="../assets/avater.jpeg" alt="">
            </div>
            <el-form  ref="loginForm"  :model="user" :inline="true" :rules="rules">
                <el-form-item  prop="username">
                    <el-input size="large" v-model="user.username" prefix-icon="el-icon-user" placeholder="请输入用户名" clearable></el-input>
                </el-form-item>
                <el-form-item size="medium" prop="password">
                    <el-input type="password" v-model="user.password" prefix-icon="el-icon-lock" placeholder="请输入密码"  show-password ></el-input>
                </el-form-item>
                <el-form-item >
                    <el-button size="medium" @click="login" type="primary">登录</el-button>
                    <el-button size="medium" @click="resetLogin">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
   
</template>
<script >
import Mock from 'mockjs'
import Cookie from 'js-cookie'
import axios from 'axios'

export default{
    data () {
        return {
            rules:{
                username: [
                    {required: true, trigger: 'blur', message: '请输入用户名'},
                    { min: 4, max: 12, message: '长度在 4 到 12 个字符', trigger: 'blur' }
                ],
                password: [
                    {required: true, trigger: 'blur', message: '请输入密码'},
                    { min: 4, max: 12, message: '长度在 4 到 12 个字符', trigger: 'blur' }
                ],
            },
            user: {},
        }
    },
    methods: {
        //登录
        login(){
            //token信息
            // const token = Mock.Random.guid()
            // //将token信息存入cookie用于不同页面的通信
            // Cookie.set('token',token);
            this.$refs['loginForm'].validate((valid) => {
                if(valid){
                    this.request.post("/service-user/user/login",this.user).then(res =>{                   
                        if(res.code === '200'){                     
                            if(res.data.actor === '教师'){
                                console.log(res)
                                localStorage.setItem("user",JSON.stringify(res.data)) //把后端传过来的用户信息存储到浏览器
                                sessionStorage.setItem("menu",JSON.stringify({ path: "/classes", name: "classes",}))
                                //跳转到首页
                                this.$router.push('/classes')
                                this.$message.success("登录成功")
                            }else{
                                this.$message.error("用户不存在！！！")
                            }
                        }else{
                            this.$message.error(res.msg)
                        }
                    })
                    
                }
            })
            

        },
        resetLogin(){
            this.$refs.loginForm.resetFields();
        }

    },
    
}
</script>
<style lang="less" scoped>
.background{
    margin: 0;
    padding: 0;
    width: 100%;
    height: 100%;
    background: url(../assets/background.jpeg)  no-repeat center 0px;
    position:fixed;
    background-size: cover;

    .login{
        width: 450px;  
        height: 300px;
        margin: 180px auto;
        border:1px solid #eaeaea ;
        background-color: #fff;
        border-radius: 15px;
        box-shadow: 0 0 10px #cac6c6;
        .avatar_box{
            // 将图片变小
            height: 100px;
            width: 100px;
            border: 10px solid #f9f9f9;
            border-radius: 50%;
            padding: 10px;
            box-shadow: 0 0 10px rgb(185, 215, 239);
            position: absolute;
            left: 50%;
            transform: translate(-50%,-50%);
            background-color: #fff;
            img{
                width: 100%;
                height: 100%;
                border-radius: 50%;
                background-color: #eee;
            }
        }
    
        .el-form{
            margin-top: 70px;
            padding: 30px;
            box-sizing: border-box;
            .el-input{
                width: 380px;

            }
            h3{
                text-align: center;
                margin-bottom: 20px;
            }
            .el-form-item{
                display: flex;
                justify-content: center;
                .el-input{
                    width: 380px;
                 
                }
            }
           
        }
    }

    
}

</style>