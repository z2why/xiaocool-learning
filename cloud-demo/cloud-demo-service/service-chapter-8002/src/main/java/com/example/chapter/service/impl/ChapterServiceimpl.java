package com.example.chapter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.chapter.mapper.ChapterMapper;
import com.example.chapter.service.IChapterService;
import entity.chapter;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChapterServiceimpl extends ServiceImpl<ChapterMapper, chapter> implements IChapterService {

    @Resource
    private ChapterMapper chapterMapper;


    @Override
    public void reorder(Map<String, Object> params) {
//        Integer pageNum = Integer.valueOf((String) params.get("pageNum"));
//        Integer pageSize = Integer.valueOf((String) params.get("pageSize"));
        int newIndex = Integer.parseInt((String) params.get("newIndex"));
        int dragFromOrder = Integer.parseInt((String) params.get("oldIndex"));
        int couId = Integer.parseInt((String) params.get("couId"));

        int from = Math.min(dragFromOrder, newIndex);
        int to =   Math.max(dragFromOrder, newIndex);
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("cha_index", dragFromOrder);
        columnMap.put("course_id",couId);
        List<chapter> list = chapterMapper.selectByMap(columnMap);
        chapter chapter = list.get(0);
        chapter.setChaIndex(-1);
        chapterMapper.updateById(chapter);
        if (dragFromOrder < newIndex) {
            chapterMapper.reorderClassify(from+1, to, -1,couId);
        } else {
            chapterMapper.reorderClassify(from, to-1, 1,couId);
        }
        Map<String, Object> columnMap2 = new HashMap<String, Object>();
        columnMap2.put("course_id",couId);
        columnMap2.put("cha_index", -1);
        List<chapter> list2 = chapterMapper.selectByMap(columnMap2);
        chapter chapter2 = list2.get(0);
        chapter2.setChaIndex(newIndex);
        chapterMapper.updateById(chapter2);

    }




}
