package cc.sharper.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liumin3 on 2016/1/22.
 */

//@DisallowConcurrentExecution
public class QuartzJobFactory implements Job
{
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        log.info("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
        log.info("任务名称 = [" + scheduleJob.getJobName() + "]");
    }
}
