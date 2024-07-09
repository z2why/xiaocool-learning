<template>
    <div class="tabs">
        <el-tag
            type="success"
            v-for="(item,index) in tags"
            :key="item.path"
            :closable="item.name !== 'classes'"
            :effect="$route.name === item.name ? 'dark' : 'plain' "
            @click="changeMenu(item)"
            @close="handleClose(item,index)"
            size="small">
            <!-- closable高亮， -->
            {{ item.label }}
        </el-tag>
    </div>
</template>
<script>
import { mapState,mapMutations } from 'vuex';
export default{
  // eslint-disable-next-line vue/multi-word-component-names
    name: 'Tag',
    data () {
        return {
           
        }
    },
    computed: {
        ...mapState({
            tags: state => state.tab.tabsList
        })
    },
    methods: {
        ...mapMutations(['closeTag']),
        //点击tag跳转的功能
        changeMenu(item){
            // console.log(item.name,'item')
            this.$router.push({name : item.name}) //根据name进行跳转
            // this.$router.push(item.path)    //根据path进行跳转
        },
        //点击tag删除的功能
        handleClose(item,index){
            //调用store中的mutation
            this.closeTag(item);
            const length = this.tags.length;
            //删除之后的逻辑
            //删除的不是当前已经激活的tag
            if(item.name !== this.$route.name){
                return
            }
            //表示的是删除的最后一项
            if(index === length){
                this.$router.push({
                    name: this.tags[index - 1].name
                })
                                  
            }else{
                this.$router.push({
                    name: this.tags[index].name
                })
            }
        }
    }
    // mounted () {
    //     console.log(this.$route.name, this.tags);
    // }
}

</script>
<style lang="less" scoped>
*{
  user-select: none;

}
.tabs{
    padding: 8px 20px;
    cursor: pointer;
    border-bottom: 1px solid #cdcbcb;
    .el-tag{
        margin-right: 10px;
    }
}
</style>