package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class orderDetailDTO {
    //订单课程关联表
    @TableId(type = IdType.INPUT)
    private Long id;
    private int couId;
    private int orderId;


    //order表
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderFormDate; //	购买时间
    private String orderFormDetail; //	订单说明
    private Long uId;
    private String username;
    private Long stuId;
    private Long orderFormP; //	订单完成情况


    //course表
    private String couName;
    private String cover;
    private String couIntroduction;  //课程介绍
    private String couType;         //课程类别
    private String couChileType;    //课程类别
    private String couPrice;        //课程价格
    private String couChaNum;    //课程章节数
    private String teaId;           //课程作者
    private String couCollNum;    //课程收藏数
    private String couRecoNum;    //课程推荐数
    private String deleted;       //是否删除

}
