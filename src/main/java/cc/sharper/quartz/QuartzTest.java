package cc.sharper.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liumin3 on 2016/1/22.
 */
public class QuartzTest
{
    private static  final Logger log = LoggerFactory.getLogger(QuartzTest.class);

    public void run()
    {
        log.info("定时任务在执行！");
    }
}
