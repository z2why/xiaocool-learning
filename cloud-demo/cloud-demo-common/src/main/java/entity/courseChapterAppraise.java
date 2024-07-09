package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("course_chapter_appraise")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class courseChapterAppraise {
    //章节表
    @TableId(type = IdType.AUTO)
    private Long id;
    private int couId;
    private int chaId;
    private int stuId;
    private String chaAppraise;   //章节评价
}
