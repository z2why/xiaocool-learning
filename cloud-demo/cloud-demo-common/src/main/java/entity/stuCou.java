package entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class stuCou implements Serializable {
    //学生-课程关联表(适用于自选课程)
    @TableId(type = IdType.AUTO)
    private Long id;
    private  int couId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime selectTime;  //选课时间

    private Long stuId;	//学生id
    private int stuStudyTime;	//学生学习时长
    private int stuScore;	//学生积分
    private String stuName;	//学生姓名


    private int collect; //是否收藏
    private int have; //是否拥有
    private int recommend; //是否点赞

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;  //更新时间

    private String couName;
    private String couIntroduction;  //课程介绍
    private String couType;         //课程类别
    private String couChileType;    //课程类别
    private String couPrice;        //课程价格
    private String couChaNum;    //课程章节数
    private String teaId;           //课程作者
    private String couCollNum;    //课程收藏数
    private String couRecoNum;    //课程推荐数
    private String deleted;       //是否删除
    private String truename;           //课程作者

    private String cover;       //

}
