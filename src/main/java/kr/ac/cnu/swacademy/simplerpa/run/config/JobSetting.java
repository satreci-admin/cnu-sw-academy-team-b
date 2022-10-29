package kr.ac.cnu.swacademy.simplerpa.run.config;

import kr.ac.cnu.swacademy.simplerpa.run.RegisterJobDescriptionJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;

import static org.quartz.JobBuilder.newJob;

@RequiredArgsConstructor
@Configuration
public class JobSetting {

    private final Scheduler scheduler;
    private final String SCHEDULE_EXP = "00 50 23 1/1 * ? *";
//    private final String SCHEDULE_EXP = "0 58 23 1/1 * ? *";

    @PostConstruct
    public void start(){
        try{
            scheduler.scheduleJob(buildJobDetail(RegisterJobDescriptionJob.class, new HashMap()), buildJobTrigger(SCHEDULE_EXP));
        } catch(SchedulerException e){
            e.printStackTrace();
        }
    }

    public JobDetail buildJobDetail(Class job, HashMap params){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return newJob(job).usingJobData(jobDataMap).build();
    }

    public Trigger buildJobTrigger(String scheduleExp){
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }
}
