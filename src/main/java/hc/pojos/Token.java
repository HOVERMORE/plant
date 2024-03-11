package hc.pojos;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.sf.jsqlparser.statement.insert.Insert;

import java.time.LocalDateTime;

@Data
@ToString
@TableName("tb_token")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String userId;

    private String token;

    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill=FieldFill.UPDATE)
    private LocalDateTime updateTime;

    private Integer isDelete;
}
