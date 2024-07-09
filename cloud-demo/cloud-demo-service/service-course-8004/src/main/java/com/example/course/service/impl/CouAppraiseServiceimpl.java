package com.example.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.course.mapper.CouAppraiseMapper;
import com.example.course.mapper.CourseMapper;
import com.example.course.service.ICouAppraiseService;
import entity.StuCouAppDTO;
import entity.courseAppraise;
import entity.stuCou;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CouAppraiseServiceimpl extends ServiceImpl<CouAppraiseMapper, courseAppraise> implements ICouAppraiseService {
    @Resource
    private CourseMapper courseMapper;

    public IPage<StuCouAppDTO>
    findStuCouApp(int pageNum, int pageSize,
                        String stuId, String couId){
        QueryWrapper<StuCouAppDTO> wrapper  = new QueryWrapper<>();
        Page<StuCouAppDTO> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(stuId)) {
            wrapper.eq("ca.stu_id",stuId);
        }

        if (!"".equals(couId)) {
            wrapper.eq("ca.cou_id",couId);
        }
        return courseMapper.findStuCouApp(page, wrapper);
    }

}
