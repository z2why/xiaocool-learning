package entity;

import com.baomidou.mybatisplus.annotation.IdType;
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


@TableName("stu_practice")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class practiceStu  implements Serializable {
    //学生-实践关联表(适用于自选课程)

    @TableId(type = IdType.AUTO)
    private Long id;
    private int stuId;
    private int praId;
    private int audit; //审核是否通过 0位未审核 1为通过 2为不通过
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime praDate;  //报名时间
}
