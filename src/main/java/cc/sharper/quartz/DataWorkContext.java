package cc.sharper.quartz;

import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liumin3 on 2016/1/22.
 */
public class DataWorkContext
{

    static List<ScheduleJob> li = new ArrayList<ScheduleJob>();
    private static Map<String, ScheduleJob> jobMap = new HashMap<String, ScheduleJob>();

    static
    {
        for (int i = 0; i < 5; i++)
        {
            ScheduleJob job = new ScheduleJob();
            job.setJobId("10001" + i);
            job.setJobName("data_import" + i);
            job.setJobGroup("dataWork");
            job.setJobStatus("1");
            job.setCronExpression("0/5 * * * * ?");
            job.setDesc("数据导入任务");
            addJob(job);
            li.add(job);
        }
    }

    /**
     * 添加任务
     *
     * @param scheduleJob
     */
    public static void addJob(ScheduleJob scheduleJob)
    {
        jobMap.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(), scheduleJob);
    }
    public static List<ScheduleJob>  getAllJob()
    {
        return li;
    }

}
