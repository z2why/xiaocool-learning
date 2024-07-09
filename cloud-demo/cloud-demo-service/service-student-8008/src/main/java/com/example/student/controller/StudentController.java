package com.example.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.student.mapper.*;
import com.example.student.service.impl.CourseInteractionServiceimpl;
import com.example.student.service.impl.StuClassServiceimpl;
import com.example.student.service.impl.StudentServiceimpl;
import entity.*;
import org.springframework.web.bind.annotation.*;
import vo.Result;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentServiceimpl studentService;

    @Resource
    private  StuClassServiceimpl stuClassServiceimpl;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private CourseInteractionServiceimpl courseInteractionServiceimpl;

    @Resource
    private StuCouMapper stuCouMapper;

    @Resource
    private ClassCouMapper classCouMapper;

    @Resource
    private StuClassMapper stuClassMapper;

    @Resource
    private ClassMapper classMapper;

    //查找所有学生信息
    @GetMapping
    public Result findAll(){
        return Result.success(studentService.list());
    }

    //保存与更新学生资料
    @PostMapping
    public Result save(@RequestBody student student ){
        return Result.success(studentService.saveOrUpdate(student));
    }

    //删除数据
    @DeleteMapping("/{stuId}")
    public Result delete(@PathVariable Integer stuId){
        return Result.success(studentService.removeById(stuId));
    }

    @GetMapping("/findStudent")
    public Result findOne(@RequestParam Integer stuId){
        return Result.success(studentService.getById(stuId));
    }


    //根据班级查班级或者根据学生查班级
    @GetMapping("/findStuClass")
    public Result selectstuClassPage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String classId,
            @RequestParam(defaultValue = "") String stuName,
            @RequestParam(defaultValue = "") String deleted

    ) {
//        if (studentService.selectstuClassPage(pageNum, pageSize, stuId, classId).isEmpty()){
//            return Result.error(Constants.CODE_600, "不存在！");
//        }
        return Result.success(studentService.selectstuClassPage(pageNum, pageSize, stuId, classId,stuName,deleted));
    }

    @GetMapping("/joinClass")
    public Result joinClass(
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String classId
    ){
        stuClass stuClass =new stuClass();
        stuClass.setStuId(Long.valueOf(stuId));
        stuClass.setClassId(Long.valueOf(classId));
        QueryWrapper<ClassCou> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("class_id",classId);
        List<ClassCou> ClassCous =classCouMapper.selectList(queryWrapper);
        List<Long> l1 = ClassCous.stream().map(ClassCou::getCouId).collect(Collectors.toList());//获取到每个班的每门课的id
        for (Long i:l1){
            CouStu couStu;
            QueryWrapper<CouStu> wrapper=new QueryWrapper<>();
            wrapper.eq("stu_id",stuId);
            wrapper.eq("cou_id",i);
            couStu= stuCouMapper.selectOne(wrapper);
            if (couStu==null){
                couStu=new CouStu();
                couStu.setStuId(Long.valueOf(stuId));
                couStu.setCouId(i);
                stuCouMapper.insert(couStu);
            }
        }
        studentMapper.joinClass(stuClass);
        classMapper.selectById(classId).setClassStudentNum(classMapper.selectById(classId).getClassStudentNum()+1);
        return Result.success("添加成功");
    }


    @PostMapping("/saveBatch")
    public Result saveBatch(@RequestBody List<ClassStu> stuClasses){
        List<Long> l1 = stuClasses.stream().map(ClassStu::getStuId).collect(Collectors.toList());//获取到stuId
        List<Long> l2 = stuClasses.stream().map(ClassStu::getClassId).distinct().collect(Collectors.toList()); //获取classId
        Long classId=l2.get(0);
        for (Long i:l1){
            ClassStu stuClass =new ClassStu();
            QueryWrapper<ClassStu> wrap=new QueryWrapper<>();
            wrap.eq("stu_id",i);
            wrap.eq("class_id",classId);
            stuClass=stuClassMapper.selectOne(wrap);
            if (stuClass==null){
            QueryWrapper<ClassCou> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("class_id",classId);
            List<ClassCou> ClassCous =classCouMapper.selectList(queryWrapper);
            List<Long> l3 = ClassCous.stream().map(ClassCou::getCouId).collect(Collectors.toList());//获取到每个班的每门课的id
            for (Long q:l3){
                CouStu couStu;
                QueryWrapper<CouStu> wrapper=new QueryWrapper<>();
                wrapper.eq("stu_id",i);
                wrapper.eq("cou_id",q);
                couStu= stuCouMapper.selectOne(wrapper);
                if (couStu==null){
                    couStu=new CouStu();
                    couStu.setStuId(i);
                    couStu.setCouId(q);
                    stuCouMapper.insert(couStu);
                }
                QueryWrapper<courseInteraction> rapper = new QueryWrapper<>();
                rapper.eq("stu_id",i);
                rapper.eq("cou_id",q);
                courseInteraction qwq =  courseInteractionServiceimpl.getOne(rapper);
                if (qwq!=null){
                    if (qwq.getHave()!=1){
                        qwq.setHave(1);
                        courseInteractionServiceimpl.saveOrUpdate(qwq);
                    }
                }
                else {
                    qwq=new courseInteraction();
                    qwq.setStuId(Math.toIntExact(i));
                    qwq.setCouId(Math.toIntExact(q));
                    qwq.setHave(1);
                    courseInteractionServiceimpl.saveOrUpdate(qwq);
                }
            }
           }
            else {
                stuClass=new ClassStu();
                stuClass.setClassId(classId);
                stuClass.setStuId(i);
                stuClasses.remove(stuClass);
            }
        }
        classes classes=classMapper.selectById(classId);
        classes.setClassStudentNum(classMapper.selectById(classId).getClassStudentNum()+l1.size());
        classMapper.updateById(classes);
        return Result.success(stuClassServiceimpl.saveBatch(stuClasses));
    }




    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String classId,
            @RequestParam(defaultValue = "") String stuId,
            @RequestParam(defaultValue = "") String stuName
            ){
        IPage<student> page = new Page<>(pageNum,pageSize);
        QueryWrapper<student> queryWarpper =new QueryWrapper<>();
        if (!"".equals(stuId)){
            queryWarpper.eq("stu_id",stuId);
        }
        if (!"".equals(stuName)){
            queryWarpper.like("stu_name",stuName);

        }
        if (!"".equals(classId)){
            QueryWrapper<ClassStu> wrapper=new QueryWrapper<>();
            wrapper.eq("class_id",classId);
            List<ClassStu>  ClassStus= stuClassServiceimpl.list(wrapper);
            List<Long> l1 = ClassStus.stream().map(ClassStu::getStuId).collect(Collectors.toList());
            for (Long i:l1){
                queryWarpper.ne("stu_id",i);
            }

        }

        return Result.success(studentService.page(page,queryWarpper));
    }


    //某同学离开某班
    @DeleteMapping("/leaveclass/{id}")
    public Result leaveClass(@PathVariable Long id)
    {
        ClassStu classStu = stuClassMapper.selectById(id);
        Long classid = classStu.getClassId();
        Long stuid=classStu.getStuId();
        QueryWrapper<ClassCou> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("class_id",classid);      //查看这个学生所在班级选的课
        List<Long> l1 = classCouMapper.selectList(queryWrapper).stream().map(ClassCou::getCouId).collect(Collectors.toList());//获取班级所有课程
        for (Long i:l1){
            QueryWrapper<CouStu> Wrapper =new QueryWrapper<>();
            Wrapper.eq("stu_id",stuid);
            Wrapper.eq("cou_id",i);
            Long scId=stuCouMapper.selectOne(Wrapper).getId();
            stuCouMapper.deleteById(scId);
            QueryWrapper<courseInteraction> rapper = new QueryWrapper<>();
            rapper.eq("stu_id",stuid);
            rapper.eq("cou_id",i);
            courseInteraction qwq =  courseInteractionServiceimpl.getOne(rapper);
            if (qwq!=null){
                if (qwq.getHave()!=0){
                    qwq.setHave(0);
                    courseInteractionServiceimpl.saveOrUpdate(qwq);
                }
            }
            else {
                qwq=new courseInteraction();
                qwq.setStuId(Math.toIntExact(stuid));
                qwq.setCouId(Math.toIntExact(i));
                qwq.setHave(0);
                courseInteractionServiceimpl.saveOrUpdate(qwq);
            }

        }
        int num= classMapper.selectById(classid).getClassStudentNum();
        classes classes=classMapper.selectById(classid);
        classes.setClassStudentNum(num-1);
        classMapper.updateById(classes);
        return Result.success(stuClassMapper.deleteById(classStu));
    }


}
