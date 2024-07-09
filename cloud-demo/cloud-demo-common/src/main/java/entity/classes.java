package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@TableName("class")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class classes implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long classId;
    private String className;
    private String teaId;
    private String teaName;
    private Integer classStudentNum;//班级学生人数
    private String deleted; //是否删除

}