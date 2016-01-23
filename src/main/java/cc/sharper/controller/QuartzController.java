package cc.sharper.controller;

import cc.sharper.base.BaseController;
import cc.sharper.quartz.ScheduleJob;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liumin3 on 2016/1/23.
 */

@Controller
@RequestMapping("/quartz")
public class QuartzController extends BaseController
{

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private final Logger log = LoggerFactory.getLogger(getClass());


    @RequestMapping("/add")
    public void add()
    {
        try
        {

        }catch (Exception e)
        {

        }
    }
    @RequestMapping("/delete")
    public void delete(ScheduleJob scheduleJob)
    {
        try
        {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            scheduler.deleteJob(jobKey);
        }catch (Exception e)
        {

        }

    }

    @RequestMapping("/update")
    public void update()
    {
        try
        {

        }catch (Exception e)
        {

        }
    }


    @RequestMapping("/stop")
    public void stop(ScheduleJob scheduleJob)
    {

        try
        {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e)
        {
            e.printStackTrace();
        }
    }
    @RequestMapping("/restard")
    public  void restard(ScheduleJob scheduleJob)
    {
        try
        {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            scheduler.resumeJob(jobKey);
        }catch (Exception e)
        {

        }
    }

}
