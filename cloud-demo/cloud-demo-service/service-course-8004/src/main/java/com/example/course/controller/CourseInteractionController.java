package com.example.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.course.mapper.CourseInteractionMapper;
import com.example.course.service.impl.CourseInteractionServiceimpl;
import com.example.course.service.impl.CourseServiceimpl;
import entity.course;
import entity.courseInteraction;
import org.springframework.web.bind.annotation.*;
import vo.Constants;
import vo.Result;

import javax.annotation.Resource;


@RestController
@RequestMapping("/courseInteraction")
public class CourseInteractionController {
    @Resource
    private CourseInteractionServiceimpl courseInteractionService;

    @Resource
    private CourseServiceimpl courseServiceimpl;


    //查找所有订单
    @GetMapping
    public Result findAll(){
        return Result.success(courseInteractionService.list());
    }

    @Resource
    private CourseInteractionMapper courseInteractionMapper;


    //保存与更新字典资料-----假删除
    @PostMapping
    public Result save(@RequestBody courseInteraction courseInteraction){
        return Result.success(courseInteractionService.saveOrUpdate(courseInteraction));
    }

    //删除数据
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(courseInteractionService.removeById(id));
    }

    //根据ID查询字典
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(courseInteractionService.getById(id));
    }


    //点赞
    @GetMapping("/recommend")
    public Result recommend(@RequestParam String stuId,@RequestParam String couId)
    {   QueryWrapper<course> wrapper=new QueryWrapper<>();
        wrapper.eq("cou_id",couId);
        course course=courseServiceimpl.getOne(wrapper);
        if (course!=null){
        QueryWrapper<courseInteraction> queryWarpper =new QueryWrapper<>();
        queryWarpper.eq("stu_id",stuId);
        queryWarpper.eq("cou_id",couId);
        courseInteraction courseInteraction;
        courseInteraction=courseInteractionService.getOne(queryWarpper);

        if (courseInteraction!=null){
            if (courseInteraction.getRecommend()==1){
                courseInteraction.setRecommend(0);
                int cr=Integer.parseInt(course.getCouRecoNum())-1;
                course.setCouRecoNum(String.valueOf(cr));
                courseServiceimpl.saveOrUpdate(course);
            }
            else {
                courseInteraction.setRecommend(1);
                int cr=Integer.parseInt(course.getCouRecoNum())+1;
                course.setCouRecoNum(String.valueOf(cr));
                courseServiceimpl.saveOrUpdate(course);
            }
            courseInteractionService.saveOrUpdate(courseInteraction);
            return Result.success(courseInteraction);
        }
        courseInteraction=new courseInteraction();
        courseInteraction.setCouId(Integer.parseInt(couId));
        courseInteraction.setStuId(Integer.parseInt(stuId));
            if (courseInteraction.getRecommend()==1){
                courseInteraction.setRecommend(0);
                int cr=Integer.parseInt(course.getCouRecoNum())-1;
                course.setCouRecoNum(String.valueOf(cr));
                courseServiceimpl.saveOrUpdate(course);
            }
            else {
                courseInteraction.setRecommend(1);
                int cr=Integer.parseInt(course.getCouRecoNum())+1;
                course.setCouRecoNum(String.valueOf(cr));
                courseServiceimpl.saveOrUpdate(course);
            }courseInteractionService.saveOrUpdate(courseInteraction);
            return Result.success(courseInteraction);}
        else {
            return Result.error(Constants.CODE_600,"课程不存在！");
        }
    }


    //收藏
    @GetMapping("/collect")
    public Result collect(@RequestParam String stuId,@RequestParam String couId)
    {
        QueryWrapper<course> wrapper=new QueryWrapper<>();
        wrapper.eq("cou_id",couId);
        course course=courseServiceimpl.getOne(wrapper);
        if (course!=null){
        QueryWrapper<courseInteraction> queryWarpper =new QueryWrapper<>();
        queryWarpper.eq("stu_id",stuId);
        queryWarpper.eq("cou_id",couId);
        courseInteraction courseInteraction;
        courseInteraction=courseInteractionService.getOne(queryWarpper);
        if (courseInteraction!=null){
            if (courseInteraction.getCollect()==1){
                courseInteraction.setCollect(0);
                int cr=Integer.parseInt(course.getCouCollNum())-1;
                course.setCouCollNum(String.valueOf(cr));
                courseServiceimpl.saveOrUpdate(course);
            }
            else {
                courseInteraction.setCollect(1);
                int cr=Integer.parseInt(course.getCouCollNum())+1;
                course.setCouCollNum(String.valueOf(cr));
                courseServiceimpl.saveOrUpdate(course);
            }
            courseInteractionService.saveOrUpdate(courseInteraction);
            return Result.success(courseInteraction);
        }
        courseInteraction=new courseInteraction();
        courseInteraction.setCouId(Integer.parseInt(couId));
        courseInteraction.setStuId(Integer.parseInt(stuId));
            if (courseInteraction.getCollect()==1){
                courseInteraction.setCollect(0);
                int cr=Integer.parseInt(course.getCouCollNum())-1;
                course.setCouCollNum(String.valueOf(cr));
                courseServiceimpl.saveOrUpdate(course);
            }
            else {
                courseInteraction.setCollect(1);
                int cr=Integer.parseInt(course.getCouCollNum())+1;
                course.setCouCollNum(String.valueOf(cr));
                courseServiceimpl.saveOrUpdate(course);
            }
            courseInteractionService.saveOrUpdate(courseInteraction);
        return Result.success(courseInteraction);}
        else {
            return Result.error(Constants.CODE_600,"课程不存在！");

        }
    }


    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String collect,
            @RequestParam(defaultValue = "") String have,
            @RequestParam(defaultValue = "") String recommend

            ){
        IPage<courseInteraction> page = new Page<>(pageNum,pageSize);
        QueryWrapper<courseInteraction> queryWarpper =new QueryWrapper<>();
        if (!"".equals(id)){
            queryWarpper.eq("id",id);
        }

        if (!"".equals(couId)){
            queryWarpper.eq("cou_id",couId);
        }

        if (!"".equals(stuId)){
            queryWarpper.eq("stu_id",stuId);
        }

        if (!"".equals(collect)){
            queryWarpper.eq("collect",collect);
        }

        if (!"".equals(have)){
            queryWarpper.eq("have",have);
        }

        if (!"".equals(recommend)){
            queryWarpper.eq("recommend",recommend);
        }


        return Result.success(courseInteractionService.page(page,queryWarpper));
    }

}
