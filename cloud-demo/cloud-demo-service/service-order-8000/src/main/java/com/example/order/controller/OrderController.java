package com.example.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.order.mapper.OrderMapper;
import com.example.order.service.impl.*;
import entity.*;
import org.springframework.web.bind.annotation.*;
import vo.Constants;
import vo.Result;
import java.time.LocalDateTime;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderServiceimpl orderService;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private StuCouServiceimpl stuCouServiceimpl;

    @Resource
    private StudentServiceimpl studentServiceimpl;

    @Resource
    private CourseServiceimpl courseServiceimpl;

    @Resource
    private CourseInteractionServiceimpl courseInteractionServiceimpl;

    @Resource
    private StuCouCarServiceimpl stuCouCarServiceimpl;


    @Resource
    private OrderMapper orderMapper;


    //查找所有订单
    @GetMapping
    public Result findAll(){
        return Result.success(orderService.list());
    }






    //保存与更新订单资料-----假删除
    @PostMapping
    public Result save(@RequestBody order order){
        return Result.success(orderService.saveOrUpdate(order));
    }

    //删除数据
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(orderService.removeById(id));
    }

    //根据ID查询订单
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(orderService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String orderFormDate
    ){
        IPage<order> page = new Page<>(pageNum,pageSize);
        QueryWrapper<order> queryWarpper =new QueryWrapper<>();
        if (!"".equals(id)){
            queryWarpper.like("id",id);
        }
        if (!"".equals(orderFormDate)){
            queryWarpper.like("orderForm_date",orderFormDate);
        }

        return Result.success(orderService.page(page,queryWarpper));
    }



    //退货
    @PostMapping("/refund")
    public Result refund(@RequestBody List<orderDetail> courses) {
        //先用courses表里的orderId查询order订单信息里的stuId
        orderDetail ods = new orderDetail();
        List<Integer> l1 = courses.stream().map(orderDetail::getOrderId).distinct().collect(Collectors.toList());
        List<Integer> l2 = courses.stream().map(orderDetail::getCouId).distinct().collect(Collectors.toList()); //l2即从前端获取的couId
        ods.setOrderId(l1.get(0)); //获取了订单id，接下来要去查订单信息获取stuId
        QueryWrapper<order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", ods.getOrderId());//获取了这个订单号的所有信息
        order order = orderService.getOne(queryWrapper);
        String sId = order.getStuId();
        QueryWrapper<CouStu> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id", sId);
        course course;
        int total = 0;
        for (int i : l2) {
            wrapper.eq("cou_id", i);//判断是否在stu_cou表里
            System.out.print(wrapper);
            CouStu one = stuCouServiceimpl.getOne(wrapper);
            if (one != null) {//说明在里面
                course = courseServiceimpl.getById(i);
                int t = course.getCouPrice();
                stuCouServiceimpl.removeById(one.getId());
                total += t;
                wrapper.clear();
                wrapper.eq("stu_id", sId);
                //查看是否在中间表里
                courseInteraction courseInteraction=new courseInteraction();
                QueryWrapper<courseInteraction> rapper = new QueryWrapper<>();
                rapper.eq("stu_id",sId);
                rapper.eq("cou_id",i);
                courseInteraction qwq =  courseInteractionServiceimpl.getOne(rapper);
                if (qwq!=null){ //有这张表
                    if (qwq.getHave()!=0){
                        qwq.setHave(0);
                        courseInteractionServiceimpl.saveOrUpdate(qwq);
                    }
                }


            }
        }
            //获取student表里stu_score
            QueryWrapper<student> QWrapper = new QueryWrapper<>();
            QWrapper.eq("stu_id", sId);
            student student;
            student = studentServiceimpl.getOne(QWrapper);    //获取学生信息。包括积分
            student.setStuScore(student.getStuScore() + total); //学生积分加上课程积分
            return Result.success(studentServiceimpl.saveOrUpdate(student));

    }








    //购买
    @PostMapping("/buy")
    public Result saveBatch(@RequestBody List<orderDetailDTO> courses) {
        LocalDateTime localDateTime = LocalDateTime.now();
        //时间转字符串
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime dateTime = LocalDateTime.parse(format, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Long> l1 = courses.stream().map(orderDetailDTO::getStuId).collect(Collectors.toList());//获取到stuId
        List<Integer> l2 = courses.stream().map(orderDetailDTO::getCouId).distinct().collect(Collectors.toList()); //l2即从前端获取的couId
        List<Long> l3 = courses.stream().map(orderDetailDTO::getUId).collect(Collectors.toList()); //l2即从前端获取的couId
        QueryWrapper<CouStu> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id", l1.get(0));
        CouStu one=new CouStu();
        for (int i : l2) {
            wrapper.eq("cou_id", i);//判断是否在stu_cou表里
            one = stuCouServiceimpl.getOne(wrapper);
            if (one != null) {
                return Result.error(Constants.CODE_600, "您已购买过,无需重复购买");
            }
            wrapper.clear();
            wrapper.eq("stu_id", l1.get(0));
        }
        order order = new order();
        order.setUId(l3.get(0));
        order.setStuId(String.valueOf(l1.get(0)));
        order.setOrderFormDate(dateTime);
        orderService.saveOrUpdate(order);
        //获取student表里stu_score
        QueryWrapper<student> QWrapper = new QueryWrapper<>();
        QWrapper.eq("stu_id", l1.get(0));
        student student=new student();
        student = studentServiceimpl.getOne(QWrapper);    //获取学生信息。包括积分
        Long sId = student.getStuId();
        course course=new course();
        int total = 0;
        for (int i : l2) {
            course = courseServiceimpl.getById(i);
             int t = course.getCouPrice();
            total += t;
        }
        if (student.getStuScore() < total) {
            return Result.error(Constants.CODE_600, "您的积分不够！购买失败");
        }

        QueryWrapper<order> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("u_id", l3.get(0));
        QueryWrapper.eq("stu_id", String.valueOf(l1.get(0)));
        QueryWrapper.eq("orderForm_date",dateTime);
        order order1 = orderService.getOne(QueryWrapper);

        for (int i : l2) {
            CouStu couStu = new CouStu();
            couStu.setStuId(sId);
            couStu.setCouId((long) i);
            orderMapper.addCourse(couStu);
            //去点赞评论中间表看看有没有数据
            courseInteraction courseInteraction=new courseInteraction();
            QueryWrapper<courseInteraction> rapper = new QueryWrapper<>();
            rapper.eq("stu_id",sId);
            rapper.eq("cou_id",i);
            courseInteraction qwq =  courseInteractionServiceimpl.getOne(rapper);
            if (qwq!=null){
                if (qwq.getHave()!=1){
                    qwq.setHave(1);
                    courseInteractionServiceimpl.saveOrUpdate(qwq);
                }
            }
            else {
                courseInteraction.setStuId(Math.toIntExact(sId));
                courseInteraction.setCouId(i);
                courseInteraction.setHave(1);
                courseInteractionServiceimpl.saveOrUpdate(courseInteraction);
            }
        }

        student.setStuScore(student.getStuScore() - total); //学生积分减去课程积分
        studentServiceimpl.saveOrUpdate(student);

        List<orderDetail> owo = new ArrayList<>();
        for (int t=0;t<l2.size();t++) {
            int i= l2.get(t);
            orderDetail orderDetail = new orderDetail();
            orderDetail.setOrderId(Math.toIntExact(order1.getId()));
            orderDetail.setCouId(i);
            owo.add(t,orderDetail);

            QueryWrapper<stuCouCar> que=new QueryWrapper<>();
            que.eq("stu_id",sId);
            que.eq("cou_id",i);
            stuCouCar stuCouCar=new stuCouCar();
            stuCouCar=stuCouCarServiceimpl.getOne(que);
            if (stuCouCar!=null){
                stuCouCarServiceimpl.remove(que);
            }

        }
        orderDetailService.saveBatch(owo);
        return Result.success(order);
    }



    //查订单信息
    @GetMapping("/findorderdetail")
    public Result selectOrderDetail(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "") String orderId,
            @RequestParam(defaultValue = "") String couId,
            @RequestParam(defaultValue = "") String uId,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String couName,
            @RequestParam(defaultValue = "") String teaId,
            @RequestParam(defaultValue = "") String couPrice,
            @RequestParam(defaultValue = "") String orderFormDate,
            @RequestParam(defaultValue = "") String orderFormP     //订单完成情况

            ) {

        return Result.success(orderService.selectOrderDetail(pageNum, pageSize, orderId, couId,uId,username,couName,teaId,couPrice,orderFormDate,orderFormP));
    }








}
