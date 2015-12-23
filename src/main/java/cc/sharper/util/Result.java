package cc.sharper.util;

import java.io.Serializable;

/**
 * Created by liumin3 on 2015/12/23.
 */
public class Result<T> implements Serializable
{

    private Integer code;
    private String message;

    private T data;

    public static Result getSuccessResult() {
        return getResult(ResultCodeEnum.SUCCESS);
    }

    public static <T> Result getSuccessResult(T data) {
        return getResult(ResultCodeEnum.SUCCESS, data);
    }
    public static Result getFailureResult(Integer failureCode,String failureMessage){
        return getResult(failureCode , failureMessage , null );
    }
    public static <T> Result getFailureResult(Integer failureCode, String failureMessage,T data){
        return getResult(failureCode , failureMessage , data );
    }
    public static Result getResult(Integer code) {
        return getResult(code, null, null);
    }

    public static Result getResult(Integer code, String message) {
        return getResult(code, message, null);
    }

    public static Result getResult(ResultCodeEnum resultCodeEnum) {
        return getResult(resultCodeEnum.code(), resultCodeEnum.msg(), null);
    }

    public static <T> Result getResult(ResultCodeEnum resultCodeEnum, T data) {
        return getResult(resultCodeEnum.code(), resultCodeEnum.msg(), data);
    }

    @SuppressWarnings("unchecked")
    private static <T> Result getResult(Integer code, String message, T data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    private Result() {
    }

    public boolean isSuccess() {
        return is(ResultCodeEnum.SUCCESS);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean is(ResultCodeEnum e) {
        return code != null && code.equals(e.code());
    }

    public void setEnum(ResultCodeEnum e) {
        this.setCode(e.code());
        this.setMessage(e.msg());
    }

    public void setResult(Result<?> result) {
        this.setCode(result.getCode());
        this.setMessage(result.getMessage());
    }
}
