package cc.fun.hutoolemail.demos.web.Entity;

import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class vEmail {
    private Integer id;
    private String email;
    private Integer code;
    private DateTime created_at;
}
