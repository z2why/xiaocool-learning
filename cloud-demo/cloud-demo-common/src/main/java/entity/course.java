package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@TableName("course")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class course implements Serializable {
    //章节表
    @TableId(type = IdType.AUTO)
    private Long couId;
    private String couName;
    private String couIntroduction;  //课程介绍
    private String couType;         //课程类别
    private String couChileType;    //课程类别
    private int couPrice;        //课程价格
    private String teaId;           //课程作者
    private String couCollNum;    //课程收藏数
    private Long couChaNum;    //课程章节数

    private String couRecoNum;    //课程推荐数
    private String deleted;       //是否删除
    private String cover; //封面
}


