package cc.fun.hutoolemail.demos.web.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegUser {
    private String email;
    private String password;
    private Integer code;
}
