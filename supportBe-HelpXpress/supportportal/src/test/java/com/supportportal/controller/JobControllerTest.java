package com.supportportal.controller;

import com.supportportal.domain.Job;
import com.supportportal.service.impl.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JobControllerTest {

    @Mock
    private JobServiceImpl jobService;

    @InjectMocks
    private JobController jobController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateRandomJobs() {
        doNothing().when(jobService).addRandomJobs(100);

        String response = jobController.generateRandomJobs();

        verify(jobService, times(1)).addRandomJobs(100);
        assertEquals("100 de joburi random au fost adăugate cu succes.", response);
    }

    @Test
    public void testGetAllJobs() {
        List<Job> jobs = Collections.singletonList(new Job());
        when(jobService.getAllJobs()).thenReturn(jobs);

        List<Job> response = jobController.getAllJobs();

        verify(jobService, times(1)).getAllJobs();
        assertEquals(jobs, response);
    }
}
