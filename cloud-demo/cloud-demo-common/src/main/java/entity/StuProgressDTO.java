package entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class StuProgressDTO {
    private Long stuId;
    private Long couId;
    private Long classId;
    private Long lastChaId;
    private String truename;             //教师姓名

    private String learnedStatus;             //学习情况
    private Long learnedSectionCount;        //这门课已学完总的视频媒体数量
    private Long time;                       //这门课已学习完的总时长

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;  //第一次观看时间

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private LocalDateTime updateTime;  //观看进度更新

    private String couName;
    private String couIntroduction;  //课程介绍
    private String couType;         //课程类别
    private String couChileType;    //课程类别
    private int couPrice;        //课程价格
    private String teaId;           //课程作者
    private String couCollNum;    //课程收藏数
    private Long couChaNum;    //课程章节数

    private String couRecoNum;    //课程推荐数
    private String deleted;       //是否删除
    private String cover; //封面
}
