import request from "@/utils/request";

//请求登录数据
export const getData = () => {
    return request.get('/home/getData')
}

/*
使用
1.引入：import {getData} from '../api'
2.getData().then((rea) => {
    
})
*/