package cc.sharper.test;

import org.apache.velocity.tools.generic.ClassTool;

/**
 * Created by liumin3 on 2015/12/27.
 */
public class RealSubject extends Subject
{
    @Override
    public void request()
    {
        System.out.printf("from real Subject");
    }



}
