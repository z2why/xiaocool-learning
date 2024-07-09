package com.example.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.course.mapper.CourseMapper;
import com.example.course.service.ICourseService;
import entity.course;
import entity.stuCou;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServiceimpl extends ServiceImpl<CourseMapper, course> implements ICourseService{
    @Resource
    private CourseMapper courseMapper;


    public IPage<stuCou>
    selectstuCoursePage(int pageNum, int pageSize,
                       String stuId, String couId, String collect,
                        String recommend,String have,String couName){
        QueryWrapper<stuCou> wrapper  = new QueryWrapper<>();
        Page<stuCou> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(stuId)) {
            wrapper.eq("sc.stu_id",stuId);
        }

        if (!"".equals(couId)) {
            wrapper.eq("sc.cou_id",couId);
        }
        if (!"".equals(couName)) {
            wrapper.like("c.cou_name",couName);
        }

        if (!"".equals(collect)) {
            wrapper.eq("ci.collect",1);
        }

        if (!"".equals(recommend)) {
            wrapper.eq("ci.recommend",1);
        }
        if (!"".equals(have)) {
            wrapper.eq("ci.have",1);
        }
        return courseMapper.selectstuCoursePage(page, wrapper);
    }

    public IPage<stuCou>
    selectCoursePage(int pageNum, int pageSize,
                        String stuId, String couId, String collect,
                        String recommend,String have){
        QueryWrapper<stuCou> wrapper  = new QueryWrapper<>();
        Page<stuCou> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(stuId)) {
            wrapper.eq("ci.stu_id",stuId);
        }

        if (!"".equals(couId)) {
            wrapper.eq("ci.cou_id",couId);
        }
        if (!"".equals(collect)) {
            wrapper.eq("ci.collect",1);
        }

        if (!"".equals(recommend)) {
            wrapper.eq("ci.recommend",1);
        }
        if (!"".equals(have)) {
            wrapper.eq("ci.have",1);
        }
        wrapper.select().orderByDesc("ci.update_time");
        return courseMapper.selectCoursePage(page, wrapper);
    }

}
