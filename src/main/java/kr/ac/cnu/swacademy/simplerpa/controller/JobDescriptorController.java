package kr.ac.cnu.swacademy.simplerpa.controller;

import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.service.JobDescriptorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class JobDescriptorController {

    private final JobDescriptorService jobDescriptorService;

    @GetMapping("/jobdescriptors")
    public String adminJobDescriptorsPage(Model model) {
        List<JobDescriptorListResponseDto> jobDescriptors = jobDescriptorService.findAllDesc();
        model.addAttribute("jobDescriptors", jobDescriptors);
        return "jobdescriptor/jobDescriptorList";
    }

    @GetMapping("/jobdescriptor/save")
    public String adminJobDescriptorSavePage() {
        return "jobdescriptor/saveForm";
    }

    @GetMapping("/jobdescriptor/{id}")
    public String adminJobDescriptorDetailPage(@PathVariable Long id, Model model) {
        model.addAttribute("jobDescriptor", jobDescriptorService.findById(id));
        return "jobdescriptor/detail";
    }
}
