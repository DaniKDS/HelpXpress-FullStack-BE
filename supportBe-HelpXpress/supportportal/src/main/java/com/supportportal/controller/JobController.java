package com.supportportal.controller;

import com.supportportal.domain.Job;
import com.supportportal.service.impl.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobServiceImpl jobService;

    @GetMapping("/generate-random-jobs")
    public String generateRandomJobs() {
        jobService.addRandomJobs(100);
        return "100 de joburi random au fost adăugate cu succes.";
    }

    @GetMapping("/all")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }
}
