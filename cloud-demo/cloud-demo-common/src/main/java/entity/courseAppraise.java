package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("course_appraise")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class courseAppraise {
    //章节表
    @TableId(type = IdType.AUTO)
    private Long id;
    private int couId;
    private int stuId;
    private String couAppraise;   //课程评价
}
