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

import java.time.LocalDateTime;

@TableName("order_form")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class order {
    @TableId(type = IdType.AUTO)
    private Long Id;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("orderForm_date")
    private LocalDateTime orderFormDate; //	购买时间

    @TableField("orderForm_p")
    private int orderFormP; //	订单完成情况

    @TableField("orderForm_detail")
    private String orderFormDetail; //	订单说明
    private Long uId;
    private String stuId;





}
