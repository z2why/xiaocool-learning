package entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class CouCarDTO {

    @TableId(type = IdType.AUTO)
    private Long id;
    private int stuId;
    private  int couId;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("add_time")
    private LocalDateTime addTime;  //加入购物车时间


    private String couName;
    private String couIntroduction;  //课程介绍
    private String couType;         //课程类别
    private String couChileType;    //课程类别
    private int couPrice;        //课程价格
    private String couChaNum;    //课程章节数
    private String teaId;           //课程作者
    private String couCollNum;    //课程收藏数
    private String couRecoNum;    //课程推荐数
    private String deleted;       //是否删除
    private String cover; //封面

}
