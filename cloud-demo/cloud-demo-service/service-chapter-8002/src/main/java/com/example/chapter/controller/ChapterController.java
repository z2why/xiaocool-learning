package com.example.chapter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.chapter.mapper.CourseMapper;
import com.example.chapter.service.impl.ChapterServiceimpl;
import entity.ClassStu;
import entity.chapter;
import entity.course;
import org.springframework.web.bind.annotation.*;
import vo.Result;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Resource
    private ChapterServiceimpl chapterService;

    @Resource
    private CourseMapper courseMapper;

    //查找所有章节信息
    @GetMapping
    public Result findAll(){
        return Result.success(chapterService.list());
    }


    //删除章节
    @DeleteMapping("/{chaId}")
    public Result delete(@PathVariable Integer chaId){
        chapter chapter1=chapterService.getById(chaId);
        int index= chapter1.getChaIndex();
        QueryWrapper<chapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",chapter1.getCourseId());
        queryWrapper.gt("cha_index",index);
        List<chapter> chapters1= chapterService.list(queryWrapper);
        if (chapters1!=null){
            List<Long> l2 = chapters1.stream().map(chapter::getChaId).collect(Collectors.toList());
            for (Long i:l2){
                chapter chapter2=chapterService.getById(i);
                chapter2.setChaIndex(index);
                chapterService.saveOrUpdate(chapter2);
                index++;
            }
        }
        course course =courseMapper.selectById(chapter1.getCourseId());
        course.setCouChaNum(course.getCouChaNum()-1);
        courseMapper.updateById(course);
        return Result.success(chapterService.removeById(chaId));
    }

    //保存与更新章节资料
    @PostMapping
    public Result save(@RequestBody chapter chapter1 ){
        if (chapter1.getChaId()!=null){
            return Result.success(chapterService.saveOrUpdate(chapter1));
        }
        int index= chapter1.getChaIndex();
        QueryWrapper<chapter> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("course_id",chapter1.getCourseId());
        queryWrapper1.ge("cha_index",index);
        List<chapter> chapters= chapterService.list(queryWrapper1);

        if (chapters!=null){
            List<Long> l2 = chapters.stream().map(chapter::getChaId).collect(Collectors.toList());
            for (Long i:l2){
                chapter chapter2=chapterService.getById(i);
                chapter2.setChaIndex(index+1);
                chapterService.saveOrUpdate(chapter2);
                index++;
            }
        }
        course course =courseMapper.selectById(chapter1.getCourseId());
        course.setCouChaNum(course.getCouChaNum()+1);
        courseMapper.updateById(course);
        return Result.success(chapterService.saveOrUpdate(chapter1));
    }



    @GetMapping("/{chaId}")
    public Result findOne(@PathVariable Integer chaId){

        return Result.success(chapterService.getById(chaId));
    }

    //分页查询
    @GetMapping("/allchapter")
    public Result findPage(

            @RequestParam(defaultValue = "") String chaId,
            @RequestParam(defaultValue = "") String courseId,
            @RequestParam(defaultValue = "") String chaTitle

            ){
        QueryWrapper<chapter> queryWarpper =new QueryWrapper<>();
        if (!"".equals(chaId)){
            queryWarpper.eq("cha_id",chaId);
        }
        if (!"".equals(courseId)){
            queryWarpper.eq("course_id",courseId);
        }

        if (!"".equals(chaTitle)){
            queryWarpper.eq("cha_title",chaTitle);
        }
        queryWarpper.orderByAsc("cha_index");
        return Result.success(chapterService.list(queryWarpper));
    }



    /**
     * 拖拽后的排序
     */
    @RequestMapping("/drag")
    public Result drag(@RequestParam Map<String, Object> params) {
        chapterService.reorder(params);
        return Result.success("成功！");
    }
}
