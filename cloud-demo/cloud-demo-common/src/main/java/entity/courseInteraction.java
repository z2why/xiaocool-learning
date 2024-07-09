package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@TableName("course_interaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class courseInteraction {
    //课程互动
    @TableId(type = IdType.AUTO)
    private Long id;
    private int couId;
    private int stuId;
    private int collect; //是否收藏
    private int have; //是否拥有
    private int recommend; //是否点赞
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;  //更新时间
}
