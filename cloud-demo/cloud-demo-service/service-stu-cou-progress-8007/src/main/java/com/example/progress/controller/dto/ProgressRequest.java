package com.example.progress.controller.dto;


import lombok.Data;
import org.jetbrains.annotations.NotNull;


@Data
public class ProgressRequest {

    private Long stuId;

    /**
     * 节id(媒体视频id)
     */
    private Long chaId;

    /**
     * 代表媒体视频或者文档属于哪个维度下的id，比如学习维度，娱乐维度
     */

    private Long couId;

    /**
     * 类型，代表当前是媒体视频还是文档
     */
    private String sectionType;

    /**
     * 增量时间，非视频为0，视频就传新增的看视频时长
     */
    private Long time;


    /**
     * 视频总时长
     */
    private Long mediaDuration;

    /**
     * 视频播放节点，非视频为0，视频就传已看到的视频节点
     */
    private Long mediaProgress;

    /**
     * 是否第一次打开当前媒体。(方便以后扩展使用)
     */
    private Boolean first;

}