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

@TableName("stu_cou")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CouStu implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("cou_id")
    private Long couId;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("select_time")
    private LocalDateTime selectTime;  //选课时间

    @TableField("stu_id")
    private Long stuId;	//学生id

}
