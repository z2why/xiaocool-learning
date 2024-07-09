package com.example.practice.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.couClass;
import entity.practice;
import entity.stuClass;
import entity.stuPractice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PracticeMapper extends BaseMapper<practice> {

    @Insert("INSERT INTO stu_practice (stu_id,pra_id) SELECT #{stuId} ,#{praId} from DUAL  where not exists(select * from stu_practice where stu_id = #{stuId} and pra_id = #{praId})")
    int addPractice(stuPractice stuPractice);



    IPage<stuPractice> selectPracticePage(Page<stuPractice> page, @Param(Constants.WRAPPER) Wrapper<stuPractice> wrapper);

    IPage<stuPractice> PracticePage(Page<stuPractice> page, @Param(Constants.WRAPPER) Wrapper<stuPractice> wrapper);

}
