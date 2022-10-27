package kr.ac.cnu.swacademy.simplerpa.run;

import kr.ac.cnu.swacademy.simplerpa.service.JobDescriptorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExecutionJobDescriptorJob extends QuartzJobBean {

    private final JobDescriptorService jobDescriptorService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("ExecutionJobDescriptorJob) id={} 작업명세서 실행 시작..", context.getJobDetail().getJobDataMap().get("jobDescriptorId"));
        try {
            Long jobDescriptorId = (Long) context.getJobDetail().getJobDataMap().get("jobDescriptorId");
            jobDescriptorService.execute(jobDescriptorId);
//            .forEach((logResponseDto) -> log.info("ExecutionJobDescriptorJob) id={} 작업명세서 실행 완료 결과 : {}", jobDescriptorId, logOutputDto.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
