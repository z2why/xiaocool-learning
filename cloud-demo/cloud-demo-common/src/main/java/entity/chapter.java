package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@TableName("chapter")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class chapter implements Serializable {
    //章节表
    @TableId(type = IdType.AUTO)
    private Long chaId;
    private int chaIndex;        // 顺序
    private String chaTitle;
    private Long courseId;
    private String chaUrl;
    private String chaTime;     // 章节时长，以秒为单位


}