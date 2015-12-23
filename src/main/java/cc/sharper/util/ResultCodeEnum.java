package cc.sharper.util;

/**
 * Created by liumin3 on 2015/12/23.
 */
public enum ResultCodeEnum
{
    SUCCESS(1, "成功");

    private Integer code;
    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

}
