package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@TableName("practice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class practice implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long praId;
    private String praName;
    private String praTime;
    private String couId;
    private String teaId;
    private String praSite;//实践地点
    private String praMain; //实践内容
    private int deleted;

}