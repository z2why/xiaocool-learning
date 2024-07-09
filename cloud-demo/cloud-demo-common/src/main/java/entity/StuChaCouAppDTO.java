package entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class StuChaCouAppDTO {
    //课程评价中间表
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long couId;
    private Long stuId;
    private Long chaId;
    private String chaAppraise;   //课程评价
    private String username;
    private String truename;
    private String avatar;
}
