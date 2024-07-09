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

@TableName("cours_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class cousType implements Serializable {
    //章节表
    @TableId(type = IdType.AUTO)
    private Long id;
    private int couType;	//课程类别
    private int couParentType;	//父类别ID

}