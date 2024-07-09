<template>
    <!--补签审核-->
    <div CLASS="MeetingMember">
        <el-select v-model="orderFormP" clearable  placeholder="请选择">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
        <el-button size="mini" style="margin-left: 10px" type="primary" @click="load">搜索</el-button> 
        <!-- 刷新按钮 -->
        <el-button size="mini" style="margin-left: 10px" type="primary" @click="reast"><i class="el-icon-refresh"></i></el-button> 
        <el-table :data="getData" :header-cell-style="{ backgroundColor: 'rgb(244, 244, 245)' }"
            style="margin-bottom: 10px" stripe>

            <el-table-column prop="orderId" label="订单id" width="70" align="center"></el-table-column>
            <el-table-column prop="username" label="退货账号" width="100" align="center"></el-table-column>
            <el-table-column prop="couName" label="课程名称" width="150" align="center"></el-table-column>
            <el-table-column prop="couPrice" label="课程价格" width="80" align="center"></el-table-column>
            <el-table-column prop="couIntroduction" label="课程介绍" width="180" align="center"></el-table-column>
            <el-table-column prop="orderFormDate" label="订单完成时间" width="180" align="center"></el-table-column>
            <el-table-column prop="orderFormP" key="orderFormP" label="订单完成情况" width="120" align="center">
                <template slot-scope="scope" >
                    <template v-if="scope.row.orderFormP === 0">正常</template>
                    <template v-else-if="scope.row.orderFormP === 1">申请退款</template>
                    <template v-else-if="scope.row.orderFormP === 2">申请退款通过</template>
                    <template v-else-if="scope.row.orderFormP === 3">申请退款不通过</template>
                </template>
            </el-table-column>
            <el-table-column label="操作" prop="orderFormP"  align="center">
                <template slot-scope="scope" >
                    <template v-if="scope.row.orderFormP !== 0">
                        <el-button icon="el-icon-check" type="success" v-model="scope.row.participateStatus"
                            active-color="#13ce66" inactive-color="#ccc" @click="check(scope.row.orderFormP,scope.row.orderId,scope.row.couId)" plain>通过
                        </el-button>
                        <el-button icon="el-icon-close" type="warning" v-model="scope.row.participateStatus"
                            active-color="#13ce66" inactive-color="#ccc"
                            @click="checkout(scope.row.orderId)"
                            plain>不通过
                        </el-button>
                    </template>                   
                </template>
            </el-table-column>

        </el-table>


 

        <!-- 分页 -->
        <div class="block">
            <el-pagination 
                @size-change="handleSizeChange"
                 @current-change="handleCurrentChange"
                :current-page.="pageNum" :page-sizes="[8, 10, 12]" 
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" 
                :total="total">
            </el-pagination>
        </div>

    </div>

</template>
    
<script>
export default {
    name: "tabAudit",
    data() {
        return {
            options: [
                {
                    value: 0,
                    label: '正常'
                },
                {
                    value: 1,
                    label: '申请退款'
                }, 
                {
                    value: 2,
                    label: '申请退款通过'
                },
                {
                    value: 3,
                    label: '申请退款不通过'
                },
            ],
            orderFormP: '',
            getData: [],
            pageNum: 1,
            pageSize: 8,
            total: 0,
        }
    },

    created() {
        //请求分页查询数据
        this.load()
    },
    methods: {
        load() {
            this.request.get("/service-order/order/findorderdetail", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    orderFormP: this.orderFormP,
                }

            }).then(res => {
                console.log(res)
                this.getData = res.data.records
                this.total = res.data.total
            }
            )
        },
        //每页显示多少条数据
        handleSizeChange(pageSize) {
            // console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        //页码
        //刷新
        reast(){
            this.orderFormP = '',
            this.load()
        },
        handleCurrentChange(pageNum) {
            // console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
        //审核通过
        check(orderFormP,orderId,couId) {
            //console.log(orderFormP,orderId,couId)
            
            this.request.post("/service-order/order",{
                id: orderId,
                orderFormP: 2
            }).then(res => {
                //console.log(res)
            })
           
            this.request.post("/service-order/order/refund",[
                {
                    couId:couId,
                    orderId: orderId,
                }
            ]).then(res => {
                if(res.code === '200'){
                    this.$message.success("审核通过！")
                    this.load()
                }else{
                    this.$message.error("审核失败")
                }
               
                
            })
        },
        //审核不通过
        checkout(orderId) {
            this.request.post("/service-order/order",{
                id: orderId,
                orderFormP: 3
            }).then(res => {
                console.log(res)
                if(res.code === '200'){
                    this.$message.success("审核不通过！")
                    this.load()
                }else{
                    this.$message.error("审核失败")
                }
            })
        },


    }
}
</script>
    
<style lang="less" scoped>
/deep/.el-table {
    margin-bottom: 20px;
    margin-top: 20px;
       .cell {
           display: -webkit-box;
           -webkit-box-orient: vertical;
           -webkit-line-clamp: 2;
       }
   }

   .el-tag--small {
       height: 33px;
       padding: 4px 13px;
       line-height: 22px;
       font-size: initial;
   }
</style>