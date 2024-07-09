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

@TableName("topic_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class topicQuestion {
    //问题表
    @TableId(type = IdType.AUTO)
    private Long tqId;	//id
    @TableField("u_id")
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
}
