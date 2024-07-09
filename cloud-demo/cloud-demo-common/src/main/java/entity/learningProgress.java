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

@TableName("learning_progress")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class learningProgress {
    //学习进度
    @TableId(type = IdType.AUTO)
    private Long id;  //id
    private Long  couId;
    private Long  stuId;
//    private int classId;	//班级id
    private Long chaId; 	//章节id
//    private Long  time;  //	已学习的时长
    private Long mediaDuration;
    private String learnedStatus;
    private Long mediaProgress;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private LocalDateTime updateTime; 	//记录更新的时间

}
