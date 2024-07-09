package com.example.student.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.stuClass;
import entity.student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudentMapper extends BaseMapper<student> {

    @Insert("INSERT INTO stu_class (stu_id, class_id) SELECT #{stuId},#{classId} from DUAL  where not exists(select * from stu_class where stu_id=#{stuId} and class_id = #{classId})")
    int joinClass(stuClass stuClass);

    IPage<stuClass> selectstuClassPage(Page<stuClass> page, @Param(Constants.WRAPPER) Wrapper<stuClass> wrapper);


}
