package site.bzyl.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data // 没有getter方法会导致响应给前端的结果是空白
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 查询到的结果数据，
     */
    private T data;

    private static final Integer SUCCESS_CODE = 200;

    private static final Integer ERROR_CODE = 500;

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }


    public static <T> ResponseResult success() {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.code = SUCCESS_CODE;
        responseResult.msg = "响应成功";
        responseResult.data = null;
        return responseResult;
    }

    public static <T> ResponseResult success(T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.code = SUCCESS_CODE;
        responseResult.msg = "响应成功";
        responseResult.data = data;
        return responseResult;
    }


    public static <T> ResponseResult failure(RuntimeException ex) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.code = 500;
        responseResult.msg = ex.getMessage();
        responseResult.data = null;
        return responseResult;
    }

    public static <T> ResponseResult error(String msg) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.code = ERROR_CODE;
        responseResult.msg = msg;
        responseResult.data = null;
        return responseResult;
    }
}