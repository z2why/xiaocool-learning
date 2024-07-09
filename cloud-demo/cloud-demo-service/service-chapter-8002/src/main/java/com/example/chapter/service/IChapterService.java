package com.example.chapter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.chapter;

import java.util.Map;


public interface IChapterService extends IService<chapter> {


    void reorder(Map<String, Object> params);
}
