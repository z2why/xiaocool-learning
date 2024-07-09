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

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class student implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long stuId;	//学生id
    private String stuName;	//学生姓名
    private Long stuStudyTime;	//学生学习时长
    private Long todayStudyTime;	//学生今日学习时长

    private int stuScore;	//学生积分

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private LocalDateTime updateTime; 	//记录更新的时间
}
