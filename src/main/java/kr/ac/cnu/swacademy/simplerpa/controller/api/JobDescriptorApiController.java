package kr.ac.cnu.swacademy.simplerpa.controller.api;

import kr.ac.cnu.swacademy.simplerpa.dto.*;
import kr.ac.cnu.swacademy.simplerpa.service.JobDescriptorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.of(jobDescriptorService.findById(id));
    }

    @PostMapping("/api/v1/jobdescriptor")
    public ResponseEntity<Long> save(@RequestBody JobDescriptorSaveRequestDto requestDto) {
        Optional<Long> jobDescriptorId = jobDescriptorService.save(requestDto);
        return ResponseEntity.of(jobDescriptorId);
    }

    @PutMapping("/api/v1/jobdescriptor/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody JobDescriptorUpdateRequestDto requestDto) {
        Optional<Long> jobDescriptorId = jobDescriptorService.update(id, requestDto);
        return ResponseEntity.of(jobDescriptorId);
    }

    @DeleteMapping("/api/v1/jobdescriptor/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        Long jobDescriptorId = jobDescriptorService.delete(id);
        return ResponseEntity.ok(jobDescriptorId);
    }

    @GetMapping("/api/v1/exec/jobdescriptor/{id}")
    public ResponseEntity<LogOutputDto> execute(@PathVariable Long id) {
        try {
            LogOutputDto execute = jobDescriptorService.execute(id);
            return ResponseEntity.ok(execute);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
