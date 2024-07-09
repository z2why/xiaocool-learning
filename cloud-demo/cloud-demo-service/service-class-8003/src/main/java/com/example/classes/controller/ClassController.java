package com.example.classes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.classes.mapper.ClassMapper;
import com.example.classes.mapper.StuClassMapper;
import com.example.classes.service.impl.ClassServiceimpl;
import entity.CouStu;
import entity.classes;
import entity.orderDetailDTO;
import entity.stuClass;
import org.springframework.web.bind.annotation.*;
import vo.Result;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {
    @Resource
    private ClassServiceimpl classService;

    @Resource
    private ClassMapper classMapper;

    @Resource
    private StuClassMapper stuClassMapper;


    //查找所有班级
    @GetMapping
    public Result findAll(){
        return Result.success(classService.list());
    }

    //保存与更新班级资料
    @PostMapping
    public Result save(@RequestBody classes classes ){
        return Result.success(classService.saveOrUpdate(classes));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String className,
            @RequestParam(defaultValue = "") String classId,
            @RequestParam(defaultValue = "") String teaId,
            @RequestParam(defaultValue = "") String teaName,
            @RequestParam(defaultValue = "") String deleted

    ){
        IPage<classes> page = new Page<>(pageNum,pageSize);
        QueryWrapper<classes> queryWarpper =new QueryWrapper<>();
        if (!"".equals(className)){
            queryWarpper.like("class_name",className);
        }
        if (!"".equals(classId)){
            queryWarpper.eq("class_id",classId);
        }
        if (!"".equals(teaId)){
            queryWarpper.eq("tea_id",teaId);
        }
        if (!"".equals(teaName)){
            queryWarpper.like("tea_name",teaName);
        }
        if (!"".equals(deleted)){
            queryWarpper.eq("deleted",deleted);
        }
        return Result.success(classService.page(page,queryWarpper));
    }



    //根据班级信息查课程或根据课程信息查班级
    @GetMapping("/findClassCourse")
    public Result selectclassCoursePage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String classId,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String couName
    ) {
//        if (classService.selectclassCoursePage(pageNum, pageSize, classId, couId,couName).){
//            return Result.error(Constants.CODE_600, "不存在！");
//        }
        return Result.success(classService.selectclassCoursePage(pageNum, pageSize, classId, couId,couName));
    }

    //选课
    @GetMapping("/findCourse")
    public Result selectCoursePage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String classId,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String couName,
            @RequestParam(defaultValue = "") String teaName,
            @RequestParam(defaultValue = "") String teaId,
            @RequestParam(defaultValue = "") String deleted
    ) {
//        if (classService.selectCoursePage(pageNum, pageSize, classId, couId,couName,teaName,teaId,deleted).isEmpty()){
//            return Result.error(Constants.CODE_600, "不存在！");
//        }
        return Result.success(classService.selectCoursePage(pageNum, pageSize, classId, couId,couName,teaName,teaId,deleted));
    }




//    @DeleteMapping("/leave/{id}")
//    public Result leaveClass(@PathVariable Long id)
//    {
//        stuClassMapper.deleteById(id);
//        return Result.success("移除成功！");
//    }




}
