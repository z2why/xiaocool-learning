package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@TableName("order_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class orderDetail {
    //订单课程关联表
    @TableId(type = IdType.INPUT)
    private Long id;
    private int couId;
    private int orderId;
}
