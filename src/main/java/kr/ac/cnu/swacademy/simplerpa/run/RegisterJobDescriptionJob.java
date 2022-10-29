package kr.ac.cnu.swacademy.simplerpa.run;

import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorResponseDto;
import kr.ac.cnu.swacademy.simplerpa.service.JobDescriptorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.quartz.JobBuilder.newJob;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterJobDescriptionJob extends QuartzJobBean {

    private final JobDescriptorService jobDescriptorService;

    private final Scheduler scheduler;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("RegisterJobDescriptionJob) 오늘 실행될 작업명세서들을 스케줄러에 등록 중..");
        LocalDate tomorrowDate = LocalDate.now();
//        LocalDate tomorrowDate = LocalDate.now().plusDays(1);
        LocalDateTime startExecutedDatetime = tomorrowDate.atStartOfDay();
        LocalDateTime endExecutedDatetime = tomorrowDate.atTime(23, 59, 59);

        List<JobDescriptorResponseDto> todayJobDescritors = jobDescriptorService.findAllByExecutedDatetimeBetween(startExecutedDatetime, endExecutedDatetime);

        todayJobDescritors.forEach((jobDescriptor) -> {
            HashMap<String, Object> params = new HashMap<>();
            params.put("jobDescriptorId", jobDescriptor.getId());

            LocalDateTime executedDatetime = jobDescriptor.getExecutedDateTime();
            int second = executedDatetime.getSecond();
            int minute = executedDatetime.getMinute();
            int hour = executedDatetime.getHour();
            int dayOfMonth = executedDatetime.getDayOfMonth();
            int month = executedDatetime.getMonth().getValue();
            int year = executedDatetime.getYear();

            StringBuilder sb = new StringBuilder();
            if(Boolean.FALSE.equals(jobDescriptor.getIsRepeat())) {
                String scheduleExp = sb
                        .append(second).append(" ")
                        .append(minute).append(" ")
                        .append(hour).append(" ")
                        .append(dayOfMonth).append(" ")
                        .append(month).append(" ")
                        .append("?").append(" ")
                        .append(year)
                        .toString();
                try{
                    scheduler.scheduleJob(buildJobDetail(ExecutionJobDescriptorJob.class, params), buildJobTrigger(scheduleExp));
                    log.info("id={} 작업명세서가 {}에 실행 예약되었습니다.", jobDescriptor.getId(), scheduleExp);
                } catch(SchedulerException e){
                    e.printStackTrace();
                }
            }else {
                String scheduleExp = sb
                        .append(second).append(" ")
                        .append(minute).append(" ")
                        .append(hour).append(" ")
                        .append("1/1 * ? *")
                        .toString();
                try{
                    scheduler.scheduleJob(buildJobDetail(ExecutionJobDescriptorJob.class, params), buildJobTrigger(scheduleExp));
                    log.info("id={} 작업명세서가 {}에 실행 예약되었습니다.", jobDescriptor.getId(), scheduleExp);
                } catch(SchedulerException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private JobDetail buildJobDetail(Class job, HashMap params){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return newJob(job).usingJobData(jobDataMap).build();
    }

    private Trigger buildJobTrigger(String scheduleExp){
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }
}
