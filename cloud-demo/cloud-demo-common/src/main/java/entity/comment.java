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

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class comment implements Serializable {
//咨询评论表
    @TableId(value = "c_id",type = IdType.AUTO)
    private  Long cId;


    @TableField("c_content")
    private String cContent;


    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("c_date")
    private LocalDateTime cDate;//评论时间


    private String infoId;//资讯ID

    @TableField("u_id")
    private String uId; //评论者ID-》userid

    private String role; //评论者身份

    private String truename;
    private String avatar;
}