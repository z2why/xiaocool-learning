package com.example.order.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper extends BaseMapper<order> {
    IPage<orderDetailDTO> selectOrderDetail(Page<orderDetailDTO> page, @Param(Constants.WRAPPER) Wrapper<orderDetailDTO> wrapper);

    @Insert("INSERT INTO stu_cou (stu_id, cou_id) SELECT #{stuId},#{couId} from DUAL  where not exists(select * from stu_cou where stu_id=#{stuId} and cou_id = #{couId})")
    int addCourse(CouStu CouStu);
}
