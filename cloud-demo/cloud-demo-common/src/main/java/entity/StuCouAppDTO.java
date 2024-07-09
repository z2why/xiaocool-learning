package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class StuCouAppDTO {
    //课程评价中间表
    @TableId(type = IdType.AUTO)
    private Long id;
    private int couId;
    private int stuId;
    private String couAppraise;   //课程评价
    private String username;
    private String password;
    private String truename;
    private String actor;
    private String avatar;
}
