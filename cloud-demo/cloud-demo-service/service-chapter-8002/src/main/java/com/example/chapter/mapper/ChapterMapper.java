package com.example.chapter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.chapter;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ChapterMapper extends BaseMapper<chapter> {



    void reorderClassify(int from, int to, int value,int couId);

}
