package com.example.classes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.classes.mapper.ClassCouMapper;
import com.example.classes.mapper.ClassMapper;
import com.example.classes.service.IClassService;
import entity.ClassCou;
import entity.classes;
import entity.couClass;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceimpl extends ServiceImpl<ClassMapper, classes> implements IClassService {
    @Resource
    private ClassMapper classMapper;
    @Resource
    private ClassCouMapper classCouMapper;


    public IPage<couClass>
    selectclassCoursePage(int pageNum, int pageSize,
                          String classId, String couId,String couName){
        QueryWrapper<couClass> wrapper  = new QueryWrapper<>();
        Page<couClass> page = new Page<>(pageNum, pageSize, true);

        if (!"".equals(classId)) {
            wrapper.eq("cc.class_id",classId);
        }

        if (!"".equals(couId)) {
            wrapper.eq("cc.cou_id",couId);
        }
        if (!"".equals(couName)) {
            wrapper.like("c.cou_name",couName);
        }

        return classMapper.selectclassCoursePage(page, wrapper);
    }


    //选课
    public IPage<couClass>
    selectCoursePage(int pageNum, int pageSize,
                        String classId, String couId,String couName,
                        String teaName, String teaId,String deleted){
        QueryWrapper<couClass> wrapper  = new QueryWrapper<>();
        Page<couClass> page = new Page<>(pageNum, pageSize, true);
        if (!"".equals(classId)) {
            QueryWrapper<ClassCou> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("class_id",classId);
            List<ClassCou> ClassCous=classCouMapper.selectList(queryWrapper);
            if (ClassCous!=null){
                List<Long> l1 = ClassCous.stream().map(ClassCou::getCouId).collect(Collectors.toList());//获取到课程id
                for (Long i:l1){
                    wrapper.ne("c.cou_id",i);

                }
            }
        }
        if (!"".equals(couId)) {
            wrapper.eq("c.cou_id",couId);
        }
        if (!"".equals(teaName)) {
            wrapper.like("u.truename",teaName);
        }

        if (!"".equals(couName)) {
            wrapper.like("c.cou_name",couName);
        }

        if (!"".equals(teaId)) {
            wrapper.like("c.tea_id",teaId);
        }
        if (!"".equals(deleted)) {
            wrapper.eq("c.deleted",deleted);
        }

        return classMapper.selectCoursePage(page, wrapper);
    }

}
