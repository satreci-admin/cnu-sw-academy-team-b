package kr.ac.cnu.swacademy.simplerpa.controller;

import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorUpdateRequestDto;
import kr.ac.cnu.swacademy.simplerpa.service.JobDescriptorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class JobDescriptorApiController {

    private final JobDescriptorService jobDescriptorService;

    @PostMapping("/jobdescriptor")
    public ResponseEntity<Long> save(@RequestBody JobDescriptorSaveRequestDto requestDto) {
        Long jobDescriptorId = jobDescriptorService.save(requestDto);
        return ResponseEntity.ok(jobDescriptorId);
    }

    @PutMapping("/jobdescriptor/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody JobDescriptorUpdateRequestDto requestDto) {
        Long jobDescriptorId = jobDescriptorService.update(id, requestDto);
        return ResponseEntity.ok(jobDescriptorId);
    }

    @DeleteMapping("/jobdescriptor/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        Long jobDescriptorId = jobDescriptorService.delete(id);
        return ResponseEntity.ok(jobDescriptorId);
    }
}
