package kr.ac.cnu.swacademy.simplerpa.controller.api;

import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorUpdateRequestDto;
import kr.ac.cnu.swacademy.simplerpa.service.JobDescriptorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class JobDescriptorApiController {

    private final JobDescriptorService jobDescriptorService;

    @GetMapping("/api/v1/jobdescriptors")
    public ResponseEntity<List<JobDescriptorListResponseDto>> findAllDesc() {
        List<JobDescriptorListResponseDto> jobDescriptors = jobDescriptorService.findAllDesc();
        return ResponseEntity.ok(jobDescriptors);
    }

    @GetMapping("/api/v1/jobdescriptor/{id}")
    public ResponseEntity<JobDescriptorResponseDto> findById(@PathVariable Long id) {
        JobDescriptorResponseDto jobDescriptor = jobDescriptorService.findById(id);
        return ResponseEntity.ok(jobDescriptor);
    }

    @PostMapping("/api/v1/jobdescriptor")
    public ResponseEntity<Long> save(@RequestBody JobDescriptorSaveRequestDto requestDto) {
        Long jobDescriptorId = jobDescriptorService.save(requestDto);
        return ResponseEntity.ok(jobDescriptorId);
    }

    @PutMapping("/api/v1/jobdescriptor/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody JobDescriptorUpdateRequestDto requestDto) {
        Long jobDescriptorId = jobDescriptorService.update(id, requestDto);
        return ResponseEntity.ok(jobDescriptorId);
    }

    @DeleteMapping("/api/v1/jobdescriptor/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        Long jobDescriptorId = jobDescriptorService.delete(id);
        return ResponseEntity.ok(jobDescriptorId);
    }

    @GetMapping("/api/v1/exec/jobdescriptor/{id}")
    public void execute(@PathVariable Long id){
        jobDescriptorService.execute(id);
    }
}
