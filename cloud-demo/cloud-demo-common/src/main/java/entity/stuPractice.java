package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
public class stuPractice {
    //学生-实践关联表(适用于自选课程)

    @TableId(type = IdType.AUTO)
    private Long id;
    private int stuId;
    private int praId;
    private int audit; //审核是否通过 0位未审核 1为通过 2为不通过
    
    
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime praDate;  //报名时间

    //学生信息
    private String stuName;	//学生姓名
    private int stuStudyTime;	//学生学习时长
    private int stuScore;	//学生积分

    //课程
    private Long couId;
    private String couName;
//    private String couIntroduction;  //课程介绍
//    private String couType;         //课程类别
//    private String couChileType;    //课程类别
//    private int couPrice;        //课程价格
//    private String couChaNum;    //课程章节数
//    private String teaId;           //课程作者
//    private String couCollNum;    //课程收藏数
//    private String couRecoNum;    //课程推荐数
    private String truename;


    //实践
    private String praName;
    private String praTime;
    private String praSite;//实践地点
    private String praMain; //实践内容
    private String deleted;       //是否删除
}
