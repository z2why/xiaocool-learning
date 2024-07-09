<template>
    <el-menu 
      :default-active="$route.path" 
      class="el-menu-vertical-demo" 
      :collapse="isCollapse"
      background-color="#f6f6f6"
      text-color="#000"
      active-text-color="#67C23A"
      router
      >
      <h3>
        <!-- 图标 -->
        <img src="@/assets/edu.png"/>
        {{isCollapse ? '' : '教师端'}}
      </h3>
      <el-menu-item @click="clickMenu(item)"  v-for="item in noChildren" :key="item.name" :index="item.path">
          <i :class="`el-icon-${item.icon}`"></i>
          <span slot="title">{{ item.label }}</span>
      </el-menu-item> 
 
      <!-- 个人中心 -->
      <el-submenu  v-for="item in hasChildren" :key="item.label" :index="item.label">
          <template slot="title">
              <i :class="`el-icon-${item.icon}`"></i>
              <span slot="title">{{ item.label }}</span>
          </template>
          <el-menu-item-group class="ex" v-for="subItem in item.children" :key="subItem.path">
            <el-menu-item @click="clickMenu(subItem)" :index="subItem.path">
              <i :class="`el-icon-${subItem.icon}`"></i>
              {{ subItem.label }}
            </el-menu-item>
            
          </el-menu-item-group>
      </el-submenu>
        
      </el-menu>
</template>

<script>
  export default {
    data() {
      return {      
        menuData: [
          {
            path: "/classes",
            name: "classes",
            label: "班级管理",
            icon: "s-home",
          },
          {
            path: "/course",
            name: "course",
            label: "课程管理",
            icon: "video-camera-solid",
          },    
          {
            path: "/practice",
            name: "practice",
            label: "线下实践管理",
            icon: "s-opportunity",
          },
          {
            path: "/order",
            name: "order",
            label: "订单审核",
            icon: "s-order",
          },     
          {
            path: "/find",
            name: "find",
            label: "发现",
            icon: "news",
          },         
          {
            path: "/person",
            name: "person",
            label: "个人中心",
            icon: "user-solid",
          }
        ],
      };
    },
    methods: {
      //点击菜单进行跳转
      clickMenu(item) {
        //将item存储在sessionStorage
        sessionStorage.setItem("menu",JSON.stringify(item))
        //更新面包屑
        this.$store.commit('selectMenu')
      }
    },
    created(){
     this.$store.commit('selectMenu')
    },
    computed: {
      //没有子菜单
      noChildren() {
          return this.menuData.filter(item => !item.children);
      },
      //有子菜单
      hasChildren() {
        return this.menuData.filter(item => item.children);
      },
      //默认展示菜单
      isCollapse(){
        return this.$store.state.tab.isCollapse
      },
      
    },
  }
</script>
<style>

</style>
<style lang="less" scoped>
*{
  user-select: none;

}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 250px;
  min-height: 100vh;
}

  .el-menu{
    height: 100%;
    border-right: none;
    h3{
      color: black;
      text-align: center;
      line-height: 48px;
      font-size: 18px;
      font-weight: 400;
      img{
        width: 30px;
        height: 30px;
        position: relative;
        top: 8px;
        margin-right: 5px;
      }
    }
  }

</style>