package cc.fun.hutoolemail.demos.web.Misc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String code;
    private String message;
    private T data;


    public static <T> Result<T> success(String msg,T data) {
        return new Result<>("200", msg, data);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>("200", msg, null);
    }

    public static <T> Result<T> error(String code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>("500", message, null);
    }


}
