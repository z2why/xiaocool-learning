package entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class infoUserDTO {
    //章节表
    @TableId(type = IdType.AUTO)
    private Long    infoId; //资讯id
    private String  infoTitle;	//资讯标题
    private String  infoMain;	//资讯内容
    private String  infoAuthor;	//资讯作者

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("info_data")
    private LocalDateTime infoData;	//发布日期

    private String  infoLike;	//点赞数
    private String uId;
    private String avatar;

}
