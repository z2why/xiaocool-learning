package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class tqUserDTO {

    //问题表
    @TableId(type = IdType.AUTO)
    private Long tqId;	//id
    private int uId;	//发布者id
    private String truename;
    private String content;	//内容

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;	//发布时间

    private int isQuestion;	//是否为问题
    @TableField("answered_tq_id")
    private int answeredTqId;	//被回复的问题id
    private int isSelect;	//是否被采纳
    private String stuId;
    private String actor;
    private String avatar;


}
