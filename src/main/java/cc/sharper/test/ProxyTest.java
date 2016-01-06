package cc.sharper.test;

/**
 * Created by liumin3 on 2015/12/27.
 */
public class ProxyTest
{
    public static void main(String[] args)
    {

        Subject subject = new ProxySubject();

        subject.request();

    }
}
