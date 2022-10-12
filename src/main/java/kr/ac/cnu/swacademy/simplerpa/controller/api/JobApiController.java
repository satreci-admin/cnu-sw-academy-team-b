package kr.ac.cnu.swacademy.simplerpa.controller.api;

import kr.ac.cnu.swacademy.simplerpa.dto.JobUpdateRequestDto;
import kr.ac.cnu.swacademy.simplerpa.service.JobService;
import kr.ac.cnu.swacademy.simplerpa.dto.JobSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class JobApiController {

    private final JobService jobService;

    @PostMapping("/job")
    public Long save(@RequestBody JobSaveRequestDto jobSaveRequestDto) {

        return jobService.create(jobSaveRequestDto);
    }

    @PutMapping("/job/{jobId}")
    public Long update(@RequestBody JobUpdateRequestDto jobUpdateRequestDto, @PathVariable Long jobId) {
        return jobService.update(jobUpdateRequestDto, jobId);
    }

    @DeleteMapping("/job/{jobId}")
    public void delete(@PathVariable Long jobId) {
        jobService.delete(jobId);
    }
}
