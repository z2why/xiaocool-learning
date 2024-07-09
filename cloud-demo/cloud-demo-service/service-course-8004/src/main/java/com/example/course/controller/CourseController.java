package com.example.course.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.course.mapper.ClassCouMapper;
import com.example.course.mapper.CourseMapper;
import com.example.course.mapper.StuClassMapper;
import com.example.course.mapper.StuCouMapper;
import com.example.course.service.impl.ClassCouServiceimpl;
import com.example.course.service.impl.CourseInteractionServiceimpl;
import com.example.course.service.impl.CourseServiceimpl;
import com.example.course.service.impl.StuCouServiceimpl;
import entity.*;
import org.springframework.web.bind.annotation.*;
import vo.Result;

import javax.annotation.Resource;
import java.util.*;
import java.util.logging.Handler;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseServiceimpl CourseService;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ClassCouMapper classCouMapper;

    @Resource
    private StuClassMapper stuClassMapper;

    @Resource
    private CourseInteractionServiceimpl courseInteractionServiceimpl;

    @Resource
    private ClassCouServiceimpl classCouServiceimpl;

    @Resource
    private StuCouMapper stuCouMapper;

    @Resource
    private StuCouServiceimpl stuCouServiceimpl;

    //查找所有课程信息
    @GetMapping
    public Result findAll(){
        return Result.success(CourseService.list());
    }

    //保存与更新课程资料-----假删除
    @PostMapping
    public Result save(@RequestBody course course ){
        return Result.success(CourseService.saveOrUpdate(course));
    }

    //删除数据
    @DeleteMapping("/{couId}")
    public Result delete(@PathVariable Integer couId){
        return Result.success(CourseService.removeById(couId));
    }

    //根据ID查询课程
    @GetMapping("/{couId}")
    public Result findOne(@PathVariable Integer couId){
        return Result.success(CourseService.getById(couId));
    }


    @GetMapping("/findcou")
    public Result fincou(@RequestParam Integer pageNum,
                         @RequestParam Integer pageSize,
                         @RequestParam String stuId
                         ) {
        Page<course> page = new Page<>(pageNum, pageSize);
        QueryWrapper<course> query1 = new QueryWrapper<>();
        query1.eq("cou_type", 1);
        query1.eq("deleted", 0);
        Page<course> course1 = CourseService.page(page, query1);
        List<course> cou1 = course1.getRecords();
        QueryWrapper<course> query2 = new QueryWrapper<>();
        query2.eq("cou_type", 2);
        query2.eq("deleted", 0);
        Page<course> course2 = CourseService.page(page, query2);
        List<course> cou2 = course2.getRecords();
        QueryWrapper<course> query3 = new QueryWrapper<>();
        query3.eq("cou_type", 3);
        query3.eq("deleted", 0);
        Page<course> course3 = CourseService.page(page, query3);
        List<course> cou3 = course3.getRecords();
        QueryWrapper<course> query4 = new QueryWrapper<>();
        query4.eq("cou_type", 4);
        query4.eq("deleted", 0);
        Page<course> course4 = CourseService.page(page, query4);
        List<course> cou4 = course4.getRecords();
        QueryWrapper<course> query5 = new QueryWrapper<>();
        query5.eq("deleted", 0);
        List<course> courseList = courseMapper.selectList(query5);
        QueryWrapper<CouStu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_id", stuId);
        List<CouStu> couStu = stuCouMapper.selectList(queryWrapper);
        // 从list2中筛选出list1中没有的元素
        List<Long> l1 = couStu.stream().map(CouStu::getCouId).collect(Collectors.toList());
        List<Long> l2 = courseList.stream().map(course::getCouId).collect(Collectors.toList());

        List<Long> l3 = l2.stream().filter(
                user2 -> !new ArrayList<>(l1).contains(user2)).collect(Collectors.toList());
        Collections.shuffle(l3);
        System.out.println(l3 + "---------------------------");
        List<course> cou5 = new ArrayList<>();
        if (l3.size() < 3) {
            for (Long index : l3) {
                QueryWrapper<course> query6 = new QueryWrapper<>();
                query6.eq("cou_id", index);
                course course5 = new course();
                course5 = CourseService.getOne(query6);
                cou5.add(course5);
            }
        } else {
            for (int index=0 ; index<3;index++) {
                QueryWrapper<course> query6 = new QueryWrapper<>();
                query6.eq("cou_id", l3.get(index));
                course course5 = new course();
                course5 = CourseService.getOne(query6);
                cou5.add(course5);
            }
        }

        List<Object> course = new ArrayList<>();
        course.add(cou1);
        course.add(cou2);
        course.add(cou3);
        course.add(cou4);
        course.add(cou5);
        return Result.success(course);

    }



    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String couName,
            @RequestParam(defaultValue = "") String couType,
            @RequestParam(defaultValue = "") String deleted,
            @RequestParam(defaultValue = "") String couChileType
    ){
        IPage<course> page = new Page<>(pageNum,pageSize);
        QueryWrapper<course> queryWarpper =new QueryWrapper<>();
        if (!"".equals(couId)){
            queryWarpper.eq("cou_id",couId);
        }
        if (!"".equals(couName)){
            queryWarpper.like("cou_name",couName);
        }
        if (!"".equals(deleted)){
            queryWarpper.eq("deleted",deleted);
        }
        if (!"".equals(couType)){
            queryWarpper.eq("cou_type",couType);
            if (!"".equals(couChileType)){
                queryWarpper.eq("cou_chile_type",couChileType);
            }

        }
        return Result.success(CourseService.page(page,queryWarpper));
    }


    //根据学生信息查课程或根据课程信息查学生
    @GetMapping("/findStuCourse")
    public Result selectstuCoursePage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String collect,
            @RequestParam(defaultValue = "") String recommend,
            @RequestParam(defaultValue = "") String have,
            @RequestParam(defaultValue = "") String couName

    ) {

        return Result.success(CourseService.selectstuCoursePage(pageNum, pageSize, stuId, couId,collect,recommend, have,couName));
    }

    //根据学生信息查课程或根据课程信息查学生
    @GetMapping("/findCoursePage")
    public Result selectCoursePage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String collect,
            @RequestParam(defaultValue = "") String recommend,
            @RequestParam(defaultValue = "") String have

    ) {

        return Result.success(CourseService.selectCoursePage(pageNum, pageSize, stuId, couId,collect,recommend, have));
    }
    @GetMapping("/addCourse")
    public Result addCourse(
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String classId
    ){
        couClass couClass =new couClass();
        couClass.setCouId(Long.valueOf(couId));
        couClass.setClassId(Long.valueOf(classId));
        courseMapper.addCourse(couClass);
        QueryWrapper<ClassStu> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("class_id",classId);
        List<ClassStu> stuClass=stuClassMapper.selectList(queryWrapper);
        List<Long> l1 = stuClass.stream().map(ClassStu::getStuId).collect(Collectors.toList());//获取到stuId
        for (Long i : l1){
            CouStu CouStu=new CouStu();
            CouStu.setStuId(i);
            CouStu.setCouId(Long.valueOf(couId));
            courseMapper.addCou(CouStu);
        }

        return Result.success("添加成功！");

    }

    @PostMapping("/saveBatch")
    public Result saveBatch(@RequestBody List<ClassCou> ClassCous){
        List<Long> l1 = ClassCous.stream().map(ClassCou::getCouId).collect(Collectors.toList());//获取到课程id
        List<Long> l2 = ClassCous.stream().map(ClassCou::getClassId).distinct().collect(Collectors.toList()); //获取classId
        Long classId=l2.get(0);
        for (Long i:l1){
            ClassCou ClassCou =new ClassCou();
            QueryWrapper<ClassCou> wrap=new QueryWrapper<>();
            wrap.eq("class_id",classId);
            wrap.eq("cou_id",i);
            ClassCou=classCouServiceimpl.getOne(wrap);
            if (ClassCou==null){
            QueryWrapper<ClassStu> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("class_id",classId);
            List<ClassStu> ClassStus =stuClassMapper.selectList(queryWrapper); //查这个班的学生
            List<Long> l3 = ClassStus.stream().map(ClassStu::getStuId).collect(Collectors.toList());
            for (Long q:l3){
                CouStu couStu;
                QueryWrapper<CouStu> wrapper=new QueryWrapper<>();
                wrapper.eq("stu_id",q);
                wrapper.eq("cou_id",i);
                couStu= stuCouMapper.selectOne(wrapper);
                if (couStu==null){
                    couStu=new CouStu();
                    couStu.setStuId(q);
                    couStu.setCouId(i);
                    stuCouMapper.insert(couStu);
                }
                QueryWrapper<courseInteraction> rapper = new QueryWrapper<>();
                rapper.eq("stu_id",q);
                rapper.eq("cou_id",i);
                courseInteraction qwq =  courseInteractionServiceimpl.getOne(rapper);
                if (qwq!=null){
                    if (qwq.getHave()!=1){
                        qwq.setHave(1);
                        courseInteractionServiceimpl.saveOrUpdate(qwq);
                    }
                }
                else {
                    qwq=new courseInteraction();
                    qwq.setStuId(Math.toIntExact(q));
                    qwq.setCouId(Math.toIntExact(i));
                    qwq.setHave(1);
                    courseInteractionServiceimpl.saveOrUpdate(qwq);
                }
            }
        }
            else {
                ClassCou=new ClassCou();
                ClassCou.setCouId(classId);
                ClassCou.setCouId(i);
                ClassCous.remove(ClassCou);
            }
        }

        return Result.success(classCouServiceimpl.saveBatch(ClassCous));
    }




    //某班删除某课
    @DeleteMapping("/leaveCourse/{id}")
    public Result leaveCourse(@PathVariable Long id)
    {
        ClassCou couClass = classCouMapper.selectById(id);
        Long classid = couClass.getClassId();
        Long couid=couClass.getCouId();
        classCouMapper.deleteById(id);
        QueryWrapper<ClassStu> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("class_id",classid);
        List<Long> l1 = stuClassMapper.selectList(queryWrapper).stream().map(ClassStu::getStuId).collect(Collectors.toList());//获取班级所有人名单
        for (Long i:l1){
            QueryWrapper<CouStu> Wrapper =new QueryWrapper<>();
            Wrapper.eq("stu_id",i);
            Wrapper.eq("cou_id",couid);
            Long scId=stuCouMapper.selectOne(Wrapper).getId();
            stuCouMapper.deleteById(scId);
            QueryWrapper<courseInteraction> rapper = new QueryWrapper<>();
            rapper.eq("stu_id",i);
            rapper.eq("cou_id",couid);
            courseInteraction qwq =  courseInteractionServiceimpl.getOne(rapper);
            if (qwq!=null){
                if (qwq.getHave()!=0){
                    qwq.setHave(0);
                    courseInteractionServiceimpl.saveOrUpdate(qwq);
                }
            }
            else {
                qwq=new courseInteraction();
                qwq.setStuId(Math.toIntExact(i));
                qwq.setCouId(Math.toIntExact(couid));
                qwq.setHave(0);
                courseInteractionServiceimpl.saveOrUpdate(qwq);
            }

        }

        return Result.success("移除成功！");
    }



}
