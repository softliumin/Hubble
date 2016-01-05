package cc.sharper.base;

/**
 * 前台直接执行sql时候的sql类型
 * Created by liumin3 on 2016/1/5.
 */
public enum SqlTypeEnum
{
    ADD("0","增加"),
    DELETE("1","删除"),
    UPDATE("2","修改"),
    SELECT("3","查询");
    SqlTypeEnum(String id,String msg){
        this.msg = msg;
        this.id = id;
    }
    private String id;
    private String msg;

}
