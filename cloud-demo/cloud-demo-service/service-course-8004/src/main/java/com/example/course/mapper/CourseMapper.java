package com.example.course.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseMapper extends BaseMapper<course> {

    @Insert("INSERT INTO cou_class (cou_id,class_id) SELECT #{couId} ,#{classId} from DUAL  where not exists(select * from cou_class where cou_id = #{couId} and class_id = #{classId})")
    int addCourse(couClass couClass);

    IPage<stuCou> selectstuCoursePage(Page<stuCou> page, @Param(Constants.WRAPPER) Wrapper<stuCou> wrapper);


    IPage<stuCou> selectCoursePage(Page<stuCou> page, @Param(Constants.WRAPPER) Wrapper<stuCou> wrapper);

    @Insert("INSERT INTO stu_cou (stu_id, cou_id) SELECT #{stuId},#{couId} from DUAL  where not exists(select * from stu_cou where stu_id=#{stuId} and cou_id = #{couId})")
    int addCou(CouStu CouStu);


    IPage<StuCouAppDTO> findStuCouApp(Page<StuCouAppDTO> page, @Param(Constants.WRAPPER) Wrapper<StuCouAppDTO> wrapper);



}
