package kr.ac.cnu.swacademy.simplerpa.controller;

import kr.ac.cnu.swacademy.simplerpa.service.JobService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class JobController {

    private final JobService jobService;

    @GetMapping("/jobs/{jobDescriptorId}")
    public String jobList(Model model, @PathVariable Long jobDescriptorId) {
        model.addAttribute("jobList", jobService.findByJobDescriptorEntity(jobDescriptorId));
        model.addAttribute("jobDescriptorId", jobDescriptorId);
        return "job/jobList";
    }

    @GetMapping("/job/{id}")
    public String jobDetail(Model model, @PathVariable Long id)
    {
        model.addAttribute("job", jobService.findById(id).get() );
        return "job/jobDetail";
    }

    @GetMapping("/job/save/{jobDescriptorId}")
    public String jobSave(Model model, @PathVariable Long jobDescriptorId) {
        model.addAttribute("jobDescriptorId", jobDescriptorId);
        return "job/jobSave";
    }

}
