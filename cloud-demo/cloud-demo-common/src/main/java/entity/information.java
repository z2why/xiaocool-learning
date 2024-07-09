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

@TableName("information")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class information implements Serializable {
    //章节表
    @TableId(type = IdType.AUTO)
    private Long    infoId; //资讯id
    private String  infoTitle;	//资讯标题
    private String  infoMain;	//资讯内容
    private String  infoAuthor;	//资讯作者
    private String uId;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("info_data")
    private LocalDateTime infoData;	//发布日期

    private String  infoLike;	//点赞数
//    private String  teaId;	//教师ID
//
//    private String  teaName;	//教师ID





}