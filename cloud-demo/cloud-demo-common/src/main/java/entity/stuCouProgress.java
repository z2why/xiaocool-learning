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

@TableName("stu_cou_progress")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class stuCouProgress {
    //学生-课程学习进度（适用于自选课程）

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long stuId;
    private Long couId;
    private Long lastChaId;
    private String learnedStatus;             //学习情况
    private Long learnedSectionCount;        //这门课已学完总的视频媒体数量
    private Long time;                       //这门课已学习完的总时长

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private LocalDateTime updateTime;

}
