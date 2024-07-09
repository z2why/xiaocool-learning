    package com.example.chapter.controller;

    import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
    import com.baomidou.mybatisplus.core.metadata.IPage;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
    import com.example.chapter.mapper.CouChaAppraiseMapper;
    import com.example.chapter.service.impl.CouChaAppraiseServiceimpl;

    import entity.courseChapterAppraise;
    import org.springframework.web.bind.annotation.*;
    import vo.Result;

    import javax.annotation.Resource;


    @RestController
    @RequestMapping("/couChaAppraise")
    public class CouChaAppraiseController {
        @Resource
        private CouChaAppraiseServiceimpl couChaAppraiseServiceimpl;

        //查找所有订单
        @GetMapping
        public Result findAll(){
            return Result.success(couChaAppraiseServiceimpl.list());
        }

        @Resource
        private CouChaAppraiseMapper couChaAppraiseMapper;


        @PostMapping
        public Result save(@RequestBody courseChapterAppraise courseChapterAppraise){
            return Result.success(couChaAppraiseServiceimpl.saveOrUpdate(courseChapterAppraise));
        }

        //删除数据
        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id){
            return Result.success(couChaAppraiseServiceimpl.removeById(id));
        }


        //删除评价
        @GetMapping("/deleteChaApp")
        public Result delete(@RequestParam Long id){
            return Result.success(couChaAppraiseServiceimpl.removeById(id));
        }


        //根据ID查询评论
        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id){
            return Result.success(couChaAppraiseServiceimpl.getById(id));
        }

        //分页查询
        @GetMapping("/page")
        public Result findPage(
                @RequestParam Integer pageNum,
                @RequestParam Integer pageSize,
                @RequestParam(defaultValue = "") String id,
                @RequestParam(defaultValue = "") String couId,
                @RequestParam(defaultValue = "") String stuId,
                @RequestParam(defaultValue = "") String chaId,
                @RequestParam(defaultValue = "") String chaAppraise
        ){
            IPage<courseChapterAppraise> page = new Page<>(pageNum,pageSize);
            QueryWrapper<courseChapterAppraise> queryWarpper =new QueryWrapper<>();
            if (!"".equals(id)){
                queryWarpper.eq("id",id);
            }

            if (!"".equals(couId)){
                queryWarpper.eq("cou_id",couId);
            }

            if (!"".equals(stuId)){
                queryWarpper.eq("stu_id",stuId);
            }

            if (!"".equals(chaId)){
                queryWarpper.eq("cha_id",chaId);
            }

            if (!"".equals(chaAppraise)){
                queryWarpper.like("cou_appraise",chaAppraise);
            }
            return Result.success(couChaAppraiseServiceimpl.page(page,queryWarpper));
        }

        //根据学生信息查课程或根据课程信息查学生
        @GetMapping("/findStuCouChaApp")
        public Result findStuCouChaApp(
                @RequestParam(defaultValue = "") String couId,
                @RequestParam(defaultValue = "") String stuId,
                @RequestParam(defaultValue = "") String chaId,
                @RequestParam(defaultValue = "") String chaAppraise

        ) {

            return Result.success(couChaAppraiseServiceimpl.findStuCouChaApp(stuId,couId,chaId,chaAppraise));
        }



    }
