package com.example.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.student.mapper.StudentMapper;
import com.example.student.service.IStudentService;
import entity.stuClass;
import entity.student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentServiceimpl extends ServiceImpl<StudentMapper, student> implements IStudentService {
    @Resource
    private StudentMapper studentMapper;



    public IPage<stuClass>
    selectstuClassPage(int pageNum, int pageSize,
                       String stuId, String classId,String stuName,String deleted){
        QueryWrapper<stuClass> wrapper  = new QueryWrapper<>();
        Page<stuClass> page = new Page<>(pageNum, pageSize,true);

        if (!"".equals(stuId)) {
            wrapper.eq("sc.stu_id",stuId);
        }

        if (!"".equals(classId)) {
            wrapper.eq("sc.class_id",classId);
        }

        if (!"".equals(stuName)) {
            wrapper.eq("s.stu_name",stuName);
        }


        if (!"".equals(deleted)) {
            wrapper.eq("c.deleted",deleted);
        }
        IPage<stuClass> studentPage = studentMapper.selectstuClassPage(page, wrapper);
        return studentPage;
    }


}
