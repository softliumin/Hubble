package cc.sharper.test;

/**
 * Created by liumin3 on 2015/12/27.
 */
public class ProxySubject extends Subject
{
    private RealSubject realSubject; //代理角色内部引用了真实角色

    private void preRequest()
    {
        System. out.println( "pre request");
    }

    private void postRequest()
    {
        System. out.println( "post request");
    }

    @Override
    public void request()
    {
        this.preRequest(); //在真实角色操作之前所附加的操作

        if( null == realSubject)
        {
            realSubject = new RealSubject();
        }

        realSubject.request(); //真实角色所完成的事情

        this.postRequest(); //在真实角色操作之后所附加的操作
    }



}
