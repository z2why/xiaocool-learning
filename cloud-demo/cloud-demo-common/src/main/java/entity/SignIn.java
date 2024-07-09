package entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@TableName("sign_in")
@Data
@NoArgsConstructor
public class SignIn {


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 签到用户id
     */
    private Long stuId;

    /**
     * 连续签到天数
     */
    private Integer continueDays;

    /**
     * 更新日期, 最后签到日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    public SignIn(Long stuId, LocalDateTime updateTime) {
        this.stuId = stuId;
        this.updateTime = updateTime;
    }
}